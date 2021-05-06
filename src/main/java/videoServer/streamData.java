package videoServer;


class streamData {
    private String mediaRoot;
    private RTPserver server;
    private RTP_thread rtp_stream;
    private videoMetaData mediaInfo;

    public streamData(String mediaRoot) {
        this.mediaRoot = mediaRoot;
        rtp_stream = new RTP_thread(server, mediaRoot);
        mediaInfo = new videoMetaData(mediaRoot);
    }

    class RTP_thread extends Thread {

        private final RTPserver server;

        public RTP_thread(RTPserver streamServer, String mediaRoot) {
            server = streamServer;
            server.setMediaRoot(mediaRoot);
        }

        @Override
        public void run() {
            long Threadid = Thread.currentThread().getId();
            System.out.printf("RTP thread is: %d", Threadid);
            server.StreamMedia();
        }
    }
}
