package de.notepass.KSPModManager.test;

import de.notepass.KSPModManager.gui.FxGui;
import de.notepass.KSPModManager.objects.Mod;
import de.notepass.general.logger.Log;
import de.notepass.general.util.Util;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.File;
import java.io.IOException;

public class test {
    public static void main(String [] args) {
        FxGui.main(args);

        Mod mod = new Mod("Mod Name","Mod version");
        mod.getDllFiles().add("DLL 1");
        mod.getDllFiles().add("DLL 2");
        mod.getDllFiles().add("DLL 3");

        mod.getPartFiles().add("Part 1");
        mod.getPartFiles().add("Part 2");
        mod.getPartFiles().add("Part 3");
        mod.getPartFiles().add("Part 4");
        try {
            mod.saveToFile();
        } catch (ParserConfigurationException e) {
            //Log.logError(e);
            //Util.showError(e);
            e.printStackTrace();
        } catch (TransformerException e) {
            //Log.logError(e);
            //Util.showError(e);
            e.printStackTrace();
        }
    }

    public void start(Stage primaryStage) {
        TabPane pane = new TabPane();
        Tab tab = new Tab("TAB 1");
        tab.setClosable(false);

        GridPane pane1 = new GridPane();
        pane1.add(new Button("BUT 1"),0,0);
        pane1.add(new Button("BUT 2"),0,1);
        pane1.add(new Button("BUT 3"),1,0);

        tab.setContent(pane1);
        pane.getTabs().addAll(tab);

        Scene scene = new Scene(pane,500,500);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
