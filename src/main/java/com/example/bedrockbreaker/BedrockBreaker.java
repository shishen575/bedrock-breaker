package com.example.bedrockbreaker;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("bedrockbreaker")
public class BedrockBreaker {

    public static final Logger LOGGER = LogManager.getLogger();

    public BedrockBreaker() {
        MinecraftForge.EVENT_BUS.register(this);
        LOGGER.info("Bedrock Breaker MOD loaded!");
    }

    @SubscribeEvent
    public void onBlockBreak(BlockEvent.BreakEvent event) {
        BlockState state = event.getState();
        if (!state.is(Blocks.BEDROCK)) return;

        BlockPos pos = event.getPos();

        if (pos.getY() < -63) {
            event.setCanceled(true);
            return;
        }

        // ダイヤ相当未満のツール（AOE含む）はキャンセル
        if (!event.getPlayer().hasCorrectToolForDrops(Blocks.OBSIDIAN.defaultBlockState())) {
            event.setCanceled(true);
        }
    }
}
