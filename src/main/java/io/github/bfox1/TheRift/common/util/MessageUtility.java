package io.github.bfox1.TheRift.common.util;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.BossInfo;

/**
 * Created by bfox1 on 11/25/2016.
 */
public class MessageUtility
{
    public enum MessageType
    {
        INVALID_LINKED_SIDE,
        CANNOT_LINK_ITSELF,
        LINKED_SIDE_OCCUPIED,
        NOT_TILE_ENTITY,
        HOME_BLOCK_NOT_VALID,
        GUEST_BLOCK_NOT_VALID,
        VALID_HOME_LINK,
        VALID_GUEST_LINK,
        CONNECTION_LINKED_COMPLETE,

    }


    public static void sendPlayerMessage(EntityPlayer player, MessageType type)
    {
        String msg = "No Declared Message, this is a Bug";
        switch (type)
        {
            case INVALID_LINKED_SIDE: msg = "This is not a valid Side to link to."; break;
            case CANNOT_LINK_ITSELF: msg = "Cannot link to itself silly!"; break;
            case LINKED_SIDE_OCCUPIED: msg = "This side is already linked to another!"; break;
            case NOT_TILE_ENTITY: msg = "This Block is not a Tile Entity!"; break;
            case HOME_BLOCK_NOT_VALID: msg = "Home Block cannot Link! Side is already occupied!"; break;
            case GUEST_BLOCK_NOT_VALID: msg = "Guest Block cannot Link! Side must already occupied!"; break;
            case VALID_HOME_LINK: msg = "Successfully selected Home Block!"; break;
            case VALID_GUEST_LINK: msg = "Successfully selected Guest Block!"; break;
            case CONNECTION_LINKED_COMPLETE: msg = "Established Connection between two Blocks!"; break;
            default: break;
        }
        System.out.println("SPM-1");
       // player.sendStatusMessage(new TextComponentString(BossInfo.Color.RED + msg), true);

        player.sendMessage(new TextComponentString(BossInfo.Color.RED + msg));
    }
}
