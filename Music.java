import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import java.io.*;
public class Music{
  MediaPlayer mediaPlayer;
  public Music(){
    musicplaying=false;
  }
  boolean musicplaying;
  public void stopMusic(){
    mediaPlayer.stop();
    musicplaying=false;
  }

  public void playPowerglide(){
    if(musicplaying){
      stopMusic();
    }
    String bip = "POWERGLIDE.mp3";
    Media hit = new Media(new File(bip).toURI().toString());
    mediaPlayer = new MediaPlayer(hit);
    musicplaying=true;
    mediaPlayer.play();
  }
  public void play5th(){
    if(musicplaying){
      stopMusic();
    }
    String bip = "5TH.mp3";
    Media hit = new Media(new File(bip).toURI().toString());
    mediaPlayer = new MediaPlayer(hit);
    musicplaying=true;
    mediaPlayer.play();
  }
  public void playAnpanman(){
    if(musicplaying){
      stopMusic();
    }
    String bip = "ANPANMAN.mp3";
    Media hit = new Media(new File(bip).toURI().toString());
    mediaPlayer = new MediaPlayer(hit);
    musicplaying=true;
    mediaPlayer.play();
  }
  public void playRPG(){
    if(musicplaying){
      stopMusic();
    }
    String bip = "RPG.mp3";
    Media hit = new Media(new File(bip).toURI().toString());
    mediaPlayer = new MediaPlayer(hit);
    musicplaying=true;
    mediaPlayer.play();
  }
}
