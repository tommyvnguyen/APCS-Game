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
public class Game extends Scene{
	int timeCounter;
	Player plyr;
	ArrayList<Enemy> enemies;
	ArrayList<Powerup> powerups;
	Pane gamePane;
	StatsPane statsPane;
	BorderPane rootPane;
	Map map;
	
	Game(){
		super(new BorderPane(), 800, 950);
		rootPane = (BorderPane)getRoot();
		gamePane = new Pane();
		gamePane.setPrefSize(800,800);
		gamePane.setStyle("-fx-border: single; -fx-border-color: black; -fx-border-width: 5px;");
		rootPane.setCenter(gamePane);
		
		plyr = new Player(200,200,50,50,1,1,4);
		enemies = new ArrayList<Enemy>();
		powerups = new ArrayList<Powerup>();
		gamePane.getChildren().add(plyr);
		timeCounter = 0;
		
		statsPane = new StatsPane(plyr);
		statsPane.setPrefSize(800,150);
		statsPane.setStyle("-fx-border: single; -fx-border-color: black; -fx-border-width: 5px; -fx-background-color: #3e4651;");
		rootPane.setTop(statsPane);
		
		
		map = new Map(20);
		gamePane.getChildren().add(map);
		// TESTING ----------------------------- 
		powerups.add(new DamagePowerup(500,500));
		gamePane.getChildren().add(powerups.get(0));
		powerups.add(new PoisonPowerup(200,500));
		gamePane.getChildren().add(powerups.get(1));
		
		Chaser e1 = new Chaser(1,1,plyr.getHitbox());
		enemies.add(e1);
		gamePane.getChildren().add(e1);
		e1.getHitbox().setX(600);
		e1.getHitbox().setY(400);
		Chaser e2 = new Chaser(1,1,plyr.getHitbox());
		enemies.add(e2);
		gamePane.getChildren().add(e2);
		e2.getHitbox().setX(200);
		e2.getHitbox().setY(400);
		FlyingShooter s1 = new FlyingShooter(1,1,plyr.getHitbox());
		s1.getHitbox().setX(400);
		s1.getHitbox().setY(400);
		enemies.add(s1);
		gamePane.getChildren().add(s1);
		
		// --------------------------------------
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
		ArrayList<Enemy> hitList = new ArrayList<Enemy>();
		//if(timeCounter%100 == 0 && enemies.size() < 2){
		//	Chaser newEnemy = new Chaser(1,1,plyr.getHitbox());
		//	enemies.add(newEnemy);
		//	gamePane.getChildren().add(newEnemy);
		//}
		
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getTimeCounter() > 50){
				enemies.get(i).move();
				if(enemies.get(i).collides(plyr)){
					plyr.takeDamage();
					if(!plyr.isImmune()){
						if(enemies.get(i).decreaseHealth(plyr.getMeleeDmg()) <= 0){
								hitList.add(enemies.get(i));
						}
						statsPane.update();
						plyr.setImmune();
						System.out.println(plyr);
					}
				}
				
				//Enemy bullets collide w/ objects
				if(enemies.get(i) instanceof Shooter){
					Shooter shooter = (Shooter)enemies.get(i);
					shooter.fire();
					for(int j = 0; j < shooter.getBullets().size(); j++){
						Rectangle bulletHitbox = shooter.getBullets().get(j).getHitbox();
						if(shooter.getBullets().get(j).collides(plyr)){
							shooter.getChildren().remove(j+1);
							shooter.getBullets().remove(j);
							plyr.takeDamage();
							plyr.setImmune();
							System.out.println(plyr);
							statsPane.update();
						}
						
						if(bulletHitbox.getX() < 0 || bulletHitbox.getY() < 0 || bulletHitbox.getX() > 1600 || bulletHitbox.getY() > 900){
							shooter.getChildren().remove(j+1);
							shooter.getBullets().remove(j);
						}
					}
				}
			}
			enemies.get(i).increaseTimeCounter();
		}
		
		//playerBullets collide w/ objects
		for(int i = 0; i < plyr.getBullets().size(); i++){
			for(int j = 0; j < enemies.size(); j++){
				//System.out.println("Check");
				if(plyr.getBullets().get(i).collides(enemies.get(j))){
					//System.out.println(j + "--" + enemies.size() + "--" + root.getChildren().size());
						
					if(enemies.get(j).decreaseHealth(plyr.getBulletType().getDamage()) <= 0){
						hitList.add(enemies.get(j));
					}
					plyr.getChildren().remove(plyr.getBullets().get(i));
					plyr.getBullets().remove(i);
					
					break;
					//System.out.println(enemies.size() + " " + root.getChildren().size());
				}
				if(plyr.getBullets().get(i).getHitbox().getX() <= 0 || plyr.getBullets().get(i).getHitbox().getY() <= 0 || plyr.getBullets().get(i).getHitbox().getX() + plyr.getBullets().get(i).getHitbox().getWidth() >= 1600 || plyr.getBullets().get(i).getHitbox().getY() + plyr.getBullets().get(i).getHitbox().getHeight() >= 900){
					plyr.getChildren().remove(plyr.getBullets().get(i));
					plyr.getBullets().remove(i);
					break;
				}
			}
		}
		for(Enemy e : hitList){
			double healthDropChance = Math.random();
			if(healthDropChance >= .90){
				HealingPowerup health = new HealingPowerup(e.getHitbox().getX(),e.getHitbox().getY());
				powerups.add(health);
				gamePane.getChildren().add(health);
			}
			gamePane.getChildren().remove(e);
			enemies.remove(e);

				
		}
		hitList.clear();
		
		//Player collide w/ objects
		for(int i = 0; i < powerups.size(); i++){
			if(plyr.collides(powerups.get(i))){
				if(powerups.get(i).upgrade(plyr)){
					gamePane.getChildren().remove(powerups.get(i));
					powerups.remove(i);
					statsPane.update();
				}
			}
		}
		
		timeCounter++;
	}
	
	//private void checkDoors(){
	//	String dir=map.checkDoorCollision(plyr.getHitbox());
	//	if(dir!=null) System.out.println(dir);
	//	if(dir!=null){
	//		map.moveRooms(dir);
	//		//might be a good idea to change the numbers to actual variable getters
	//		if(dir.equals("right")){
	//			plyr.setX(30);
	//			plyr.setY(400);
	//		}
	//		if(dir.equals("left")){
	//			plyr.setX(720);
	//			plyr.setY(400);
	//		}
	//		if(dir.equals("top")){
	//			plyr.setX(400);
	//			plyr.setY(720);
	//		}
	//		if(dir.equals("bottom")){
	//			plyr.setX(400);
	//			plyr.setY(30);
	//		}
	//	}
	//}	
	//
	//private void checkWalls(){
	//	plyr.setHittingWall(null);
	//	if(plyr.getHitbox().getX()<0){
	//		plyr.setHittingWall("left");
	//	}
	//	if(plyr.getHitbox().getX()>800-plyr.getHitbox().getWidth()){
	//		plyr.setHittingWall("right");
	//	}
	//}
}