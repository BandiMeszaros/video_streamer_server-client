package videoServer.streamApi;


import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter;

public class RTPserver {


    private  RTPserver rtpServer_object;


    public String mediaRoot;
    private final  String options;
    private MediaPlayer mediaPlayer;
    private boolean streamRootSet = false;


    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public String getMediaRoot() {
        return mediaRoot;
    }

    public void setMediaRoot(String mediaRoot) {

        if (streamRootSet) {
            StopAndReleaseMedia();
            MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
            mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();
        }
        this.mediaRoot = mediaRoot;

        mediaPlayer.media().startPaused(mediaRoot);
        streamRootSet = true;
    }

    public RTPserver() {
        options = formatRtpStream("127.0.0.1", 5555);
        MediaPlayerFactory mediaPlayerFactory = new MediaPlayerFactory();
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
        System.out.println("\nStreaming '" + mediaRoot + "' to '" + options + "'");
    }

    public void StopAndReleaseMedia(){
        System.out.printf("Stopping stream: %s ", mediaRoot);
        mediaPlayer.release();
    }
    public void setStreamSet(boolean v)
    {
        streamRootSet = v;
    }

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
