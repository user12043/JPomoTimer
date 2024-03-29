package ogr.user12043.jpomotimer;

import java.awt.*;

/**
 * Created on 11.09.2018 - 16:40
 * part of JPomoTimer
 *
 * @author user12043
 */
class Properties {
    public static final String APP_NAME = "JPomoTimer";
    public static final String SETTINGS_FILE = "settings.json";
    public static final String ICON_PATH = "res/icon-pomodoro-with-timer.png";
    public static final String PAUSE_ICON_PATH = "res/icon-pause.png";
    public static final String RESUME_ICON_PATH = "res/icon-resume.png";
    public static final String STOP_ICON_PATH = "res/icon-stop.png";
    public static final String ALERT_SOUND_PATH = "res/sound-timeup.wav";
    public static final String MESSAGE_APPLICATION_STARTED = "JPomoTimer is active\nYou can use the app from system tray.";
    public static final String MESSAGE_TIME_UP = "Time is up!";
    public static Color timerPanelBackground = Color.BLACK;
    public static Color timerPanelForeground = Color.WHITE;
    public static int timerDialogFontSize = 30;
    public static int workTime = 25;
    public static int breakTime = 5;
    public static int longBreakTime = 15;
    public static boolean iconThemeDark = true;
    public static boolean continuousMode = true;
}
