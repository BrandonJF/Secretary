package com.brandonjf.secretary;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.Telephony;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by brandon on 1/18/16.
 */
public class TextService {
    private final Uri messageUri = Telephony.Sms.Conversations.CONTENT_URI;
    com.android.pro
    String[] reqCols = {Telephony.Sms.Conversations._ID, Telephony.Sms.Conversations.ADDRESS, Telephony.Sms.Conversations.SNIPPET, Telephony.Sms.READ};
    private static TextService mInstance;

   public TextService() {

    }

    public static TextService getInstance(){
        if (mInstance == null){
            mInstance = new TextService();
        }
        return mInstance;
    }

    public List<String> getMessages(Context context){
        List<String> messages = new ArrayList<String>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(messageUri,
                reqCols,
                null,
                null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER);
        int totalMessages = cursor.getCount();

        if(cursor.moveToFirst()){
            //got to the first message if it exists
            for(int i = 0; i < 10; i++){
                messages.add(cursor.getString(2));
                cursor.moveToNext();
            }
        } else{
            throw new RuntimeException("There are no messages in the inbox.");
        }
        //close the cursor stream
        cursor.close();
        return messages;

    }


}
