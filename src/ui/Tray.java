package ui;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.SystemTray;
import java.awt.TrayIcon;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import cww.CWW;
import util.Output;

public class Tray {

  private static int renderedForWeek = -1;

  private Tray() {
  }

  private static TrayIcon trayIcon;

  public static void initRedrawTimer(Color fontColor) {
    Calendar today = Calendar.getInstance();
    today.set(Calendar.HOUR_OF_DAY, 0);
    today.set(Calendar.MINUTE, 0);
    today.set(Calendar.SECOND, 0);

    TimerTask task = new TimerTask() {

      public void run() {
        int weekNumber = Calendar.getInstance().get(Calendar.WEEK_OF_YEAR);
        if (renderedForWeek != weekNumber) {
          renderedForWeek = weekNumber;
          Image trayIconImage = IconRenderer.getWeekIcon(weekNumber, fontColor);
          if (trayIcon == null) {
            trayIcon = new TrayIcon(
                trayIconImage.getScaledInstance(new TrayIcon(trayIconImage).getSize().width, -1, Image.SCALE_SMOOTH));
            SwingUtilities.invokeLater(Tray::createAndShowTray);
          } else {
            trayIcon.setImage(trayIconImage);
          }
        }
      }
    };

    Timer timer = new Timer();
    timer.schedule(task, today.getTime(), TimeUnit.MILLISECONDS.convert(1, TimeUnit.MINUTES));
  }

  public static void createAndShowTray() {
    String sysTraySupportMsg = "SystemTray is not supported";
    if (!SystemTray.isSupported()) {
      JOptionPane.showMessageDialog(null, sysTraySupportMsg, "Error", JOptionPane.ERROR_MESSAGE);
      Output.outputError(sysTraySupportMsg);
      return;
    }
    PopupMenu popup = new PopupMenu();
    final SystemTray tray = SystemTray.getSystemTray();
    trayIcon.setToolTip(CWW.APP_NAME);
    MenuItem exitItem = new MenuItem("Exit");
    popup.add(exitItem);

    trayIcon.setPopupMenu(popup);
    trayIcon.setImageAutoSize(true);

    try {
      tray.add(trayIcon);
    } catch (AWTException e) {
      Output.outputError("TrayIcon could not be added.");
      return;
    }

    exitItem.addActionListener(e -> {
      tray.remove(trayIcon);
      System.exit(0);
    });
  }
}
