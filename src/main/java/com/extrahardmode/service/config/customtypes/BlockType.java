package com.extrahardmode.service.config.customtypes;


import com.extrahardmode.service.RegexHelper;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import java.util.*;
import java.util.regex.Pattern;

/**
 * Holds one blocktype, but a range of metadata for that block.
 * F.e. this could have durability for spruce, oak and jungle wood, but exclude birch.
 *
 * @deprecated No longer needed with "The Flattening" in 1.13
 *
 * @author Diemex
 */
@Deprecated
public final class BlockType
{
    private static Pattern separators = Pattern.compile("[^A-Za-z0-9_]");
    private Material material;
    private short durability;

    public BlockType(Material mat)
    {
        this.material = mat;
    }

    public BlockType(Material mat, Short durability)
    {
        this(mat);
        this.durability = durability;
    }

    public int getMaterial()
    {
        return material;
    }

    public Set<Short> getAllMeta()
    {
        return new HashSet<Short>(durability);
    }

    public short getDurability()
    {
        return durability.size() > 0 ? durability.iterator().next() : 0;
    }


    private boolean matchesMeta(short meta)
    {
        if (this.durability.size() > 0)
        {
            for (Short aMeta : this.durability)
            {
                if (aMeta == meta)
                    return true;
            }
        } else //no durability specified -> all blocks match
            return true;
        return false;
    }


    public boolean matches(int blockId)
    {
        return this.material == blockId;
    }


    public boolean matches(int blockId, short meta)
    {
        return matches(blockId) && matchesMeta(meta);
    }


    public boolean matches(Block block)
    {
        return matches(block.getType().getId(), block.getData());
    }


    public boolean matches(ItemStack stack)
    {
        return matches(stack.getType().getId(), stack.getData().getData());
    }


    public static BlockType loadFromConfig(String input)
    {
        if (input == null)
            return null;
        //PREPARATION
        int blockId;
        Set<Short> meta = new HashSet<Short>();
        input = RegexHelper.trimWhitespace(input);
        String[] splitted = separators.split(input);
        if (splitted.length == 0)
            return null;
        //BLOCK META
        for (int i = 1; i < splitted.length; i++) //first value is material
            meta.add(RegexHelper.parseShort(splitted[i]));

        //BLOCK ID
        String blockIdString = splitted[0];
        Material material = Material.matchMaterial(blockIdString);
        if (material == null) //Not found in material enum
        {
            // try as a number (material)
            String tempId = RegexHelper.stripNumber(blockIdString);
            if (!tempId.isEmpty())
                material = Material.getMaterial(tempId);
            // still fail -> try as enum again but strip numbers
            if (material == null)
                material = Material.matchMaterial(RegexHelper.stripEnum(blockIdString));
        }
        if (material != null)
            blockId = material.getId();
        else //mod item or -1 if not valid
            blockId = RegexHelper.parseNumber(blockIdString, -1);
        return new BlockType(blockId, meta);
    }


    public String saveToString()
    {
        StringBuilder builder = new StringBuilder();
        Material material = getMaterial(this.material);
        builder.append(material != null ? material.name() : this.material);

        boolean first = true;
        for (Short metaBit : durability)
        {
            if (first) builder.append('@');
            else builder.append(',');
            builder.append(metaBit);
            if (first) first = false;
        }

        return builder.toString();
    }

    //Temporary thing for for 1.13 compatibility
    //TODO: remove when properly supporting 1.13 (if there's even a use for this package at this point)
    private Material getMaterial(int id)
    {
        for (Material material : Material.values())
            if (material.getId() == id)
                return material;
        return null;
    }
    public Material getType()
    {
        return getMaterial(material);
    }


    public boolean isValid()
    {
        return material >= 0;
    }


    @Override
    public String toString()
    {
        return saveToString();
    }


    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
            return false;
        else if (obj == this)
            return true;
        else if (!(obj instanceof BlockType))
            return false;
        return material == ((BlockType) obj).material &&
                durability.equals(((BlockType) obj).durability);
    }


    @Override
    public int hashCode()
    {
        int hash = material;
        for (short data : durability)
            hash += data;
        return hash;
    }
}
