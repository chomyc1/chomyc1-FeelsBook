package ca.ualberta.cs.feelsbook;

import java.util.Date;

public class Sadness extends EmotionRecord {
    public Sadness (String inputMessage, Date inputDate){
        super(inputMessage, inputDate);
        emotionType = "Sadness";
    }
}
