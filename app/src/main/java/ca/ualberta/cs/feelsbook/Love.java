package ca.ualberta.cs.feelsbook;

import java.util.Date;

public class Love extends EmotionRecord {
    public Love (String inputMessage, Date inputDate){
        super(inputMessage, inputDate);
        emotionType = "Love";
    }
}
