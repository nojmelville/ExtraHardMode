//package com.extrahardmode.compatibility;
//
//
//import de.diddiz.LogBlock.Consumer;
//import de.diddiz.LogBlock.LogBlock;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockState;
//import org.bukkit.plugin.Plugin;
//
///**
// * @author Diemex
// */
//public class CompatLogBlock implements ICompat, IBlockLogger
//{
//    private final Consumer consumer;
//
//
//    public CompatLogBlock(Plugin plugin)
//    {
//        Plugin logBlockPlugin = plugin.getServer().getPluginManager().getPlugin("LogBlock");
//        if (logBlockPlugin instanceof LogBlock)
//            consumer = ((LogBlock) logBlockPlugin).getConsumer();
//        else
//            consumer = null;
//    }
//
//
//    @Override
//    public void logFallingBlockFall(Block block)
//    {
//        consumer.queueBlockBreak(fallingBlockFallTag, block.getState());
//    }
//
//
//    @Override
//    public void logFallingBlockLand(BlockState block)
//    {
//        consumer.queueBlock(fallingBlockLandTag, block.getLocation(), block.getBlock().getTypeId(), block.getTypeId(), block.getData().getData());
//    }
//
//
//    @Override
//    public boolean isEnabled()
//    {
//        return consumer != null;
//    }
//}
