package ca.ualberta.cs.feelsbook;

import java.util.Date;

public class Joy extends EmotionRecord {
    public Joy (String inputMessage, Date inputDate){
        super(inputMessage, inputDate);
        emotionType = "Joy";
    }
}
