package videoServer.streamApi;


class streamVideoError extends Exception
{
    public streamVideoError(String message) {
        super(message);
    }
}


public class streamData {
    final RTPserver server = RTPserver.getRtpServer_object();
    final RTP_thread RtpStreamThread;
    final videoMetaData mediaInfo;

    public RTPserver getServer() {
        return server;
    }

    public RTP_thread getRtpStreamThread() {
        return RtpStreamThread;
    }

    public videoMetaData getMediaInfo() {
        return mediaInfo;
    }

    public streamData(String mediaRoot) {

        server.setMediaRoot(mediaRoot);
        mediaInfo = new videoMetaData(server);
        RtpStreamThread = new RTP_thread(server, mediaInfo);
        RtpStreamThread.setName("RTP_stream_thread");
    }

    static class RTP_thread extends Thread {
        private final RTPserver server;
        private final videoMetaData mediaInfo;
        RTP_thread(RTPserver server, videoMetaData mediaInfo)
        {
            this.mediaInfo = mediaInfo;
            this.server = server;
        }
        @Override
        public void run() {
            long Threadid = Thread.currentThread().getId();
            String ThreadName = Thread.currentThread().getName();
            System.out.printf("RTP thread is: %d -- %s", Threadid, ThreadName);
            try {
                server.StreamMedia();
                uk.co.caprica.vlcj.player.base.State streamState = mediaInfo.getVideoState();
                System.out.println("Streaming video curently....");
                System.out.printf("Streamed content length: %d seconds\n", mediaInfo.getVideoLength_s());
                while (streamState != uk.co.caprica.vlcj.player.base.State.ENDED) {
                    try {
                        uk.co.caprica.vlcj.player.base.State tempState = mediaInfo.getVideoState();
                        if (tempState != streamState) {
                            streamState = tempState;
                            System.out.printf("Streaming video curently, streamState: %s\n", streamState.name());
                        }
                        if (tempState == uk.co.caprica.vlcj.player.base.State.ERROR) {
                            throw new streamVideoError("Error streaming video....\n");
                        }
                    }
                    catch (streamVideoError e)
                    {
                        e.printStackTrace();
                        server.StopAndReleaseMedia();
                        Thread.currentThread().interrupt();
                    }
                }
                if (streamState == uk.co.caprica.vlcj.player.base.State.ENDED)
                {
                    server.setStreamSet(false);
                }

            }
            catch (Exception e)
            {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }
}
