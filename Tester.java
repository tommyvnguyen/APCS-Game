import javafx.scene.text.Text;
import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
public class Tester extends Application{
	public static void main(String[] args){
		launch(args);
	}
	public void start(Stage stage){

		Pane root = new Pane();
		Player plyr = new Player(root,200,200,100,100,3,3);
		root.getChildren().add(plyr);
		
		
		stage.setScene(new Scene(root, 600,600));
		stage.getScene().onKeyPressedProperty().bind(plyr.onKeyPressedProperty());
		stage.getScene().onKeyReleasedProperty().bind(plyr.onKeyReleasedProperty());
		stage.show();
	}
}		