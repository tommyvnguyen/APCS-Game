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
public class Laser extends Projectile{
	Rectangle pointer;
	int timeCounter;
	double angle;

	public Laser(double x, double y, double angle){
		super(x,y,0,0);
		this.angle = angle;
		
		pointer = new Rectangle(x,y,2,1000);
		pointer.setFill(Color.RED);
		pointer.getTransforms().add(new Rotate(angle-90,this.xStart,this.yStart));
		this.getChildren().add(pointer);
		damage = 2;
		fireRate = 10;
		//this.toBack();
	}
	
	public void increaseTimeCounter(){
		timeCounter++;
	}
	
	public int getTimeCounter(){
		return timeCounter;
	}
	
	public void move(){ //Used to animate the laser
		if(timeCounter == 35){
			this.hitbox = new Rectangle(xStart,yStart,5,1000);
			this.hitbox.setFill(Color.RED);
			this.hitbox.getTransforms().add(new Rotate(angle-90,this.xStart,this.yStart));
			getChildren().add(this.hitbox);
		}else if(timeCounter < 60){
			if(hitbox.getWidth() < 50){
				hitbox.setWidth(hitbox.getWidth() + 6);
				hitbox.setX(hitbox.getX() - 3);
			}
		}else{
			if(hitbox.getWidth() >= 0){
				hitbox.setWidth(hitbox.getWidth() - 1);
				hitbox.setX(hitbox.getX() + 0.5);
			}
		}
	}
	
	public void move(Rectangle target){
		System.out.println(timeCounter);
		if(timeCounter == 25){
			this.hitbox = new Rectangle(xStart,yStart,5,1000);
			this.hitbox.setFill(Color.RED);
			getChildren().add(this.hitbox);
		}else if(timeCounter < 50){
			if(hitbox.getWidth() < 50){
				hitbox.setWidth(hitbox.getWidth() + 6);
				hitbox.setX(hitbox.getX() - 3);
			}
		}else{
			if(hitbox.getWidth() > 0){
				hitbox.setWidth(hitbox.getWidth() - 2);
				hitbox.setX(hitbox.getX() + 1);
			}
		}
	}
	
	public boolean collides(Collidable collidable){
		double currentAngle = Math.toDegrees(Math.atan2(this.hitbox.getY() - collidable.getHitbox().getY(),this.hitbox.getX()-collidable.getHitbox().getX())) + 180;
		//System.out.println(angle);
		//return hitbox.getX() <= collidable.getHitbox().getX() + collidable.getHitbox().getWidth() && (hitbox.getY() + hitbox.getX()*Math.tan(angle)) <= collidable.getHitbox().getY() + collidable.getHitbox().getHeight() && (hitbox.getX() + hitbox.getWidth() >= collidable.getHitbox().getX() && ((hitbox.getY() + hitbox.getHeight()) + hitbox.getX()*Math.tan(angle)) >= collidable.getHitbox().getY());  
		return currentAngle >= angle-5 && currentAngle <= angle + 5 && timeCounter >= 35;
	}
	
}	