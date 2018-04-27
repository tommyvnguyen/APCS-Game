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
	
	boolean movingN;
	boolean movingE;
	boolean movingW;
	boolean movingS;
	
	public Player(Pane outerPane, double x, double y, double w, double h, double xv, double yv){
		super(outerPane,xv,yv);
		plyr = new Rectangle(x,y,w,h);
		movingN=false; movingE=false; movingW= false; movingS=false;
		
		this.setStyle("-fx-background-color: blue");
		
		timer = new AnimationTimer() {
            @Override
            public void handle(long now) {

               getInput();
               move();
            }
        };
        timer.start();
        getChildren().add(plyr);
        
        
	}
	
	private void getInput(){
	 	setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                   if(event.getCode().equals(KeyCode.UP)) movingN = true;
                   if(event.getCode().equals(KeyCode.DOWN)) movingS = true; 
                   if(event.getCode().equals(KeyCode.RIGHT)) movingE = true;
                   if(event.getCode().equals(KeyCode.LEFT)) movingW= true;
                
            }
        });

        setOnKeyReleased(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
               if(event.getCode().equals(KeyCode.UP)) movingN = false;
               if(event.getCode().equals(KeyCode.DOWN)) movingS = false; 
               if(event.getCode().equals(KeyCode.RIGHT)) movingE = false;
               if(event.getCode().equals(KeyCode.LEFT)) movingW= false;
            }
        });
    } 
	
	public void move(){
		if(movingN){ plyr.setY(plyr.getY()-ySpd); }
		if(movingS){ plyr.setY(plyr.getY()+ySpd); }
		if(movingE){ plyr.setX(plyr.getX()+xSpd); }
		if(movingW){ plyr.setX(plyr.getX()-xSpd); }
	}
	
}