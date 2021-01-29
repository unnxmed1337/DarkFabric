package com.github.darkfabric.injection.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.packs.PackSelectionScreen;
import net.minecraft.client.gui.screens.packs.TransferableSelectionList;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(PackSelectionScreen.class)
public abstract class MixinPackSelectionScreen extends Screen {


    @Shadow
    @Final
    private static Component DRAG_AND_DROP;

    @Shadow
    private TransferableSelectionList availablePackList;

    @Shadow
    private TransferableSelectionList selectedPackList;

    protected MixinPackSelectionScreen(Component component) {
        super(component);
    }

    /**
     * @author unnamed
     * @reason retarded background-checks
     */
    @Overwrite
    public void render(PoseStack poseStack, int i, int j, float f) {
        renderBackground(poseStack);
        this.availablePackList.render(poseStack, i, j, f);
        this.selectedPackList.render(poseStack, i, j, f);
        drawCenteredString(poseStack, this.font, this.title, this.width / 2, 8, 16777215);
        drawCenteredString(poseStack, this.font, DRAG_AND_DROP, this.width / 2, 20, 16777215);
        super.render(poseStack, i, j, f);
    }

}