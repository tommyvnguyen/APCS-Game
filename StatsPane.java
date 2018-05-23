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
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

import javafx.scene.paint.Color;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

import java.util.ArrayList;

import java.util.ArrayList;
public class StatsPane extends Pane{
	ArrayList<ImageView> hearts;
	ArrayList<Rectangle> tickMarks;
	Player plyr;
	
	StatsPane(Player plyr){
		this.plyr = plyr;
		hearts = new ArrayList<ImageView>();
		tickMarks = new ArrayList<Rectangle>();
		for(int i = 0; i < plyr.getMaxHealth(); i++){
			Image image = null;
			if(i > plyr.getHealth()-1){
				image = new Image("emptyHeart.png");
			}else{
				image = new Image("heart.png");
			}
			ImageView iv = new ImageView();
			iv.setX(((i%6)*45) + 525);
			iv.setY((i/6 * 45) + 10);
			iv.setImage(image);
			iv.setFitWidth(40);
			iv.setPreserveRatio(true);
			iv.setSmooth(true);
			iv.setCache(true);
			hearts.add(iv);
			this.getChildren().add(iv);
		}
		
		ImageView bullet = new ImageView();
		bullet.setImage(new Image("bullet.png"));
		bullet.setFitWidth(40);
		bullet.setX(350);
		bullet.setY(10);
		bullet.setPreserveRatio(true);
		bullet.setSmooth(true);
		bullet.setCache(true);
		this.getChildren().add(bullet);
		
		ImageView shoe = new ImageView();
		shoe.setImage(new Image("shoe.png"));
		shoe.setFitWidth(30);
		shoe.setX(357);
		shoe.setY(60);
		shoe.setPreserveRatio(true);
		shoe.setSmooth(true);
		shoe.setCache(true);
		this.getChildren().add(shoe);
		
		ImageView fist = new ImageView();
		fist.setImage(new Image("fist.png"));
		fist.setFitWidth(30);
		fist.setX(355);
		fist.setY(97);
		fist.setPreserveRatio(true);
		fist.setSmooth(true);
		fist.setCache(true);
		this.getChildren().add(fist);
		
		for(int i = 0; i < plyr.getBulletType().getDamage(); i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),12,5, 35);
			tickMark.setFill(Color.WHITE);
			tickMarks.add(tickMark);
			this.getChildren().add(tickMark);
		}
		
		for(int i = 0; i < plyr.spdMultiplier; i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),52,5, 35);
			tickMark.setFill(Color.WHITE);
			tickMarks.add(tickMark);
			this.getChildren().add(tickMark);
		}
		
		for(int i = 0; i < plyr.getMeleeDmg(); i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),92,5, 35);
			tickMark.setFill(Color.WHITE);
			tickMarks.add(tickMark);
			this.getChildren().add(tickMark);
		}
	}
	
	public void update(){
		this.getChildren().clear();
		hearts.clear();
		tickMarks.clear();
		for(int i = 0; i < plyr.getMaxHealth(); i++){
			Image image = null;
			if(i > plyr.getHealth()-1){
				image = new Image("emptyHeart.png");
			}else{
				image = new Image("heart.png");
			}
			ImageView iv = new ImageView();
			iv.setX(((i%6)*45) + 525 );
			iv.setY((i/6 * 45) + 10);
			iv.setImage(image);
			iv.setFitWidth(40);
			iv.setPreserveRatio(true);
			hearts.add(iv);
			this.getChildren().add(iv);
		}
		
		ImageView bullet = new ImageView();
		bullet.setImage(new Image("bullet.png"));
		bullet.setFitWidth(40);
		bullet.setX(350);
		bullet.setY(10);
		bullet.setPreserveRatio(true);
		this.getChildren().add(bullet);
		
		ImageView shoe = new ImageView();
		shoe.setImage(new Image("shoe.png"));
		shoe.setFitWidth(30);
		shoe.setX(357);
		shoe.setY(60);
		shoe.setPreserveRatio(true);
		this.getChildren().add(shoe);
		
		ImageView fist = new ImageView();
		fist.setImage(new Image("fist.png"));
		fist.setFitWidth(30);
		fist.setX(355);
		fist.setY(97);
		fist.setPreserveRatio(true);
		this.getChildren().add(fist);
		
		for(int i = 0; i < plyr.getBulletType().getDamage(); i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),12,5, 35);
			tickMark.setFill(Color.WHITE);
			tickMarks.add(tickMark);
			this.getChildren().add(tickMark);
		}
		
		for(int i = 0; i < plyr.spdMultiplier; i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),52,5, 35);
			tickMark.setFill(Color.WHITE);
			tickMarks.add(tickMark);
			this.getChildren().add(tickMark);
		}
		
		for(int i = 0; i < plyr.getMeleeDmg(); i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),92,5, 35);
			tickMark.setFill(Color.WHITE);
			tickMarks.add(tickMark);
			this.getChildren().add(tickMark);
		}
	}
}