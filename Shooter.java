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

import java.util.ArrayList;
public abstract class Shooter extends Enemy{
	//Projectile projectile;
	//jpeg
	
	public Shooter(Pane outerPane, double dy, double dx){
		super(outerPane, dy, dx);
		//May need to receive ArrayList<Sprite>, so that fire() can add a projectile object to it.
		//The timer will loop through the arraylist and call each move() in it.
	}
	
	public void fire(){
		SingleShot newProj = new SingleShot();
		newProj.setLayoutX(this.getLayoutX() + this.getWidth()/2);
		newProj.setLayoutY(this.getLayoutY() + this.getHeight()/2);
		outerPane.getChildren().add(new SingleShot());
		//this.sprites.add(singleShot);
	}
}