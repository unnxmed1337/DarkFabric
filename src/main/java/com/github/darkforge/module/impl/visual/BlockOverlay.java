package com.github.darkforge.module.impl.visual;

import com.github.darkforge.event.impl.render.RenderWorldEvent;
import com.github.darkforge.injection.interfaces.IEntityRendererManager;
import com.github.darkforge.injection.interfaces.IPlayerController;
import com.github.darkforge.module.AbstractModule;
import com.github.darkforge.util.render.RenderUtils;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.AirBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import org.lwjgl.opengl.GL11;

/**
 * BlockOverlay
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:50 pm
 */

@AbstractModule.Info(name = "Block Overlay", description = "highlights a block",
        type = AbstractModule.Type.VISUAL)
public class BlockOverlay extends AbstractModule {

    @EventHandler
    public Listener<RenderWorldEvent> renderWorldEventListener = new Listener<>(event -> {
        if (!isEnabled()) return;
        assert mc.objectMouseOver != null;
        if (mc.objectMouseOver.getType() == net.minecraft.util.math.RayTraceResult.Type.ENTITY)
            return;

        BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) mc.objectMouseOver;
        BlockPos blockpos = blockraytraceresult.getPos();

        assert mc.world != null;
        if (mc.world.getBlockState(blockpos).getBlock() instanceof AirBlock ||
                mc.world.getBlockState(blockpos).getMaterial() == Material.WATER)
            return;

        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(SourceFactor.SRC_ALPHA.param,
                DestFactor.ONE_MINUS_SRC_ALPHA.param);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GlStateManager.lineWidth(2);
        GlStateManager.disableTexture();
        GlStateManager.enableCull();
        GlStateManager.disableDepthTest();
        double renderPosX = ((IEntityRendererManager) mc.getRenderManager()).getPosX();
        double renderPosY = ((IEntityRendererManager) mc.getRenderManager()).getPosY();
        double renderPosZ = ((IEntityRendererManager) mc.getRenderManager()).getPosZ();

        GlStateManager.translated(-renderPosX, -renderPosY, -renderPosZ);
        GlStateManager.translated(blockpos.getX(), blockpos.getY(), blockpos.getZ());

        assert mc.playerController != null;
        float progress = ((IPlayerController) mc.playerController).getCurrentBlockDamage();

        if (progress < 0)
            progress = 1;

        float red = progress * 2;
        float green = 2 - red;

        GlStateManager.color4f(red, green, 0, 0.25f);
        RenderUtils.drawSolidBox(mc.world.getBlockState(blockpos)
                .getShape(mc.world, blockpos).getBoundingBox());
        GlStateManager.color4f(red, green, 0, 0.5f);
        RenderUtils.drawOutlinedBox(mc.world.getBlockState(blockpos)
                .getShape(mc.world, blockpos).getBoundingBox());

        GlStateManager.color4f(1, 1, 1, 1);

        GlStateManager.enableDepthTest();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GlStateManager.popMatrix();
    });

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

}