package com.example.bedrockbreaker.mixin;

import com.example.bedrockbreaker.BedrockConstants;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBehaviour.BlockStateBase.class)
public class BedrockStateMixin {

    @Inject(method = "getDestroySpeed", at = @At("HEAD"), cancellable = true)
    private void onGetDestroySpeed(BlockGetter level, BlockPos pos,
                                   CallbackInfoReturnable<Float> cir) {
        BlockState self = (BlockState) (Object) this;
        if (!self.is(Blocks.BEDROCK)) return;
        cir.setReturnValue(BedrockConstants.HARDNESS);
    }
}
