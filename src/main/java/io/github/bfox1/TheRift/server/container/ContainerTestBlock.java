package io.github.bfox1.TheRift.server.container;

import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;

/**
 * Created by bfox1 on 11/5/2016.
 */
public class ContainerTestBlock extends net.minecraft.inventory.Container
{
    private TileEntityRiftChest block;


    public ContainerTestBlock(InventoryPlayer player, TileEntityRiftChest testBlock)
    {
        this.block = testBlock;

        for(int i = 0; i < 3; ++i)
        {
            for(int j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(testBlock,j+i * 9 + 9, 8+j*18, 84 + i *18));
            }
        }
        int i;
        for (i = 0; i < 3; ++i)
        {
            for (int j = 0; j < 9; ++j)
            {
          //      this.addSlotToContainer(new Slot(player, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
          //  this.addSlotToContainer(new Slot(player, i, 8 + i * 18, 142));
        }
    }



    public void detectAndSendChanges()
    {
        super.detectAndSendChanges();


    }

    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return this.block.isUsableByPlayer(playerIn);
    }
}
