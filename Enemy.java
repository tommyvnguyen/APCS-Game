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
public abstract class Enemy extends Sprite{
	int damage;
	int health;
	int timeCounter;

	Rectangle target; //The thing that the enemy is looking at
	//Projectile projectile;
	//jpeg
	
	public Enemy(double dy, double dx, Rectangle target){
		super(dy, dx);

		damage = 1;
		health = 10;
		timeCounter = 0;
		this.target = target;
	}
	

	public void track(){
		if(timeCounter > 60){
			this.hitbox.getTransforms().clear();
			this.hitbox.getTransforms().add(new Rotate(Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX()))+180, (this.hitbox.getWidth()/2 + this.hitbox.getX()), (this.hitbox.getHeight()/2 + this.hitbox.getY())));
		}
	}
	
	public void move(){
		if(this.hitbox.getX() >= 0 && this.hitbox.getX() + this.hitbox.getWidth() < 1600){ //Be sure to make it based around the root's size, later
			this.hitbox.setX(this.hitbox.getX() + xSpd * spdMultiplier);
		}
		else{
			xSpd *= -1;
			this.hitbox.setX(this.hitbox.getX() + xSpd * spdMultiplier);
		}
		if(this.hitbox.getY() >= 0 && this.hitbox.getY() + this.hitbox.getHeight() < 900){ 
			this.hitbox.setY(this.hitbox.getY() + ySpd * spdMultiplier);
		}else{
			ySpd *= -1;
			this.hitbox.setY(this.hitbox.getY() + ySpd * spdMultiplier);
		}
		//if(spdMultiplier > trueSpd){
		//	spdMultiplier -= 0.1;
		//}else if(spdMultiplier < trueSpd){
		//	spdMultiplier += 0.1;
		//}
	}
	
	public int getHealth(){
		return this.health;
	}
	public int getDamage(){
		return this.damage;
	}
	
	public int decreaseHealth(int decreaseAmt){
		health -= decreaseAmt;
		return health;
	}
	
	public void increaseTimeCounter(){
		timeCounter++;
	}
	
	public int getTimeCounter(){
		return timeCounter;
	}
   // It takes a y value and an x value and finds their proportion
}