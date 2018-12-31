package io.github.bfox1.TheRift.common.items.essencecontainer;

import io.github.bfox1.TheRift.api.riftessence.IRiftEssence;
import io.github.bfox1.TheRift.api.riftessence.essencecontainer.IRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.RiftItem;
import net.minecraft.item.ItemStack;

/**
 * Created by bfox1 on 12/8/2016.
 */
public abstract class ItemRiftEssenceContainer extends RiftItem implements IRiftItemEssenceContainer, IRiftEssenceContainer
{
    @Override
    public ItemStack addReToStack(ItemStack stack, int increment)
    {
        boolean flag = stack.getItemDamage() == 0;
        int rem = stack.getMaxDamage() -  stack.getItemDamage(); // ex: if flag = true, 7500 - 0, else, 7500 - 7495
        int dmg = flag ? rem - (increment) : rem + (increment); //ex: if true, 7500 - (5+6) else 5 + (5+6)
        stack.setItemDamage(flag ? dmg : stack.getMaxDamage() - dmg);
        if(this.getEssence().getRiftEssence() == 0)
        {
            this.getEssence().addRiftEssence(stack.getMaxDamage() - stack.getItemDamage());
        }
        else
            this.getEssence().addRiftEssence(increment + 6);

        return stack;
    }

    @Override
    public ItemStack removeReFromStack(ItemStack stack, int decrement)
    {
        boolean flag = stack.getItemDamage() == 0;

        int addedAm = stack.getItemDamage() + decrement;

        this.getEssence().removeRiftEssence(decrement);

        stack.setItemDamage(addedAm);
        return stack;
    }


    /**
     * THESE SHOULD NEVER BE USED! Please Use IRiftItemEssenceContainer#addReToStack(ItemStack stack) to add.
     * @param essence
     * @param increment
     */
    @Override
    public final void addRiftEssence(IRiftEssence essence, int increment)
    {

    }

    /**
     * THESE SHOULD NEVER BE USED! Please Use IRiftItemEssenceContainer#removeReFromStack(ItemStack stack) to remove.
     * @param essence
     * @param decrement
     */
    @Override
    public final void decreRiftEssence(IRiftEssence essence, int decrement)
    {

    }


    public final boolean canRemoveAmount(ItemStack stack, int i)
    {
        int added = stack.getItemDamage() + i;
        int essence = this.getEssence().getRiftEssence() - i;

        return added >= stack.getMaxDamage() && essence <= 0;
    }

    public final boolean canAddAmount(ItemStack stack, int i) {
        boolean a = false;
        boolean b = false;
        int added = stack.getItemDamage() - i;
        System.out.println(added);
        int essence = this.getEssence().getRiftEssence() + i;

        a = stack.getItemDamage() == 0 || added > 0;

        b = this.getEssence().getMaxRiftEssence() > essence;

        return a && b;
    }

    public static final IRiftItemEssenceContainer getFromItem(ItemStack stack)
    {
        return (IRiftItemEssenceContainer)stack.getItem();
    }

}
