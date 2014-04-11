package org.haw.cas;

import org.haw.cas.ApplicationCore.Implementation.ApplicationCoreFactory;
import org.haw.cas.ApplicationCore.Interface.IDataMiner;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IDataMiner dataMiner = ApplicationCoreFactory.getApplicationCore();
        dataMiner.startAll();
        Scanner in = new Scanner(System.in);
        String input = null;
        System.out.println("Press enter to close");
        try {
            input = in.nextLine();
            while (input!="\n"){
                System.out.println("Press enter to close");
                input = in.nextLine();
            }
            in.close();
        } catch (NoSuchElementException e) {
            // This is expected, if the application is shut down using IntelliJ.
        }

        dataMiner.stopAll();
    }
}
