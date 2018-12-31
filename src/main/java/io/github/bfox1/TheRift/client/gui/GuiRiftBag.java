package io.github.bfox1.TheRift.client.gui;

import io.github.bfox1.TheRift.common.inventory.InventoryRiftBag;
import io.github.bfox1.TheRift.common.items.ItemRiftBag;
import io.github.bfox1.TheRift.common.util.Reference;
import io.github.bfox1.TheRift.server.container.ContainerRiftBag;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

public class GuiRiftBag extends GuiContainer
{

    private static final ResourceLocation PLAYERINVENTORY = new ResourceLocation(Reference.MODID, "textures/gui/container/riftBag.ItemRiftPouch_PlayerInv.png");
    private static final ResourceLocation PouchInv = new ResourceLocation(Reference.MODID, "textures/gui/container/riftBag.ItemRiftPouch_PoughInv.png");
    private static final ResourceLocation ItemFilter = new ResourceLocation(Reference.MODID, "textures/gui/container/riftBag.ItemRiftPouch_ItemFilter.png");
    private static final ResourceLocation RiftLinked = new ResourceLocation(Reference.MODID, "textures/gui/container/riftBag.ItemRiftPouch_RiftLinked.png");

    private final InventoryPlayer player;

    private final InventoryRiftBag inventoryRiftBag;


    public GuiRiftBag(InventoryPlayer player, ItemStack stack)
    {

        super(new ContainerRiftBag(player, ItemRiftBag.getInventory(stack)));
        this.player = player;
        this.inventoryRiftBag = ItemRiftBag.getInventory(stack);

        this.xSize = 175;
        this.ySize = 165;
    }


    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(PouchInv);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);


        //System.out.println(cEssence + " : " + maxEssence);
        //System.out.println(dematerializer.getEssence().getRiftEssence() + " : " + dematerializer.getEssence().getMaxRiftEssence());
    }


}
