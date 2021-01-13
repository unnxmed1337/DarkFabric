package com.github.darkforge.injection.mixins;

import com.github.darkforge.DarkForgeReloaded;
import com.github.darkforge.event.impl.player.PlayerChatsEvent;
import com.github.darkforge.event.impl.player.PlayerTickEvent;
import com.github.darkforge.event.impl.player.PlayerWalkingEvent;
import me.zero.alpine.event.EventState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * MixinClientPlayerEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:04 pm
 */
@Mixin(ClientPlayerEntity.class)
public abstract class MixinClientPlayerEntity {

    @Inject(method = "tick", at = @At(value = "HEAD"))
    public void tick(CallbackInfo ci) {
        DarkForgeReloaded.getInstance().getEventBus().post(new PlayerTickEvent());
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    private void onUpdateWalkingPlayerPre(CallbackInfo callbackInfoReturnable) {
        PlayerWalkingEvent eventPlayerWalking = new PlayerWalkingEvent(EventState.PRE,
                ((ClientPlayerEntity) (Object) this));
        DarkForgeReloaded.getInstance().getEventBus().post(eventPlayerWalking);
        if (eventPlayerWalking.isCancelled())
            callbackInfoReturnable.cancel();
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"), cancellable = true)
    private void onUpdateWalkingPlayerPost(CallbackInfo callbackInfoReturnable) {
        PlayerWalkingEvent eventPlayerWalking = new PlayerWalkingEvent(EventState.POST, ((ClientPlayerEntity) (Object) this));
        DarkForgeReloaded.getInstance().getEventBus().post(eventPlayerWalking);
        if (eventPlayerWalking.isCancelled())
            callbackInfoReturnable.cancel();
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessage(String message, CallbackInfo callbackInfo) {
        PlayerChatsEvent eventSendChatMessage = new PlayerChatsEvent(message);
        DarkForgeReloaded.getInstance().getEventBus().post(eventSendChatMessage);
        if (eventSendChatMessage.isCancelled())
            callbackInfo.cancel();
    }

}
