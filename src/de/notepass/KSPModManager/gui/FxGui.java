package de.notepass.KSPModManager.gui;
/*
In this class, the FX-Gui will be build up
 */

import de.notepass.KSPModManager.InternalConfig;
import de.notepass.KSPModManager.objects.Mod;
import de.notepass.general.logger.Log;
import de.notepass.general.objects.gui.GroupBox;
import de.notepass.general.objects.gui.SizeGroup;
import de.notepass.general.objects.gui.TitlePos;
import de.notepass.general.util.Util;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class FxGui extends Application {

    //Declaration of important variables and GUI Elements
    protected ArrayList<Mod> aviableMods = new ArrayList<Mod>();
    GridPane mainLayoutModlist;
    GridPane mainLayoutAddMod;
    TabPane layoutTab;
    Tab tabModlist;
    Tab tabAddMod;
    Scene mainScene;
    GroupBox gb_aviabledMods;
    ListView<String> lb_aviableMods;
    GroupBox gb_modInformation;
    Button bt_install;
    Button bt_uninstall;
    TreeItem<String> ti_modInfoRoot;
    TreeItem<String> ti_parts;
    TreeItem<String> ti_dlls;
    TreeItem<String> ti_info;
    TreeView<String> tv_modInfo;
    //General Information
    int focusedTab = 0;

    private static String translate(String node) {
        //Translate Function
        return Util.translate(node);
    }

    public static void main(String [] args) {
        //Method that is called on the FX launch
        Log.logInfo(translate("infoStartSession"));
        //Check the folder structure
        generateFolderStructure();
        //Intiate the GUI
        Log.logDebug("Initiating GUI (1/3)");  //TODO: TRANSLATE
        launch(args);
    }

    public void start(final Stage primaryStage) {
        //Method that Initialises the GUI

        Log.logDebug("Initiating GUI (2/3)");  //TODO: TRANSLATE
        try {
            aviableMods = getHDDMods();
        } catch (Exception e) {
            Log.logWarning("MOD FILE DAMAGED");   //TODO: Translate
        }

        build(primaryStage);

        //Automatic reload the List of mods from the HDD
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(Double.valueOf(Util.readConfig("reloadGuiTime"))),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (Util.readConfig("reloadGui").toLowerCase().equals("true")) {
                        Log.logDebug("GUI Reload"); //TODO: Translate
                        aviableMods = getHDDMods();
                        focusedTab = layoutTab.getSelectionModel().getSelectedIndex();
                        build(primaryStage);
                        layoutTab.getSelectionModel().select(focusedTab);
                    }
                } catch (Exception e) {
                    Log.logWarning("MOD FILE DAMAGED");   //TODO: Translate
                }
            }
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void build(Stage primaryStage) {
        //Method that builds the GUI

        /*====================================================*/


        //Building up a standard FX-Gui
        /*
        For those of you having no clue about FX-Guis, it's normally build up like this:

        There are these Parts:
        1. Stage (This is the "Window". So window settings are going here [Like JFrame in Swing])
        2. Scene(s) (This configures, which Content is shown and which size the Window has)
        3. Pane(s) (The align the content in a special way)
        4. Content(s) (The main Content like Buttons, Labels, etc)

        It's linked like this:
        Content is Linked to the Panes
        The Panes are Linked to the Scenes (At this Point you can also set the Window size)
        The Scenes are linked to the Stage

         */


        /* ========================== LAYOUT ==========================*/

        //This is good for adding the MenuBar and a StatusBar (It has a Center/Left/Right/Top/Bottom Content)
        final BorderPane rawLayout = new BorderPane();

        //These are the Panes for the Tab-Contents. Only in these Stuff like Buttons etc. are Stored
        mainLayoutModlist = new GridPane();
        mainLayoutModlist.setHgap(InternalConfig.GROUPBOX_DEFAULT_HGAP);
        mainLayoutModlist.setVgap(InternalConfig.GROUPBOX_DEFAULT_VGAP);
        mainLayoutModlist.setPadding(InternalConfig.GUI_DEFAULT_PADDING);

        mainLayoutAddMod = new GridPane();
        mainLayoutAddMod.setHgap(InternalConfig.GUI_DEFAULT_HGAP);
        mainLayoutAddMod.setVgap(InternalConfig.GUI_DEFAULT_VGAP);
        mainLayoutAddMod.setPadding(InternalConfig.GUI_DEFAULT_PADDING);

        //This is the Tab-Pane. It delivers the Tabs
        layoutTab = new TabPane();
        layoutTab.getStylesheets().addAll(InternalConfig.CSS_FILES);
        layoutTab.getStyleClass().addAll("GeneralPane"); //TODO: TRANSLATE
        //Tabs:
        //Modlist
        tabModlist = new Tab("Modliste"); //TODO: TRANSLATE
        tabModlist.setContent(mainLayoutModlist);
        tabModlist.setClosable(false);
        //Add Mod
        tabAddMod = new Tab("Add Mod"); //TODO: TRANSLATE
        tabAddMod.setContent(mainLayoutAddMod);
        tabAddMod.setClosable(false);
        //Add all tabs to the tab pane
        layoutTab.getTabs().addAll(tabModlist,tabAddMod);

        //The main-Scene for the Gui
        mainScene = new Scene(rawLayout,700,550);

        //Add everything to each other as needed
        rawLayout.setCenter(layoutTab);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);
        /* ====================================================*/


        /*========================== ADD TABS ==========================*/

        buildTabModlist();
        buildTabAddMod();

        /*====================================================*/



        /*========================== ACTION LISTENER ==========================*/
        //If you close the Window this Stuff should happen
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Log.logInfo(translate("infoStopSession"));
            }
        });
        /*====================================================*/


        Log.logDebug("Initiating GUI (3/3)");  //TODO: TRANSLATE

        primaryStage.show();
        primaryStage.centerOnScreen();

        //Set the dimension/position of elements
        gb_aviabledMods.setPrefWidth(primaryStage.getWidth() / 2);
        gb_modInformation.setPrefWidth(primaryStage.getWidth()/2);

        gb_aviabledMods.contentPane.setMinWidth(gb_aviabledMods.getWidth()-gb_aviabledMods.getPadding().getRight()-gb_aviabledMods.getPadding().getLeft());
        gb_aviabledMods.contentPane.setMinHeight(gb_aviabledMods.getHeight() - gb_aviabledMods.getPadding().getBottom() - gb_aviabledMods.getPadding().getTop());
        gb_aviabledMods.contentPane.setAlignment(Pos.CENTER);

        gb_modInformation.contentPane.setMinWidth(gb_modInformation.getWidth()-gb_modInformation.getPadding().getRight()-gb_modInformation.getPadding().getLeft());
        gb_modInformation.contentPane.setMinHeight(gb_modInformation.getWidth()-gb_modInformation.getPadding().getTop()-gb_modInformation.getPadding().getBottom());
        gb_modInformation.contentPane.setAlignment(Pos.CENTER);

        bt_install.setPrefWidth(tv_modInfo.getWidth());
        bt_uninstall.setPrefWidth(tv_modInfo.getWidth());

        lb_aviableMods.requestFocus();
        lb_aviableMods.getSelectionModel().select(0);

    }

    private ArrayList<Mod> getHDDMods() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        ArrayList<Mod> worker = new ArrayList<Mod>();
        String [] hddMods = Util.listFiles(InternalConfig.MOD_CONFIG_ROOT_FOLDER);
        for (String hddMod : hddMods) {
            Mod m = Mod.loadFromFile(hddMod.replace(".xml", ""));   //TODO: Add to InternalConfig
            worker.add(m);
        }
        return worker;
    }



    private void buildTabModlist() {
        //Clear the Content Pane
        mainLayoutModlist.getChildren().clear();

        //Get all aviable mods
        try {
            aviableMods = getHDDMods();
        } catch (ParserConfigurationException e) {
            Log.logError(e);
            Util.showError(e);
        } catch (SAXException e) {
            Log.logError(e);
            Util.showError(e);
        } catch (XPathExpressionException e) {
            Log.logError(e);
            Util.showError(e);
        } catch (IOException e) {
            Log.logError(e);
            Util.showError(e);
        }

         /*========================== CONTENT ==========================*/
        //Definition of elements

        /* AVIABLE MODS */
        //The GroupBox for the Aviable Mods
        gb_aviabledMods = new GroupBox("Installed Mods", TitlePos.POS_CENTER); //TODO: TRANSLATE
        gb_aviabledMods.setPadding(InternalConfig.GROUPBOX_DEFAULT_PADDING);

        //The ListBox for the aviable Mods
        lb_aviableMods = new ListView<String>();
        lb_aviableMods.setPrefWidth(235);
        //This is the part, which is in charge that the UUID which is originality saved in the list, is translated into the name of the mod
        lb_aviableMods.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {
            @Override
            public ListCell<String> call(ListView<String> stringListView) {
                return new txt_getModNameFromUuid();
            }

            class txt_getModNameFromUuid extends ListCell<String> {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item != null) {
                        UUID ident = UUID.fromString(item);
                        for (Mod aviableMod : aviableMods) {
                            if (aviableMod.getIdent().equals(ident)) {
                                setText(aviableMod.getName());
                            }
                        }
                    }
                }
            }
        });

        //TODO: Read Database of Mods
        //The Items for the ListBox
        final ObservableList<String> lb_aviableModsItems = FXCollections.observableArrayList();
        //Write all mod names from the ArrayList to the ObservableList
        for (Mod aviableMod : aviableMods) {
            lb_aviableModsItems.addAll(aviableMod.getIdent().toString());
        }
        lb_aviableMods.setItems(lb_aviableModsItems);
        gb_aviabledMods.add(lb_aviableMods, 0, 0);




        /* MOD INFORMATION */
        //GroupBox for the ModInformation
        gb_modInformation = new GroupBox("Mod Information",TitlePos.POS_CENTER);     //TODO: TRANSLATE
        gb_modInformation.setPadding(InternalConfig.GROUPBOX_DEFAULT_PADDING);
        gb_modInformation.contentPane.setHgap(InternalConfig.GROUPBOX_DEFAULT_HGAP);
        gb_modInformation.contentPane.setVgap(InternalConfig.GROUPBOX_DEFAULT_VGAP);

        //Mod-Information
        ti_modInfoRoot = new TreeItem<String>("Mod"); //Todo: Translate
        ti_modInfoRoot.setExpanded(true);
        ti_parts = new TreeItem<String>("Parts"); //TODO: Translate
        ti_parts.setExpanded(true);
        ti_dlls = new TreeItem<String>("DLLs"); //TODO: Translate
        ti_dlls.setExpanded(true);
        ti_info = new TreeItem<String>("Info"); //TODO: Translate
        ti_info.setExpanded(true);
        ti_modInfoRoot.getChildren().addAll(ti_dlls, ti_parts, ti_info);
        tv_modInfo = new TreeView<String>(ti_modInfoRoot);
        tv_modInfo.setPrefHeight(350);
        gb_modInformation.add(tv_modInfo,0,0);
        SizeGroup sg_modInfo = new SizeGroup(false,true);
        sg_modInfo.addAll(tv_modInfo,bt_uninstall,bt_install);



        /* Install/Uninstall */
        bt_install = new Button("Install");  //TODO: TRANSLATE
        //bt_install.setPrefWidth(lb_dllFiles.getPrefWidth()+InternalConfig.groupBoxDefaultVGap+lb_partFiles.getPrefWidth());
        bt_uninstall = new Button("Uninstall");     //TODO: TRANSLATE
        //bt_uninstall.setPrefWidth(lb_dllFiles.getPrefWidth()+InternalConfig.groupBoxDefaultVGap+lb_partFiles.getPrefWidth());
        gb_modInformation.contentPane.add(bt_install, 0, 1);
        gb_modInformation.contentPane.add(bt_uninstall, 0, 2);


        //Adding Content to the Panes
        mainLayoutModlist.add(gb_aviabledMods,0,0);
        mainLayoutModlist.add(gb_modInformation,1,0);
        /*====================================================*/





        /*========================== ACTION LISTENER ==========================*/

        lb_aviableMods.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                //Get the selected Items
                ObservableList<String> selectedItems = lb_aviableMods.getSelectionModel().getSelectedItems();
                //Loop through each element
                for (String selectedItem : selectedItems) {
                    System.out.println(selectedItem);
                    //Loop through each element of all aviable mods
                    for (Mod aviableMod : aviableMods) {
                        //If the UUID of a certain mod is the same than the one of the selected mod
                        if (aviableMod.getIdent().toString().equals(selectedItem)) {
                            //Fill the information lists with content

                            //Rename the root-node
                            ti_modInfoRoot.setValue(aviableMod.getName());

                            //Add all DLL-Files to the TreeView
                            ti_dlls.getChildren().clear();
                            for (String dllFile : aviableMod.getDllFiles()) {
                                ti_dlls.getChildren().addAll(new TreeItem<String>(dllFile));
                            }

                            //Add all Part-Files t the TreeView
                            ti_parts.getChildren().clear();
                            for (String partFile : aviableMod.getPartFiles()) {
                                ti_parts.getChildren().addAll(new TreeItem<String>(partFile));
                            }

                            //Add Info to the TreeView
                            ti_info.getChildren().clear();
                            ti_info.getChildren().addAll(new TreeItem<String>("Mod Version: "+aviableMod.getVersion())); //Todo: Translate

                        }
                    }
                }
            }
        });

        bt_install.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO: Write this function
            }
        });

        bt_uninstall.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //TODO: Write this function
            }
        });

        /*====================================================*/
    }





    private void buildTabAddMod() {
        //Clear the main panel
        mainLayoutAddMod.getChildren().clear();

        /*========================== CONTENT ==========================*/
        final GroupBox gb_dllFilesAddMod = new GroupBox("DLL-Files",TitlePos.POS_CENTER); //TODO: Translate
        gb_dllFilesAddMod.setPadding(InternalConfig.GROUPBOX_DEFAULT_PADDING);
        final GroupBox gb_partFilesAddMod = new GroupBox("Part-Files",TitlePos.POS_CENTER); //TODO: Translate
        gb_partFilesAddMod.setPadding(InternalConfig.GROUPBOX_DEFAULT_PADDING);
        final GroupBox gb_modConfigAddMod = new GroupBox("Mod-Config",TitlePos.POS_CENTER); //TODO: Translate
        gb_modConfigAddMod.setPadding(InternalConfig.GROUPBOX_DEFAULT_PADDING);

        mainLayoutAddMod.add(gb_dllFilesAddMod,0,0);
        mainLayoutAddMod.add(gb_partFilesAddMod,1,0);
        mainLayoutAddMod.add(gb_modConfigAddMod,0,1,2,1);

        //TODO: Delte test statement
        Button but = new Button("Reload other tab");
        gb_dllFilesAddMod.add(but,0,0);
        gb_partFilesAddMod.add(new Button("aswfwewaefgrg"),0,0);

        /*====================================================*/





        /*========================== ACTION LISTENER ==========================*/

        but.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                buildTabModlist();
            }
        });

        /*====================================================*/
    }


    private static void generateFolderStructure() {
        if (!Util.exist(InternalConfig.ROOT_FOLDER)) {
            Log.logDebug("Root folder not Found. Generating it");  //TODO: translate
            new File(InternalConfig.ROOT_FOLDER).mkdirs();
        }

        if (!Util.exist(InternalConfig.MOD_ROOT_FOLDER)) {
            Log.logDebug("Mod root folder not Found. Generating it");  //TODO: translate
            new File(InternalConfig.MOD_ROOT_FOLDER).mkdirs();
        }

        if (!Util.exist(InternalConfig.MOD_CONFIG_ROOT_FOLDER)) {
            Log.logDebug("Mod root config folder not Found. Generating it");  //TODO: translate
            new File(InternalConfig.MOD_CONFIG_ROOT_FOLDER).mkdirs();
        }
    }
}
