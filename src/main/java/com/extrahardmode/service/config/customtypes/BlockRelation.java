package com.extrahardmode.service.config.customtypes;


/**
 * Simple Pair
 *
 * @deprecated Unused, uses BlockType
 */
@Deprecated
public final class BlockRelation
{
    private BlockType mBlock1 = null;
    private BlockType mBlock2 = null;


    public BlockRelation(BlockType block1, BlockType block2)
    {
        this.mBlock1 = block1;
        this.mBlock2 = block2;
    }


    public BlockType getKeyBlock()
    {
        return mBlock1;
    }


    public BlockType getValueBlock()
    {
        return mBlock2;
    }
}
