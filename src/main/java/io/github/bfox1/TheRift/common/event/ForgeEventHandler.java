package io.github.bfox1.TheRift.common.event;



import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import io.github.bfox1.TheRift.common.TheRift;
import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftCreeper;
import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftSpecialPlayer;
import io.github.bfox1.TheRift.common.entity.tileentity.AbstractRiftTileEntity;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftChest;
import io.github.bfox1.TheRift.common.items.DebugItem;
import io.github.bfox1.TheRift.common.items.ItemRiftFluxCapacitor;
import io.github.bfox1.TheRift.common.items.RiftMechanism;
import io.github.bfox1.TheRift.common.items.essencecontainer.IRiftItemEssenceContainer;
import io.github.bfox1.TheRift.common.items.essencecontainer.ItemRiftEssenceContainer;
import io.github.bfox1.TheRift.common.items.swords.ItemSwordRiftBlade;
import io.github.bfox1.TheRift.common.proxy.ClientProxy;
import io.github.bfox1.TheRift.common.proxy.CommonProxy;
import io.github.bfox1.TheRift.common.proxy.ServerProxy;
import io.github.bfox1.TheRift.common.util.MessageUtility;
import io.github.bfox1.TheRift.common.world.RiftCreeperExplosion;
import io.github.bfox1.TheRift.init.ItemInit;
import io.github.bfox1.TheRift.riftessence.RiftEssenceMobMap;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.entity.EntityEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.event.world.ExplosionEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.fml.common.network.NetworkEventFiringHandler;
import scala.tools.nsc.transform.SpecializeTypes;

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
        System.out.println("POOP");
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

        if(!e.getWorld().isRemote)
        {
            if(!e.getItemStack().isEmpty() && e.getItemStack().getItem() instanceof RiftMechanism)
            {
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
