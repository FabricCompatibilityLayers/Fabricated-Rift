package net.optifine;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.optifine.render.RenderEnv;

public class CustomColors {
    public static int getFluidColor(IWorldReader blockAccess, IBlockState blockState, BlockPos blockPos, RenderEnv renderEnv) {
        return -1;
    }
}