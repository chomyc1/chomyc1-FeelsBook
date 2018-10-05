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

    public void setMessage(String newMessage) {
        message = newMessage;
    }

    public void setDate(Date newDate) {
        date = newDate;
    }

    public String toString() {
        String outputString;
        String emotionType = getEmotionType();
        if (message.equals("")) { // Don't display anything for the message if we don't have one.
            outputString = emotionType + " | " + date.toString();
        }
        else {
            outputString = emotionType + " | " + date.toString() + " | " + message;
        }
        return outputString;
    }
}
