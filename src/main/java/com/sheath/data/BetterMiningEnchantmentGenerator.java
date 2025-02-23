package com.sheath.data;

import com.sheath.enchantments.effects.VeinminerEnchantmentEffect;
import com.sheath.init.EnchantmentInit;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricDynamicRegistryProvider;
import net.fabricmc.fabric.api.resource.conditions.v1.ResourceCondition;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class BetterMiningEnchantmentGenerator extends FabricDynamicRegistryProvider {
    public BetterMiningEnchantmentGenerator(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup registeries, Entries entries) {
        RegistryWrapper<Item> itemLookup = registeries.getOrThrow(RegistryKeys.ITEM);

        if (!FabricLoader.getInstance().getEnvironmentType().equals(EnvType.SERVER)) {
            return; // Ensure this does not sync to clients
        }
        register(entries, EnchantmentInit.VEINMINER, Enchantment.builder(
                Enchantment.definition(
                        itemLookup.getOrThrow(ItemTags.PICKAXES),
                        15, //Weight
                        1, //Max Level
                        Enchantment.leveledCost(1,5), //Base cost
                        Enchantment.leveledCost(1,10),//Max cost
                        4, //Anvil Cost
                        AttributeModifierSlot.HAND
                ))
                .addEffect(EnchantmentEffectComponentTypes.HIT_BLOCK,
                        new VeinminerEnchantmentEffect(10))
        );
    }

    private static void register(Entries entries, RegistryKey<Enchantment> key, Enchantment.Builder builder, ResourceCondition... resourceConditions){
        entries.add(key,builder.build(key.getValue()), resourceConditions);
    }

    @Override
    public String getName() {
        return "Enchantment Generator ";
    }
}
