package videoServer;

import uk.co.caprica.vlcj.factory.MediaPlayerFactory;
import uk.co.caprica.vlcj.player.base.MediaPlayer;
import uk.co.caprica.vlcj.player.base.State;

public class videoMetaData {

    private final String mediaRoot;
    private final MediaPlayerFactory mediaPlayerFactory;
    private final MediaPlayer mediaPlayer;
    private State videoState;
    private final long videoLength_ms;
    private final int videoLength_s;


    public videoMetaData(String mediaRoot) {
        this.mediaRoot = mediaRoot;
        mediaPlayerFactory = new MediaPlayerFactory();
        mediaPlayer = mediaPlayerFactory.mediaPlayers().newMediaPlayer();
        videoState = mediaPlayer.media().info().state();
        videoLength_ms = mediaPlayer.media().info().duration();
        videoLength_s = (int)videoLength_ms/1000;

    }

    public long getVideoLength_ms() {
        return videoLength_ms;
    }

    public int getVideoLength_s() {
        return videoLength_s;
    }

    public String getMediaRoot() {
        return mediaRoot;
    }

    public State getVideoState() {
        return videoState;
    }

    public void setVideoState(State videoState) {
        this.videoState = videoState;
    }


}
