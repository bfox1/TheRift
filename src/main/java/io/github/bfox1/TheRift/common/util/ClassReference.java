package io.github.bfox1.TheRift.common.util;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;

/**
 * Created by rober on 5/24/2016.
 */
public class ClassReference
{

    //public  Minecraft getMinecraft()
   // {
      //  return Minecraft.getMinecraft();
   // }

    public void sendPlayerMessage(String message, EntityPlayer player)
    {
        player.sendMessage(new TextComponentString(message));
    }
}
