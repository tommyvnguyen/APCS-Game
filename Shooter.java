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

import javafx.animation.AnimationTimer;

import javafx.util.Duration;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Rotate;

import java.util.ArrayList;
public abstract class Shooter extends Enemy{
	//Projectile projectile;
	//jpeg

	AnimationTimer timer;
	ArrayList<Projectile> bullets;
	int shootingCounter; 
	
	public Shooter(double dy, double dx, Rectangle target){
		super(dy, dx, target);
		shootingCounter = 0;
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
			   for(Projectile p : bullets){
				   p.move();
			   }
            }
        };
        timer.start();
		bullets = new ArrayList<Projectile>();

		//May need to receive ArrayList<Sprite>, so that fire() can add a projectile object to it.
		//The timer will loop through the arraylist and call each move() in it.
	}
	
		
	public ArrayList<Projectile> getBullets(){
		return this.bullets;
	}
	
	
	public void fire(){
		if(shootingCounter % 50 == 0){
			double angle = Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX())) + 180;
			double x = (target.getX() + target.getWidth()/2) - (this.hitbox.getWidth() + this.hitbox.getX()) + (this.hitbox.getHeight()/2 * Math.cos(Math.toRadians(angle)));
			double y = (target.getY() + target.getHeight()/2) - (this.hitbox.getHeight()/2 + this.hitbox.getY()) + (this.hitbox.getWidth()/2 * Math.sin(Math.toRadians(angle)));
			double d = Math.sqrt(Math.pow(x,2) + Math.pow(y,2));
			double shotDx = x*10/d;  //Yeah I know how redundant this is.
			double shotDy = y*10/d;
		
			//System.out.println(shotDx + " " + shotDy);
			SingleShot shot = new SingleShot(this.hitbox.getWidth() + this.hitbox.getX(), this.hitbox.getHeight()/2 + this.hitbox.getY(), shotDx, shotDy);
		//shot.getTransforms().add(new Rotate(Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX()))+180, (this.hitbox.getWidth()/2 + this.hitbox.getX()), (this.hitbox.getHeight()/2 + this.hitbox.getY())));
			this.getChildren().add(shot);
			this.bullets.add(shot);
		}
		shootingCounter++;
	}
	public void move(){
		super.move();
	}
}