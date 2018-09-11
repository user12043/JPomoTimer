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


    private SystemTrayIcon() {

    }

    public static void showIcon() throws AWTException {
        SystemTray.getSystemTray().add(getTrayIcon());
        getTrayIcon().displayMessage(Constants.APP_NAME, Constants.MESSAGE_APPLICATION_STARTED, TrayIcon.MessageType.INFO);
    }

    public static void hideIcon() {
        SystemTray.getSystemTray().remove(trayIcon);
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
        menu.add(getStopItem());
        menu.add(getSettingsItem());
        menu.add(getExitItem());
        return menu;
    }

    private static MenuItem getStartWorkItem() {
        MenuItem startWorkItem = new MenuItem("Start Timer (Work)");
        startWorkItem.addActionListener(e -> System.out.println());
        return startWorkItem;
    }

    private static MenuItem getStartBreakItem() {
        MenuItem startBreakItem = new MenuItem("Start Timer (Break)");
        startBreakItem.addActionListener(e -> System.out.println());
        return startBreakItem;
    }

    private static MenuItem getStartLongBreakItem() {
        MenuItem startLongBreakItem = new MenuItem("Start Timer (Long Break)");
        startLongBreakItem.addActionListener(e -> System.out.println());
        return startLongBreakItem;
    }

    private static MenuItem getPauseItem() {
        MenuItem pauseItem = new MenuItem("Pause Timer");
        pauseItem.addActionListener(e -> System.out.println());
        return pauseItem;
    }

    private static MenuItem getStopItem() {
        MenuItem stopItem = new MenuItem("Stop Timer");
        stopItem.addActionListener(e -> System.out.println());
        return stopItem;
    }

    private static Menu getSettingsItem() {
        Menu settingsItem = new Menu("Settings");
        settingsItem.addActionListener(e -> System.out.println());
        return settingsItem;
    }

    private static MenuItem getExitItem() {
        MenuItem exitItem = new MenuItem("Exit");
        exitItem.addActionListener(e -> System.out.println());
        return exitItem;
    }
}
