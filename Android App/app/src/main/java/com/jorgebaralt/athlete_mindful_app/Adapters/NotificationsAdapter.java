package com.jorgebaralt.athlete_mindful_app.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.jorgebaralt.athlete_mindful_app.Notifications;
import com.jorgebaralt.athlete_mindful_app.R;

import java.util.ArrayList;

/**
 * Created by User on 11/20/2017.
 */

public class NotificationsAdapter  extends ArrayAdapter{
    public NotificationsAdapter(@NonNull Context context, ArrayList<Notifications> notifications) {
        super(context,0,notifications);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.notification_item,parent,false);
        }
        Notifications currentNotification = (Notifications) getItem(position);

        //declar view on layour
        TextView url = (TextView) listItemView.findViewById(R.id.notification_url);
        TextView message = (TextView) listItemView.findViewById(R.id.notification_message);

        url.setText(currentNotification.getUrl());
        message.setText(currentNotification.getMessage());

        return listItemView;
    }
}
