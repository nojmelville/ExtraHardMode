/*
 * This file is part of
 * ExtraHardMode Server Plugin for Minecraft
 *
 * Copyright (C) 2012 Ryan Hamshire
 * Copyright (C) 2013 Diemex
 *
 * ExtraHardMode is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * ExtraHardMode is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU Affero Public License
 * along with ExtraHardMode.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.extrahardmode.module;


import com.extrahardmode.ExtraHardMode;
import com.extrahardmode.config.messages.MessageNode;
import com.extrahardmode.config.messages.MsgCategory;
import com.extrahardmode.service.EHMModule;
import com.google.common.collect.Table;
import org.apache.commons.lang.Validate;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Diemex
 */
public class MsgPersistModule extends EHMModule
{
    private final String dbFile;

    private final String msgTable = "messages";

    private final String playerTable = "players";

    /**
     * Buffer player ids (playerName, playerId)
     */
    private Map<String, Integer> playerIdBuffer;

    /**
     * Buffer data from the db (playerid, message, value)
     */
    private Table<Integer, MessageNode, Integer> buffer;


    /**
     * Constructor.
     *
     * @param plugin
     *         - Plugin instance.
     */
    public MsgPersistModule(ExtraHardMode plugin, String dbFile)
    {
        super(plugin);
        this.dbFile = dbFile;
    }


    /**
     * Make sure JDBC is enabled/loaded
     */
    protected void testJDBC()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e)
        {
            plugin.getLogger().severe("JDBC Driver not found : " + e);
            closing();
        }
    }


    /**
     * Get the id of the Player. Buffers id in a Map. Creates new id if Player not in the db yet.
     *
     * @param playerName
     *         name of the Player
     *
     * @return id of Player
     */
    private int getPlayerId(String playerName)
    {
        if (playerIdBuffer.containsKey(playerName))
            return playerIdBuffer.get(playerName);
        int id = 0;

        Connection conn = null;
        try
        {
            conn = retrieveConnection();
            String query = String.format(
                    "SELECT id FROM %s WHERE %s = %s", playerTable, "name", '"' + playerName + '"');

            ResultSet resultSet = conn.createStatement().executeQuery(query);
            if (resultSet.next())
                id = resultSet.getInt("id");

            if (id == 0) //Create a new Player
            {
                String newPlayerQuery = String.format( //new id
                        "INSERT INTO %s (%s) VALUES (%s)", playerTable, "name", '"' + playerName + '"');
                id = conn.createStatement().executeUpdate(newPlayerQuery);

                String newPlayerDataQuery = String.format( //empty row in messages
                        "INSERT INTO %s (%s) VALUES (%s)", msgTable, "id", id);
                conn.createStatement().executeUpdate(newPlayerDataQuery);
            }

            playerIdBuffer.put(playerName, id);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null) conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return id;
    }


    /**
     * Get a connection to our database
     *
     * @return a connection
     */
    private Connection retrieveConnection() throws SQLException
    {
        return DriverManager.getConnection("jdbc:sqlite:" + dbFile);
    }


    /**
     * Creates tables if they do not exist.
     */
    private void initializeTables()
    {
        Connection conn = null;
        try
        {
            conn = retrieveConnection();
            Statement statement = conn.createStatement();
            statement.setQueryTimeout(30);

            //One table holding the playername id relation
            String playerQuery = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (id INTEGER PRIMARY KEY AUTOINCREMENT, %s STRING)", playerTable, "name");
            statement.executeUpdate(playerQuery);

            //One column for every message
            StringBuilder columns = new StringBuilder();
            for (MessageNode message : MessageNode.values())
            {
                if (message.getColumnName() != null && message.getMsgCategory() == MsgCategory.TUTORIAL)
                {
                    columns.append(',');
                    columns.append(message.getColumnName());
                }
            }

            String msgQuery = String.format(
                    "CREATE TABLE IF NOT EXISTS %s (id INTEGER PRIMARY KEY UNIQUE %s)", msgTable, columns);
            statement.executeUpdate(msgQuery);

            //Check if all columns are present
            DatabaseMetaData dmd = conn.getMetaData();
            //Add missing columns
            for (MessageNode node : MessageNode.values())
            {
                if (node.getMsgCategory() == MsgCategory.TUTORIAL)
                {
                    ResultSet set = dmd.getColumns(null, null, msgTable, node.getColumnName());
                    if (!set.next())
                    {
                        String updateQuery = String.format(
                                "ALTER TABLE %s ADD COLUMN %s", msgTable, node.getColumnName());
                        statement.executeUpdate(updateQuery);
                    }
                }
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null) conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Increment the count of a certain message by one
     *
     * @param node
     *         to increment
     * @param playerName
     *         only for this player
     */
    public void increment(MessageNode node, String playerName)
    {
        int playerId = getPlayerId(playerName);
        int value = getCountFor(node, playerId);

        set(node, playerId, ++value);
    }


    /**
     * Set the count of a certain message to a certain value
     *
     * @param node
     *         node to set the count for
     * @param playerId
     *         player for whom we are tracking the count
     * @param value
     *         value to set
     */
    private void set(MessageNode node, int playerId, int value)
    {
        Validate.isTrue(value >= 0, "Count has to be positive");
        Connection conn = null;
        try
        {
            conn = retrieveConnection();
            Statement statement = conn.createStatement();

            //Set the count to the provided value
            String setQuery = String.format(
                    "UPDATE %s SET %s = %s WHERE id = %s", msgTable, node.getColumnName(), value, playerId);
            statement.execute(setQuery);
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null) conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }


    /**
     * Get the count of a message
     *
     * @param node
     *         which message
     * @param playerName
     *         player which has seen this message
     *
     * @return count >= 0
     */
    public int getCountFor(MessageNode node, String playerName)
    {
        return getCountFor(node, getPlayerId(playerName));
    }


    /**
     * Get how often a message has been displayed
     *
     * @param node
     *         to get the count for
     * @param playerId
     *         id of the player to get the count for
     *
     * @return how often this message has been displayed
     */
    private int getCountFor(MessageNode node, int playerId)
    {
        Connection conn = null;
        int value = 0;

        try
        {
            conn = retrieveConnection();
            Statement statement = conn.createStatement();

            String select = String.format("SELECT * FROM %s WHERE %s = %s", msgTable, "id", playerId);

            ResultSet result = statement.executeQuery(select);
            if (result.next())
                value = result.getInt(node.getColumnName());
            else //create the missing row
            {
                String newPlayerDataQuery = String.format( //empty row in messages
                        "INSERT INTO %s (%s) VALUES (%s)", msgTable, "id", playerId);
                conn.createStatement().executeUpdate(newPlayerDataQuery);
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        } finally
        {
            try
            {
                if (conn != null) conn.close();
            } catch (SQLException e)
            {
                e.printStackTrace();
            }
        }

        return value;
    }


    /**
     * Resets all counts for a given player
     *
     * @param playerName
     *         player to reset the stats for
     */
    public void resetAll(String playerName)
    {
        int playerId = getPlayerId(playerName);
        for (MessageNode node : MessageNode.values())
        {
            if (node.getColumnName() != null)
            {
                set(node, playerId, 0);
            }
        }
    }


    @Override
    public void starting()
    {
        playerIdBuffer = new HashMap<String, Integer>();
        testJDBC();
        initializeTables();
    }


    @Override
    public void closing()
    {
        playerIdBuffer = null;
    }
}
