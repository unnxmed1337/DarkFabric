package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.injection.interfaces.IMixinMinecraft;
import net.minecraft.SharedConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.server.IntegratedServer;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.util.profiling.SingleTickProfiler;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.Proxy;

/**
 * MixinMinecraft
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 10/11/2019 - 12:44 pm
 */
@Mixin(Minecraft.class)
public abstract class MixinMinecraft implements IMixinMinecraft {

    @Shadow
    @Final
    private static Logger LOGGER;

    @Shadow
    @Nullable
    public Screen screen;

    @Shadow
    @Nullable
    public LocalPlayer player;

    @Mutable
    @Shadow
    @Final
    private User user;

    @Shadow
    @Nullable
    private ServerData currentServer;

    @Shadow
    @Nullable
    private IntegratedServer singleplayerServer;

    @Shadow
    private Thread gameThread;

    @Shadow
    private volatile boolean running;

    @Shadow
    private ProfilerFiller profiler;

    @Mutable
    @Shadow
    @Final
    private Proxy proxy;

    @Shadow
    @Nullable
    public abstract ClientPacketListener getConnection();

    @Shadow
    public abstract boolean isConnectedToRealms();

    @Shadow
    protected abstract boolean shouldRenderFpsPie();

    @Shadow
    protected abstract void startProfilers(boolean bl, @Nullable SingleTickProfiler singleTickProfiler);

    @Shadow
    protected abstract void runTick(boolean bl);

    @Shadow
    protected abstract void finishProfilers(boolean bl, @Nullable SingleTickProfiler singleTickProfiler);

    @Shadow
    public abstract void emergencySave();

    @Shadow
    public abstract void setScreen(@Nullable Screen screen);

    @Inject(method = "<init>", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;setWindowActive(Z)V", shift = At.Shift.BEFORE))
    private void startGame(CallbackInfo callbackInfo) {
        DarkFabric.getInstance().preInitialize();
    }

    @Inject(method = "<init>", at = @At(value = "RETURN"))
    private void startGamePost(CallbackInfo callbackInfo) {
        DarkFabric.getInstance().initialize();
    }

    @Inject(method = "close", at = @At(value = "HEAD"))
    private void close(CallbackInfo callbackInfo) {
        DarkFabric.getInstance().terminate();
    }

    @Override
    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @author unnamed
     * @reason for a fancier window title.
     */
    @Overwrite
    public String createTitle() {
        StringBuilder stringBuilder = new StringBuilder(DarkFabric.getInstance().getName()
                + " | Minecraft " + SharedConstants.getCurrentVersion().getName() + " (Fabric)");
        ClientPacketListener clientPlayNetworkHandler = this.getConnection();
        if (clientPlayNetworkHandler != null && clientPlayNetworkHandler.getConnection().isConnected()) {
            stringBuilder.append(" | ");
            if (this.singleplayerServer != null && !this.singleplayerServer.isPublished())
                stringBuilder.append("Singleplayer");
            else if (this.isConnectedToRealms())
                stringBuilder.append("Multiplayer (Realms)");
            else if (this.singleplayerServer == null && (this.currentServer == null || !this.currentServer.isLan())) {
                assert player != null;
                stringBuilder.append("Multiplayer (").append(currentServer.ip).append(")");
            } else
                stringBuilder.append("Multiplayer (Lan)");
        }
        return stringBuilder.toString();
    }

    @Override
    public void setProxy(Proxy proxy) {
        this.proxy = proxy;
    }

}