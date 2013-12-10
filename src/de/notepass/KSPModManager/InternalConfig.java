package de.notepass.KSPModManager;

import de.notepass.general.internalConfig.GeneralConfig;
import de.notepass.general.objects.gui.StatusBarItem;
import de.notepass.general.objects.gui.TitleBar;
import de.notepass.general.util.Util;
import javafx.geometry.Insets;

public class InternalConfig extends GeneralConfig {
    public static String[] cssFiles = {Util.createLoadString("style/GroupBox_Fixed.css"), StatusBarItem.cssFile, Util.createLoadString("style/General.css"), TitleBar.cssFile};
    public static Insets groupBoxDefaultPadding = new Insets(10,10,10,10);
    public static String modRootFolder = rootFolder+"/mods";
    public static String modConfigRootFolder = modRootFolder+"/config";

    //Config for reading the Mods
    public static String modXpathRoot = "/mod";
    public static String modXpathDllRoot = modXpathRoot+"/dllFiles";
    public static String modXpathDllRepetition = modXpathDllRoot+"/dll_";
    public static String modXpathPartRoot = modXpathRoot+"/partFiles";
    public static String modXpathPartRepetition = modXpathPartRoot+"/part_";
    public static String modXpathName = modXpathRoot+"/@display";
    public static String modXpathVersion = modXpathRoot+"/@version";
}
