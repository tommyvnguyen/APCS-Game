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
public abstract class Area extends Pane{
	ArrayList<Rectangle> walls; //each wall is represented by a rectangle
	ArrayList<Rectangle> doors; //each door is represented by a rectangle
	int[] coord; //room represented by a coordinate for the map 
	
	public Area(double height, double width,int xcoord, int ycoord){
		setPrefWidth(width);
		setPrefHeight(height);
		coord = new int[]{xcoord,ycoord};
		
	}
	public void addLeftDoor(){
		Rectangle door = new Rectangle(0,getPrefHeight()/2-50,15,100);
		getChildren().add(door);
	}
	public void addRightDoor(){
		Rectangle door = new Rectangle(getPrefWidth()-15,getPrefHeight()/2-50,15,100);
		getChildren().add(door);
	}
	public void addTopDoor(){
		Rectangle door = new Rectangle(0,getPrefHeight()/2-10,20,15);
		getChildren().add(door);
	}
	public void addBottomDoor(){
		Rectangle door = new Rectangle(0,getPrefHeight()/2-10,20,15);
		getChildren().add(door);
	}
}