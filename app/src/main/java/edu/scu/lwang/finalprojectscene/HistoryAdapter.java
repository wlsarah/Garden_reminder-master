package edu.scu.lwang.finalprojectscene;

/**
 * Created by Sarahwang on 5/24/16.
 */
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HistoryAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutinflater;
    private List<GridItem> listStorage;
    //String plantName;



    public HistoryAdapter(Context c, String nameOfPlant) {
        mContext = c;
        listStorage = new ArrayList<GridItem>();
        layoutinflater =(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PlantDBHelper dbHelper = new PlantDBHelper(mContext);
        // Cursor cursor = dbHelper.fetchAll();

        Cursor cursor = dbHelper.getHistoryPlant(nameOfPlant);
        //imageInListView.setRotation(90f);
        while (cursor.moveToNext())
        {

            String plantPicPath= cursor.getString(0);
            String date = cursor.getString(1);

            Plant p= new Plant();
            p.setPhotoPath(plantPicPath);
            p.setDate(date);

            GridItem gi = new GridItem(date, plantPicPath, p);
            listStorage.add(gi);

        }
    }

    public int getCount() {
        return listStorage.size();
        //return mThumbIds.length;
    }

    public Object getItem(int position) {
        return listStorage.get(position).getPlant();
        //return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder listViewHolder;



//        Canvas canvas = new Canvas();
//        Paint paint = new Paint();
//        Rect rect = new Rect(0, 0, imageView.getWidth(), imageView.getHeight());
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            listViewHolder = new ViewHolder();
            convertView = layoutinflater.inflate(R.layout.history_item_layout, parent, false);
            listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.textView);
            listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.imageView);

            convertView.setTag(listViewHolder);

        } else {
            listViewHolder = (ViewHolder)convertView.getTag();
        }

//        if(listStorage.get(position).getPlant().getPhotoPath().charAt(0) == 'h'){
//            new DownloadImageTask(listViewHolder.imageInListView)
//                    .execute(listStorage.get(position).getPlant().getPhotoPath());
//        }else{
        listViewHolder.imageInListView.setImageURI(Uri.parse(listStorage.get(position).getPlant().getPhotoPath()));

        listViewHolder.textInListView.setText(listStorage.get(position).getPlant().getDate());

        System.out.println("After creating pic");

        return convertView;
    }

    static class ViewHolder{
        TextView textInListView;
        ImageView imageInListView;

    }


}
