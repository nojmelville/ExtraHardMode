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

package com.extrahardmode.service.config;


/**
 * Represents a configuration node.
 */
public interface ConfigNode
{

    /**
     * Get the config path.
     *
     * @return Config path.
     */
    public String getPath();

    /**
     * Get the variable type.
     *
     * @return Variable type.
     */
    public VarType getVarType();

    /**
     * Get the SubType Used especially for verfification
     *
     * @return SubType
     */
    public SubType getSubType();

    /**
     * Get the default value.
     *
     * @return Default value.
     */
    public Object getDefaultValue();

    /**
     * Get a value to disable this Node
     */
    public Object getValueToDisable();

    /**
     * Variable Types.
     */
    public enum VarType
    {
        STRING,
        INTEGER,
        DOUBLE,
        BOOLEAN,
        LIST,
        COLOR,
        POTION_EFFECT,
        MATERIAL,
        @Deprecated MATERIAL_LIST,
        @Deprecated BLOCK_RELATION_LIST,
        COMMENT
    }


    /**
     * SubTypes, like percentage, y-value, custom etc
     */
    public enum SubType
    {
        PERCENTAGE,
        Y_VALUE,
        HEALTH,
        NATURAL_NUMBER,
        PLAYER_NAME //limited to 16 chars
    }
}
