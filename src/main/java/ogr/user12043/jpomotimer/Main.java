package ogr.user12043.jpomotimer;

import java.awt.*;

/**
 * Created on 11.09.2018 - 15:47
 * part of JPomoTimer
 *
 * @author user12043
 */
public class Main {
    public static void main(String[] args) {
        try {
            SystemTrayIcon.showIcon();
        } catch (AWTException e) {
            System.err.println("Can not create tray icon");
            e.printStackTrace();
        }
    }
}
