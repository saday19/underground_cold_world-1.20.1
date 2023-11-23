package saday.underground.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FluidBlock;
import net.minecraft.fluid.FluidState;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import saday.underground.UndergroundGlobalSettings;

@Mixin(Biome.class)
public class BiomeMixin {

    @Inject(method = "getPrecipitation", at = @At("RETURN"), cancellable = true)
    private void onGetPrecipitation(BlockPos pos, CallbackInfoReturnable<Biome.Precipitation> ci) {
        if(UndergroundGlobalSettings.isUndergroundEnabled()) {
            ci.setReturnValue(Biome.Precipitation.SNOW);
        }
    }

    @Inject(method = "hasPrecipitation", at = @At("RETURN"), cancellable = true)
    private void onHasPrecipitation(CallbackInfoReturnable<Boolean> ci) {
        if(UndergroundGlobalSettings.isUndergroundEnabled()) {
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "canSetIce", at = @At("RETURN"), cancellable = true)
    private void onCanSetIce(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        if (UndergroundGlobalSettings.isUndergroundEnabled()) {
            BlockState blockState = world.getBlockState(pos);
            FluidState fluidState = world.getFluidState(pos);
            if (fluidState.getFluid() == Fluids.WATER && blockState.getBlock() instanceof FluidBlock)
            ci.setReturnValue(true);
        }
    }

    @Inject(method = "canSetSnow", at = @At("RETURN"), cancellable = true)
    private void onCanSetSnow(WorldView world, BlockPos pos, CallbackInfoReturnable<Boolean> ci) {
        if(UndergroundGlobalSettings.isUndergroundEnabled()) {
            BlockPos pos2 = pos.down();
            if(!world.getBlockState(pos2).isOf(Blocks.ICE) && !world.getBlockState(pos2).isOf(Blocks.WATER) && !world.getBlockState(pos2).isOf(Blocks.LAVA)) {
                if(world.getBlockState(pos).isOf(Blocks.AIR)) {
                    ci.setReturnValue(true);
                }
            }
        }
    }

}
