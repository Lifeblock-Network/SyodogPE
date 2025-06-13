/*
 * Copyright 2025 WaterdogTEAM
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

package dev.waterdog.waterdogpe.utils;

import io.netty.buffer.ByteBuf;
import org.cloudburstmc.math.vector.Vector2f;
import org.cloudburstmc.math.vector.Vector3f;
import org.cloudburstmc.math.vector.Vector3i;
import org.cloudburstmc.nbt.NbtType;
import org.cloudburstmc.protocol.bedrock.codec.BedrockCodecHelper;
import org.cloudburstmc.protocol.bedrock.data.EncodingSettings;
import org.cloudburstmc.protocol.bedrock.data.ExperimentData;
import org.cloudburstmc.protocol.bedrock.data.GameRuleData;
import org.cloudburstmc.protocol.bedrock.data.PlayerAbilityHolder;
import org.cloudburstmc.protocol.bedrock.data.command.CommandEnumData;
import org.cloudburstmc.protocol.bedrock.data.command.CommandOriginData;
import org.cloudburstmc.protocol.bedrock.data.definitions.BlockDefinition;
import org.cloudburstmc.protocol.bedrock.data.definitions.ItemDefinition;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityDataMap;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityLinkData;
import org.cloudburstmc.protocol.bedrock.data.entity.EntityProperties;
import org.cloudburstmc.protocol.bedrock.data.inventory.ContainerSlotType;
import org.cloudburstmc.protocol.bedrock.data.inventory.FullContainerName;
import org.cloudburstmc.protocol.bedrock.data.inventory.ItemData;
import org.cloudburstmc.protocol.bedrock.data.inventory.descriptor.ItemDescriptorWithCount;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.request.ItemStackRequest;
import org.cloudburstmc.protocol.bedrock.data.inventory.itemstack.response.ItemStackResponseContainer;
import org.cloudburstmc.protocol.bedrock.data.inventory.transaction.InventoryActionData;
import org.cloudburstmc.protocol.bedrock.data.skin.SerializedSkin;
import org.cloudburstmc.protocol.bedrock.data.structure.StructureSettings;
import org.cloudburstmc.protocol.bedrock.packet.InventoryTransactionPacket;
import org.cloudburstmc.protocol.common.DefinitionRegistry;
import org.cloudburstmc.protocol.common.NamedDefinition;
import org.cloudburstmc.protocol.common.util.TriConsumer;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.function.*;

public abstract class WrapperBedrockCodecHelper implements BedrockCodecHelper {
    final BedrockCodecHelper helper;

    public WrapperBedrockCodecHelper(BedrockCodecHelper helper) {
        this.helper = helper;
    }

    @Override
    public void setItemDefinitions(DefinitionRegistry<ItemDefinition> registry) {
        helper.setItemDefinitions(registry);
    }

    @Override
    public void setBlockDefinitions(DefinitionRegistry<BlockDefinition> registry) {
        helper.setBlockDefinitions(registry);
    }

    @Override
    public void setCameraPresetDefinitions(DefinitionRegistry<NamedDefinition> registry) {
        helper.setCameraPresetDefinitions(registry);
    }

    @Override
    public DefinitionRegistry<ItemDefinition> getItemDefinitions() {
        return helper.getItemDefinitions();
    }

    @Override
    public DefinitionRegistry<BlockDefinition> getBlockDefinitions() {
        return helper.getBlockDefinitions();
    }

    @Override
    public DefinitionRegistry<NamedDefinition> getCameraPresetDefinitions() {
        return helper.getCameraPresetDefinitions();
    }

    @Override
    public EncodingSettings getEncodingSettings() {
        return helper.getEncodingSettings();
    }

    @Override
    public void setEncodingSettings(EncodingSettings settings) {
        helper.setEncodingSettings(settings);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, BiFunction<ByteBuf, BedrockCodecHelper, T> function) {
        helper.readArray(buffer, array, function);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, BiFunction<ByteBuf, BedrockCodecHelper, T> function, int maxLength) {
        helper.readArray(buffer, array, function, maxLength);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, ToLongFunction<ByteBuf> lengthReader, BiFunction<ByteBuf, BedrockCodecHelper, T> function) {
        helper.readArray(buffer, array, lengthReader, function);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, ToLongFunction<ByteBuf> lengthReader, BiFunction<ByteBuf, BedrockCodecHelper, T> function, int maxLength) {
        helper.readArray(buffer, array, lengthReader, function, maxLength);
    }

    @Override
    public <T> void writeArray(ByteBuf buffer, Collection<T> array, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        helper.writeArray(buffer, array, consumer);
    }

    @Override
    public <T> void writeArray(ByteBuf buffer, Collection<T> array, ObjIntConsumer<ByteBuf> lengthWriter, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        helper.writeArray(buffer, array, lengthWriter, consumer);
    }

    @Override
    public <T> T[] readArray(ByteBuf buffer, T[] array, BiFunction<ByteBuf, BedrockCodecHelper, T> function) {
        return helper.readArray(buffer, array, function);
    }

    @Override
    public <T> T[] readArray(ByteBuf buffer, T[] array, BiFunction<ByteBuf, BedrockCodecHelper, T> function, int maxLength) {
        return helper.readArray(buffer, array, function, maxLength);
    }

    @Override
    public <T> void writeArray(ByteBuf buffer, T[] array, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        helper.writeArray(buffer, array, consumer);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, Function<ByteBuf, T> function) {
        helper.readArray(buffer, array, function);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, ToLongFunction<ByteBuf> lengthReader, Function<ByteBuf, T> function) {
        helper.readArray(buffer, array, lengthReader, function);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, ToLongFunction<ByteBuf> lengthReader, Function<ByteBuf, T> function, int maxLength) {
        helper.readArray(buffer, array, lengthReader, function, maxLength);
    }

    @Override
    public <T> void readArray(ByteBuf buffer, Collection<T> array, Function<ByteBuf, T> function, int maxLength) {
        helper.readArray(buffer, array, function, maxLength);
    }

    @Override
    public <T> void writeArray(ByteBuf buffer, Collection<T> array, BiConsumer<ByteBuf, T> consumer) {
        helper.writeArray(buffer, array, consumer);
    }

    @Override
    public <T> void writeArray(ByteBuf buffer, Collection<T> array, ObjIntConsumer<ByteBuf> lengthWriter, BiConsumer<ByteBuf, T> consumer) {
        helper.writeArray(buffer, array, lengthWriter, consumer);
    }

    @Override
    public <T> T[] readArray(ByteBuf buffer, T[] array, Function<ByteBuf, T> function) {
        return helper.readArray(buffer, array, function);
    }

    @Override
    public <T> T[] readArray(ByteBuf buffer, T[] array, Function<ByteBuf, T> function, int maxLength) {
        return helper.readArray(buffer, array, function, maxLength);
    }

    @Override
    public <T> void writeArray(ByteBuf buffer, T[] array, BiConsumer<ByteBuf, T> consumer) {
        helper.writeArray(buffer, array, consumer);
    }

    @Override
    public EntityLinkData readEntityLink(ByteBuf buffer) {
        return helper.readEntityLink(buffer);
    }

    @Override
    public void writeEntityLink(ByteBuf buffer, EntityLinkData link) {
        helper.writeEntityLink(buffer, link);
    }

    @Override
    public ItemData readNetItem(ByteBuf buffer) {
        return helper.readNetItem(buffer);
    }

    @Override
    public void writeNetItem(ByteBuf buffer, ItemData item) {
        helper.writeNetItem(buffer, item);
    }

    @Override
    public ItemData readItem(ByteBuf buffer) {
        return helper.readItem(buffer);
    }

    @Override
    public void writeItem(ByteBuf buffer, ItemData item) {
        helper.writeItem(buffer, item);
    }

    @Override
    public ItemData readItemInstance(ByteBuf buffer) {
        return helper.readItemInstance(buffer);
    }

    @Override
    public void writeItemInstance(ByteBuf buffer, ItemData item) {
        helper.writeItemInstance(buffer, item);
    }

    @Override
    public CommandOriginData readCommandOrigin(ByteBuf buffer) {
        return helper.readCommandOrigin(buffer);
    }

    @Override
    public void writeCommandOrigin(ByteBuf buffer, CommandOriginData commandOrigin) {
        helper.writeCommandOrigin(buffer, commandOrigin);
    }

    @Override
    public GameRuleData<?> readGameRule(ByteBuf buffer) {
        return helper.readGameRule(buffer);
    }

    @Override
    public void writeGameRule(ByteBuf buffer, GameRuleData<?> gameRule) {
        helper.writeGameRule(buffer, gameRule);
    }

    @Override
    public void readEntityData(ByteBuf buffer, EntityDataMap entityData) {
        helper.readEntityData(buffer, entityData);
    }

    @Override
    public void writeEntityData(ByteBuf buffer, EntityDataMap entityData) {
        helper.writeEntityData(buffer, entityData);
    }

    @Override
    public CommandEnumData readCommandEnum(ByteBuf buffer, boolean soft) {
        return helper.readCommandEnum(buffer, soft);
    }

    @Override
    public void writeCommandEnum(ByteBuf buffer, CommandEnumData commandEnum) {
        helper.writeCommandEnum(buffer, commandEnum);
    }

    @Override
    public StructureSettings readStructureSettings(ByteBuf buffer) {
        return helper.readStructureSettings(buffer);
    }

    @Override
    public void writeStructureSettings(ByteBuf buffer, StructureSettings settings) {
        helper.writeStructureSettings(buffer, settings);
    }

    @Override
    public SerializedSkin readSkin(ByteBuf buffer) {
        return helper.readSkin(buffer);
    }

    @Override
    public void writeSkin(ByteBuf buffer, SerializedSkin skin) {
        helper.writeSkin(buffer, skin);
    }

    @Override
    public byte[] readByteArray(ByteBuf buffer) {
        return helper.readByteArray(buffer);
    }

    @Override
    public byte[] readByteArray(ByteBuf buffer, int maxLength) {
        return helper.readByteArray(buffer, maxLength);
    }

    @Override
    public void writeByteArray(ByteBuf buffer, byte[] bytes) {
        helper.writeByteArray(buffer, bytes);
    }

    @Override
    public ByteBuf readByteBuf(ByteBuf buffer) {
        return helper.readByteBuf(buffer);
    }

    @Override
    public void writeByteBuf(ByteBuf buffer, ByteBuf toWrite) {
        helper.writeByteBuf(buffer, toWrite);
    }

    @Override
    public String readString(ByteBuf buffer) {
        return helper.readString(buffer);
    }

    @Override
    public String readStringMaxLen(ByteBuf buffer, int maxLength) {
        return helper.readStringMaxLen(buffer, maxLength);
    }

    @Override
    public void writeString(ByteBuf buffer, String string) {
        helper.writeString(buffer, string);
    }

    @Override
    public UUID readUuid(ByteBuf buffer) {
        return helper.readUuid(buffer);
    }

    @Override
    public void writeUuid(ByteBuf buffer, UUID uuid) {
        helper.writeUuid(buffer, uuid);
    }

    @Override
    public Vector3f readVector3f(ByteBuf buffer) {
        return helper.readVector3f(buffer);
    }

    @Override
    public void writeVector3f(ByteBuf buffer, Vector3f vector3f) {
        helper.writeVector3f(buffer, vector3f);
    }

    @Override
    public Vector2f readVector2f(ByteBuf buffer) {
        return helper.readVector2f(buffer);
    }

    @Override
    public void writeVector2f(ByteBuf buffer, Vector2f vector2f) {
        helper.writeVector2f(buffer, vector2f);
    }

    @Override
    public Vector3i readVector3i(ByteBuf buffer) {
        return helper.readVector3i(buffer);
    }

    @Override
    public void writeVector3i(ByteBuf buffer, Vector3i vector3i) {
        helper.writeVector3i(buffer, vector3i);
    }

    @Override
    public float readByteAngle(ByteBuf buffer) {
        return helper.readByteAngle(buffer);
    }

    @Override
    public void writeByteAngle(ByteBuf buffer, float angle) {
        helper.writeByteAngle(buffer, angle);
    }

    @Override
    public Vector3i readBlockPosition(ByteBuf buffer) {
        return helper.readBlockPosition(buffer);
    }

    @Override
    public void writeBlockPosition(ByteBuf buffer, Vector3i blockPosition) {
        helper.writeBlockPosition(buffer, blockPosition);
    }

    @Override
    public <T> T readTag(ByteBuf buffer, Class<T> expected) {
        return helper.readTag(buffer, expected);
    }

    @Override
    public <T> T readTag(ByteBuf buffer, Class<T> expected, long maxReadSize) {
        return helper.readTag(buffer, expected, maxReadSize);
    }

    @Override
    public void writeTag(ByteBuf buffer, Object tag) {
        helper.writeTag(buffer, tag);
    }

    @Override
    public <T> T readTagLE(ByteBuf buffer, Class<T> expected) {
        return helper.readTagLE(buffer, expected);
    }

    @Override
    public <T> T readTagLE(ByteBuf buffer, Class<T> expected, long maxReadSize) {
        return helper.readTagLE(buffer, expected, maxReadSize);
    }

    @Override
    public void writeTagLE(ByteBuf buffer, Object tag) {
        helper.writeTagLE(buffer, tag);
    }

    @Override
    public <T> T readTagValue(ByteBuf buffer, NbtType<T> type) {
        return helper.readTagValue(buffer, type);
    }

    @Override
    public <T> T readTagValue(ByteBuf buffer, NbtType<T> type, long maxReadSize) {
        return helper.readTagValue(buffer, type, maxReadSize);
    }

    @Override
    public void writeTagValue(ByteBuf buffer, Object tag) {
        helper.writeTagValue(buffer, tag);
    }

    @Override
    public void readItemUse(ByteBuf buffer, InventoryTransactionPacket packet) {
        helper.readItemUse(buffer, packet);
    }

    @Override
    public void writeItemUse(ByteBuf buffer, InventoryTransactionPacket packet) {
        helper.writeItemUse(buffer, packet);
    }

    @Override
    public boolean readInventoryActions(ByteBuf buffer, List<InventoryActionData> actions) {
        return helper.readInventoryActions(buffer, actions);
    }

    @Override
    public void writeInventoryActions(ByteBuf buffer, List<InventoryActionData> actions, boolean hasNetworkIds) {
        helper.writeInventoryActions(buffer, actions, hasNetworkIds);
    }

    @Override
    public void readExperiments(ByteBuf buffer, List<ExperimentData> experiments) {
        helper.readExperiments(buffer, experiments);
    }

    @Override
    public void writeExperiments(ByteBuf buffer, List<ExperimentData> experiments) {
        helper.writeExperiments(buffer, experiments);
    }

    @Override
    public ItemStackRequest readItemStackRequest(ByteBuf buffer) {
        return helper.readItemStackRequest(buffer);
    }

    @Override
    public void writeItemStackRequest(ByteBuf buffer, ItemStackRequest request) {
        helper.writeItemStackRequest(buffer, request);
    }

    @Override
    public <O> O readOptional(ByteBuf buffer, O emptyValue, Function<ByteBuf, O> function) {
        return helper.readOptional(buffer, emptyValue, function);
    }

    @Override
    public <T> T readOptional(ByteBuf buffer, T emptyValue, BiFunction<ByteBuf, BedrockCodecHelper, T> function) {
        return helper.readOptional(buffer, emptyValue, function);
    }

    @Override
    public <T> void writeOptional(ByteBuf buffer, Predicate<T> isPresent, T object, BiConsumer<ByteBuf, T> consumer) {
        helper.writeOptional(buffer, isPresent, object, consumer);
    }

    @Override
    public <T> void writeOptional(ByteBuf buffer, Predicate<T> isPresent, T object, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        helper.writeOptional(buffer, isPresent, object, consumer);
    }

    @Override
    public <T> void writeOptionalNull(ByteBuf buffer, T object, BiConsumer<ByteBuf, T> consumer) {
        helper.writeOptionalNull(buffer, object, consumer);
    }

    @Override
    public <T> void writeOptionalNull(ByteBuf buffer, T object, TriConsumer<ByteBuf, BedrockCodecHelper, T> consumer) {
        helper.writeOptionalNull(buffer, object, consumer);
    }

    @Override
    public void readEntityProperties(ByteBuf buffer, EntityProperties properties) {
        helper.readEntityProperties(buffer, properties);
    }

    @Override
    public void writeEntityProperties(ByteBuf buffer, EntityProperties properties) {
        helper.writeEntityProperties(buffer, properties);
    }

    @Override
    public ItemDescriptorWithCount readIngredient(ByteBuf buffer) {
        return helper.readIngredient(buffer);
    }

    @Override
    public void writeIngredient(ByteBuf buffer, ItemDescriptorWithCount ingredient) {
        helper.writeIngredient(buffer, ingredient);
    }

    @Override
    public void writeContainerSlotType(ByteBuf buffer, ContainerSlotType slotType) {
        helper.writeContainerSlotType(buffer, slotType);
    }

    @Override
    public ContainerSlotType readContainerSlotType(ByteBuf buffer) {
        return helper.readContainerSlotType(buffer);
    }

    @Override
    public void writePlayerAbilities(ByteBuf buffer, PlayerAbilityHolder abilityHolder) {
        helper.writePlayerAbilities(buffer, abilityHolder);
    }

    @Override
    public void readPlayerAbilities(ByteBuf buffer, PlayerAbilityHolder abilityHolder) {
        helper.readPlayerAbilities(buffer, abilityHolder);
    }

    @Override
    public void writeItemStackResponseContainer(ByteBuf buffer, ItemStackResponseContainer container) {
        helper.writeItemStackResponseContainer(buffer, container);
    }

    @Override
    public ItemStackResponseContainer readItemStackResponseContainer(ByteBuf buffer) {
        return helper.readItemStackResponseContainer(buffer);
    }

    @Override
    public void writeFullContainerName(ByteBuf buffer, FullContainerName containerName) {
        helper.writeFullContainerName(buffer, containerName);
    }

    @Override
    public FullContainerName readFullContainerName(ByteBuf buffer) {
        return helper.readFullContainerName(buffer);
    }

    @Override
    public <T extends Enum<?>> void writeLargeVarIntFlags(ByteBuf buffer, Set<T> flags, Class<T> clazz) {
        helper.writeLargeVarIntFlags(buffer, flags, clazz);
    }

    @Override
    public <T extends Enum<?>> void readLargeVarIntFlags(ByteBuf buffer, Set<T> flags, Class<T> clazz) {
        helper.readLargeVarIntFlags(buffer, flags, clazz);
    }
}
