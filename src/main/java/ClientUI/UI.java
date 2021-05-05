package ClientUI;

import uk.co.caprica.vlcj.player.component.EmbeddedMediaPlayerComponent;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class UI {

    private final JFrame frame;
    private final EmbeddedMediaPlayerComponent mediaPlayerComponent;
    private final String VideoRoot;
    private final JButton play_pauseButton;

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

        JPanel contentPane = new JPanel();
        contentPane.setLayout(new BorderLayout());

        mediaPlayerComponent = new EmbeddedMediaPlayerComponent();
        contentPane.add(mediaPlayerComponent, BorderLayout.CENTER);

        JPanel controlsPane = new JPanel();
        play_pauseButton = new JButton("Pause");
        controlsPane.add(play_pauseButton);
        contentPane.add(controlsPane, BorderLayout.SOUTH);

        play_pauseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mediaPlayerComponent.mediaPlayer().controls().pause();
                if (play_pauseButton.getText().equals("Pause")){
                    play_pauseButton.setText("Play");
                }
                else{ play_pauseButton.setText("Pause");}
            }
        });

        frame.setContentPane(contentPane);

        frame.setVisible(true);
        mediaPlayerComponent.mediaPlayer().media().play(VideoRoot);
    }


    public static void main(String[] args) {
        final UI a = new UI(args[0]);
    }

}