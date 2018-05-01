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

import java.util.ArrayList;
public abstract class Projectile extends Sprite{
	int damage;
	int fireRate;
	
	public Projectile(Pane outerPane, double dy, double dx){
		super(outerPane,dy,dx);
		this.setStyle("-fx-background-color: yellow");
		this.setPrefSize(10,10);
		this.setLayoutX(0);
		this.setLayoutY(0);
		damage = 10;
		fireRate = 1;
		this.dy = dy;
		this.dx = dx;
	}

}