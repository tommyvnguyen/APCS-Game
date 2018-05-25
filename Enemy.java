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
public abstract class Enemy extends Sprite{
	int damage;
	int health;
	int timeCounter;
	int trackingDifficulty;//frames before tracking algorithm refreshes, lower==more difficult
	Rectangle target; //The thing that the enemy is looking at
	//Projectile projectile;
	//jpeg
	String hittingWall;

	public Enemy(double dy, double dx, Rectangle target){
		super(dy, dx);
		damage = 1;
		health = 10;
		timeCounter = 0;
		this.target = target;
		trackingDifficulty=20;
		hittingWall="";
	}


	public void track(){
		if(timeCounter > 60){
			this.hitbox.getTransforms().clear();
			this.hitbox.getTransforms().add(new Rotate(Math.toDegrees(Math.atan2(this.hitbox.getY() - target.getY(),this.hitbox.getX()-target.getX()))+180, (this.hitbox.getWidth()/2 + this.hitbox.getX()), (this.hitbox.getHeight()/2 + this.hitbox.getY())));
		}
	}

	public void move(){

		if(hittingWall.indexOf("right")!=-1 && xSpd>0){
			xSpd=-1*xSpd;
		}
		if(hittingWall.indexOf("left")!=-1 && xSpd<0){
			xSpd=-1*xSpd;

		}
		if(hittingWall.indexOf("top")!=-1 && ySpd<0){
			ySpd=-1*ySpd;
		}

		if(hittingWall.indexOf("bottom")!=-1 && ySpd>0){
			ySpd=-1*ySpd;

		}
		this.hitbox.setX(this.hitbox.getX() + xSpd * spdMultiplier);
		this.hitbox.setY(this.hitbox.getY() + ySpd * spdMultiplier);
	}
	public void setHittingWall(String s){
		hittingWall=s;
	}
	public void setTrackingDifficulty(int d){
		trackingDifficulty=d;
	}
	public int getHealth(){
		return this.health;
	}
	public int getDamage(){
		return this.damage;
	}

	public int decreaseHealth(int decreaseAmt){
		health -= decreaseAmt;
		return health;
	}

	public void increaseTimeCounter(){
		timeCounter++;
	}
	
	public int getTimeCounter(){
		return timeCounter;
	}
   // It takes a y value and an x value and finds their proportion

	 //given an Area and the player's hitbox, check to see if the mob has a straight line of sight to
	 //the given rectangle
	 //returns boolean depnding on blockage
	 public boolean lineOfSightBlock(Area a, Rectangle r){
		 	boolean rightWallIntersection =false;
			boolean leftWallIntersection =false;
			boolean bottomWallIntersection =false;
			boolean topWallIntersection =false;
			for(Rectangle wall: a.getWalls()){
				double slope = (this.hitbox.getY()-r.getY())/(this.hitbox.getX()-r.getX());

				double yValueLeft=slope*(wall.getX()-r.getX())+r.getY();
				if((wall.getX()>r.getX() && wall.getX()<this.hitbox.getX()) || wall.getX()<r.getX() &&wall.getX()>this.hitbox.getX()){
					if(yValueLeft>wall.getY() && yValueLeft<wall.getY()+wall.getHeight()) {
						leftWallIntersection=true;
					}
				}

				double yValueRight=slope*(wall.getX()+wall.getWidth()-r.getX())+r.getY();
				if((wall.getX()>r.getX() && wall.getX()<this.hitbox.getX()) || wall.getX()<r.getX() &&wall.getX()>this.hitbox.getX()){
					if(yValueRight>wall.getY() && yValueRight<wall.getY()+wall.getHeight()){
						rightWallIntersection=true;;
					}
				}

				double invSlope = 1/slope;
				double xValueTop = invSlope*(wall.getY()-r.getY())+r.getX();
				if((wall.getY()>r.getY() && wall.getY()<this.hitbox.getY()) || wall.getY()<r.getY() &&wall.getY()>this.hitbox.getY()){
					if(xValueTop>wall.getX() && xValueTop<wall.getX()+wall.getWidth()){
						topWallIntersection=true;
					}
				}
				double xValueBottom=invSlope*(wall.getY()+wall.getHeight()-r.getY())+r.getX();
				if((wall.getY()>r.getY() && wall.getY()<this.hitbox.getY()) || wall.getY()<r.getY() &&wall.getY()>this.hitbox.getY()){
					if(xValueBottom>wall.getX() && xValueBottom<wall.getX()+wall.getWidth()){
						bottomWallIntersection=true;
					}
				}
			}
			int count=0;
			if(rightWallIntersection) count++;
			if(leftWallIntersection) count++;
			if(bottomWallIntersection) count++;
			if(topWallIntersection) count++;
			if(count >=2){
				return true;
			}else{
				return false;
			}
	 }
	 public boolean lineOfSightBlock(double x, double y, Area a, Rectangle r){
		 	boolean rightWallIntersection =false;
			boolean leftWallIntersection =false;
			boolean bottomWallIntersection =false;
			boolean topWallIntersection =false;
			for(Rectangle wall: a.getWalls()){
				double slope = (y-r.getY())/(x-r.getX());
				double yValueLeft=slope*(wall.getX()-r.getX())+r.getY();
				if((wall.getX()>r.getX() && wall.getX()<x) || wall.getX()<r.getX() &&wall.getX()>x){
					if(yValueLeft>wall.getY() && yValueLeft<wall.getY()+wall.getHeight()) {
						leftWallIntersection=true;
					}
				}

				double yValueRight=slope*(wall.getX()+wall.getWidth()-r.getX())+r.getY();
				if((wall.getX()>r.getX() && wall.getX()<x) || wall.getX()<r.getX() &&wall.getX()>x){
					if(yValueRight>wall.getY() && yValueRight<wall.getY()+wall.getHeight()){
						rightWallIntersection=true;
					}
				}

				double invSlope = 1/slope;
				double xValueTop = invSlope*(wall.getY()-r.getY())+r.getX();
				if((wall.getY()>r.getY() && wall.getY()<y) || wall.getY()<r.getY() &&wall.getY()>y){
					if(xValueTop>wall.getX() && xValueTop<wall.getX()+wall.getWidth()){
						topWallIntersection=true;
					}
				}
				double xValueBottom=invSlope*(wall.getY()+wall.getHeight()-r.getY())+r.getX();
				if((wall.getY()>r.getY() && wall.getY()<y) || wall.getY()<r.getY() &&wall.getY()>y){
					if(xValueBottom>wall.getX() && xValueBottom<wall.getX()+wall.getWidth()){
						bottomWallIntersection=true;
					}
				}
				//redo all the checks,this time checking for the other side of the enemy
				/*double slope = (y-r.getY())/(x-r.getX());
				double yValueLeft=slope*(wall.getX()-r.getX())+r.getY();
				if((wall.getX()>r.getX() && wall.getX()<x) || wall.getX()<r.getX() &&wall.getX()>x){
					if(yValueLeft>wall.getY() && yValueLeft<wall.getY()+wall.getHeight()) {
						leftWallIntersection=true;
					}
				}

				double yValueRight=slope*(wall.getX()+wall.getWidth()-r.getX())+r.getY();
				if((wall.getX()>r.getX() && wall.getX()<x) || wall.getX()<r.getX() &&wall.getX()>x){
					if(yValueRight>wall.getY() && yValueRight<wall.getY()+wall.getHeight()){
						rightWallIntersection=true;
					}
				}
				double invSlope = 1/slope;
				double xValueTop = invSlope*(wall.getY()-r.getY())+r.getX();
				if((wall.getY()>r.getY() && wall.getY()<y) || wall.getY()<r.getY() &&wall.getY()>y){
					if(xValueTop>wall.getX() && xValueTop<wall.getX()+wall.getWidth()){
						topWallIntersection=true;
					}
				}
				double xValueBottom=invSlope*(wall.getY()+wall.getHeight()-r.getY())+r.getX();
				if((wall.getY()>r.getY() && wall.getY()<y) || wall.getY()<r.getY() &&wall.getY()>y){
					if(xValueBottom>wall.getX() && xValueBottom<wall.getX()+wall.getWidth()){
						bottomWallIntersection=true;
					}
				}*/
			}
			int count=0;
			if(rightWallIntersection) count++;
			if(leftWallIntersection) count++;
			if(bottomWallIntersection) count++;
			if(topWallIntersection) count++;
			if(count >=2){
				return true;
			}else{
				return false;
			}
	 }
	 //given an area and a target(player), move to a spot to shoot the enemy that is within the distance parameters
	 //favors distances towards the min, then goes out towards max
	 public void rangedTrackMove(Area a, Rectangle target, double d){
		 if(timeCounter%trackingDifficulty==0){
				double[] targetPos = generateRangedPosition(a, target,d);
				int iterations=0;
				while(lineOfSightBlock(targetPos[0],targetPos[1],a,this.hitbox) && iterations <200 ){
					Rectangle test = new Rectangle(targetPos[0],targetPos[1], 1,1);
					targetPos = generateRangedPosition(a,test,d);
					iterations++;
				}
				if(iterations== 200){
					targetPos=new double[]{400,400};
				}
				double distance = Math.sqrt(Math.pow(targetPos[0]-hitbox.getX(),2) +Math.pow(targetPos[1]-hitbox.getY(),2));
				double magnitudeSpd = Math.sqrt(xSpd*xSpd+ySpd*ySpd);
				xSpd = magnitudeSpd * (targetPos[0]-hitbox.getX())/(distance);
				ySpd = magnitudeSpd * (targetPos[1]-hitbox.getY())/(distance);
		}
		if(!hittingWall.equals(""))System.out.println(hittingWall);
		if(hittingWall.indexOf("right")!=-1 && xSpd>=0){
			ySpd = Math.sqrt(xSpd*xSpd+ySpd*ySpd)*ySpd/Math.abs(ySpd);
			xSpd=0;

		}
		if(hittingWall.indexOf("left")!=-1 && xSpd<=0){
			ySpd = Math.sqrt(xSpd*xSpd+ySpd*ySpd) *ySpd/Math.abs(ySpd);
			xSpd=0;
		}
		if(hittingWall.indexOf("top")!=-1 && ySpd<=0){
			xSpd = Math.sqrt(xSpd*xSpd+ySpd*ySpd) *xSpd/Math.abs(xSpd);
			ySpd=0;
		}
		if(hittingWall.indexOf("bottom")!=-1 && ySpd>=0){
			xSpd = Math.sqrt(xSpd*xSpd+ySpd*ySpd) *xSpd/Math.abs(xSpd);
			ySpd=0;
		}
		this.hitbox.setX(this.hitbox.getX() + xSpd * spdMultiplier);
		this.hitbox.setY(this.hitbox.getY() + ySpd * spdMultiplier);
	 }

	 private double[] generateRangedPosition(Area a, Rectangle target,double d){
		 ArrayList<double[]> possibilities = new ArrayList<double[]>();
		 double[] pos = new double[2];
		 int multiplier =1;
		 if(hitbox.getX()<target.getX()){
			 multiplier = -1;
		 }
		 for(int degrees = 1; degrees<=360;degrees+=5){
			 double testX =target.getX() + multiplier*d*Math.sin(Math.toRadians(degrees));
			 double testY = target.getY() - d*Math.cos(Math.toRadians(degrees));
			 if(!lineOfSightBlock(testX,testY,a,target) && !lineOfSightBlock(testX,testY,a,this.hitbox)){
				 possibilities.add(new double[]{testX,testY});
			 }
		 }
		 if(possibilities.size()==0){
			 for(int degrees = 1; degrees<=360;degrees+=5){
				 double testX =target.getX() + multiplier*d*Math.sin(Math.toRadians(degrees));
				 double testY = target.getY() - d*Math.cos(Math.toRadians(degrees));
				 if(!lineOfSightBlock(testX,testY,a,target)){
						possibilities.add(new double[]{testX,testY});
				 }
			 }
		 }
		 if(possibilities.size()==0){
			 return new double[]{400,400};
		 }
		 int randomNum=(int)(Math.random()*possibilities.size());
		 return possibilities.get(randomNum);
	 }
}
