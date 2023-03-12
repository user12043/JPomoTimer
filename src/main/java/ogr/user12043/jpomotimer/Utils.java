package ogr.user12043.jpomotimer;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.io.*;
import java.util.NoSuchElementException;

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
            if (fileContent.isEmpty()) {
                return;
            }
            JSONObject jsonObject = new JSONObject(fileContent);
            if (jsonObject.has("timerPanelBackground")) {
                Properties.timerPanelBackground = new Color(jsonObject.getInt("timerPanelBackground"));
            }
            if (jsonObject.has("timerPanelForeground")) {
                Properties.timerPanelForeground = new Color(jsonObject.getInt("timerPanelForeground"));
            }
            if (jsonObject.has("timerDialogFontSize")) {
                Properties.timerDialogFontSize = jsonObject.getInt("timerDialogFontSize");
            }
            if (jsonObject.has("workTime")) {
                Properties.workTime = jsonObject.getInt("workTime");
            }
            if (jsonObject.has("breakTime")) {
                Properties.breakTime = jsonObject.getInt("breakTime");
            }
            if (jsonObject.has("longBreakTime")) {
                Properties.longBreakTime = jsonObject.getInt("longBreakTime");
            }
            if (jsonObject.has("iconThemeDark")) {
                Properties.iconThemeDark = jsonObject.getBoolean("iconThemeDark");
            }
            if (jsonObject.has("continuousMode")) {
                Properties.continuousMode = jsonObject.getBoolean("continuousMode");
            }
        } catch (IOException | JSONException e) {
            System.err.println("Failed to read settings file. Using default settings.");
        }
    }

    public static void updateSettings() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.putOnce("timerPanelBackground", Properties.timerPanelBackground.getRGB());
            jsonObject.putOnce("timerPanelForeground", Properties.timerPanelForeground.getRGB());
            jsonObject.putOnce("timerDialogFontSize", Properties.timerDialogFontSize);
            jsonObject.putOnce("workTime", Properties.workTime);
            jsonObject.putOnce("breakTime", Properties.breakTime);
            jsonObject.putOnce("longBreakTime", Properties.longBreakTime);
            jsonObject.putOnce("iconThemeDark", Properties.iconThemeDark);
            jsonObject.putOnce("continuousMode", Properties.continuousMode);
            Writer writer = jsonObject.write(new FileWriter(Properties.SETTINGS_FILE), 2, 0);
            writer.close();
        } catch (IOException e) {
            System.err.println("Failed to write settings file.");
        }
    }

    private static String readSettingsFile() throws IOException {
        StringBuilder builder;
        File file = new File(Properties.SETTINGS_FILE);
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

    public static Image getIconImage(String iconPath) {
        Image image;
        File file = new File(iconPath);
        try {
            if (file.exists()) {
                image = Toolkit.getDefaultToolkit().getImage(iconPath);
            } else {
                image = Toolkit.getDefaultToolkit().getImage(SystemTrayIcon.class.getResource("/" + iconPath));
            }
            int size = Properties.timerDialogFontSize / 2;
            image = image.getScaledInstance(size, size, Image.SCALE_SMOOTH);
        } catch (Exception e) {
            System.err.println("Can not load tray icon image");
            return null;
        }
        return image;
    }

    public static UIManager.LookAndFeelInfo findLookAndFeelByName(String name) {
        for (UIManager.LookAndFeelInfo laf : UIManager.getInstalledLookAndFeels()) {
            if (laf.getName().equals(name)) {
                return laf;
            }
        }
        throw new NoSuchElementException();
    }
}
