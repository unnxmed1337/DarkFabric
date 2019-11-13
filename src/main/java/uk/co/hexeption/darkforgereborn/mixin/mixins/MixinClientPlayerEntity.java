package uk.co.hexeption.darkforgereborn.mixin.mixins;

import me.zero.alpine.event.EventState;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.event.events.chat.EventSendChatMessage;
import uk.co.hexeption.darkforgereborn.event.events.player.EventPlayerWalking;

/**
 * MixinClientPlayerEntity
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:04 pm
 */
@Mixin(ClientPlayerEntity.class)
public class MixinClientPlayerEntity {

    @Inject(method = "onUpdateWalkingPlayer", at = @At("HEAD"), cancellable = true)
    private void onUpdateWalkingPlayerPre(CallbackInfo callbackInfoReturnable) {
        EventPlayerWalking eventPlayerWalking = new EventPlayerWalking(EventState.PRE, ((ClientPlayerEntity) (Object) this));
        DarkForgeReborn.INSTANCE.eventBus.post(eventPlayerWalking);
        if (eventPlayerWalking.isCancelled()) {
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method = "onUpdateWalkingPlayer", at = @At("RETURN"), cancellable = true)
    private void onUpdateWalkingPlayerPost(CallbackInfo callbackInfoReturnable) {
        EventPlayerWalking eventPlayerWalking = new EventPlayerWalking(EventState.POST, ((ClientPlayerEntity) (Object) this));
        DarkForgeReborn.INSTANCE.eventBus.post(eventPlayerWalking);
        if (eventPlayerWalking.isCancelled()) {
            callbackInfoReturnable.cancel();
        }
    }

    @Inject(method = "sendChatMessage", at = @At("HEAD"), cancellable = true)
    private void sendChatMessage(String message, CallbackInfo callbackInfo) {
        EventSendChatMessage eventSendChatMessage = new EventSendChatMessage(message);
        DarkForgeReborn.INSTANCE.eventBus.post(eventSendChatMessage);
        if (eventSendChatMessage.isCancelled()) {
            callbackInfo.cancel();
        }
    }

}
