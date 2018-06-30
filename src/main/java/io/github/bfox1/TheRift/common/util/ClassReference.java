package io.github.bfox1.TheRift.common.util;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;

import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

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
