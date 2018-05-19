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

	Rectangle hitbox;//Face is a drawn point on each sprite to show which way the sprite is facing.
	
	public Sprite(double xStart, double yStart, double xSpd, double ySpd){

		this.xSpd = xSpd;
		this.ySpd = ySpd;
		this.xStart = xStart;
		this.yStart = yStart;
		spdMultiplier = 1;


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
	
	public boolean collides(Rectangle collideRect){
		return hitbox.getX() <= collideRect.getX() + collideRect.getWidth() && hitbox.getY() <= collideRect.getY() + collideRect.getHeight() && (hitbox.getX() + hitbox.getWidth() >= collideRect.getX() && hitbox.getY() + hitbox.getHeight() >= collideRect.getY()); 
	}

}