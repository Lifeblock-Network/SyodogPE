/*
 * Copyright 2022 WaterdogTEAM
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
import dev.waterdog.waterdogpe.network.protocol.updaters.CodecUpdater419;
import dev.waterdog.waterdogpe.network.protocol.updaters.ProtocolCodecUpdater;
import dev.waterdog.waterdogpe.utils.WrapperBedrockCodecHelper;
import io.netty.buffer.ByteBuf;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodec;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.codec.v388.BedrockCodecHelper_v388;
import org.cloudburstmc.protocol.bedrock.data.EncodingSettings;
import org.cloudburstmc.protocol.bedrock.data.GameRuleData;
import org.cloudburstmc.protocol.bedrock.data.skin.*;
import org.cloudburstmc.protocol.bedrock.packet.*;
import org.cloudburstmc.protocol.common.util.TextConverter;

import java.util.ArrayList;
import java.util.List;

public class ProtocolCodecs {

    private static final List<Class<? extends BedrockPacket>> HANDLED_PACKETS = new ArrayList<>();

    static {
        HANDLED_PACKETS.add(LoginPacket.class);
        HANDLED_PACKETS.add(PlayStatusPacket.class);
        HANDLED_PACKETS.add(ServerToClientHandshakePacket.class);
        HANDLED_PACKETS.add(ClientToServerHandshakePacket.class);
        HANDLED_PACKETS.add(DisconnectPacket.class);
        HANDLED_PACKETS.add(ResourcePacksInfoPacket.class);
        HANDLED_PACKETS.add(ResourcePackStackPacket.class);
        HANDLED_PACKETS.add(ResourcePackClientResponsePacket.class);
        HANDLED_PACKETS.add(ResourcePackDataInfoPacket.class);
        HANDLED_PACKETS.add(ResourcePackChunkDataPacket.class);
        HANDLED_PACKETS.add(ResourcePackChunkRequestPacket.class);
        HANDLED_PACKETS.add(TextPacket.class);
        HANDLED_PACKETS.add(StartGamePacket.class);
        HANDLED_PACKETS.add(AddPlayerPacket.class);
        HANDLED_PACKETS.add(AddEntityPacket.class);
        HANDLED_PACKETS.add(RemoveEntityPacket.class);
        HANDLED_PACKETS.add(AddItemEntityPacket.class);
        HANDLED_PACKETS.add(TakeItemEntityPacket.class);
        HANDLED_PACKETS.add(MoveEntityAbsolutePacket.class);
        HANDLED_PACKETS.add(MovePlayerPacket.class);
        HANDLED_PACKETS.add(UpdateBlockPacket.class);
        HANDLED_PACKETS.add(AddPaintingPacket.class);
        HANDLED_PACKETS.add(LevelSoundEvent1Packet.class);
        HANDLED_PACKETS.add(LevelEventPacket.class);
        HANDLED_PACKETS.add(EntityEventPacket.class);
        HANDLED_PACKETS.add(MobEffectPacket.class);
        HANDLED_PACKETS.add(UpdateAttributesPacket.class);
        HANDLED_PACKETS.add(MobEquipmentPacket.class);
        HANDLED_PACKETS.add(MobArmorEquipmentPacket.class);
        HANDLED_PACKETS.add(InteractPacket.class);
        HANDLED_PACKETS.add(EntityPickRequestPacket.class);
        HANDLED_PACKETS.add(PlayerActionPacket.class);
        HANDLED_PACKETS.add(SetEntityDataPacket.class);
        HANDLED_PACKETS.add(SetEntityMotionPacket.class);
        HANDLED_PACKETS.add(SetEntityLinkPacket.class);
        HANDLED_PACKETS.add(AnimatePacket.class);
        HANDLED_PACKETS.add(RespawnPacket.class);
        HANDLED_PACKETS.add(AdventureSettingsPacket.class);
        HANDLED_PACKETS.add(SetDifficultyPacket.class);
        HANDLED_PACKETS.add(ChangeDimensionPacket.class);
        HANDLED_PACKETS.add(SetPlayerGameTypePacket.class);
        HANDLED_PACKETS.add(PlayerListPacket.class);
        HANDLED_PACKETS.add(EventPacket.class);
        HANDLED_PACKETS.add(RequestChunkRadiusPacket.class);
        HANDLED_PACKETS.add(GameRulesChangedPacket.class);
        HANDLED_PACKETS.add(BossEventPacket.class);
        HANDLED_PACKETS.add(CommandRequestPacket.class);
        HANDLED_PACKETS.add(UpdateTradePacket.class);
        HANDLED_PACKETS.add(TransferPacket.class);
        HANDLED_PACKETS.add(StopSoundPacket.class);
        HANDLED_PACKETS.add(SetTitlePacket.class);
        HANDLED_PACKETS.add(NpcRequestPacket.class);
        HANDLED_PACKETS.add(RemoveObjectivePacket.class);
        HANDLED_PACKETS.add(SetDisplayObjectivePacket.class);
        HANDLED_PACKETS.add(SetScorePacket.class);
        HANDLED_PACKETS.add(MoveEntityDeltaPacket.class);
        HANDLED_PACKETS.add(SetScoreboardIdentityPacket.class);
        HANDLED_PACKETS.add(SetLocalPlayerAsInitializedPacket.class);
        HANDLED_PACKETS.add(NetworkStackLatencyPacket.class);
        HANDLED_PACKETS.add(SpawnParticleEffectPacket.class);
        HANDLED_PACKETS.add(AvailableEntityIdentifiersPacket.class);
        HANDLED_PACKETS.add(LevelSoundEvent2Packet.class);
        HANDLED_PACKETS.add(NetworkChunkPublisherUpdatePacket.class);
        HANDLED_PACKETS.add(LevelSoundEventPacket.class);
        HANDLED_PACKETS.add(ClientCacheStatusPacket.class);
        HANDLED_PACKETS.add(ClientCacheBlobStatusPacket.class);
        HANDLED_PACKETS.add(ClientCacheMissResponsePacket.class);
        HANDLED_PACKETS.add(EmotePacket.class);
        HANDLED_PACKETS.add(EmoteListPacket.class);
        HANDLED_PACKETS.add(DebugInfoPacket.class);
        HANDLED_PACKETS.add(PacketViolationWarningPacket.class);
        HANDLED_PACKETS.add(AnimateEntityPacket.class);
        HANDLED_PACKETS.add(ItemComponentPacket.class);
        HANDLED_PACKETS.add(NpcDialoguePacket.class);
        HANDLED_PACKETS.add(BiomeDefinitionListPacket.class);
        HANDLED_PACKETS.add(ChangeMobPropertyPacket.class);
        HANDLED_PACKETS.add(UpdateAbilitiesPacket.class);
        HANDLED_PACKETS.add(NetworkSettingsPacket.class);
        HANDLED_PACKETS.add(RequestNetworkSettingsPacket.class);
        HANDLED_PACKETS.add(UpdatePlayerGameTypePacket.class);
        HANDLED_PACKETS.add(SubClientLoginPacket.class);
        HANDLED_PACKETS.add(LevelChunkPacket.class);
        HANDLED_PACKETS.add(ClientCheatAbilityPacket.class);
        HANDLED_PACKETS.add(ToastRequestPacket.class);
        HANDLED_PACKETS.add(MovementEffectPacket.class);
        HANDLED_PACKETS.add(PlaySoundPacket.class);
        HANDLED_PACKETS.add(PlayerAuthInputPacket.class);
        HANDLED_PACKETS.add(ModalFormRequestPacket.class);
        HANDLED_PACKETS.add(ModalFormResponsePacket.class);
        HANDLED_PACKETS.add(BlockEntityDataPacket.class);
        HANDLED_PACKETS.add(InventoryTransactionPacket.class);
        HANDLED_PACKETS.add(ClientboundCloseFormPacket.class);
    }

    private static final List<ProtocolCodecUpdater> UPDATERS = new ObjectArrayList<>();
    private static final ProtocolCodecUpdater DEFAULT_UPDATER = (builder, codec) -> builder.retainPackets(HANDLED_PACKETS.toArray(new Class[]{}));

    static {
        UPDATERS.add(new CodecUpdater419());
    }

    public static void addUpdater(ProtocolCodecUpdater updater) {
        UPDATERS.add(updater);
    }

    public static List<ProtocolCodecUpdater> getUpdaters() {
        return UPDATERS;
    }

    public static BedrockCodec buildCodec(BedrockCodec baseCodec) {
        BedrockCodec.Builder builder = baseCodec.toBuilder();
        DEFAULT_UPDATER.updateCodec(builder, baseCodec);

        for (ProtocolCodecUpdater updater : UPDATERS) {
            if (baseCodec.getProtocolVersion() >= updater.getRequiredVersion()) {
                updater.updateCodec(builder, baseCodec);
            }
        }
        builder.helper(() -> {
            BedrockCodecHelper helper = baseCodec.createHelper();
            EncodingSettings encodingSettings = EncodingSettings.builder()
                    .maxByteArraySize(ProxyServer.getInstance().getConfiguration().getNetworkSettings().maxByteArraySize())
                    .maxListSize(ProxyServer.getInstance().getConfiguration().getNetworkSettings().maxListSize())
                    .maxNetworkNBTSize(ProxyServer.getInstance().getConfiguration().getNetworkSettings().maxNetworkNBTSize())
                    .maxItemNBTSize(ProxyServer.getInstance().getConfiguration().getNetworkSettings().maxItemNBTSize())
                    .maxStringLength(ProxyServer.getInstance().getConfiguration().getNetworkSettings().maxStringLength())
                    .build();
            helper.setEncodingSettings(encodingSettings);
            return new WrapperBedrockCodecHelper(helper) {
                static final int CUSTOM_MODEL_SIZE = ProxyServer.getInstance().getConfiguration().getNetworkSettings().maxSkinLength();
                static final AnimatedTextureType[] TEXTURE_TYPES = AnimatedTextureType.values();
                static final AnimationExpressionType[] EXPRESSION_TYPES = AnimationExpressionType.values();


                final BedrockCodecHelper_v388 helperV388;

                {
                    if (!(helper instanceof BedrockCodecHelper_v388 h)) {
                        throw new IllegalStateException("Expected BedrockCodecHelper_v388, got " + helper.getClass().getName());
                    }
                    this.helperV388 = h;
                }

                public AnimationData readAnimationData(ByteBuf buffer) {
                    ImageData image = helperV388.readImage(buffer, CUSTOM_MODEL_SIZE);
                    AnimatedTextureType textureType = TEXTURE_TYPES[buffer.readIntLE()];
                    float frames = buffer.readFloatLE();
                    AnimationExpressionType expressionType = EXPRESSION_TYPES[buffer.readIntLE()];
                    return new AnimationData(image, textureType, frames, expressionType);
                }

                @Override
                public TextConverter getTextConverter() {
                    return null;
                }

                @Override
                public void setTextConverter(TextConverter textConverter) {

                }

                @Override
                public void writeGameRuleInStartGame(ByteBuf buffer, GameRuleData<?> gameRule) {

                }

                @Override
                public GameRuleData<?> readGameRuleInStartGame(ByteBuf buffer) {
                    return null;
                }

                @Override
                public SerializedSkin readSkin(ByteBuf buffer) {
                    String skinId = this.readString(buffer);
                    String playFabId = this.readString(buffer);
                    String skinResourcePatch = this.readString(buffer);
                    ImageData skinData = helperV388.readImage(buffer, CUSTOM_MODEL_SIZE);

                    List<AnimationData> animations = new ObjectArrayList<>();
                    this.readArray(buffer, animations, ByteBuf::readIntLE, (b, h) -> this.readAnimationData(b));

                    ImageData capeData = helperV388.readImage(buffer, CUSTOM_MODEL_SIZE);
                    String geometryData = this.readStringMaxLen(buffer, 1024 * 256); // Allow larger geometry data
                    String geometryDataEngineVersion = this.readString(buffer);
                    String animationData = this.readString(buffer);
                    String capeId = this.readString(buffer);
                    String fullSkinId = this.readString(buffer);
                    String armSize = this.readString(buffer);
                    String skinColor = this.readString(buffer);

                    List<PersonaPieceData> personaPieces = new ObjectArrayList<>();
                    this.readArray(buffer, personaPieces, ByteBuf::readIntLE, (buf, h) -> {
                        String pieceId = this.readString(buf);
                        String pieceType = this.readString(buf);
                        String packId = this.readString(buf);
                        boolean isDefault = buf.readBoolean();
                        String productId = this.readString(buf);
                        return new PersonaPieceData(pieceId, pieceType, packId, isDefault, productId);
                    });

                    List<PersonaPieceTintData> tintColors = new ObjectArrayList<>();
                    this.readArray(buffer, tintColors, ByteBuf::readIntLE, (buf, h) -> {
                        String pieceType = this.readString(buf);
                        List<String> colors = new ObjectArrayList<>();
                        int colorsLength = buf.readIntLE();
                        for (int i2 = 0; i2 < colorsLength; i2++) {
                            colors.add(this.readString(buf));
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
            };
        });
        return builder.build();
    }
}
