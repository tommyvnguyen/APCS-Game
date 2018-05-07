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

import java.util.ArrayList;
public abstract class Sprite extends Pane{
	double xStart;
	double yStart;
	double xSpd;
	double ySpd;
	Pane outerPane; 
	Rectangle face;//Face is a drawn point on each sprite to show which way the sprite is facing.
	
	public Sprite(Pane outerPane, double xStart, double yStart, double xSpd, double ySpd){
		this.xSpd = xSpd;
		this.ySpd = ySpd;
		this.xStart = xStart;
		this.yStart = yStart;

		setLayoutX(xStart);
		setLayoutY(yStart);
		//this.outerPane = outerPane;
		//outerPane.getChildren().add(this);

		
		face = new Rectangle(5,5,5,5); 
		face.setFill(Color.RED);
		getChildren().add(face);
	}
	public Sprite(Pane outerPane, double xSpd, double ySpd){
		this.xSpd = xSpd;
		this.ySpd = ySpd;
		this.xStart = 0;
		this.yStart = 0;

	
		//this.outerPane = outerPane;
		//outerPane.getChildren().add(this);
		
	}
	public void move(){
		if(this.getLayoutX() > 0 && this.getLayoutX() < this.outerPane.getWidth())
			this.setLayoutX(this.getLayoutX() + xSpd);
		if(this.getLayoutY() > 0 && this.getLayoutY() < this.outerPane.getHeight())
			this.setLayoutY(this.getLayoutY() + ySpd);
	}

}