/*
 * Copyright 2026 WaterdogTEAM
 * Licensed under the GNU General Public License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.waterdog.waterdogpe.network.protocol;

import dev.waterdog.waterdogpe.ProxyServer;
import dev.waterdog.waterdogpe.network.protocol.handler.ProxyBatchBridge;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.BedrockPacketSerializer;
import org.cloudburstmc.protocol.bedrock.codec.v388.BedrockCodecHelper_v388;
import org.cloudburstmc.protocol.bedrock.codec.v388.Bedrock_v388;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket;
import org.cloudburstmc.protocol.common.util.TextConverter;
import org.cloudburstmc.protocol.common.util.VarInts;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Base64;
import java.util.List;

import static org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket.Action;
import static org.cloudburstmc.protocol.bedrock.packet.PlayerListPacket.Entry;
import static org.cloudburstmc.protocol.common.util.Preconditions.checkArgument;

public class CustomPlayerListSerializer implements BedrockPacketSerializer<PlayerListPacket> {
    public static final CustomPlayerListSerializer INSTANCE = new CustomPlayerListSerializer();

    private static final String GEOMETRY_HUMANOID;

    static {
        String geoData;
        try (var stream = CustomPlayerListSerializer.class.getClassLoader().getResourceAsStream("skin_geometry.json")) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
            geoData = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
        } catch (IOException e) {
            geoData = "";
            ProxyServer.getInstance().getLogger().error("Failed to load skin geometry data", e);
        }
        GEOMETRY_HUMANOID = geoData;
    }

    @Override
    public void serialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket packet) {
        buffer.writeByte(packet.getAction().ordinal());
        VarInts.writeUnsignedInt(buffer, packet.getEntries().size());

        if (packet.getAction() == Action.ADD) {
            for (Entry entry : packet.getEntries()) {
                this.writeEntryBase(buffer, helper, entry);
            }

            for (Entry entry : packet.getEntries()) {
                buffer.writeBoolean(entry.isTrustedSkin());
            }
        }
        else {
            for (Entry entry : packet.getEntries()) {
                helper.writeUuid(buffer, entry.getUuid());
            }
        }
    }

    @Override
    public void deserialize(ByteBuf buffer, BedrockCodecHelper helper, PlayerListPacket packet) {
        Action action = Action.values()[buffer.readUnsignedByte()];
        packet.setAction(action);
        int length = VarInts.readUnsignedInt(buffer);

        if (action == Action.ADD) {
            for (int i = 0; i < length; i++) {
                packet.getEntries().add(this.readEntryBase(buffer, helper));
            }

            for (int i = 0; i < length && buffer.isReadable(); i++) {
                packet.getEntries().get(i).setTrustedSkin(buffer.readBoolean());
            }
        }
        else {
            for (int i = 0; i < length; i++) {
                packet.getEntries().add(new Entry(helper.readUuid(buffer)));
            }
        }
    }

    protected void writeEntryBase(ByteBuf buffer, BedrockCodecHelper helper, Entry entry) {
        helper.writeUuid(buffer, entry.getUuid());
        VarInts.writeLong(buffer, entry.getEntityId());
        TextConverter converter = helper.getTextConverter();
        helper.writeString(buffer, converter.serialize(entry.getName(CharSequence.class)));
        helper.writeString(buffer, entry.getXuid());
        helper.writeString(buffer, entry.getPlatformChatId());
        buffer.writeIntLE(entry.getBuildPlatform());
        helper.writeSkin(buffer, entry.getSkin());
        buffer.writeBoolean(entry.isTeacher());
        buffer.writeBoolean(entry.isHost());
        buffer.writeIntLE(entry.getColor().getRGB());
    }

    protected Entry readEntryBase(ByteBuf buffer, BedrockCodecHelper helper) {
        Entry entry = new Entry(helper.readUuid(buffer));
        entry.setEntityId(VarInts.readLong(buffer));
        TextConverter converter = helper.getTextConverter();
        entry.setName(converter.deserialize(helper.readString(buffer)));
        entry.setXuid(helper.readString(buffer));
        entry.setPlatformChatId(helper.readString(buffer));
        entry.setBuildPlatform(buffer.readIntLE());
        entry.setSkin(readSkin(buffer, helper));
        entry.setTeacher(buffer.readBoolean());
        entry.setHost(buffer.readBoolean());
        entry.setColor(new Color(buffer.readIntLE(), true));
        return entry;
    }


    public SerializedSkin readSkin(ByteBuf buffer, BedrockCodecHelper helper) {
        String skinId = helper.readString(buffer);
        String playFabId = helper.readString(buffer);
        String skinResourcePatch = helper.readString(buffer);
        ImageData skinData = this.readImage(buffer,helper);

        List<AnimationData> animations = new ObjectArrayList<>();
        helper.readArray(buffer, animations, ByteBuf::readIntLE, (b, h) -> this.readAnimationData(b,helper));

        ImageData capeData = this.readImage(buffer, helper);
        String geometryData = helper.readStringMaxLen(buffer, helper.getEncodingSettings().maxGeometryDataSize());
        String geometryDataEngineVersion = helper.readString(buffer);
        String animationData = helper.readString(buffer);
        String capeId = helper.readString(buffer);
        String fullSkinId = helper.readString(buffer);
        String armSize = helper.readString(buffer);
        String skinColor = helper.readString(buffer);

        List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
        helper.readArray(buffer, personaPieces, ByteBuf::readIntLE, (buf, h) -> {
            String pieceId = helper.readString(buf);
            String pieceType = helper.readString(buf);
            String packId = helper.readString(buf);
            boolean isDefault = buf.readBoolean();
            String productId = helper.readString(buf);
            return new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId);
        });

        List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
        helper.readArray(buffer, tintColors, ByteBuf::readIntLE, (buf, h) -> {
            String pieceType = helper.readString(buf);
            List<String> colors = new ObjectArrayList<>();
            int colorsLength = buf.readIntLE();
            for (int i2 = 0; i2 < colorsLength; i2++) {
                colors.add(helper.readString(buf));
            }
            return new PersonaPieceTintData(pieceType, colors);
        });

        boolean premium = buffer.readBoolean();
        boolean persona = buffer.readBoolean();
        boolean capeOnClassic = buffer.readBoolean();
        boolean primaryUser = buffer.readBoolean();
        boolean overridingPlayerAppearance = buffer.readBoolean();

        return SerializedSkin.of(skinId, playFabId, skinResourcePatch, skinData, animations, capeData, geometryData, geometryDataEngineVersion,
                animationData, premium, persona, capeOnClassic, primaryUser, capeId, fullSkinId, armSize, skinColor, personaPieces, tintColors,
                overridingPlayerAppearance);
    }


    public AnimationData readAnimationData(ByteBuf buffer, BedrockCodecHelper helper) {
        ImageData image = readImage(buffer, helper);
        AnimatedTextureType textureType = AnimatedTextureType.values()[buffer.readIntLE()];
        float frames = buffer.readFloatLE();
        AnimationExpressionType expressionType = AnimationExpressionType.values()[buffer.readIntLE()];
        return new AnimationData(image, textureType, frames, expressionType);
    }


    public ImageData readImage(ByteBuf buffer, BedrockCodecHelper helper) {
        int width = buffer.readIntLE();
        int height = buffer.readIntLE();
        byte[] image = helper.readByteArray(buffer, Integer.MAX_VALUE);
        return ImageData.of(width, height, image);
    }
}

