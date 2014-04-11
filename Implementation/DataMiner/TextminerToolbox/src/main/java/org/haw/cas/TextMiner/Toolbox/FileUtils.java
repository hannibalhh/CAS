package org.haw.cas.TextMiner.Toolbox;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileUtils {

    private static Logger logger = LogManager.getLogger(FileUtils.class);

    /**
     * Gets a list of all text files in the specified directory.
     *
     * @param directory
     * @return
     */
    public static File[] getAllTxtFilesInDirectory(File directory) {
        return directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename) {
                return filename.endsWith(".txt");
            }
        });
    }

    /**
     * Gets all rows from all text files at a specified path.
     *
     * @param path
     * @return
     */
    public static List<String> getEntriesFromTextFilesInPath(String path) {
        List<String> result = new ArrayList<String>();
        File nameDirectory = new File(path);

        try {
            FileUtils.logger.debug("Reading entries from: " + nameDirectory.getCanonicalPath());
        } catch (IOException e) {
            throw new ExceptionInInitializerError(e);
        }

        File[] nameFiles = getAllTxtFilesInDirectory(nameDirectory);

        if (nameFiles == null) {
            try {
                throw new FileNotFoundException("Reading entries from " + nameDirectory.getCanonicalPath() + "failed.");
            } catch (IOException e) {
                FileUtils.logger.fatal(e);
                throw new RuntimeException(e);
            }
        }

        for (File nameFile : nameFiles) {
            try {
                Scanner scanner = new Scanner(nameFile);
                while (scanner.hasNextLine()) {
                    String nextLine = scanner.nextLine();
                    result.add(nextLine.trim().toLowerCase());
                }
            } catch (FileNotFoundException e) {
                throw new ExceptionInInitializerError(e);
            }
        }

        return result;
    }
}