package de.notepass.general.objects.gui;

import de.notepass.general.internalConfig.GeneralConfig;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Created with IntelliJ IDEA.
 * User: kim.hayo
 * Date: 27.11.13
 * Time: 16:11
 * To change this template use File | Settings | File Templates.
 */
public class OptionPane {
    public enum Response { NO, YES, CANCEL};
    private static Response buttonSelected = Response.CANCEL;
    static class Dialog extends Stage {
        public Dialog(String title, Stage primaryStage, Scene mainScene) {
            setTitle(title);
            initStyle(StageStyle.UTILITY);
            initModality(Modality.APPLICATION_MODAL);
            initOwner(primaryStage);
            setResizable(false);
            setScene(mainScene);
        }

        public void showDialog() {
            sizeToScene();
            centerOnScreen();
            showAndWait();
        }

        static class Message extends Text {
            public Message(String text) {
                super(text);
                setWrappingWidth(250);
            }
        }

        public static Response ShowConfirmDialog(Stage primaryStage, String message, String title) {
            VBox vb = new VBox();
            Scene scene = new Scene(vb);
            vb.setPadding(GeneralConfig.guiDefaultPadding);
            vb.setSpacing(GeneralConfig.guiDefaultSpacing);
            final Dialog dial = new Dialog(title,primaryStage,scene /*, "res/Confirm.png"*/);
            Button yesButton = new Button("yes");
            yesButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    dial.close();
                    buttonSelected = Response.YES;
                }
            });
            /* Same for No */
            BorderPane bp = new BorderPane();
            HBox buttons = new HBox();
            buttons.setAlignment(Pos.CENTER);
            buttons.setSpacing(GeneralConfig.guiDefaultSpacing);
            buttons.getChildren().addAll(yesButton);
            bp.setCenter(buttons);
            HBox msg = new HBox();
            msg.setSpacing(GeneralConfig.guiDefaultSpacing);
            msg.getChildren().addAll(msg,bp);
            dial.showDialog();
            return buttonSelected;
        }


    }
}
