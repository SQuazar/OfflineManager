package net.flawe.offlinemanager.api.data.entity;

import net.flawe.offlinemanager.api.nbt.ITagCompound;
import net.flawe.offlinemanager.api.nbt.type.*;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.Objects;
import java.util.UUID;

public abstract class AbstractPlayerData implements IPlayerData {

    private final UUID uuid;
    private final ITagCompound compound;

    protected AbstractPlayerData(UUID uuid, ITagCompound compound) {
        this.uuid = uuid;
        this.compound = compound;
    }

    @Override
    public UUID getUUID() {
        return uuid;
    }

    @Override
    public String getName() {
        return ((TagString) compound.getTagCompound("bukkit").getTagValue("lastKnownName")).getValue();
    }

    @Override
    public float getFallDistance() {
        return ((TagFloat) compound.getTagValue("FallDistance")).getValue();
    }

    @Override
    public void setFallDistance(float distance) {
        compound.setValue("FallDistance", new TagFloat(distance));
    }

    @Override
    public short getFireTicks() {
        return ((TagShort) compound.getTagValue("Fire")).getValue();
    }

    @Override
    public void setFireTicks(short ticks) {
        compound.setValue("Fire", new TagShort(ticks));
    }

    @Override
    public float getExhaustion() {
        return ((TagFloat) compound.getTagValue("foodExhaustionLevel")).getValue();
    }

    @Override
    public void setExhaustion(float exhaustion) {
        compound.setValue("foodExhaustionLevel", new TagFloat(exhaustion));
    }

    @Override
    public int getFoodLevel() {
        return ((TagInteger) compound.getTagValue("foodLevel")).getValue();
    }

    @Override
    public void setFoodLevel(int level) {
        compound.setValue("foodLevel", new TagInteger(level));
    }

    @Override
    public float getSaturation() {
        return ((TagFloat) compound.getTagValue("foodSaturationLevel")).getValue();
    }

    @Override
    public void setSaturation(float saturation) {
        compound.setValue("foodSaturationLevel", new TagFloat(saturation));
    }

    @Override
    public float getHealth() {
        return ((TagFloat) compound.getTagValue("Health")).getValue();
    }

    @Override
    public void setHealth(float health) {
        compound.setValue("Health", new TagFloat(health));
    }

    @Override
    public boolean isInvulnerable() {
        return ((TagByte) compound.getTagValue("Invulnerable")).getValue() != 0;
    }

    private static final TagByte byteFalse = new TagByte((byte) 0);
    private static final TagByte byteTrue = new TagByte((byte) 1);

    @Override
    public void setInvulnerable(boolean b) {
        compound.setValue("Invulnerable", b ? byteTrue : byteFalse);
    }

    @Override
    public boolean isOnGround() {
        return ((TagByte) compound.getTagValue("OnGround")).getValue() != 0;
    }

    @Override
    public GameMode getGameMode() {
        return GameMode.getByValue(((TagInteger) compound.getTagValue("playerGameType")).getValue());
    }

    @Override
    public void setGameMode(GameMode gameMode) {
        compound.setValue("playerGameType", new TagInteger(gameMode.getValue()));
    }

    @Override
    public int getPortalCooldown() {
        return ((TagInteger) compound.getTagValue("PortalCooldown")).getValue();
    }

    @Override
    public void setPortalCooldown(int cooldown) {
        compound.setValue("PortalCooldown", new TagInteger(cooldown));
    }

    @Override
    public int getSelectedSlot() {
        return ((TagInteger) compound.getTagValue("SelectedItemSlot")).getValue();
    }

    @Override
    public void setSelectedSlot(int slot) {
        compound.setValue("SelectedItemSlot", new TagInteger(slot));
    }

    @Override
    public World getWorld() {
        long most = ((TagLong) compound.getTagValue("WorldUUIDMost")).getValue();
        long least = ((TagLong) compound.getTagValue("WorldUUIDLeast")).getValue();
        return Bukkit.getWorld(new UUID(most, least));
    }

    @Override
    public Location getLocation() {
        TagList pos = (TagList) compound.getTagValue("Pos");
        TagList rotation = (TagList) compound.getTagValue("Rotation");
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
        compound.setValue("Pos", pos);
        compound.setValue("Rotation", rotation);
        compound.setValue("WorldUUIDLeast", worldUuidLeast);
        compound.setValue("WorldUUIDMost", worldUuidMost);
    }

    @Override
    public void setLocation(Entity entity) {
        setLocation(entity.getLocation());
    }

    @Override
    public int getExpLevel() {
        return ((TagInteger) compound.getTagValue("XpLevel")).getValue();
    }

    @Override
    public void setExpLevel(int level) {
        compound.setValue("XpLevel", new TagInteger(level));
    }

    @Override
    public float getExp() {
        return ((TagFloat) compound.getTagValue("XpP")).getValue();
    }

    @Override
    public void setExp(float exp) {
        compound.setValue("XpP", new TagFloat(exp));
    }

    @Override
    public int getExpTotal() {
        return ((TagInteger) compound.getTagValue("XpTotal")).getValue();
    }

    @Override
    public void setExpTotal(int exp) {
        compound.setValue("XpTotal", new TagInteger(exp));
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
