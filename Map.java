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
import javafx.util.Duration;
import javafx.scene.transform.Transform;
import javafx.scene.transform.Rotate;
import java.util.ArrayList;
public class Map {
	Pane minimap;
	ArrayList<Area> areas = new ArrayList<Area>();
	final int NUM_OF_AREA_TYPES = 4;
	final int BOSS_FREQUENCY = 5; //rounds before each boss spawn
	public Map(int size){ //how random is the generation? is there a guided path where you 
						  //know when you will get a box with multiple side dungeons? or
						  //is there true randomness where bosses could spawn right next
						  //to each other, e.g. can the map resemble a rectangle blob where 
						  //the player must go back into old rooms to finish exploring
						  //or does it resemble a straight line with branches where the player
						  //has the follow a main path but has a chance to go to side rooms to buy
						  //things or take extra boss fights?
						
		for(int i=0; i<size;i++){
			int generatedMapNum = (int)(Math.random()* NUM_OF_AREA_TYPES+1);
			/*if((i+1)%BOSS_FREQUENCY==0){
				generateBossMap();
			}*/
		}
	}
}