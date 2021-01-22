package com.github.darkfabric.util.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.BufferUploader;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.AABB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

/**
 * RenderUtils
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 05:23 pm
 */
public class RenderUtils {

    private static final AABB DEFAULT_AABB = new AABB(0, 0, 0, 1, 1, 1);

    public static void drawCircle(final int x2, final int y2, final float radius, final Color color) {
        int sections = 50;
        double dAngle = 2 * Math.PI / sections;
        float x, y;

        glPushAttrib(GL_ENABLE_BIT);

        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_LINE_SMOOTH);
        glBegin(GL_TRIANGLE_FAN);

        for (int i = 0; i < sections; i++) {
            x = (float) (radius * Math.sin((i * dAngle)));
            y = (float) (radius * Math.cos((i * dAngle)));

            glColor4f(color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, color.getAlpha() / 255F);
            glVertex2f(x + x2, y2 + y);
        }

        glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        glEnd();
        glPopAttrib();
    }

    public static void drawSolidBox() {
        drawSolidBox(DEFAULT_AABB);
    }

    public static void drawSolidBox(AABB bb) {
        glBegin(GL_QUADS);
        {
            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.minY, bb.maxZ);

            glVertex3d(bb.minX, bb.maxY, bb.minZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);

            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.minX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.minZ);

            glVertex3d(bb.maxX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);

            glVertex3d(bb.minX, bb.minY, bb.maxZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);

            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.minX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.minZ);
        }
        glEnd();
    }

    public static void drawOutlinedBox() {
        drawOutlinedBox(DEFAULT_AABB);
    }

    public static void drawOutlinedBox(AABB bb) {
        glBegin(GL_LINES);
        {
            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.minZ);

            glVertex3d(bb.maxX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.minY, bb.maxZ);

            glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.minY, bb.maxZ);

            glVertex3d(bb.minX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.minY, bb.minZ);

            glVertex3d(bb.minX, bb.minY, bb.minZ);
            glVertex3d(bb.minX, bb.maxY, bb.minZ);

            glVertex3d(bb.maxX, bb.minY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);

            glVertex3d(bb.maxX, bb.minY, bb.maxZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

            glVertex3d(bb.minX, bb.minY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);

            glVertex3d(bb.minX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.minZ);

            glVertex3d(bb.maxX, bb.maxY, bb.minZ);
            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);

            glVertex3d(bb.maxX, bb.maxY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.maxZ);

            glVertex3d(bb.minX, bb.maxY, bb.maxZ);
            glVertex3d(bb.minX, bb.maxY, bb.minZ);
        }
        glEnd();
    }

    public static AABB getBoundingBox(BlockPos pos) {
        assert Minecraft.getInstance().level != null;
        return Minecraft.getInstance().level.getBlockState(pos)
                .getCollisionShape(Minecraft.getInstance().level, pos)
                .move(pos.getX(), pos.getY(), pos.getZ()).bounds();
    }

    public static class Texture {
        public static void drawTextureAt(int x, int y, ResourceLocation location, int width, int height, boolean reverse) {
            Minecraft.getInstance().getTextureManager().bind(location);
            GlStateManager._enableBlend();
            drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (reverse ? -width : width), height);
            GlStateManager._disableBlend();
        }

        public static void drawTextureAt(double x, double y, ResourceLocation location, double width, double height, boolean reverse) {
            Minecraft.getInstance().getTextureManager().bind(location);
            GlStateManager._enableBlend();
            drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (reverse ? -width : width), height);
            GlStateManager._disableBlend();
        }

        public static void drawTextureAt(float x, float y, ResourceLocation location, float width, float height, boolean reverse) {
            Minecraft.getInstance().getTextureManager().bind(location);
            GlStateManager._enableBlend();
            drawModalRectWithCustomSizedTexture(x, y, 0.0f, 0.0f, width, height, (reverse ? -width : width), height);
            GlStateManager._disableBlend();
        }

        /**
         * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
         */
        public static void drawModalRectWithCustomSizedTexture(int x, int y, int u, int v, int width, int height, int textureWidth, int textureHeight) {
            int f = 1 / textureWidth;
            int f1 = 1 / textureHeight;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION_TEX);
            bufferBuilder.vertex(x, y + height, 0.0D).uv(u * f, (v + (float) height) * f1).endVertex();
            bufferBuilder.vertex(x + width, y + height, 0.0D).uv((u + (float) width) * f, (v + (float) height) * f1).endVertex();
            bufferBuilder.vertex(x + width, y, 0.0D).uv((u + (float) width) * f, v * f1).endVertex();
            bufferBuilder.vertex(x, y, 0.0D).uv(u * f, v * f1).endVertex();
            bufferBuilder.end();
            BufferUploader.end(bufferBuilder);
        }

        /**
         * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
         */
        public static void drawModalRectWithCustomSizedTexture(float x, float y, float u, float v, float width, float height, float textureWidth, float textureHeight) {
            float f = 1.f / textureWidth;
            float f1 = 1.f / textureHeight;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION_TEX);
            bufferBuilder.vertex(x, y + height, 0.0D).uv(u * f, (v + height) * f1).endVertex();
            bufferBuilder.vertex(x + width, y + height, 0.0D).uv((u + width) * f, (v + height) * f1).endVertex();
            bufferBuilder.vertex(x + width, y, 0.0D).uv((u + width) * f, v * f1).endVertex();
            bufferBuilder.vertex(x, y, 0.0D).uv(u * f, v * f1).endVertex();
            bufferBuilder.end();
            BufferUploader.end(bufferBuilder);
        }

        /**
         * Draws a textured rectangle at z = 0. Args: x, y, u, v, width, height, textureWidth, textureHeight
         */
        public static void drawModalRectWithCustomSizedTexture(double x, double y, double u, double v, double width, double height, double textureWidth, double textureHeight) {
            double f = 1.d / textureWidth;
            double f1 = 1.d / textureHeight;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION_TEX);
            bufferBuilder.vertex(x, y + height, 0.0D).uv((float) (u * f), (float) ((v + height) * f1)).endVertex();
            bufferBuilder.vertex(x + width, y + height, 0.0D).uv((float) ((u + width) * f),
                    (float) ((v + (float) height) * f1)).endVertex();
            bufferBuilder.vertex(x + width, y, 0.0D).uv((float) ((u + width) * f), (float) (v * f1)).endVertex();
            bufferBuilder.vertex(x, y, 0.0D).uv((float) (u * f), (float) (v * f1)).endVertex();
            bufferBuilder.end();
            BufferUploader.end(bufferBuilder);
        }
    }

    public static class Rectangle {
        public static void drawRoundedRect(int left, int top, int right, int bottom, float radius, Color color) {
            drawRect(left + (int) radius, top, right - (int) radius, bottom, color.hashCode());
            drawRect(left, top + (int) radius, right, bottom - (int) radius, color.hashCode());
            drawCircle(left + (int) radius, top + (int) radius, radius, color);
            drawCircle(left + (int) radius, bottom - (int) radius, radius, color);
            drawCircle(right - (int) radius, top + (int) radius, radius, color);
            drawCircle(right - (int) radius, bottom - (int) radius, radius, color);
        }

        /**
         * Draws a solid color rectangle with the specified coordinates and color (ARGB
         * format). Args: x1, y1, x2, y2, color
         */
        public static void drawRect(int left, int top, int right, int bottom, int color) {
            if (left < right) {
                int i = left;
                left = right;
                right = i;
            }

            if (top < bottom) {
                int j = top;
                top = bottom;
                bottom = j;
            }

            float f3 = (float) (color >> 24 & 255) / 255.0F;
            float f = (float) (color >> 16 & 255) / 255.0F;
            float f1 = (float) (color >> 8 & 255) / 255.0F;
            float f2 = (float) (color & 255) / 255.0F;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            GlStateManager._enableBlend();
            GlStateManager._disableTexture();
            GlStateManager._blendFuncSeparate(770, 771, 1, 0);
            GlStateManager._color4f(f, f1, f2, f3);
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION);
            bufferBuilder.vertex(left, bottom, 0.0D).endVertex();
            bufferBuilder.vertex(right, bottom, 0.0D).endVertex();
            bufferBuilder.vertex(right, top, 0.0D).endVertex();
            bufferBuilder.vertex(left, top, 0.0D).endVertex();
            bufferBuilder.end();
            BufferUploader.end(bufferBuilder);
            GlStateManager._enableTexture();
            GlStateManager._disableBlend();
        }

        /**
         * Draws a solid color rectangle with the specified coordinates and color (ARGB
         * format). Args: x1, y1, x2, y2, color
         */
        public static void drawRect(float left, float top, float right, float bottom, int color) {
            if (left < right) {
                float i = left;
                left = right;
                right = i;
            }

            if (top < bottom) {
                float j = top;
                top = bottom;
                bottom = j;
            }

            float f3 = (float) (color >> 24 & 255) / 255.0F;
            float f = (float) (color >> 16 & 255) / 255.0F;
            float f1 = (float) (color >> 8 & 255) / 255.0F;
            float f2 = (float) (color & 255) / 255.0F;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            GlStateManager._enableBlend();
            GlStateManager._disableTexture();
            GlStateManager._blendFuncSeparate(770, 771, 1, 0);
            GlStateManager._color4f(f, f1, f2, f3);
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION);
            bufferBuilder.vertex(left, bottom, 0.0D).endVertex();
            bufferBuilder.vertex(right, bottom, 0.0D).endVertex();
            bufferBuilder.vertex(right, top, 0.0D).endVertex();
            bufferBuilder.vertex(left, top, 0.0D).endVertex();
            bufferBuilder.end();
            BufferUploader.end(bufferBuilder);
            GlStateManager._enableTexture();
            GlStateManager._disableBlend();
        }

        /**
         * Draws a solid color rectangle with the specified coordinates and color (ARGB
         * format). Args: x1, y1, x2, y2, color
         */
        public static void drawRect(double left, double top, double right, double bottom, int color) {
            if (left < right) {
                double i = left;
                left = right;
                right = i;
            }

            if (top < bottom) {
                double j = top;
                top = bottom;
                bottom = j;
            }

            float f3 = (float) (color >> 24 & 255) / 255.0F;
            float f = (float) (color >> 16 & 255) / 255.0F;
            float f1 = (float) (color >> 8 & 255) / 255.0F;
            float f2 = (float) (color & 255) / 255.0F;
            Tesselator tesselator = Tesselator.getInstance();
            BufferBuilder bufferBuilder = tesselator.getBuilder();
            GlStateManager._enableBlend();
            GlStateManager._disableTexture();
            GlStateManager._blendFuncSeparate(770, 771, 1, 0);
            GlStateManager._color4f(f, f1, f2, f3);
            bufferBuilder.begin(7, DefaultVertexFormat.POSITION);
            bufferBuilder.vertex(left, bottom, 0.0D).endVertex();
            bufferBuilder.vertex(right, bottom, 0.0D).endVertex();
            bufferBuilder.vertex(right, top, 0.0D).endVertex();
            bufferBuilder.vertex(left, top, 0.0D).endVertex();
            bufferBuilder.end();
            BufferUploader.end(bufferBuilder);
            GlStateManager._enableTexture();
            GlStateManager._disableBlend();
        }

        public static void drawBorderedRect(double left, double top, double right, double bottom, int insideColor,
                                            int borderColor, int borderSize, BorderedRectType rectType) {
            if (borderSize <= -1)
                borderSize = -1;
            if (rectType == BorderedRectType.INSIDE) {
                drawRect(left, top, right, bottom, insideColor); // rectangle inside
                drawRect(left, top, left + borderSize, bottom, borderColor); // line left
                drawRect(left, top, right, top + borderSize, borderColor); // line top
                drawRect(right, top, right - borderSize, bottom, borderColor); // line right
                drawRect(left, bottom, right, bottom - borderSize, borderColor); // line bottom
            } else {
                drawRect(left + 1, top + 1, right - 1, bottom - 1, insideColor); // rectangle inside
                drawRect(left - borderSize, top, left + 1, bottom, borderColor); // line left
                drawRect(right - 1, top, right + borderSize, bottom, borderColor); // line right
                drawRect(left - borderSize, top - borderSize, right + borderSize, top + 1, borderColor); // line top
                drawRect(left - borderSize, bottom - 1, right + borderSize, bottom + borderSize, borderColor); // line bottom
            }
        }

        public enum BorderedRectType {
            INSIDE, OUTSIDE
        }

    }

    public static class Schere {
        private static final Window SCALED_RESOLUTION = Minecraft.getInstance().getWindow();

        public static void startSchere(int startX, int startY, int endX, int endY) {
            int width = endX - startX;
            int height = endY - startY;
            assert Minecraft.getInstance().screen != null;
            int bottomY = Minecraft.getInstance().screen.height - endY;
            double factor = SCALED_RESOLUTION.getGuiScale();

            int scissorX = (int) (startX * factor);
            int scissorY = (int) (bottomY * factor);
            int scissorWidth = (int) (width * factor);
            int scissorHeight = (int) (height * factor);
            GL11.glScissor(scissorX, scissorY, scissorWidth, scissorHeight);
            GL11.glEnable(GL_SCISSOR_TEST);
        }

        public static void stopSchere() {
            GL11.glDisable(GL_SCISSOR_TEST);
        }
    }

}