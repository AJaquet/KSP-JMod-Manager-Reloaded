package de.notepass.KSPModManager.test;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

/**
 * Created with IntelliJ IDEA.
 * Creator: Kim Hayo
 * Date: 06.12.13
 * Time: 08:52
 * As part of the project KSP-JMod-Manager
 * As part of the package de.notepass.KSPModManager.test
 */
public class test extends Application {
    public static void main(String [] args) {
        launch(args);
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
