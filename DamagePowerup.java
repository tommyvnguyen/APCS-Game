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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.ArrayList;
public class DamagePowerup extends Powerup{
	public DamagePowerup(double xStart, double yStart){
		super(xStart,yStart);
		try{
        	Image  img = new Image("DamagePU.png");
          ImageView imgview = new ImageView(img);
          imgview.setFitWidth(25);
          imgview.setFitHeight(25);
          hitbox.setFill(Color.TRANSPARENT);
          //health = new Image("healthkit.png");
          setPrefWidth(imgview.getFitWidth());
          setPrefHeight(imgview.getFitHeight());
					imgview.setX(xStart); imgview.setY(yStart);
          getChildren().add(imgview);

      }catch(Exception e){
          e.printStackTrace();
      }
	}
	public boolean upgrade(Player plyr){
		plyr.getBulletType().setDamage(plyr.getBulletType().getDamage()+1);
		Notify.displayMessage(plyr, "Damage up!", "STRONGER");
		return true;
	}
}
