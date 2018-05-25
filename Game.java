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
import java.util.ArrayList;
public class Game extends Scene{
	int timeCounter;
	Player plyr;
	ArrayList<Enemy> enemies;
	ArrayList<Powerup> powerups;
	AnimationTimer timer;
	
	boolean isActive;

	Pane gamePane;
	StatsPane statsPane;
	BorderPane rootPane;
	Pane menuPane;
	Map map;
															private final long[] frameTimes = new long[100];
														private int frameTimeIndex = 0 ;
														private boolean arrayFilled = false ;

	Game(){
		super(new Pane(), 800, 950);
		menuPane = (Pane)getRoot();
		Button button = new Button("Start");
		button.setPrefSize(100,50);
		button.setLayoutX(350);
		button.setLayoutY(375);
		button.setOnAction(new EventHandler<ActionEvent>() {
			@Override public void handle(ActionEvent e) {
				startGame();
			}
		});
		menuPane.getChildren().add(button);
		isActive = false;
		
		rootPane = new BorderPane();
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

		if(timeCounter%100 == 0 && enemies.size() < 1){
			FlyingShooter newShooter = new FlyingShooter(1,1,plyr.getHitbox());
			newShooter.setX(50);
			newShooter.setY(50);
			enemies.add(newShooter);
			gamePane.getChildren().add(newShooter);
		}

		//Machine s1 = new Machine(1,1,plyr.getHitbox());
		//enemies.add(s1);
		//gamePane.getChildren().add(s1);
		//s1.getHitbox().setX(600);
		//s1.getHitbox().setY(400);




		//Vomiter v1 = new Vomiter(1,1,plyr.getHitbox());
		//enemies.add(v1);
		//gamePane.getChildren().add(v1);
		//v1.getHitbox().setX(600);
		//v1.getHitbox().setY(400);

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
		//FlyingShooter s1 = new FlyingShooter(1,1,plyr.getHitbox());
		//s1.getHitbox().setX(400);
		//s1.getHitbox().setY(400);
		//enemies.add(s1);
		//gamePane.getChildren().add(s1);

		// --------------------------------------

		timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				run();
				checkWalls();
				checkDoors();
				enemyCheckWalls();
			}
		};
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
																gamePane.getChildren().add(new StackPane(label));

	}

	public void run(){
    //Enemy actions
		for(int i = 0; i < enemies.size(); i++){
			enemies.get(i).track();
			if(enemies.get(i) instanceof Shooter){
				enemies.get(i).rangedTrackMove(map.getCurrentArea(),plyr.getHitbox(),300);
			}
		}
		ArrayList<Enemy> hitList = new ArrayList<Enemy>();
		for(int i = 0; i < enemies.size(); i++){
			if(enemies.get(i).getTimeCounter() > 50){
				enemies.get(i).move();
				if(enemies.get(i).collides(plyr)){

					plyr.takeDamage();
					if(plyr.getHealth() <= 0){
							endGame();
						}
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
						Projectile bullet = shooter.getBullets().get(j);
						if(shooter.getBullets().get(j).collides(plyr)){
							plyr.takeDamage();
							if(plyr.getHealth() <= 0){
								endGame();
							}
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
							//if(!removed){
								if(shooter.getBullets().get(j) instanceof Laser){
									Laser laser = (Laser)shooter.getBullets().get(j);
									if(laser.getTimeCounter() >= 125){
										shooter.getChildren().remove(j+1);
										shooter.getBullets().remove(j);
									}
									laser.increaseTimeCounter();
								}
							//}
						}

					}

				}
			}
			enemies.get(i).increaseTimeCounter();

		}

		//playerBullets collide w/ objects
		for(int i = plyr.getBullets().size()-1; i >=0 ; i--){

			for(int j = enemies.size()-1; j >=0;  j--){
				//System.out.println("Check");
				if(plyr.getBullets().size()>0){
					if(plyr.getBullets().get(i).collides(enemies.get(j))){
						if(enemies.get(j).decreaseHealth(plyr.getBulletType().getDamage()) <= 0){
							hitList.add(enemies.get(j));
						}
						plyr.getChildren().remove(plyr.getBullets().get(i));
						plyr.getBullets().remove(i);

						break;
					}else{
						boolean removed = false;
						for(Rectangle wall : map.getCurrentArea().getWalls()){
								System.out.println("size:" +plyr.getBullets().size());
								if(plyr.getBullets().size()>0){
									if(plyr.getBullets().get(i).getHitbox().intersects(wall.getX(),wall.getY(),wall.getWidth(),wall.getHeight()) &&!removed){
										plyr.getChildren().remove(plyr.getBullets().get(i));
										plyr.getBullets().remove(i);
										removed = true;
									}
								}
						}
					}
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
	
	public void startGame(){
		//map = new Map(20);
		//plyr = new Player(200,200,50,50,1,1,4);
		//enemies = new ArrayList<Enemy>();
		//powerups = new ArrayList<Powerup>();
		//statsPane = new StatsPane(plyr);
		plyr.setHealth(5);
		plyr.setMaxHealth(5);
		plyr.setSpdMultiplier(4);
		plyr.setMeleeDmg(0);
		plyr.getBulletType().setDamage(1);
		plyr.setX(200);
		plyr.setY(200);
		setRoot(rootPane);
		timer.start();
		//isActive = true;
	}
	
	public void endGame(){
		gamePane.getChildren().remove(map);
		map = new Map(20);
		gamePane.getChildren().add(map);
		plyr.setHealth(5);
		plyr.setMaxHealth(5);
		plyr.setSpdMultiplier(4);
		plyr.setMeleeDmg(0);
		plyr.getBulletType().setDamage(1);
		plyr.setX(200);
		plyr.setY(200);
		timer.stop();
		setRoot(menuPane);
		//isActive = false;
	}
}
