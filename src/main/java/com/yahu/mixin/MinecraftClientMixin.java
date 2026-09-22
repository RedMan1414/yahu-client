package com.yahu.mixin;

import com.yahu.event.EventBus;
import com.yahu.event.events.EventTick;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftClientMixin {
    @Inject(method = "tick", at = @At("HEAD"))
    private void onPreTick(CallbackInfo ci) {
        EventBus.getInstance().post(new EventTick(EventTick.Phase.PRE, EventTick.Side.CLIENT));
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void onPostTick(CallbackInfo ci) {
        EventBus.getInstance().post(new EventTick(EventTick.Phase.POST, EventTick.Side.CLIENT));
    }
}