package com.sheath;

import com.sheath.init.EnchantmentInit;
import net.fabricmc.api.ModInitializer;

import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BetterMining implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("Better Mining");

	public static final String MOD_ID = "better-mining";

	@Override
	public void onInitialize() {
		LOGGER.info("Loading...");

		//Load init classes
		EnchantmentInit.load();
	}

	public static Identifier id(String path) {return Identifier.of(MOD_ID,path);}
}