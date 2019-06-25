package com.extrahardmode.features;


import com.extrahardmode.ExtraHardMode;
import com.extrahardmode.module.DataStoreModule;
import com.extrahardmode.module.MsgModule;
import com.extrahardmode.service.ListenerModule;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;

import java.text.DecimalFormat;
import java.util.HashSet;

/**
 * Debugging mode with some extra data to help the developers
 */
public class DebugMode extends ListenerModule
{
    private final DataStoreModule dataStoreModule;
    private final MsgModule msgModule;

    private final String key_blockdata_msg = "key_blockdata_msg_";


    public DebugMode(ExtraHardMode plugin)
    {
        super(plugin);
        dataStoreModule = plugin.getModuleForClass(DataStoreModule.class);
        msgModule = plugin.getModuleForClass(MsgModule.class);
    }


    public void enableDebugMode(String playerName)
    {
        dataStoreModule.getPlayerData(playerName).inDebug = true;
    }


    public void disableDebugMode(String playerName)
    {
        dataStoreModule.getPlayerData(playerName).inDebug = false;
        for (int line = 0; line < 6; line++)
            msgModule.getManager().removePopup(playerName, key_blockdata_msg + line);
    }


    public boolean isInDebugMode(String playerName)
    {
        return dataStoreModule.getPlayerData(playerName).inDebug;
    }


    /**
     * Output block info when the player moves
     */
    @EventHandler
    public void onPlayerTurn(PlayerMoveEvent event)
    {
        Player player = event.getPlayer();
        if (isInDebugMode(player.getName()))
        {
            Block target = player.getTargetBlock(new HashSet<Material>(), 50);
            for (int line = 0; line < 6; line++)
                msgModule.getManager().removePopup(player.getName(), key_blockdata_msg + line);
            DecimalFormat twoDecimalPlaces = new DecimalFormat("#.##");
            msgModule.getManager().showPopup(player.getName(), key_blockdata_msg + 5, 0, ChatColor.RED, null, "EHM DEBUGMODE", "Humidity: " + twoDecimalPlaces.format(target.getHumidity()));
            msgModule.getManager().showPopup(player.getName(), key_blockdata_msg + 4, 0, ChatColor.RED, null, "EHM DEBUGMODE", "Temp: " + twoDecimalPlaces.format(target.getTemperature()));
            msgModule.getManager().showPopup(player.getName(), key_blockdata_msg + 3, 0, ChatColor.RED, null, "EHM DEBUGMODE", "Biome: " + target.getBiome().name());
            msgModule.getManager().showPopup(player.getName(), key_blockdata_msg + 2, 0, ChatColor.RED, null, "EHM DEBUGMODE", "Data: " + target.getData());
            msgModule.getManager().showPopup(player.getName(), key_blockdata_msg + 1, 0, ChatColor.RED, null, "EHM DEBUGMODE", target.getType().name());
            msgModule.getManager().showPopup(player.getName(), key_blockdata_msg + 0, 0, ChatColor.RED, null, "EHM DEBUGMODE", "CURSOR BLOCK");
        }
    }
}
