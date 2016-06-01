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
//    private final Uri inboxURI = Telephony.Sms.CONTENT_URI;
    private final Uri inboxURI = Telephony.Sms.Conversations.CONTENT_URI;
//    String[] reqCols = {Telephony.Sms.ADDRESS, Telephony.Sms.Conversations.SNIPPET, Telephony.Sms.READ};
    String[] reqCols = {Telephony.Sms.Conversations.SNIPPET};
    private static TextService mInstance;

    public TextService() {

    }

    public static TextService getInstance() {
        if (mInstance == null) {
            mInstance = new TextService();
        }
        return mInstance;
    }

    public List<Conversation> getConversations(Context context) {
        List<Conversation> conversations = new ArrayList<Conversation>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(inboxURI,
                reqCols,
                null,
                null,
                Telephony.Sms.Inbox.DEFAULT_SORT_ORDER);
        int totalConversations = cursor.getCount();

        if (cursor.moveToFirst()) {
            //got to the first message if it exists
            for (int i = 0; i < cursor.getCount(); i++) {
                Conversation conversation = new Conversation();
//                conversation.setId(cursor.getString(cursor.getColumnIndexOrThrow("_ID")));
//                conversation.setId(cursor.getString(cursor.get));
//                conversation.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("ADDRESS")));
//                conversation.setRead(cursor.getString(cursor.getColumnIndexOrThrow("READ")));
                conversation.setSnippet(cursor.getString(cursor.getColumnIndexOrThrow("snippet")));
                String[] names = cursor.getColumnNames();
                conversations.add(conversation);
                cursor.moveToNext();
            }
        } else {
            throw new RuntimeException("There are no messages in the inbox.");
        }
        //close the cursor stream
        cursor.close();
        return conversations;

    }


}
