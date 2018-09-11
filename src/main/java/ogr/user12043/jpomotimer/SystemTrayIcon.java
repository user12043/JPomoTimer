package ogr.user12043.jpomotimer;

import java.awt.*;

/**
 * Created on 11.09.2018 - 16:39
 * part of JPomoTimer
 *
 * @author user12043
 */
public class SystemTrayIcon {
    private static TrayIcon trayIcon;
    private static MenuItem startWorkItem;
    private static MenuItem startBreakItem;
    private static MenuItem startLongBreakItem;
    private static MenuItem pauseItem;
    private static MenuItem resumeItem;
    private static MenuItem stopItem;
    private static MenuItem showTimerItem;
    private static Menu settingsItem;
    private static MenuItem exitItem;

    private SystemTrayIcon() {

    }

    public static void showIcon() throws AWTException {
        SystemTray.getSystemTray().add(getTrayIcon());
        getTrayIcon().displayMessage(Constants.APP_NAME, Constants.MESSAGE_APPLICATION_STARTED, TrayIcon.MessageType.INFO);
    }

    private static TrayIcon getTrayIcon() {
        if (trayIcon == null) {
            Image image = Toolkit.getDefaultToolkit().getImage(Constants.DARK_ICON_PATH); // TODO add icons' maker <div>Icons made by <a href="http://www.freepik.com" title="Freepik">Freepik</a> from <a href="https://www.flaticon.com/" title="Flaticon">www.flaticon.com</a> is licensed by <a href="http://creativecommons.org/licenses/by/3.0/" title="Creative Commons BY 3.0" target="_blank">CC 3.0 BY</a></div>
            trayIcon = new TrayIcon(image, Constants.APP_NAME, getPopupMenu());
            trayIcon.setImageAutoSize(true);
        }
        return trayIcon;
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
        startWorkItem = new MenuItem("Start Timer (Work)");
        startWorkItem.addActionListener(e -> {
            Timer.start(Constants.WORK_TIME);
            startWorkItem.setEnabled(false);
            startBreakItem.setEnabled(true);
            startLongBreakItem.setEnabled(true);
            stopItem.setEnabled(true);
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(true);
        });
        return startWorkItem;
    }

    private static MenuItem getStartBreakItem() {
        startBreakItem = new MenuItem("Start Timer (Break)");
        startBreakItem.addActionListener(e -> {
            Timer.start(Constants.BREAK_TIME);
            startWorkItem.setEnabled(true);
            startBreakItem.setEnabled(false);
            startLongBreakItem.setEnabled(true);
            stopItem.setEnabled(true);
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(true);
        });
        return startBreakItem;
    }

    private static MenuItem getStartLongBreakItem() {
        startLongBreakItem = new MenuItem("Start Timer (Long Break)");
        startLongBreakItem.addActionListener(e -> {
            Timer.start(Constants.LONG_BREAK_TIME);
            startWorkItem.setEnabled(true);
            startBreakItem.setEnabled(true);
            startLongBreakItem.setEnabled(false);
            stopItem.setEnabled(true);
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(true);
        });
        return startLongBreakItem;
    }

    private static MenuItem getPauseItem() {
        pauseItem = new MenuItem("Pause Timer");
        pauseItem.addActionListener(e -> {
            Timer.pause();
            pauseItem.setEnabled(false);
            resumeItem.setEnabled(true);
        });
        return pauseItem;
    }

    private static MenuItem getResumeItem() {
        resumeItem = new MenuItem("Resume Timer");
        resumeItem.addActionListener(e -> {
            Timer.resume();
            pauseItem.setEnabled(true);
            resumeItem.setEnabled(false);
        });
        return resumeItem;
    }

    private static MenuItem getStopItem() {
        stopItem = new MenuItem("Stop Timer");
        stopItem.addActionListener(e -> {
            Timer.stop();
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
        showTimerItem = new MenuItem("Show timer dialog");
        showTimerItem.addActionListener(e -> {
            final boolean state = TimerDialog.get().isVisible();
            TimerDialog.get().setVisible(!state);
            if (state) {
                showTimerItem.setLabel("Show timer dialog");
            } else {
                showTimerItem.setLabel("Hide timer dialog");
            }
        });
        return showTimerItem;
    }

    private static Menu getSettingsItem() {
        settingsItem = new Menu("Settings");
        settingsItem.addActionListener(e -> System.out.println());
        return settingsItem;
    }

    private static MenuItem getExitItem() {
        exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        return exitItem;
    }
}
