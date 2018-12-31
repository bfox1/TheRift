package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftCreeper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by bfox1 on 12/12/2016.
 */
public class DebugItem extends RiftItem
{


    public void onRightClick(BlockPos pos, World world)
    {
        if(!world.isRemote) {
            EntityRiftCreeper player = new EntityRiftCreeper(world);
            player.setPosition(pos.getX(), pos.getY(), pos.getZ());

            world.spawnEntity(player);
        }
    }
}
