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
public class FlyingShooter extends Shooter{
	
	//Projectile projectile;
	//jpeg
	

	public FlyingShooter(double dy, double dx, Rectangle target){
		super(dy, dx, target);
		this.getChildren().add(hitbox);

	}
	//TimeCounter counts how many times the animationTimer has called its methods. 
	//TimeCounter acts as a unit of time.
	public void move(int timeCounter){

		if(this.hitbox.getX() >= 0 && this.hitbox.getX() + this.hitbox.getWidth() < 1600){ //Be sure to make it based around the root's size, later
			this.hitbox.setX(this.hitbox.getX() + xSpd);
		}
		else{
			xSpd *= -1;
			this.hitbox.setX(this.hitbox.getX() + xSpd);
		}
		if(this.hitbox.getY() >= 0 && this.hitbox.getY() + this.hitbox.getHeight() < 900){ 
			this.hitbox.setY(this.hitbox.getY() + ySpd);
		}else{
			ySpd *= -1;
			this.hitbox.setY(this.hitbox.getY() + ySpd);
		}
		
		
		//this.getTransforms().add(new Rotate(Math.atan(((this.getLayoutY() + this.getHeight()/2) - (target.getLayoutY() + target.getHeight()/2))/((this.getLayoutX() + this.getWidth()/2) - (target.getLayoutX() + target.getWidth()/2)) ),this.getWidth()/2, this.getHeight()/2));
		//this.getTransforms().add(new Rotate(1,this.getWidth()/2, this.getHeight()/2));
		track();

		
		if(timeCounter%360 == 0){
			int PosOrNeg = (int)(Math.random() * 2);
			xSpd = Math.random();
		 	ySpd = Math.sqrt(1 - Math.pow(xSpd,2));
			if(PosOrNeg == 0){
				xSpd *= -1;
			}
			PosOrNeg = (int)(Math.random() * 2);
			if(PosOrNeg == 0){
				ySpd *= -1;
			}

			//System.out.println("     " + PosOrNeg);

		}else if(timeCounter%360 == 270){
			xSpd = 0;
		 	ySpd = 0;
		}
		
		//Turn towards player

		//System.out.println(ySpd + " -- " + xSpd);
	}
	
	

}