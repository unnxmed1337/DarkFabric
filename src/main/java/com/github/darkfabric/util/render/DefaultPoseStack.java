package com.github.darkfabric.util.render;

import com.mojang.blaze3d.vertex.PoseStack;

public class DefaultPoseStack {
    private static final PoseStack MATRIX_STACK = new PoseStack();
    public static PoseStack getMatrixStack() {
        return MATRIX_STACK;
    }
}