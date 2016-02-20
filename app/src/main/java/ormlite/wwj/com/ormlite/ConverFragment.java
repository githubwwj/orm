package ormlite.wwj.com.ormlite;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Models.CategoryConver;
import Models.LeftCat;
import adapter.WoShiTuPian;
import orm.DAOManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class ConverFragment extends Fragment {

    private DAOManager mDAOManager;
    private  View mView;
    private ListView mListView;
    private List<CategoryConver> mCategoryConverArrayList;

    public ConverFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDAOManager=DAOManager.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_main, container, false);
        return  mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mListView= (ListView)mView.findViewById(R.id.woshitupian);

        //=========================轮播图开始
        addCategoryConver();
        try {
            mCategoryConverArrayList=mDAOManager.queryAllCategoryConver();
            if(null!=mCategoryConverArrayList){
                mListView.setAdapter(new WoShiTuPian(mCategoryConverArrayList,getActivity()));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //=========================轮播图结束

    }

    /**
     * 数据库中添加轮播图
     */
    private void addCategoryConver(){

            CategoryConver categoryConver=new CategoryConver();
            categoryConver.setImg_width(250);
            categoryConver.setImg_height(200);
            categoryConver.setTitle("我是一张图片");

            CategoryConver categoryConver1=new CategoryConver();
            categoryConver1.setImg_width(250);
            categoryConver1.setImg_height(200);
            categoryConver1.setTitle("GOGOGOG");
            ArrayList<CategoryConver> categoryConverArrayList=new ArrayList<CategoryConver>();
            categoryConverArrayList.add(categoryConver);
            categoryConverArrayList.add(categoryConver1);
            try {
                mDAOManager.deleteAllCategoryConver();
                mDAOManager.addCategoryConver(categoryConverArrayList);
            } catch (SQLException e) {
                e.printStackTrace();
        }
    }



}

