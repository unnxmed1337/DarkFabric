package com.github.darkfabric.injection.mixins;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.util.render.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import io.github.prospector.modmenu.gui.ModsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractSelectionList;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.components.events.AbstractContainerEventHandler;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.awt.*;

@Mixin(AbstractSelectionList.class)
public abstract class MixinAbstractSelectionList<E extends AbstractSelectionList.Entry<E>>
        extends AbstractContainerEventHandler implements Widget {

    @Shadow
    protected int x0, y0, x1, y1;

    @Shadow
    protected int height, width;

    @Shadow
    private boolean renderBackground, renderTopAndBottom, renderHeader;

    @Shadow
    protected abstract void renderList(PoseStack poseStack, int i, int j, int k, int l, float f);

    @Shadow
    protected abstract void renderDecorations(PoseStack poseStack, int i, int j);

    @Shadow
    public abstract int getRowLeft();

    @Shadow
    public abstract double getScrollAmount();

    @Shadow
    protected abstract void renderHeader(PoseStack poseStack, int i, int j, Tesselator tesselator);

    @Shadow
    public abstract int getMaxScroll();

    @Shadow
    protected abstract int getScrollbarPosition();

    @Shadow
    protected abstract int getMaxPosition();

    @Inject(method = "render", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/components/AbstractSelectionList" +
                    ";renderBackground(Lcom/mojang/blaze3d/vertex/PoseStack;)V",
            shift = At.Shift.BEFORE), cancellable = true)
    public void render(PoseStack poseStack, int i, int j, float f, CallbackInfo ci) {
        int k = getScrollbarPosition();
        int l = k + 6;

        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder bufferBuilder = tesselator.getBuilder();

        if (renderBackground)
            if (Minecraft.getInstance().level != null)
                fillGradient(poseStack, 0, 0, this.width, this.height, -1072689136, -804253680);
            else
                DarkFabric.getInstance().renderShader();

        Screen screen = Minecraft.getInstance().screen;
        boolean flag = screen instanceof PackSelectionScreen;
        boolean flag1 = !(screen instanceof ModsScreen);

        Color purpleColor = new Color(135, 0, 135, 65);
        Color darkerPurpleColor = new Color(purpleColor.getRed(), purpleColor.getGreen(),
                purpleColor.getBlue(), 65).darker();
        if (renderTopAndBottom && flag1) {
            RenderUtils.Rectangle.drawRect(flag ? 0 : x0, 0,
                    flag ? screen.width : x1, y0, purpleColor.hashCode()); // fill
            RenderUtils.Rectangle.drawRect(flag ? 0 : x0, y0 - 2, flag ? screen.width : x1, y0,
                    new Color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).hashCode()); // line

            RenderUtils.Rectangle.drawRect(flag ? 0 : x0, y1,
                    flag ? screen.width : x1, height, purpleColor.hashCode()); // fill
            RenderUtils.Rectangle.drawRect(flag ? 0 : x0, y1, flag ? screen.width : x1, y1 + 2,
                    new Color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).hashCode()); // line
        }

        int m = getRowLeft();
        int n = y0 + 4 - (int) getScrollAmount();
        if (renderHeader)
            renderHeader(poseStack, m, n, tesselator);

        renderList(poseStack, m, n, i, j, f);

        int q = getMaxScroll();
        if (q > 0) {
            RenderSystem.disableTexture();
            int r = (int) ((float) ((this.y1 - this.y0) * (this.y1 - this.y0)) / (float) getMaxPosition());
            r = Mth.clamp(r, 32, this.y1 - this.y0 - 8);
            int s = (int) this.getScrollAmount() * (this.y1 - this.y0 - r) / q + this.y0;

            if (s < this.y0)
                s = this.y0;

            bufferBuilder.begin(7, DefaultVertexFormat.POSITION_TEX_COLOR);
            bufferBuilder.vertex(k, s + r, 0.0D).uv(0.0F, 1.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(l, s + r, 0.0D).uv(1.0F, 1.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(l, s, 0.0D).uv(1.0F, 0.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(k, s, 0.0D).uv(0.0F, 0.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(k, s + r - 1, 0.0D).uv(0.0F, 1.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(l - 1, s + r - 1, 0.0D).uv(1.0F, 1.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(l - 1, s, 0.0D).uv(1.0F, 0.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            bufferBuilder.vertex(k, s, 0.0D).uv(0.0F, 0.0F)
                    .color(darkerPurpleColor.getRed(), darkerPurpleColor.getGreen(), darkerPurpleColor.getBlue(),
                            255).endVertex();
            tesselator.end();
        }

        ci.cancel();
    }

}