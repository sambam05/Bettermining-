package com.sheath.init;

import com.mojang.serialization.MapCodec;
import com.sheath.BetterMining;
import com.sheath.enchantments.effects.VeinminerEnchantmentEffect;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;

public class EnchantmentInit {
    public static final RegistryKey<Enchantment> VEINMINER = RegistryKey.of(RegistryKeys.ENCHANTMENT, BetterMining.id("veinminer"));

    public static final MapCodec<VeinminerEnchantmentEffect> VEINMINER_EFFECT = register("veinminer", VeinminerEnchantmentEffect.CODEC);

    private static <T extends EnchantmentEntityEffect> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, BetterMining.id(name), codec);
    }

    public static void load() {
        // Load enchantments
    }
}
