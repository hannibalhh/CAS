package MessageAdapter.Implementation.Component;

import DSPersistenceManager.Services.MessageDAO;

/**
 * Created with IntelliJ IDEA.
 * User: Jonas
 * Date: 24.10.13
 * Time: 19:35
 * To change this template use File | Settings | File Templates.
 */
public class Tester {

    public static void main(String[] args) {

        System.out.println("Anfang");
        MessageDAO messageDAO = new MessageDAO();
        MessageAdapterComponent mac = new MessageAdapterComponent(messageDAO);

        //mac.startThread();
        System.out.println("Ende");

    }
}
