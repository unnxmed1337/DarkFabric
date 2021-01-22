package com.github.darkfabric.gui;

import com.github.darkfabric.util.render.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class FancyButtonWidget extends Button {

    private final Color color;
    private final String title, description;

    private final ResourceLocation icon;

    public FancyButtonWidget(int x, int y, int width, int height, OnPress onPress, Color color,
                             String title, String description, ResourceLocation icon) {
        super(x, y, width, height, new TextComponent(title), onPress);
        this.color = color;
        this.title = title;
        this.description = description;
        this.icon = icon;
    }

    @Override
    public boolean mouseDragged(double d, double e, int i, double f, double g) {
        return super.mouseDragged(d, e, i, f, g);
    }

    @Override
    public void renderButton(PoseStack poseStack, int i, int j, float f) {
        int offset = height;
        GL11.glPushMatrix();
        RenderSystem.color4f(1.0F, 1.0F, 1.0F, this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();

        fill(poseStack, x + offset, y, x + width, y + height, color.hashCode());
        fill(poseStack, x, y, x + offset, y + height, color.darker().hashCode());

        if (isHovered) {
            RenderUtils.Rectangle.drawBorderedRect(x, y, x + width, y + height, 0,
                    -1, 1, RenderUtils.Rectangle.BorderedRectType.INSIDE);
            RenderUtils.Rectangle.drawBorderedRect(x, y, x + offset + 1, y + height, 0,
                    -1, 1, RenderUtils.Rectangle.BorderedRectType.INSIDE);
        }
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        Minecraft.getInstance().font.draw(poseStack, title, x + offset + 3,
                y + height / 2.f - Minecraft.getInstance().font.lineHeight + 1, color.darker().hashCode()); // shadow
        Minecraft.getInstance().font.draw(poseStack, title, x + offset + 2,
                y + height / 2.f - Minecraft.getInstance().font.lineHeight, -1); // normal title

        Minecraft.getInstance().font.draw(poseStack, description, x + offset + 3,
                y + height / 2.f + 2, color.darker().hashCode()); // shadow
        Minecraft.getInstance().font.draw(poseStack, description, x + offset + 2,
                y + height / 2.f + 1, -1); // normal desc.
        GL11.glPopMatrix();

        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_POLYGON_SMOOTH);
        RenderUtils.Texture.drawTextureAt(x, y, icon, offset, offset, false);
        GL11.glPopMatrix();
    }

}