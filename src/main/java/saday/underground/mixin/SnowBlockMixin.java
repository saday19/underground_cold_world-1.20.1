package saday.underground.mixin;

import net.minecraft.block.BlockState;
import net.minecraft.block.SnowBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.LightType;
import net.minecraft.world.biome.Biome;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import saday.underground.UndergroundGlobalSettings;

@Mixin(SnowBlock.class)
public class SnowBlockMixin {

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void onRandomTick(BlockState state, ServerWorld world, BlockPos pos, Random random, CallbackInfo ci) {
        if (world.getLightLevel(LightType.BLOCK, pos) > 11) {
            SnowBlock.dropStacks(state, world, pos);
            world.removeBlock(pos, false);
        }
        if(!UndergroundGlobalSettings.isUndergroundEnabled()) {
            Biome biome = world.getBiomeAccess().getBiome(pos).value();
            if(biome.getTemperature() >= 0.15f) {
                if(world.getLightLevel(LightType.SKY, pos) > 11) {
                    world.removeBlock(pos, false);
                }
            }
        }
        ci.cancel();
    }

}
