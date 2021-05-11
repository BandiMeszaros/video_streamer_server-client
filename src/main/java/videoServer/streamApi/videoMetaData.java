package videoServer.streamApi;

import uk.co.caprica.vlcj.player.base.State;

public class videoMetaData {

    private final String mediaRoot;
    private State videoState;
    private final long videoLength_ms;
    private final int videoLength_s;
    private final RTPserver server;


    public videoMetaData(RTPserver server) {
        this.server = server;
        this.mediaRoot = server.getMediaRoot();

        videoState = server.getMediaPlayer().media().info().state();
        videoLength_ms = server.getMediaPlayer().media().info().duration();
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
        videoState = server.getMediaPlayer().media().info().state();
        return videoState;
    }

    public void setVideoState(State videoState) {
        this.videoState = videoState;
    }


}
