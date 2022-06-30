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

package net.quazar.offlinemanager.api.data.entity;

import net.quazar.offlinemanager.api.nbt.ITagAdapter;
import net.quazar.offlinemanager.api.nbt.TagValue;
import net.quazar.offlinemanager.api.nbt.type.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

/**
 * An abstract class that implements IPlayerData to reduce repetitive code
 * @since 3.0.0
 * @author quazar
 */
public abstract class AbstractPlayerData implements IPlayerData {
    private final UUID uuid;
    private final PlayerProfile playerProfile;
    protected ITagAdapter adapter;

    protected AbstractPlayerData(PlayerProfile playerProfile, ITagAdapter adapter) {
        this.playerProfile = playerProfile;
        this.uuid = playerProfile.getUuid();
        this.adapter = adapter;
    }

    @Deprecated
    protected AbstractPlayerData(UUID uuid, ITagAdapter adapter) {
        this(PlayerProfile.of(uuid, Bukkit.getOfflinePlayer(uuid).getName()), adapter);
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return ((TagString) adapter.getTagCompound("bukkit").getTagValue("lastKnownName")).getValue();
    }

    @Override
    public PlayerProfile getPlayerProfile() {
        return playerProfile;
    }

    @Override
    public float getFallDistance() {
        return ((TagFloat) adapter.getTagValue("FallDistance")).getValue();
    }

    @Override
    public void setFallDistance(float distance) {
        adapter.setValue("FallDistance", new TagFloat(distance));
    }

    @Override
    public short getFireTicks() {
        return ((TagShort) adapter.getTagValue("Fire")).getValue();
    }

    @Override
    public void setFireTicks(short ticks) {
        adapter.setValue("Fire", new TagShort(ticks));
    }

    @Override
    public float getExhaustion() {
        return ((TagFloat) adapter.getTagValue("foodExhaustionLevel")).getValue();
    }

    @Override
    public void setExhaustion(float exhaustion) {
        adapter.setValue("foodExhaustionLevel", new TagFloat(exhaustion));
    }

    @Override
    public int getFoodLevel() {
        return ((TagInteger) adapter.getTagValue("foodLevel")).getValue();
    }

    @Override
    public void setFoodLevel(int level) {
        adapter.setValue("foodLevel", new TagInteger(level));
    }

    @Override
    public float getSaturation() {
        return ((TagFloat) adapter.getTagValue("foodSaturationLevel")).getValue();
    }

    @Override
    public void setSaturation(float saturation) {
        adapter.setValue("foodSaturationLevel", new TagFloat(saturation));
    }

    @Override
    public float getHealth() {
        return ((TagFloat) adapter.getTagValue("Health")).getValue();
    }

    @Override
    public void setHealth(float health) {
        adapter.setValue("Health", new TagFloat(health));
    }

    @Override
    public boolean isInvulnerable() {
        return ((TagByte) adapter.getTagValue("Invulnerable")).getValue() != 0;
    }

    private static final TagByte byteFalse = new TagByte((byte) 0);
    private static final TagByte byteTrue = new TagByte((byte) 1);

    @Override
    public void setInvulnerable(boolean b) {
        adapter.setValue("Invulnerable", b ? byteTrue : byteFalse);
    }

    @Override
    public boolean isOnGround() {
        return ((TagByte) adapter.getTagValue("OnGround")).getValue() != 0;
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.getByValue(((TagInteger) adapter.getTagValue("playerGameType")).getValue());
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        adapter.setValue("playerGameType", new TagInteger(gameMode.getValue()));
    }

    @Override
    public int getPortalCooldown() {
        return ((TagInteger) adapter.getTagValue("PortalCooldown")).getValue();
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        adapter.setValue("PortalCooldown", new TagInteger(cooldown));
    }

    @Override
    public int getSelectedSlot() {
        return ((TagInteger) adapter.getTagValue("SelectedItemSlot")).getValue();
    }

    @Override
    public void setSelectedSlot(int slot) {
        adapter.setValue("SelectedItemSlot", new TagInteger(slot));
    }

    @Override
    public World getWorld() {
        long most = ((TagLong) adapter.getTagValue("WorldUUIDMost")).getValue();
        long least = ((TagLong) adapter.getTagValue("WorldUUIDLeast")).getValue();
        return Bukkit.getWorld(new UUID(most, least));
    }

    @Override
    public Location getLocation() {
        TagList pos = (TagList) adapter.getTagValue("Pos");
        TagList rotation = (TagList) adapter.getTagValue("Rotation");
        double x = 0, y = 0, z = 0;
        if (pos != null) {
            x = ((TagDouble) pos.getTagValue(0)).getValue();
            y = ((TagDouble) pos.getTagValue(1)).getValue();
            z = ((TagDouble) pos.getTagValue(2)).getValue();
        }
        float yaw = 0, pitch = 0;
        if (rotation != null) {
            yaw = ((TagFloat) rotation.getTagValue(0)).getValue();
            pitch = ((TagFloat) rotation.getTagValue(1)).getValue();
        }
        return new Location(getWorld(), x, y, z, yaw, pitch);
    }

    @Override
    public void setLocation(Location location) {
        if (location.getWorld() == null) return;
        TagList pos = new TagList();
        pos.addValue(new TagDouble(location.getX()));
        pos.addValue(new TagDouble(location.getY()));
        pos.addValue(new TagDouble(location.getZ()));

        TagList rotation = new TagList();
        rotation.addValue(new TagFloat(location.getYaw()));
        rotation.addValue(new TagFloat(location.getPitch()));
        TagLong worldUuidMost = new TagLong(location.getWorld().getUID().getMostSignificantBits());
        TagLong worldUuidLeast = new TagLong(location.getWorld().getUID().getLeastSignificantBits());
        adapter.setValue("Pos", pos);
        adapter.setValue("Rotation", rotation);
        adapter.setValue("WorldUUIDLeast", worldUuidLeast);
        adapter.setValue("WorldUUIDMost", worldUuidMost);
    }

    @Override
    public void setLocation(Entity entity) {
        setLocation(entity.getLocation());
    }

    @Override
    public int getExpLevel() {
        return ((TagInteger) adapter.getTagValue("XpLevel")).getValue();
    }

    @Override
    public void setExpLevel(int level) {
        adapter.setValue("XpLevel", new TagInteger(level));
    }

    @Override
    public float getExp() {
        return ((TagFloat) adapter.getTagValue("XpP")).getValue();
    }

    @Override
    public void setExp(float exp) {
        adapter.setValue("XpP", new TagFloat(exp));
    }

    @Override
    public int getExpTotal() {
        return ((TagInteger) adapter.getTagValue("XpTotal")).getValue();
    }

    @Override
    public void setExpTotal(int exp) {
        adapter.setValue("XpTotal", new TagInteger(exp));
    }

    @Override
    public void setTag(String key, TagValue<?> tagValue) {
        adapter.setValue(key, tagValue);
    }

    @Override
    public void removeTag(String key) {
        adapter.setValue(key, null);
    }

    @Override
    public TagValue<?> getTagValue(String key) {
        return adapter.getTagValue(key);
    }

    @Override
    public void openInventory(Player player) throws UnsupportedOperationException {
        throw new UnsupportedOperationException("Not supported in this version");
        /*OfflineInventoryView view = new OfflineInventoryView(this, player);
        player.openInventory(view);*/
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(uuid);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractPlayerData that = (AbstractPlayerData) o;
        return Objects.equals(uuid, that.uuid);
    }
}
