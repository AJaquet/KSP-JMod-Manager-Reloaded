package de.notepass.general.objects.gui;

import de.notepass.general.util.Util;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

//This is a standard title GroupBox

public class GroupBox extends StackPane {

    public static final TitlePos TITLE_POS_LEFT = TitlePos.POS_LEFT;
    public static final TitlePos TITLE_POS_CENTER = TitlePos.POS_CENTER;
    public static final TitlePos TITLE_POS_RIGHT = TitlePos.POS_RIGTH;

    //The titePane will later just Contain the title and a basic markup
    public StackPane titlePane = new StackPane();
    //The content Pane will contain all elements

    //Both of them are modifiable from the Outside... For Dynamical reasons

    public GridPane contentPane = new GridPane();
    private Label title = new Label();
    public static String cssFile = Util.createLoadString("style/GroupBox_Fixed.css");

    public GroupBox (String titleString, TitlePos tp) {

        title.setText(" "+titleString+" ");
        title.getStyleClass().add("GroupBoxTitle");

        StackPane.setAlignment(title, tp.getMainPos());
        title.setStyle(tp.getPosString());

        titlePane.getStyleClass().add("GroupBoxContent");
        titlePane.getChildren().add(contentPane);

        getStyleClass().add("GroupBoxBorder");
        getChildren().addAll(title, titlePane);
    }

    public void add(Node content, int column, int row) {
        this.contentPane.add(content, column, row);
    }

    public void add(Node content, int column, int row, int columnSpan, int rowSpan) {
        this.contentPane.add(content,column,row,columnSpan,rowSpan);
    }
}
