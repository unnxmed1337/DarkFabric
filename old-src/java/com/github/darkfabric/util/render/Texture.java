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
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferBuilder;
import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.util.Identifier;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;


public class Texture {

    private final Identifier texture;

    public Texture(String textureURL) {

        texture = new Identifier(textureURL);
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
    }

    public void render(float x, float y, float width, float height) {

        render(x, y, width, height, 0F, 0F, 1F, 1F);
    }

    public void render(float x, float y, float width, float height, float u, float v, float t, float s) {
        bindTexture();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder renderer = tessellator.getBuffer();
        renderer.begin(GL_TRIANGLES, VertexFormats.POSITION_TEXTURE);
        renderer.vertex(x + width, y, 0F).texture(t, v).next();
        renderer.vertex(x, y, 0F).texture(u, v).next();
        renderer.vertex(x, y + height, 0F).texture(u, s).next();
        renderer.vertex(x, y + height, 0F).texture(u, s).next();
        renderer.vertex(x + width, y + height, 0F).texture(t, s).next();
        renderer.vertex(x + width, y, 0F).texture(t, v).next();
        tessellator.draw();

    }

    private void bindTexture() {
        MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
        GlStateManager.enableTexture();
    }

    @Override
    public String toString() {
        return texture.getPath();
    }
}