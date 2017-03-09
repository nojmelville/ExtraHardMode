package com.extrahardmode.module.temporaryblock;


import org.bukkit.block.Block;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.block.BlockEvent;
import org.bukkit.event.block.BlockFromToEvent;

public class TemporaryBlockBreakEvent extends Event
{
    //private final BlockEvent event;
    private final TemporaryBlock temporaryBlock;
    private final Block block;


    public TemporaryBlockBreakEvent(TemporaryBlock temporaryBlock, Block block)
    {
        this.block = block;
        this.temporaryBlock = temporaryBlock;
    }


    public TemporaryBlock getTemporaryBlock()
    {
        return temporaryBlock;
    }


//    public BlockBreakEvent getBlockBreakEvent()
//    {
//        return event;
//    }


    public Block getBlock()
    {
        return block;
    }

    private static final HandlerList HANDLERS = new HandlerList();


    public HandlerList getHandlers()
    {
        return HANDLERS;
    }


    public static HandlerList getHandlerList()
    {
        return HANDLERS;
    }
}
