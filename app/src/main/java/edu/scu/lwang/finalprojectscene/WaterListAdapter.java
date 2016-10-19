package edu.scu.lwang.finalprojectscene;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Date;

/**
 * Created by mingming on 5/9/16.
 */
public class WaterListAdapter extends CursorAdapter {

    public WaterListAdapter(Context context, Cursor cursor, int flags) {
        super(context, cursor, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.row, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        String plantName = cursor.getString(cursor.getColumnIndex("PlantName"));
        String imagePath = cursor.getString(cursor.getColumnIndex("PhotoPath"));
        long next = cursor.getLong(cursor.getColumnIndex("NextWater"));
        long now = new Date().getTime();
        int fromNow = (int)((next - now )/ 86400000);
        String waterDay;
        if(next < now){
            waterDay = "Today";
        }else if(fromNow == 0){
            waterDay = "Tomorrow";
        }else{
            waterDay = "In 1 week";
        }
        ((ImageView)view.findViewById(R.id.imageView2)).setImageURI(Uri.parse(imagePath));
        TextView tvName = (TextView)view.findViewById(R.id.textView2);
        tvName.setText(plantName);

        TextView tvWater = (TextView)view.findViewById(R.id.textView3);
        tvWater.setText(waterDay);

        TextView tvDone = (TextView)view.findViewById(R.id.textView5);
        tvDone.setText("Water me!");

//        BitmapFactory.Options optio
    }
}