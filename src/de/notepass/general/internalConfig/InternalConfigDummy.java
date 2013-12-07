/*
 This is a Dummy-File fpr the internalConfig. It's normally specialised for every project. But this File is the "master" File
 containing EVERY configuration Part.
 */


package de.notepass.general.internalConfig;

import de.notepass.general.objects.gui.StatusBar;
import de.notepass.general.util.Util;
import de.notepass.general.objects.gui.GroupBox;
import de.notepass.general.objects.gui.StatusBarItem;
import de.notepass.general.objects.gui.TitleBar;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

//This thing here just brings up some Config. If you want to change one of these, have fun with recompiling Everything... (I don't think it's hard to do it, just Updating is not... nice)
public class InternalConfigDummy {
    @Deprecated
    //Internal Configs

    //General Config
    public static double doubleClickTimeout=1000;

    //The internal Version. Important for the modules!
    public static String version = "0.1";

    //The showed version. Just some Makeup for the end-User
    public static String showedVersion="Closed Alpha 1 (Internal Version: "+version+")";

    //Kind of Release
    //TODO: CHANGE TYPE OF RELEASE!
    public static String versionTypeUnreleased = "Unreleased Build";
    public static String versionTypeDev = "Developer Build";
    public static String versionTypeTest = "Test Build";
    public static String versionTypeRelease = "Release Build";

    public static String versionType = "Unreleased Build";

    //Config for the FileSystem
    public static String rootFolder = "data";
    public static String configRoot = rootFolder+"/conf";
    public static String tempRoot = rootFolder+"/temp";
    public static String templateRoot = rootFolder+"/template";
    public static String varRoot = rootFolder + "/variables";

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

    //Config for Variables
    public static String varXMLcontent="/variable/content/text()";
    public static String varInitalPattern = "%%im::";
    public static String varEndPattern = "%%";

    //Config for savefiles
    public static String saveRoot=rootFolder+"/savefiles";
    public static String invoiceSaveRoot=saveRoot+"/invoice";
    public static String customerSaveRoot=saveRoot+"/customer";
    public static String tableSaveRoot=saveRoot+"/table";

    //Config for archive
    public static String archiveRoot=rootFolder+"/archive";
    public static String invoiceArchiveRoot=archiveRoot+"/invoice";

    //Config for languages-files
    public static String langRoot=rootFolder+"/language";

    //Config for external libs
    public static String externalRoot=rootFolder+"/external";
    public static String modulesRoot=externalRoot+"/lib";
    public static String rendererRoot=externalRoot+"/lib";
    public static String guiRoot=externalRoot+"/lib";
    public static String externalConfigRoot=externalRoot+"/config";
    public static String externalTempRoot=externalRoot+"/temp";

    //Config for Installer
    public static String langNameXpath="/lang/@display";
    public static String langLocaleXpath="/lang/@code";
    public static String stdLangFile="en_GB.xml";

    //Config for the currency
    public static String currencyRoot = rootFolder+"/currency";
    public static String currencyDisplayXpath = "/currency/@display";
    public static String currencyNameXpath = "/currency/name";
    public static String currencySymbolXpath = "/currency/symbol";
    public static String currencyIsoXpath="/currency/ISO4217";

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

    //Standard GUI
    public static Scene buildStandardGui(final Stage primaryStage, final Pane contentPane, double width, double height) {
        @Deprecated
        //primaryStage.initStyle(StageStyle.UNDECORATED);
        VBox lowContentPane = new VBox();
        lowContentPane.getStylesheets().addAll(InternalConfigDummy.cssFiles);
        contentPane.getStylesheets().addAll(InternalConfigDummy.cssFiles);
        contentPane.getStyleClass().addAll("GeneralPane");
        //contentPane.setStyle("-fx-background-color: grey;");
        contentPane.setPadding(InternalConfigDummy.guiDefaultPadding);
        lowContentPane.getStyleClass().addAll("TitleBarPane","LowContentPane");
        final TitleBar tb = new TitleBar(primaryStage,true,true,true);
        final MenuBar mb = buildStandardMenuBar(primaryStage);
        //Resize the ContentPane on resize
        tb.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                Dimension contentPaneSize = new Dimension((int) primaryStage.getWidth(),(int) primaryStage.getHeight());
                tb.doubleCLickAction();
                if (!contentPaneSize.equals(new Dimension((int) primaryStage.getWidth(),(int) primaryStage.getHeight()))) {
                    contentPane.setMinWidth(primaryStage.getWidth()-2);
                    contentPane.setMaxWidth(primaryStage.getWidth() - 2);
                    contentPane.setMaxHeight(primaryStage.getHeight() - tb.getHeight() - mb.getHeight() - 2);
                    contentPane.setMinHeight(primaryStage.getHeight() - tb.getHeight() - mb.getHeight() - 2);
                }
            }
        });
        lowContentPane.getChildren().addAll(tb,mb,contentPane);
        Scene returnScene = new Scene(lowContentPane, width, height);
        return returnScene;
    }

    //Standard GUI Elements
    public static MenuBar buildStandardMenuBar(final Stage primaryStage, final StatusBarItem ... statusItems) {
        //Declaration
        MenuBar mb_std=new MenuBar();
        Menu m_file = new Menu(Util.translate("guiMainMenuBarFile"));
        StatusBar statusBar = new StatusBar();
        MenuItem mi_exit = new MenuItem(Util.translate("guiMainMenuItemExit"));
        mi_exit.setAccelerator(KeyCombination.keyCombination("Esc"));

        //Linking
        m_file.getItems().addAll(mi_exit);
        mb_std.getMenus().addAll(m_file);

        //Action Handler
        mi_exit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                primaryStage.close();
            }
        });

        return mb_std;
    }


}
