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
	ArrayList<Door> doors; //each door is represented by a rectangle
	int[] coord; //room represented by a coordinate for the map 
	boolean completed = false;
	public Area(double height, double width,int xcoord, int ycoord){
		doors=new ArrayList<Door>();
		walls = new ArrayList<Rectangle>();
		setPrefWidth(width);
		setPrefHeight(height);
		coord = new int[]{xcoord,ycoord};
		
	}
	public void setCompleted(boolean b){
		completed = b;
	}
	public boolean getCompleted(){
		return completed;
	}
	public void addLeftDoor(){
		Door d = new Door("left",getPrefWidth(),getPrefHeight());
		doors.add(d);
		getChildren().add(d);
	}
	public void addRightDoor(){
		Door d = new Door("right",getPrefWidth(),getPrefHeight());
		doors.add(d);
		getChildren().add(d);
	}
	public void addTopDoor(){
		Door d = new Door("top",getPrefWidth(),getPrefHeight());
		doors.add(d);
		getChildren().add(d);
	}
	public void addBottomDoor(){
		Door d = new Door("bottom",getPrefWidth(),getPrefHeight());
		doors.add(d);
		getChildren().add(d);
	}
	public String checkDoorCollision(Rectangle hitbox){ //returns left,right,top,bottom, or null if no door
		String ans = null;
		for(Door d: doors){
			if(d.checkCollision(hitbox)){
				ans=d.getPosition();
			}
		}
		return ans;
	}
}