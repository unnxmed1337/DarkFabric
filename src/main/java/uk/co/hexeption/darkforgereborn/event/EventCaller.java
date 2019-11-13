package uk.co.hexeption.darkforgereborn.event;

import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import org.lwjgl.glfw.GLFW;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.IMC;
import uk.co.hexeption.darkforgereborn.event.events.input.EventKeyPressed;
import uk.co.hexeption.darkforgereborn.mod.options.Option;

/**
 * EventCaller
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 11:37 am
 */
public class EventCaller implements Listenable, IMC {

    @EventHandler
    private Listener<EventKeyPressed> eventKeyPressedListener = new Listener<>(event -> {
        DarkForgeReborn.INSTANCE.modManager.getMods().forEach(mod -> {
            if (GLFW.glfwGetKeyName(event.getKey(), event.getScancode()) == null || mc.currentScreen != null) {
                return;
            }
            if (GLFW.glfwGetKeyName(event.getKey(), event.getScancode()).equalsIgnoreCase(Option.get(mod.options, "keybind").STRING())) {
                mod.toggle();
            }
        });
    });

}
