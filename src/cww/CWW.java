package cww;


import static ui.Tray.initRedrawTimer;

import java.awt.Color;
import java.lang.reflect.Field;

import javax.swing.UIManager;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import it.sauronsoftware.junique.AlreadyLockedException;
import it.sauronsoftware.junique.JUnique;
import util.Output;

public class CWW {

  public static final String APP_NAME = "Calendar Week Widget";
  public static final String LOOK_AND_FEEL = "com.sun.java.swing.plaf.windows.WindowsLookAndFeel";
  public static final String SWING_BOLD_METAL = "swing.boldMetal";

  public static void main(String[] args) {

    Output.output(APP_NAME + " starting");
    Color fontColor = getColorFromCLI(args);
    preventMultipleInstances();
    try {
      UIManager.setLookAndFeel(LOOK_AND_FEEL);
      UIManager.put(SWING_BOLD_METAL, Boolean.FALSE);
      initRedrawTimer(fontColor);
    } catch (Exception e) {
      Output.outputException(e);
    }
  }

  private static void preventMultipleInstances() {
    boolean alreadyRunning;
    try {
      JUnique.acquireLock(APP_NAME, message -> message);
      alreadyRunning = false;
    } catch (AlreadyLockedException e) {
      alreadyRunning = true;
    }
    if (alreadyRunning) {
      String tooLate = "i guess i am too late";
      JUnique.sendMessage(APP_NAME, tooLate);
      Output.output(APP_NAME + " can't start:" + tooLate);
      System.exit(0);
    }
  }


  private static Color getColorFromCLI(String[] args) {
    Options options = new Options();

    Option color = new Option("c", "color", true, "font color");
    color.setRequired(false);
    options.addOption(color);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();

    try {
      CommandLine cmd = parser.parse(options, args);
      String colorValue = cmd.getOptionValue("color");
      Field field = Class.forName("java.awt.Color").getField(colorValue.toLowerCase());
      return (Color) field.get(null);
    } catch (ParseException e) {
      Output.outputException(e);
      formatter.printHelp("cww", options);
      System.exit(1);
    } catch (Exception e) {
      Output.outputError("Cannot load specific color. Defaulting to white.");
    }
    return Color.WHITE;
  }


}
