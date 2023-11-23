package saday.underground.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import saday.underground.EntityFreezeController;
import saday.underground.Underground;
import saday.underground.UndergroundGlobalSettings;

import java.util.ArrayList;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    private static ArrayList<Entity> modifiedDamage = new ArrayList<Entity>();

    //set the player to be cold if they are above ground or exposed to air
    @Inject(method = "tickMovement", at = @At("HEAD"))
    private void modifyTickMovement(CallbackInfo ci) {

        if(UndergroundGlobalSettings.isUndergroundEnabled()){

            LivingEntity livingEntity = (LivingEntity) (Object) this;

            if(EntityFreezeController.isNearSurface(livingEntity) || EntityFreezeController.isExposedToSurface(livingEntity) || EntityFreezeController.isCold(livingEntity)) {
                livingEntity.inPowderSnow = true;
            }

        }
    }

    @Inject(method = "getAttributeValue(Lnet/minecraft/entity/attribute/EntityAttribute;)D",
            at = @At("RETURN"), cancellable = true)
    public void modifyAttributeValue(EntityAttribute attribute, CallbackInfoReturnable<Double> ci) {
        if (UndergroundGlobalSettings.isUndergroundEnabled()) {
            if (attribute == EntityAttributes.GENERIC_ATTACK_DAMAGE) {
                double modifiedValue = ci.getReturnValue() * 2;
                //ci.setReturnValue(modifiedValue);
                //Underground.LOGGER.info("Damage modifier - " + modifiedValue);
            }
        }
    }

    @Inject(method = "damage(Lnet/minecraft/entity/damage/DamageSource;F)Z", at = @At("HEAD"), cancellable = true)
    private void doubleMobDamage(DamageSource source, float amount, CallbackInfoReturnable<Boolean> ci) {
        if(UndergroundGlobalSettings.isUndergroundEnabled()) {
            Object targetObject = this;
            if (targetObject instanceof PlayerEntity && source.getSource() instanceof LivingEntity) {
                LivingEntity target = (LivingEntity) targetObject;
                LivingEntity attacker = (LivingEntity) source.getSource();
                if (!attacker.isPlayer()) {
                    EntityAttributeInstance attribute = attacker.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
                    if (attacker != null && !attacker.isPlayer()) {
                        if(!modifiedDamage.contains(attacker)) {
                            modifiedDamage.add(attacker);
                            double modifiedAmount = amount * 2.0f;
                            attribute.setBaseValue(modifiedAmount);
                            modifiedDamage.add(attacker);
                        }
                    }
                }
            }
        }
    }

}