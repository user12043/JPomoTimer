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
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Can not set Nimbus look and feel");
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
