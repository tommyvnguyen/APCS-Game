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
public class Map extends Pane{
	Pane minimap;
	public Area currentArea;
	ArrayList<Area> areas = new ArrayList<Area>(); //coordinate system IS TRADITIONAL EUCLIDIAN SPACE
	final int NUM_OF_AREA_TYPES = 2;
	public Map(int size){
		generateNewMap();

	}

	public String checkDoorCollision(Rectangle hitbox){
			return currentArea.checkDoorCollision(hitbox);
	}
	private void generateNewMap(){
		SpawnArea spawn = new SpawnArea(0,0);
		spawn.addLeftDoor();spawn.addRightDoor();spawn.addTopDoor();spawn.addBottomDoor();
		areas.add(spawn);
		currentArea=spawn;
		getChildren().add(currentArea);
		generateMainBranch("right");
	}

	//takes "right", "left", "up", "down"
	private void generateMainBranch(String direction){
		int modifier = 1;
		if(direction.equals("left")||direction.equals("down")){
			modifier=-1;
		}
		for(int i=1; i<10; i++){
			if(i!=5){
				if(direction.equals("up") || direction.equals("down")){
					areas.add(randomArea(0,i*modifier));
				}else{
					areas.add(randomArea(i*modifier,0));
				}
			}
		}
		if(direction.equals("up") || direction.equals("down")){
			Area a = new MidBossArea(0,5*modifier);
			a.addRightDoor();
			areas.add(a);
			a=new FinalBossArea(0,10*modifier);
			a.addRightDoor();
			areas.add(a);
		}else{
			Area a = new MidBossArea(5*modifier,0);
			a.addRightDoor();
			areas.add(a);
			a= new FinalBossArea(10*modifier,0);
			a.addRightDoor();
			areas.add(a);
		}
		for(Area as : areas){
				System.out.println(as.getXCoord()+" " + as.getYCoord());
		}
	}

	//takes in a coordinate and creates a random area from possible selection
	private Area randomArea(int x, int y){
		int index = (int)(Math.random()*NUM_OF_AREA_TYPES +1);
		Area result= null;
		switch(index) { //add in a switch statement for each new area type
			case 1: result= new WallyArea(x,y);
					break;
			case 2: result= new OpenArea(x,y);
					break;
		}
		//CHANGE THIS LINE TO ADD CORECT DOORS
		result.addRightDoor();
		return result;
	}

	//SWITCHES THE AREA ONLY, DOESN'T ACTUALLY MOVE PLAYER
	//needs the collision to be checked elsewhere, use this only when you are sure the
	//player is ready to be moved.
	public void moveRooms(String direction){
		if(direction.equals("right") || direction.equals("left")){
			int modifier = 1;
			if(direction.equals("left")){
				modifier= -1;
			}
			for(Area a : areas){
				if(a.getXCoord()==currentArea.getXCoord()+1*modifier && a.getYCoord()==currentArea.getYCoord()){
					System.out.println("move success");
					currentArea = a;
					refreshArea();
					System.out.println(currentArea.getXCoord()+" " + currentArea.getYCoord());
					break;
				}
			}
		}
		if(direction.equals("top") || direction.equals("bottom")){
			int modifier = 1;
			if(direction.equals("bottom")){
				modifier= -1;
			}
			for(Area a : areas){
				if(a.getYCoord()==currentArea.getYCoord()+1*modifier && a.getXCoord()==currentArea.getXCoord()){
					currentArea = a;
					refreshArea();
					break;
				}
			}
		}
	}
	private void refreshArea(){
		getChildren().removeAll(getChildren());
		getChildren().add(currentArea);
	}
}
