package com.github.darkforge.util.render;

import com.mojang.blaze3d.matrix.MatrixStack;

public class DefaultMatrixStack {

    private static final MatrixStack MATRIX_STACK = new MatrixStack();

    public static MatrixStack getMatrixStack() {
        return MATRIX_STACK;
    }

}
