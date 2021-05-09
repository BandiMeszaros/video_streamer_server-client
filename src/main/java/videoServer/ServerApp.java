package videoServer;

import videoServer.RequestApi.requestEntryPoint;
import videoServer.streamApi.RTPserver;
import videoServer.streamApi.streamData;

public class ServerApp {

    public static streamData stream;
    public static requestEntryPoint httpInterface;

    public static void main(String[] args) throws InterruptedException {
//        stream = new streamData("C:\\Users\\Andras Meszaros\\Documents\\BME\\java_tech\\hazi\\VideoStreaming\\videos\\cica.mp4");
//        int duration = stream.getMediaInfo().getVideoLength_s();
//        long duration_ms = stream.getMediaInfo().getVideoLength_ms();
//        System.out.printf("Length of video: %dseconds\n", duration);
//        Thread rtp_stream = stream.getRtpStreamThread();
//        System.out.println("started video streaming....");
//        rtp_stream.start();
        httpInterface = new requestEntryPoint();
        httpInterface.start();

//        try {
//            rtp_stream.join(2*duration_ms);
//        } catch (InterruptedException e) {
//            System.out.println("streaming thread were killed because videoplaying take to much time");
//            e.printStackTrace();
//        }

        //todo ehelyett a buta try catch helyett lehetne hogy lelövi a streamet ha a másik oldal lenyomta
        // todo pl igy stream.getServer().StopAndReleaseMedia();
        //todo: valahogy ezt vissza kell jelezni nyilván

        System.out.println("videoEnded");
        Thread.currentThread().join();

    }
}
