package videoServer.RequestApi;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;


import java.net.InetSocketAddress;

public class RequestInterface {

    private final HttpServer server;
    private final ServerHttpHandler handler;


    public RequestInterface() {
        HttpServer server1;
        try {
            server1 = HttpServer.create(new InetSocketAddress(8500), 0);
        } catch (IOException e) {
            e.printStackTrace();
            server1 = null;
        }

        server = server1;
        handler = new ServerHttpHandler();
        server.createContext("/test", handler);
        server.createContext("/listvideos", handler);
        server.createContext("/video", handler);
        //add here new endpoints

        }

        public void ServerStart()
        {
            System.out.println("Starting up HTTP server");
            server.start();
        }

        public void ServerStop()
        {
            handler.getDbAPI().close_connection();
            server.stop(10);
        }


}
