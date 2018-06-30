package io.github.bfox1.TheRift.riftessence;

import net.minecraft.entity.EntityLiving;

/**
 * Created by bfox1 on 12/2/2016.
 */
public class RiftEssenceMobMap
{
    private static double CREEPER, SKELETON, ZOMBIE, PIGZOMBIE, SLIME_BIG, SLIME_MEDIUM, SLIME_SMALL, GHAST,WITHERSKELETON,
    WITHER, ENDERMAN,ENDER_DRAGON, SHULKER, MAGMACUBE, SPIDER, CAVE_SPIDER, SILVERFISH, BLAZE, WITCH, ENDERMITE, GUARDIAN;

    private static double COW, MOOSHROOM, PIG, CHICKEN, SHEEP, RABBIT, BAT, VILLAGER, IRON_GOLEM, GOLEM, OCELOT, WOLF,
    SQUID, POLARBEAR, HORSE;



    static
    {
        CREEPER = 1.5;
        SKELETON = 1.0;
        ZOMBIE = 1.0;
        PIGZOMBIE = 1.0;
        SLIME_BIG = 2.0;
        SLIME_MEDIUM = 1.25;
        SLIME_SMALL = 0.75;
        GHAST = 3.5;
        WITHERSKELETON = 1.5;
        WITHER = 10.0;
        ENDERMAN = 2.25;
        ENDER_DRAGON = 25;
        SHULKER = 1.5;
        MAGMACUBE = 2.5;
        SPIDER = 1.0;
        CAVE_SPIDER = 0.5;
        SILVERFISH = 0.5;
        BLAZE = 2.5;
        WITCH = 1.5;
        ENDERMITE = 0.7;
        GUARDIAN = 4.5;

        COW = 0.5;
        MOOSHROOM = 1.0;
        PIG = 0.35;
        CHICKEN = 0.17;
        SHEEP = 0.4;
        RABBIT = 0.3;
        BAT = 0.17;
        VILLAGER = 0.65;
        IRON_GOLEM = 3.5;
        GOLEM = 1.0;
        OCELOT = 0.7;
        WOLF = 0.5;
        SQUID = 0.5;
        POLARBEAR = 1.5;
        HORSE = 0.75;
    }

    public static double getEntityEssence(EntityLiving living)
    {
        int id = living.getEntityId();
        System.out.println(living.getPersistentID() + " : " + living.getUniqueID());
        switch (id)
        {
            case 50: return CREEPER;
            case 51: return SKELETON;
            case 52 : return SPIDER;
            case 54: return ZOMBIE;
            case 55: return SLIME_MEDIUM;
            case 56: return GHAST;
            case 57: return PIGZOMBIE;
            case 58: return ENDERMAN;
            case 59: return CAVE_SPIDER;
            case 60 : return SILVERFISH;
            case 61: return BLAZE;
            case 62: return MAGMACUBE;
            case 63: return ENDER_DRAGON;
            case 64: return WITHER;
            case 65: return BAT;
            case 66: return WITCH;
            case 67: return ENDERMITE;
            case 68: return GUARDIAN;
            case 69 : return SHULKER;
            case 90: return PIG;
            case 91: return SHEEP;
            case 92: return COW;
            case 93: return CHICKEN;
            case 94: return SQUID;
            case 95: return WOLF;
            case 96: return MOOSHROOM;
            case 97: return GOLEM;
            case 98: return OCELOT;
            case 99: return IRON_GOLEM;
            case 100: return HORSE;
            case 101: return RABBIT;
            case 102: return POLARBEAR;
            case 120: return VILLAGER;
            default: return 1;
        }
    }
}
