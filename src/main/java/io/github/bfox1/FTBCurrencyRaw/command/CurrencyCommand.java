package io.github.bfox1.FTBCurrencyRaw.command;

import io.github.bfox1.FTBCurrencyRaw.Currency;
import io.github.bfox1.FTBCurrencyRaw.player.FoxPlayer;
import io.github.bfox1.FTBCurrencyRaw.utility.Color;
import io.github.bfox1.FTBCurrencyRaw.utility.FoxUtils;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.FMLCommonHandler;

import java.util.List;

public class CurrencyCommand extends FcCommand
{

    private static final String prefix = Color.ORANGE + "Currency";
    private static final String messageColor = Color.DARKGREEN.getColorCode();
    private static final Color bracket = Color.WHITE;
    /**
     * Constructor for FcCommand
     *
     * @param basePerm
     */
    public CurrencyCommand(String basePerm)
    {
        super(basePerm);
    }

    @Override
    public String commandName()
    {
        return "Currency";
    }

    @Override
    public void initializeCommands()
    {
        this.addCommand(Commands.getCommandNames());
    }

    @Override
    public CommandResult execute(MinecraftServer server, String command, String[] args, ICommandSender sender)
    {
        Commands.getValue(command.toLowerCase()).executeCommand(args, sender);

        return CommandResult.SUCCESS.setMessage(sender.getName() + " executed command: " + command + " " + command, this,false);
    }





    /**
     * Enumeration for our Commands to be loaded and looked at.
     * This should be the best method to create sub-commands for your command.
     */
    enum Commands
    {
        set("set", "currency.admin.set_currency", 2, 2)
                {
                    @Override
                    public void execute(String[] args, ICommandSender sender)
                    {

                        FoxPlayer player = getFoxPlayer(args[0]);
                        player.getCurrency().setCurrency(Integer.getInteger(args[1]));
                        player.markDirty();
                        FoxUtils.sendPluginMessage(sender, CurrencyCommand.prefix, Color.ORANGE.getColorCode() + args[0] + CurrencyCommand.messageColor + " Currency has been set to: " + Color.ORANGE + args[1], CurrencyCommand.bracket);
                    }
                },

        buy("buy", "currency.basic") {
            @Override
            public void execute(String[] args, ICommandSender sender)
            {

            }
        },
        sell("sell", "currency.basic") {
            @Override
            public void execute(String[] args, ICommandSender sender)
            {

            }
        },
        trade("trade", "currency.basic") {
            @Override
            public void execute(String[] args, ICommandSender sender)
            {

            }
        },
        info("info", "currency.basic")
                {
                    @Override
                    public void execute(String[] args, ICommandSender sender)
                    {

                    }
                },
        balance("bal", "currency.basic", 0, 1)
                {
                    @Override
                    public void execute(String[] args, ICommandSender sender)
                    {

                        FoxPlayer player = getFoxPlayer(sender);

                        if(player != null)
                        {
                            if(player.getPermissions().hasPerm(getPerm()))
                            {
                                if(isArgGreater(args.length))
                                {
                                    FoxPlayer player2 = getFoxPlayer(args[1]);

                                    if(player2 == null)
                                    {
                                        player.sendMessage("Sorry but this player doesnt exist! or is offline!");
                                    }
                                    else
                                    {
                                        player.sendMessage("Here is the balance: " + player2.getCurrency().getCurrency());
                                    }
                                }
                                player.sendPluginMessage("Currency", "$"+player.getCurrency().getCurrency(), Color.ORANGE, Color.DARKRED, Color.DARKGREEN);
                            }
                        }
                        else
                        {
                            sender.sendMessage(new TextComponentString("Sorry Console! You cannot use this command!"));
                        }
                    }
                },
        debug("debug","currency.admin.override")
                {
                    @Override
                    public void execute(String[] args, ICommandSender sender)
                    {
                        List<EntityPlayerMP> list = FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().getPlayers();

                        for(EntityPlayerMP player : list)
                        {
                            System.out.println(player.getName() + " : " + player.getUniqueID());
                        }
                    }
                };
        // [/FcCommand balance bfox1] //
        private final String name;

        private final String perm;

        private final int min, max;

        Commands(String name, String perm)
        {
            this(name, perm, 0,0);
        }

        Commands(String name, String perm, int min, int max)
        {
            this.name = name;
            this.perm = perm;

            this.min = min;
            this.max = max;
        }



        public abstract void execute(String[] args, ICommandSender sender);

        public void executeCommand(String[] args, ICommandSender sender)
        {

            System.out.println("I passed here .");
            if(!(args.length >= this.min) && !(args.length <= this.max))
            {
                FoxUtils.sendPluginMessage(sender, CurrencyCommand.prefix, Color.LIGHRED + "Sorry, but this command requires Parameter legnth of " + this.max + "!", CurrencyCommand.bracket);
            }

            if(sender instanceof EntityPlayerMP)
            {
                FoxPlayer player = Currency.getInstance().getPlayerManager().getPlayer(((EntityPlayerMP) sender).getUniqueID());

                if(player.getPermissions().hasPerm(perm))
                {
                    execute(args, sender);
                }

            }
            else
            {
                execute(args, sender);
            }
        }

        public static Commands getValue(String name)
        {
            for(Commands commands : values())
            {
                if(commands.name.equalsIgnoreCase(name))
                {
                    return commands;
                }
            }

            return null;
        }

        public static String[] getCommandNames()
        {
            String[] strings = new String[Commands.values().length];

            int i = 0;
            for(Commands c : Commands.values())
            {
                strings[i] = c.getName();
                i++;
            }
            return strings;
        }


        public String getPerm()
        {
            return perm;
        }

        public String getName()
        {
            return name;
        }

        public int getMin() {
            return min;
        }

        public int getMax() {
            return max;
        }

        public FoxPlayer getFoxPlayer(ICommandSender sender)
        {
            if(sender instanceof EntityPlayer)
            {
                return Currency.getInstance().getPlayerManager().getPlayer(((EntityPlayer) sender).getUniqueID());
            }
            else
            {

                return null;
            }
        }

        public FoxPlayer getFoxPlayer(String name)
        {
            return Currency.getInstance().getPlayerManager().getPlayerByName(name);
        }

        public boolean isArgGreater(int argLength)
        {
            return getMin() < argLength && getMax() >= argLength;
        }
    }
}
