package com.wfj.bmobstudy.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.wfj.bmobstudy.Adapter.NewsInformAdapter;
import com.wfj.bmobstudy.Bean.NewsInform;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.GlideImageLoader;
import com.wfj.bmobstudy.Utils.NewsInformListUtil;
import com.wfj.bmobstudy.Utils.ShowDialogUtil;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/*
* 主页 -点击更多新闻 进入这里
* */
public class NewsInformListActivity extends AppBaseActivity {
    private LinearLayout ly_back;
    private Banner banner;
    private GridView gridView;
    private RecyclerView recyclerView;
    private STATE abl_state;

    //展开、折叠、中间
    private enum STATE {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE
    }

    private AppBarLayout abl_news_inform_list;
    private String categories_url[] = {
            "http://www.asc.jx.cn/ykxw/xyxw.htm",
            "http://www.asc.jx.cn/ykxw/mtgz.htm",
            "http://www.asc.jx.cn/ykxw/xbdt.htm",
            "http://www.asc.jx.cn/ykxw/tzgg.htm"};

    //设置各类新闻最多400条
    private int all_numbers = 400;
    //当前已经显示的新闻条数
    private int shown = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.activity_news_inform_list;
    }

    private void initViews() {
        ly_back = (LinearLayout) findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        initGridView();
        initBannerData();
        initCollapsingToolBarlayout();
        initRecyclerView();


    }


    private void initBanner(List<NewsInform> list_banner) {
        banner = (Banner) findViewById(R.id.banner_news_inform_list);
        //设置banner样式
        banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        List<String> title = new ArrayList<>();
        final List<String> url = new ArrayList<>();
        List<String> pic_url = new ArrayList<>();
        for (int i = 0; i < list_banner.size(); i++) {
            title.add(list_banner.get(i).getTitle());
            url.add(list_banner.get(i).getUrl());
            pic_url.add(list_banner.get(i).getFirst_pic_url());
        }
        banner.setImages(pic_url);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.Default);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(title);
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(3000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                Intent i = new Intent(NewsInformListActivity.this, NewsInfoActivity.class);
                i.putExtra("url", url.get(position));
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();


    }

    private void initBannerData() {
        NewsInformListUtil.get_news_inform_pic_url(new NewsInformListUtil.two_categories_listCall() {
            @Override
            public void success() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                    }
                });
                initBanner(NewsInformListUtil.merge_have_pic_list());
            }
        });
    }

    private void initGridView() {
        gridView = (GridView) findViewById(R.id.gv_news_inform_list);
        String text[] = {"学院新闻", "媒体关注", "系部动态", "通知公告"};
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", getObject("important"));
        map.put("text", text[0]);
        arrayList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("image", getObject("inform"));
        map1.put("text", text[1]);
        arrayList.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("image", getObject("academic"));
        map2.put("text", text[2]);
        arrayList.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("image", getObject("message"));
        map3.put("text", text[3]);
        arrayList.add(map3);

        SimpleAdapter adapter = new SimpleAdapter(this,
                arrayList,
                R.layout.item_grid,
                new String[]{"image", "text"},
                new int[]{R.id.iv_grid, R.id.tv_grid});

        gridView.setAdapter(adapter);
        //默认加载学院新闻
        loadNewsData(categories_url[0]);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position) {
                    case 0:
                        NewsInformListUtil.current_url = categories_url[0];
                        break;
                    case 1:
                        NewsInformListUtil.current_url = categories_url[1];
                        break;
                    case 2:
                        NewsInformListUtil.current_url = categories_url[2];
                        break;
                    case 3:
                        NewsInformListUtil.current_url = categories_url[3];
                        break;
                }
//                NewsInformListUtil.page = 1;
                //加载新闻列表
                loadNewsData( NewsInformListUtil.current_url);
            }


        });
    }

    //加载新闻列表
    private void loadNewsData(String url) {
        NewsInformListUtil.get_list_by_page(url, new NewsInformListUtil.get_list_by_pageCall() {
            @Override
            public void success(final List<NewsInform> list) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRecyclerViewData(list);
                    }
                });
            }
        });
    }

    @SuppressLint("WrongConstant")
    private void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_news_inform_list);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(manager);

    }

    private void initRecyclerViewData(final List<NewsInform> list) {
        final NewsInformAdapter adapter = new NewsInformAdapter(R.layout.item_news_inform, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_LEFT);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Intent i = new Intent(NewsInformListActivity.this, NewsInfoActivity.class);
                i.putExtra("url", list.get(position).getUrl());
                startActivity(i);
                overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
//                if (shown >= all_numbers) {
                //为了减少获取信息的缓慢带来的烦恼， 设置只能加载一页
                    adapter.loadMoreEnd();
//                } else {
//                    //再次获取数据
//                    //此处应该是判断网络情况，默认良好
//                    if (true) {
//                        NewsInformListUtil.get_list_by_page(NewsInformListUtil.current_url, new NewsInformListUtil.get_list_by_pageCall() {
//                            @Override
//                            public void success(final List<NewsInform> list) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        adapter.addData(list);
//                                        adapter.loadMoreComplete();
//                                    }
//                                });
//                            }
//                        });
//                    } else {
//                        adapter.loadMoreFail();
//                    }
//                }

            }
        }, recyclerView);
        recyclerView.setAdapter(adapter);
    }

    private void initCollapsingToolBarlayout() {
        abl_news_inform_list = (AppBarLayout) findViewById(R.id.abl_news_inform_list);
        abl_news_inform_list.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (verticalOffset == 0) {
                    if (abl_state != STATE.EXPANDED) {
                        abl_state = STATE.EXPANDED;//修改状态标记为展开
                    }
                } else if (Math.abs(verticalOffset) >= appBarLayout.getTotalScrollRange()) {
                    if (abl_state != STATE.COLLAPSED) {
                        abl_state = STATE.COLLAPSED;//修改状态标记为折叠
                    }
                } else {
                    if (abl_state != STATE.INTERNEDIATE) {
                        abl_state = STATE.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });


    }

    public Object getObject(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());

    }

    public void setAblExpanded(Boolean b) {
        if (b) {
            abl_news_inform_list.setExpanded(true);
            abl_state = STATE.EXPANDED;
        } else {
            abl_news_inform_list.setExpanded(false);
            abl_state = STATE.COLLAPSED;
        }

    }

    public void onBackPressed() {
        if (abl_state != STATE.EXPANDED) {
            setAblExpanded(true);
            return;
        }
        startActivity(new Intent(NewsInformListActivity.this, MainActivity.class));
        overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        super.onBackPressed();
    }
}
