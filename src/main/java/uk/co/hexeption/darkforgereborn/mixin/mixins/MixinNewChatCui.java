package uk.co.hexeption.darkforgereborn.mixin.mixins;

import com.mojang.blaze3d.platform.GlStateManager;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ChatLine;
import net.minecraft.client.gui.NewChatGui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.ChatVisibility;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import uk.co.hexeption.darkforgereborn.DarkForgeReborn;
import uk.co.hexeption.darkforgereborn.mod.mods.misc.CustomChat;

/**
 * MixinNewChatCui
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 13/11/2019 - 06:23 pm
 */
@Mixin(NewChatGui.class)
public abstract class MixinNewChatCui {

    @Shadow
    @Final
    private Minecraft mc;

    @Shadow
    public abstract int getLineCount();

    @Shadow
    @Final
    private List<ChatLine> drawnChatLines;

    @Shadow
    public abstract boolean getChatOpen();

    @Shadow
    public abstract double getScale();

    @Shadow
    public abstract int getChatWidth();

    @Shadow
    private int scrollPos;

    @Shadow
    protected static double func_212915_c(int p_212915_0_) {
        double d0 = (double) p_212915_0_ / 200.0D;
        d0 = 1.0D - d0;
        d0 = d0 * 10.0D;
        d0 = MathHelper.clamp(d0, 0.0D, 1.0D);
        d0 = d0 * d0;
        return d0;
    }

    @Shadow
    private boolean isScrolled;

    /**
     * @author
     */
    @Overwrite
    public void render(int updateCounter) {
        if (this.mc.gameSettings.chatVisibility != ChatVisibility.HIDDEN) {
            int i = this.getLineCount();
            int j = this.drawnChatLines.size();
            if (j > 0) {
                boolean flag = false;
                if (this.getChatOpen()) {
                    flag = true;
                }

                double d0 = this.getScale();
                int k = MathHelper.ceil((double) this.getChatWidth() / d0);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(2.0F, 8.0F, 0.0F);
                GlStateManager.scaled(d0, d0, 1.0D);
                double d1 = this.mc.gameSettings.chatOpacity * (double) 0.9F + (double) 0.1F;
                double d2 = this.mc.gameSettings.accessibilityTextBackgroundOpacity;
                int l = 0;

                for (int i1 = 0; i1 + this.scrollPos < this.drawnChatLines.size() && i1 < i; ++i1) {
                    ChatLine chatline = this.drawnChatLines.get(i1 + this.scrollPos);
                    if (chatline != null) {
                        int j1 = updateCounter - chatline.getUpdatedCounter();
                        if (j1 < 200 || flag) {
                            double d3 = flag ? 1.0D : func_212915_c(j1);
                            int l1 = (int) (255.0D * d3 * d1);
                            int i2 = (int) (255.0D * d3 * d2);
                            ++l;
                            if (l1 > 3) {
                                int j2 = 0;
                                int k2 = -i1 * 9;
                                fill(-2, k2 - 9, 0 + k + 4, k2, i2 / 2 << 24);
                                String s = chatline.getChatComponent().getFormattedText();
                                GlStateManager.enableBlend();
                                drawStringWithShadow(s, 0, (k2 - 8), 16777215 + (l1 << 24));
                                GlStateManager.disableAlphaTest();
                                GlStateManager.disableBlend();
                            }
                        }
                    }
                }

                if (flag) {
                    int l2 = 9;
                    GlStateManager.translatef(-3.0F, 0.0F, 0.0F);
                    int i3 = j * l2 + j;
                    int j3 = l * l2 + l;
                    int k3 = this.scrollPos * j3 / j;
                    int k1 = j3 * j3 / i3;
                    if (i3 != j3) {
                        int l3 = k3 > 0 ? 170 : 96;
                        int i4 = this.isScrolled ? 13382451 : 3355562;
                        fill(0, -k3, 2, -k3 - k1, i4 + (l3 << 24));
                        fill(2, -k3, 1, -k3 - k1, 13421772 + (l3 << 24));
                    }
                }

                GlStateManager.popMatrix();
            }
        }
    }

    private void drawStringWithShadow(String text, float x, float y, int color) {

        if (DarkForgeReborn.INSTANCE.modManager.getModByClass(CustomChat.class).getState()) {
            DarkForgeReborn.INSTANCE.fontManager.chat.drawStringWithShadow(text, (int) x, (int) y - 3, color);
        } else {
            mc.fontRenderer.drawStringWithShadow(text, x, y, color);
        }
    }

    public void fill(int p_fill_0_, int p_fill_1_, int p_fill_2_, int p_fill_3_, int p_fill_4_) {
        if (p_fill_0_ < p_fill_2_) {
            int i = p_fill_0_;
            p_fill_0_ = p_fill_2_;
            p_fill_2_ = i;
        }

        if (p_fill_1_ < p_fill_3_) {
            int j = p_fill_1_;
            p_fill_1_ = p_fill_3_;
            p_fill_3_ = j;
        }

        float f3 = (float) (p_fill_4_ >> 24 & 255) / 255.0F;
        float f = (float) (p_fill_4_ >> 16 & 255) / 255.0F;
        float f1 = (float) (p_fill_4_ >> 8 & 255) / 255.0F;
        float f2 = (float) (p_fill_4_ & 255) / 255.0F;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.enableBlend();
        GlStateManager.disableTexture();
        GlStateManager.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        GlStateManager.color4f(f, f1, f2, f3);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION);
        bufferbuilder.pos((double) p_fill_0_, (double) p_fill_3_, 0.0D).endVertex();
        bufferbuilder.pos((double) p_fill_2_, (double) p_fill_3_, 0.0D).endVertex();
        bufferbuilder.pos((double) p_fill_2_, (double) p_fill_1_, 0.0D).endVertex();
        bufferbuilder.pos((double) p_fill_0_, (double) p_fill_1_, 0.0D).endVertex();
        tessellator.draw();
        GlStateManager.enableTexture();
        GlStateManager.disableBlend();
    }
}
