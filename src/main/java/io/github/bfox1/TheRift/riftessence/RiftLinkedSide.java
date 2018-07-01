package io.github.bfox1.TheRift.riftessence;


import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * Created by bfox1 on 11/5/2016.
 */
public class RiftLinkedSide
{
    private int[] position = new int[3];
    private final EnumFacing face;
    private final EnumRiftAction action;
    private boolean isMasterLink;

    private int hashCode;

    public RiftLinkedSide(int x, int y, int z, EnumFacing face, EnumRiftAction action, boolean masterLink)
    {
        position[0] = x;
        position[1] = y;
        position[2] = z;
        this.face = face;
        this.action = action;
        this.isMasterLink = masterLink;
    }

    public RiftLinkedSide(BlockPos pos, EnumFacing face, EnumRiftAction action, boolean masterLink)
    {
        position[0] = pos.getX();
        position[1] = pos.getY();
        position[2] = pos.getZ();

        this.face = face;
        this.action = action;

        this.isMasterLink = masterLink;
    }


    public int getX()
    {
        return position[0];
    }

    public int getY()
    {
        return position[1];
    }

    public int getZ()
    {
        return position[2];
    }


    public EnumFacing getFace() {
        return face;
    }

    public EnumRiftAction getAction() {
        return action;
    }

    public boolean isMasterLink()
    {
        return isMasterLink;
    }

    public BlockPos getPos()
    {
        return new BlockPos(getX(), getY(), getZ());
    }

    public void setHashCode(int code)
    {
        this.hashCode = code;
    }

    public boolean checkHashCode(int hashCode)
    {
        return hashCode == this.hashCode;
    }

    public void updateTempHash(int hash)
    {

    }

    public enum EnumRiftAction
    {
        INSERT(0),
        EJECT(1),
        GUI(2),
        CLEAR(3);

        private final int id;

        EnumRiftAction(int id)
        {
            this.id = id;
        }

        public int getId()
        {
            return id;
        }

        public static EnumRiftAction getNext(EnumRiftAction action)
        {
            if(action.getId() == EnumRiftAction.values().length)
            {
                return EnumRiftAction.INSERT;
            }
            for(int i = action.getId() + 1; i < EnumRiftAction.values().length; i++)
            {
                return EnumRiftAction.values()[i];
            }
            return EnumRiftAction.INSERT;
        }

        public static EnumRiftAction getByID(int id)
        {
            for(EnumRiftAction action : EnumRiftAction.values())
            {
                if(action.getId() == id)
                {
                    return action;
                }
            }
            return INSERT;
        }
    }
}
