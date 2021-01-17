package com.github.darkfabric.module.impl.movement;

import com.github.darkfabric.event.impl.player.PlayerTickEvent;
import com.github.darkfabric.module.AbstractModule;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.client.Minecraft;

/**
 * AutoSprint
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:15 pm
 */
@AbstractModule.Info(name = "Sprint", description = "Automatically sprints for you.",
        type = AbstractModule.Type.MOVEMENT)
public class Sprint extends AbstractModule {

    private final Minecraft mc = Minecraft.getInstance();

    @EventHandler
    public Listener<PlayerTickEvent> playerTickEventListener = new Listener<>(event -> {
        assert mc.player != null;
        if (mc.player.getSpeed() > 0 && !mc.player.horizontalCollision
                && mc.player.getFoodData().getFoodLevel() >= 6) mc.player.setSprinting(true);
    });

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

}