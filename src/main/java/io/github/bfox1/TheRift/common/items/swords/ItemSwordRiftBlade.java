package io.github.bfox1.TheRift.common.items.swords;

import io.github.bfox1.TheRift.api.items.IRiftItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;

/**
 * Created by bfox1 on 12/2/2016.
 */
public class ItemSwordRiftBlade extends ItemSword implements IRiftItem
{
    public ItemSwordRiftBlade(ToolMaterial material)
    {
        super(material);
    }

    public ItemSwordRiftBlade setRegName(String s)
    {
        this.setRegistryName(s);
        return this;
    }

    @Override
    public Item getItem() {
        return this;
    }
}
