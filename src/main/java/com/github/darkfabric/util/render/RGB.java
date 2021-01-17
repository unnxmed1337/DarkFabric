package com.github.darkfabric.util.render;

import net.minecraft.util.Mth;

public class RGB {

    public float r, g, b, a;

    public RGB(float r, float g, float b, float a) {
        set(r, g, b, a);
    }

    public RGB(int r, int g, int b, int a) {
        set(r / 255f, g / 255f, b / 255f, a / 255f);
    }

    public RGB(float[] rgba) {
        this(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    public RGB(int[] rgba) {
        this(rgba[0] / 255f, rgba[1] / 255f, rgba[2] / 255f, rgba[3] / 255f);
    }

    public RGB(int color) {
        this((color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f,
                (color >> 24 & 255) / 255f);
    }

    public void setR(float r) {
        this.r = Mth.clamp(r, 0f, 1f);
    }

    public void setG(float g) {
        this.g = Mth.clamp(g, 0f, 1f);
    }

    public void setB(float b) {
        this.b = Mth.clamp(b, 0f, 1f);
    }

    public RGB setA(float a) {
        this.a = Mth.clamp(a, 0f, 1f);
        return this;
    }

    public void set(float r, float g, float b, float a) {
        setR(r);
        setG(g);
        setB(b);
        setA(a);
    }

    public void set(int color) {
        set((color >> 16 & 255) / 255f, (color >> 8 & 255) / 255f, (color & 255) / 255f,
                (color >> 24 & 255) / 255f);
    }

    public int get() {
        return ((int) (r * 255) << 16) | ((int) (g * 255) << 8) | ((int) (b * 255)) | ((int) (a * 255) << 24);
    }

    public float[] getFloats() {
        return new float[]{r, g, b, a};
    }

    public RGB blend(RGB rgb, float ratio) {
        ratio = Mth.clamp(ratio, 0, 1);
        float iRatio = 1 - ratio;
        return new RGB((r * iRatio) + (rgb.r * ratio), (g * iRatio) + (rgb.g * ratio),
                (b * iRatio) + (rgb.b * ratio), (a * iRatio) + (rgb.a * ratio));
    }

    public RGB multiply(RGB rgb) {
        return new RGB(r * rgb.r, g * rgb.g, b * rgb.b, a * rgb.a);
    }
}
