import java.util.*;
import java.io.*;
import javafx.animation.AnimationTimer;
import javafx.scene.shape.*;
import javafx.animation.AnimationTimer;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.input.KeyCode;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
public class Player extends Sprite{

	int health;
	int maxHealth;
	int meleeDmg;

	AnimationTimer timer;
	boolean movingN,movingE,movingW,movingS;
	boolean shootingN,shootingE,shootingW,shootingS;
	String hittingWall;
	Projectile bulletType;
	ArrayList<Projectile> bullets;


	int delay =0;
	int delayResetTimer=0;

	int immuneCounter;

	public Player(double x, double y, double w, double h, double xv, double yv, double multiplier){

		super(xv,yv);
		maxHealth = 5;
		health = maxHealth;
		hitbox = new Rectangle(x,y,w,h);
		movingN=false; movingE=false; movingW= false; movingS=false;
		shootingN=false; shootingE=false; shootingW= false; shootingS=false;
		spdMultiplier = multiplier;
		hittingWall=null;

		//this.hitbox = new Rectangle(10,10,10,10);
		//this.hitbox.setFill(Color.RED);
		//getChildren().add(this.hitbox);


		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
               getInput();
			   move();
			   for(Projectile p : bullets){
				   p.move();
			   }
               shoot();
			   countdownImmunity();
            }
        };
        timer.start();
        getChildren().add(hitbox);


        bulletType = new SingleShot(0,0,1,1);

        bullets= new ArrayList<Projectile>();

	}

	public int getMaxHealth(){
		return this.maxHealth;
	}

	public int getHealth(){
		return this.health;
	}

	public int getMeleeDmg(){
		return this.meleeDmg;
	}

	public ArrayList<Projectile> getBullets(){
		return this.bullets;
	}

	public Projectile getBulletType(){
		return this.bulletType;
	}

	public void setMaxHealth(int maxHealth){
		this.maxHealth = maxHealth;
	}

	public void setHealth(int health){
		this.health = health;
	}

	public void setMeleeDmg(int meleeDmg){
		this.meleeDmg = meleeDmg;
	}

	public boolean isImmune(){
		return immuneCounter > 0;
	}

	public void setImmune(){
		immuneCounter = 60;
	}
	public void setHittingWall(String b){
		hittingWall=b;
	}
	public String getHittingWall(){
		return hittingWall;
	}
	private void countdownImmunity(){
		if(immuneCounter > 0){
			if(immuneCounter % 20 == 0){
				hitbox.setFill(Color.WHITE);
			}else if(immuneCounter % 10 == 0 || immuneCounter == 1){
				hitbox.setFill(Color.BLACK);
			}
			immuneCounter--;
		}
	}

	public void takeDamage(){
		if(getHealth() >= 1 && !(isImmune())){
			setHealth(getHealth()-1);
			if(getHealth() <= 0){

			}else{
				setImmune();
				System.out.println(this);
			}
		}
	}

	private void getInput(){
	 	setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                   if(event.getCode().equals(KeyCode.W)) movingN = true;
                   if(event.getCode().equals(KeyCode.S)) movingS = true;
                   if(event.getCode().equals(KeyCode.D)) movingE = true;
                   if(event.getCode().equals(KeyCode.A)) movingW= true;
                   if(event.getCode().equals(KeyCode.UP)) shootingN=true;
                   if(event.getCode().equals(KeyCode.DOWN)) shootingS=true;
                   if(event.getCode().equals(KeyCode.RIGHT)) shootingE=true;
                   if(event.getCode().equals(KeyCode.LEFT)) shootingW=true;

            }
        });

        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
               if(event.getCode().equals(KeyCode.W)) movingN = false;
               if(event.getCode().equals(KeyCode.S)) movingS = false;
               if(event.getCode().equals(KeyCode.D)) movingE = false;
               if(event.getCode().equals(KeyCode.A)) movingW= false;
            	if(event.getCode().equals(KeyCode.UP)) shootingN=false;
                if(event.getCode().equals(KeyCode.DOWN)) shootingS=false;
                if(event.getCode().equals(KeyCode.RIGHT)) shootingE=false;
                if(event.getCode().equals(KeyCode.LEFT)) shootingW=false;
            }
        });
    }

	public void move(){
		//need this here in order to refresh the moving input, otherwise holding key breaks if you run into a wall
		boolean wasMovingN=movingN;
		boolean wasMovingW=movingW;
		boolean wasMovingE=movingE;
		boolean wasMovingS=movingS;
		if(hittingWall!=null){
			System.out.println(hittingWall);
			if(hittingWall.indexOf("right")!=-1){
				movingE=false;
			}
			if(hittingWall.indexOf("left")!=-1){
				movingW=false;
			}
			if(hittingWall.indexOf("top")!=-1){
				movingN=false;
			}
			if(hittingWall.indexOf("bottom")!=-1){
				movingS=false;
			}
		}
			if(movingN && !movingE && !movingW){ hitbox.setY(hitbox.getY()-ySpd*spdMultiplier); }
			if(movingS && !movingE && !movingW){ hitbox.setY(hitbox.getY()+ySpd*spdMultiplier); }
			if(movingE && !movingN && !movingS){ hitbox.setX(hitbox.getX()+xSpd*spdMultiplier); }
			if(movingW && !movingN && !movingS){ hitbox.setX(hitbox.getX()-xSpd*spdMultiplier); }
			if(movingN && movingE &&!movingW){
				hitbox.setY(hitbox.getY()-(ySpd/Math.sqrt(2))*spdMultiplier);
				hitbox.setX(hitbox.getX()+(xSpd/Math.sqrt(2))*spdMultiplier);
			}
			if(movingN && movingW && !movingE){
				hitbox.setY(hitbox.getY()-(ySpd/Math.sqrt(2))*spdMultiplier);
				hitbox.setX(hitbox.getX()-(xSpd/Math.sqrt(2))*spdMultiplier);
			}
			if(movingS && movingE && !movingW){
				hitbox.setY(hitbox.getY()+(ySpd/Math.sqrt(2))*spdMultiplier);
				hitbox.setX(hitbox.getX()+(xSpd/Math.sqrt(2))*spdMultiplier);
			}
			if(movingS && movingW && !movingE){
				hitbox.setY(hitbox.getY()+(ySpd/Math.sqrt(2))*spdMultiplier);
				hitbox.setX(hitbox.getX()-(xSpd/Math.sqrt(2))*spdMultiplier);
			}
			if (movingN && movingE && movingW){
				hitbox.setY(hitbox.getY()-ySpd*spdMultiplier);
			}
			if (movingS && movingE && movingW){
				hitbox.setY(hitbox.getY()+ySpd*spdMultiplier);
			}
			if (movingE && movingN && movingS){
				hitbox.setX(hitbox.getX()+xSpd*spdMultiplier);
			}
			if (movingW && movingN && movingS){
				hitbox.setX(hitbox.getX()-xSpd*spdMultiplier);
			}
			movingN=wasMovingN;
			movingS=wasMovingS;
			movingE=wasMovingE;
			movingW=wasMovingW;
	}

	private void shoot(){
	//replace "5" with projectile.getFireRate();
		if(delay==bulletType.getFireRate()){delay=0;}
		if(!(shootingN||shootingS||shootingW||shootingE)){
			//delayResetTimer prevents repeated tapping vs holding down
			delayResetTimer++;
			if(delayResetTimer==bulletType.getFireRate()){
				delay=0;
				delayResetTimer=0;
			}
		}else if(delay==0){
			if(shootingN){
				//System.out.println("North shot");
				//how to determine what shot to create? possible have hard checker for type
				//maybe have a method which makes a copy for the projectile type, then feeds it to here and this only changes vx,vy,x,y, etc.

				Projectile shot = new SingleShot(hitbox.getX()+0.5*hitbox.getWidth(),hitbox.getY(),0,-10);
				//Projectile shot=makeBulletCopy(hitbox.getX()+0.5*hitbox.getWidth(),hitbox.getY(),0.0,-10.0);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			else if(shootingS){
				//System.out.println("South shot");

				Projectile shot = new SingleShot(hitbox.getX()+0.5*hitbox.getWidth(),hitbox.getY()+hitbox.getHeight(),0,10);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			else if(shootingW){
				//System.out.println("West shot");

				Projectile shot = new SingleShot(hitbox.getX(),hitbox.getY()+0.5*hitbox.getHeight(),-10,0);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			else if(shootingE){
				//System.out.println("East shot");

				Projectile shot = new SingleShot(hitbox.getX()+hitbox.getWidth(),hitbox.getY()+0.5*hitbox.getHeight(),10,0);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}


		}else{
			delay++;
		}

	}

	public String toString(){
		return "Max HP : " + getMaxHealth() + "\nHP : " + getHealth() + "\nDamage : " + bulletType.getDamage() + "\nMeleeDmg : " + getMeleeDmg() + "\nSpeed : " + getSpdMultiplier() + "\n\n";
	}

}
