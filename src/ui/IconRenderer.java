package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.util.Calendar;

import util.Output;


public class IconRenderer {

  public static final int TRAY_ICON_IMG_SIZE = 256;
  public static final int TRAY_ICON_FONT_SIZE = 210;

  private IconRenderer() {
  }

  public static Image renderTrayIconForCurrentWeek(Color color) {
    return IconRenderer.getWeekIcon(Calendar.getInstance().get(Calendar.WEEK_OF_YEAR), color);
  }

  public static Image getWeekIcon(int weekNumber, Color fontColor) {
    Output.output("Rendering tray icon for week " + weekNumber);
    BufferedImage image = new BufferedImage(TRAY_ICON_IMG_SIZE, TRAY_ICON_IMG_SIZE, BufferedImage.TYPE_INT_ARGB);
    Font font = new Font("Monospaced", Font.BOLD, TRAY_ICON_FONT_SIZE);
    Graphics2D g = image.createGraphics();
    g.setFont(font);
    g.setColor(fontColor);
    String str = String.valueOf(weekNumber);
    FontMetrics metrics = g.getFontMetrics(font);
    int x = (TRAY_ICON_IMG_SIZE - metrics.stringWidth(str)) / 2;
    int y = ((TRAY_ICON_IMG_SIZE - metrics.getHeight()) / 2) + metrics.getAscent();
    g.drawString(str, x, y);
    g.dispose();
    return image;
  }
}
