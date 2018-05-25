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
public class Vomiter extends Shooter{
	private int pauseCounter;
	private String directionFacing;

	Vomiter(double dy, double dx, Rectangle target){
		super(dy, dx, target);

		directionFacing = "FORWARD";
		health = 100;
		this.hitbox.setWidth(75);
		this.hitbox.setHeight(75);
		this.getChildren().add(hitbox);
		this.spdMultiplier = 2.1;
	}

	public void move(){
		super.move();
		track();
		double angle = Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX())) + 180;
		double x = (target.getX() + target.getWidth()/2) - (this.hitbox.getWidth() + this.hitbox.getX()) + (this.hitbox.getHeight()/2 * Math.cos(Math.toRadians(angle)));
		double y = (target.getY() + target.getHeight()/2) - (this.hitbox.getHeight()/2 + this.hitbox.getY()) + (this.hitbox.getWidth()/2 * Math.sin(Math.toRadians(angle)));
		double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
		if(pauseCounter <= 0){
			if(d <= 225){
				xSpd = 0;
				ySpd = 0;
				pauseCounter++;
			}else{
				xSpd = x*spdMultiplier/d;
				ySpd = y*spdMultiplier/d;
			}
		}else{
			if(pauseCounter < 180){
				pauseCounter++;
			}else{
				pauseCounter = 0;
			}
		}
	}

	public void fire(){
		if(pauseCounter > -1){
			double randDx = Math.random() * 3 + 0.5;
			double randDy = Math.random() * 3 + 0.5;
			double angle = Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX())) + 180;
			double x = (target.getX() + target.getWidth()/2) - (this.hitbox.getWidth() + this.hitbox.getX()) + (this.hitbox.getHeight()/2 * Math.cos(Math.toRadians(angle)));
			double y = (target.getY() + target.getHeight()/2) - (this.hitbox.getHeight()/2 + this.hitbox.getY()) + (this.hitbox.getWidth()/2 * Math.sin(Math.toRadians(angle)));
			double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
			double shotDx = x*randDx/d;
			double shotDy = y*randDy/d;

				//System.out.println(shotDx + " " + shotDy);
			SingleShot shot = new SingleShot(this.hitbox.getWidth()/2 + this.hitbox.getX(), this.hitbox.getHeight()/2 + this.hitbox.getY(), shotDx, shotDy);
			//shot.getTransforms().add(new Rotate(Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX()))+180, (this.hitbox.getWidth()/2 + this.hitbox.getX()), (this.hitbox.getHeight()/2 + this.hitbox.getY())));
			this.getChildren().add(shot);
			this.bullets.add(shot);
		}
	}
}
