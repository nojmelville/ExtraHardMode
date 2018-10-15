//package com.extrahardmode.compatibility;
//
//
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockState;
//import org.bukkit.plugin.Plugin;
//import org.bukkit.plugin.java.JavaPlugin;
//import uk.co.oliwali.HawkEye.DataType;
//import uk.co.oliwali.HawkEye.entry.BlockChangeEntry;
//import uk.co.oliwali.HawkEye.entry.BlockEntry;
//import uk.co.oliwali.HawkEye.util.BlockUtil;
//import uk.co.oliwali.HawkEye.util.HawkEyeAPI;
//
///**
// * Thanks HawkEye for wasting my time
// */
//public class CompatHawkEye implements ICompat, IBlockLogger
//{
//    private final JavaPlugin plugin;
//    private boolean hawkEyeEnabled;
//
//
//    public CompatHawkEye(Plugin plugin)
//    {
//        this.plugin = (JavaPlugin) plugin;
//        hawkEyeEnabled = plugin.getServer().getPluginManager().isPluginEnabled("HawkEye");
//    }
//
//
//    @Override
//    public void logFallingBlockFall(Block block)
//    {
//        HawkEyeAPI.addEntry(plugin, new BlockEntry(fallingBlockFallTag, DataType.BLOCK_BREAK, block));
//    }
//
//
//    @Override
//    public void logFallingBlockLand(BlockState block)
//    {
//        String after = BlockUtil.getBlockString(block.getBlock());
//        String before = block.getRawData() != 0 ? block.getTypeId() + ":" + block.getRawData() : Integer.toString(block.getTypeId());
//        HawkEyeAPI.addEntry(plugin, new BlockChangeEntry(fallingBlockLandTag, DataType.BLOCK_PLACE, block.getLocation(), before, after));
//    }
//
//
//    @Override
//    public boolean isEnabled()
//    {
//        return hawkEyeEnabled;
//    }
//}
