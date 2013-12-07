/*
 This File will from now on only contain the Stuff for the Swing/AWT-GUI
 General config parts can be found in the de.notepass.general.internalConfig.InternalConfigDummy
 Class.
 If you have any Questions about this, saphirian is your guy
 */
package de.notepass.KSPModManager;

public class SwingConfig
{
	public static String PLACEHOLDER_VERSION = "%version%";
	public static String PLACEHOLDER_FLAG = "%flag%";
	
	public static String TAB_MAIN = "Modliste";
	public static String TAB_MANAGE = "Modliste verwalten";
	public static String TAB_SAVEGAMEMANAGER = "Savegames verwalten";
	public static String TAB_PROFILES = "Profile";
	public static String TAB_CONFIG = "Konfiguration";
	public static String TAB_INFO = "Informationen";
	
	public static String TOOLTIP_TAB_MAIN = "Liste der bekanntenn Mods mit zusaetzlichen Informationen";
	public static String TOOLTIP_TAB_MANAGE = "Hinzufuegen und Entfernen von Mods";
	public static String TOOLTIP_TAB_SAVEGAMEMANAGER = "Ex- und Importieren von Savegames";
	public static String TOOLTIP_TAB_PROFILES = "Konfigurationsprofil wechseln";
	public static String TOOLTIP_TAB_CONFIG = "Konfiguration des KSP Mod-Managers";
	public static String TOOLTIP_TAB_INFO = "Weitere Informationen ueber den KSP Mod-Manager";
	public static String TOOLTIP_BTN_RELOAD = "Laedt die Mod-Liste neu";
	public static String TOOLTIP_BTN_INSTALL = "Installiert den ausgewaehlten Mod";
	public static String TOOLTIP_BTN_REMOVE = "Entfernt den ausgewaehlten Mod";
	
	public static String LBL_LISTMODS = "Mods";
	public static String LBL_LISTMODIFIEDDLLS = "DLL-Dateien";
	public static String LBL_LISTMODIFIEDPARTS = "Teile";
	public static String LBL_MODVERSION = "Mod-Version: " + PLACEHOLDER_VERSION;
	public static String LBL_MODVERSION_DEFAULT = "0.0.N/A";
	public static String LBL_KSPVERSION = "KSP-Version: " + PLACEHOLDER_VERSION;
	public static String LBL_KSPVERSION_DEFAULT = "0.0.N/A";
	public static String LBL_INSTALLED = "Installiert: " + PLACEHOLDER_FLAG;
	public static String LBL_INSTALLED_DEFAULT = "N/A";
	
	public static String BTN_INSTALL = "Installieren";
	public static String BTN_REMOVE = "Deinstallieren";
	
	public static String ERROR_TEXTFILE = "ERROR: Could not read text file.";
	
	public static String CONSOLE_PREFIX = "[KSPModManager] ";
}