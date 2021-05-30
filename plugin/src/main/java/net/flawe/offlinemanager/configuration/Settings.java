package net.flawe.offlinemanager.configuration;

import net.flawe.annotation.ConfigurationLoader;
import net.flawe.annotation.util.Comment;
import net.flawe.annotation.util.ConfigKey;
import net.flawe.annotation.util.Indents;
import net.flawe.offlinemanager.api.configuration.CacheConfiguration;
import net.flawe.offlinemanager.api.configuration.CommandConfiguration;
import net.flawe.offlinemanager.api.configuration.Configuration;
import net.flawe.offlinemanager.api.configuration.ContainerConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

@Comment({"OfflineManager 3.0.0-SNAPSHOT", "Created by flawe, LOVEC3327", "Contact https://vk.com/flawesv or https://t.me/flawe_sv", "Configuration file"})
public class Settings implements Configuration {

    @Indents(1)
    @Comment("Set true if you want to enable only api. Commands will be disabled")
    @ConfigKey("only-api")
    private boolean onlyApi = false;
    @Indents(1)
    @ConfigKey("placeholders")
    private boolean placeholders = true;
    @Indents(1)
    @ConfigKey("player-complete")
    private boolean playerComplete = true;
    @ConfigKey("command-complete")
    private boolean commandComplete = true;
    @Indents(1)
    @Comment({"Cache configuration", "This cache stores the data of recently used players"})
    @ConfigKey("cache")
    private final CacheConfiguration cacheConfiguration = new CacheConfiguration() {
        @ConfigKey("size")
        @Comment({"Maximum cache size", "Set 0 if you want unlimited cache"})
        private int size = 50;
        @ConfigKey("life-time")
        @Comment({"Cache node life time in minutes", "Set to 0 if you don't want to delete the cache over time"})
        private int lifeTime = 0;

        @Override
        public int getSize() {
            return size;
        }

        @Override
        public int getLifeTime() {
            return lifeTime;
        }
    };
    @Indents(1)
    @ConfigKey("offline-inventory")
    private final ContainerConfiguration inventoryConfiguration = new ContainerConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;
        @ConfigKey("interact")
        private boolean interact = true;
        @ConfigKey("name")
        private String name = "OfflineInventory";

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public boolean interact() {
            return interact;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "InventoryConfiguration{" +
                    "enabled=" + enabled +
                    ", interact=" + interact +
                    ", name='" + name + '\'' +
                    '}';
        }
    };
    @ConfigKey("offline-enderchest")
    private final ContainerConfiguration enderChestConfiguration = new ContainerConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;
        @ConfigKey("interact")
        private boolean interact = true;
        @ConfigKey("name")
        private String name = "OfflineEnderchest";

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public boolean interact() {
            return interact;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "EnderChestConfiguration{" +
                    "enabled=" + enabled +
                    ", interact=" + interact +
                    ", name='" + name + '\'' +
                    '}';
        }
    };
    @ConfigKey("offline-armor")
    private final ContainerConfiguration armorInventoryConfiguration = new ContainerConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;
        @ConfigKey("interact")
        private boolean interact = true;
        @ConfigKey("name")
        private String name = "OfflineArmor";

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public boolean interact() {
            return interact;
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return "ArmorInventoryConfiguration{" +
                    "enabled=" + enabled +
                    ", interact=" + interact +
                    ", name='" + name + '\'' +
                    '}';
        }
    };
    @Indents(1)
    @Comment("Commands configuration")
    @ConfigKey("change-gamemode")
    private final CommandConfiguration changeGamemodeConfiguration = new CommandConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "GamemodeConfiguration{" +
                    "enabled=" + enabled +
                    '}';
        }
    };
    @ConfigKey("teleportation")
    private final CommandConfiguration teleportConfiguration = new CommandConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "TeleportConfiguration{" +
                    "enabled=" + enabled +
                    '}';
        }
    };
    @ConfigKey("edit-location")
    private final CommandConfiguration editLocationConfiguration = new CommandConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "EditLocationConfiguration{" +
                    "enabled=" + enabled +
                    '}';
        }
    };
    @ConfigKey("kill-player")
    private final CommandConfiguration.CommandKillConfiguration commandKillConfiguration = new CommandConfiguration.CommandKillConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;
        @ConfigKey("clear-items")
        private boolean clear = true;
        @ConfigKey("drop-items")
        private boolean drop = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public boolean clearItems() {
            return clear;
        }

        @Override
        public boolean dropItems() {
            return drop;
        }

        @Override
        public String toString() {
            return "KillPlayerConfiguration{" +
                    "enabled=" + enabled +
                    ", clear=" + clear +
                    ", drop=" + drop +
                    '}';
        }
    };
    @ConfigKey("clear-inventory")
    private final CommandConfiguration clearConfiguration = new CommandConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "ClearInventoryConfiguration{" +
                    "enabled=" + enabled +
                    '}';
        }
    };
    @ConfigKey("heal-player")
    private final CommandConfiguration healConfiguration = new CommandConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "HealConfiguration{" +
                    "enabled=" + enabled +
                    '}';
        }
    };
    @ConfigKey("feed-player")
    private final CommandConfiguration feedConfiguration = new CommandConfiguration() {
        @ConfigKey("enabled")
        private boolean enabled = true;

        @Override
        public boolean enabled() {
            return enabled;
        }

        @Override
        public String toString() {
            return "FeedConfiguration{" +
                    "enabled=" + enabled +
                    '}';
        }
    };

    public boolean isOnlyApi() {
        return onlyApi;
    }

    public boolean placeholders() {
        return placeholders;
    }

    public boolean isPlayerComplete() {
        return playerComplete;
    }

    public boolean isCommandComplete() {
        return commandComplete;
    }

    public CacheConfiguration getCacheConfiguration() {
        return cacheConfiguration;
    }

    public ContainerConfiguration getInventoryConfiguration() {
        return inventoryConfiguration;
    }

    public ContainerConfiguration getEnderChestConfiguration() {
        return enderChestConfiguration;
    }

    public ContainerConfiguration getArmorInventoryConfiguration() {
        return armorInventoryConfiguration;
    }

    public CommandConfiguration getChangeGamemodeConfiguration() {
        return changeGamemodeConfiguration;
    }

    public CommandConfiguration getTeleportConfiguration() {
        return teleportConfiguration;
    }

    public CommandConfiguration getEditLocationConfiguration() {
        return editLocationConfiguration;
    }

    public CommandConfiguration.CommandKillConfiguration getCommandKillConfiguration() {
        return commandKillConfiguration;
    }

    public CommandConfiguration getClearConfiguration() {
        return clearConfiguration;
    }

    public CommandConfiguration getHealConfiguration() {
        return healConfiguration;
    }

    public CommandConfiguration getFeedConfiguration() {
        return feedConfiguration;
    }

    public void reload(FileConfiguration configuration) {
        ConfigurationLoader.loadConfiguration(this, configuration);
    }

    @Override
    public String toString() {
        return "Settings{" +
                "onlyApi=" + onlyApi +
                ", placeholders=" + placeholders +
                ", playerComplete=" + playerComplete +
                ", commandComplete=" + commandComplete +
                ", inventoryConfiguration=" + inventoryConfiguration +
                ", enderChestConfiguration=" + enderChestConfiguration +
                ", armorInventoryConfiguration=" + armorInventoryConfiguration +
                ", changeGamemodeConfiguration=" + changeGamemodeConfiguration +
                ", teleportConfiguration=" + teleportConfiguration +
                ", editLocationConfiguration=" + editLocationConfiguration +
                ", commandKillConfiguration=" + commandKillConfiguration +
                ", clearConfiguration=" + clearConfiguration +
                ", healConfiguration=" + healConfiguration +
                ", feedConfiguration=" + feedConfiguration +
                '}';
    }
}
