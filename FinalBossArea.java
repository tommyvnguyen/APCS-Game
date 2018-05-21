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
import javafx.util.Duration;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Rotate;
import java.util.*;
public class FinalBossArea extends Area{


	public FinalBossArea(int xcoord, int ycoord){
		super(xcoord, ycoord);
		Rectangle leftWall = new Rectangle(0,0,10,getPrefHeight());
		leftWall.setFill(Color.BROWN);
		Rectangle topWall = new Rectangle(0,0,getPrefWidth(),10);
		topWall.setFill(Color.BROWN);
		Rectangle rightWall= new Rectangle(getPrefWidth()-10,0,10,getPrefHeight());
		rightWall.setFill(Color.BROWN);
		Rectangle bottomWall = new Rectangle(0,getPrefHeight()-10,getPrefWidth(),10);
		bottomWall.setFill(Color.BROWN);
		Rectangle midBlock = new Rectangle (400,400,30,30);
		Rectangle bottomBar = new Rectangle (200,600,400,30);
		bottomBar.setFill(Color.BLUE);
		getChildren().addAll(leftWall,rightWall,topWall,bottomWall,midBlock,bottomBar);
		walls.addAll(leftWall,rightWall,topWall,bottomWall,midBlock,bottomBar);
		
	}
}