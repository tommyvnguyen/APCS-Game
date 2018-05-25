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
import javafx.scene.control.Label;
import java.util.*;
public class Game extends Scene{
	int timeCounter;
	Player plyr;
	ArrayList<Enemy> enemies;
	ArrayList<Powerup> powerups;
	AnimationTimer timer;
	Pane gamePane;
	StatsPane statsPane;
	BorderPane rootPane;
	Map map;
	Menu menu;
	int score;
	private void startGame(){
		timer.start();
		rootPane.setCenter(gamePane);
	}
	Game(){
		super(new BorderPane(), 800, 950);
		menu = new Menu();
		rootPane = (BorderPane)getRoot();
		gamePane = new Pane();
		gamePane.setPrefSize(800,800);
		gamePane.setStyle("-fx-border: single; -fx-border-color: black; -fx-border-width: 5px;");

		rootPane.setCenter(menu);
		score = -1;
		AnimationTimer tempTimer = new AnimationTimer(){
		@Override
			public void handle(long now){
				if(menu.getStartGame()){
					startGame();
					menu.setStartGame(false);
				}
			}
		};
		tempTimer.start();

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


		 timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				run();
				checkWalls();
				checkDoors();
				enemyCheckWalls();
				spawnMobs();
				plyr.updateSprite();
				checkDeath();
				for(Enemy e : enemies){
						e.updateSprite();
						if((e instanceof FlyingShooter) || e instanceof Vomiter ){
							Shooter s = (Shooter)e;
							for(Projectile p : s.getBullets()){
								p.updateSprite();
							}
						}
				}
				for(Projectile p : plyr.getBullets()){
					p.updateSprite();
				}
			}
		};
		timer.start();
		onKeyPressedProperty().bind(plyr.onKeyPressedProperty());
		onKeyReleasedProperty().bind(plyr.onKeyReleasedProperty());
	}

	public void run(){

    //Enemy actions
		for(int i = 0; i < enemies.size(); i++){
		//	enemies.get(i).track();
			if(enemies.get(i) instanceof FlyingShooter){
				enemies.get(i).rangedTrackMove(map.getCurrentArea(),plyr.getHitbox(),300);
			}else{
				enemies.get(i).move();
			}
		}
		ArrayList<Enemy> hitList = new ArrayList<Enemy>();
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getTimeCounter() > 50){
				/* CHANGE
					THIS
					LINE TO CHECK FOR EACH ENEMY */
				//enemies.get(i).move();
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
					for(int j= shooter.getBullets().size()-1; j>=0; j--){
						if(shooter.getBullets().get(j) instanceof Laser){
							Laser temp = (Laser)shooter.getBullets().get(j);
							temp.increaseTimeCounter();
						}
						Projectile bullet = shooter.getBullets().get(j);
						if(shooter.getBullets().get(j).collides(plyr)){
							plyr.takeDamage();
							if(!(plyr.isImmune())){
								if(!(shooter.getBullets().get(j) instanceof Laser)){
									shooter.getChildren().remove(shooter.getBullets().get(j));
									shooter.getBullets().remove(j);
								}
								plyr.setImmune();
								System.out.println(plyr);
								statsPane.update();
							}


						}else{
							boolean removed =false;
							for(Rectangle wall : map.getCurrentArea().getWalls()){
								if(bullet.getHitbox().intersects(wall.getX(),wall.getY(),wall.getWidth(),wall.getHeight()) && !(shooter.getBullets().get(j) instanceof Laser) && !removed){
									shooter.getChildren().remove(shooter.getBullets().get(j));
									shooter.getBullets().remove(j);
									removed =true;
								}

							}
							if(!removed){
								if(shooter.getBullets().get(j) instanceof Laser){
									Laser laser = (Laser)shooter.getBullets().get(j);
									if(laser.getTimeCounter() >= 125){
										shooter.getChildren().remove(shooter.getBullets().get(j));
										shooter.getBullets().remove(j);
									}

								}
							}
						}

					}

				}
			}

			enemies.get(i).increaseTimeCounter();

		}

		//playerBullets collide w/ objects
		for(int i = plyr.getBullets().size()-1; i >=0 ; i--){
			boolean removed=false;
			for(int j = enemies.size()-1; j >=0 && !removed;  j--){
				if(plyr.getBullets().size()>0){
					if(plyr.getBullets().get(i).collides(enemies.get(j))){
						if(enemies.get(j).decreaseHealth(plyr.getBulletType().getDamage()) <= 0){
							hitList.add(enemies.get(j));
						}
						plyr.getChildren().remove(plyr.getBullets().get(i));
						plyr.getBullets().remove(i);
						removed =true;
					}
				}
			}
			//index error occurs when there are bullets out already and a newer bullet is removed
			if(!removed){
				for(Rectangle wall : map.getCurrentArea().getWalls()){
						if(plyr.getBullets().size()>0 && !removed){
							if(plyr.getBullets().get(i).getHitbox().intersects(wall.getX(),wall.getY(),wall.getWidth(),wall.getHeight()) &&!removed){
								plyr.getChildren().remove(plyr.getBullets().get(i));
								plyr.getBullets().remove(i);
								removed = true;
							}
						}
				}
			}
		}
		for(Enemy e : hitList){
			double healthDropChance = Math.random();
			double damageDropChance = Math.random();
			double poisonDropChance=Math.random();
			double speedDropChance = Math.random();
			if(e instanceof Vomiter){
				poisonDropChance=100;
			}
			if(e instanceof Chaser){
				healthDropChance+=0.10;
			}
			if(damageDropChance >= 0.95){
				DamagePowerup dup = new DamagePowerup(e.getHitbox().getX(),e.getHitbox().getY());
				powerups.add(dup);
				gamePane.getChildren().add(dup);
			}
			else if(poisonDropChance>=0.9){
				PoisonPowerup dup = new PoisonPowerup(e.getHitbox().getX(),e.getHitbox().getY());
				powerups.add(dup);
				gamePane.getChildren().add(dup);
			}
			else if(speedDropChance>=0.9){
				SpdPowerup spd = new SpdPowerup(e.getHitbox().getX(),e.getHitbox().getY());
				powerups.add(spd);
				gamePane.getChildren().add(spd);
			}
			else	if(healthDropChance >= 0.7){
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

	private void checkDoors(){
		String dir=map.checkDoorCollision(plyr.getHitbox());
		if(dir!=null && map.getCurrentArea().getCompleted()){
			map.moveRooms(dir);
			clearPowerUps();
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
				int exactCorners=0;
				if(e.getHitbox().getY()-Math.abs(e.getYSpd())*e.getSpdMultiplier()== r.getY()+r.getHeight() && (e.getHitbox().getY()==r.getY()+r.getHeight())){
					exactCorners++;
				}
				if(e.getHitbox().getY()+e.getHitbox().getHeight()+Math.abs(e.getYSpd())*e.getSpdMultiplier()>= r.getY() && e.getHitbox().getY()+e.getHitbox().getHeight()<=r.getY()){
					exactCorners++;
				}
				if(e.getHitbox().getX()-Math.abs(e.getXSpd())*e.getSpdMultiplier()<= r.getX()+r.getWidth() && e.getHitbox().getX()>=r.getX()+r.getWidth()){
					exactCorners++;
				}
				if(e.getHitbox().getX()+e.getHitbox().getWidth()+e.getXSpd()*e.getSpdMultiplier()>= r.getX() && e.getHitbox().getX()+e.getHitbox().getWidth()<=r.getX()){
					exactCorners++;
				}
				if(exactCorners>=2){
					wallsHit="";
				}
			}
			e.setHittingWall(wallsHit);
		}
	}
	private void spawnMobs(){
		if(!map.getCurrentArea().getCompleted() && !map.getCurrentArea().getSpawned()){
			map.getCurrentArea().setSpawned(true);

			if(map.getCurrentArea() instanceof PillarArea){
				for(int i = 0; i<5;i++){
					FlyingShooter fs = new FlyingShooter(1,1,plyr.getHitbox());
					fs.getHitbox().setX(430); //175-225
					fs.getHitbox().setY(225+ i*100);
					enemies.add(fs);
					gamePane.getChildren().add(fs);
				}
			}

			if(map.getCurrentArea() instanceof OpenArea){
					LaserShooter ls = new LaserShooter(0,0,plyr.getHitbox());
					ls.getHitbox().setX(400); ls.getHitbox().setY(400);
					enemies.add(ls);
					gamePane.getChildren().add(ls);
					Chaser e1 = new Chaser(1,1,plyr.getHitbox());
					e1.getHitbox().setX(450); e1.getHitbox().setY(400);
					Chaser e2 = new Chaser(1,1,plyr.getHitbox());
					e2.getHitbox().setX(350); e2.getHitbox().setY(400);
					Chaser e3 = new Chaser(1,1,plyr.getHitbox());
					e3.getHitbox().setX(400); e3.getHitbox().setY(450);
					Chaser e4 = new Chaser(1,1,plyr.getHitbox());
					e4.getHitbox().setX(400); e4.getHitbox().setY(350);
					Collections.addAll(enemies,e1,e2,e3,e4);
					gamePane.getChildren().addAll(e1,e2,e3,e4);
			}
			if(map.getCurrentArea() instanceof WallyArea){
				LaserShooter ls = new LaserShooter(0,0,plyr.getHitbox());
				ls.getHitbox().setX(325); ls.getHitbox().setY(325);
				enemies.add(ls);
				gamePane.getChildren().add(ls);
				ls = new LaserShooter(1,1,plyr.getHitbox());
				ls.getHitbox().setX(425); ls.getHitbox().setY(325);
				enemies.add(ls);
				gamePane.getChildren().add(ls);
				 ls = new LaserShooter(0,0,plyr.getHitbox());
				ls.getHitbox().setX(325); ls.getHitbox().setY(425);
				enemies.add(ls);
				gamePane.getChildren().add(ls);
				 ls = new LaserShooter(0,0,plyr.getHitbox());
				ls.getHitbox().setX(425); ls.getHitbox().setY(425);
				enemies.add(ls);
				gamePane.getChildren().add(ls);
				FlyingShooter fs = new FlyingShooter(1,1,plyr.getHitbox());
				fs.getHitbox().setX(30);fs.getHitbox().setY(30);
				enemies.add(fs);
				gamePane.getChildren().add(fs);
				fs = new FlyingShooter(1,1,plyr.getHitbox());
				fs.getHitbox().setX(730);fs.getHitbox().setY(730);
				enemies.add(fs);
				gamePane.getChildren().add(fs);
			}
			if(map.getCurrentArea() instanceof FinalBossArea){
				Machine s1 = new Machine(1,1,plyr.getHitbox());
					enemies.add(s1);
					gamePane.getChildren().add(s1);
					s1.getHitbox().setX(400);
					s1.getHitbox().setY(400);
			}
			if(map.getCurrentArea() instanceof MidBossArea){
				Vomiter s1 = new Vomiter(0,0,plyr.getHitbox());
					enemies.add(s1);
					gamePane.getChildren().add(s1);
					s1.getHitbox().setX(362.5);
					s1.getHitbox().setY(362.5);
			}
		}
		if(!map.getCurrentArea().getCompleted()&&enemies.size()==0){
			map.getCurrentArea().setCompleted(true);
			score++;
		}


	}
	private void clearPowerUps(){
		for(int i=powerups.size()-1; i>=0; i--){
			gamePane.getChildren().remove(powerups.get(i));
			powerups.remove(i);
		}
	}
	private void checkDeath(){
		if(plyr.getHealth()<=0){
			//timer.stop();
			rootPane.setCenter(menu);
			menu.receiveScore(score);
			timer.stop();
			resetGame();
		}
	}
	private void resetGame(){
		score=-1;
		gamePane.getChildren().removeAll(gamePane.getChildren());
		map= new Map(20);
		gamePane.getChildren().add(map);
		while(enemies.size()>0){
			enemies.remove(0);
		}
		plyr.setHealth(plyr.getMaxHealth());
		plyr.setMeleeDmg(0);
		plyr.setSpdMultiplier(4);
		plyr.getBulletType().setDamage(1);
		gamePane.getChildren().add(plyr);
		statsPane.update();
	}
}
