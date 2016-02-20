package adapter;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import Models.CategoryConver;
import ormlite.wwj.com.ormlite.R;

/**
 * TODO: document your custom view class.
 */
public class WoShiTuPian extends BaseAdapter {

    private  List<CategoryConver> categoryConverArrayList;
    private  Context mContext;

    public WoShiTuPian(List<CategoryConver> categoryConverArrayList,Context context){
            this.categoryConverArrayList=categoryConverArrayList;
            this.mContext=context;
    }

    @Override
    public int getCount() {
        if(null!=categoryConverArrayList)
            return categoryConverArrayList.size();
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CategoryConver categoryConver= categoryConverArrayList.get(position);
        View view=LayoutInflater.from(mContext).inflate(R.layout.item_category_conver,null);
        ImageView imageView=(ImageView)view.findViewById(R.id.pic);
        imageView.setImageResource(R.mipmap.ic_launcher);

        TextView textView=(TextView)view.findViewById(R.id.title);
        textView.setText("width="+categoryConver.getImg_width()+"height="+categoryConver.getImg_height());
        return view;
    }
}
