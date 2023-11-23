package saday.underground;

import net.fabricmc.api.ModInitializer;

import net.minecraft.world.GameRules;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import saday.underground.block.ModBlockRegistry;

public class Underground implements ModInitializer {
	
	public static final String MOD_ID = "underground";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModBlockRegistry.registerModBlocks();
	}
}