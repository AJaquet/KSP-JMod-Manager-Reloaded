package de.notepass.KSPModManager;

import de.notepass.general.internalConfig.InternalConfigDummy;

public class InternalConfig extends InternalConfigDummy {
    //final public static String[] cssFiles = {Util.createLoadString("style/GroupBox_Fixed.css"), StatusBarItem.cssFile, Util.createLoadString("style/General.css"), TitleBar.cssFile};
    //final public static Insets groupBoxDefaultPadding = new Insets(10,10,10,10);
    final public static String MOD_ROOT_FOLDER = ROOT_FOLDER+"/mods";
    final public static String MOD_CONFIG_ROOT_FOLDER = MOD_ROOT_FOLDER +"/config";

    //Config for reading the Mods
    final public static String MOD_XPATH_ROOT = "/mod";
    final public static String MOD_XPATH_DLL_ROOT = MOD_XPATH_ROOT +"/dllFiles";
    final public static String MOD_XPATH_DLL_REPETITION = MOD_XPATH_DLL_ROOT +"/dll_";
    final public static String MOD_XPATH_PART_ROOT = MOD_XPATH_ROOT +"/partFiles";
    final public static String MOD_XPATH_PART_REPETITION = MOD_XPATH_PART_ROOT +"/part_";
    final public static String MOD_XPATH_NAME = MOD_XPATH_ROOT +"/@display";
    final public static String MOD_XPATH_VERSION = MOD_XPATH_ROOT +"/@version";
}
