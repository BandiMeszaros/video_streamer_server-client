package videoServer.RequestApi;

public class requestEntryPoint extends Thread{

    private final RequestInterface Interface;

    public requestEntryPoint() {
        Interface = new RequestInterface();
    }

    public RequestInterface getInterface() {
        return Interface;
    }



    @Override
    public void run() {
        long Threadid = Thread.currentThread().getId();
        String ThreadName = Thread.currentThread().getName();
        System.out.printf("HTTP thread is: %d -- %s", Threadid, ThreadName);
        try {
            Interface.ServerStart();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

}
