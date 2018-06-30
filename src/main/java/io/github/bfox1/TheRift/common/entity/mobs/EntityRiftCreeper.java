package io.github.bfox1.TheRift.common.entity.mobs;

import io.github.bfox1.TheRift.api.items.IRiftItem;
import io.github.bfox1.TheRift.common.world.storage.loot.RiftLootTableList;
import io.netty.util.internal.ThreadLocalRandom;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.*;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.lang.reflect.Field;

/**
 * Created by bfox1 on 12/3/2016.
 */
public class EntityRiftCreeper extends EntityCreeper
{

    private EntityPlayer player;
    private BlockPos lastKnownPos;
    private int tick;
    private int count;
    private int maxSpawn = 3;
    private boolean flag = false;
    private int tries;

    public EntityRiftCreeper(World worldIn)
    {

        super(worldIn);
       // if(!worldIn.isRemote)
        try
        {
            Field f = EntityRiftCreeper.class.getSuperclass().getDeclaredField("explosionRadius");
            f.setAccessible(true);

            //System.out.println(f.get(this));
            f.set(this, 4);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Nullable
    @Override
    protected ResourceLocation getLootTable()
    {
        return RiftLootTableList.RIFT_CREEPER;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        if(!world.isRemote)
        if(flag)
        {
            tick++;
            if(tick == 10 && count < 3)
            {
                EntityZombie zombie = new EntityZombie(world);

                BlockPos ps = determinePositionLocation();
                zombie.setPosition(ps.getX(), ps.getY(), ps.getZ());
                if(player != null)
                zombie.setAttackTarget(player);

                zombie.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30);
                zombie.addPotionEffect(new PotionEffect(Potion.getPotionById(1), 1000, 1));
                zombie.addPotionEffect(new PotionEffect(Potion.getPotionById(11), 1000, 2));


                this.world.spawnEntity(zombie);
                tick = 0;
                count++;
            }
            if(count == 3)
            {
                tick = 0;
                flag = false;
                this.isDead = true;
            }
        }

        if(this.isDead && count < maxSpawn)
        {
            this.isDead = false;
            flag = true;
            this.lastKnownPos = this.getPosition();
        }
    }

    /**
     * Called when the mob's health reaches 0.
     */
    public void onDeath(DamageSource cause)
    {
        super.onDeath(cause);

        if (this.world.getGameRules().getBoolean("doMobLoot"))
        {
            if(cause.getTrueSource() instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer)cause.getTrueSource();
                ItemStack stack = player.getHeldItemMainhand();

                if(stack != null)
                if(stack.getItem() instanceof IRiftItem)
                {
                    this.entityDropItem(new ItemStack(Items.ENDER_PEARL, ThreadLocalRandom.current().nextInt(5,8)), 0.0F);
                }
            }
            if(!cause.isCreativePlayer())
            {
                if(cause.getTrueSource() instanceof EntityPlayer)
                this.player = (EntityPlayer) cause.getTrueSource();
            }
        }

    }


    private BlockPos determinePositionLocation()
    {
        BlockPos pos = new BlockPos(ThreadLocalRandom.current().nextInt(-1, 1) + lastKnownPos.getX(), ThreadLocalRandom.current().nextInt(-1,1) + lastKnownPos.getY(), ThreadLocalRandom.current().nextInt(-1, 1) + lastKnownPos.getZ());
        World world = this.world;

        if(world.isAirBlock(pos) || tries > 5)
        {
            this.tries = 0;
            return pos;
        }
        else
        {
            tries++;
            return determinePositionLocation();
        }

    }


}
