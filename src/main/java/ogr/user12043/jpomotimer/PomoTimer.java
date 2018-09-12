package ogr.user12043.jpomotimer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created on 11.09.2018 - 16:46
 * part of JPomoTimer
 *
 * @author user12043
 */
public class PomoTimer {
    private static int minute = 0;
    private static int second = 0;
    private static Timer timer = new Timer(true);

    private static TimerTask getTimerTask() {
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                if (second == 0) {
                    if (minute == 0) {
                        timer.cancel();
                        timeUp();
                    } else {
                        second = 59;
                        minute--;
                    }
                } else {
                    second--;
                }
                TimerDialog.get().setTime(minute, second);
            }
        };
        return timerTask;
    }

    public static void start(int startMinute) {
        System.out.println("starting timer for " + startMinute + " minute");
        minute = startMinute;
        timer.scheduleAtFixedRate(getTimerTask(), 0, 1000);
    }

    public static void pause() {
        System.out.println("pausing timer");
        timer.cancel();
    }

    public static void resume() {
        System.out.println("resuming timer");
        start(minute);
    }

    public static void stop() {
        System.out.println("stopping timer");
        timer.cancel();
        minute = 0;
        second = 0;
        TimerDialog.get().setTime(minute, second);
    }

    public static void timeUp() {
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(Constants.ALERT_SOUND_PATH)));
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
//            System.err.println("Can not play sound!");
            e.printStackTrace();
        }

        if (clip != null) {
            clip.start();
        }

        JOptionPane.showMessageDialog(null, Constants.MESSAGE_TIMEUP, Constants.APP_NAME, JOptionPane.WARNING_MESSAGE);

        if (clip != null) {
            clip.stop();
        }
    }
}
