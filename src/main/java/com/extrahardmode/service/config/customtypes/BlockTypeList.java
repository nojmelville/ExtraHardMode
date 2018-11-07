package com.extrahardmode.service.config.customtypes;


import org.bukkit.block.Block;

import java.util.*;

/**
 * @deprecated BlockType is deprecated
 */
@Deprecated
public class BlockTypeList
{
    private Map<Integer, BlockType> blockTypeMap = new HashMap<Integer, BlockType>();
    /**
     * Empty List with no values
     */
    public final static BlockTypeList EMPTY_LIST = new BlockTypeList();


    public BlockTypeList()
    {
    }


    public BlockTypeList(Collection<BlockType> blockTypes)
    {
        for (BlockType blockType : blockTypes)
            add(blockType);
    }


    public boolean contains(Block block)
    {
        BlockType type = blockTypeMap.get(block.getType().getId());
        return type != null && type.matches(block);
    }


    public boolean contains(int blockId)
    {
        return blockTypeMap.containsKey(blockId);
    }


    public void add(BlockType blockType)
    {
        //merge meta if exists
        if (blockTypeMap.containsKey(blockType.getBlockId()))
        {
            Set<Short> merged = blockTypeMap.get(blockType.getBlockId()).getAllMeta();
            merged.addAll(blockType.getAllMeta());
            blockType = new BlockType(blockType.getBlockId(), merged);
        }
        blockTypeMap.put(blockType.getBlockId(), blockType);
    }


    public BlockType get(int blockId)
    {
        return blockTypeMap.get(blockId);
    }


    public Iterator iterator()
    {
        return blockTypeMap.keySet().iterator();
    }


    public BlockType[] toArray()
    {
        return blockTypeMap.values().toArray(new BlockType[blockTypeMap.size()]);
    }
}
