package org.dimdev.rift.mixin.optifine.client;

import net.optifine.CustomColors;
import net.optifine.render.RenderEnv;
import org.dimdev.rift.injectedmethods.RiftFluid;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Desc;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.block.state.IBlockState;
import net.minecraft.fluid.IFluidState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;

@Pseudo
@Mixin(value = CustomColors.class, remap = false)
@Desc(
		id = "fluidColor",
		value = "getFluidColor",
		args = {
				IWorldReader.class,
				IBlockState.class,
				BlockPos.class,
				RenderEnv.class
		},
		ret = int.class
)
public class MixinCustomColors {
	@Inject(at = @At("RETURN"), method = "@Desc(fluidColor)", remap = false, cancellable = true, constraints = "OPTIFINE(1+)")
	private static void fixFluidColours(IWorldReader world, IBlockState state, BlockPos pos, RenderEnv renderEnv, CallbackInfoReturnable<Integer> callback) {
		IFluidState fluidState = world.getFluidState(pos);

		if (fluidState.getFluid() instanceof RiftFluid) {
			RiftFluid fluid = (RiftFluid) fluidState.getFluid();
			if (fluid.ignoreOptifine()) callback.setReturnValue(fluid.getColorMultiplier(world, pos));
		}
	}
}