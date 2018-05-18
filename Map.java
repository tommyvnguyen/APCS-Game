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
public class Map extends Area {
	Pane minimap;
	ArrayList<Area> areas = new ArrayList<Area>(); //coordinate system IS TRADITIONAL EUCLIDIAN SPACE
	final int NUM_OF_AREA_TYPES = 2;
	public Map(int size){ 
		super(0,0);
		generateNewMap();
		
	}
	private void generateNewMap(){
		SpawnArea spawn = new SpawnArea(0,0);
		areas.add(spawn);
		this=spawn;
		generateMainBranch("right");
	}
	
	//takes "right", "left", "up", "down"
	private void generateMainBranch(String direction){
		int modifier = 1;
		if(direction.equals("left")||direction.equals("down")){
			modifier=-1;
		}
		for(i=1; i<10 && i!=5; i++){
			if(direction.equals("up") || direction.equals("down")){
				areas.add(randomArea(0,i*modifier));
			}else{
				areas.add(randomArea(i*modifer,0));
			}
		}
		if(direction.equals("up") || direction.equals("down")){
			areas.add(new MidBossArea(0,5*modifier));
			areas.add(new FinalBossArea(0,10*modifier));
		}else{
			areas.add(new MidBossArea(5*modifier,0));
			areas.add(new FinalBossArea(10*modifier,0));
		}
	}
	
	//takes in a coordinate and creates a random area from possible selection
	private Area randomArea(int x, int y){
		int index = (int)Math.random()*NUM_OF_AREA_TYPES +1;
		switch(index) { //add in a switch statement for each new area type
			case 1: areas.add(new WallyArea(x,y));
					break;
			case 2: areas.add(new OpenArea(x,y));
					break;
		}
	}
	
	//moves player and returns true if move is successful
	//use the boolean to determine whether to move the player or not
	private boolean moveRooms(String direction){
		if(direction.equals("right") || direction.equals("left")){
			int modifier = 1;
			if(direction.equals("left")){
				modifier= -1;
			}
			for(Area a : areas){
				if(a.getXCoord()==this.getXCoord()+1*modifier && a.getYCoord()==this.getYCoord()){
					this = a;
				}
			}
		}
		if(direction.equals("top") || direction.equals("bottom")){
			int modifier = 1;
			if(direction.equals("bottom")){
				modifier= -1;
			}
			for(Area a : areas){
				if(a.getYCoord()==this.getYCoord()+1*modifier && a.getXCoord()==this.getXCoord()){
					this = a;
				}
			}
		}
	}
}