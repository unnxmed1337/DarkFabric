package uk.co.hexeption.darkforgereborn.ui.hud;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.Comparator;
import java.util.List;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listenable;
import me.zero.alpine.listener.Listener;
import org.lwjgl.opengl.GL11;
import uk.co.hexeption.darkforgereborn.ClientInfo;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.IMC;
import uk.co.hexeption.darkforgereborn.event.events.screen.EventRender2D;
import uk.co.hexeption.darkforgereborn.mod.Mod;
import uk.co.hexeption.darkforgereborn.ui.hud.DarkForgeHud.ModList.Colour;
import uk.co.hexeption.darkforgereborn.ui.hud.DarkForgeHud.ModList.Position;
import uk.co.hexeption.darkforgereborn.util.ColourUtils;

/**
 * DarkForgeHud
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 06:23 pm
 */
public class DarkForgeHud implements IMC, Listenable {

    private ModList.Colour modListColor = Colour.RAINBOW;

    private ModList.Position modlistPosition = Position.TOPRIGHT;

    private ModList.Sort modListSort = ModList.Sort.LENGTH;

    private Color solidColor = new Color(0x8C3038);

    @EventHandler
    private Listener<EventRender2D> eventRender2DListener = new Listener<>(event -> {
        if (mc.gameSettings.showDebugInfo) {
            return;
        }
        DarkForgeReborn.INSTANCE.fontManager.hud.drawStringWithShadow(String.format("%s %s", ClientInfo.MOD_NAME, ClientInfo.MOD_BUILD), 5, 5, 0xffffff);
        renderModList(event.getWidth(), event.getHeight());
    });


    private void renderModList(int displayWidth, int displayHeight) {

        List<Mod> mods = Lists.newArrayList();
        DarkForgeReborn.INSTANCE.modManager.getMods().forEach(mod -> {
            if (mod.getState()) {
                mods.add(mod);
            }
        });

        GL11.glPushMatrix();
        switch (modlistPosition) {
            case TOPLEFT:
                GL11.glTranslatef(4, 17, 0);
                break;
            case TOPRIGHT:
                GL11.glTranslatef(displayWidth - 4, 4, 0);
                break;
            case BOTTOMLEFT:
                GL11.glTranslatef(4, displayHeight - 4 - DarkForgeReborn.INSTANCE.fontManager.arraylist.getHeight(), 0);
                break;
            case BOTTOMRIGHT:
                GL11.glTranslatef(displayWidth - 4, displayHeight - 4 - DarkForgeReborn.INSTANCE.fontManager.arraylist.getHeight(), 0);
                break;
        }

        Comparator<Mod> comparator = Comparator.comparing(Mod::getName);
        switch (modListSort) {
            case ALPHABETICAL:
                break;
            case CATEGORY:
                comparator = Comparator.comparing(c -> c.getCategory().name());
                break;
            case LENGTH:
                comparator = ((o1, o2) -> (int) (
                    DarkForgeReborn.INSTANCE.fontManager.arraylist.getStringWidth(o2.getName())
                        - DarkForgeReborn.INSTANCE.fontManager.arraylist.getStringWidth(o1.getName())));
                break;
        }
        mods.sort(comparator);

        for (Mod mod : mods) {
            int xOffset = modlistPosition == ModList.Position.TOPRIGHT
                || modlistPosition == ModList.Position.BOTTOMRIGHT ? -DarkForgeReborn.INSTANCE.fontManager.arraylist
                .getStringWidth(mod.getName()) : 0;

            int color = -1;

            switch (modListColor) {
                case CATEGORY:
                    color = mod.getCategory().getColor();
                    break;
                case RANDOM:
                    color = ColourUtils.hs(mod.hashCode(), 60);
                    break;
                case RAINBOW:
                    color = ColourUtils.rainbow(xOffset + mods.indexOf(mod));
                    break;
                case SOLID:
                    color = solidColor.hashCode();
                    break;
            }

            DarkForgeReborn.INSTANCE.fontManager.arraylist.drawString(mod.getName(), xOffset, 0, color);

            GL11.glTranslatef(0,
                DarkForgeReborn.INSTANCE.fontManager.arraylist.getStringHeight(mod.getName()) * (
                    modlistPosition == ModList.Position.TOPLEFT
                        || modlistPosition == ModList.Position.TOPRIGHT ? 1 : -1), 0);
        }

        GL11.glPopMatrix();

    }


    /* Mod List Enums */

    public abstract static class ModList {

        public enum Colour {
            CATEGORY("Category"),
            RANDOM("Random"),
            RAINBOW("RAINBOW"),
            SOLID("Solid");

            String name;

            Colour(String name) {

                this.name = name;
            }

            public String getName() {

                return name;
            }

            @Override
            public String toString() {

                return name;
            }
        }

        public enum Position {
            TOPLEFT("Top Left"),
            TOPRIGHT("Top Right"),
            BOTTOMLEFT("Bottom Left"),
            BOTTOMRIGHT("Bottom Right");

            String name;

            Position(String name) {

                this.name = name;
            }

            public String getName() {

                return name;
            }

            @Override
            public String toString() {

                return name;
            }
        }

        public enum Sort {
            CATEGORY("Category"),
            LENGTH("Length"),
            ALPHABETICAL("Alphabetical");

            String name;

            Sort(String name) {

                this.name = name;
            }

            public String getName() {

                return name;
            }

            @Override
            public String toString() {

                return name;
            }
        }

    }

}
