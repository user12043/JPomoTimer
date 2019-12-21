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
        Utils.loadSettings();

        /* Set the look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code ">
        /* If Windows or CDE/Motif is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                } else if ("CDE/Motif".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Can not set look and feel");
        }
        //</editor-fold>
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
