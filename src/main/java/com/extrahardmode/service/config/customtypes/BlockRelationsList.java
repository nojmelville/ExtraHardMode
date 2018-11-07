package com.extrahardmode.service.config.customtypes;


import org.bukkit.block.Block;

import java.util.HashMap;
import java.util.Map;

/**
 * Holds a relationship. BlockTypes can be retrieved by their key BlockType. F.e stone -> cobblestone
 *
 * @deprecated Uses deprecated BlockTypes
 *
 * Only used for the "soften surrounding stone" feature, turning one block (e.g. stone) into another (e.g. cobblestone)
 */
@Deprecated
public class BlockRelationsList
{
    private Map<BlockType, BlockType> mBlockRelations = new HashMap<BlockType, BlockType>();
    /**
     * An empty list
     */
    public final static BlockRelationsList EMPTY_LIST = new BlockRelationsList();


    /**
     * Add Relationships from a string loaded from a config
     *
     * @param configString expected format: block1@meta-block2@meta. If it doesn't match it won't be added
     */
    public void addFromConfig(String configString)
    {
        if (configString == null)
            return;

        String[] splitted = configString.split("-");
        if (splitted.length < 2)
            return;

        BlockType block1 = BlockType.loadFromConfig(splitted[0]);
        BlockType block2 = BlockType.loadFromConfig(splitted[1]);
        if (block1.isValid() && block2.isValid())
            add(block1, block2);
    }


    /**
     * Saves this so it can be from the config again
     *
     * @return list of block relations
     */
    public String[] toConfigStrings()
    {
        if (mBlockRelations.size() == 0)
            return new String[]{""};
        String[] configStrings = new String[mBlockRelations.size()];
        int i = 0;
        for (Map.Entry<BlockType, BlockType> relation : mBlockRelations.entrySet())
        {
            configStrings[i] = relation.getKey().toString() + "-" + relation.getValue().toString();
            i++;
        }
        return configStrings;
    }


    public void add(BlockType block1, BlockType block2)
    {
        mBlockRelations.put(block1, block2);
    }


    public BlockType get(BlockType blockType)
    {
        for (Map.Entry<BlockType, BlockType> entry : mBlockRelations.entrySet())
            if (entry.getKey().equals(blockType))
                return entry.getValue();
        return null;
    }


    public BlockType get(Block block)
    {
        for (Map.Entry<BlockType, BlockType> entry : mBlockRelations.entrySet())
            if (entry.getKey().matches(block.getType().getId(), block.getData()))
                return entry.getValue();
        return null;
    }


    public boolean contains(Block block)
    {
        return get(block) != null;
    }
}
