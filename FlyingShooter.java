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
	
	public FlyingShooter(Pane outerPane, double dy, double dx, Pane target){
		super(outerPane, dy, dx, target);
		
	}
	//TimeCounter counts how many times the animationTimer has called its methods. 
	//TimeCounter acts as a unit of time.
	public void move(int timeCounter){
		if(this.getLayoutX() >= 0 && this.getLayoutX() + this.getWidth() < 1600){ //Be sure to make it based around the root's size, later
			this.setLayoutX(this.getLayoutX() + xSpd);
		}
		else{
			xSpd *= -1;
			this.setLayoutX(this.getLayoutX() + xSpd);
		}
		if(this.getLayoutY() >= 0 && this.getLayoutY() + this.getHeight() < 900){ 
			this.setLayoutY(this.getLayoutY() + ySpd);
		}else{
			ySpd *= -1;
			this.setLayoutY(this.getLayoutY() + ySpd);
		}
		
		
	
		this.getTransforms().clear();
		this.getTransforms().add(new Rotate(Math.atan(((this.getLayoutY() + this.getHeight()/2) - (target.getLayoutY() + target.getHeight()/2))/((this.getLayoutX() + this.getWidth()/2) - (target.getLayoutX() + target.getWidth()/2)) ),this.getWidth()/2, this.getHeight()/2));
		//this.getTransforms().add(new Rotate(1,this.getWidth()/2, this.getHeight()/2));
		
		
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
			System.out.println("     " + PosOrNeg);
		}else if(timeCounter%360 == 270){
			xSpd = 0;
		 	ySpd = 0;
		}
		
		//Turn towards player
		System.out.println(ySpd + " -- " + xSpd);
	}
	
}