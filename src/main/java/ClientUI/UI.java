package ClientUI;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class UI {

    private final JFrame frame;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private final String VideoRoot;

    public UI(String videoroot) {
        VideoRoot = videoroot;
        frame = new JFrame("MediaPlayer");
        frame.setBounds(100,100,600,400);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                mediaPlayerComponent.release();
                System.exit(0);
            }
        });
        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        frame.setContentPane(mediaPlayerComponent);

        frame.setVisible(true);
        mediaPlayerComponent.mediaPlayer().media().play(VideoRoot);
    }


    public static void main(String[] args) {
        final UI a = new UI(args[0]);
    }

}