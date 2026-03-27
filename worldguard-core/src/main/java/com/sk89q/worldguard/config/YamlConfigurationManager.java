/*
 * WorldGuard, a suite of tools for Minecraft
 * Copyright (C) sk89q <http://www.sk89q.com>
 * Copyright (C) WorldGuard team and contributors
 *
 * This program is free software: you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as published by the
 * Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License
 * for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */

package com.sk89q.worldguard.config;

import com.google.common.collect.ImmutableMap;
import com.sk89q.util.yaml.YAMLFormat;
import com.sk89q.util.yaml.YAMLProcessor;
import com.sk89q.worldguard.protection.managers.storage.DriverType;
import com.sk89q.worldguard.protection.managers.storage.RegionDriver;
import com.sk89q.worldguard.protection.managers.storage.file.DirectoryYamlDriver;
import com.sk89q.worldguard.protection.managers.storage.sql.SQLDriver;
import com.sk89q.worldedit.util.report.Unreported;
import com.sk89q.worldguard.util.sql.DataSourceConfig;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class YamlConfigurationManager extends ConfigurationManager {

    @Unreported private YAMLProcessor config;

    public abstract void copyDefaults();

    @Override
    public void load() {
        copyDefaults();

        config = new YAMLProcessor(new File(getDataFolder(), "config.yml"), true, YAMLFormat.EXTENDED);
        try {
            config.load();
        } catch (IOException e) {
            log.severe("Error reading configuration for global config: ");
            e.printStackTrace();
        }


        config.removeProperty("suppress-tick-sync-warnings");
        denyBlockBreaks = config.getString("messages.deny-message.block-break", "<red>Вы не можете ломать блоки");
        useDynamite = config.getString("messages.deny-message.use-dynamite", "<red>Вы не можете использовать динамит");
        denyPlaceFire = config.getString("messages.deny-message.place-fire", "<red>Вы не можете ставить огонь здесь");
        denyPlaceFrostedIce = config.getString("messages.deny-message.place-frosted-ice", "<red>Вы не можете использовать Frost Walker здесь");
        denyPlaceBlock = config.getString("messages.deny-message.place-block", "<red>Вы не можете ставить этот блок здесь");
        denyUseBlock = config.getString("messages.deny-message.use-block", "Вы не можете использовать этот блок");
        denyOpenInventory = config.getString("messages.deny-message.open-inventory", "Вы не можете открыть этот инвентарь");
        denyTakeItem = config.getString("messages.deny-message.take-item", "Вы не можете забрать этот предмет");
        denyUseAnvil = config.getString("messages.deny-message.use-anvil", "Вы не можете использовать этот наковальню");
        denySleepInBed = config.getString("messages.deny-message.sleep-in-bed", "Вы не можете лечь в эту кровать");
        denyUseRespawnAnchor = config.getString("messages.deny-message.use-respawn-anchor", "Вы не можете использовать этот якорь возрождения");
        denyUseExplosives = config.getString("messages.deny-message.use-explosives", "Вы не можете использовать взрывчатку");
        denyUseItem = config.getString("messages.deny-message.use-item", "Вы не можете использовать этот предмет");
        denyEat = config.getString("messages.deny-message.deny-eat", "<#FF3131>Вы не можете здесь есть этот предмет");
        denyFly = config.getString("messages.deny-message.deny-fly", "<#FF3131>Вы не можете здесь летать");
        denyPlaceVehicle = config.getString("messages.deny-message.place-vehicle", "Вы не можете поставить транспортное средство");
        denyDropItem = config.getString("messages.deny-message.drop-item", "Вы не можете выбросить этот предмет");
        denyDropXP = config.getString("messages.deny-message.drop-xp", "Вы не можете выбросить опыт");
        denyUseLingeringPotion = config.getString("messages.deny-message.use-lingering-potion", "Вы не можете использовать взрывные зелья");
        denyPlaceObject = config.getString("messages.deny-message.place-object", "Вы не можете поставить этот объект");
        denyHitEntity = config.getString("messages.deny-message.hit-entity", "Вы не можете повредить это существо");
        denyChangeItemFrame = config.getString("messages.deny-message.change-item-frame", "Вы не можете изменять этот предмет в рамке");
        denyOpenChest = config.getString("messages.deny-message.open-chest", "Вы не можете открыть этот сундук");
        denyRideEntity = config.getString("messages.deny-message.ride-entity", "Вы не можете кататься на этом существе");
        denyPvP = config.getString("messages.deny-message.pvp", "PvP запрещено в этой зоне");
        denyDamageAnimal = config.getString("messages.deny-message.damage-animal", "Вы не можете повредить это животное");
        denyInteractEntity = config.getString("messages.deny-message.interact-entity", "Вы не можете взаимодействовать с этим существом");
        denyRideVehicle = config.getString("messages.deny-message.ride-vehicle", "Вы не можете садиться в этот транспорт");
        denyChangeEntity = config.getString("messages.deny-message.change-entity", "Вы не можете изменить это существо");
        denyBreakVehicle = config.getString("messages.deny-message.break-vehicle", "Вы не можете сломать этот транспорт");
        denyPickUpItem = config.getString("messages.deny-message.pick-up-item", "Вы не можете поднять этот предмет");
        denyDamagePlayer = config.getString("messages.deny-message.damage-player", "Вы не можете повредить игрока");
        denyDamageHostileMob = config.getString("messages.deny-message.damage-hostile-mob", "Вы не можете повредить враждебное существо");
        denyDamageAmbientMob = config.getString("messages.deny-message.damage-ambient-mob", "Вы не можете повредить атмосферное существо");
        denyChangePainting = config.getString("messages.deny-message.change-painting", "Вы не можете изменить это изображение");
        denyInteractWithNPC = config.getString("messages.deny-message.interact-with-npc", "Вы не можете взаимодействовать с этим NPC");
        denyRideHostileMob = config.getString("messages.deny-message.ride-hostile-mob", "Вы не можете кататься на враждебном существе");
        denyRideAmbientMob = config.getString("messages.deny-message.ride-ambient-mob", "Вы не можете кататься на атмосферном существе");
        denyDamageNonHostile = config.getString("messages.deny-message.damage-non-hostile", "Вы не можете повредить неагрессивное существо");
        denyInteractWithItemFrame = config.getString("messages.deny-message.interact-with-item-frame", "Вы не можете взаимодействовать с этой рамкой предмета");
        denyUsePotion = config.getString("messages.deny-message.use-potion", "Вы не можете использовать это зелье");
        denyInteractWithVehicle = config.getString("messages.deny-message.interact-with-vehicle", "Вы не можете взаимодействовать с этим транспортом");
        denyPlaceTnt = config.getString("messages.deny-message.place-tnt", "Вы не можете поставить TNT");
        denyPlaceBed = config.getString("messages.deny-message.place-bed", "Вы не можете поставить эту кровать");
        denyUseBlockWithChestAccess = config.getString("messages.deny-message.use-block-with-chest-access", "Вы не можете использовать этот блок с доступом к сундуку");
        denyPlaceItem = config.getString("messages.deny-message.place-item", "Вы не можете поставить этот предмет");
        denyInteractWithAnvil = config.getString("messages.deny-message.interact-with-anvil", "Вы не можете взаимодействовать с этим наковальней");
        denyInteractWithChest = config.getString("messages.deny-message.interact-with-chest", "Вы не можете взаимодействовать с этим сундуком");
        denyCreatePortal = config.getString("messages.deny-message.create-portal", "<red>Вы не можете создавать портал");
        denyUseCommand = config.getString("messages.deny-message.use-command", "<red>Вы не можете использовать <command> эту команду");
        denyChatMessage = config.getString("messages.deny-message.chat-message", "<red>Вы не можете писать в чат");

        addMemberMessage = config.getString("messages.region-message.add-member", "<green>Члены успешно добавлены в регион '<region>' на '<world>'.");
        addOwnerMessage = config.getString("messages.region-message.add-owner", "<blue>Владельцы успешно добавлены в регион '<region>' на '<world>'.");
        removeMemberMessage = config.getString("messages.region-message.remove-member", "<red>Члены успешно удалены из региона '<region>' на '<world>'.");
        removeOwnerMessage = config.getString("messages.region-message.remove-owner", "<yellow>Владельцы успешно удалены из региона '<region>' на '<world>'.");
        defineRegionMessage = config.getString("messages.region-message.define-region", "<green>Регион '<region>' успешно создан в мире '<world>'.");
        redefineRegionMessage = config.getString("messages.region-message.redefine-region", "<blue>Регион '<region>' успешно обновлён с новым выбором.");
        claimRegionMessage = config.getString("messages.region-message.claim-region", "<yellow>Регион '<region>' успешно захвачен.");
        selectRegionMessage = config.getString("messages.region-message.select-region", "<purple>Регион '<region>' выбран как WorldEdit выбор.");
        removeRegionSuccessMessage = config.getString("messages.region-message.remove-region", "<green>Регион '<region>' успешно удален.");

        useGodPermission = config.getBoolean("auto-invincible", config.getBoolean("auto-invincible-permission", false));
        useGodGroup = config.getBoolean("auto-invincible-group", false);
        useAmphibiousGroup = config.getBoolean("auto-no-drowning-group", false);
        config.removeProperty("auto-invincible-permission");
        usePlayerMove = config.getBoolean("use-player-move-event", true);
        usePlayerTeleports = config.getBoolean("use-player-teleports", true);
        particleEffects = config.getBoolean("use-particle-effects", true);
        disablePermissionCache = config.getBoolean("disable-permission-cache", false);

        deopOnJoin = config.getBoolean("security.deop-everyone-on-join", false);
        blockInGameOp = config.getBoolean("security.block-in-game-op-command", false);

        hostKeys = new HashMap<>();
        Object hostKeysRaw = config.getProperty("host-keys");
        if (!(hostKeysRaw instanceof Map)) {
            config.setProperty("host-keys", new HashMap<String, String>());
        } else {
            for (Map.Entry<Object, Object> entry : ((Map<Object, Object>) hostKeysRaw).entrySet()) {
                String key = String.valueOf(entry.getKey());
                String value = String.valueOf(entry.getValue());
                hostKeys.put(key.toLowerCase(), value);
            }
        }
        hostKeysAllowFMLClients = config.getBoolean("security.host-keys-allow-forge-clients", false);

        // ====================================================================
        // Region store drivers
        // ====================================================================

        boolean useSqlDatabase = config.getBoolean("regions.sql.use", false);
        String sqlDsn = config.getString("regions.sql.dsn", "jdbc:mysql://localhost/worldguard");
        String sqlUsername = config.getString("regions.sql.username", "worldguard");
        String sqlPassword = config.getString("regions.sql.password", "worldguard");
        String sqlTablePrefix = config.getString("regions.sql.table-prefix", "");
        if (!useSqlDatabase) {
            config.removeProperty("regions.sql");
        } else {
            log.warning("SQL support for WorldGuard region storage is deprecated for removal in a future version. Please migrate to YAML storage.");
            log.warning("For details, see https://worldguard.enginehub.org/en/latest/regions/storage/");
        }

        DataSourceConfig dataSourceConfig = new DataSourceConfig(sqlDsn, sqlUsername, sqlPassword, sqlTablePrefix);
        SQLDriver sqlDriver = new SQLDriver(dataSourceConfig);
        DirectoryYamlDriver yamlDriver = new DirectoryYamlDriver(getWorldsDataFolder(), "regions.yml");

        this.regionStoreDriverMap = ImmutableMap.<DriverType, RegionDriver>builder()
                .put(DriverType.MYSQL, sqlDriver)
                .put(DriverType.YAML, yamlDriver)
                .build();
        this.selectedRegionStoreDriver = useSqlDatabase ? sqlDriver : yamlDriver;

        postLoad();

        config.setHeader(CONFIG_HEADER);
    }

    public void postLoad() {}

    public YAMLProcessor getConfig() {
        return config;
    }

    @Override
    public void disableUuidMigration() {
        config.setProperty("regions.uuid-migration.perform-on-next-start", false);
        if (!config.save()) {
            log.severe("Error saving configuration!");
        }
    }
}
