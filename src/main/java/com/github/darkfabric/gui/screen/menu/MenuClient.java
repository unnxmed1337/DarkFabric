package com.github.darkfabric.gui.screen.menu;

import com.github.darkfabric.DarkFabric;
import com.github.darkfabric.gui.menu.Menu;
import com.github.darkfabric.util.render.RenderUtils;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.math.Matrix4f;
import io.github.prospector.modmenu.mixin.EntryListWidgetAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.ObjectSelectionList;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Objects;

public class MenuClient extends Menu {

    private ScreenList screenList;
    private ScreenEntry selected;

    public MenuClient(Screen previousScreen) {
        super(previousScreen, "Client Menu", "Make everything you want to.");
    }

    @Override
    public void render(PoseStack poseStack, int i, int j, float f) {
        screenList.render(poseStack, i, j, f);
        GL11.glPushMatrix();
        float scale = 1.45f;
        Color purpleColor = new Color(135, 0, 135, 65);
        GL11.glScalef(scale, scale, scale);
        assert minecraft != null;
        drawCenteredString(poseStack, minecraft.font, getCaption(), (int) (width / scale) / 2,
                (int) (5 * scale), purpleColor.brighter().brighter().hashCode());
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        drawCenteredString(poseStack, minecraft.font, getDescription(), width / 2, (int) (5 * scale + 17),
                Color.gray.brighter().hashCode());
        GL11.glPopMatrix();
        for (AbstractWidget button : this.buttons)
            button.render(poseStack, i, j, f);
    }

    @Override
    public boolean mouseClicked(double d, double e, int i) {
        screenList.mouseClicked(d, e, i);
        return super.mouseClicked(d, e, i);
    }

    @Override
    protected void init() {
        assert Minecraft.getInstance().screen != null;
        this.screenList = new ScreenList(this, Minecraft.getInstance(),
                Minecraft.getInstance().screen.width, this.height, 20 + 19,
                this.height - 36, 20);
        screenList.children().clear();
        addEntries();
        this.screenList.setLeftPos(0);
        this.children.add(this.screenList);
        super.init();
    }

    public void addEntries() {
        screenList.addEntry(new ScreenEntry("Login", screenList,
                new ResourceLocation(DarkFabric.getInstance().getName().toLowerCase(),
                        "icons/darkfabric-16.png"), () -> Minecraft.getInstance()
                .setScreen(new MenuLogin(this))));
        screenList.addEntry(new ScreenEntry("Proxy", screenList,
                new ResourceLocation(DarkFabric.getInstance().getName().toLowerCase(),
                        "icons/darkfabric-16.png"), () -> Minecraft.getInstance()
                .setScreen(new MenuProxy(this))));
    }

    static class ScreenEntry extends ObjectSelectionList.Entry<ScreenEntry> implements IAction {
        protected final ScreenList screenList;
        private final String name;
        private final IAction action;
        private final ResourceLocation icon;

        public ScreenEntry(String name, ScreenList screenList, ResourceLocation icon, IAction action) {
            this.name = name;
            this.screenList = screenList;
            this.icon = icon;
            this.action = action;
        }

        public boolean mouseClicked(double v, double v1, int i) {
            execute();
            return true;
        }

        @Override
        public void render(PoseStack poseStack, int index, int y, int x,
                           int rowWidth, int rowHeight, int mouseX, int mouseY,
                           boolean isSelected, float delta) {
            GL11.glPushMatrix();
            RenderUtils.Rectangle.drawBorderedRect(x, y, x + rowWidth, y + rowHeight,
                    Integer.MIN_VALUE, new Color(170, 0, 170).hashCode(),
                    1, RenderUtils.Rectangle.BorderedRectType.INSIDE);
            Minecraft.getInstance().font.draw(poseStack, name, x + (rowWidth / 2.f)
                    - (Minecraft.getInstance().font.width(name) / 2.f) + 8 + 2, y + rowHeight / 2.f
                    - Minecraft.getInstance().font.lineHeight / 2.f, -1);
            GL11.glPopMatrix();

            GL11.glPushMatrix();
            GL11.glColor4f(1.f, 1.f, 1.f, 1.f);
            RenderUtils.Texture.drawTextureAt(x + rowWidth / 2.f - 16 - 2,
                    y + rowHeight / 2.f - 8, icon, 16, 16, false);
            GL11.glPopMatrix();
        }

        @Override
        public void execute() {
            action.execute();
        }
    }

    static class ScreenList extends ObjectSelectionList<ScreenEntry> {

        private final Screen screen;
        private boolean scrolling;

        public ScreenList(Screen screen, Minecraft client, int width, int height, int y1, int y2, int entryHeight) {
            super(client, width, height, y1, y2, entryHeight);
            this.screen = screen;
        }

        @Override
        public int addEntry(ScreenEntry entry) {
            return super.addEntry(entry);
        }

        @Override
        protected void renderList(PoseStack matrices, int x, int y, int mouseX, int mouseY, float delta) {
            int itemCount = this.getItemCount();
            Tesselator tessellator_1 = Tesselator.getInstance();
            BufferBuilder buffer = tessellator_1.getBuilder();

            for (int index = 0; index < itemCount; ++index) {
                int entryTop = this.getRowTop(index) + 2;
                int entryBottom = this.getRowTop(index) + this.itemHeight;
                if (entryBottom >= this.y0 && entryTop <= this.y1) {
                    int entryHeight = this.itemHeight - 4;
                    ScreenEntry entry = this.getEntry(index);
                    int rowWidth = this.getRowWidth();
                    int entryLeft;
                    if (((EntryListWidgetAccessor) this).isRenderSelection() && this.isSelectedItem(index)) {
                        entryLeft = this.getRowLeft() - 2;
                        int selectionRight = x + rowWidth + 2;
                        RenderSystem.disableTexture();
                        float float_2 = this.isFocused() ? 1.0F : 0.5F;
                        RenderSystem.color4f(float_2, float_2, float_2, 1.0F);
                        Matrix4f matrix = matrices.last().pose();
                        buffer.begin(7, DefaultVertexFormat.POSITION);
                        buffer.vertex(matrix, (float) entryLeft,
                                (float) (entryTop + entryHeight + 2), 0.0F).endVertex();
                        buffer.vertex(matrix, (float) selectionRight,
                                (float) (entryTop + entryHeight + 2), 0.0F).endVertex();
                        buffer.vertex(matrix, (float) selectionRight,
                                (float) (entryTop - 2), 0.0F).endVertex();
                        buffer.vertex(matrix, (float) entryLeft,
                                (float) (entryTop - 2), 0.0F).endVertex();
                        tessellator_1.end();
                        RenderSystem.color4f(0.0F, 0.0F, 0.0F, 1.0F);
                        buffer.begin(7, DefaultVertexFormat.POSITION);
                        buffer.vertex(matrix, (float) (entryLeft + 1),
                                (float) (entryTop + entryHeight + 1), 0.0F).endVertex();
                        buffer.vertex(matrix, (float) (selectionRight - 1),
                                (float) (entryTop + entryHeight + 1), 0.0F).endVertex();
                        buffer.vertex(matrix, (float) (selectionRight - 1),
                                (float) (entryTop - 1), 0.0F).endVertex();
                        buffer.vertex(matrix, (float) (entryLeft + 1),
                                (float) (entryTop - 1), 0.0F).endVertex();
                        tessellator_1.end();
                        RenderSystem.enableTexture();
                    }

                    entryLeft = this.getRowLeft();
                    entry.render(matrices, index, entryTop, entryLeft, rowWidth, entryHeight, mouseX, mouseY,
                            this.isMouseOver(mouseX, mouseY)
                                    && Objects.equals(this.getEntryAtPos(mouseX, mouseY), entry), delta);
                }
            }

        }

        protected void updateScrollingState(double double_1, double double_2, int int_1) {
            super.updateScrollingState(double_1, double_2, int_1);
            this.scrolling = int_1 == 0 && double_1 >= (double) this.getScrollbarPosition()
                    && double_1 < (double) (this.getScrollbarPosition() + 6);
        }

        public boolean mouseClicked(double double_1, double double_2, int int_1) {
            this.updateScrollingState(double_1, double_2, int_1);
            if (!this.isMouseOver(double_1, double_2)) {
                return false;
            } else {
                ScreenEntry entry = this.getEntryAtPos(double_1, double_2);
                if (entry != null) {
                    if (entry.mouseClicked(double_1, double_2, int_1)) {
                        this.setFocused(entry);
                        this.setDragging(true);
                        return true;
                    }
                } else if (int_1 == 0) {
                    this.clickedHeader((int) (double_1 - (double) (this.x0 + this.width / 2
                            - this.getRowWidth() / 2)), (int) (double_2 - (double) this.y0)
                            + (int) this.getScrollAmount() - 4);
                    return true;
                }


                return this.scrolling;
            }
        }

        public final ScreenEntry getEntryAtPos(double x, double y) {
            int int_5 = Mth.floor(y - (double) this.y0) - this.headerHeight + (int) this.getScrollAmount() - 4;
            int index = int_5 / this.itemHeight;
            return x < (double) this.getScrollbarPosition() && x >= (double) this.getRowLeft()
                    && x <= (double) (this.getRowLeft() + this.getRowWidth()) && index >= 0
                    && int_5 >= 0 && index < this.getItemCount() ? this.children().get(index) : null;
        }

        protected int getScrollbarPosition() {
            return this.width - 6;
        }

        public int getRowWidth() {
            return this.width - (Math.max(0, this.getMaxPosition() - (this.y1 - this.y0 - 4)) > 0 ? 18 : 12);
        }

        public int getRowLeft() {
            return this.x0 + 6;
        }

        public int getWidth() {
            return this.width;
        }

        public int getTop() {
            return this.y0;
        }

        protected int getMaxPosition() {
            return super.getMaxPosition() + 4;
        }

        public int getDisplayedCount() {
            return this.children().size();
        }

        @Override
        public void render(PoseStack poseStack, int i, int j, float f) {
            super.render(poseStack, i, j, f);
        }
    }

}