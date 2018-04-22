package fxAce.data;

import java.util.Calendar;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Label;
import javafx.util.Duration;





public class Kello
  extends Label
{
  private Calendar pete;
  
  public Kello()
  {
    setId("TImeLabel");
    bindToTime();
  }
  




  private void bindToTime()
  {
    Timeline timeline = new Timeline(new KeyFrame[] {
      new KeyFrame(Duration.seconds(0.0D), 
      new EventHandler()
      {
        public void handle(ActionEvent actionEvent)
        {
          Calendar time = Calendar.getInstance();
          String tunti = tuntiA();
          String minuutti = minuuttiA();
          String sekunti = sekuntiA();
          String paivailta = time.get(9) == 0 ? "AM" : "PM";
          setText(sekunti + ":" + minuutti + ":" + tunti + " " + paivailta); } }, new KeyValue[0]), 
      


      new KeyFrame(Duration.seconds(1.0D), new KeyValue[0]) });
    
    timeline.setCycleCount(-1);
    timeline.play();
  }
  



  public String sekuntiA()
  {
    pete = Calendar.getInstance();
    int aika = pete.get(13);
    String time = String.valueOf(aika);
    return time;
  }
  




  public String minuuttiA()
  {
    int aika = pete.get(12);
    String time = String.valueOf(aika);
    return time;
  }
  



  public String tuntiA()
  {
    int aika = pete.get(10);
    String time = String.valueOf(aika);
    return time;
  }
}
