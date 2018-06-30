package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.api.items.IRiftItem;
import net.minecraft.item.Item;

/**
 * Created by bfox1 on 4/3/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class RiftItem extends Item implements IRiftItem
{
    public RiftItem()
    {
        super();
        //this.setCreativeTab(RiftTabManager.SaoItems);
    }


    public int getItemID()
    {
        return Item.getIdFromItem(this);
    }


    public IRiftItem setRegName(String s)
    {
        this.setRegistryName(s);
        return this;
    }

    @Override
    public Item getItem() {
        return this;
    }
}
