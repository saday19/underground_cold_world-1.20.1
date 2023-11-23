package saday.underground.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.piston.PistonBehavior;
import net.minecraft.item.BlockItem;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;
import saday.underground.Underground;

public class ModBlockRegistry {

    public static final Block UNLIT_TORCH = new UnlitTorch(AbstractBlock.Settings.create().noCollision().breakInstantly().luminance(state -> 3).sounds(BlockSoundGroup.WOOD).pistonBehavior(PistonBehavior.DESTROY), ParticleTypes.SMOKE);


    public static void registerModBlocks() {
        Registry.register(Registries.BLOCK, new Identifier(Underground.MOD_ID, "unlit_torch"), UNLIT_TORCH);
        Registry.register(Registries.ITEM, new Identifier(Underground.MOD_ID, "unlit_torch"), new BlockItem(UNLIT_TORCH, new FabricItemSettings()));
    }

}
