package io.github.bfox1.FTBCurrencyRaw.command;

import io.github.bfox1.FTBCurrencyRaw.FoxCommandManager;
import io.github.bfox1.FTBCurrencyRaw.handler.CommandEventHandler;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FcCommand implements IFCommand
{

    private final List<String> commandList;

    public boolean vanillaCreated;

    private final String basePerm;

    /**
     * Constructor.
     * String basePerm is the Base permission node for this command. This gets checked first
     * So if Player does not have the basePerm node, they will not get to use any command.
     */
    public FcCommand(String basePerm)
    {
        commandList = new ArrayList<>();
        this.basePerm = basePerm;
        initializeCommands();
    }

    /**
     * Initialized the Commands.
     * To Initialize Commands, use the {@link FcCommand#addCommand} to add the commands.
     * For Example Commands, check out {@link CurrencyCommand.Commands}
     */
    public abstract void initializeCommands();

    public final void addCommand(String name)
    {
        this.commandList.add(name);
    }

    public final void addCommand(String... names)
    {
        this.commandList.addAll(Arrays.asList(names));
    }

    /**
     * Gets the name of the Command.
     * @return Command Name.
     */
    public final String getName()
    {
        return this.commandName();
    }

    public void setVanillaCreated()
    {
        this.vanillaCreated = true;
    }

    public boolean isVanillaCreated()
    {
        return vanillaCreated;
    }


    /**
     * Privately invoked execute Method.
     * This method should never get Called. Please use {@link FcCommand#execute(MinecraftServer, String, String[], ICommandSender)}
     * Should be available when creating a sub-class.
     * It gets executed within {@link FoxCommandManager#execute(MinecraftServer, String, String[], ICommandSender)}
     * @param server The Minecraft Server
     * @param command The Stringed Command. This is the name of the command. IE: Currency
     * @param args The Arguments/Parameters for the Command.
     * @param sender The Command Sender. This can be both Console and Player
     * @param clazz The Check is to verify its being invoked correctly.
     * @return {@link CommandResult}
     */
    public final CommandResult execute(MinecraftServer server, String command, String args[], ICommandSender sender, Class clazz)
    {

        if(clazz.equals(FoxCommandManager.class))
        {
            System.out.println(this.vanillaCreated);
                if(this.isVanillaCreated())
                {
                    return execute(server, command,args,sender);
                }
                if(command.equalsIgnoreCase(this.getName()))
                {
                    return execute(server, command, args, sender);
                }
                if(!this.commandList.contains(command))
                {
                    return CommandResult.FAIL.setMessage("Command Not Found!", this, true);
                }
                return execute(server, command, args, sender);
        }


        return CommandResult.UNVERIFIED.setMessage("Sorry, but this execution was not Verified!", this,true);
    }

    /**
     * Publicly Invoked Execute Method.
     *
     * This method gets invoked by {@link FcCommand#execute(MinecraftServer, String, String[], ICommandSender, Class)}.
     * @param server The Minecraft Server.
     * @param command The Stringed Command. This is the name of the command. IE: Currency
     * @param args The Arguments/Parameters for the Command.
     * @param sender The Command Sender. This can be both Console and Player
     * @return {@link CommandResult}
     */
    public abstract CommandResult execute(MinecraftServer server, String command, String args[], ICommandSender sender);


    /**
     * @return the amount of Commands in the command list.
     */
    @Override
    public int commandIndex() {
        return this.commandList.size();
    }

    /**
     * Gets the Base Perm Node.
     * @return
     */
    public String getBasePerm() {
        return basePerm;
    }


    /**
     * Unique CommandResults issued in relations to Commands.
     * This system is to be used to monitor Command activity and notify console and/or player.
     * This should NOT be used as a messaging system to tell players information.
     * Following the Higher-Archy of this Enum, it gets executed within {@link CommandEventHandler#checkResult(CommandResult, ICommandSender)}
     */
    public enum CommandResult
    {
        SUCCESS(0),
        FAIL(1),
        UNVERIFIED(2);

        private String message;
        private FcCommand command;
        private final int code;
        private boolean tellPlayer = false;

        CommandResult(int code)
        {
            this.code = code;
            this.tellPlayer = false;
        }


        public String getMessage()
        {
            return this.name() + " : " + message;
        }

        public String getPlayerMessage()
        {
            return tellPlayer ? message : "";
        }


        public CommandResult setMessage(String message, FcCommand command, boolean tellPlayer)
        {

            this.message = message;
            this.tellPlayer = tellPlayer;
            this.command = command;

            return this;
        }

        public String getCommandName()
        {
            if(command == null)
            {
                return "";
            }
            return command.getName();
        }


        public int getCode() {
            return code;
        }

        public boolean tellPlayer()
        {
            return tellPlayer;
        }
    }

}
