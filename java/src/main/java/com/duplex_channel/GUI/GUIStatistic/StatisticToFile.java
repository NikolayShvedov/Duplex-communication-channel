package com.duplex_channel.GUI.GUIStatistic;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class StatisticToFile {

    String dateTxt;

    public void modelingFileСreation()
    {
        try
        {
            DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmmss");
            Date date = new Date();
            dateTxt = "Modeling_in_" + dateFormat.format(date) + ".txt";
            File fileStatistic = new File(dateTxt);
            if (fileStatistic.createNewFile()) {
                System.out.println("File " + dateTxt + " is created!");
            }
            else {
                System.out.println("Error!");
            }
        }
        catch (Exception e)
        {
            System.err.println(e);
        }
    }

    public void modelingFileFilling(String statisticModeling)
    {
        try
        {
            FileWriter writer = new FileWriter(dateTxt);
            writer.write(statisticModeling);
            writer.close();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
