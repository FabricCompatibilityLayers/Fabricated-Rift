package org.dimdev.rift.util;

import net.minecraft.nbt.NBTTagCompound;
import org.jetbrains.annotations.NotNull;

/**
 * Base interface for (de)serializable objects to serialize
 * themselves to and from {@link NBTTagCompound} tag compounds
 */
public interface NBTSerializable {
    /**
     * Writes this object's data to the given compound
     * @param compound The tag compound to be written to
     * @return The written tag compound
     */
    @NotNull
    NBTTagCompound serialize(@NotNull NBTTagCompound compound);

    /**
     * Reads this object's data from the given compound
     * @param compound The tag compound to be read from
     * @return The given tag compound
     */
    @NotNull
    NBTTagCompound deserialize(@NotNull NBTTagCompound compound);

    /**
     * Writes this object's data to a new tag compound
     * @return The written tag compound
     */
    @NotNull
    default NBTTagCompound writeToNBT() {
        return this.serialize(new NBTTagCompound());
    }
}
