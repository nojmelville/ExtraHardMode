package com.extrahardmode.module.temporaryblock;


import com.extrahardmode.ExtraHardMode;
import com.extrahardmode.service.ListenerModule;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.HashMap;
import java.util.Map;

public class TemporaryBlockHandler extends ListenerModule
{
    private Map<LiteLocation, TemporaryBlock> temporaryBlockList = new HashMap<LiteLocation, TemporaryBlock>();


    public TemporaryBlockHandler(ExtraHardMode plugin)
    {
        super(plugin);
    }


    /**
     * int addTemporaryBlock(Block block)
     * removeBlock (int)
     * onBlockBreak -> mark as broken
     * onTempBlockBreakEvent
     * onZombieRespawnTask -> check if broken
     */
    @EventHandler(ignoreCancelled = true)
    public void onBlockBreak(BlockBreakEvent event)
    {
        fireTemporaryBlockBreakEvent(event.getBlock());
    }

    //Also account for water
    @EventHandler(ignoreCancelled = true)
    public void onWaterBreakBlock(BlockFromToEvent event)
    {
        fireTemporaryBlockBreakEvent(event.getToBlock());
    }

    //And explosions
    @EventHandler(ignoreCancelled = true)
    public void onEntityExplosionBreak(EntityExplodeEvent event)
    {
        for (Block block : event.blockList())
            fireTemporaryBlockBreakEvent(block);
    }

    //And other weird plugin explosions
    @EventHandler(ignoreCancelled = true)
    public void onBlockExplosionBreak(BlockExplodeEvent event)
    {
        for (Block block : event.blockList())
            fireTemporaryBlockBreakEvent(block);
    }

    private void fireTemporaryBlockBreakEvent(Block block)
    {
        if (temporaryBlockList.containsKey(LiteLocation.fromLocation(block.getLocation())))
        {
            TemporaryBlock temporaryBlock = temporaryBlockList.remove(LiteLocation.fromLocation(block.getLocation()));
            temporaryBlock.isBroken = true;
            plugin.getServer().getPluginManager().callEvent(new TemporaryBlockBreakEvent(temporaryBlock, block));
        }
    }



    public TemporaryBlock addTemporaryBlock(Location loc, Object... data)
    {
        TemporaryBlock temporaryBlock = new TemporaryBlock(loc, data);
        temporaryBlockList.put(LiteLocation.fromLocation(loc), temporaryBlock);
        return temporaryBlock;
    }
}
