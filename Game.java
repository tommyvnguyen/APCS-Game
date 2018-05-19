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

import java.util.ArrayList;
public class Game extends Scene{
	int timeCounter;
	Player plyr;
	ArrayList<FlyingShooter> enemies;
	Pane pane;
	
	Game(){
		super(new Pane(), 1600, 900);
		pane = (Pane)getRoot();
		plyr = new Player(200,200,100,100,1,1,3);
		enemies = new ArrayList<FlyingShooter>();
		pane.getChildren().add(plyr);
		timeCounter = 0;
		
		AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				run();
			}
		};
		timer.start();
		onKeyPressedProperty().bind(plyr.onKeyPressedProperty());
		onKeyReleasedProperty().bind(plyr.onKeyReleasedProperty());
	}
	
	public void run(){
		//FlyingShooter testEnemy = new FlyingShooter(1, 1, plyr.getHitbox());
		//root.getChildren().add(testEnemy);
		
		//System.out.println(timeCounter);
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).fire();
		}
		if(timeCounter%100 == 0 && enemies.size() < 5){
			FlyingShooter newShooter = new FlyingShooter(1,1,plyr.getHitbox());
			enemies.add(newShooter);
			pane.getChildren().add(newShooter);
		}
		//testEnemy.move(timeCounter);
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).move(timeCounter);
			for(int j = 0; j < enemies.get(i).getBullets().size(); j++){
				Rectangle bulletHitbox = enemies.get(i).getBullets().get(j).getHitbox();
				if(enemies.get(i).getBullets().get(j).collides(plyr.getHitbox())|| bulletHitbox.getX() < 0 || bulletHitbox.getY() < 0 || bulletHitbox.getX() > 1600 || bulletHitbox.getY() > 900){
					enemies.get(i).getChildren().remove(j+1);
					enemies.get(i).getBullets().remove(j);
				}
			}
		}
		
		//playerBullets collide w/ objects
		ArrayList<Enemy> hitList = new ArrayList<Enemy>();
		for(int i = 0; i < plyr.getBullets().size(); i++){
			for(int j = 0; j < enemies.size(); j++){
				//System.out.println("Check");
				if(plyr.getBullets().get(i).collides(enemies.get(j).getHitbox())){
					//System.out.println(j + "--" + enemies.size() + "--" + root.getChildren().size());
						
					if(enemies.get(j).decreaseHealth(plyr.getBullets().get(i).getDamage()) <= 0){
						hitList.add(enemies.get(j));
					}
					plyr.getBullets().remove(i);
					plyr.getChildren().remove(i+1);
					break;
					//System.out.println(enemies.size() + " " + root.getChildren().size());
				}
				if(plyr.getBullets().get(i).getHitbox().getX() <= 0 || plyr.getBullets().get(i).getHitbox().getY() <= 0 || plyr.getBullets().get(i).getHitbox().getX() + plyr.getBullets().get(i).getHitbox().getWidth() >= 1600 || plyr.getBullets().get(i).getHitbox().getY() + plyr.getBullets().get(i).getHitbox().getHeight() >= 900){
					plyr.getBullets().remove(i);
					plyr.getChildren().remove(i+1);
					break;
				}
			}
		}
		for(Enemy e : hitList){
			pane.getChildren().remove(e);
			enemies.remove(e);
		}
		hitList.clear();
		timeCounter++;
	}
}