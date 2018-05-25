import javafx.scene.shape.*;
import javafx.scene.paint.Color;
import java.util.*;
import java.io.*;
import javafx.application.Application;
import javafx.stage.Stage;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.text.*;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;

import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.KeyCode;
import java.util.*;
import java.io.*;
public class HighScores extends VBox{
	ArrayList<String> scores = new ArrayList<String>();
	Text scoresText=new Text();

	public HighScores(){

		readWordFile();
		String scoresString="";
		for(String s : scores){
			scoresString+=(s+"\n");
		}
		Text scoresText = new Text(scoresString);
		scoresText.setFont(new Font(18));
		scoresText.setFill(Color.BLACK);
		Text title = new Text("HIGH SCORES");
		title.setFill(Color.BLACK);
		title.setFont(new Font (80));
		getChildren().addAll(title,scoresText);

	}



	public void addNewScore(String name, int score){
		for (int i=0;i<scores.size();i++){
			int index = scores.get(i).indexOf("-");
			String scoreText = scores.get(i).substring(index+1);
			if(score> Integer.parseInt(scoreText)){
				scores.add(i, name+" -"+score);
				break;

			}
			if (i==(scores.size()-1)){
					scores.add(name+" -"+score);
					break;
			}
		}
    if(scores.size()==0){
      scores.add(name+" -"+score);
    }
		writeWordFile();
		refreshScores();
	}

	private void readWordFile(){
		try{
			FileReader fr = new FileReader("./HighScoresList.txt");
			BufferedReader br = new BufferedReader(fr);
			String line = null;
			while((line=br.readLine()) != null){
				scores.add(line);
			}
			br.close();
			fr.close();
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}

	private void writeWordFile(){
		try{
			FileWriter fw= new FileWriter("./HighScoresList.txt");
			BufferedWriter bw = new BufferedWriter(fw);

			for(int i=0;i<scores.size();i++){
				bw.write(scores.get(i)+"\n");
			}

			bw.close();
			fw.close();
		}catch(Exception e){
			System.out.println(e.getStackTrace());
		}
	}
	public void refreshScores(){
		String scoresString="";
		for(String s : scores){
			scoresString+=(s+"\n");
		}
		scoresText.setText(scoresString);
	}
}
