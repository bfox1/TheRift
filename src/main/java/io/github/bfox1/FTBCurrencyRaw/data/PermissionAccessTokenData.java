package io.github.bfox1.FTBCurrencyRaw.data;

import io.netty.util.internal.ThreadLocalRandom;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

/**
 * UNUSED Token information.
 * Planned to get scraped later for simpler solution.
 */
public class PermissionAccessTokenData
{

    private int temporal;

    public PermissionAccessTokenData()
    {

    }
    public static final void setTokeSettings()
    {
        File file = new File("Permission/");

        if(!file.exists())
            file.mkdir();
        file.mkdirs();
    }

    public final void saveToken()
    {
        File file = new File("Permission/" + "token" + ".dat");

        if(!file.exists());
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try
        {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));

            stream.write(temporal);

            stream.close();
            this.temporal = 0;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int randomizeGen()
    {
        String text = "B f o x M a s t e r";

        String[] textList = text.split(" ");

        String sequence = "";
        for(String s : textList)
        {
            sequence += ThreadLocalRandom.current().nextInt(12,100);
        }

        return Integer.getInteger(sequence);
    }

    public final int loadToken()
    {

        int token = 0;
        File file = new File("Currency/" + "token" + ".dat");
        if(!file.exists());
        {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BufferedInputStream stream = null;
        try {
            stream = new BufferedInputStream(new FileInputStream(file));

            token = stream.read();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(token == 0)
        {
            token = randomizeGen();
            saveToken();
        }
        return token;
    }


}
