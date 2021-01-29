package com.github.darkfabric.gui.menu;

import com.github.darkfabric.util.render.RenderUtils;
import com.mojang.blaze3d.vertex.PoseStack;
import lombok.Getter;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.TextComponent;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class Menu extends Screen {

    public final Screen previousScreen;

    @Getter
    private final String caption, description;

    protected Menu(Screen previousScreen, String caption, String description) {
        super(new TextComponent(caption));
        this.previousScreen = previousScreen;
        this.caption = caption;
        this.description = description;
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        assert minecraft != null;
        float scale = 1.45f;
        GL11.glPushMatrix();
        Color purpleColor = new Color(135, 0, 135, 65);
        RenderUtils.Rectangle.drawRect(0, 0, width, (int) (5 * scale + 17) +
                font.lineHeight + 8, purpleColor.hashCode()); //fill
        RenderUtils.Rectangle.drawRect(0, (int) (5 * scale + 17)
                + font.lineHeight + 5, width, (int) (5 * scale + 17)
                + font.lineHeight + 8, new Color(purpleColor.getRed(), purpleColor.getGreen(), purpleColor.getBlue(),
                255).hashCode()); //line
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glScalef(scale, scale, scale);
        drawCenteredString(poseStack, minecraft.font, getCaption(), (int) (width / scale) / 2,
                (int) (5 * scale), purpleColor.brighter().brighter().hashCode());
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        drawCenteredString(poseStack, minecraft.font, getDescription(), width / 2, (int) (5 * scale + 17),
                Color.gray.brighter().hashCode());
        GL11.glPopMatrix();
        super.render(poseStack, i, j, f);
    }

    @Override
    protected void init() {
        addButton(new Button(width / 2 - 50, height - 25, 100, 20,
                CommonComponents.GUI_BACK, button -> {
            assert minecraft != null;
            minecraft.setScreen(previousScreen);
        }));
        super.init();
    }

}
