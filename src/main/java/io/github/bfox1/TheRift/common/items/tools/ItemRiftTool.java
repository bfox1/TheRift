package io.github.bfox1.TheRift.common.items.tools;

import io.github.bfox1.TheRift.api.items.IRiftItem;
import io.github.bfox1.TheRift.common.items.RiftItem;
import io.github.bfox1.TheRift.common.items.essencecontainer.IRiftItemEssenceContainer;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemTool;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Set;

/**
 * Created by bfox1 on 12/9/2016.
 */
public class ItemRiftTool extends ItemTool implements IRiftItem
{
    public ItemRiftTool(float attackDamageIn, float attackSpeedIn, ToolMaterial materialIn, Set<Block> effectiveBlocksIn)
    {
        super(attackDamageIn, attackSpeedIn, materialIn, effectiveBlocksIn);
    }

    @Override
    public IRiftItem setRegName(String s)
    {
        this.setRegistryName(s);
        return this;
    }

    @Override
    public Item getItem()
    {
        return this;
    }


}
