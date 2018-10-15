//package com.extrahardmode.compatibility;
//
//
//import net.coreprotect.CoreProtect;
//import net.coreprotect.CoreProtectAPI;
//import org.bukkit.block.Block;
//import org.bukkit.block.BlockState;
//import org.bukkit.plugin.Plugin;
//
///**
// * @author Diemex
// */
//public class CompatCoreProtect implements ICompat, IBlockLogger
//{
//    private final Plugin plugin;
//    private final CoreProtectAPI coreProtectAPI;
//
//
//    public CompatCoreProtect(Plugin plugin)
//    {
//        this.plugin = plugin;
//        this.coreProtectAPI = getCoreProtect();
//    }
//
//
//    @Override
//    public void logFallingBlockFall(Block block)
//    {
//        coreProtectAPI.logRemoval(fallingBlockFallTag, block.getLocation(), block.getTypeId(), block.getData());
//    }
//
//
//    @Override
//    public void logFallingBlockLand(BlockState block)
//    {
//        coreProtectAPI.logPlacement(fallingBlockLandTag, block.getLocation(), block.getTypeId(), block.getData().getData());
//    }
//
//
//    @Override
//    public boolean isEnabled()
//    {
//        return coreProtectAPI != null;
//    }
//
//
//    private CoreProtectAPI getCoreProtect()
//    {
//        Plugin coreProtect = plugin.getServer().getPluginManager().getPlugin("CoreProtect");
//
//        // Check that CoreProtect is loaded
//        if (coreProtect == null || !(coreProtect instanceof CoreProtect))
//        {
//            return null;
//        }
//
//        // Check that the API is enabled
//        CoreProtectAPI CoreProtect = ((CoreProtect) coreProtect).getAPI();
//        if (!CoreProtect.isEnabled())
//        {
//            return null;
//        }
//
//        // Check that a compatible version of the API is loaded
//        if (CoreProtect.APIVersion() < 2)
//        {
//            return null;
//        }
//
//        return CoreProtect;
//    }
//
//
//}
