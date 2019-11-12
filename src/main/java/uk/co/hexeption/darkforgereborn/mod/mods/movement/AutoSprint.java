package uk.co.hexeption.darkforgereborn.mod.mods.movement;

import me.zero.alpine.event.EventState;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import org.lwjgl.glfw.GLFW;
import uk.co.hexeption.darkforgereborn.event.events.player.EventPlayerWalking;
import uk.co.hexeption.darkforgereborn.mod.Category;
import uk.co.hexeption.darkforgereborn.mod.Mod;

/**
 * AutoSprint
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 07:15 pm
 */
@Mod.ModInfo(name = "Auto Sprint", description = "Automatically Sprints for you.", category = Category.MOVEMENT, bind = GLFW.GLFW_KEY_L)
public class AutoSprint extends Mod {

    @Override
    public void onDisable() {
        if (mc.player != null) {
            mc.player.setSprinting(false);
        }
    }

    @EventHandler
    private Listener<EventPlayerWalking> eventPlayerWalkingListener = new Listener<>(event -> {
        if (getState()) {
            if (event.getEventState() == EventState.PRE) {
                if ((!mc.player.collidedHorizontally) && (mc.player.moveForward > 0.0f) && (!mc.player.isSneaking())) {
                    mc.player.setSprinting(true);
                }
            }
        }
    });
}
