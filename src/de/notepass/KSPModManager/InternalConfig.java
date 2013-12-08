package de.notepass.KSPModManager;

import de.notepass.general.internalConfig.InternalConfigDummy;
import de.notepass.general.objects.gui.GroupBox;
import de.notepass.general.objects.gui.StatusBarItem;
import de.notepass.general.objects.gui.TitleBar;
import de.notepass.general.util.Util;
import javafx.geometry.Insets;

/**
 * Created with IntelliJ IDEA.
 * Creator: Kim Hayo
 * Date: 08.12.13
 * Time: 00:23
 * As part of the project KSP-JMod-Manager-Reloaded
 * As part of the package de.notepass.KSPModManager
 */
public class InternalConfig extends InternalConfigDummy {
    public static String[] cssFiles = {Util.createLoadString("style/GroupBox_Fixed.css"), StatusBarItem.cssFile, Util.createLoadString("style/General.css"), TitleBar.cssFile};
    public static Insets groupBoxDefaultPadding = new Insets(10,10,10,10);
}
