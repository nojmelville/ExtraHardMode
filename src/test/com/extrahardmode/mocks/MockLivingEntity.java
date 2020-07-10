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

package com.extrahardmode.mocks;


import org.bukkit.World;
import org.bukkit.entity.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * A very basic Mock only overrides
 * <pre>
 *     getWorld
 * </pre>
 */
public class MockLivingEntity
{
    private LivingEntity entity;


    /**
     * Basic constructor
     *
     * @param world the world where this Entity resides in
     * @param type  to stub
     */
    public MockLivingEntity(World world, EntityType type)
    {
        mockForType(type);
        when(entity.getType()).thenReturn(type);
        when(entity.getWorld()).thenReturn(world);
    }


    /**
     * Get the mocked Object
     */
    public LivingEntity get()
    {
        return entity;
    }


    private void mockForType(EntityType type)
    {
        switch (type)
        {
            case BLAZE:
                entity = mock(Blaze.class);
                break;
            case CAVE_SPIDER:
                entity = mock(CaveSpider.class);
                break;
            case ENDERMAN:
                entity = mock(Enderman.class);
                break;
            case GHAST:
                entity = mock(Ghast.class);
                break;
            case ZOMBIFIED_PIGLIN:
                entity = mock(PigZombie.class);
                break;
            case SILVERFISH:
                entity = mock(Silverfish.class);
                break;
            case SKELETON:
                entity = mock(Skeleton.class);
                break;
            case SLIME:
                entity = mock(Slime.class);
                break;
            case SPIDER:
                entity = mock(Spider.class);
                break;
            case WITCH:
                entity = mock(Witch.class);
                break;
            case WITHER:
                entity = mock(Wither.class);
                break;
            case ZOMBIE:
                entity = mock(Zombie.class);
                break;
            default:
                entity = mock(LivingEntity.class);
                break;
        }
    }
}
