package ogr.user12043.jpomotimer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
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
    private static Timer timer;

    private static TimerTask getTimerTask() {
        return new TimerTask() {
            @Override
            public void run() {
                if (second == 0) {
                    if (minute == 0) {
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
    }

    public static void start(int startMinute) {
        System.out.println("starting timer for " + startMinute + " minute");
        minute = startMinute;
        timer = new Timer(true);
        timer.scheduleAtFixedRate(getTimerTask(), 0, 200);
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
        stop();
        SystemTrayIcon.stopped();
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            File file = new File(Constants.ALERT_SOUND_PATH);
            if (file.exists()) {
                clip.open(AudioSystem.getAudioInputStream(file));
            } else {
                clip.open(AudioSystem.getAudioInputStream(PomoTimer.class.getResource("/" + Constants.ALERT_SOUND_PATH)));
            }

            clip.start();
        } catch (Exception e) {
            System.err.println("Can not play sound!");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "could not play sound:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, Constants.MESSAGE_TIME_UP, Constants.APP_NAME, JOptionPane.WARNING_MESSAGE);

        if (clip != null) {
            clip.stop();
            clip.flush();
        }


        timer.cancel();
        if (Constants.continuousMode) {
            if (SystemTrayIcon.working) {
                // break
                if (SystemTrayIcon.worked == 4) {
                    // if 4 pomodoro passed
                    SystemTrayIcon.worked = 0;
                    start(Constants.longBreakTime);
                    SystemTrayIcon.startedLongBreak();
                } else {
                    start(Constants.breakTime);
                    SystemTrayIcon.startedBreak();
                }
            } else {
                // start work
                start(Constants.workTime);
                SystemTrayIcon.startedWork();
            }
        }
    }
}
