/*
 * Copyright (c) 2021 squazar
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.quazar.offlinemanager.configuration;

import net.quazar.annotation.ConfigurationLoader;
import net.quazar.annotation.util.Comment;
import net.quazar.annotation.util.ConfigKey;
import net.quazar.annotation.util.Indents;
import net.quazar.offlinemanager.api.configuration.CacheConfiguration;
import net.quazar.offlinemanager.api.configuration.CommandConfiguration;
import net.quazar.offlinemanager.api.configuration.Configuration;
import net.quazar.offlinemanager.api.configuration.ContainerConfiguration;
import org.bukkit.configuration.file.FileConfiguration;

@SuppressWarnings("FieldMayBeFinal")
@Comment({"OfflineManager 3.0.7", "Created by quazar, LOVEC3327", "Contact https://vk.com/flawesv or https://t.me/squazar", "Configuration file"})
public class Settings implements Configuration {

    @Indents(1)
    @Comment("Set true if you want to enable only api. Commands will be disabled")
    @ConfigKey("only-api")
    private boolean onlyApi = false;
    @Indents(1)
    @Comment("When the player is removed from cache, it will write about it to the console")
    @ConfigKey("remove-from-cache-notification")
    private boolean removeFromCacheNotify = false;
    @Indents(1)
    @ConfigKey("placeholders")
    private boolean placeholders = true;
    @Indents(1)
    @Comment("Tab-complete by players")
    @ConfigKey("player-complete")
    private boolean playerComplete = true;
    @Comment("Tab-complete by plugin commands")
    @ConfigKey("command-complete")
    private boolean commandComplete = true;
    @Comment("Async player profiles storage loading")
    @ConfigKey("async-storage-init")
    private boolean asyncStorageInit = false;
    @Indents(1)
    @Comment({"Cache configuration", "This cache stores the data of recently used players"})
    @ConfigKey("cache")
    private final CacheConfiguration cacheConfiguration = new CacheConfiguration() {
        @ConfigKey("size")
        @Comment({"Maximum cache size", "Set 0 if you want unlimited cache"})
        private int size = 50;
/*
        @ConfigKey("life-time")
        @Comment({"Cache node life time in minutes", "Set to 0 if you don't want to delete the cache over time"})
        private int lifeTime = 0;
*/

        @Override
        public int getSize() {
            return size;
        }

/*        @Override
        public int getLifeTime() {
            return lifeTime;
        }*/
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

/*    @ConfigKey("addons")
    @Indents(1)
    private final AddonsSection addonsSection = new AddonsSection() {

        @ConfigKey("menus")
        private final MenusConfiguration menusConfiguration = (MenusConfiguration) OfflineManager.getApi().getAddon(AddonType.MENUS).getConfiguration();

        @Override
        public AddonConfiguration getAddon(AddonType type) {
            switch (type) {
                case MENUS:
                    return menusConfiguration;
                default:
                    return null;
            }
        }
    };*/

    public boolean isOnlyApi() {
        return onlyApi;
    }

    public boolean removeFromCacheNotify() {
        return removeFromCacheNotify;
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

    public boolean isAsyncStorageInit() {
        return asyncStorageInit;
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
