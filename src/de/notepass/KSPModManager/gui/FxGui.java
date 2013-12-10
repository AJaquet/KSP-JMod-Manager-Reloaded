package de.notepass.KSPModManager.gui;
/*
In this class, the FX-Gui will be build up
 */

import de.notepass.KSPModManager.InternalConfig;
import de.notepass.KSPModManager.objects.Mod;
import de.notepass.general.logger.Log;
import de.notepass.general.objects.gui.GroupBox;
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
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import javafx.util.Duration;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

public class FxGui extends Application {

    protected ArrayList<Mod> aviableMods = new ArrayList<Mod>();

    private static String translate(String node) {
        String worker = Util.translate(node);

        return worker;
    }

    public static void main(String [] args) {
        Log.logInfo(translate("infoStartSession"));
        Log.logDebug("Initiating GUI (1/3)");
        launch(args);
    }

    public void start(final Stage primaryStage) {
        Log.logDebug("Initiating GUI (2/3)");
        try {
            aviableMods = getHDDMods();
        } catch (Exception e) {
            Log.logWarning("TRANSLATE: MOD FILE DAMAGED");   //TODO: Translate
        }

        build(primaryStage);

        //Automatic reload the List of mods from the HDD
        /*
        Why no Timer you ask? The Answer is easy:
        A Timer runs in an extra thread. If I try to access JavaFx-Stuff from a timer, JavaFx starts killing me...
        (If youre working with JavaFx you can only use it in JavaFx threads)
         */

        Timeline t = new Timeline(new KeyFrame(Duration.seconds(5),new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    ArrayList<Mod> worker = getHDDMods();
                    if (!worker.equals(aviableMods)) {
                        System.out.println("NOK");
                        aviableMods = worker;
                        build(primaryStage);
                    }
                } catch (Exception e) {
                    Log.logWarning("TRANSLATE: MOD FILE DAMAGED");   //TODO: Translate
                }
            }
        }));
        t.setCycleCount(Timeline.INDEFINITE);
        t.play();
    }

    private void build(Stage primaryStage) {
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
        final GridPane mainLayoutModlist = new GridPane();
        mainLayoutModlist.setHgap(InternalConfig.groupBoxDefaultHGap);
        mainLayoutModlist.setVgap(InternalConfig.groupBoxDefaultVGap);
        mainLayoutModlist.setPadding(InternalConfig.guiDefaultPadding);
        final GridPane mainLayoutAddMod = new GridPane();
        mainLayoutAddMod.setHgap(InternalConfig.guiDefaultHGap);
        mainLayoutAddMod.setVgap(InternalConfig.guiDefaultVGap);
        mainLayoutAddMod.setPadding(InternalConfig.guiDefaultPadding);

        //This is the Tab-Pane. It delivers the Tabs
        final TabPane layoutTab = new TabPane();
        layoutTab.getStylesheets().addAll(InternalConfig.cssFiles);
        layoutTab.getStyleClass().addAll("GeneralPane");
        //Tabs:
        //Modlist
        final Tab tabModlist = new Tab("Modliste");
        tabModlist.setContent(mainLayoutModlist);
        tabModlist.setClosable(false);
        //Add Mod
        final Tab tabAddMod = new Tab("Add Mod");
        tabAddMod.setContent(mainLayoutAddMod);
        tabAddMod.setClosable(false);
        //Add all tabs to the tab pane
        layoutTab.getTabs().addAll(tabModlist,tabAddMod);

        //The main-Scene for the Gui
        Scene mainScene = new Scene(rawLayout,700,550);

        //Add everything to each other as needed
        rawLayout.setCenter(layoutTab);

        primaryStage.setScene(mainScene);
        primaryStage.setResizable(false);

        primaryStage.show();

        /* ====================================================*/


        /*========================== CONTENT ==========================*/
        //Definition of elements

        /* AVIABLE MODS */
        //The GroupBox for the Aviable Mods
        final GroupBox gb_aviabledMods = new GroupBox("Installed Mods", TitlePos.POS_CENTER);
        gb_aviabledMods.setPadding(InternalConfig.groupBoxDefaultPadding);

        //The ListBox for the aviable Mods
        final ListView<String> lb_aviableMods = new ListView<String>();
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
        final GroupBox gb_modInformation = new GroupBox("Mod Information",TitlePos.POS_CENTER);
        gb_modInformation.setPadding(InternalConfig.groupBoxDefaultPadding);
        gb_modInformation.contentPane.setHgap(InternalConfig.groupBoxDefaultHGap);
        gb_modInformation.contentPane.setVgap(InternalConfig.groupBoxDefaultVGap);

        //ListBox for the DLL-Files which are used
        final ListView<String> lb_dllFiles = new ListView<String>();
        lb_dllFiles.setPrefWidth(200);
        lb_dllFiles.setPrefHeight(150);

        //Items for the DLL-Files ListBox
        //TODO: Read Data from Database
        final ObservableList<String> lb_dllFilesItems = FXCollections.observableArrayList();
        lb_dllFiles.setItems(lb_dllFilesItems);
        //ListBox for the used Parts
        ListView<String> lb_partFiles = new ListView<String>();
        lb_partFiles.setPrefWidth(200);
        lb_partFiles.setPrefHeight(150);

        //Items for the used Parts
        //TODO: Read data from Database
        final ObservableList<String> lb_partFilesItems = FXCollections.observableArrayList();
        lb_partFiles.setItems(lb_partFilesItems);
        //Add the Mod-Information-Interface-Parts (new Record for longest word!) to the GroupBox for them
        gb_modInformation.add(lb_dllFiles,0,0);
        gb_modInformation.add(lb_partFiles,1,0);

        //Label to show the mod version
        final Label lbl_modVersion = new Label("Version: N/A");
        gb_modInformation.add(lbl_modVersion,0,3);


        /* Install/Uninstall */
        Button bt_install = new Button("Install");
        bt_install.setPrefWidth(lb_dllFiles.getPrefWidth()+InternalConfig.groupBoxDefaultVGap+lb_partFiles.getPrefWidth());
        Button bt_uninstall = new Button("Uninstall");
        bt_uninstall.setPrefWidth(lb_dllFiles.getPrefWidth()+InternalConfig.groupBoxDefaultVGap+lb_partFiles.getPrefWidth());
        gb_modInformation.contentPane.add(bt_install, 0, 1, 2, 1);
        gb_modInformation.contentPane.add(bt_uninstall,0,2,2,1);

        //TODO: Delete test Statements
        Button but_save = new Button("Save");
        gb_modInformation.add(but_save,0,4);


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
                            lb_dllFilesItems.clear();
                            lb_dllFilesItems.addAll(aviableMod.getDllFiles());

                            lb_partFilesItems.clear();
                            lb_partFilesItems.addAll(aviableMod.getPartFiles());
                        }
                    }
                }
            }
        });

        but_save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //Get the selected Items
                ObservableList<String> selectedItems = lb_aviableMods.getSelectionModel().getSelectedItems();
                //Loop through each element
                for (String selectedItem : selectedItems) {
                    System.out.println(selectedItem);
                    //Loop through each element of all aviable mods
                    for (Mod aviableMod : aviableMods) {
                        //If the UUID of a certain mod is the same than the one of the selected mod
                        if (aviableMod.getIdent().toString().equals(selectedItem)) {
                            try {
                                aviableMod.saveToFile();
                            } catch (ParserConfigurationException e) {
                                e.printStackTrace();
                            } catch (TransformerException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        });



        //If you close the Window this Stuff should happen
        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent windowEvent) {
                Log.logInfo(translate("infoStopSession"));
            }
        });
        /*====================================================*/
    }

    private ArrayList<Mod> getHDDMods() throws ParserConfigurationException, SAXException, XPathExpressionException, IOException {
        ArrayList<Mod> worker = new ArrayList<Mod>();
        String [] hddMods = Util.listFiles(InternalConfig.modConfigRootFolder);
        for (int i=0;i<hddMods.length;i++) {
            Mod m = Mod.loadFromFile(hddMods[i].replace(".xml",""));
            worker.add(m);
        }
        return worker;
    }
}
