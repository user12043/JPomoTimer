package ogr.user12043.jpomotimer;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 11.09.2018 - 15:47
 * part of JPomoTimer
 *
 * @author user12043
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                SystemTrayIcon.showIcon();
                TimerDialog.get().setVisible(true);
            } catch (AWTException e) {
                System.err.println("Can not create tray icon");
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("Unexpected error!");
                e.printStackTrace();
            }
        });
    }
}
