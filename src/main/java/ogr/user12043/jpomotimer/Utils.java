package ogr.user12043.jpomotimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;

/**
 * Created on 19.09.2018 - 11:21
 * part of JPomoTimer
 *
 * @author user12043
 */
public class Utils {
    public static JSpinner getSpinner(int minValue, int maxvalue) {
        JSpinner spinner = new JSpinner();
        spinner.setValue(minValue);
        spinner.addMouseWheelListener(new MouseAdapter() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                int rotation = e.getWheelRotation();
                if (rotation < 0) {
                    spinner.setValue(spinner.getNextValue());
                } else {
                    spinner.setValue(spinner.getPreviousValue());
                }
            }
        });
        spinner.addChangeListener(e -> {
            if (minValue != -1 && ((int) spinner.getValue()) < minValue) {
                spinner.setValue(minValue);
            } else if (maxvalue != -1 && ((int) spinner.getValue()) > maxvalue) {
                spinner.setValue(maxvalue);
            }
        });

        return spinner;
    }

    public static Color selectColor(Dialog parent, Color initialColor) {
        JColorChooser chooser = new JColorChooser(initialColor);
        JDialog dialog = new JDialog(parent, true);
        dialog.setLayout(new FlowLayout());
        dialog.add(chooser);
        JButton button = new JButton("OK");
        button.addActionListener(e -> dialog.dispose());
        dialog.add(button);
        dialog.pack();
        dialog.setLocationByPlatform(true);
        dialog.setResizable(false);
        dialog.setVisible(true);
        return chooser.getColor();
    }
}
