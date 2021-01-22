package com.github.darkfabric.util.render.texture;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

@Environment(EnvType.CLIENT)
public class GLAllocationUtils {

    public static synchronized ByteBuffer allocateByteBuffer(int size) {
        return ByteBuffer.allocateDirect(size).order(ByteOrder.nativeOrder());
    }

    public static FloatBuffer allocateFloatBuffer(int size) {
        return allocateByteBuffer(size << 2).asFloatBuffer();
    }

    public static IntBuffer allocateIntBuffer(int size) {
        return allocateByteBuffer(size << 2).asIntBuffer();
    }

}