package com.github.darkfabric.gui.screen.menu;

import com.github.darkfabric.gui.menu.Menu;
import com.github.darkfabric.util.AuthUtils;
import com.github.darkfabric.util.chat.ChatColors;
import com.github.darkfabric.util.render.DefaultPoseStack;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;

public class MenuLogin extends Menu {

    private EditBox emailBox, passwordBox;

    private String status;

    public MenuLogin(Screen prevScreen) {
        super(prevScreen, "Login", "Just a simple login.");
        status = ChatColors.GRAY + "Idle...";
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.render(poseStack, i, j, f);
        GL11.glPushMatrix();
        emailBox.render(poseStack, i, j, f);
        passwordBox.render(poseStack, i, j, f);
        assert minecraft != null;
        drawCenteredString(DefaultPoseStack.getMatrixStack(), minecraft.font, status, width / 2, height / 2 + 14, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void tick() {
        emailBox.tick();
        passwordBox.tick();
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (i == GLFW.GLFW_KEY_ENTER) login();
        if (getFocused() != this.emailBox && getFocused() != passwordBox || i != 257 && i != 335) {
            return super.keyPressed(i, j, k);
        } else {
            return true;
        }
    }

    public void login() {
        try {
            AuthUtils.logInIntoSession(emailBox.getValue(), passwordBox.getValue());
            status = String.format("%sLogged in! (%s)",
                    ChatColors.GREEN, Minecraft.getInstance().getUser().getName());
        } catch (AuthenticationException exception) {
            exception.printStackTrace();
            status = String.format("%sError whilst logging in! (%s)",
                    ChatColors.RED, Minecraft.getInstance().getUser().getName());
        }
    }

    @Override
    protected void init() {
        assert minecraft != null;
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        emailBox = new EditBox(Minecraft.getInstance().font, width / 2 - 100, height / 2 - 12 - 21,
                200, 20, new TextComponent("okay"));
        emailBox.setMaxLength(Integer.MAX_VALUE);
        emailBox.setFocus(true);
        emailBox.setValue("your@provider.me");

        passwordBox = new EditBox(Minecraft.getInstance().font, width / 2 - 100, height / 2 + 12 - 21,
                200, 20, new TextComponent("okay v2"));
        passwordBox.setMaxLength(Integer.MAX_VALUE);

        addButton(new Button(width / 2 - 101, height / 2 + 24, 200, 20,
                new TextComponent("Login"), button -> login()));

        children.add(emailBox);
        children.add(passwordBox);
        super.init();
    }

}