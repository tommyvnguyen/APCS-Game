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
import javafx.animation.AnimationTimer;
import javafx.util.Duration;

import java.util.ArrayList;
public class CollisionTester extends Application{
	int timeCounter = 1;
	
	public static void main(String[] args){
		launch(args);
	}
	
	public void start(Stage stage){
		Pane root = new Pane();
		Scene scene = new Scene(root, 1600, 900);
		Player plyr = new Player(200,200,100,100,3,3);
		//FlyingShooter testEnemy = new FlyingShooter(1, 1, plyr.getHitbox());
		ArrayList<FlyingShooter> enemies = new ArrayList<FlyingShooter>();
		
		
		root.getChildren().add(plyr);
		//root.getChildren().add(testEnemy);
		
		AnimationTimer timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				//System.out.println(timeCounter);
				if(timeCounter % 50 == 0){
					//testEnemy.fire();
					for(int i = 0; i < enemies.size(); i++){
						enemies.get(i).fire();
					}
				}
				if(timeCounter%1000 == 0 && enemies.size() < 5){
					FlyingShooter newShooter = new FlyingShooter(1,1,plyr.getHitbox());
					enemies.add(newShooter);
					root.getChildren().add(newShooter);
				}
				//testEnemy.move(timeCounter);
				for(int i = 0; i < enemies.size(); i++){
					enemies.get(i).move(timeCounter);
					for(int j = 0; j < enemies.get(i).getBullets().size(); j++){
						Rectangle bulletHitbox = enemies.get(i).getBullets().get(j).getHitbox();
						if(bulletHitbox.getX() < plyr.getHitbox().getX() + plyr.getHitbox().getWidth() && bulletHitbox.getY() < plyr.getHitbox().getY() + plyr.getHitbox().getHeight() && bulletHitbox.getX() + bulletHitbox.getWidth() > plyr.getHitbox().getX() && bulletHitbox.getY() + bulletHitbox.getHeight() > plyr.getHitbox().getY()
						|| bulletHitbox.getX() < 0 || bulletHitbox.getY() < 0 || bulletHitbox.getX() > 1600 || bulletHitbox.getY() > 900){
							enemies.get(i).getChildren().remove(j+1);
							enemies.get(i).getBullets().remove(j);
						}
					}
				}
				
				
				timeCounter++;
			}
		};
		
		
		timer.start();
		
		stage.setScene(scene);
		stage.getScene().onKeyPressedProperty().bind(plyr.onKeyPressedProperty());
		stage.getScene().onKeyReleasedProperty().bind(plyr.onKeyReleasedProperty());
		
		stage.show();
	}
}
