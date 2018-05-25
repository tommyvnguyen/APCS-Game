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
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

import java.util.ArrayList;
public class LaserShooter extends Shooter{
	
	LaserShooter(double dy, double dx, Rectangle target){
		super(dy, dx, target);
		
		health = 25;
		this.getChildren().add(hitbox);
		this.spdMultiplier = 0.5;
	}
	
	public void move(){
		super.move();
		
		double angle = Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX())) + 180;
		double x = (target.getX() + target.getWidth()/2) - (this.hitbox.getWidth() + this.hitbox.getX()) + (this.hitbox.getHeight()/2 * Math.cos(Math.toRadians(angle)));
		double y = (target.getY() + target.getHeight()/2) - (this.hitbox.getHeight()/2 + this.hitbox.getY()) + (this.hitbox.getWidth()/2 * Math.sin(Math.toRadians(angle)));
		double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
		
		if(timeCounter % 250 < 100){
			track();
			xSpd = x*spdMultiplier/d;
			ySpd = y*spdMultiplier/d;
		}else{
			xSpd = 0;
			ySpd = 0;
		}
		
	}
	
	public void fire(){
		if(timeCounter % 250 == 100){
			double angle = Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX())) + 180;
			Laser laser = new Laser(this.getHitbox().getX() + this.getHitbox().getWidth()/2, this.getHitbox().getY() + this.getHitbox().getHeight()/2, angle);
			getChildren().add(laser);
			getBullets().add(laser);
		}
	}
	
}