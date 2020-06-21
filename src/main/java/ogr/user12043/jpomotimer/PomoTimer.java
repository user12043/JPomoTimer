package ogr.user12043.jpomotimer;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import java.io.File;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created on 11.09.2018 - 16:46
 * part of JPomoTimer
 *
 * @author user12043
 */
public class PomoTimer {
    private static volatile int minute = 0;
    private static volatile int second = 0;
    private static Thread timerTask;
    private static ScheduledExecutorService service;

    private static Thread getTimerTask() {
//        if (timerTask == null) {
        timerTask = new Thread(() -> {
            int currentMinute = minute;
            int currentSecond = second;

            if (second == 0) {
                if (minute == 0) {
                    timeUp();
                } else {
                    second = 59;
                    minute = currentMinute - 1;
                }
            } else {
                second = currentSecond - 1;
            }
            TimerDialog.get().setTime(minute, second);
        });
//        }
        return timerTask;
    }

    private static ScheduledExecutorService getService() {
        if (service == null || service.isTerminated() || service.isShutdown()) {
            service = new ScheduledThreadPoolExecutor(1);
        }
        return service;
    }

    public static void start(int startMinute) {
        System.out.println("starting timer for " + startMinute + " minutes");
        minute = startMinute;
        getService().scheduleAtFixedRate(getTimerTask(), 0L, 1L, TimeUnit.SECONDS);
//        timer = new Timer(true);
//        timer.scheduleAtFixedRate(getTimerTask(), 0, 200);
    }

    public static void pause() {
        System.out.println("pausing timer");
//        timer.cancel();
        getService().shutdown();
    }

    public static void stop() {
        System.out.println("stopping timer");
//        timer.cancel();
        getService().shutdown();
        minute = 0;
        second = 0;
        TimerDialog.get().setTime(minute, second);
    }

    public static void resume() {
        System.out.println("resuming timer");
        start(minute);
    }

    public static void timeUp() {
        stop();
        SystemTrayIcon.stopped();
        Clip clip = null;
        try {
            clip = AudioSystem.getClip();
            File file = new File(Properties.ALERT_SOUND_PATH);
            if (file.exists()) {
                clip.open(AudioSystem.getAudioInputStream(file));
            } else {
                clip.open(AudioSystem.getAudioInputStream(PomoTimer.class.getResource("/" + Properties.ALERT_SOUND_PATH)));
            }

            clip.start();
        } catch (Exception e) {
            System.err.println("Can not play sound!");
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "could not play sound:\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        final JDialog dialog = new JDialog();
        dialog.setAlwaysOnTop(true);
        JOptionPane.showMessageDialog(dialog, Properties.MESSAGE_TIME_UP, Properties.APP_NAME, JOptionPane.WARNING_MESSAGE);

        if (clip != null) {
            clip.stop();
            clip.flush();
        }

        SystemTrayIcon.onTimeUp();
    }
}
