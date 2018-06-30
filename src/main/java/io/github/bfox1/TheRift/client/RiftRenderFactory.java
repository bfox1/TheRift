package io.github.bfox1.TheRift.client;


import io.github.bfox1.TheRift.RenderRiftPlayer;
import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftCreeper;
import io.github.bfox1.TheRift.common.entity.mobs.EntityRiftSpecialPlayer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;


import java.lang.reflect.InvocationTargetException;


/**
 * Created by bfox1 on 12/6/2016.
 */
public class RiftRenderFactory implements IRenderFactory
{
    private final Class<? extends Render> renderClassObject;

    /**
     * Using a Universal and simplistic method of creating Factories for each Entity,
     * All that is required, is to pass down the Render.class Object and watch as it creates
     * a new instance of it. Be warned, adding anything to the constructor will result in ERROR!.
     * @param clazz
     */
    private RiftRenderFactory(Class<? extends Render> clazz)
    {
        this.renderClassObject = clazz;
    }

    public static RiftRenderFactory RIFT_CREEPER_FACTORY()
    {
        return new RiftRenderFactory(RenderRiftCreeper.class);
    }

    public static RiftRenderFactory CUSTOM_RENDER_FACTORY(Class<? extends Render> clazz)
    {
        return new RiftRenderFactory(clazz);
    }

    @Override
    public Render createRenderFor(RenderManager manager)
    {
        try
        {
            return renderClassObject.getConstructor(RenderManager.class).newInstance(new Object[] {manager});
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * To register All Rendered Entities for Client Proxy Only!
     */
    public static void registerRenderEntityFactory()
    {
        RenderingRegistry.registerEntityRenderingHandler(EntityRiftCreeper.class, RiftRenderFactory.RIFT_CREEPER_FACTORY());
        RenderingRegistry.registerEntityRenderingHandler(EntityRiftSpecialPlayer.class, RiftRenderFactory.CUSTOM_RENDER_FACTORY(RenderRiftPlayer.class));
    }


}
