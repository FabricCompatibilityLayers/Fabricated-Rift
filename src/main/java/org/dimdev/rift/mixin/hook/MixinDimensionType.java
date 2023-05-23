package org.dimdev.rift.mixin.hook;

import net.minecraft.world.dimension.DimensionType;
import org.dimdev.rift.listener.DimensionTypeAdder;
import fr.catcore.fabricatedrift.DimensionTypesUtils;
import org.dimdev.riftloader.RiftLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(DimensionType.class)
public class MixinDimensionType {

    static {
        for (DimensionType dimensionType : DimensionType.values()) {
            DimensionTypesUtils.dimensionTypes.put(dimensionType.getId(), dimensionType);
        }

        for (DimensionTypeAdder dimensionTypeAdder : RiftLoader.instance.getListeners(DimensionTypeAdder.class)) {
            for (DimensionType dimensionType : dimensionTypeAdder.getDimensionTypes()) {
                DimensionTypesUtils.dimensionTypes.put(dimensionType.getId(), dimensionType);
            }
        }
    }

    /**
     * @author Runemoro
     * @reason Handle additional dimensions
     */
    @Overwrite
    public static DimensionType getById(int id) {
        DimensionType dimensionType = DimensionTypesUtils.dimensionTypes.get(id);
        if (dimensionType == null) {
            throw new IllegalArgumentException("Invalid dimension id " + id);
        }

        return dimensionType;
    }
}
