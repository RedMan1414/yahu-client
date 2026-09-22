package com.yahu.mixin;

import com.yahu.event.EventBus;
import com.yahu.event.events.EventAttack;
import com.yahu.event.events.EventMove;
import com.yahu.event.events.EventUseItem;
import com.yahu.event.events.EventBreakBlock;
import com.yahu.event.events.EventPlaceBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.phys.Vec3;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public class PlayerEntityMixin {
    @Inject(method = "attack", at = @At("HEAD"), cancellable = true)
    private void onAttack(Entity target, CallbackInfo ci) {
        EventAttack event = new EventAttack(target);
        EventBus.getInstance().post(event);
        if (event.isCancelled()) ci.cancel();
    }

    @Inject(method = "useItem", at = @At("HEAD"), cancellable = true)
    private void onUseItem(InteractionHand hand, CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
        EventUseItem event = new EventUseItem(hand);
        EventBus.getInstance().post(event);
        if (event.isCancelled()) cir.setReturnValue(net.minecraft.world.InteractionResult.FAIL);
    }

    @Inject(method = "destroyBlock", at = @At("HEAD"), cancellable = true)
    private void onBreakBlock(BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        Direction direction = Direction.DOWN; // Simplified
        EventBreakBlock event = new EventBreakBlock(pos, direction);
        EventBus.getInstance().post(event);
        if (event.isCancelled()) cir.setReturnValue(false);
    }

    @Inject(method = "placeBlock", at = @At("HEAD"), cancellable = true)
    private void onPlaceBlock(BlockPos pos, Direction direction, InteractionHand hand, CallbackInfoReturnable<net.minecraft.world.InteractionResult> cir) {
        EventPlaceBlock event = new EventPlaceBlock(pos, hand);
        EventBus.getInstance().post(event);
        if (event.isCancelled()) cir.setReturnValue(net.minecraft.world.InteractionResult.FAIL);
    }
}