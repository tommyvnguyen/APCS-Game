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
public class Notify{
	private static int messageCounter;
	private static AnimationTimer timer;
	public static void displayMessage(Pane pane, String heading, String subheading){
		Rectangle messageRect = new Rectangle(0,30,0,100);
		Text headingText = new Text(5, 65, heading);
		headingText.setFont(new Font(45));
		Text subheadingText = new Text(5, 95, subheading);
		subheadingText.setFont(new Font(20));
		messageRect.setFill(Color.rgb(200,200,200,0.5));
		headingText.setVisible(false);
		subheadingText.setVisible(false);
		
		
		pane.getChildren().add(messageRect);
		pane.getChildren().add(headingText);
		pane.getChildren().add(subheadingText);
		timer = new AnimationTimer(){
			@Override
			public void handle(long now){
				pane.toFront();
				animateMessage(messageRect);
				if(messageCounter >= 500){
					messageCounter = 0;
					pane.getChildren().remove(messageRect);
					pane.getChildren().remove(headingText);
					pane.getChildren().remove(subheadingText);
					this.stop();
				}
				if(!(messageRect.getWidth() >= 300)){
					headingText.setVisible(false);
					subheadingText.setVisible(false);
				}else{
					headingText.setVisible(true);
					subheadingText.setVisible(true);
				}
				messageCounter++;
			}
		};
		timer.start();
	}
	private static void animateMessage(Rectangle rect){
		if(messageCounter < 470){
			if(rect.getWidth() < 300){
				rect.setWidth(rect.getWidth() + 10);
			}
		}else{
			rect.setWidth(rect.getWidth() - 10);
		}
	}
}