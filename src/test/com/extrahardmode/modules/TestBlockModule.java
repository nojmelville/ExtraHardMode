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

package com.extrahardmode.modules;


import com.extrahardmode.ExtraHardMode;
import com.extrahardmode.mocks.MockBlock;
import com.extrahardmode.mocks.MockExtraHardMode;
import com.extrahardmode.module.BlockModule;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 */
public class TestBlockModule
{
    private final ExtraHardMode plugin;

    private final BlockModule module;


    public TestBlockModule()
    {
        plugin = new MockExtraHardMode().get();
        module = new BlockModule(plugin);
    }


    @Test
    public void testBreaksFallingBlocks()
    {
//        assertTrue(module.breaksFallingBlock(Material.STEP));
//        assertTrue(module.breaksFallingBlock(Material.REDSTONE_TORCH_ON));
//        assertTrue(module.breaksFallingBlock(Material.REDSTONE_TORCH_OFF));
//        assertTrue(module.breaksFallingBlock(Material.TORCH));
//        assertTrue(module.breaksFallingBlock(Material.RAILS));
//        assertTrue(module.breaksFallingBlock(Material.ACTIVATOR_RAIL));
//        assertTrue(module.breaksFallingBlock(Material.RED_ROSE));
//        assertTrue(module.breaksFallingBlock(Material.BROWN_MUSHROOM));
//        assertTrue(module.breaksFallingBlock(Material.WEB));
//        //assertTrue(module.breaksFallingBlock(Material.CARPET));
//        assertTrue(module.breaksFallingBlock(Material.SNOW));
//        assertTrue(module.breaksFallingBlock(Material.SIGN_POST));
//        assertTrue(module.breaksFallingBlock(Material.DAYLIGHT_DETECTOR));
//        //assertTrue(module.breaksFallingBlock(Material.GOLD_PLATE));
//        assertTrue(module.breaksFallingBlock(Material.TRAP_DOOR));
//        assertTrue(module.breaksFallingBlock(Material.TRIPWIRE));
//
//        assertFalse(module.breaksFallingBlock(Material.DOUBLE_STEP));
//        assertFalse(module.breaksFallingBlock(Material.LOG));
    }


    @Test
    public void testIsOffAxis()
    {
        MockBlock player = new MockBlock();
        player.setLocation(0, 64, 0);

        MockBlock againstBlock = new MockBlock();
        againstBlock.setLocation(0, 64, 10);

        MockBlock placedBlock = new MockBlock();
        placedBlock.setLocation(1, 64, 10);
        placedBlock.setRelative(BlockFace.DOWN, new MockBlock().setMaterial(Material.AIR).get());

        assertTrue(BlockModule.isOffAxis(player.get(), placedBlock.get(), againstBlock.get()));
    }


    @Test
    public void testIsOffAxisBut()
    {
        MockBlock player = new MockBlock();
        player.setLocation(0, 64, 0);

        MockBlock againstBlock = new MockBlock();
        againstBlock.setLocation(0, 64, 2);

        MockBlock placedBlock = new MockBlock();
        placedBlock.setLocation(0, 64, 1);
        placedBlock.setRelative(BlockFace.DOWN, new MockBlock().setMaterial(Material.AIR).get());

        assertFalse(BlockModule.isOffAxis(player.get(), placedBlock.get(), againstBlock.get()));
    }
}
