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
	
	Rectangle plyr;
	AnimationTimer timer;
	boolean movingN,movingE,movingW,movingS;
	boolean shootingN,shootingE,shootingW,shootingS;
	Projectile bulletType;
	ArrayList<Projectile> bullets;
	
	
	int delay =0;
	int delayResetTimer=0;
	public Player(Pane outerPane, double x, double y, double w, double h, double xv, double yv){
		super(outerPane,xv,yv);
		plyr = new Rectangle(x,y,w,h);
		movingN=false; movingE=false; movingW= false; movingS=false;
		shootingN=false; shootingE=false; shootingW= false; shootingS=false;
		
		
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

               getInput();
               move();
               shoot();
            }
        };
        timer.start();
        getChildren().add(plyr);
        
        bulletType = new SingleShot(this,0,0,1,1);
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
		if(movingN){ plyr.setY(plyr.getY()-ySpd); }
		if(movingS){ plyr.setY(plyr.getY()+ySpd); }
		if(movingE){ plyr.setX(plyr.getX()+xSpd); }
		if(movingW){ plyr.setX(plyr.getX()-xSpd); }
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
			}
			else if(shootingS){
				System.out.println("South shot");
			}
			else if(shootingW){
				System.out.println("West shot");
			}
			else if(shootingE){
				System.out.println("East shot");
			}
			delay++;
		}else{
			delay++;
		}
	}
	
	
}