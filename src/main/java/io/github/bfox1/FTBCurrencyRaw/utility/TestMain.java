package io.github.bfox1.FTBCurrencyRaw.utility;

import io.netty.util.internal.ThreadLocalRandom;

public class TestMain
{

    public static void main(String args[])
    {
        String testCommand = "./FcCommand test";

        String testFake = "/fcCOmmand test";

        String testFake2 = ".fcCommand./test";

        String seq = "./";

        System.out.println(testCommand.replaceFirst(seq, ""));

        String commandLine = testCommand.replaceFirst(seq, "");

        String[] commandList = commandLine.split(" ");

        System.out.println(commandList[0]);

        String commandName = commandList[0];

        String[] finishedList = commandLine.replace(commandName, "").split(" ");
        String[] finished = new String[commandList.length - 1];
        int i = 0;
        for(String s : commandList)
        {
            if(s.equals(commandName))
            {
                System.out.println("I equal this, not doing. ");
            }
            else
            {
                finished[i] = s;
                i++;
            }

        }
        {
            String text = "B f o x M a s t e r";

            String[] textList = text.split(" ");

            String sequence = "";
            for(String s : textList)
            {
                sequence += ThreadLocalRandom.current().nextInt(12,100);
            }

            System.out.println(sequence);
            System.out.println(sequence.hashCode());


        System.out.println(finished[0]);
        System.out.println(commandName);
    }}
}
