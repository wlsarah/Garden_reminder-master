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

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater layoutinflater;//?
    private List<GridItem> listStorage;

    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

    public ImageAdapter(Context c) {
        mContext = c;
        //mThumbIds = new ArrayList<Plant>();
        listStorage = new ArrayList<GridItem>();
        layoutinflater =(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        PlantDBHelper dbHelper = new PlantDBHelper(mContext);
        Cursor cursor = dbHelper.fetchFirstOcurrences();

        while (cursor.moveToNext())
        {
            //System.out.println("test 4" );
            int id= cursor.getInt(0);
            String plantName= cursor.getString(1);
            String plantPicPath= cursor.getString(2);
            String date = cursor.getString(3);
            System.out.println("this is imageAdapter => date = " + date + " ID = " + id);

            //CREATE PLAYER
            Plant p= new Plant(id, plantName, plantPicPath, date, 0, new Date());
//            p.setId(id);
//            p.setPlantName(plantName);
//            p.setPhotoPath(plantPicPath);
//            p.setDate(date);



            GridItem gi = new GridItem(plantName, plantPicPath, p);
            listStorage.add(gi);

            //ADD TO PLAYERS
            //mThumbIds.add(p);
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
            convertView = layoutinflater.inflate(R.layout.grid_item_layout, parent, false);
            listViewHolder.textInListView = (TextView) convertView.findViewById(R.id.textView);
            listViewHolder.imageInListView = (ImageView) convertView.findViewById(R.id.imageView);
            convertView.setTag(listViewHolder);

//            imageView = new ImageView(mContext);
//            imageView.setLayoutParams(new GridView.LayoutParams(300, 300));
//
////            canvas.drawARGB(0, 0, 0, 0);
////            canvas.drawCircle(imageView.getWidth()/2, imageView.getHeight()/2, imageView.getWidth()/2, paint );
//            imageView.setPadding(8, 8, 8, 8);
        } else {
            listViewHolder = (ViewHolder)convertView.getTag();
        }
        //imageView.setImageResource(mThumbIds[position]);

        //System.out.println("Before creating pic " + mThumbIds.get(position).getPhotoPath());
        if(listStorage.get(position).getPlant().getPhotoPath().charAt(0) == 'h'){
            new DownloadImageTask(listViewHolder.imageInListView)
                    .execute(listStorage.get(position).getPlant().getPhotoPath());
        }else{
            listViewHolder.imageInListView.setImageURI(Uri.parse(listStorage.get(position).getPlant().getPhotoPath()));
        }
        listViewHolder.textInListView.setText(listStorage.get(position).getPlant().getPlantName());



        System.out.println("After creating pic");

        return convertView;
    }

    static class ViewHolder{
        TextView textInListView;
        ImageView imageInListView;
    }

    // references to our images
    //private ArrayList<Plant> mThumbIds;

}
