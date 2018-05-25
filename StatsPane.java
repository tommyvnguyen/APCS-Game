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
	Player plyr;
	ArrayList<ImageView> hearts;
	ArrayList<ImageView> emptyHearts;
	ArrayList<Rectangle> tickMarks;

	StatsPane(Player plyr){
		this.plyr = plyr;
		this.hearts = new ArrayList<ImageView>();
		this.emptyHearts = new ArrayList<ImageView>();
		this.tickMarks = new ArrayList<Rectangle>();
		for(int i = 0; i < 18; i++){
			Image emptyHeartImage = new Image("emptyHeart.png");
			Image heartImage = new Image("heart.png");
			ImageView iv1 = new ImageView();
			iv1.setX(((i%6)*45) + 525);
			iv1.setY((i/6 * 45) + 10);
			iv1.setImage(emptyHeartImage);
			iv1.setFitWidth(40);
			iv1.setPreserveRatio(true);
			iv1.setSmooth(true);
			iv1.setCache(true);
			this.getChildren().add(iv1);
			hearts.add(iv1);

			ImageView iv2 = new ImageView();
			iv2.setX(((i%6)*45) + 525);
			iv2.setY((i/6 * 45) + 10);
			iv2.setImage(heartImage);
			iv2.setFitWidth(40);
			iv2.setPreserveRatio(true);
			iv2.setSmooth(true);
			iv2.setCache(true);
			this.getChildren().add(iv2);
			emptyHearts.add(iv2);
			if(i >= plyr.getMaxHealth()){
				iv1.setVisible(false);
			}
			if(i >= plyr.getHealth()){
				iv2.setVisible(false);
			}
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

		for(int i = 0; i < 10; i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),12,5, 35);
			tickMark.setFill(Color.WHITE);
			this.getChildren().add(tickMark);
			tickMarks.add(tickMark);
			if(i >= plyr.getBulletType().getDamage()){
				tickMark.setVisible(false);
			}
		}

		for(int i = 0; i < 10; i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),52,5, 35);
			tickMark.setFill(Color.WHITE);
			this.getChildren().add(tickMark);
			tickMarks.add(tickMark);
			if(i >= plyr.getSpdMultiplier()){
				tickMark.setVisible(false);
			}
		}

		for(int i = 0; i < 10; i++){
			Rectangle tickMark = new Rectangle(400 + (i*7),92,5, 35);
			tickMark.setFill(Color.WHITE);
			this.getChildren().add(tickMark);
			tickMarks.add(tickMark);
			if(i >= plyr.getMeleeDmg()){
				tickMark.setVisible(false);
			}
		}
	}

	public void update(){
		for(int i = 0; i < 18; i++){
			if(i < plyr.getMaxHealth()){
				hearts.get(i).setVisible(true);
			}else{
				hearts.get(i).setVisible(false);
			}

			if(i < plyr.getHealth()){
				emptyHearts.get(i).setVisible(true);
			}else{
				emptyHearts.get(i).setVisible(false);
			}
		}
		for(int i = 0; i < 10; i++){
			if(i >= plyr.getBulletType().getDamage()){
				tickMarks.get(i).setVisible(false);
			}else{
				tickMarks.get(i).setVisible(true);
			}
		}

		for(int i = 10; i < 20; i++){
			if(i-10 >= plyr.getSpdMultiplier()){
				tickMarks.get(i).setVisible(false);
			}else{
				tickMarks.get(i).setVisible(true);
			}
		}

		for(int i = 20; i < 30; i++){
			if(i-20 >= plyr.getMeleeDmg()){
				tickMarks.get(i).setVisible(false);
			}else{
				tickMarks.get(i).setVisible(true);
			}
		}
	}
}
