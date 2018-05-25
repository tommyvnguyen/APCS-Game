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
import javafx.scene.text.*;


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

		Text title = new Text("THIS GUY'S TOAST");
		title.setFont(new Font(95));
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
    Button helpBtn = new Button("Help/Controls");
		helpBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				showHelp();
			}
		});
		helpBtn.setPrefWidth(300);
		helpBtn.setPrefHeight(100);
		StackPane helpPane = new StackPane();
		helpPane.getChildren().add(helpBtn);
		menuLayers = new VBox();
		menuLayers.setPrefWidth(800);
		menuLayers.getChildren().addAll(titlePane,startPane,scorePane,helpPane);

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
		scoreBox.setLayoutX(0);scoreBox.setLayoutY(500);
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
  private void showHelp(){
    getChildren().removeAll(getChildren());
    String s= "You start in a room. You must travel around room to room defeating enemies and collecting powerups which drop randomly. \n";
    s+="Eventually, youll find the find the final boss, the Toaster. \n\n";
    s+="Controls- \nW - Up \nA - Left \nS - Down \nD - Right \nArrow keys - Shoot";
    Text help = new Text(s);
    help.setFont(new Font(20));
    help.setY(100);
    help.setX(50);
    help.setWrappingWidth(700);
    Button backBtn = new Button("Return to menu");
    backBtn.setOnAction(new EventHandler <ActionEvent> (){
			@Override
			public void handle(ActionEvent event){
				resetToMenu(null,0);
			}
		});
		getChildren().addAll(backBtn,help);
  }
}
