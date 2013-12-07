package de.notepass.KSPModManager.gui;
/*
In this class, the FX-Gui will be build up
 */

import de.notepass.general.internalConfig.InternalConfigDummy;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

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
        //This is good for adding the MenuBar and a StatusBar (It has a Center/Left/Right/Top/Bottom Content)
        final BorderPane rawLayout = new BorderPane();

        //These are the Panes for the Tab-Contents. Only in these Stuff like Buttons etc. are Stored
        //All other Content Panes will have this one as "Parent"
        final GridPane mainLayoutParent = new GridPane();
        mainLayoutParent.setPadding(InternalConfigDummy.guiDefaultPadding);
        mainLayoutParent.setHgap(InternalConfigDummy.guiDefaultHGap);
        mainLayoutParent.setVgap(InternalConfigDummy.guiDefaultVGap);
        final GridPane mainLayoutModlist = mainLayoutParent;

        //This is the Tab-Pane. It delivers the Tabs
        final TabPane layoutTab = new TabPane();
        //Tabs:
        final Tab tabModlist = new Tab("Modliste");
        tabModlist.setContent(mainLayoutModlist);
        layoutTab.getTabs().addAll(tabModlist);

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
        final Button but1 = new Button("Button Text");


        //Adding Content to the Panes
        mainLayoutModlist.add(new Button("Test"),0,0);

        /*====================================================*/




        //Show the window
        primaryStage.show();

    }
}
