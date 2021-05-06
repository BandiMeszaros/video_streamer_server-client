package videoServer;


import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.media.MediaRef;
import uk.co.caprica.vlcj.media.TrackType;
import uk.co.caprica.vlcj.player.base.EventApi;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventListener;
import uk.co.caprica.vlcj.player.list.MediaListPlayerEventListener;

public class RTPserver {

    private static String mediaRoot;
    private final  String options;
    private final  MediaPlayerFactory mediaPlayerFactory;
    private  final MediaPlayer mediaPlayer;

    public static String getMediaRoot() {
        return mediaRoot;
    }

    public static void setMediaRoot(String mediaRoot) {
        RTPserver.mediaRoot = mediaRoot;
    }

    RTPserver() {
        options = formatRtpStream("127.0.0.1", 5555);
        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();

        mediaPlayer.events().addMediaPlayerEventListener(new MediaPlayerEventAdapter()
        {
            @Override
            public void finished(MediaPlayer mediaPlayer) {
                System.out.println("Media is over....");
                mediaPlayer.release();
            }
        });
        System.out.println("RTP server instance has been created....");
    }

    public void StreamMedia() {
        mediaPlayer.media().play(mediaRoot, options, ":no-sout-rtp-sap",
                ":no-sout-standard-sap", ":sout-all", ":sout-keep");
        System.out.println("Streaming '" + mediaRoot + "' to '" + options + "'");
    }
    public void StopAndReleaseMedia(){
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
