package uk.co.hexeption.darkforgereborn.util;


import java.awt.Color;
import java.util.Random;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;


public class ColourUtils {


    private static final Random random = new Random();

    public static int multiply(int c1, int c2) {

        return new RGB(c1).multiply(new RGB(c2)).get();
    }

    public static int alpha(int colour, float alpha) {

        return (colour & 0x00FFFFFF) | ((int) (MathHelper.clamp(alpha, 0, 1) * 255) << 24);
    }

    public static int alpha(int colour, double alpha) {

        return (colour & 0x00FFFFFF) | ((int) (MathHelper.clamp(alpha, 0, 1) * 255) << 24);
    }

    public static void glColor(float red, float green, float blue, float alpha) {
        GL11.glColor4f(red, green, blue, alpha);
    }

    public static void glColor(Color color) {

        GL11.glColor4f((float) color.getRed() / 255F, (float) color.getGreen() / 255F, (float) color.getBlue() / 255F, (float) color.getAlpha() / 255F);
    }

    public static void glColor(int color) {

        GL11.glColor4f((float) (color >> 16 & 255) / 255F, (float) (color >> 8 & 255) / 255F, (float) (color & 255) / 255F, (float) (color >> 24 & 255) / 255F);
    }

    public static Color getHSBColor(float hue, float saturation, float luminance) {

        return Color.getHSBColor(hue, saturation, luminance);
    }

    public static float[] getRGBA(int color) {
        float a = (color >> 24 & 255) / 255f;
        float r = (color >> 16 & 255) / 255f;
        float g = (color >> 8 & 255) / 255f;
        float b = (color & 255) / 255f;
        return new float[]{r, g, b, a};
    }

    public static Color getRandomColor(int saturationRandom, float luminance) {

        final float hue = random.nextFloat();
        final float saturation = (random.nextInt(saturationRandom) + (float) saturationRandom) / (float) saturationRandom + (float) saturationRandom;
        return getHSBColor(hue, saturation, luminance);
    }

    public static Color getRandomColor() {

        return getRandomColor(1000, 0.6f);
    }

    public static int hue(int hue) {

        return hs(hue, 100);
    }

    public static int hs(int hue, int sat) {

        return hsb(hue, sat, 100);
    }

    public static int hsb(int hue, int sat, int bri) {

        hue = (int) MathUtils.wrap(hue, 0, 360);
        sat = MathHelper.clamp(sat, 0, 100);
        bri = MathHelper.clamp(bri, 0, 100);
        return Color.HSBtoRGB((1 / 360f) * hue, (1 / 100f) * sat, (1 / 100f) * bri);
    }

    public static int rainbow(float offset) {

        return hue((int) ((System.nanoTime() / 20000000f) + offset));
    }

    public static int blend(int c1, int c2, float ratio) {

        return new RGB(c1).blend(new RGB(c2), ratio).get();
    }

    public static int blend(int c1, int c2, double ratio) {

        return blend(c1, c2, (float) ratio);
    }

    public static int blend(int c1, int c2) {

        return blend(c1, c2, 0.5f);
    }


}
