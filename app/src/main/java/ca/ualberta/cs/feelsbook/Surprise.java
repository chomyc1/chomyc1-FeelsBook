package ca.ualberta.cs.feelsbook;

import java.util.Date;

public class Surprise extends EmotionRecord {
    public Surprise (String inputMessage, Date inputDate){
        super(inputMessage, inputDate);
        emotionType = "Surprise";
    }
}
