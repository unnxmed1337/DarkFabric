package com.github.darkfabric.gui.menu;

import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.opengl.GL11;

public class Menu extends Screen {

    @Getter
    private final String caption, description;

    protected Menu(String caption, String description) {
        super(new TextComponent(caption));
        this.caption = caption;
        this.description = description;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        assert minecraft != null;
        float scale = 1.45f;
        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        drawCenteredString(poseStack, minecraft.font, title, (int) (width / scale) / 2,
                (int) (5 * scale), -1);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        drawCenteredString(poseStack, minecraft.font, description, width / 2, (int) (5 * scale + 17), -1);
        GL11.glPopMatrix();
        super.render(poseStack, i, j, f);
    }

    @Override
    protected void init() {
        super.init();
    }

}
