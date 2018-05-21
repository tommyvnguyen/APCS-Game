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
public abstract class Powerup extends Pane implements Collidable{
	Rectangle hitbox;
	
	public Powerup(double xStart, double yStart){
		hitbox = new Rectangle(xStart,yStart,25,25);
		hitbox.setFill(Color.PURPLE);
		getChildren().add(hitbox);
	}
	
	public Rectangle getHitbox(){
		return this.hitbox;
	}
	
	public boolean collides(Collidable collidable){
		return hitbox.getX() <= collidable.getHitbox().getX() + collidable.getHitbox().getWidth() && hitbox.getY() <= collidable.getHitbox().getY() + collidable.getHitbox().getHeight() && (hitbox.getX() + hitbox.getWidth() >= collidable.getHitbox().getX() && hitbox.getY() + hitbox.getHeight() >= collidable.getHitbox().getY()); 
	}
	
	public boolean upgrade(Player plyr){return true;}
}