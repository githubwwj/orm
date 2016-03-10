package ormlite.wwj.com.ormlite;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nostra13.universalimageloader.core.download.ImageDownloader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import Models.LeftCat;
import Models.RightCat;
import Models.RightCatSub;
import orm.DAOManager;
import util.ConstantsField;
import util.ImageLoaderUtil;

/**
 * A placeholder fragment containing a simple view.
 */
public class CategoryFragment extends Fragment {

    private DAOManager mDAOManager;
    private View mView;
    private ListView mCategoryList;
    private ArrayList<LeftCat> mFirstCategoryList = new ArrayList<LeftCat>();
    private List<RightCat> mRightcat = new ArrayList<RightCat>();
    private CategoryAdapter mCategoryAdapter = null;
    private LayoutInflater mLayoutInflater;
    private View mProgresssBar;
    private LinearLayout mLastCategoryItem;

    public CategoryFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDAOManager = DAOManager.getInstance(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_category, container, false);
        return mView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mCategoryList = (ListView) mView.findViewById(R.id.category_list);
        mLayoutInflater = LayoutInflater.from(getActivity());
        mProgresssBar = mView.findViewById(R.id.progressbar);

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
            InputStreamReader inputReader = new InputStreamReader(getResources().getAssets().open("category.txt"));
            BufferedReader bufReader = new BufferedReader(inputReader);
            String line = "";
            StringBuffer stringBuffer = new StringBuffer();
            while ((line = bufReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            ObjectMapper om = new ObjectMapper();
            JsonNode jn = om.readTree(stringBuffer.toString());

            JsonNode leftCategory = jn.findValue("leftCat");
            JsonNode hotCat = jn.findValue("hotCat");

            mFirstCategoryList.clear();
            mFirstCategoryList = om.readValue(leftCategory.toString(), new TypeReference<List<LeftCat>>() {
            });
//            HotCat[] hotArray = om.readValue(hotCat.toString(), HotCat[].class);
//            ArrayList<RightCatSub> rightCatSubs = new ArrayList<RightCatSub>();
//            if (null != hotArray) {
//                for (HotCat hc : hotArray) {
//                    if (null == hc) {
//                        continue;
//                    }
//                    RightCatSub rightCatSub = new RightCatSub(0, hc.getName(), hc.getGroup(), hc.getImg_url());
//                    rightCatSubs.add(rightCatSub);
//                }
//            }
//            mTVCommit.setText("手动新增热门推荐...");
//            ArrayList<RightCat> categoryTwoList = new ArrayList<RightCat>();
//            LeftCat leftCat = new LeftCat();
//            leftCat.setName("热门推荐");
//            leftCat.setIsCheck(true);
//            leftCat.setRightcat(categoryTwoList);
//            mFirstCategoryList.add(0, leftCat);
//            RightCat categoryTwo = new RightCat(0, "", "", "");
//            categoryTwoList.add(categoryTwo);
//            categoryTwo.setSub(rightCatSubs);

            if (mFirstCategoryList != null && mFirstCategoryList.size() > 0) {
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
            List<LeftCat> leftCatList = mDAOManager.queryAllLeftCat();
            if (leftCatList.size() > 0) {
                leftCatList.get(0).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.male_cloth + ""));
            }

            if (leftCatList.size() > 1) {
                leftCatList.get(1).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.female_cloth + ""));
            }

            if (leftCatList.size() > 2) {
                leftCatList.get(2).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.chlidren_cloth + ""));
            }

            if (leftCatList.size() > 3) {
                leftCatList.get(3).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.male_peishi + ""));
            }
            if (leftCatList.size() > 4) {
                leftCatList.get(4).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.female_acc + ""));
            }

            if (leftCatList.size() > 5) {
                leftCatList.get(5).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.gravida_thing + ""));
            }


            if (leftCatList.size() > 6) {
                leftCatList.get(6).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.baby_toy + ""));
            }

            if (leftCatList.size() > 7) {
                leftCatList.get(7).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.snack_food + ""));
            }

            if (leftCatList.size() > 8) {
                leftCatList.get(8).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.drink + ""));
            }

            if (leftCatList.size() > 9) {
                leftCatList.get(9).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.food + ""));
            }

            if (leftCatList.size() > 10) {
                leftCatList.get(10).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.fresh_fruits + ""));
            }

            if (leftCatList.size() > 11) {
                leftCatList.get(11).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.cream + ""));
            }
            if (leftCatList.size() > 12) {
                leftCatList.get(12).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.daily_household + ""));
            }
            if (leftCatList.size() > 13) {
                leftCatList.get(13).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.house_appliance + ""));
            }

            if (leftCatList.size() > 14) {
                leftCatList.get(14).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.motors + ""));
            }

            if (leftCatList.size() > 15) {
                leftCatList.get(15).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.gif_bag + ""));
            }
            if (leftCatList.size() > 16) {
                leftCatList.get(16).setImg_url(ImageDownloader.Scheme.DRAWABLE.wrap(R.drawable.service + ""));
            }

            if (leftCatList != null && leftCatList.size() > 0) {
                mCategoryList.setVisibility(View.VISIBLE);
                mFirstCategoryList.addAll(leftCatList);

                mCategoryAdapter = new CategoryAdapter();
                mCategoryList.setAdapter(mCategoryAdapter);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * t添加数据到数据库
     *
     * @throws SQLException
     */
    private void addCategoryToDB() throws SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mDAOManager.deleteLeftCat();
                    mDAOManager.deleteRightCat();
                    mDAOManager.deleteRightCatSub();


                    mHandler.sendEmptyMessage(4);
                    mDAOManager.addLeftCat(mFirstCategoryList);
                    for (int k = 0; k < mFirstCategoryList.size(); k++) {
                        Iterator<RightCat> rightCats = mFirstCategoryList.get(k).getRightcat().iterator();
                        while (rightCats.hasNext()) {
                            RightCat rightCat = rightCats.next();
                            rightCat.setLeftCat(mFirstCategoryList.get(k));  //设置二级分类数据与一级分类数据关联
                            mDAOManager.addRightCat(rightCat);
                            if (null != rightCat && rightCat.getSub() != null && rightCat.getSub().size() > 0) {
                                Iterator<RightCatSub> rightCatSubIterator = rightCat.getSub().iterator();
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
                    mFirstCategoryList.clear();
                    mHandler.sendEmptyMessage(5);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 5) {
                showDataUI();
                mProgresssBar.setVisibility(View.GONE);
            }
        }
    };

    private class CategoryAdapter extends BaseAdapter {
        LayoutInflater mLayoutInflater;

        public CategoryAdapter() {
            mLayoutInflater = LayoutInflater.from(getActivity());
        }

        @Override
        public int getCount() {
            return mFirstCategoryList.size() + 1;
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
            GViewHolder holder = null;
            if (convertView == null) {
                holder = new GViewHolder();
                convertView = mLayoutInflater.inflate(R.layout.left_category_item, parent, false);
                holder.left_category_title = (TextView) convertView.findViewById(R.id.left_category);
                holder.categoryTag = (ImageView) convertView.findViewById(R.id.categoryTag);
                holder.bottomMargin = convertView.findViewById(R.id.bottomMargin);
                holder.rightCategoryLL = (LinearLayout) convertView.findViewById(R.id.rightCategoryLL);

                convertView.setTag(holder);
            } else {
                holder = (GViewHolder) convertView.getTag();
            }

            if (position + 1 == mFirstCategoryList.size() + 1) {
                holder.left_category_title.setVisibility(View.GONE);
                holder.categoryTag.setVisibility(View.GONE);
                holder.bottomMargin.setVisibility(View.VISIBLE);
                holder.bottomMargin.setBackgroundColor(Color.parseColor("#eeeeee"));
                holder.rightCategoryLL.setVisibility(View.GONE);
            } else {
                holder.bottomMargin.setVisibility(View.GONE);
                holder.left_category_title.setVisibility(View.VISIBLE);
                holder.categoryTag.setVisibility(View.VISIBLE);
                final LeftCat leftCat = mFirstCategoryList.get(position);
                if (TextUtils.isEmpty(leftCat.getName())) {
                    holder.left_category_title.setText("");
                } else {
                    holder.left_category_title.setText(leftCat.getName());
                }
                RelativeLayout.LayoutParams imgLP = new RelativeLayout.LayoutParams(holder.categoryTag.getLayoutParams());
                int imageHeight = ConstantsField.devicePixelsWidth * 360 / 720;
                imgLP.height = imageHeight;
                holder.categoryTag.setLayoutParams(imgLP);
                holder.rightCategoryLL.setMinimumHeight(imageHeight);

                holder.categoryTag.setLayoutParams(imgLP);
                ImageLoaderUtil.loadImgNoRest(leftCat.getImg_url(), holder.categoryTag);
                if (leftCat.isCheck()) {
                    if (null != mFirstCategoryList.get(position).getRightcat() && mFirstCategoryList.get(position).getRightcat().size() > 0) {

                        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(holder.left_category_title.getLayoutParams());
                        layoutParams.height = dip2px(getActivity(), 55);
                        holder.left_category_title.setLayoutParams(layoutParams);

                        holder.rightCategoryLL.removeAllViews();
                        mRightcat.clear();
                        mRightcat.addAll(mFirstCategoryList.get(position).getRightcat());
                        for (int i = 0; i < mFirstCategoryList.get(position).getRightcat().size(); i++) {
                            setCategoryTwo(holder.rightCategoryLL, i);
                        }
                        openCategoryThree(holder);
                    }
                } else {
                    RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(holder.left_category_title.getLayoutParams());
                    layoutParams.height = imageHeight;
                    holder.left_category_title.setLayoutParams(layoutParams);
                    holder.left_category_title.setMinimumHeight(imageHeight);

                    holder.rightCategoryLL.setVisibility(View.GONE);
                    holder.rightCategoryLL.removeAllViews();
                }
                holder.categoryTag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        boolean isEqualItem = false;
                        for (int i = 0; i < mFirstCategoryList.size(); i++) {
                            if (i == position && mFirstCategoryList.get(i).isCheck()) {
                                mFirstCategoryList.get(i).setIsCheck(false);
                                isEqualItem = true;
                                break;
                            }
                            mFirstCategoryList.get(i).setIsCheck(false);
                        }
                        if (!isEqualItem) {
                            mFirstCategoryList.get(position).setIsCheck(true);
                        }
                        notifyDataSetChanged();
                        mCategoryList.setSelection(position);
                        mCategoryList.setSelected(true);
                    }
                });
            }
            return convertView;
        }

        private void openCategoryThree(final GViewHolder holder) {
            //二级分类展开动画
//            holder.rightCategoryLL.clearAnimation();
//            Animation animation= AnimationUtils.loadAnimation(getActivity(), R.anim.category_open);
//            holder.rightCategoryLL.setAnimation(animation);
            holder.rightCategoryLL.setVisibility(View.VISIBLE);
        }

        private class GViewHolder {
            View bottomMargin;
            ImageView categoryTag;
            TextView left_category_title;
            LinearLayout rightCategoryLL;
        }

    }

    private void setCategoryTwo(LinearLayout rightCategoryLL, int position) {
        View convertView = mLayoutInflater.inflate(R.layout.right_category_two, null, false);
        rightCategoryLL.addView(convertView);
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
                Iterator<RightCatSub> iterator = rightCat.getSub().iterator();
                ArrayList<RightCatSub> rightCatSubList = new ArrayList<RightCatSub>();
                while (iterator.hasNext()) {
                    rightCatSubList.add(iterator.next());
                }
                setCategoryThree(categoryThreeGridView, rightCatSubList);
            }
        }
    }

    private void setCategoryThree(LinearLayout categoryThreeGridView, ArrayList<RightCatSub> rightCatSubList) {

        int sum = rightCatSubList.size();

        // 计算行数,默认最多显示3行数据
        int row = sum % mColumn == 0 ? sum / mColumn : sum / mColumn + 1;

        for (int k = 1; k <= row; k++) {
            setCategoryThreeData(categoryThreeGridView, rightCatSubList, sum, k);
        }


    }

    private int mColumn = 4;
    RightCatSub rightCatSub4 = null;
    RightCatSub rightCatSub3 = null;
    RightCatSub rightCatSub2 = null;
    RightCatSub rightCatSub1 = null;

    private void setCategoryThreeData(LinearLayout categoryThreeGridView, ArrayList<RightCatSub> rightCatSubList, int sum, int k) {
        rightCatSub4 = null;
        rightCatSub3 = null;
        rightCatSub2 = null;
        rightCatSub1 = null;
        if (sum > k * mColumn - 1) {
            rightCatSub4 = rightCatSubList.get(k * mColumn - 1);
            rightCatSub3 = rightCatSubList.get(k * mColumn - 2);
            rightCatSub2 = rightCatSubList.get(k * mColumn - 3);
            rightCatSub1 = rightCatSubList.get(k * mColumn - 4);
        } else if (sum > k * mColumn - 2) {
            rightCatSub3 = rightCatSubList.get(k * mColumn - 2);
            rightCatSub2 = rightCatSubList.get(k * mColumn - 3);
            rightCatSub1 = rightCatSubList.get(k * mColumn - 4);
        } else if (sum > k * mColumn - 3) {
            rightCatSub2 = rightCatSubList.get(k * mColumn - 3);
            rightCatSub1 = rightCatSubList.get(k * mColumn - 4);
        } else if (sum > k * mColumn - 4) {
            rightCatSub1 = rightCatSubList.get(k * mColumn - 4);
        }
        View convertView = mLayoutInflater.inflate(R.layout.right_category_three_item_text, (ViewGroup) null, false);
        categoryThreeGridView.addView(convertView);

        final TextView text_title1 = ViewHolder.get(convertView, R.id.text_title1);

        final TextView text_title2 = ViewHolder.get(convertView, R.id.text_title2);

        final TextView text_title3 = ViewHolder.get(convertView, R.id.text_title3);

        final TextView text_title4 = ViewHolder.get(convertView, R.id.text_title4);

        if (null == rightCatSub1 || TextUtils.isEmpty(rightCatSub1.getName())) {
            text_title1.setText("");
        } else {
            text_title1.setText(rightCatSub1.getName());
            text_title1.setTag(rightCatSub1);
        }
        if (null == rightCatSub2 || TextUtils.isEmpty(rightCatSub2.getName())) {
            text_title2.setText("");
        } else {
            text_title2.setText(rightCatSub2.getName());
            text_title2.setTag(rightCatSub2);
        }
        if (null == rightCatSub3 || TextUtils.isEmpty(rightCatSub3.getName())) {
            text_title3.setText("");
        } else {
            text_title3.setText(rightCatSub3.getName());
            text_title3.setTag(rightCatSub3);
        }
        if (null == rightCatSub4 || TextUtils.isEmpty(rightCatSub4.getName())) {
            text_title4.setText("");
        } else {
            text_title4.setText(rightCatSub4.getName());
            text_title4.setTag(rightCatSub4);
        }
        text_title1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enterGoodList((RightCatSub) text_title1.getTag());
            }
        });
        text_title2.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enterGoodList((RightCatSub) text_title2.getTag());
            }
        });
        text_title3.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enterGoodList((RightCatSub) text_title3.getTag());
            }
        });
        text_title4.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                enterGoodList((RightCatSub) text_title4.getTag());
                ;
            }
        });
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    private void enterGoodList(RightCatSub rightCatSub){
        Toast.makeText(getActivity(),rightCatSub.getName(),Toast.LENGTH_SHORT).show();
    }
}

