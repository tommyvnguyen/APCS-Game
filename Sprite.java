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
public abstract class Sprite extends Pane implements Collidable{

	double xStart;
	double yStart;
	double xSpd;
	double ySpd;
	double spdMultiplier;
	//double trueSpd; // In the case that the sprite slows down or stops, trueSpd will be used to make sure that the sprite will go back to its normal speed

	Rectangle hitbox;//Face is a drawn point on each sprite to show which way the sprite is facing.
	
	public Sprite(double xStart, double yStart, double xSpd, double ySpd){

		this.xSpd = xSpd;
		this.ySpd = ySpd;
		this.xStart = xStart;
		this.yStart = yStart;
		spdMultiplier = 1;
		//trueSpd = spdMultiplier;


		//setLayoutX(xStart);
		//setLayoutY(yStart);
		
		hitbox = new Rectangle(xStart,yStart,50,50);
		hitbox.setFill(Color.BLACK);
		//this.outerPane = outerPane;
		//outerPane.getChildren().add(this);
		//this.getChildren().add(hitbox);
		

	}
	
	public Sprite(double xStart, double yStart, double xSpd, double ySpd, double multiplier){

		this.xSpd = xSpd;
		this.ySpd = ySpd;
		this.xStart = xStart;
		this.yStart = yStart;
		spdMultiplier = multiplier;
		//trueSpd = spdMultiplier;


		//setLayoutX(xStart);
		//setLayoutY(yStart);
		
		hitbox = new Rectangle(xStart,yStart,50,50);
		hitbox.setFill(Color.BLACK);
		//this.outerPane = outerPane;
		//outerPane.getChildren().add(this);
		//this.getChildren().add(hitbox);
		

	}
	
	public Sprite(double xSpd, double ySpd){

		this.xSpd = xSpd;
		this.ySpd = ySpd;
		this.xStart = 0;
		this.yStart = 0;
		spdMultiplier = 1;
		//trueSpd = spdMultiplier;
		
		//setLayoutX(xStart);
		//setLayoutY(yStart);
		
		hitbox = new Rectangle(xStart,yStart,50,50);
		hitbox.setFill(Color.BLACK);
		//getChildren().add(this.hitbox);
	}
	
	public double getSpdMultiplier(){
		return spdMultiplier;
	}
	
	public void setSpdMultiplier(double multiplier){
		spdMultiplier = multiplier;
	}
	
	public Rectangle getHitbox(){
		return this.hitbox;
	}
	public void move(){
		this.hitbox.setX(this.hitbox.getX() + xSpd);
		this.hitbox.setY(this.hitbox.getY() + ySpd);
	}
	
	public boolean collides(Collidable collidable){
		return hitbox.getX() <= collidable.getHitbox().getX() + collidable.getHitbox().getWidth() && hitbox.getY() <= collidable.getHitbox().getY() + collidable.getHitbox().getHeight() && (hitbox.getX() + hitbox.getWidth() >= collidable.getHitbox().getX() && hitbox.getY() + hitbox.getHeight() >= collidable.getHitbox().getY()); 
	}

}