package io.github.bfox1.FTBCurrencyRaw;

import io.github.bfox1.FTBCurrencyRaw.command.FcCommand;
import io.github.bfox1.FTBCurrencyRaw.command.IFCommand;
import io.github.bfox1.FTBCurrencyRaw.handler.CommandEventHandler;
import io.github.bfox1.FTBCurrencyRaw.player.FoxPlayer;
import io.github.bfox1.FTBCurrencyRaw.utility.Color;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ITickable;
import net.minecraftforge.event.CommandEvent;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;

import java.util.HashMap;

public class FoxCommandManager
{
    private final HashMap<String, FcCommand> commands;

    public final Currency currency;


    public FoxCommandManager(Currency currency)
    {
        this.currency = currency;
        commands = new HashMap<>();
    }


    public void addCommand(FcCommand command)
    {
        if(!commands.containsKey(command.getName().toLowerCase()))
        {
            System.out.println(command.getName() + " : " + "Was added!");
            commands.put(command.getName().toLowerCase(), command);
        }
    }

    public void parseCommandEvent(CommandEvent event)
    {
        ICommandSender sender = event.getSender();
        ICommand command = event.getCommand();
        String[] args = event.getParameters();

    }

    public final void createCommand(ICommand commands)
    {

        FcCommand fCommand = new FcCommand("minecraft." + commands.getName().toLowerCase() + ".basic")
        {

            @Override
            public void initializeCommands()
            {
                addCommand(commands.getName());
            }

            @Override
            public CommandResult execute(MinecraftServer server, String command, String[] args, ICommandSender sender)  {

                try
                {
                    commands.execute(server, sender, args);

                    return CommandResult.SUCCESS.setMessage("Successfully executed Command", this, false);
                } catch (CommandException e)
                {

                    return CommandResult.FAIL.setMessage(Color.LIGHRED + "Sorry! Unknown error Occured with a command using default MC commands." +
                            "\n Would advise to swtich to FCommand if possible!", this, true);
                }


            }

            @Override
            public String commandName()
            {
                return commands.getName().toLowerCase();
            }
        };
        fCommand.setVanillaCreated();
        this.addCommand(fCommand);
    }

    /**
     * This Methods gets inv
     *
     * @param server
     * @param args
     * @param sender
     */
    public final FcCommand.CommandResult execute(MinecraftServer server, String command, String[] args, ICommandSender sender)
    {
        FcCommand.CommandResult result = FcCommand.CommandResult.FAIL.setMessage("Sorry, That command was not found!", null, true);
        try
        {

            for(String k : commands.keySet())
            {
                System.out.println(k);
                    if(k.toLowerCase().equalsIgnoreCase(command))
                    {
                        System.out.println("EXECUTING COMMAND:  " + command + " " + (args.length ==1 ? args[0] : ""));
                        result = runCommand(server,command, args, sender, commands.get(k));
                    }
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return FcCommand.CommandResult.FAIL.setMessage("Sorry, an Error had occured and an Exception was caught!\n " +
                    "Thankfully I was here to catch that bad exception!", null, true);
        }

        return result;
    }

    private FcCommand.CommandResult runCommand(MinecraftServer server, String commandString, String[] args, ICommandSender sender, FcCommand ifCommand)
    {
        FcCommand command = ifCommand;

        if(sender instanceof EntityPlayer)
        {
            FoxPlayer player = Currency.getInstance().getPlayerManager().getPlayer(((EntityPlayer)sender).getUniqueID());
            if(player.getPermissions().hasPerm(command.getBasePerm()))
            {
                String[] array;
                if(args.length <= 1)
                {
                    System.out.println(args.length);
                    return command.execute(server, args.length == 0 ? commandString : args[0] ,new String[0], sender, this.getClass());
                }
                else if(command.vanillaCreated)
                {
                    return command.execute(server, commandString, args, sender, this.getClass());
                }
                else
                {
                    System.out.println("creating new string");
                    System.out.println(args.length);

                    array = new String[args.length - 1];
                    int i = 0;
                    for(String s : args)
                    {
                        if(!s.equalsIgnoreCase(args[0]))
                        {
                            array[i] = s;
                            i++;
                        }
                    }
                    i=0;
                    return command.execute(server, args[0], array, sender, this.getClass());
                }

            }
            else
            {
                return FcCommand.CommandResult.FAIL.setMessage("Sorry but you dont have permission!", command,true);
            }
        }
        else
        {
            return FcCommand.CommandResult.FAIL.setMessage("Sorry Console, but currently you dont have access to issue commands YET!\n" +
                    "Please log into your MC Account to issue commands.", command,true);
        }
    }

}
