package ormlite.wwj.com.ormlite;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Models.CategoryConver;
import Models.HotCat;
import Models.LeftCat;
import Models.RightCat;
import Models.RightCatSub;
import orm.DAOManager;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryFragment extends Fragment {

    private DAOManager mDAOManager;
    private View mView;
    private ListView mLeftCategoryList;
    private ArrayList<LeftCat> mLeftArray = new ArrayList<LeftCat>();
    private List<RightCat> mRightcat = new ArrayList<RightCat>();
    private LinearLayout mRightCategoryLL;
    private LeftCategoryAdapter mLeftCategoryAdapter = null;
    private LayoutInflater mLayoutInflater;
    private View mProgresssBar;
    private ScrollView mScrollView;
    private TextView mTVCommit;

    public CategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDAOManager=DAOManager.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView=inflater.inflate(R.layout.fragment_category, container, false);
        return  mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mLeftCategoryList= (ListView)mView.findViewById(R.id.category_list);
        mRightCategoryLL = (LinearLayout) mView.findViewById(R.id.rightCategoryLL);
        mTVCommit=(TextView)mView.findViewById(R.id.wait);
        mLayoutInflater = LayoutInflater.from(getActivity());
        mProgresssBar=mView.findViewById(R.id.progressbar);
        mScrollView=(ViewPagerScrollView)mView.findViewById(R.id.scrollview);

        mLeftCategoryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == mLeftArray.size()) {
                    return;
                }
                for (int k = 0; k < mLeftArray.size(); k++) {
                    mLeftArray.get(k).setIsCheck(false);
                }
                mLeftArray.get(position).setIsCheck(true);
                mLeftCategoryAdapter.notifyDataSetChanged();

                if (null != mLeftArray.get(position).getRightcat() && mLeftArray.get(position).getRightcat().size() > 0) {
                    mRightCategoryLL.setVisibility(View.VISIBLE);

                    mRightCategoryLL.removeAllViews();
                    mScrollView.scrollTo(0, 0);
                    mRightcat.clear();
                    mRightcat.addAll(mLeftArray.get(position).getRightcat());
                    for (int i = 0; i < mLeftArray.get(position).getRightcat().size(); i++) {
                        setCategoryTwo(i);
                    }
                    setCategoryTwo(mLeftArray.get(position).getRightcat().size());
                }
            }
        });

        prepareAddDataToDB();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 从资源文件中读取数据，并准备添加数据到数据库
     */
    private void prepareAddDataToDB() {
        try {
            mTVCommit.setText("读取资源文件中的分类数据...");
            InputStreamReader inputReader = new InputStreamReader( getResources().getAssets().open("category.txt"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line="";
            StringBuffer stringBuffer=new StringBuffer();
            while((line = bufReader.readLine()) != null){
                stringBuffer.append(line);
            }
            ObjectMapper om = new ObjectMapper();
            JsonNode jn = om.readTree(stringBuffer.toString());

            JsonNode leftCategory = jn.findValue("leftCat");
            JsonNode hotCat = jn.findValue("hotCat");

            mLeftArray.clear();
            mLeftArray = om.readValue(leftCategory.toString(), new TypeReference<List<LeftCat>>() {});
            HotCat[] hotArray = om.readValue(hotCat.toString(), HotCat[].class);
            ArrayList<RightCatSub> rightCatSubs = new ArrayList<RightCatSub>();
            if (null != hotArray) {
                for (HotCat hc : hotArray) {
                    if (null == hc) {
                        continue;
                    }
                    RightCatSub rightCatSub = new RightCatSub(0, hc.getName(), hc.getGroup(), hc.getImg_url());
                    rightCatSubs.add(rightCatSub);
                }
            }
            mTVCommit.setText("手动新增热门推荐...");
            ArrayList<RightCat> categoryTwoList = new ArrayList<RightCat>();
            LeftCat leftCat = new LeftCat();
            leftCat.setName("热门推荐");
            leftCat.setIsCheck(true);
            leftCat.setRightcat(categoryTwoList);
            mLeftArray.add(0, leftCat);
            RightCat categoryTwo = new RightCat(0, "", "", "");
            categoryTwoList.add(categoryTwo);
            categoryTwo.setSub(rightCatSubs);

            if (mLeftArray != null && mLeftArray.size() > 0) {
                mRightcat.clear();
                mRightcat.addAll(mLeftArray.get(0).getRightcat());
                mTVCommit.setText("删除分类数据...");
                addCategoryToDB();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示数据到界面上
     */
    private void showDataUI() {
        try {

            List<LeftCat> leftCatList=mDAOManager.queryAllLeftCat();
            if (leftCatList != null && leftCatList.size() > 0) {
                leftCatList.get(0).setIsCheck(true);
                mLeftCategoryList.setVisibility(View.VISIBLE);
                mLeftArray.addAll(leftCatList);

                mLeftCategoryAdapter = new LeftCategoryAdapter();
                mLeftCategoryList.setAdapter(mLeftCategoryAdapter);

                mRightCategoryLL.removeAllViews();
                mRightcat.clear();
                mRightcat.addAll(mLeftArray.get(0).getRightcat());
                for (int i = 0; i < mLeftArray.get(0).getRightcat().size(); i++) {
                    setCategoryTwo(i);
                }
                setCategoryTwo(mLeftArray.get(0).getRightcat().size());
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * t添加数据到数据库
     * @throws SQLException
     */
    private void addCategoryToDB() throws SQLException {
        new  Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mDAOManager.deleteLeftCat();
                    mDAOManager.deleteRightCat();
                    mDAOManager.deleteRightCatSub();


                    mHandler.sendEmptyMessage(4);
                    mDAOManager.addLeftCat(mLeftArray);
                    for(int k=0;k<mLeftArray.size();k++){
                        Iterator<RightCat> rightCats= mLeftArray.get(k).getRightcat().iterator();
                        while (rightCats.hasNext()){
                            RightCat rightCat=rightCats.next();
                            rightCat.setLeftCat(mLeftArray.get(k));  //设置二级分类数据与一级分类数据关联
                            mDAOManager.addRightCat(rightCat);
                            if(null!=rightCat && rightCat.getSub()!=null && rightCat.getSub().size()>0){
                                Iterator<RightCatSub> rightCatSubIterator=rightCat.getSub().iterator();
                                ArrayList<RightCatSub> rightCatSubArrayList = new ArrayList<RightCatSub>();
                                while (rightCatSubIterator.hasNext()) {
                                    RightCatSub rightCatSub = rightCatSubIterator.next();
                                    rightCatSub.setRightCat(rightCat);    //设置三级分类数据与二级分类数据关联
                                    rightCatSubArrayList.add(rightCatSub);
                                }
                                mDAOManager.addRightCatSub(rightCatSubArrayList);
                            }
                        }
                    }
                    mLeftArray.clear();
                    mHandler.sendEmptyMessage(5);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler mHandler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==4){
                mTVCommit.setText("添加分类数据到数据库...");
            }else  if(msg.what==5){
                showDataUI();
                mTVCommit.setVisibility(View.GONE);
                mProgresssBar.setVisibility(View.GONE);
            }
        }
    };

    private class LeftCategoryAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mLeftArray.size() + 1;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final GViewHolder holder;
            if (convertView == null) {
                holder = new GViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.left_category_item, (ViewGroup) null, false);
                holder.left_category = (TextView) convertView.findViewById(R.id.left_category);
                holder.categoryTag = (ImageView) convertView.findViewById(R.id.categoryTag);
                holder.bottomMargin = (View) convertView.findViewById(R.id.bottomMargin);

                convertView.setTag(holder);
            } else {
                holder = (GViewHolder) convertView.getTag();
            }
            if (position + 1 == mLeftArray.size() + 1) {
                holder.left_category.setVisibility(View.GONE);
                holder.categoryTag.setVisibility(View.GONE);
                holder.bottomMargin.setVisibility(View.VISIBLE);
                holder.bottomMargin.setBackgroundColor(Color.parseColor("#eeeeee"));

            } else {
                holder.bottomMargin.setVisibility(View.GONE);
                holder.left_category.setVisibility(View.VISIBLE);
                LeftCat leftCat = mLeftArray.get(position);
                if (TextUtils.isEmpty(leftCat.getName())) {
                    holder.left_category.setText("");
                } else {
                    holder.left_category.setText(leftCat.getName());
                }
                if (leftCat.isCheck()) {
                    holder.categoryTag.setVisibility(View.VISIBLE);
                    convertView.setBackgroundColor(Color.WHITE);
                } else {
                    holder.categoryTag.setVisibility(View.GONE);
                    convertView.setBackgroundColor(Color.parseColor("#eeeeee"));
                }
            }
            return convertView;
        }

        private class GViewHolder {
            View bottomMargin;
            ImageView categoryTag;
            TextView left_category;
        }
    }

    private void setCategoryTwo(int position) {
        View convertView = mLayoutInflater.inflate(R.layout.right_category_two, null, false);
        mRightCategoryLL.addView(convertView);
        TextView title = ViewHolder.get(convertView, R.id.categoryTwoTitle);
        View two_category = ViewHolder.get(convertView, R.id.two_category);
        View bottomMargin = ViewHolder.get(convertView, R.id.bottomMargin);
        LinearLayout categoryThreeGridView = ViewHolder.get(convertView, R.id.gridview_category_three);

        if (position + 1 == mRightcat.size() + 1) {
            bottomMargin.setVisibility(View.VISIBLE);
            categoryThreeGridView.setVisibility(View.GONE);
            two_category.setVisibility(View.GONE);
        } else {
            final RightCat rightCat = mRightcat.get(position);
            if (TextUtils.isEmpty(rightCat.getGroup()) && TextUtils.isEmpty(rightCat.getName())) {
                two_category.setVisibility(View.GONE);
            } else {
                two_category.setVisibility(View.VISIBLE);
            }
            categoryThreeGridView.setVisibility(View.VISIBLE);
            bottomMargin.setVisibility(View.GONE);
            if (TextUtils.isEmpty(rightCat.getName())) {
                title.setText("");
            } else {
                title.setText(rightCat.getName());
            }
            if (null != rightCat.getSub()) {
                Iterator<RightCatSub> iterator=rightCat.getSub().iterator();
                ArrayList<RightCatSub> rightCatSubList=new ArrayList<RightCatSub>();
                while (iterator.hasNext()){
                    rightCatSubList.add(iterator.next());
                }
                setCategoryThree(categoryThreeGridView,rightCatSubList);
            }
        }
    }

    private void setCategoryThree(LinearLayout categoryThreeGridView, ArrayList<RightCatSub> rightCatSubList) {

        int sum = rightCatSubList.size();

        // 计算行数,默认最多显示3行数据
        int row = sum % 3 == 0 ? sum / 3 : sum / 3 + 1;

        for (int k = 1; k <= row; k++) {
            setCategoryThreeData(categoryThreeGridView, rightCatSubList, sum, k);
        }

    }

    private void setCategoryThreeData(LinearLayout categoryThreeGridView, ArrayList<RightCatSub> rightCatSubList, int sum, int k) {
        if (sum > k * 3 - 1) {
            final	RightCatSub rightCatSub3 = rightCatSubList.get(k * 3 - 1);
            final	RightCatSub rightCatSub2 = rightCatSubList.get(k * 3 - 2);
            final	RightCatSub rightCatSub1 = rightCatSubList.get(k * 3 - 3);
            View convertView = mLayoutInflater.inflate(R.layout.right_category_three_item, (ViewGroup) null, false);
            categoryThreeGridView.addView(convertView);
            if (TextUtils.isEmpty(rightCatSub3.getImg_url())) {
                View text_rl1 = ViewHolder.get(convertView, R.id.text_rl1);
                TextView text_title1 = ViewHolder.get(convertView, R.id.text_title1);
                View text_line1 = ViewHolder.get(convertView, R.id.text_line1);

                View text_rl2 = ViewHolder.get(convertView, R.id.text_rl2);
                TextView text_title2 = ViewHolder.get(convertView, R.id.text_title2);
                View text_line2 = ViewHolder.get(convertView, R.id.text_line2);

                View text_rl3 = ViewHolder.get(convertView, R.id.text_rl3);
                TextView text_title3 = ViewHolder.get(convertView, R.id.text_title3);
                View text_line3 = ViewHolder.get(convertView, R.id.text_line3);

                ViewHolder.get(convertView, R.id.image_rl1).setVisibility(View.GONE);
                ViewHolder.get(convertView, R.id.image_rl2).setVisibility(View.GONE);
                ViewHolder.get(convertView, R.id.image_rl3).setVisibility(View.GONE);

                text_rl1.setVisibility(View.VISIBLE);
                text_rl2.setVisibility(View.VISIBLE);
                text_rl3.setVisibility(View.VISIBLE);
                text_line1.setVisibility(View.VISIBLE);
                text_line2.setVisibility(View.VISIBLE);
                text_line3.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rightCatSub1.getName())) {
                    text_title1.setText("");
                } else {
                    text_title1.setText(rightCatSub1.getName());
                }
                if (TextUtils.isEmpty(rightCatSub2.getName())) {
                    text_title2.setText("");
                } else {
                    text_title2.setText(rightCatSub2.getName());
                }
                if (TextUtils.isEmpty(rightCatSub3.getName())) {
                    text_title3.setText("");
                } else {
                    text_title3.setText(rightCatSub3.getName());
                }
                text_rl1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
                text_rl2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
                text_rl3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
            } else {
                View image_rl1 = ViewHolder.get(convertView, R.id.image_rl1);
                TextView title1 = ViewHolder.get(convertView, R.id.title1);
                final ImageView image1 = ViewHolder.get(convertView, R.id.product_iv1);
                View rightMarginIv1 = ViewHolder.get(convertView, R.id.rightMarginIv1);

                View image_rl2 = ViewHolder.get(convertView, R.id.image_rl2);
                TextView title2 = ViewHolder.get(convertView, R.id.title2);
                final ImageView image2 = ViewHolder.get(convertView, R.id.product_iv2);
                View rightMarginIv2 = ViewHolder.get(convertView, R.id.rightMarginIv2);

                View image_rl3 = ViewHolder.get(convertView, R.id.image_rl3);
                TextView title3 = ViewHolder.get(convertView, R.id.title3);
                final	ImageView image3 = ViewHolder.get(convertView, R.id.product_iv3);
                View rightMarginIv3 = ViewHolder.get(convertView, R.id.rightMarginIv3);

                ViewHolder.get(convertView, R.id.text_rl1).setVisibility(View.GONE);
                ViewHolder.get(convertView, R.id.text_rl2).setVisibility(View.GONE);
                ViewHolder.get(convertView, R.id.text_rl3).setVisibility(View.GONE);

                image_rl1.setVisibility(View.VISIBLE);
                image_rl2.setVisibility(View.VISIBLE);
                image_rl3.setVisibility(View.VISIBLE);
                rightMarginIv1.setVisibility(View.VISIBLE);
                rightMarginIv2.setVisibility(View.VISIBLE);
                rightMarginIv3.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rightCatSub1.getName())) {
                    title1.setText("");
                } else {
                    title1.setText(rightCatSub1.getName());
                }
                if (TextUtils.isEmpty(rightCatSub2.getName())) {
                    title2.setText("");
                } else {
                    title2.setText(rightCatSub2.getName());
                }
                if (TextUtils.isEmpty(rightCatSub3.getName())) {
                    title3.setText("");
                } else {
                    title3.setText(rightCatSub3.getName());
                }

                image_rl1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
                image_rl2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
                image_rl3.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
            }
        } else if (sum > k * 3 - 2) {
            final RightCatSub rightCatSub2 = rightCatSubList.get(k * 3 - 2);
            final RightCatSub rightCatSub1 = rightCatSubList.get(k * 3 - 3);
            View convertView = mLayoutInflater.inflate(R.layout.right_category_three_item, (ViewGroup) null, false);
            categoryThreeGridView.addView(convertView);
            if (TextUtils.isEmpty(rightCatSub2.getImg_url())) {
                View text_rl1 = ViewHolder.get(convertView, R.id.text_rl1);
                TextView text_title1 = ViewHolder.get(convertView, R.id.text_title1);
                View text_line1 = ViewHolder.get(convertView, R.id.text_line1);

                View text_rl2 = ViewHolder.get(convertView, R.id.text_rl2);
                TextView text_title2 = ViewHolder.get(convertView, R.id.text_title2);
                View text_line2 = ViewHolder.get(convertView, R.id.text_line2);

                View text_rl3 = ViewHolder.get(convertView, R.id.text_rl3);
                View text_line3 = ViewHolder.get(convertView, R.id.text_line3);

                text_rl1.setVisibility(View.VISIBLE);
                text_rl2.setVisibility(View.VISIBLE);
                text_rl3.setVisibility(View.VISIBLE);
                text_line1.setVisibility(View.VISIBLE);
                text_line2.setVisibility(View.VISIBLE);
                text_line3.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rightCatSub1.getName())) {
                    text_title1.setText("");
                } else {
                    text_title1.setText(rightCatSub1.getName());
                }
                if (TextUtils.isEmpty(rightCatSub2.getName())) {
                    text_title2.setText("");
                } else {
                    text_title2.setText(rightCatSub2.getName());
                }
                text_rl1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
                text_rl2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        // TODO Auto-generated method stub
                    }
                });
            } else {
                View image_rl1 = ViewHolder.get(convertView, R.id.image_rl1);
                TextView title1 = ViewHolder.get(convertView, R.id.title1);
                ImageView image1 = ViewHolder.get(convertView, R.id.product_iv1);
                View rightMarginIv1 = ViewHolder.get(convertView, R.id.rightMarginIv1);

                View image_rl2 = ViewHolder.get(convertView, R.id.image_rl2);
                TextView title2 = ViewHolder.get(convertView, R.id.title2);
                ImageView image2 = ViewHolder.get(convertView, R.id.product_iv2);
                View rightMarginIv2 = ViewHolder.get(convertView, R.id.rightMarginIv2);

                View image_rl3 = ViewHolder.get(convertView, R.id.image_rl3);
                View rightMarginIv3 = ViewHolder.get(convertView, R.id.rightMarginIv3);

                image_rl1.setVisibility(View.VISIBLE);
                image_rl2.setVisibility(View.VISIBLE);
                image_rl3.setVisibility(View.VISIBLE);
                rightMarginIv1.setVisibility(View.VISIBLE);
                rightMarginIv2.setVisibility(View.VISIBLE);
                rightMarginIv3.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rightCatSub1.getName())) {
                    title1.setText("");
                } else {
                    title1.setText(rightCatSub1.getName());
                }
                if (TextUtils.isEmpty(rightCatSub2.getName())) {
                    title2.setText("");
                } else {
                    title2.setText(rightCatSub2.getName());
                }
                image_rl1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }
                });
                image_rl2.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        } else if (sum > k * 3 - 3) {
            final RightCatSub rightCatSub1 = rightCatSubList.get(k * 3 - 3);
            View convertView = mLayoutInflater.inflate(R.layout.right_category_three_item, (ViewGroup) null, false);
            categoryThreeGridView.addView(convertView);
            if (TextUtils.isEmpty(rightCatSub1.getImg_url())) {
                View text_rl1 = ViewHolder.get(convertView, R.id.text_rl1);
                TextView text_title1 = ViewHolder.get(convertView, R.id.text_title1);
                View text_line1 = ViewHolder.get(convertView, R.id.text_line1);

                View text_rl2 = ViewHolder.get(convertView, R.id.text_rl2);
                View text_line2 = ViewHolder.get(convertView, R.id.text_line2);

                View text_rl3 = ViewHolder.get(convertView, R.id.text_rl3);
                View text_line3 = ViewHolder.get(convertView, R.id.text_line3);

                text_rl1.setVisibility(View.VISIBLE);
                text_rl2.setVisibility(View.VISIBLE);
                text_rl3.setVisibility(View.VISIBLE);
                text_line1.setVisibility(View.VISIBLE);
                text_line2.setVisibility(View.GONE);
                text_line3.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rightCatSub1.getName())) {
                    text_title1.setText("");
                } else {
                    text_title1.setText(rightCatSub1.getName());
                }

                text_rl1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }
                });
            } else {
                View image_rl1 = ViewHolder.get(convertView, R.id.image_rl1);
                TextView title1 = ViewHolder.get(convertView, R.id.title1);
                ImageView image1 = ViewHolder.get(convertView, R.id.product_iv1);
                View rightMarginIv1 = ViewHolder.get(convertView, R.id.rightMarginIv1);

                View image_rl2 = ViewHolder.get(convertView, R.id.image_rl2);
                View rightMarginIv2 = ViewHolder.get(convertView, R.id.rightMarginIv2);

                View image_rl3 = ViewHolder.get(convertView, R.id.image_rl3);
                View rightMarginIv3 = ViewHolder.get(convertView, R.id.rightMarginIv3);

                image_rl1.setVisibility(View.VISIBLE);
                image_rl2.setVisibility(View.VISIBLE);
                image_rl3.setVisibility(View.VISIBLE);
                rightMarginIv1.setVisibility(View.VISIBLE);
                rightMarginIv2.setVisibility(View.GONE);
                rightMarginIv3.setVisibility(View.GONE);
                if (TextUtils.isEmpty(rightCatSub1.getName())) {
                    title1.setText("");
                } else {
                    title1.setText(rightCatSub1.getName());
                }
                image_rl1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                    }
                });
            }
        }
    }



}

