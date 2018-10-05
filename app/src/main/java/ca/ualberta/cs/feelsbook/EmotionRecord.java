package ca.ualberta.cs.feelsbook;

import java.util.Date;

public abstract class EmotionRecord {
    protected String emotionType;
    private String message;
    private Date date;

    public EmotionRecord (String inputMessage, Date inputDate){
        message = inputMessage;
        date = inputDate;
    }

    public String getEmotionType() {
        return emotionType;
    }

    public String getMessage() {
        return message;
    }

    public Date getDate() {
        return date;
    }

    public String toString() {
        String emotionType = getEmotionType();
        String outputString = emotionType + " | " + date.toString() + " | " + message;
        return outputString;
        // If message is blank, don't include it in the output
    }
}
