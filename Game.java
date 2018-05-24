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
import javafx.scene.control.Label;
import java.util.ArrayList;
public class Game extends Scene{
	int timeCounter;
	Player plyr;
	ArrayList<Shooter> enemies;
	ArrayList<Powerup> powerups;
	Pane pane;
	Map map;

																				private final long[] frameTimes = new long[100];
																			private int frameTimeIndex = 0 ;
																			private boolean arrayFilled = false ;
	Game(){
		super(new Pane(), 800, 800);
		pane = (Pane)getRoot();
		plyr = new Player(200,200,50,50,1,1,8);
		enemies = new ArrayList<Shooter>();
		powerups = new ArrayList<Powerup>();
		pane.getChildren().add(plyr);
		timeCounter = 0;

		powerups.add(new DamagePowerup(500,500));
		pane.getChildren().add(powerups.get(0));

		map = new Map(20);
		pane.getChildren().add(map);

		AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				run();
				checkWalls();
				checkDoors();
				enemyCheckWalls();
			}
		};
		timer.start();
		onKeyPressedProperty().bind(plyr.onKeyPressedProperty());
		onKeyReleasedProperty().bind(plyr.onKeyReleasedProperty());


																Label label = new Label();
														    AnimationTimer frameRateMeter = new AnimationTimer() {

														        @Override
														        public void handle(long now) {
														            long oldFrameTime = frameTimes[frameTimeIndex] ;
														            frameTimes[frameTimeIndex] = now ;
														            frameTimeIndex = (frameTimeIndex + 1) % frameTimes.length ;
														            if (frameTimeIndex == 0) {
														                arrayFilled = true ;
														            }
														            if (arrayFilled) {
														                long elapsedNanos = now - oldFrameTime ;
														                long elapsedNanosPerFrame = elapsedNanos / frameTimes.length ;
														                double frameRate = 1_000_000_000.0 / elapsedNanosPerFrame ;
														                label.setText(String.format("Current frame rate: %.3f", frameRate));
														            }
														        }
														    };
														    frameRateMeter.start();
																pane.getChildren().add(new StackPane(label));

	}

	public void run(){
		//FlyingShooter testEnemy = new FlyingShooter(1, 1, plyr.getHitbox());
		//root.getChildren().add(testEnemy);

		//System.out.println(timeCounter);

		//Testing powerups


		//Enemy actions
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).fire();
		}
		if(timeCounter%100 == 0 && enemies.size() < 1){
			FlyingShooter newShooter = new FlyingShooter(1,1,plyr.getHitbox());
			newShooter.setX(50);
			newShooter.setY(50);
			enemies.add(newShooter);
			pane.getChildren().add(newShooter);

		}

		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).rangedTrackMove(map.getCurrentArea(),plyr.getHitbox(),20);
			if(enemies.get(i).collides(plyr)){
				plyr.takeDamage();
			}

			//Enemy bullets collide w/ objects
			for(int j = 0; j < enemies.get(i).getBullets().size(); j++){
				Rectangle bulletHitbox = enemies.get(i).getBullets().get(j).getHitbox();
				if(enemies.get(i).getBullets().get(j).collides(plyr)){
					enemies.get(i).getChildren().remove(j+1);
					enemies.get(i).getBullets().remove(j);
					plyr.takeDamage();
				}

				if(bulletHitbox.getX() < 0 || bulletHitbox.getY() < 0 || bulletHitbox.getX() > 1600 || bulletHitbox.getY() > 900){
					enemies.get(i).getChildren().remove(j+1);
					enemies.get(i).getBullets().remove(j);
				}
			}
			enemies.get(i).increaseTimeCounter();
		}

		//playerBullets collide w/ objects
		ArrayList<Enemy> hitList = new ArrayList<Enemy>();
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
			pane.getChildren().remove(e);
			enemies.remove(e);
		}
		hitList.clear();

		//Player collide w/ objects
		for(int i = 0; i < powerups.size(); i++){
			if(plyr.collides(powerups.get(i))){
				if(powerups.get(i).upgrade(plyr)){
					pane.getChildren().remove(powerups.get(i));
					powerups.remove(i);
				}
			}
		}

		timeCounter++;
	}
	private void checkDoors(){
		String dir=map.checkDoorCollision(plyr.getHitbox());
		if(dir!=null){
			map.moveRooms(dir);
			//might be a good idea to change the numbers to actual variable getters
			if(dir.equals("right")){
				plyr.setX(30);
				plyr.setY(400);
			}
			if(dir.equals("left")){
				plyr.setX(720);
				plyr.setY(400);
			}
			if(dir.equals("top")){
				plyr.setX(400);
				plyr.setY(720);
			}
			if(dir.equals("bottom")){
				plyr.setX(400);
				plyr.setY(30);
			}
		}
	}
	private void checkWalls(){
		String wallsHit="";
		for(Rectangle r : map.getCurrentArea().getWalls()){
			if(plyr.getHitbox().getY()+plyr.getHitbox().getHeight()>r.getY() && plyr.getHitbox().getY()<r.getY()+r.getHeight()){
					if(plyr.getHitbox().getX()-plyr.getXSpd()*plyr.getSpdMultiplier()< r.getX()+r.getWidth() && plyr.getHitbox().getX()>=r.getX()+r.getWidth()){
						wallsHit+="left";
						if(plyr.getMovingW()){
							plyr.setX(r.getX()+r.getWidth());
						}
					}
					if(plyr.getHitbox().getX()+plyr.getHitbox().getWidth()+plyr.getXSpd()*plyr.getSpdMultiplier()> r.getX() && plyr.getHitbox().getX()+plyr.getHitbox().getWidth()<=r.getX()){
						wallsHit+="right";
						if(plyr.getMovingE()){
							plyr.setX(r.getX()-plyr.getHitbox().getWidth());
						}
					}
			}
			if(plyr.getHitbox().getX()+plyr.getHitbox().getWidth()>r.getX() && plyr.getHitbox().getX()<r.getX()+r.getWidth()){
					if(plyr.getHitbox().getY()-plyr.getYSpd()*plyr.getSpdMultiplier()< r.getY()+r.getHeight() && plyr.getHitbox().getY()>=r.getY()+r.getHeight()){
						wallsHit+="top";
						if(plyr.getMovingN()){
							plyr.setY(r.getY()+r.getHeight());
						}
					}
					if(plyr.getHitbox().getY()+plyr.getHitbox().getHeight()+plyr.getYSpd()*plyr.getSpdMultiplier()> r.getY() && plyr.getHitbox().getY()+plyr.getHitbox().getHeight()<=r.getY()){
						wallsHit+="bottom";
						if(plyr.getMovingS()){
							plyr.setY(r.getY()-plyr.getHitbox().getHeight());
						}
					}
			}
		}
		if(wallsHit.equals("")){
			plyr.setHittingWall(null);
		}else{
			plyr.setHittingWall(wallsHit);
		}

	}
	private void enemyCheckWalls(){
		for(Enemy e: enemies){
			String wallsHit="";
			for(Rectangle r : map.getCurrentArea().getWalls()){
				if(e.getHitbox().getY()+e.getHitbox().getHeight()>=r.getY() && e.getHitbox().getY()<=r.getY()+r.getHeight()){
						if(e.getHitbox().getX()-Math.abs(e.getXSpd())*e.getSpdMultiplier()<= r.getX()+r.getWidth() && e.getHitbox().getX()>=r.getX()+r.getWidth()){
							wallsHit+="left";
							e.getHitbox().setX(r.getWidth()+r.getX());
						}
						if(e.getHitbox().getX()+e.getHitbox().getWidth()+e.getXSpd()*e.getSpdMultiplier()>= r.getX() && e.getHitbox().getX()+e.getHitbox().getWidth()<=r.getX()){
							wallsHit+="right";
							e.getHitbox().setX(r.getX()-e.getHitbox().getWidth());
						}
				}
				if(e.getHitbox().getX()+e.getHitbox().getWidth()>=r.getX() && e.getHitbox().getX()<=r.getX()+r.getWidth()){
						if(e.getHitbox().getY()-Math.abs(e.getYSpd())*e.getSpdMultiplier()<= r.getY()+r.getHeight() && (e.getHitbox().getY()>=r.getY()+r.getHeight())){
							wallsHit+="top";
							e.getHitbox().setY(r.getHeight()+r.getY());
						}
						if(e.getHitbox().getY()+e.getHitbox().getHeight()+Math.abs(e.getYSpd())*e.getSpdMultiplier()>= r.getY() && e.getHitbox().getY()+e.getHitbox().getHeight()<=r.getY()){
							wallsHit+="bottom";
							e.getHitbox().setY(r.getY()-e.getHitbox().getHeight());
						}

				}
			}
			if(!wallsHit.equals("")) System.out.println(wallsHit);
			e.setHittingWall(wallsHit);
		}
	}
}
