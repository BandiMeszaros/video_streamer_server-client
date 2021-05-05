package videoServer;


import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;

public class RTPserver {

    private static final RTPserver rtp_serverObject = new RTPserver();
    private static String mediaRoot;

    private static final String options = formatRtpStream("127.0.0.1", 5555);
    private static final MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
    private static final MediaPlayer mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();

    public static String getMediaRoot() {
        return mediaRoot;
    }

    public static void setMediaRoot(String mediaRoot) {
        RTPserver.mediaRoot = mediaRoot;
    }

    private RTPserver() {
        System.out.println("RTP server instance has been created....");
    }

    public static RTPserver getRTPServer()
    {
        return rtp_serverObject;
    }

    public static void StreamMedia() {
        mediaPlayer.media().play(mediaRoot, options, ":no-sout-rtp-sap",
                ":no-sout-standard-sap", ":sout-all", ":sout-keep");
        System.out.println("Streaming '" + mediaRoot + "' to '" + options + "'");
    }
    public static void StopAndReleaseMedia(){
        System.out.printf("Stopping stream: %s ", mediaRoot);
        mediaPlayer.release();
    }



    /*public static void main(String[] args) throws Exception {
        if(args.length != 1) {
            System.out.println("Specify a single MRL to stream");
            System.exit(1);
        }

        String media = args[0];
        String options = formatRtpStream("127.0.0.1", 5555);

        System.out.println("Streaming '" + media + "' to '" + options + "'");

        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory(args);
        MediaPlayer mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();

        mediaPlayer.media().play(media,
                options,
                ":no-sout-rtp-sap",
                ":no-sout-standard-sap",
                ":sout-all",
                ":sout-keep"
        );

        //Dont
        Thread.currentThread().join();
    }*/

    private static String formatRtpStream(String serverAddress, int serverPort) {
        StringBuilder sb = new StringBuilder(60);
        sb.append(":sout=#rtp{dst=");
        sb.append(serverAddress);
        sb.append(",port=");
        sb.append(serverPort);
        sb.append(",mux=ts}");
        return sb.toString();
    }
}
