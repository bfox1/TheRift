package io.github.bfox1.TheRift.common.items.essencecontainer;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 12/8/2016.
 */
public interface IRiftItemEssenceContainer
{
    ItemStack addReToStack(ItemStack stack, int increment);

    ItemStack removeReFromStack(ItemStack stack, int decrement);

    IRiftEssence getEssence();

    boolean canAddAmount(ItemStack stack, int i);

    boolean canRemoveAmount(ItemStack stack, int i);



}
