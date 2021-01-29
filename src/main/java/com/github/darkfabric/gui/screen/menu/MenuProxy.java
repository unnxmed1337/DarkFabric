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

import java.net.InetSocketAddress;
import java.net.Proxy;

public class MenuProxy extends Menu {

    private EditBox addressBox, portBox, passwordBox;

    private String status;

    public MenuProxy(Screen prevScreen) {
        super(prevScreen, "Proxy", "Here you can switch to socks4/5 proxies.");
        status = ChatColors.GRAY + "Idle...";
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.render(poseStack, i, j, f);
        GL11.glPushMatrix();
        addressBox.render(poseStack, i, j, f);
        portBox.render(poseStack, i, j, f);
        passwordBox.render(poseStack, i, j, f);
        assert minecraft != null;
        drawCenteredString(DefaultPoseStack.getMatrixStack(), minecraft.font, status,
                width / 2, height / 2 + 14, -1);
        GL11.glPopMatrix();
    }

    @Override
    public void tick() {
        addressBox.tick();
        portBox.tick();
        passwordBox.tick();
    }

    @Override
    public boolean keyPressed(int i, int j, int k) {
        if (i == GLFW.GLFW_KEY_ENTER) useProxy();
        if (getFocused() != this.addressBox
                && getFocused() != portBox
                && getFocused() != passwordBox || i != 257 && i != 335)
            return super.keyPressed(i, j, k);
        else return true;
    }

    public void useProxy() {
        try {
            AuthUtils.useProxy(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(addressBox.getValue(),
                    Integer.parseInt(portBox.getValue()))));
            status = String.format("%sLogged in! (%s)",
                    ChatColors.GREEN, Minecraft.getInstance().getUser().getName());
        } catch (NumberFormatException exception) {
            status = String.format("%sPort is invalid!", ChatColors.RED);
            exception.printStackTrace();
        } catch (Exception exception) {
            status = String.format("%sError with the proxy!", ChatColors.RED);
            exception.printStackTrace();
        }
    }

    @Override
    protected void init() {
        assert minecraft != null;
        minecraft.keyboardHandler.setSendRepeatsToGui(true);
        addressBox = new EditBox(Minecraft.getInstance().font, width / 2 - 100, height / 2 - 12 - 21,
                158, 20, new TextComponent("okay"));
        addressBox.setMaxLength(Integer.MAX_VALUE);
        addressBox.setFocus(true);
        addressBox.setValue("127.0.0.1");

        portBox = new EditBox(Minecraft.getInstance().font, width / 2 + 62, height / 2 - 12 - 21,
                38, 20, new TextComponent("okay v2"));
        portBox.setMaxLength(Integer.MAX_VALUE);
        portBox.setValue("9050");

        passwordBox = new EditBox(Minecraft.getInstance().font, width / 2 - 100, height / 2 + 12 - 21,
                200, 20, new TextComponent("okay v3"));
        passwordBox.setMaxLength(Integer.MAX_VALUE);

        addButton(new Button(width / 2 - 101, height / 2 + 24, 200, 20,
                new TextComponent("Connect"), button -> useProxy()));

        children.add(addressBox);
        children.add(portBox);
        children.add(passwordBox);
        super.init();
    }

}