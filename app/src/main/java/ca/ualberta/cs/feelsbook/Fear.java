package ca.ualberta.cs.feelsbook;

import java.util.Date;

public class Fear extends EmotionRecord {
    public Fear (String inputMessage, Date inputDate){
        super(inputMessage, inputDate);
        emotionType = "Fear";
    }
}
