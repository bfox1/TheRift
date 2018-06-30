package io.github.bfox1.TheRift.common.entity.mobs;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.UUID;

/**
 * Created by bfox1 on 12/12/2016.
 */
public class EntityRiftSpecialPlayer extends EntityMob
{
    public EntityRiftSpecialPlayer(World worldIn)
    {
        super(worldIn);
        this.setCustomNameTag("erinjackson");
    }

}
