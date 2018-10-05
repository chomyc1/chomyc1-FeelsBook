package ca.ualberta.cs.feelsbook;

import java.util.Date;

public class Anger extends EmotionRecord {
    public Anger (String inputMessage, Date inputDate){
        super(inputMessage, inputDate);
        emotionType = "Anger";
    }
}
