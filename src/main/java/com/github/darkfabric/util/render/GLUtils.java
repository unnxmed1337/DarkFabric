/*******************************************************************************
 *     DarkForge a Forge Hacked Client
 *     Copyright (C) 2017  Hexeption (Keir Davis)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <http://www.gnu.org/licenses/>.
 ******************************************************************************/

package com.github.darkfabric.util.render;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

/**
 * Created by halalaboos.
 */
public final class GLUtils {
    private static final Random random = new Random();

    private static final Tesselator tesselator = Tesselator.getInstance();

    public static List<Integer> vBos = new ArrayList<>(), textures
            = Collections.synchronizedList(Lists.newCopyOnWriteArrayList());

    /**
     * Checks if the mouse is hovering over a given item
     */
    public static boolean isHovered(int x, int y, int width, int height, int mouseX, int mouseY) {
        return (mouseX >= x) && (mouseX <= x + width) && (mouseY >= y) && (mouseY < y + height);
    }

    public static int genVBO() {
        int id = GL15.glGenBuffers();
        vBos.add(id);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, id);
        return id;
    }

    public static int getTexture() {
        int textureID = glGenTextures();
        textures.add(textureID);
        return textureID;
    }

    /**
     * @param filter determines how the texture will interpolate when scaling up / down. <br> GL_LINEAR
     *               - smoothest <br> GL_NEAREST - most accurate <br>
     * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled.
     *               <br> GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
     */
    public static int applyTexture(int texId, File file, int filter, int wrap) throws IOException {
        applyTexture(texId, ImageIO.read(file), filter, wrap);
        return texId;
    }

    /**
     * @param filter determines how the texture will interpolate when scaling up / down. <br> GL_LINEAR
     *               - smoothest <br> GL_NEAREST - most accurate <br>
     * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled.
     *               <br> GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
     */
    public static void applyTexture(int texId, BufferedImage image, int filter, int wrap) {
        int[] pixels = new int[image.getWidth() * image.getHeight()];
        image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());

        ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);

        for (int y = 0; y < image.getHeight(); y++)
            for (int x = 0; x < image.getWidth(); x++) {
                int pixel = pixels[y * image.getWidth() + x];
                buffer.put((byte) ((pixel >> 16) & 0xFF));
                buffer.put((byte) ((pixel >> 8) & 0xFF));
                buffer.put((byte) (pixel & 0xFF));
                buffer.put((byte) ((pixel >> 24) & 0xFF));
            }

        buffer.flip();
        applyTexture(texId, image.getWidth(), image.getHeight(), buffer, filter, wrap);
    }

    /**
     * @param filter determines how the texture will interpolate when scaling up / down. <br> GL_LINEAR
     *               - smoothest <br> GL_NEAREST - most accurate <br>
     * @param wrap   determines how the UV coordinates outside of the 0.0F ~ 1.0F range will be handled.
     *               <br> GL_CLAMP_TO_EDGE - samples edge color <br> GL_REPEAT - repeats the texture <br>
     */
    public static void applyTexture(int texId, int width, int height, ByteBuffer pixels, int filter, int wrap) {
        glBindTexture(GL_TEXTURE_2D, texId);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, filter);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, wrap);
        glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, wrap);
        glPixelStorei(GL_UNPACK_ALIGNMENT, 1);
        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA8, width, height, 0, GL_RGBA, GL_UNSIGNED_BYTE, pixels);
        glBindTexture(GL_TEXTURE_2D, 0);
    }

    /**
     * Cleans ups the arrays on close
     */
    public static void cleanup() {
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        glBindTexture(GL_TEXTURE_2D, 0);

        for (int vbo : vBos)
            GL15.glDeleteBuffers(vbo);
        for (int texture : textures)
            glDeleteTextures(texture);
    }

    /**
     * Rect
     */
    public static void drawBorderRect(float x, float y, float x1, float y1, float borderSize) {
        drawBorder(borderSize, x, y, x1, y1);
        drawRect(x, y, x1, y1);
    }

    public static void drawBorder(float size, float x, float y, float x1, float y1) {
        glLineWidth(size);
        GlStateManager._disableTexture();
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        BufferBuilder vertexBuffer = tesselator.getBuilder();
        vertexBuffer.begin(GL_LINE_LOOP, DefaultVertexFormat.POSITION);
        vertexBuffer.vertex(x, y, 0F).endVertex();
        vertexBuffer.vertex(x, y1, 0F).endVertex();
        vertexBuffer.vertex(x1, y1, 0F).endVertex();
        vertexBuffer.vertex(x1, y, 0F).endVertex();
        tesselator.end();
        GlStateManager._enableTexture();
    }

    public static void drawRect(float x, float y, float w, float h) {
        GlStateManager._disableTexture();
        GlStateManager._enableBlend();
        GlStateManager._blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        BufferBuilder vertexBuffer = tesselator.getBuilder();
        vertexBuffer.begin(GL_QUADS, DefaultVertexFormat.POSITION);
        vertexBuffer.vertex(x, h, 0F).endVertex();
        vertexBuffer.vertex(w, h, 0F).endVertex();
        vertexBuffer.vertex(w, y, 0F).endVertex();
        vertexBuffer.vertex(x, y, 0F).endVertex();
        tesselator.end();
        GlStateManager._enableTexture();
    }

    public static void drawGradientRect(int x, int y, int w, int h, int startColor, int endColor) {
        float f = (float) (startColor >> 24 & 255) / 255.0F;
        float f1 = (float) (startColor >> 16 & 255) / 255.0F;
        float f2 = (float) (startColor >> 8 & 255) / 255.0F;
        float f3 = (float) (startColor & 255) / 255.0F;
        float f4 = (float) (endColor >> 24 & 255) / 255.0F;
        float f5 = (float) (endColor >> 16 & 255) / 255.0F;
        float f6 = (float) (endColor >> 8 & 255) / 255.0F;
        float f7 = (float) (endColor & 255) / 255.0F;
        GlStateManager._disableTexture();
        GlStateManager._enableBlend();
        GlStateManager._disableAlphaTest();
        GlStateManager._blendFuncSeparate(GL_SRC_ALPHA,
                GL_ONE_MINUS_SRC_ALPHA,
                GL_ONE, GL_ZERO);
        GlStateManager._shadeModel(7425);
        BufferBuilder vertexBuffer = tesselator.getBuilder();
        vertexBuffer.begin(7, DefaultVertexFormat.POSITION_COLOR);
        vertexBuffer.vertex((double) x + w, y, 0).color(f1, f2, f3, f).endVertex();
        vertexBuffer.vertex(x, y, 0).color(f1, f2, f3, f).endVertex();
        vertexBuffer.vertex(x, (double) y + h, 0).color(f5, f6, f7, f4).endVertex();
        vertexBuffer.vertex((double) x + w, (double) y + h, 0).color(f5, f6, f7, f4).endVertex();
        tesselator.end();
        GlStateManager._shadeModel(7424);
        GlStateManager._disableBlend();
        GlStateManager._enableAlphaTest();
        GlStateManager._enableTexture();
    }

    public static void enableGL2D() {
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(true);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_NICEST);
    }

    public static void disableGL2D() {
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glHint(GL11.GL_POLYGON_SMOOTH_HINT, GL11.GL_DONT_CARE);
    }

    /**
     * Colors
     */
    public static void glColor(float red, float green, float blue, float alpha) {
        GlStateManager._color4f(red, green, blue, alpha);
    }

    public static void glColor(Color color) {
        GlStateManager._color4f((float) color.getRed() / 255F,
                (float) color.getGreen() / 255F,
                (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
    }

    public static void glColor(int color) {
        GlStateManager._color4f((float) (color >> 16 & 255) / 255F,
                (float) (color >> 8 & 255) / 255F,
                (float) (color & 255) / 255F, (float) (color >> 24 & 255) / 255F);
    }

    public static Color getHSBColor(float hue, float sturation, float luminance) {
        return Color.getHSBColor(hue, sturation, luminance);
    }

    public static Color getRandomColor(int saturationRandom, float luminance) {
        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(saturationRandom)
                + (float) saturationRandom) / (float) saturationRandom + (float) saturationRandom;
        return getHSBColor(hue, saturation, luminance);
    }

    public static Color getRandomColor() {
        return getRandomColor(1000, 0.6f);
    }

}