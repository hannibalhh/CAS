package org.haw.cas.Tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        System.out.println("Scanning recusively for *.proto files..");

        List<String> protoFiles = findProtoFiles(".");

        System.out.println("found following files:");
        System.out.println("========================================");
        for (String file : protoFiles) {
            System.out.println(file);
        }
        System.out.println("========================================\n\n");
        System.out.println("Starting compilation..");

        boolean success = true;

        for (String file : protoFiles) {
            success = success && compile(file);
        }

        try {
            if (success) {
                System.out.println("\n\nAll files compiled sucessfully!");
            } else {
                System.out.println("\n\nThere were compilations errors:(");
            }

            System.out.println("\n\nBeliebige Taste drücken, um zu beenden.");

            System.in.read();
        } catch (IOException e) {
        }
    }

    private static boolean compile(String file) {
        System.out.println("----------------------------");
        System.out.println("Compiling " + file + ", output:");
        System.out.println("----------------------------");

        Process p = null;
        boolean success = true;
        try {
            p = Runtime.getRuntime().exec("protoc -I=./ --java_out=output/ " + file);

            success = p.waitFor() == 0;

            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(
                            p.getErrorStream()));
            String line = reader.readLine();

            while (line != null) {
                System.out.println(line);
                line = reader.readLine();
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (success) {
            System.out.println("successful!");
        }

        System.out.println("\n");
        return success;
    }

    private static List<String> findProtoFiles(String directory) {
        File root = new File(directory);

        List<String> result = new LinkedList<String>();

        String name;
        for (File file : root.listFiles()) {
            name = file.getName();

            if (file.isFile() && name.endsWith(".proto")) {
                result.add(file.getPath());
            } else if (file.isDirectory()) {
                result.addAll(findProtoFiles(file.getPath()));
            }
        }

        return result;
    }


}
