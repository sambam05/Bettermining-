package com.sheath.enchantments.effects;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.sheath.utility.math.Vec3dToVec3i;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.enchantment.EnchantmentEffectContext;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.entity.player.PlayerEntity;

import java.util.HashSet;
import java.util.Set;

public record VeinminerEnchantmentEffect(int range) implements EnchantmentEntityEffect {
    public static final MapCodec<VeinminerEnchantmentEffect> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(
                    com.mojang.serialization.Codec.INT.fieldOf("range").forGetter(VeinminerEnchantmentEffect::range)
            ).apply(instance, VeinminerEnchantmentEffect::new));

    @Override
    public void apply(ServerWorld world, int level, EnchantmentEffectContext context, Entity target, Vec3d pos) {
        if (!(target instanceof PlayerEntity player)) return;

        // Ensure this only runs on the server and does not sync to clients
        if (world.isClient()) return;

        BlockPos startPos = new BlockPos(Vec3dToVec3i.convert(pos));
        BlockState startState = world.getBlockState(startPos);
        Block startBlock = startState.getBlock();

        // Ensure we're breaking an ore block
        if (isOre(startBlock)) {
            Set<BlockPos> minedBlocks = new HashSet<>();
            veinMine(world, player, startPos, startBlock, minedBlocks, range);
        }
    }

    private void veinMine(ServerWorld world, PlayerEntity player, BlockPos pos, Block startBlock, Set<BlockPos> minedBlocks, int remaining) {
        if (remaining <= 0 || !minedBlocks.add(pos)) return; // Stop if limit reached or already mined

        BlockState state = world.getBlockState(pos);
        if (state.getBlock() != startBlock) return;

        // Break the block and drop items
        world.breakBlock(pos, true, player);

        // Check all adjacent blocks
        for (BlockPos adj : getAdjacentBlocks(pos)) {
            veinMine(world, player, adj, startBlock, minedBlocks, remaining - 1);
        }
    }

    private boolean isOre(Block block) {
        return block == Blocks.COAL_ORE || block == Blocks.IRON_ORE || block == Blocks.GOLD_ORE ||
                block == Blocks.DIAMOND_ORE || block == Blocks.LAPIS_ORE || block == Blocks.REDSTONE_ORE ||
                block == Blocks.EMERALD_ORE || block == Blocks.NETHER_QUARTZ_ORE || block == Blocks.NETHER_GOLD_ORE ||
                block == Blocks.DEEPSLATE_COAL_ORE || block == Blocks.DEEPSLATE_IRON_ORE || block == Blocks.DEEPSLATE_GOLD_ORE ||
                block == Blocks.DEEPSLATE_DIAMOND_ORE || block == Blocks.DEEPSLATE_LAPIS_ORE || block == Blocks.DEEPSLATE_REDSTONE_ORE ||
                block == Blocks.DEEPSLATE_EMERALD_ORE;
    }

    private BlockPos[] getAdjacentBlocks(BlockPos pos) {
        return new BlockPos[]{
                pos.up(), pos.down(), pos.north(), pos.south(), pos.east(), pos.west()
        };
    }
    @Override
    public MapCodec<? extends EnchantmentEntityEffect> getCodec() {
        return CODEC;
    }
}
