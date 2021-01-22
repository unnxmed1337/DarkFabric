package com.github.darkfabric.gui.menu.entry;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;

public class MenuEntry extends ObjectSelectionList.Entry<MenuEntry> {

    private final Screen screen;
    private final Minecraft minecraft = Minecraft.getInstance();
    private final String name;

    protected MenuEntry(Screen screen, String name) {
        this.screen = screen;
        this.name = name;
    }

    @Override
    public void render(PoseStack poseStack, int index, int y, int x,
                       int rowWidth, int rowHeight, int mouseX, int mouseY,
                       boolean isSelected, float delta) {
        this.minecraft.font.draw(poseStack, name, x + (rowWidth / 2.f)
                - (minecraft.font.width(name) / 2.f), y + 1, -1);
    }

}