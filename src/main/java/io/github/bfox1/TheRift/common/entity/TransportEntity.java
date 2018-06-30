package io.github.bfox1.TheRift.common.entity;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Created by bfox1 on 11/17/2016.
 */
public class TransportEntity extends EntityItem
{
    public TransportEntity(World worldIn, double x, double y, double z, ItemStack stack) {
        super(worldIn, x, y, z, stack);
        this.noClip = true;


    }


}
