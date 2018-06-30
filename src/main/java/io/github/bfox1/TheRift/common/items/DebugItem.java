package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftSpecialPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldInfo;

/**
 * Created by bfox1 on 12/12/2016.
 */
public class DebugItem extends RiftItem
{


    public void onRightClick(BlockPos pos, World world)
    {
        if(!world.isRemote) {
            EntityRiftSpecialPlayer player = new EntityRiftSpecialPlayer(world);
            player.setPosition(pos.getX(), pos.getY(), pos.getZ());

            world.spawnEntity(player);
        }
    }
}
