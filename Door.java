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
public class Door extends Pane {
	String position; //left, right, top or bottom
	Rectangle door;
	public Door(String pos,double paneHeight, double paneWidth){
		setPrefWidth(paneWidth);
		setPrefHeight(paneHeight);
		pos=pos.toLowerCase();
		position=pos;
		if(pos.equals("left")){
			door = new Rectangle(0,paneHeight/2-50,15,100);
			getChildren().add(door);
		}
		else if(pos.equals("right")){
			door = new Rectangle(paneWidth-15,paneHeight/2-50,15,100);
			getChildren().add(door);
		}
		else if(pos.equals("bottom")){
			door = new Rectangle(paneWidth/2-50,paneHeight-15,100,15);
			getChildren().add(door);
		}
		else if(pos.equals("top")){
			door = new Rectangle(paneWidth/2-50,0,100,15);
			getChildren().add(door);
		}
	}
	
	
	
}