import javafx.scene.shape.*;
import javafx.scene.paint.Color;
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
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;



public class Menu extends Pane{
  VBox menuLayers;
  private boolean startGame;
  HighScores hs;
  public Menu(){
    hs= new HighScores();
		BackgroundImage menuBackground= new BackgroundImage(new Image("menuBackground.jpg",0,0,false,false), BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
		setBackground(new Background(menuBackground));

		setPrefHeight(800);
		setPrefWidth(800);

		Text title = new Text("Toasty McToaster");
		title.setFont(new Font(100));
		title.setTextAlignment(TextAlignment.JUSTIFY);
		title.setLayoutX(0);
		title.setFill(Color.BLACK);
		StackPane titlePane = new StackPane();
		titlePane.getChildren().add(title);
		Button startBtn = new Button("Start Game");
		startBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				startGame=true;
			}
		});
		startBtn.setPrefWidth(300);
		startBtn.setPrefHeight(100);
		StackPane startPane = new StackPane();
		startPane.getChildren().add(startBtn);
		Button scoresBtn = new Button("High Scores");
		scoresBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				showScores();
			}
		});
		scoresBtn.setPrefWidth(300);
		scoresBtn.setPrefHeight(100);
		StackPane scorePane = new StackPane();
		scorePane.getChildren().add(scoresBtn);
		menuLayers = new VBox();
		menuLayers.setPrefWidth(800);
		menuLayers.getChildren().addAll(titlePane,startPane,scorePane);

		getChildren().add(menuLayers);
	}
  public boolean getStartGame(){
    return startGame;
  }
  public void setStartGame(boolean b){
    startGame=b;
  }
  public void receiveScore(int score){
		VBox scoreBox = new VBox();
		Text gameOverText = new Text("Game Over!\nEnter your name:");
		gameOverText.setFont(new Font(100));
		gameOverText.setFill(Color.BLACK);
		TextField nameField = new TextField();
		nameField.setOnAction(new EventHandler<ActionEvent>(){
			public void handle(ActionEvent e){
				String name= nameField.getCharacters().toString();
				resetToMenu(name,score);
			}
		});
		scoreBox.getChildren().addAll(gameOverText,nameField);
		scoreBox.setLayoutX(0);scoreBox.setLayoutY(300);
		getChildren().addAll(scoreBox);
	}
  private void resetToMenu(String name, int score){
    if(name!=null){
			hs.addNewScore(name,score);
		}
		getChildren().removeAll(getChildren());
		getChildren().add(menuLayers);
  }
  private void showScores(){
		getChildren().removeAll(getChildren());
		hs = new HighScores();
		getChildren().add(hs);
		Button backBtn = new Button("Return to menu");
		backBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				resetToMenu(null,0);
			}
		});
		getChildren().add(backBtn);
	}
}
