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

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;


public class Texture {

    private final ResourceLocation texture;

    public Texture(String textureURL) {
        texture = new ResourceLocation(textureURL);
        Minecraft.getInstance().getTextureManager().bind(texture);
    }

    public void render(float x, float y, float width, float height) {
        render(x, y, width, height, 0F, 0F, 1F, 1F);
    }

    public void render(float x, float y, float width, float height, float u, float v, float t, float s) {
        bindTexture();
        Tesselator tesselator = Tesselator.getInstance();
        BufferBuilder renderer = tesselator.getBuilder();
        renderer.begin(GL_TRIANGLES, DefaultVertexFormat.POSITION_TEX);
        renderer.vertex(x + width, y, 0F).uv(t, v).endVertex();
        renderer.vertex(x, y, 0F).uv(u, v).endVertex();
        renderer.vertex(x, y + height, 0F).uv(u, s).endVertex();
        renderer.vertex(x, y + height, 0F).uv(u, s).endVertex();
        renderer.vertex(x + width, y + height, 0F).uv(t, s).endVertex();
        renderer.vertex(x + width, y, 0F).uv(t, v).endVertex();
        tesselator.end();

    }

    private void bindTexture() {
        Minecraft.getInstance().getTextureManager().bind(texture);
        GlStateManager._enableTexture();
    }

    @Override
    public String toString() {
        return texture.getPath();
    }
}