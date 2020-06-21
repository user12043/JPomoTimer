package ogr.user12043.jpomotimer;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * Created on 11.09.2018 - 16:39
 * part of JPomoTimer
 *
 * @author user12043
 */
public class SystemTrayIcon {
    public static boolean working;
    public static int worked = 0;
    private static TrayIcon trayIcon;
    private static MenuItem startWorkItem;
    private static MenuItem startBreakItem;
    private static MenuItem startLongBreakItem;
    private static MenuItem pauseItem;
    private static MenuItem resumeItem;
    private static MenuItem stopItem;
    private static MenuItem showTimerItem;
    private static MenuItem settingsItem;
    private static MenuItem exitItem;

    private SystemTrayIcon() {

    }

    public static void showIcon() throws AWTException {
        SystemTray.getSystemTray().add(getTrayIcon());
        getTrayIcon().displayMessage(Properties.APP_NAME, Properties.MESSAGE_APPLICATION_STARTED, TrayIcon.MessageType.INFO);
    }

    private static TrayIcon getTrayIcon() {
        if (trayIcon == null) {
            trayIcon = new TrayIcon(getIconImage(), Properties.APP_NAME, getPopupMenu());
            trayIcon.setImageAutoSize(true);
            getIconImage();
        }
        return trayIcon;
    }

    private static Image getIconImage() {
        Image image;
        String iconPath = (Properties.iconThemeDark) ? Properties.DARK_ICON_PATH : Properties.LIGHT_ICON_PATH;
        File file = new File(iconPath);
        if (file.exists()) {
            image = Toolkit.getDefaultToolkit().getImage(iconPath);
        } else {
            try {
                image = ImageIO.read(SystemTrayIcon.class.getResourceAsStream("/" + iconPath));
            } catch (IOException e) {
                System.err.println("Can not load tray icon image");
                image = Toolkit.getDefaultToolkit().getImage(iconPath);
            }
        }
        return image;
    }

    public static void updateIconImage() {
        trayIcon.setImage(getIconImage());
    }

    private static PopupMenu getPopupMenu() {
        PopupMenu menu = new PopupMenu("Options");

        menu.add(getStartWorkItem());
        menu.add(getStartBreakItem());
        menu.add(getStartLongBreakItem());
        menu.add(getPauseItem());
        menu.add(getResumeItem());
        menu.add(getStopItem());
        menu.add(getShowTimerItem());
        menu.add(getSettingsItem());
        menu.add(getExitItem());
        return menu;
    }

    private static MenuItem getStartWorkItem() {
        startWorkItem = new MenuItem("Start Work (worked 0 times)");
        startWorkItem.addActionListener(e -> {
            PomoTimer.start(Properties.workTime);
            startedWork();
        });
        return startWorkItem;
    }

    private static MenuItem getStartBreakItem() {
        startBreakItem = new MenuItem("Start Break");
        startBreakItem.addActionListener(e -> {
            PomoTimer.start(Properties.breakTime);
            startedBreak();
        });
        return startBreakItem;
    }

    private static MenuItem getStartLongBreakItem() {
        startLongBreakItem = new MenuItem("Start Long Break");
        startLongBreakItem.addActionListener(e -> {
            PomoTimer.start(Properties.longBreakTime);
            startedLongBreak();
        });
        return startLongBreakItem;
    }

    private static MenuItem getPauseItem() {
        pauseItem = new MenuItem("Pause Timer");
        pauseItem.setEnabled(false);
        pauseItem.addActionListener(e -> {
            PomoTimer.pause();
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(true);
        });
        return pauseItem;
    }

    private static MenuItem getResumeItem() {
        resumeItem = new MenuItem("Resume Timer");
        resumeItem.setEnabled(false);
        resumeItem.addActionListener(e -> {
            PomoTimer.resume();
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(false);
        });
        return resumeItem;
    }

    private static MenuItem getStopItem() {
        stopItem = new MenuItem("Stop Timer");
        stopItem.setEnabled(false);
        stopItem.addActionListener(e -> {
            PomoTimer.stop();
            stopItem.setEnabled(false);
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(false);
            startWorkItem.setEnabled(true);
            startBreakItem.setEnabled(true);
            startLongBreakItem.setEnabled(true);
        });
        return stopItem;
    }

    private static MenuItem getShowTimerItem() {
        showTimerItem = new MenuItem("Hide timer dialog");
        showTimerItem.addActionListener(e -> {
            final boolean state = TimerDialog.get().isVisible();
            TimerDialog.get().setVisible(!state);
            showTimerItem.setLabel((state ? "Show" : "Hide") + " timer dialog");
        });
        return showTimerItem;
    }

    private static MenuItem getSettingsItem() {
        settingsItem = new MenuItem("Settings");
        settingsItem.addActionListener(e -> SwingUtilities.invokeLater(() -> SettingsDialog.get().setVisible(true)));
        return settingsItem;
    }

    private static MenuItem getExitItem() {
        exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        return exitItem;
    }

    public static void startedWork() {
        startWorkItem.setEnabled(false);
        startBreakItem.setEnabled(false);
        startLongBreakItem.setEnabled(false);
        stopItem.setEnabled(true);
        pauseItem.setEnabled(true);
        resumeItem.setEnabled(false);
        working = true;

        worked++;
        startWorkItem.setLabel("Start Work" + " (worked " + worked + " times)");
    }

    public static void startedBreak() {
        startWorkItem.setEnabled(false);
        startBreakItem.setEnabled(false);
        startLongBreakItem.setEnabled(false);
        stopItem.setEnabled(true);
        pauseItem.setEnabled(true);
        resumeItem.setEnabled(false);
        working = false;
    }

    public static void startedLongBreak() {
        startWorkItem.setEnabled(false);
        startBreakItem.setEnabled(false);
        startLongBreakItem.setEnabled(false);
        stopItem.setEnabled(true);
        pauseItem.setEnabled(true);
        resumeItem.setEnabled(false);
        working = false;
    }

    public static void stopped() {
        stopItem.setEnabled(false);
        pauseItem.setEnabled(false);
        resumeItem.setEnabled(false);
        startWorkItem.setEnabled(true);
        startBreakItem.setEnabled(true);
        startLongBreakItem.setEnabled(true);
    }

    public static void onTimeUp() {
        if (Properties.continuousMode) {
            if (working) {
                // break
                if (worked == 4) {
                    // if 4 pomodoro passed
                    worked = 0;
                    SwingUtilities.invokeLater(() -> PomoTimer.start(Properties.longBreakTime));
                    startedLongBreak();
                } else {
                    SwingUtilities.invokeLater(() -> PomoTimer.start(Properties.breakTime));
                    startedBreak();
                }
            } else {
                // start work
                SwingUtilities.invokeLater(() -> PomoTimer.start(Properties.workTime));
                startedWork();
            }
        }
    }
}
