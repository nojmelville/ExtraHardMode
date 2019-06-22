package com.extrahardmode.service;


import com.extrahardmode.service.config.customtypes.BlockType;
import org.bukkit.Material;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Diemex
 */
public class BlockTypeTest
{
    @Test
    public void matches_simple()
    {
        BlockType block = new BlockType(1);
        assertTrue(block.matches(1, (byte) 0));
    }


    @Test
    public void matches_simple_false()
    {
        BlockType block = new BlockType(1);
        assertFalse(block.matches(2, (byte) 0));
    }


    @Test
    public void matches_advanced()
    {
        BlockType block = new BlockType(3, (short) 1, (short) 3, (short) 4);

        assertTrue(block.matches(3, (short) 1));
        assertFalse(block.matches(3, (short) 2));
        assertTrue(block.matches(3, (short) 4));

        assertFalse(block.matches(1, (short) 1));
    }


//    @Test
//    public void loadFromConfig_simple()
//    {
//        String input = "STONE";
//        BlockType expected = new BlockType(Material.STONE);
//        assertEquals(expected, BlockType.loadFromConfig(input));
//    }
//
//
//    @Test
//    public void loadFromConfig_fail()
//    {
//        String input = "Srtone";
//        BlockType expected = new BlockType(-1);
//        assertEquals(expected, BlockType.loadFromConfig(input));
//    }
//
//
//    @Test
//    public void loadFromConfig_meta_simple()
//    {
//        String input = "Stone,2";
//        BlockType expected = new BlockType(Material.STONE, (short) 2);
//        assertEquals(expected, BlockType.loadFromConfig(input));
//    }
//
//
//    @Test
//    public void loadFromConfig_meta_advanced()
//    {
//        String input = "Stone,2,3,4";
//        BlockType expected = new BlockType(Material.STONE, (short) 2, (short) 3, (short) 4);
//        assertEquals(expected, BlockType.loadFromConfig(input));
//    }


    @Test
    public void saveToConfig_simple()
    {
        String expected = "STONE@1,2,3";
        BlockType block = new BlockType(Material.STONE, (short) 1, (short) 2, (short) 3);
        assertEquals(expected, block.saveToString());
    }
}
