import javafx.scene.text.Text;
import java.util.*;
import java.io.*;
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
import javafx.scene.control.Label;
public class MapTester extends Application{

			private final long[] frameTimes = new long[100];
  		private int frameTimeIndex = 0 ;
    	private boolean arrayFilled = false ;

	public static void main(String [] args){
		launch(args);
	}
	public void start(Stage stage){

		Pane root = new Pane();
		Player plyr = new Player(200,200,50,50,4,4,2);

		Map m = new Map(20);
		root.getChildren().addAll(m,plyr);


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
		root.getChildren().add(new StackPane(label));


//this is the basic code for moving players to the correct position when transfering rooms
		AnimationTimer timer = new AnimationTimer() {
	      @Override
	      public void handle(long now) {
					String dir=m.checkDoorCollision(plyr.getHitbox());
					if(dir!=null) System.out.println(dir);
					if(dir!=null){
	       		m.moveRooms(dir);
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
    };
    timer.start();

		stage.setScene(new Scene(root, 800,800));
		stage.getScene().onKeyPressedProperty().bind(plyr.onKeyPressedProperty());
		stage.getScene().onKeyReleasedProperty().bind(plyr.onKeyReleasedProperty());
		stage.show();
	}

}
