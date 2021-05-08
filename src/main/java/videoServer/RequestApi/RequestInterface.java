package videoServer.RequestApi;


import com.sun.net.httpserver.HttpServer;

import java.io.IOException;


import java.net.InetSocketAddress;

public class RequestInterface {

    private final HttpServer server;

    public RequestInterface() {
        HttpServer server1;
        try {
            server1 = HttpServer.create(new InetSocketAddress(8500), 0);
        } catch (IOException e) {
            e.printStackTrace();
            server1 = null;
        }

        server = server1;
        server.createContext("/test", new ServerHttpHandler());
        server.createContext("/listvideos", new ServerHttpHandler());
        server.createContext("/video", new ServerHttpHandler());
        //add here new endpoints

        }

        public void ServerStart()
        {
            System.out.println("Starting up HTTP server");
            server.start();
        }

        public void ServerStop()
        {
            server.stop(10);
        }


}
