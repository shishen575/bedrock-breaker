package com.example.bedrockbreaker.mixin;

import com.example.bedrockbreaker.BedrockConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.class)
public class BedrockMixin {

    @Inject(method = "getDestroyProgress", at = @At("HEAD"), cancellable = true)
    private void onGetDestroyProgress(BlockState state, Player player, BlockGetter level,
                                      BlockPos pos, CallbackInfoReturnable<Float> cir) {
        if (!state.is(Blocks.BEDROCK)) return;

        if (pos.getY() < -63) {
            cir.setReturnValue(0.0f);
            return;
        }

        // オブシディアンを採掘できるツールか確認（ダイヤ相当以上）
        // Forge標準インターフェース経由なのでTinkers'等の独自ツールにも対応
        if (!player.hasCorrectToolForDrops(Blocks.OBSIDIAN.defaultBlockState())) {
            cir.setReturnValue(0.0f);
            return;
        }

        float progress = player.getDestroySpeed(state) / BedrockConstants.HARDNESS / 30.0f;
        cir.setReturnValue(progress);
    }
}
