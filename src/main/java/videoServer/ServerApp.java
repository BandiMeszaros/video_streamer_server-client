package videoServer;

public class ServerApp {

    public static void main(String[] args) {
        //testing rtp streaming
        RTPserver rtpServer = new RTPserver();
        rtpServer.setMediaRoot("C:\\Users\\Andras Meszaros\\Documents" +
                "\\BME\\java_tech\\hazi\\VideoStreaming\\videos\\sampl.mp4");
        rtpServer.StreamMedia();
        try {
            Thread.currentThread().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
