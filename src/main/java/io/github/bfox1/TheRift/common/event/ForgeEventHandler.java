package io.github.bfox1.TheRift.common.event;



import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftCreeper;
import io.github.bfox1.TheRift.common.entity.tileentity.AbstractRiftTileEntity;
import io.github.bfox1.TheRift.common.items.DebugItem;
import io.github.bfox1.TheRift.common.items.RiftMechanism;
import io.github.bfox1.TheRift.common.items.essencecontainer.IRiftItemEssenceContainer;
import io.github.bfox1.TheRift.common.items.essencecontainer.ItemRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.swords.ItemSwordRiftBlade;
import io.github.bfox1.TheRift.common.world.RiftCreeperExplosion;
import io.github.bfox1.TheRift.riftessence.RiftEssenceMobMap;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * Created by bfox1 on 4/16/2016.
 * Deuteronomy 8:18
 * 1 Peter 4:10
 */
public class ForgeEventHandler
{
    boolean check = false;

    public ForgeEventHandler()
    {

    }


    @SubscribeEvent
    public void onRiftEntityBreakEvent(BlockEvent.BreakEvent event)
    {

        TileEntity entity = event.getWorld().getTileEntity(event.getPos());

        if (entity != null) {
            if (entity instanceof AbstractRiftTileEntity) {
                AbstractRiftTileEntity rEntity = (AbstractRiftTileEntity) entity;

                if (rEntity.hasLinkedTileEntities()) {
                    for (RiftLinkedSide side : rEntity.getLinkedSides()) {
                        if(side != null)
                        {
                            TileEntity connectedEntity = event.getWorld().getTileEntity(side.getPos());

                            if (connectedEntity instanceof AbstractRiftTileEntity)
                            {
                                AbstractRiftTileEntity connectedRiftEntity = (AbstractRiftTileEntity) connectedEntity;

                                connectedRiftEntity.removeLinkedSide(event.getPos(), null);
                            }
                        }
                    }
                }
            }
        }

    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onLeftClickAirEvent(PlayerInteractEvent.LeftClickEmpty event)
    {
            ItemStack stack = event.getItemStack();
            if (!stack.isEmpty())
                if (stack.getItem() instanceof RiftMechanism)
                {
                        RiftMechanism mech = (RiftMechanism) stack.getItem();
                        mech.onLeftClick(stack, event.getWorld(), event.getEntityPlayer(), event.getHand());
                }
    }

    @SubscribeEvent
    public void onLeftClickBlock(PlayerInteractEvent.LeftClickBlock event)
    {
        if(!event.getItemStack().isEmpty())
        if(event.getItemStack().getItem() instanceof RiftMechanism)
        {
            event.setCanceled(true);
        }
    }

    @SubscribeEvent
    public void onRightClickBlock(PlayerInteractEvent.RightClickBlock e)
    {
        System.out.println(":test");
        if(!e.getWorld().isRemote)
        {
            System.out.println("made it through remote" +
                    "");
            if(!e.getItemStack().isEmpty() && e.getItemStack().getItem() instanceof RiftMechanism)
            {
                System.out.println("passed it here. ");
                RiftMechanism mech = (RiftMechanism)e.getItemStack().getItem();

                if(mech.getRiftAction() == RiftLinkedSide.EnumRiftAction.INSERT || mech.getRiftAction() == RiftLinkedSide.EnumRiftAction.EJECT)
                {
                    mech.addLinkedSides(e);
                }
                else if(mech.getRiftAction() == RiftLinkedSide.EnumRiftAction.GUI)
                {
                    mech.displayConnectedSides(e);
                }
                else if(mech.getRiftAction() == RiftLinkedSide.EnumRiftAction.CLEAR)
                {

                }
                e.setCanceled(true);
            }

            if(!e.getItemStack().isEmpty() && e.getItemStack().getItem() instanceof DebugItem)
            {
                DebugItem item = (DebugItem) e.getItemStack().getItem();

                item.onRightClick(e.getPos(), e.getWorld());
                e.setCanceled(true);
            }
        }
    }



    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event)
    {
        Entity damageSource = event.getSource().getTrueSource();
        if(event.getEntity() instanceof EntityLiving)
        if(damageSource instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) damageSource;

            if(!player.getHeldItemMainhand().isEmpty() )
            if(player.getHeldItemMainhand().getItem() instanceof ItemSwordRiftBlade)
            {
                    int i =0;
                    for(ItemStack iStack : player.inventory.mainInventory)
                    {
                        if(!iStack.isEmpty())
                        if(iStack.getItem() instanceof IRiftItemEssenceContainer)
                        {
                            ItemStack nStack = iStack.copy();
                            int adding = (int)( 7.5+ RiftEssenceMobMap.getEntityEssence((EntityLiving) event.getEntityLiving()));
                            IRiftItemEssenceContainer container = ItemRiftEssenceContainer.getFromItem(nStack);

                            if(container.canAddAmount(nStack, adding))
                            {
                                nStack = container.addReToStack(nStack, adding);
                                player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 4.0F, 4.0F);
                                player.inventory.mainInventory.set(i, nStack);
                                //player.inventory.mainInventory[i] = nStack;
                                break;
                            }
                        }
                        i++;
                    }
            }
        }
    }

    @SubscribeEvent
    public void onExplosionEvent(ExplosionEvent e)
    {
        if(e != null)
        if(e.getExplosion().getExplosivePlacedBy() instanceof EntityRiftCreeper)
        {
            RiftCreeperExplosion explosion = new RiftCreeperExplosion(e.getExplosion());

            explosion.doExplosionA();
            explosion.doExplosionB(true);
            e.setCanceled(true);
        }
    }


    @SubscribeEvent
    public void onEntityJoinEvent(EntityJoinWorldEvent event)
    {
        if(!event.getWorld().isRemote)
        {
        }
    }


}
