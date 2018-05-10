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
	
	AnimationTimer timer;
	boolean movingN,movingE,movingW,movingS;
	boolean shootingN,shootingE,shootingW,shootingS;
	
	Projectile bulletType;
	ArrayList<Projectile> bullets;
	
	
	int delay =0;
	int delayResetTimer=0;

	public Player(double x, double y, double w, double h, double xv, double yv){
		super(xv,yv);
		hitbox = new Rectangle(x,y,w,h);
		movingN=false; movingE=false; movingW= false; movingS=false;
		shootingN=false; shootingE=false; shootingW= false; shootingS=false;
		
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
            }
        };
        timer.start();
        getChildren().add(hitbox);
        

        bulletType = new SingleShot(0,0,1,1);

        bullets= new ArrayList<Projectile>();
        
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
    if(movingN && !movingE && !movingW){ hitbox.setY(hitbox.getY()-ySpd); }
		if(movingS && !movingE && !movingW){ hitbox.setY(hitbox.getY()+ySpd); }
		if(movingE && !movingN && !movingS){ hitbox.setX(hitbox.getX()+xSpd); }
		if(movingW && !movingN && !movingS){ hitbox.setX(hitbox.getX()-xSpd); }
		if(movingN && movingE &&!movingW){ 
			hitbox.setY(hitbox.getY()-(ySpd/Math.sqrt(2)));
			hitbox.setX(hitbox.getX()+(xSpd/Math.sqrt(2)));
		} 
		if(movingN && movingW && !movingE){ 
			hitbox.setY(hitbox.getY()-(ySpd/Math.sqrt(2)));
			hitbox.setX(hitbox.getX()-(xSpd/Math.sqrt(2)));
		}
		if(movingS && movingE && !movingW){ 
			hitbox.setY(hitbox.getY()+(ySpd/Math.sqrt(2)));
			hitbox.setX(hitbox.getX()+(xSpd/Math.sqrt(2)));
		}
		if(movingS && movingW && !movingE){ 
			hitbox.setY(hitbox.getY()+(ySpd/Math.sqrt(2)));
			hitbox.setX(hitbox.getX()-(xSpd/Math.sqrt(2)));
		}
		if (movingN && movingE && movingW){
			hitbox.setY(hitbox.getY()-ySpd);
		}
		if (movingS && movingE && movingW){
			hitbox.setY(hitbox.getY()+ySpd);
		}
		if (movingE && movingN && movingS){
			hitbox.setX(hitbox.getX()+xSpd);
		}
		if (movingW && movingN && movingS){
			hitbox.setX(hitbox.getX()-xSpd);
		}
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
				System.out.println("North shot");
				//how to determine what shot to create? possible have hard checker for type
				//maybe have a method which makes a copy for the projectile type, then feeds it to here and this only changes vx,vy,x,y, etc.

				Projectile shot = new SingleShot(hitbox.getX()+0.5*hitbox.getWidth(),hitbox.getY(),0,-10);
				//Projectile shot=makeBulletCopy(hitbox.getX()+0.5*hitbox.getWidth(),hitbox.getY(),0.0,-10.0);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			else if(shootingS){
				System.out.println("South shot");

				Projectile shot = new SingleShot(hitbox.getX()+0.5*hitbox.getWidth(),hitbox.getY()+hitbox.getHeight(),0,10);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			else if(shootingW){
				System.out.println("West shot");

				Projectile shot = new SingleShot(hitbox.getX(),hitbox.getY()+0.5*hitbox.getHeight(),-10,0);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			else if(shootingE){
				System.out.println("East shot");

				Projectile shot = new SingleShot(hitbox.getX()+hitbox.getWidth(),hitbox.getY()+0.5*hitbox.getHeight(),10,0);

				getChildren().add(shot);
				bullets.add(shot);
				delay++;
			}
			
			
		}else{
			delay++;
		}

	}
	//private Projectile makeBulletCopy(double x,double y, double vx, double vy){
	//	Projectile copy = new Projectile(this,x,y,vx,vy);
	//	ArrayList<Node> copiedNodes = new ArrayList<Node>();
	//	for(Node n: bulletType.getChildren()){
	//		
	//	}
	//	copy.getChildren().addAll(copiedNodes);
	//	copy.setFireRate(bulletType.getFireRate());
	//	copy.setDamage(bulletType.getDamage());
	//	return copy;
	//}
	//
	
	
}