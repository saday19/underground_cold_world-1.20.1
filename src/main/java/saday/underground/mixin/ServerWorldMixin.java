package saday.underground.mixin;

import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.level.ServerWorldProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerWorld.class)
public class ServerWorldMixin {

    @Shadow
    private ServerWorldProperties worldProperties;

    @Inject(method = "tickWeather", at = @At("TAIL"), cancellable = true)
    private void onTickWeather(CallbackInfo ci) {
        worldProperties.setRaining(true);
    }

    @Redirect(
            method = "tickChunk",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/GameRules;getInt(Lnet/minecraft/world/GameRules$Key;)I",
                    ordinal = 0
            )
    )
    private int redirectGetSnowAccumulationHeight(GameRules gameRules, GameRules.Key<GameRules.IntRule> key) {
        return 7;
    }

}
