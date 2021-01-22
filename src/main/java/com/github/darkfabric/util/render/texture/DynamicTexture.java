package com.github.darkfabric.util.render.texture;

import net.minecraft.client.renderer.texture.AbstractTexture;
import net.minecraft.server.packs.resources.ResourceManager;

import java.awt.image.BufferedImage;

public class DynamicTexture extends AbstractTexture {
    private final int[] dynamicTextureData;

    /**
     * width of this icon in pixels
     */
    private final int width;

    /**
     * height of this icon in pixels
     */
    private final int height;

    public DynamicTexture(BufferedImage bufferedImage) {
        this(bufferedImage.getWidth(), bufferedImage.getHeight());
        bufferedImage.getRGB(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight(), this.dynamicTextureData, 0, bufferedImage.getWidth());
        this.updateDynamicTexture();
    }

    public DynamicTexture(int textureWidth, int textureHeight) {
        this.width = textureWidth;
        this.height = textureHeight;
        this.dynamicTextureData = new int[textureWidth * textureHeight];
        TextureUtil.allocateTexture(this.getId(), textureWidth, textureHeight);
    }

    public void updateDynamicTexture() {
        //TextureUtil.uploadTexture(this.getGlTextureId(), this.dynamicTextureData, this.width, this.height);
        TextureUtil.uploadTexture(this.getId(), getTextureData(), width, height);
    }

    public int[] getTextureData() {
        return this.dynamicTextureData;
    }

    @Override
    public void load(ResourceManager manager) {
    }
}
