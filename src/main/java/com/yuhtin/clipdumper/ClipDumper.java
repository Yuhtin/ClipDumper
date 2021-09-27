package com.yuhtin.clipdumper;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author Yuhtin
 * Github: https://github.com/Yuhtin
 */
public class ClipDumper {

    private static final Logger LOGGER;

    static {
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tT] [%4$-7s] %5$s %n");
        LOGGER = Logger.getLogger("ClipDumper");
    }

    public static void main(String[] args) {
        File folder = new File(Paths.get("").toAbsolutePath().toString());
        File[] files = folder.listFiles();
        if (files == null) {
            LOGGER.info("This folder doesn't have files.");
            return;
        }

        List<String> clipsTrimmed = new ArrayList<>();
        for (File file : files) {
            if (!file.isFile() || !file.getName().contains("Trim")) continue;
            clipsTrimmed.add(file.getName());
        }

        int filesDeleted = 0;
        for (File file : files) {
            if (!file.isFile()) {
                LOGGER.info("Jumping " + file.getName() + " because isn't a file.");
                continue;
            }

            if (file.getName().contains("Trim")) {
                LOGGER.info("Jumping " + file.getName() + " because is a Trim.");
                continue;
            }

            String possibleFileName = file.getName().replace(".mp4", "") + "_Trim.mp4";
            if (!clipsTrimmed.contains(possibleFileName)) {
                LOGGER.info("Jumping " + file.getName() + " because they not have a Trim.");
                continue;
            }

            if (!file.delete()) LOGGER.severe("Can't delete " + file.getName());
            else {
                LOGGER.warning("Deleted " + file.getName() + " because have a Trim.");
                filesDeleted++;
            }
        }

        LOGGER.info("Deleted " + filesDeleted + " successfully.");
    }

}
