package videoServer;

import videoServer.RequestApi.requestEntryPoint;

public class ServerApp {

    public static requestEntryPoint httpInterface;

    public static void main(String[] args) throws InterruptedException {

        httpInterface = new requestEntryPoint();
        httpInterface.start();

        Thread.currentThread().join();

    }
}
