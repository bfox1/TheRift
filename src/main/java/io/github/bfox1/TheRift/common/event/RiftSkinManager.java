package io.github.bfox1.TheRift.common.event;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.io.File;

/**
 * Created by bfox1 on 12/12/2016.
 */
public class RiftSkinManager extends SkinManager
{
    public RiftSkinManager(TextureManager textureManagerInstance, File skinCacheDirectory, MinecraftSessionService sessionService)
    {
        super(textureManagerInstance, skinCacheDirectory, sessionService);
    }


    @SideOnly(Side.CLIENT)
    public interface SkinAvailableCallback
    {
        void skinAvailable(MinecraftProfileTexture.Type typeIn, ResourceLocation location, MinecraftProfileTexture profileTexture);
    }
}
