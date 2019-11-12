package uk.co.hexeption.darkforgereborn.mixin.mixins;

import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;

/**
 * MixinMinecraft
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 10/11/2019 - 12:44 pm
 */
@Mixin(Minecraft.class)
public class MixinMinecraft {

    @Inject(method = "init", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MainWindow;setLogOnGlError()V"))
    private void startGame(CallbackInfo callbackInfo){
        DarkForgeReborn.INSTANCE.start();
    }

}
