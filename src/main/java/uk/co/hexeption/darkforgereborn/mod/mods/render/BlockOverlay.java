package uk.co.hexeption.darkforgereborn.mod.mods.render;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.GlStateManager.DestFactor;
import com.mojang.blaze3d.platform.GlStateManager.SourceFactor;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.AirBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL11;
import uk.co.hexeption.darkforgereborn.event.events.world.EventRenderWorld;
import uk.co.hexeption.darkforgereborn.mixin.imp.IEntityRendererManager;
import uk.co.hexeption.darkforgereborn.mixin.imp.IPlayerController;
import uk.co.hexeption.darkforgereborn.mod.Category;
import uk.co.hexeption.darkforgereborn.mod.Mod;
import uk.co.hexeption.darkforgereborn.util.LogHelper;
import uk.co.hexeption.darkforgereborn.util.RenderUtils;

/**
 * BlockOverlay
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 04:50 pm
 */

@Mod.ModInfo(name = "Block Overlay", description = "highlights a block", category = Category.RENDER, bind = GLFW.GLFW_KEY_O)
public class BlockOverlay extends Mod {

    @EventHandler
    private Listener<EventRenderWorld> eventRenderWorldListener = new Listener<>(event -> {

        if (getState()) {
            if (mc.objectMouseOver.getType() == Type.ENTITY) {
                return;
            }
            BlockRayTraceResult blockraytraceresult = (BlockRayTraceResult) mc.objectMouseOver;
            BlockPos blockpos = blockraytraceresult.getPos();

            if (mc.world.getBlockState(blockpos).getBlock() instanceof AirBlock ||
                mc.world.getBlockState(blockpos).getMaterial() == Material.WATER) {
                return;
            }

            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(SourceFactor.SRC_ALPHA, DestFactor.ONE_MINUS_SRC_ALPHA);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GlStateManager.lineWidth(2);
            GlStateManager.disableTexture();
            GlStateManager.enableCull();
            GlStateManager.disableDepthTest();
            double renderPosX = ((IEntityRendererManager) mc.getRenderManager()).getRenderPosX();
            double renderPosY = ((IEntityRendererManager) mc.getRenderManager()).getRenderPosY();
            double renderPosZ = ((IEntityRendererManager) mc.getRenderManager()).getRenderPosZ();

            GlStateManager.translated(-renderPosX, -renderPosY, -renderPosZ);
            GlStateManager.translated(blockpos.getX(), blockpos.getY(), blockpos.getZ());

            float currentBlockDamage = ((IPlayerController) mc.playerController).getCurrentBlockDamage();

            float progress = currentBlockDamage;

            if (progress < 0) {
                progress = 1;
            }

            float red = progress * 2;
            float green = 2 - red;

            GlStateManager.color4f(red, green, 0, 0.25f);
            RenderUtils.drawSolidBox(mc.world.getBlockState(blockpos).getShape(mc.world, blockpos).getBoundingBox());
            GlStateManager.color4f(red, green, 0, 0.5f);
            RenderUtils.drawOutlinedBox(mc.world.getBlockState(blockpos).getShape(mc.world, blockpos).getBoundingBox());

            GlStateManager.color4f(1, 1, 1, 1);

            GlStateManager.enableDepthTest();
            GlStateManager.enableTexture();
            GlStateManager.disableBlend();
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GlStateManager.popMatrix();
        }

    });

}
