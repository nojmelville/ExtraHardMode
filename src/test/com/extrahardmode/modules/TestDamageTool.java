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


import com.extrahardmode.module.UtilityModule;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Diemex
 */
public class TestDamageTool
{
    @Test
    public void damage0Blocks()
    {
//        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
//        assertTrue("Using 0 doesn't break the tool", 0 == UtilityModule.damage(pick, (short) 0).getDurability());
    }


    @Test
    public void damage1Block()
    {
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
//        assertTrue("Using 0 doesn't break the tool", pick.getType().getMaxDurability() == UtilityModule.damage(pick, (short) 1).getDurability());
    }

    //TODO make testing with probabilities possible
    /*@Test
    public void damageToolTest()
    {
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        breakXBlocks(pick, (short) 35);
    }

    @Test
    public void damageHighBlockCount()
    {
        ItemStack pick = new ItemStack(Material.DIAMOND_PICKAXE);
        breakXBlocks(pick, (short) 800);
    }

    private void breakXBlocks(ItemStack item, short blocks)
    {
        //Because we are working with randoms we need some kind of tolerance
        //double tolerance = ;
        for (int i = 0; i < blocks; i++)
        {
            assertTrue("Durability " + item.getDurability() + " after " + i + "/" + blocks + " runs", item.getDurability() < item.getType().getMaxDurability());
            item = UtilityModule.damage(item, blocks);
        }
        assertTrue("Remaining durability " + (item.getType().getMaxDurability() - item.getDurability()),
                item.getDurability() >= item.getType().getMaxDurability());
    }*/
}
