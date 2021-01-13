package com.github.darkforge.module.impl.world;

import com.github.darkforge.event.impl.render.block.BlockRenderSideEvent;
import com.github.darkforge.event.impl.render.block.SetOpaqueCubeEvent;
import com.github.darkforge.module.AbstractModule;
import com.github.darkforge.module.options.Option;
import com.github.darkforge.module.options.ValueDouble;
import com.google.common.collect.Lists;
import me.zero.alpine.event.type.Cancellable;
import me.zero.alpine.listener.Listener;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.settings.AmbientOcclusionStatus;

import java.util.Collections;
import java.util.List;

/**
 * Xray
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 12/11/2019 - 12:04 pm
 */
@AbstractModule.Info(name = "Xray", description = "See through blocks to find ores.", type = AbstractModule.Type.WORLD)
public class Xray extends AbstractModule {

    public final List<Block> xrayBlocks = Collections.synchronizedList(Lists.newLinkedList());

    public Listener<SetOpaqueCubeEvent> setOpaqueCubeEventListener = new Listener<>(Cancellable::cancel);
    public Listener<BlockRenderSideEvent> blockRenderSideEventListener = new Listener<>(event -> {
        if (xrayBlocks.contains(event.getAdjacentState().getBlock()))
            event.setToRender(true);
        else
            event.cancel();
    });

    private transient AmbientOcclusionStatus ambientOcclusion;
    private transient double gamma;

    public Xray() {
        options.put("opacity", new Option("Opacity",
                "Opacity of the xrayable blocks",
                new ValueDouble(30, new double[]{1, 100}, 1), Option.Type.NUMBER));

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
        ambientOcclusion = mc.gameSettings.ambientOcclusionStatus;
        gamma = mc.gameSettings.gamma;
        mc.gameSettings.gamma = 100;
        mc.gameSettings.ambientOcclusionStatus = AmbientOcclusionStatus.OFF;
        mc.worldRenderer.loadRenderers();
    }

    @Override
    public void onDisable() {
        mc.gameSettings.ambientOcclusionStatus = ambientOcclusion;
        mc.gameSettings.gamma = gamma;
        mc.worldRenderer.loadRenderers();
    }

}