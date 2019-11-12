package uk.co.hexeption.darkforgereborn.mod.mods.world;

import java.util.ArrayList;
import me.zero.alpine.event.type.Cancellable;
import me.zero.alpine.listener.EventHandler;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.settings.AmbientOcclusionStatus;
import org.lwjgl.glfw.GLFW;
import uk.co.hexeption.darkforgereborn.event.events.block.EventBlockRenderSide;
import uk.co.hexeption.darkforgereborn.event.events.block.EventSetOpaqueCube;
import uk.co.hexeption.darkforgereborn.mod.Category;
import uk.co.hexeption.darkforgereborn.mod.Mod;
import uk.co.hexeption.darkforgereborn.mod.options.Option;
import uk.co.hexeption.darkforgereborn.mod.options.Option.Type;
import uk.co.hexeption.darkforgereborn.mod.options.ValueDouble;

/**
 * Xray
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 12:04 pm
 */
@Mod.ModInfo(name = "Xray", description = "See ores?", category = Category.WORLD, bind = GLFW.GLFW_KEY_X)
public class Xray extends Mod {

    public final ArrayList<Block> xrayBlocks = new ArrayList<>();

    private transient AmbientOcclusionStatus ambientOcclousion;
    private transient double gamma;

    public Xray() {
        options.put("opacity", new Option("Opacity", "Opacity of the xrayable blocks", new ValueDouble(30, new double[]{1, 100}, 1), Type.NUMBER));

        //Ore Blocks
        xrayBlocks.add(Blocks.COAL_ORE);
        xrayBlocks.add(Blocks.DIAMOND_ORE);
        xrayBlocks.add(Blocks.EMERALD_ORE);
        xrayBlocks.add(Blocks.GOLD_ORE);
        xrayBlocks.add(Blocks.IRON_ORE);
        xrayBlocks.add(Blocks.LAPIS_ORE);
        xrayBlocks.add(Blocks.NETHER_QUARTZ_ORE);
        xrayBlocks.add(Blocks.REDSTONE_ORE);
        xrayBlocks.add(Blocks.COAL_BLOCK);

        //Ingot Blocks + Glowstone
        xrayBlocks.add(Blocks.DIAMOND_BLOCK);
        xrayBlocks.add(Blocks.EMERALD_BLOCK);
        xrayBlocks.add(Blocks.GOLD_BLOCK);
        xrayBlocks.add(Blocks.IRON_BLOCK);
        xrayBlocks.add(Blocks.LAPIS_BLOCK);
        xrayBlocks.add(Blocks.QUARTZ_BLOCK);
        xrayBlocks.add(Blocks.REDSTONE_BLOCK);
        xrayBlocks.add(Blocks.GLOWSTONE);

        //Random Blocks
        xrayBlocks.add(Blocks.CRAFTING_TABLE);
        xrayBlocks.add(Blocks.TNT);
        xrayBlocks.add(Blocks.DROPPER);
        xrayBlocks.add(Blocks.DISPENSER);
    }

    @Override
    public void onEnable() {
        ambientOcclousion = mc.gameSettings.ambientOcclusionStatus;
        gamma = mc.gameSettings.gamma;
        mc.gameSettings.gamma = 100;
        mc.gameSettings.ambientOcclusionStatus = AmbientOcclusionStatus.OFF;
        mc.worldRenderer.loadRenderers();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.ambientOcclusionStatus = ambientOcclousion;
        mc.gameSettings.gamma = gamma;
        mc.worldRenderer.loadRenderers();
    }

    @EventHandler
    private Listener<EventSetOpaqueCube> eventSetOpaqueCubeListener = new Listener<>(Cancellable::cancel);

    @EventHandler
    private Listener<EventBlockRenderSide> eventBlockRenderSideListener = new Listener<>(event -> {
        if (xrayBlocks.contains(event.getAdjacentState().getBlock())) {
            event.setToRender(true);
        } else {
            event.cancel();
        }
    });
}
