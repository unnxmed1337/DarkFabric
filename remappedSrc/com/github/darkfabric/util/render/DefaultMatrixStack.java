package com.github.darkfabric.util.render;

import net.minecraft.client.util.math.MatrixStack;

public class DefaultMatrixStack {
    private static final MatrixStack MATRIX_STACK = new MatrixStack();

    public static MatrixStack getMatrixStack() {
        return MATRIX_STACK;
    }
}