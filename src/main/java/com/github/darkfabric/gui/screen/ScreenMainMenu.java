package com.github.darkfabric.gui.screen;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.gui.FancyButtonWidget;
import com.github.darkfabric.gui.screen.menu.MenuLogin;
import com.mojang.blaze3d.vertex.PoseStack;
import io.github.prospector.modmenu.gui.ModsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.multiplayer.JoinMultiplayerScreen;
import net.minecraft.client.gui.screens.worldselection.SelectWorldScreen;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class ScreenMainMenu extends Screen {

    public ScreenMainMenu() {
        super(new TextComponent("okay"));
    }

    @Override
    public void init(Minecraft minecraft, int i, int j) {
        super.init(minecraft, i, j);
    }

    @Override
    protected void init() {
        assert minecraft != null;
        String btnSinglePlayerDescription = "Play alone, forever. :^(";
        int btnSinglePlayerHeight = 25;
        addButton(new FancyButtonWidget(25, btnSinglePlayerHeight,
                minecraft.font.width(btnSinglePlayerDescription) + btnSinglePlayerHeight + 4,
                btnSinglePlayerHeight, button -> Minecraft.getInstance()
                .setScreen(new SelectWorldScreen(this)),
                new Color(25, 165, 25), "Singleplayer", btnSinglePlayerDescription,
                new ResourceLocation("darkfabric", "icons/mainmenu/singleplayer.png")));

        String btnMultiPlayerDescription = "Play online with/-out friends.";
        int btnMultiPlayerHeight = 25;
        addButton(new FancyButtonWidget(25, btnSinglePlayerHeight + btnMultiPlayerHeight + 5,
                minecraft.font.width(btnMultiPlayerDescription) + btnMultiPlayerHeight + 4,
                btnMultiPlayerHeight, button -> Minecraft.getInstance()
                .setScreen(new JoinMultiplayerScreen(this)),
                new Color(85, 85, 255), "Multiplayer", btnMultiPlayerDescription,
                new ResourceLocation("darkfabric", "icons/mainmenu/multiplayer.png")));

        String btnModsDescription = "Shows all installed Mods.";
        int btnModsHeight = 25;
        int btnModsWidth = minecraft.font.width(btnModsDescription) + btnModsHeight + 4;
        addButton(new FancyButtonWidget(width - btnModsWidth - 25, height - btnModsHeight * 2,
                btnModsWidth, btnModsHeight, button -> {
            try {
                minecraft.setScreen(new ModsScreen(this));
            } catch (Exception ignored) {
                // TODO: Make here a Notification (when Notifications got added) that the Mod "ModMenu" isn't installed.
            }
        },
                new Color(251, 184, 83), "Mods", btnModsDescription,
                new ResourceLocation("darkfabric", "icons/mainmenu/mods.png")));

        String btnSettingsDescription = "Set various things.";
        int btnSettingsHeight = 25;
        int btnSettingsWidth = minecraft.font.width(btnSettingsDescription) + btnSettingsHeight + 4;
        addButton(new FancyButtonWidget(width - btnSettingsWidth - 25, height - btnSettingsHeight * 3 - 5,
                btnSettingsWidth, btnSettingsHeight,
                button -> minecraft.setScreen(new OptionsScreen(this, minecraft.options)),
                new Color(170, 170, 170), "Settings", btnSettingsDescription,
                new ResourceLocation("darkfabric", "icons/mainmenu/settings.png")));

        String btnLoginDescription = ".";
        int btnLoginHeight = 25;
        int btnLoginWidth = minecraft.font.width(btnLoginDescription) + btnLoginHeight + 4;
        addButton(new FancyButtonWidget(width - btnLoginWidth - 25, height - btnLoginHeight * 4 - 10,
                btnLoginWidth, btnLoginHeight,
                button -> minecraft.setScreen(new MenuLogin(this)),
                new Color(255, 85, 85), "Client Menu", btnLoginDescription,
                new ResourceLocation("darkfabric", "icons/mainmenu/client.png")));
        super.init();
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        super.render(poseStack, i, j, f);
        int offset = 10;
        assert minecraft != null;

        float scale = 2.65f;
        String title = DarkFabric.getInstance().getName();

        GL11.glPushMatrix();
        GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        GL11.glScalef(scale, scale, scale);
        font.drawShadow(poseStack, title, offset, (height / scale) - (font.lineHeight
                * scale) + font.lineHeight, -1); // normal title
        GL11.glPopMatrix();
    }

}