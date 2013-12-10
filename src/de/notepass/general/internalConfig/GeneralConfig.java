/*
 This is the Config File for the general-Stuff. It normally gets extended by the InternalConfig-Class of a specific project
 */


package de.notepass.general.internalConfig;

import de.notepass.general.util.Util;
import de.notepass.general.objects.gui.GroupBox;
import de.notepass.general.objects.gui.StatusBarItem;
import de.notepass.general.objects.gui.TitleBar;
import javafx.geometry.Insets;

//This thing here just brings up some Config. If you want to change one of these, have fun with recompiling Everything... (I don't think it's hard to do it, just Updating is not... nice)
public class GeneralConfig {
    @Deprecated
    //Internal Configs

    //General Config
    public static double doubleClickTimeout=1000;

    //Config for the FileSystem
    public static String rootFolder = "data";
    public static String configRoot = rootFolder+"/conf";
    public static String tempRoot = rootFolder+"/temp";

    //Config for the logger
    public static String logConfPath=rootFolder+"/conf/log_conf.xml";
    public static String logXMLlog_config="/log_config";
    public static String logXMLpath="/path";
    public static String logXMLlogDebug="/logDebug";
    public static String logXMLlogInfo="/logInfo";
    public static String logXMLlogWarn="/logWarn";
    public static String logXMLlogError="/logError";
    public static String logXMLlogDebugText="/logDebugText";
    public static String logXMLlogInfoText="/logInfoText";
    public static String logXMLlogWarnText="/logWarnText";
    public static String logXMLlogErrorText="/logErrorText";
    public static String logXMLdateTimeFormat="/dateTimeFormat";
    public static String logXMLdateTimePrefix="/dateTimePrefix";
    public static String logXMLdateTimeSuffix="/dateTimeSuffix";

    //Config for languages-files
    public static String langRoot=rootFolder+"/language";

    //Config for external libs
    public static String externalRoot=rootFolder+"/external";

    //Config for General gui Stuff
    public static String[] cssFiles = {GroupBox.cssFile, StatusBarItem.cssFile, Util.createLoadString("style/General.css"), TitleBar.cssFile};
    public static Insets guiDefaultPadding = new Insets(10,10,10,10);
    public static double guiDefaultVGap = 10;
    public static double guiDefaultHGap = 10;
    public static double guiDefaultSpacing = 5;

    //Config for GroupBoxes
    public static double groupBoxDefaultVGap = 5;
    public static double groupBoxDefaultHGap = 5;
    public static Insets groupBoxDefaultPadding = new Insets(5,5,5,5);
}
