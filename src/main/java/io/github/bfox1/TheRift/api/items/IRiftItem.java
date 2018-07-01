package io.github.bfox1.TheRift.api.items;

import net.minecraft.item.Item;

/**
 * Created by bfox1 on 12/2/2016.
 */
public interface IRiftItem {

    /**
     * Method for an RiftItem to set the registry name.
     * @param s
     * @return
     */
    IRiftItem setRegName(String s);

    /**
     * Method to get the Item Object from a RiftItem Object.
     * @return
     */
    Item getItem();
}
