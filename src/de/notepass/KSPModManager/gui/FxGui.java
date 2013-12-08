package de.notepass.KSPModManager.gui;
/*
In this class, the FX-Gui will be build up
 */

import de.notepass.KSPModManager.InternalConfig;
import de.notepass.general.objects.gui.GroupBox;
import de.notepass.general.objects.gui.TitlePos;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;

public class FxGui extends Application {
    public static void main(String [] args) {
        launch(args);
    }

    public void start(Stage primaryStage) {
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
        primaryStage.setResizable(false);

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

        //This is the ScrollPane for the main Content Paine, so it can be Scrolled
        final ScrollPane layoutScroll = new ScrollPane();

        //The main-Scene for the Gui
        Scene mainScene = new Scene(rawLayout,700,550);

        //Add everything to each other as needed
        rawLayout.setCenter(layoutTab);
        //TODO: Add content to ScrollPane
        primaryStage.setScene(mainScene);
        /* ====================================================*/






        /*========================== CONTENT ==========================*/
        //Definition of elements

        /* AVIABLE MODS */
        //The GroupBox for the Aviable Mods
        final GroupBox gb_installedMods = new GroupBox("Installed Mods", TitlePos.POS_CENTER);
        gb_installedMods.setPadding(InternalConfig.groupBoxDefaultPadding);
        //The ListBox for the aviable Mods
        final ListView<String> lb_installedMods = new ListView<String>();
        //TODO: Read Database of Mods
        //The Items for the ListBox
        ObservableList<String> lb_installedModsItems = FXCollections.observableArrayList();
        for (int i=0;i<=50;i++) {
            lb_installedModsItems.addAll("Test"+i);
        }
        lb_installedMods.setItems(lb_installedModsItems);
        gb_installedMods.add(lb_installedMods,0,0);

        /* MOD INFORMATION */
        //GroupBox for the ModInformation
        final GroupBox gb_modInformation = new GroupBox("Mod Information",TitlePos.POS_CENTER);
        gb_modInformation.setPadding(InternalConfig.groupBoxDefaultPadding);
        gb_modInformation.contentPane.setHgap(InternalConfig.groupBoxDefaultHGap);
        gb_modInformation.contentPane.setVgap(InternalConfig.groupBoxDefaultVGap);
        //ListBox for the DLL-Files which are used
        final ListView<String> lb_dllFiles = new ListView<String>();
        lb_dllFiles.setPrefWidth(10);
        lb_dllFiles.setPrefHeight(10);
        //Items for the DLL-Files ListBox
        //TODO: Read Data from Database
        final ObservableList<String> lb_dllFilesItems = FXCollections.observableArrayList();
        lb_dllFiles.setItems(lb_dllFilesItems);
        //ListBox for the used Parts
        ListView<String> lb_partFiles = new ListView<String>();
        lb_partFiles.setPrefWidth(10);
        lb_partFiles.setPrefHeight(10);
        //Items for the used Parts
        //TODO: Read data from Database
        ObservableList<String> lb_partFilesItems = FXCollections.observableArrayList();
        lb_partFiles.setItems(lb_dllFilesItems);
        //Add the Mod-Information-Interface-Parts (new Record for longest word!) to the GroupBox for them
        gb_modInformation.add(lb_dllFiles,0,0);
        gb_modInformation.add(lb_partFiles,1,0);


        //Adding Content to the Panes
        mainLayoutModlist.add(gb_installedMods,0,0);
        mainLayoutModlist.add(gb_modInformation,1,0);
        /*====================================================*/


        /*========================== ACTION LISTENER ==========================*/
        lb_installedMods.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String s2) {
                ObservableList<String> selectedItems = lb_installedMods.getSelectionModel().getSelectedItems();
                for (int i=0; i<selectedItems.size();i++) {
                    System.out.println(selectedItems.get(i));
                }
            }
        });

        /*====================================================*/


        //Show the window
        primaryStage.show();

    }
}
