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
public class MaxHealthPowerup extends Powerup{
	public MaxHealthPowerup(double xStart, double yStart){
		super(xStart,yStart);
	}
	public boolean upgrade(Player plyr){
		plyr.setMaxHealth(plyr.getMaxHealth() + 1);
		plyr.setHealth(plyr.getHealth() + 1);
		Notify.displayMessage(plyr, "Health up!", "HARDER");
		return true;
	}
}