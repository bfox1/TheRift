package io.github.bfox1.FTBCurrencyRaw.player;

import java.io.*;
import java.util.HashMap;
import java.util.UUID;

public class PlayerData
{

    public PlayerData()
    {

    }
    public static void loadDataSettings()
    {
        File file = new File("Currency/");

        if(!file.exists())
        file.mkdir();
        file.mkdirs();
    }

    public void saveData(HashMap<String, Object> serialize, UUID uuid)
    {
        File file = new File("Currency/" + uuid + ".dat");

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
            ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(file));

            stream.writeObject(serialize);

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public HashMap<String, Object> loadData(UUID uuid)
    {
        HashMap<String, Object> serializedData = null;

        File file = new File("Currency/" + uuid + ".dat");
        ObjectInputStream stream = null;
        try {
            stream = new ObjectInputStream(new FileInputStream(file));

            serializedData = (HashMap<String,Object>)stream.readObject();
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        if(serializedData == null)
        {
            System.out.println("This is an ERROR. Serialized Data is null");
        }

        return serializedData;
    }

    public boolean hasData(UUID uuid)
    {
        File file = new File("Currency/" + uuid + ".dat");

        return file.exists();
    }
}
