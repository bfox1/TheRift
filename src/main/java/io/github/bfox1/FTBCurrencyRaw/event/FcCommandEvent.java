package io.github.bfox1.FTBCurrencyRaw.event;

import io.github.bfox1.FTBCurrencyRaw.command.IFCommand;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class FcCommandEvent extends Event
{


    private final ICommandSender sender;

    private final IFCommand command;

    private final String[] args;

    public FcCommandEvent(ICommandSender sender, IFCommand command, String[] args)
    {
        this.sender = sender;
        this.command = command;
        this.args = args;
    }

    public ICommandSender getSender() {
        return sender;
    }

    public IFCommand getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }
}
