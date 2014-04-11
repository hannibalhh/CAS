package org.haw.cas.PersonNameCleaning;

import org.haw.cas.PersonNameCleaning.cleaners.ICleaner;
import org.haw.cas.PersonNameCleaning.cleaners.NameVariationsCleaner;
import org.haw.cas.PersonNameCleaning.cleaners.PlaceNamesInRowsCleaner;
import org.haw.cas.PersonNameCleaning.cleaners.RemoveNameDescriptionsCleaner;

import javax.swing.*;
import java.io.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;
import org.apache.commons.io.FilenameUtils;

/**
 * A simple program that will cleanup name lists obtained from: http://www.beliebte-vornamen.de
 */
public class main {
    private static List<ICleaner> cleaners = new LinkedList<ICleaner>();

    public static void main(String[] args) {
        cleaners.add(new RemoveNameDescriptionsCleaner());
        cleaners.add(new PlaceNamesInRowsCleaner());
        cleaners.add(new NameVariationsCleaner());

        File choosenDirectory = promptUserToSpecifiyDirectory();

        if (choosenDirectory == null) {
            System.out.println("No directory specified. Process will exit.");
            return;
        }

        try {
            System.out.println("Processing files in directory: " + choosenDirectory.getCanonicalPath());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File[] textFiles = getAllTxtFilesInDirectory(choosenDirectory);

        cleanupTextFiles(textFiles);

        System.out.println("Finished processing files.");
    }

    /**
     * Reads the given text-files and places a cleaned version besides them.
     * @param txtFiles The text files that will be cleaned.
     */
    private static void cleanupTextFiles(File[] txtFiles) {
        for(File currentTxtFile : txtFiles) {
            try
            {
                String outputFileName = FilenameUtils.removeExtension(currentTxtFile.getName());
                File outputFile = new File(currentTxtFile.getParentFile().getCanonicalPath() + "/Cleaned/" + outputFileName + "_Cleaned.txt");

                if (outputFile.getParentFile() != null) {
                    outputFile.getParentFile().mkdirs();
                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile, false));
                Scanner scanner =  new Scanner(currentTxtFile);
                while (scanner.hasNextLine()){
                    String nextLine = scanner.nextLine();
                    String cleanedLine = nextLine;

                    for (ICleaner cleaner : cleaners) {
                        cleanedLine = cleaner.cleanInput(cleanedLine);
                    }

                    //process each line in some way
                    if (!cleanedLine.isEmpty()) {
                        writer.write(cleanedLine);
                    }
                }
                scanner.close();
                writer.close();

                System.out.println("Processed file: " + currentTxtFile.getCanonicalPath());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Opens a file dialog for the user, to choose the directory where the textfiles are located that will be cleaned.
     * @return The directory the user has selected. Will be null if the user has canceled the dialog.
     */
    private static File promptUserToSpecifiyDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("choosertitle");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile();
        } else {
            return null;
        }
    }

    /**
     * Gets a list of all text files in the specified directory.
     * @param directory
     * @return
     */
    private static File[] getAllTxtFilesInDirectory( File directory){
        return directory.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String filename)
            { return filename.endsWith(".txt"); }
        } );
    }
}
