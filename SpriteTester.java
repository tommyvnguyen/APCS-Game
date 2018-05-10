import javafx.application.Application;
import javafx.stage.Stage;

import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.Node;
import javafx.scene.text.Text;
import javafx.scene.text.Font;


import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import javafx.scene.paint.Color;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

import java.util.ArrayList;
public class SpriteTester extends Application{
	int timeCounter = 1;
	
	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage primaryStage){
		Pane root = new Pane();
		Scene scene = new Scene(root, 1600, 900);
		//Player plyr = new Player(root,200,200,100,100,3,3);
		Pane testPane = new Pane();
		testPane.setPrefSize(100,100);
		testPane.setLayoutX(1000);
		testPane.setLayoutY(100);
		testPane.setStyle("-fx-background-color: red");
		FlyingShooter testEnemy = new FlyingShooter(root, 1, 1, testPane);
		
		testEnemy.setPrefSize(50,50);
		testEnemy.setStyle("-fx-background-color: blue");
		root.getChildren().add(testPane);
		root.getChildren().add(testEnemy);
		
		AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				System.out.println(timeCounter);
				testEnemy.move(timeCounter);
				timeCounter++;
			}
		};
		
		timer.start();
				
		primaryStage.setScene(scene);
		primaryStage.show();
	}
}