package com.github.darkfabric.gui.menu.entry;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.vertex.PoseStack;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.TitleScreen;

import java.util.Collections;
import java.util.List;

public class MenuSelectionList extends ObjectSelectionList<MenuEntry> {

    private final Screen screen;

    private final List<MenuEntry> entries = Collections.synchronizedList(Lists.newLinkedList());

    public MenuSelectionList(Screen screen, Minecraft minecraft, int i, int j, int k, int l, int m) {
        super(minecraft, i, j, k, l, m);
        this.screen = screen;
        for (int i1 = 0; i1 <= 100; i1++)
            addEntry(new MenuEntry(new TitleScreen(), "wtf"));
    }

    protected int addEntry(MenuEntry entry) {
        children().add(entry);
        return 0;
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        return super.mouseClicked(d, e, i);
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        super.render(poseStack, i, j, f);
    }

    @Environment(EnvType.CLIENT)
    public abstract static class Entry extends ObjectSelectionList.Entry<MenuEntry> {
        public Entry() {
        }
    }

}