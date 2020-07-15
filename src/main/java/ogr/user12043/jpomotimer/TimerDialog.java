package ogr.user12043.jpomotimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * Created on 11.09.2018 - 16:49
 * part of JPomoTimer
 *
 * @author user12043
 */
public class TimerDialog extends JDialog {
    private static TimerDialog timerDialog;
    private Icon pauseIcon;
    private Icon resumeIcon;
    private Icon stopIcon;
    private JPanel timePane;
    private JPanel timeControlPanel;
    private JPanel statusPane;
    private JLabel label_minute;
    private JLabel label_separator;
    private JLabel label_second;
    private JLabel statusLabel;
    private Point mouseClickPoint;
    private JButton pauseResumeButton;
    private JButton stopButton;
    private MouseAdapter mouseAdapter;
    private MouseAdapter mouseAdapterMotion;
    private ScheduledFuture<?> hideTask;

    private TimerDialog() {
        initComponents();
        bindMouseEvents();
        addButtonEvents();
        updateProperties();
        timeControlPanel.setSize(timeControlPanel.getWidth(), 3);
        hidePanels();
    }

    public static TimerDialog get() {
        if (timerDialog == null) {
            timerDialog = new TimerDialog();
        }

        return timerDialog;
    }

    private void hidePanels() {
        // if mouse is still in frame do not close the controls
        if (getContentPane().getMousePosition(true) != null) {
            return;
        }
        hideTask = Executors.newSingleThreadScheduledExecutor().schedule(() -> {
            timeControlPanel.setVisible(false);
            statusPane.setVisible(false);
            pack();
        }, 1L, TimeUnit.SECONDS);
    }

    public void updateProperties() {
        timePane.setBackground(Properties.timerPanelBackground);
        setTimerForeground(Properties.timerPanelForeground);
        Font timerFont = new Font("Sans Serif", Font.BOLD, Properties.timerDialogFontSize);
        label_minute.setFont(timerFont);
        label_separator.setFont(timerFont);
        label_second.setFont(timerFont);
        updateIcons();
        statusLabel.setFont(timerFont.deriveFont(Font.PLAIN, timerFont.getSize() / 3f));
        pack();
    }

    public void setTime(int minute, int second) {
        label_minute.setText(((minute < 10) ? "0" : "") + minute);
        label_second.setText(((second < 10) ? "0" : "") + second);
    }

    private void bindMouseEvents() {
        removeMouseListener(mouseAdapter);
        mouseAdapter = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseClickPoint = e.getPoint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                TimerDialog.this.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                TimerDialog.this.mouseExited(e);
            }
        };
        addMouseListener(mouseAdapter);
        removeMouseMotionListener(mouseAdapterMotion);
        mouseAdapterMotion = new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                TimerDialog.this.mouseDragged(e);
            }
        };
        addMouseMotionListener(mouseAdapterMotion);
    }

    private void addButtonEvents() {
        pauseResumeButton.addActionListener(actionEvent -> {
            if (PomoTimer.isRunning()) {
                PomoTimer.pause();
            } else {
                PomoTimer.resume();
            }
        });
        stopButton.addActionListener(actionEvent -> PomoTimer.stop());
    }

    private void setTimerForeground(Color foregroundColor) {
        label_minute.setForeground(foregroundColor);
        label_separator.setForeground(foregroundColor);
        label_second.setForeground(foregroundColor);
    }

    public void mouseDragged(MouseEvent event) {
        Point newPoint = event.getLocationOnScreen();
        newPoint.translate(-mouseClickPoint.x, -mouseClickPoint.y);
        setLocation(newPoint);
    }

    public void mouseEntered(MouseEvent event) {
        if (hideTask != null && (!hideTask.isCancelled() || !hideTask.isDone())) {
            hideTask.cancel(true);
        }
        timeControlPanel.setVisible(true);
        statusPane.setVisible(true);
        pack();
    }

    public void mouseExited(MouseEvent event) {
        hidePanels();
    }

    public void setStatus(String text) {
        statusLabel.setText(text);
    }

    private void initComponents() {
        setLayout(new FlowLayout());
        JPanel contentPane = new JPanel(new BorderLayout());
        timePane = new JPanel(new FlowLayout());
        statusPane = new JPanel();

        label_minute = new JLabel("00");
        label_separator = new JLabel(" : ");
        label_second = new JLabel("00");

        timePane.add(label_minute);
        timePane.add(label_separator);
        timePane.add(label_second);

        pauseResumeButton = new JButton();
        stopButton = new JButton();
        pauseResumeButton.setEnabled(false);
        stopButton.setEnabled(false);

        statusLabel = new JLabel("Stopped");

        statusPane.add(statusLabel);

        timeControlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 2, 2));
        timeControlPanel.add(pauseResumeButton);
        timeControlPanel.add(stopButton);

        contentPane.add(timePane, BorderLayout.CENTER);
        contentPane.add(timeControlPanel, BorderLayout.SOUTH);
        contentPane.add(statusPane, BorderLayout.NORTH);

        setContentPane(contentPane);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setAlwaysOnTop(true);
        setUndecorated(true);
        setResizable(false);
        pack();
        // place center of the screen
        final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((screenSize.width / 2) - getSize().width, (screenSize.height / 2) - getSize().height);
    }

    public void updateIcons() {
        pauseIcon = new ImageIcon(Utils.getIconImage(Properties.PAUSE_ICON_PATH));
        resumeIcon = new ImageIcon(Utils.getIconImage(Properties.RESUME_ICON_PATH));
        stopIcon = new ImageIcon(Utils.getIconImage(Properties.STOP_ICON_PATH));
        pauseResumeButton.setIcon(PomoTimer.isRunning() ? pauseIcon : resumeIcon);
        stopButton.setIcon(stopIcon);
    }

    public void timerStarted() {
        pauseResumeButton.setEnabled(true);
        stopButton.setEnabled(true);
        pauseResumeButton.setIcon(pauseIcon);
    }

    public void timerStopped() {
        pauseResumeButton.setEnabled(false);
        stopButton.setEnabled(false);
        setStatus("Stopped");
        setTime(0, 0);
        updateIcons();
    }

    public void timerPaused() {
        pauseResumeButton.setIcon(resumeIcon);
    }

    public void timerResumed() {
        pauseResumeButton.setIcon(pauseIcon);
    }
}
