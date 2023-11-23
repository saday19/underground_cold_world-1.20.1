package saday.underground;

import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.LightType;
import net.minecraft.world.World;

import java.util.HashMap;

public class EntityFreezeController {

    private static HashMap<PlayerEntity, Integer> map = new HashMap<PlayerEntity, Integer>();

    public static void tickPlayerEntity(PlayerEntity playerEntity) {

        if(map.containsKey(playerEntity)) {
            Integer temperatureCounter = map.get(playerEntity);
            if(isNearHeat(playerEntity, 5)) {
                if(temperatureCounter >= 5) {
                    map.put(playerEntity, temperatureCounter - 5);
                } else {
                    map.put(playerEntity, 0);
                }
            } else {
                if(temperatureCounter < UndergroundGlobalSettings.getMaxColdTicks()) {
                    map.put(playerEntity, temperatureCounter + 1);
                }
            }
        } else {
            map.put(playerEntity, 0);
        }

    }

    private static boolean isNearHeat(PlayerEntity playerEntity, int maxRange) {

        World world = playerEntity.getWorld();
        Vec3d playerPos = playerEntity.getPos();
        BlockPos centerPos = new BlockPos(new Vec3i((int) playerPos.getX(), (int) playerPos.getY(), (int) playerPos.getZ()));

        for (int x = -maxRange; x <= maxRange; x++) {
            for (int y = -maxRange; y <= maxRange; y++) {
                for (int z = -maxRange; z <= maxRange; z++) {
                    BlockPos currentPos = centerPos.add(x, y, z);
                    if(world.getBlockState(currentPos).isOf(Blocks.TORCH) ||
                            world.getBlockState(currentPos).isOf(Blocks.FURNACE) ||
                            world.getBlockState(currentPos).isOf(Blocks.BLAST_FURNACE) ||
                            world.getBlockState(currentPos).isOf(Blocks.FIRE) ||
                            world.getBlockState(currentPos).isOf(Blocks.CAMPFIRE) ||
                            world.getBlockState(currentPos).isOf(Blocks.SOUL_CAMPFIRE) ||
                            world.getBlockState(currentPos).isOf(Blocks.SOUL_LANTERN) ||
                            world.getBlockState(currentPos).isOf(Blocks.SOUL_TORCH) ||
                            world.getBlockState(currentPos).isOf(Blocks.WALL_TORCH) ||
                            world.getBlockState(currentPos).isOf(Blocks.LANTERN) ||
                            world.getBlockState(currentPos).isOf(Blocks.LAVA) ||
                            world.getBlockState(currentPos).isOf(Blocks.JACK_O_LANTERN)) {

                        return true;

                    }

                }
            }
        }

        return false;
    }

    public static boolean isCold(LivingEntity livingEntity) {

        if(!(livingEntity instanceof PlayerEntity)) {
            return false;
        }

        PlayerEntity playerEntity = (PlayerEntity) livingEntity;

        if(map.containsKey(playerEntity)) {
            if(map.get(playerEntity) >= UndergroundGlobalSettings.getMaxColdTicks()) return true;
        }

        return false;
    }

    public static boolean isExposedToSurface(LivingEntity livingEntity) {
        int worldHeight = livingEntity.getWorld().getHeight();
        Vec3d pos = livingEntity.getPos();
        BlockPos blockPos = new BlockPos((int) pos.getX(), (int) pos.getY(), (int) pos.getZ());
        World world = livingEntity.getWorld();
        return world.getLightLevel(LightType.SKY, blockPos) >= 1;
    }

    public static boolean isNearSurface(LivingEntity livingEntity) {
        int worldHeight = livingEntity.getWorld().getHeight();
        Vec3d pos = livingEntity.getPos();
        BlockPos blockPos = new BlockPos((int) pos.getX(), (int) pos.getY() + 10, (int) pos.getZ());
        World world = livingEntity.getWorld();
        return world.getLightLevel(LightType.SKY, blockPos) >= world.getMaxLightLevel();
    }

}
