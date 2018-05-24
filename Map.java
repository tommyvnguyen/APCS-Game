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
		addDoors();
	}

	public Area getCurrentArea(){
		return currentArea;
	}

	public String checkDoorCollision(Rectangle hitbox){
			return currentArea.checkDoorCollision(hitbox);
	}
	private void generateNewMap(){
		SpawnArea spawn = new SpawnArea(0,0);
		areas.add(spawn);
		currentArea=spawn;
		getChildren().add(currentArea);
		randomMap(6,3,1);
		for(Area as : areas){
				System.out.println(as.getXCoord()+" " + as.getYCoord());
		}
	}
	//assigns a main branch, a long side branch, a short side branch then adds side rooms and short side branches
	// any maps is given to have a main branch with 2 bosses, a side branch with 1 boss and 1 side branch with no boss
	private void randomMap(int numSideRooms, int numSideBranches, int numBossBranch){
		String dir= randomDirection();
			System.out.println("main branch direction:" +dir);
		generateMainBranch(dir);
		String currentDir= dir;
		while(dir.equals(currentDir)){
			dir = randomDirection();
		}
		System.out.println("side branch direction:" +dir);
		generateSideBranch(0,0,dir,randomInt(4,6),false);
		String currentDir2=dir;
		while(dir.equals(currentDir)||currentDir2.equals(dir)){
			dir = randomDirection();

		}
		System.out.println("side rooms direction:" +dir);
		generateSideRooms(0,0,dir,randomInt(3,4),false);
		for(int i=0;i<numBossBranch;i++){
			int index = randomInt(0,areas.size()-1);
			int xc=areas.get(index).getXCoord();
			int yc=areas.get(index).getYCoord();
			generateSideBranch(xc,yc,openRandomDirection(xc,yc),randomInt(5,7),false);
		}
		for(int i=0; i<numSideBranches; i++){
			int index = randomInt(0,areas.size()-1);
			int xc=areas.get(index).getXCoord();
			int yc=areas.get(index).getYCoord();
			generateSideRooms(xc,yc,openRandomDirection(xc,yc),randomInt(3,5),false);
		}
		for(int i=0; i<numSideRooms; i++){
			int index = randomInt(0,areas.size()-1);
			int xc=areas.get(index).getXCoord();
			int yc=areas.get(index).getYCoord();
			generateSideRooms(xc,yc,openRandomDirection(xc,yc),randomInt(1,2),false);
		}
	}

	//takes "right", "left", "top", "bottom"
	private void generateMainBranch(String direction){
		int modifier = 1;
		if(direction.equals("left")||direction.equals("bottom")){
			modifier=-1;
		}
		for(int i=1; i<10; i++){
			if(i!=5){
				if(direction.equals("top") || direction.equals("bottom")){
					areas.add(randomArea(0,i*modifier));
				}else{
					areas.add(randomArea(i*modifier,0));
				}
			}
		}
		if(direction.equals("top") || direction.equals("bottom")){
			areas.add(new MidBossArea(0,5*modifier));
			areas.add(new FinalBossArea(0,10*modifier));
		}else{
			areas.add(new MidBossArea(5*modifier,0));
			areas.add(new FinalBossArea(10*modifier,0));
		}

	}
	//generates branch with size number rooms, starting a x,y and going in direction given. boolean whether intial coordiante is incldued
	// (e.g exclusive vs inclusive)
	private void generateSideBranch(int x, int y,String direction,int size,boolean inclusive){
		int modifier = 1;
		if(direction.equals("left")||direction.equals("bottom")){
			modifier=-1;
		}
		if(!inclusive){
			if(direction.equals("top")||direction.equals("bottom")){
				y+=1*modifier;
			}
			if(direction.equals("left")||direction.equals("right")){
				x+=1*modifier;
			}
		}
		for(int i=0; i<size-1; i++){

				if(direction.equals("top") || direction.equals("bottom")){
					if(!checkPresentRoom(x,y+i*modifier)){
						areas.add(randomArea(x,y+(i*modifier)));
					}
				}else{
					if(!checkPresentRoom(x+(i*modifier),y))
					areas.add(randomArea(x+(i*modifier),y));
				}
		}
		if(direction.equals("top") || direction.equals("bottom")){
			if(!checkPresentRoom(x,y+(size-1)*modifier)){
				areas.add(new MidBossArea(x,y+(size-1)*modifier));
			}
		}else{
			if(!checkPresentRoom(x+(size-1)*modifier,y)){
				areas.add(new MidBossArea(x+(size-1)*modifier,y));
			}
		}
	}
	//nos boss usually for small side rooms (1 or 2)
	private void generateSideRooms(int x, int y, String direction, int size,boolean inclusive){
		int modifier = 1;
		System.out.println(x +" "+ y +" "+ direction+" " + size +" "+ inclusive);
		if(direction.equals("left")||direction.equals("bottom")){
			modifier=-1;
		}
		if(!inclusive){
			if(direction.equals("top")||direction.equals("bottom")){
				y+=1*modifier;
			}
			if(direction.equals("left")||direction.equals("right")){
				x+=1*modifier;
			}
		}
		for(int i=0; i<size-1; i++){
			if(direction.equals("top") || direction.equals("bottom")){
				if(!checkPresentRoom(x,y+i*modifier)){
					areas.add(randomArea(x,y+(i*modifier)));
				}
			}else{
				if(!checkPresentRoom(x+(i*modifier),y))
				areas.add(randomArea(x+(i*modifier),y));
			}
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
	private void addDoors(){
		for(int i=0;i<areas.size();i++){
			for(Area a: areas){
				if(a.getXCoord()==areas.get(i).getXCoord()+1 && a.getYCoord()==areas.get(i).getYCoord()){
					areas.get(i).addRightDoor();
				}
				if(a.getXCoord()==areas.get(i).getXCoord()-1 && a.getYCoord()==areas.get(i).getYCoord()){
					areas.get(i).addLeftDoor();
				}
				if(a.getXCoord()==areas.get(i).getXCoord() && a.getYCoord()==areas.get(i).getYCoord()+1){
					areas.get(i).addTopDoor();
				}
				if(a.getXCoord()==areas.get(i).getXCoord() && a.getYCoord()==areas.get(i).getYCoord()-1){
					areas.get(i).addBottomDoor();
				}
			}
		}
	}
	private String randomDirection(){
		int random = (int)(Math.random()*4+1);
		if(random ==1){ return "top";}
		else if(random==2){ return "bottom";}
		else if (random==3){return "right";}
		else { return "left";}
	}
	//given coordinate, find random direction that has no rooms
	private String openRandomDirection(int x, int y){
		boolean rightEmpty=true;
		boolean leftEmpty=true;
		boolean topEmpty=true;
		boolean bottomEmpty=true;
		for(Area a : areas){
			if(a.getXCoord()==x+1 && a.getYCoord()==y){
				rightEmpty=false;
			}
			if(a.getXCoord()==x-1 && a.getYCoord()==y){
				leftEmpty=false;
			}
			if(a.getXCoord()==x && a.getYCoord()==y+1){
				topEmpty=false;
			}
			if(a.getXCoord()==x && a.getYCoord()==y-1){
				bottomEmpty=false;
			}
		}
		if(!rightEmpty && !leftEmpty && !topEmpty && !bottomEmpty){
			return randomDirection();
		}else{
			while(true){
				String dir = randomDirection();
				if(dir.equals("right") && rightEmpty){ return "right";}
				if(dir.equals("left") && leftEmpty){ return "left";}
				if(dir.equals("top") && topEmpty){ return "top";}
				if(dir.equals("bottom") && bottomEmpty){ return "bottom";}
			}
		}
	}
	private int randomInt(int low, int high){
		int x = (int)(Math.random()*(high-low+1)+low);
		return x;
	}
	//checks to see if a coordinate is already occupied
	private boolean checkPresentRoom(int x, int y){
		for (Area a : areas){
			if(a.getXCoord()==x && a.getYCoord()==y){
				return true;
			}
		}
		return false;
	}
}
