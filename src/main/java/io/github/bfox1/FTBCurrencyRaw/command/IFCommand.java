package io.github.bfox1.FTBCurrencyRaw.command;

import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;

public interface IFCommand
{
    String commandName();

    int commandIndex();

    void initializeCommands();

    FcCommand.CommandResult execute(MinecraftServer server, String command, String[] args, ICommandSender sender);

}
