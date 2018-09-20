package ogr.user12043.jpomotimer;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.*;

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

    public static void loadSettings() {
        try {
            String fileContent = readSettingsFile();
            JSONObject jsonObject = new JSONObject(fileContent);
            if (jsonObject.has("timerPanelBackground")) {
                Constants.timerPanelBackground = new Color(jsonObject.getInt("timerPanelBackground"));
            }
            if (jsonObject.has("timerPanelForeground")) {
                Constants.timerPanelForeground = new Color(jsonObject.getInt("timerPanelForeground"));
            }
            if (jsonObject.has("timerDialogFontSize")) {
                Constants.timerDialogFontSize = jsonObject.getInt("timerDialogFontSize");
            }
            if (jsonObject.has("workTime")) {
                Constants.workTime = jsonObject.getInt("workTime");
            }
            if (jsonObject.has("breakTime")) {
                Constants.breakTime = jsonObject.getInt("breakTime");
            }
            if (jsonObject.has("longBreakTime")) {
                Constants.longBreakTime = jsonObject.getInt("longBreakTime");
            }
            if (jsonObject.has("iconThemeDark")) {
                Constants.iconThemeDark = jsonObject.getBoolean("iconThemeDark");
            }
        } catch (IOException | JSONException e) {
            System.err.println("Failed to read settings file. Using default settings.");
            e.printStackTrace();
        }
    }

    public static void updateSettings() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("timerPanelBackground", Constants.timerPanelBackground.getRGB());
            jsonObject.putOnce("timerPanelForeground", Constants.timerPanelForeground.getRGB());
            jsonObject.putOnce("timerDialogFontSize", Constants.timerDialogFontSize);
            jsonObject.putOnce("workTime", Constants.workTime);
            jsonObject.putOnce("breakTime", Constants.breakTime);
            jsonObject.putOnce("longBreakTime", Constants.longBreakTime);
            jsonObject.putOnce("iconThemeDark", Constants.iconThemeDark);
            Writer writer = jsonObject.write(new FileWriter(Constants.SETTINGS_FILE), 2, 0);
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to write settings file.");
        }
    }

    private static String readSettingsFile() throws IOException {
        StringBuilder builder;
        File file = new File(Constants.SETTINGS_FILE);
        if (!file.exists()) {
            return "";
        }
        try (FileReader reader = new FileReader(file)) {
            builder = new StringBuilder();
            while (reader.ready()) {
                builder.append((char) reader.read());
            }
        }
        return builder.toString();
    }
}
