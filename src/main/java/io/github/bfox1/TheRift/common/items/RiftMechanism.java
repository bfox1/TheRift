package io.github.bfox1.TheRift.common.items;

import io.github.bfox1.TheRift.common.entity.tileentity.AbstractRiftTileEntity;
import io.github.bfox1.TheRift.common.proxy.ServerProxy;
import io.github.bfox1.TheRift.common.util.MessageUtility;
import io.github.bfox1.TheRift.riftessence.RiftLinkedSide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bfox1 on 11/10/2016.
 */
public class RiftMechanism extends RiftItem
{

    private RiftLinkedSide.EnumRiftAction action;
    public RiftMechanism()
    {
        this.action = RiftLinkedSide.EnumRiftAction.INSERT;
    }

    public RiftLinkedSide.EnumRiftAction getRiftAction()
    {
        return action;
    }

    public void onLeftClick(ItemStack stack, World world, EntityPlayer player, EnumHand hand)
    {
        if(EnumHand.MAIN_HAND == hand && !player.isSneaking())
        {
            this.action = RiftLinkedSide.EnumRiftAction.getNext(action);
            player.sendMessage(new TextComponentString("Mode: " + action.name().toLowerCase()));
        }
        else if(player.isSneaking())
        {
            ServerProxy.riftLinkedConnection.remove(player.getUniqueID());
        }
    }

    public void addLinkedSides(PlayerInteractEvent.RightClickBlock event)
    {
        World world = event.getWorld();
        EntityPlayer player = event.getEntityPlayer();

        TileEntity entity = world.getTileEntity(event.getPos());

        if(entity != null)
        {
            //Checking if TileEntity is Rift Entity//
            if(isRiftEntity(entity, world))
            {

                AbstractRiftTileEntity rEntity = (AbstractRiftTileEntity) entity;
                AbstractRiftTileEntity originalREntity;

                //Checks if Player has any established connections already//
                if(hasPlayerConnection(player))
                {

                    RiftLinkedSide[] sides = this.getPlayerLinkedSides(player);
                    originalREntity = (AbstractRiftTileEntity) world.getTileEntity(sides[0].getPos());
                    sides[1] = new RiftLinkedSide(event.getPos(), event.getFace(), this.getRiftAction(), false);

                    //Checks if Connection is to itself//
                    if(isConnectingToSelf(sides, world))
                    {
                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.CANNOT_LINK_ITSELF);
                    }
                    else if(!originalREntity.canAddLinkedSide(sides[1]))
                    {

                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.GUEST_BLOCK_NOT_VALID);
                    }
                    else
                    {

                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.VALID_GUEST_LINK);
                        this.applyAttachments(world, player, sides[0], sides[1], true);
                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.CONNECTION_LINKED_COMPLETE);

                    }
                    this.clearPlayerLinkedConnection(player);
                    event.setCanceled(true);

                }
                else
                {

                    RiftLinkedSide side = new RiftLinkedSide(event.getPos(), event.getFace(), this.action, true);

                    RiftLinkedSide[] rEntitySides = rEntity.linkedSides;
                    boolean flag = true;
                    for(RiftLinkedSide connected : rEntitySides)
                    {

                        if(connected != null)
                        {

                            if(side.getX() == connected.getX() && side.getY() == connected.getY() && side.getZ() == connected.getZ())
                            {

                                if(side.getFace() == connected.getFace())
                                {

                                    MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.LINKED_SIDE_OCCUPIED);
                                    flag = false;
                                    break;
                                }
                            }
                        }
                    }
                    if(flag)
                    {
                        this.establishPlayerLinkedConnection(side, player);
                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.VALID_HOME_LINK);
                    }
                }

            }
            else
            {
                if(hasPlayerConnection(player))
                {
                    RiftLinkedSide[] sides = this.getPlayerLinkedSides(player);
                    AbstractRiftTileEntity rEntity = (AbstractRiftTileEntity) world.getTileEntity(sides[0].getPos());
                    sides[1] = new RiftLinkedSide(event.getPos(), event.getFace(), this.action, false);

                    if(rEntity.canAddLinkedSide(sides[1]))
                    {
                        applyAttachments(world, player, sides[0], sides[1], false);
                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.CONNECTION_LINKED_COMPLETE);
                    }
                    else
                    {
                        MessageUtility.sendPlayerMessage(player, MessageUtility.MessageType.GUEST_BLOCK_NOT_VALID);
                    }
                    this.clearPlayerLinkedConnection(player);
                    event.setCanceled(true);
                }
            }
        }
    }

    private boolean hasPlayerConnection(EntityPlayer player)
    {
        return ServerProxy.riftLinkedConnection.containsKey(player.getUniqueID());
    }

    private RiftLinkedSide[] getPlayerLinkedSides(EntityPlayer player)
    {
        if(hasPlayerConnection(player))
        {
            return ServerProxy.riftLinkedConnection.get(player.getUniqueID());
        }

        return null;
    }

    /**
     * Checks to see if the Player Connection Sides are not connected to the same block.
     * IE: Rift chest being connected to the same Rift Chest
     * @param sides
     * @param world
     * @return
     */
    private boolean isConnectingToSelf(RiftLinkedSide[] sides, World world)
    {
        RiftLinkedSide sideA = sides[0];
        RiftLinkedSide sideB = sides[1];

        return world.getTileEntity(sideA.getPos()) == world.getTileEntity(sideB.getPos());
    }

    private boolean isSideTileEntity(RiftLinkedSide side, World world)
    {
        return world.getTileEntity(side.getPos()) != null;
    }

    private boolean isRiftEntity(TileEntity entity, World world)
    {
        return entity instanceof AbstractRiftTileEntity;
    }

    private void establishPlayerLinkedConnection(RiftLinkedSide side, EntityPlayer player)
    {
        ServerProxy.riftLinkedConnection.put(player.getUniqueID(), new RiftLinkedSide[]{side, null});
    }

    private void clearPlayerLinkedConnection(EntityPlayer player)
    {
        ServerProxy.riftLinkedConnection.remove(player.getUniqueID());
    }

    public boolean applyAttachments(World world, EntityPlayer player, RiftLinkedSide sideA, RiftLinkedSide sideB, boolean allREntity)
    {
        AbstractRiftTileEntity oEntity = (AbstractRiftTileEntity) world.getTileEntity(sideA.getPos());

        if(allREntity)
        {
            AbstractRiftTileEntity cEntity = (AbstractRiftTileEntity) world.getTileEntity(sideB.getPos());

            oEntity.addLinkedSide(sideB);
            cEntity.addLinkedSide(sideA);

        }
        else
        {
            oEntity.addLinkedSide(oEntity.generateHashCode(sideB));

        }
        return true;
    }

    public void displayConnectedSides(PlayerInteractEvent.RightClickBlock e)
    {
        World world = e.getWorld();
        EntityPlayer player = e.getEntityPlayer();

        TileEntity entiy = world.getTileEntity(e.getPos());

        if(entiy != null && entiy instanceof AbstractRiftTileEntity)
        {
            AbstractRiftTileEntity entity = (AbstractRiftTileEntity) entiy;

            List<String> sides = new ArrayList<>();

            for(RiftLinkedSide side : entity.linkedSides)
            {
                String s = "";
                if(side != null)
                {
                    TileEntity ef = world.getTileEntity(side.getPos());
                    if(ef != null)
                    {
                        if (ef instanceof IInventory)
                        {
                            s = ((IInventory) ef).getName();
                        }
                        else
                        {
                            s = "unknown TE Name";
                        }

                        s += " : " + side.getX() + ", " + side.getY() + ", " + side.getZ() + ", " + " Face: " + side.getFace() + " Home: " + side.isMasterLink();
                        sides.add(s);
                    }
                }
            }

            if(sides.size() > 0)
            {
                for(String s : sides)
                {
                    player.sendMessage(new TextComponentString(s));
                }
            }
        }
    }
}