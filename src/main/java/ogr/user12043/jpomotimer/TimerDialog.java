package ogr.user12043.jpomotimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Created on 11.09.2018 - 16:49
 * part of JPomoTimer
 *
 * @author user12043
 */
public class TimerDialog extends JDialog {
    private static TimerDialog timerDialog;
    private JLabel label_minute;
    private JLabel label_separator;
    private JLabel label_second;
    private Point mouseClickPoint;

    private TimerDialog() {
        initComponents();
        addEventsForMovingWindow();
    }

    public static TimerDialog get() {
        if (timerDialog == null) {
            timerDialog = new TimerDialog();
        }

        return timerDialog;
    }

    private void addEventsForMovingWindow() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseClickPoint = e.getPoint();
            }

        });
        addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                TimerDialog.this.mouseDragged(e);
            }
        });
    }

    public void mouseDragged(MouseEvent event) {
        Point newPoint = event.getLocationOnScreen();
        newPoint.translate(-mouseClickPoint.x, -mouseClickPoint.y);
        setLocation(newPoint);
    }

    private void initComponents() {
        setLayout(new FlowLayout());
        label_minute = new JLabel("00");
        Font font = label_minute.getFont().deriveFont(Font.BOLD, Constants.TIMER_DIALOG_FONT_SIZE);
        label_minute.setFont(font);
        label_separator = new JLabel(" : ");
        label_separator.setFont(font);
        label_second = new JLabel("00");
        label_second.setFont(font);

        add(label_minute);
        add(label_separator);
        add(label_second);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);
        pack();
    }
}
