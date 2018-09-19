package ogr.user12043.jpomotimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SettingsDialog extends JDialog {
    private static SettingsDialog settingsDialog;

    private JPanel contentPane;
    private JLabel label_workTime;
    private JLabel label_breakTime;
    private JLabel label_longBreakTime;
    private JSpinner spinner_workTime;
    private JSpinner spinner_breakTime;
    private JSpinner spinner_longBreakTime;
    private JButton button_apply;
    private JLabel label_timerPanelBackground;
    private JLabel label_timerPanelForeground;
    private JButton button_timerPanelBackground;
    private JButton button_timerPanelForeground;
    private JButton button_setDefaults;
    private JLabel label_iconTheme;
    private JRadioButton radio_iconThemeDark;
    private JRadioButton radio_iconThemeLight;
    private JSpinner spinner_timerPanelFontSize;
    private JLabel label_timerPanelFontSize;

    private SettingsDialog() {
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        setLocationByPlatform(true);
        pack();
        setMinimumSize(new Dimension(getPreferredSize().width + 10, getPreferredSize().height));
        setValues();
        button_timerPanelBackground.addActionListener(this::button_timerBackgroundAction);
        button_timerPanelForeground.addActionListener(this::button_timerForegroundAction);
        button_setDefaults.addActionListener(this::button_setDefaultsAction);
        button_apply.addActionListener(this::button_applyAction);
    }

    public static SettingsDialog get() {
        if (settingsDialog == null) {
            settingsDialog = new SettingsDialog();
        }
        return settingsDialog;
    }

    private void createUIComponents() {
        spinner_workTime = Utils.getSpinner(5, Integer.MAX_VALUE);
        spinner_breakTime = Utils.getSpinner(5, Integer.MAX_VALUE);
        spinner_longBreakTime = Utils.getSpinner(5, Integer.MAX_VALUE);
        spinner_timerPanelFontSize = Utils.getSpinner(10, 150);
        radio_iconThemeDark = new JRadioButton();
        radio_iconThemeLight = new JRadioButton();
        ButtonGroup buttonGroup = new ButtonGroup();
        buttonGroup.add(radio_iconThemeDark);
        buttonGroup.add(radio_iconThemeLight);
    }

    private void setValues() {
        spinner_workTime.setValue(Constants.workTime);
        spinner_breakTime.setValue(Constants.breakTime);
        spinner_longBreakTime.setValue(Constants.longBreakTime);
        spinner_timerPanelFontSize.setValue(Constants.timerDialogFontSize);
        button_timerPanelBackground.setBackground(Constants.timerPanelBackground);
        button_timerPanelForeground.setBackground(Constants.timerPanelForeground);
        radio_iconThemeDark.setSelected(Constants.iconThemeDark);
        radio_iconThemeLight.setSelected(!Constants.iconThemeDark);
    }

    private void button_timerBackgroundAction(ActionEvent event) {
        final Color selectedColor = Utils.selectColor(this, button_timerPanelBackground.getBackground());
        button_timerPanelBackground.setBackground(selectedColor);
    }

    private void button_timerForegroundAction(ActionEvent event) {
        final Color selectedColor = Utils.selectColor(this, button_timerPanelForeground.getBackground());
        button_timerPanelForeground.setBackground(selectedColor);
    }

    private void button_applyAction(ActionEvent event) {
        Constants.workTime = (int) spinner_workTime.getValue();
        Constants.breakTime = (int) spinner_breakTime.getValue();
        Constants.longBreakTime = (int) spinner_longBreakTime.getValue();
        Constants.timerDialogFontSize = (int) spinner_timerPanelFontSize.getValue();
        Constants.timerPanelBackground = button_timerPanelBackground.getBackground();
        Constants.timerPanelForeground = button_timerPanelForeground.getBackground();
        Constants.iconThemeDark = radio_iconThemeDark.isSelected();
        TimerDialog.get().updateProperties();
        SystemTrayIcon.updateIconImage();
    }

    private void button_setDefaultsAction(ActionEvent event) {
        spinner_workTime.setValue(25);
        spinner_breakTime.setValue(5);
        spinner_longBreakTime.setValue(15);
        spinner_timerPanelFontSize.setValue(30);
        button_timerPanelBackground.setBackground(Color.BLACK);
        button_timerPanelForeground.setBackground(Color.WHITE);
        radio_iconThemeDark.setSelected(true);
    }

    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new GridBagLayout());
        contentPane.setMinimumSize(new Dimension(540, 290));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridBagLayout());
        GridBagConstraints gbc;
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.anchor = GridBagConstraints.NORTH;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 10, 0, 10);
        contentPane.add(panel1, gbc);
        label_workTime = new JLabel();
        label_workTime.setText("Work time: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_workTime, gbc);
        label_breakTime = new JLabel();
        label_breakTime.setText("Break time: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_breakTime, gbc);
        label_longBreakTime = new JLabel();
        label_longBreakTime.setText("Long break time: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_longBreakTime, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.weightx = 6.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel1.add(spinner_workTime, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.weightx = 6.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel1.add(spinner_breakTime, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.weightx = 6.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel1.add(spinner_longBreakTime, gbc);
        label_timerPanelBackground = new JLabel();
        label_timerPanelBackground.setText("Timer panel background: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_timerPanelBackground, gbc);
        label_timerPanelForeground = new JLabel();
        label_timerPanelForeground.setText("Timer panel foreground: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_timerPanelForeground, gbc);
        button_timerPanelBackground = new JButton();
        button_timerPanelBackground.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 5;
        gbc.gridwidth = 2;
        gbc.weightx = 6.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel1.add(button_timerPanelBackground, gbc);
        button_timerPanelForeground = new JButton();
        button_timerPanelForeground.setText("");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        gbc.weightx = 6.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel1.add(button_timerPanelForeground, gbc);
        label_iconTheme = new JLabel();
        label_iconTheme.setText("Icon theme: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 7;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_iconTheme, gbc);
        radio_iconThemeDark.setText("Dark");
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 7;
        panel1.add(radio_iconThemeDark, gbc);
        radio_iconThemeLight.setText("Light");
        gbc = new GridBagConstraints();
        gbc.gridx = 3;
        gbc.gridy = 7;
        panel1.add(radio_iconThemeLight, gbc);
        label_timerPanelFontSize = new JLabel();
        label_timerPanelFontSize.setText("Timer panel fon size: ");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 20);
        panel1.add(label_timerPanelFontSize, gbc);
        gbc = new GridBagConstraints();
        gbc.gridx = 2;
        gbc.gridy = 4;
        gbc.gridwidth = 2;
        gbc.weightx = 6.0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(0, 0, 10, 0);
        panel1.add(spinner_timerPanelFontSize, gbc);
        button_setDefaults = new JButton();
        button_setDefaults.setText("Apply Defaults");
        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        contentPane.add(button_setDefaults, gbc);
        button_apply = new JButton();
        button_apply.setText("Apply");
        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.insets = new Insets(0, 0, 20, 0);
        contentPane.add(button_apply, gbc);
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }
}
