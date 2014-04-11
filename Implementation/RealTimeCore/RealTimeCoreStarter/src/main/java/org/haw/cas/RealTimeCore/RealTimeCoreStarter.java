package org.haw.cas.RealTimeCore;

import org.haw.cas.RealTimeCore.ApplicationCore.Interface.ApplicationCoreFactory;
import org.haw.cas.RealTimeCore.ApplicationCore.Interface.IApplicationCore;

import java.io.IOException;

/**
 * User: Jason Wilmans
 * Date: 22.10.13
 * Time: 19:49
 */
public class RealTimeCoreStarter {

    public static void main(String... args) {
        // Start up application core and the forwarder

        IApplicationCore applicationCore = ApplicationCoreFactory.getApplicationCore();


        System.out.println("RealTimeCore process started.\n\n Press 'q' to quit");

        try {
            while (System.in.read() != 'q') {

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
