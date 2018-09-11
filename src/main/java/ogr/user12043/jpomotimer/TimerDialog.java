package ogr.user12043.jpomotimer;

import javax.swing.*;
import java.awt.*;

/**
 * Created on 11.09.2018 - 16:49
 * part of JPomoTimer
 *
 * @author user12043
 */
public class TimerDialog extends JDialog {
    private JLabel label_minute;
    private JLabel label_separator;
    private JLabel label_second;

    public TimerDialog() {
        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        setLayout(new FlowLayout());
        label_minute = new JLabel("00");
        label_separator = new JLabel(" : ");
        label_second = new JLabel("00");
        System.out.println(label_minute.getFont().getSize());

        add(label_minute);
        add(label_separator);
        add(label_second);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
    }
}
