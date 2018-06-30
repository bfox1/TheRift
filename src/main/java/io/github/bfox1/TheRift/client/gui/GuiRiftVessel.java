package io.github.bfox1.TheRift.client.gui;

import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityDematerializer;
import io.github.bfox1.TheRift.common.entity.tileentity.TileEntityRiftVessel;
import io.github.bfox1.TheRift.common.util.MathHelper;
import io.github.bfox1.TheRift.common.util.Reference;
import io.github.bfox1.TheRift.server.container.ContainerDematerializer;
import io.github.bfox1.TheRift.server.container.ContainerRiftVessel;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by bfox1 on 11/13/2016.
 */
public class GuiRiftVessel extends GuiContainer {
    private static final ResourceLocation DEMATERILIZER_GUI_TEXTURE = new ResourceLocation(Reference.MODID, "textures/gui/container/riftvessel.png");

    private final TileEntityRiftVessel vessel;
    private final InventoryPlayer player;

    private int cEssence;

    private int maxEssence;
    public GuiRiftVessel(InventoryPlayer player, TileEntityRiftVessel vessel)
    {
        super(new ContainerRiftVessel(player, vessel));
        this.vessel = vessel;
        this.player = player;
        this.xSize = 175;
        this.ySize = 165;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mX, int mY)
    {
        String s = this.vessel.getDisplayName().getUnformattedText();
        s = "Rift Vessel";
        this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 2, 4210752);
        //this.fontRendererObj.drawString(this.player.getDisplayName().getUnformattedText(), 8, this.ySize - 96 + 2, 4210752);

        if(MathHelper.isIntWithinRange(this.guiLeft + 12, this.guiTop + 11, this.guiLeft + 21, this.guiTop + 73, mX, mY))
        {
            List list = new ArrayList<>();

            list.add("Essence Type: " + this.vessel.getEssence().getName());
            list.add("Comparable Essence: " + cEssence + "/" + maxEssence);
            this.drawHoveringText(list, (int)mX - this.guiLeft, (int)mY - this.guiTop, fontRenderer);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
    {
        this.cEssence = this.vessel.getField(2);
        this.maxEssence = this.vessel.getField(3);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(DEMATERILIZER_GUI_TEXTURE);
        this.drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);

        //int l = this.getDematerializedProgressScaled(24);
        //this.drawTexturedModalRect(guiLeft + 63, guiTop + 41, 176, 33, l + 1, 16);
        drawBar(cEssence);
       // System.out.println(cEssence + " : " + maxEssence);

    }


    public int getDematerializedProgressScaled(int pixels)
    {
        int i = cEssence;

        if(i == 0)
        {
            i = 0;
        }
        return i / 5;
    }

    public double getReScaled()
    {
        double i = cEssence;
        double value = io.github.bfox1.TheRift.common.util.MathHelper.newRangeValue(0, maxEssence, 102, 40, i);
        return value;
    }

    public void drawBar(double percent)
    {
        double l = getReScaled();
        this.drawSpecialBar(guiLeft + 12, guiTop + 11, 176, (int)l, 16, 120,percent);

    }


    private void drawSpecialBar(int x, int y, int textureX, int textureY, int width, int height, double percent)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);

        GL11.glEnable(GL11.GL_BLEND);

        double oRange = (maxEssence);
        double nRange = 1.0;
        double value = (((percent-0)*nRange)/oRange)+0;
        //GL11.glColor3d(2*(1-value), 2*(value), 0);
        GL11.glColor3d((1-value), (value), 0);

        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder vertexbuffer = tessellator.getBuffer();
        vertexbuffer.begin(7, DefaultVertexFormats.POSITION_TEX);
        vertexbuffer.pos((double)(x + 0), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + height), (double)this.zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + height) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + width), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + width) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        vertexbuffer.pos((double)(x + 0), (double)(y + 0), (double)this.zLevel).tex((double)((float)(textureX + 0) * 0.00390625F), (double)((float)(textureY + 0) * 0.00390625F)).endVertex();
        tessellator.draw();

        GL11.glColor3f(255, 255, 255);
        GL11.glPopMatrix();
    }
}
