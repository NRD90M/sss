package com.wfj.bmobstudy.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.SearchView;
import android.widget.SimpleAdapter;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.google.android.material.appbar.AppBarLayout;
import com.wfj.bmobstudy.Adapter.BookAdapter;
import com.wfj.bmobstudy.Adapter.BookRecommendAdapter;
import com.wfj.bmobstudy.Bean.Book;
import com.wfj.bmobstudy.Bean.Book_recommend;
import com.wfj.bmobstudy.Bean.SlideShow;
import com.wfj.bmobstudy.Fragment.SetMidFragment.BookItemFragment;
import com.wfj.bmobstudy.Fragment.SetMidFragment.LibraryInfoFragment;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.BookRecommendUtil;
import com.wfj.bmobstudy.Utils.BookUtil;
import com.wfj.bmobstudy.Utils.CallBackListener;
import com.wfj.bmobstudy.Utils.GlideImageLoader;
import com.wfj.bmobstudy.Utils.LibraryPictureUtil;
import com.wfj.bmobstudy.Utils.Toast;
import com.wfj.bmobstudy.View.MyGridView;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class LibraryActivity extends AppBaseActivity {
    private LinearLayout ly_back;
    private SearchView searchView;
    private RecyclerView recyclerView;
    private List<Book> book_list;
    //已经显示过的数目
    private int shown = 0;
    //显示的全部list集合
    private List<Book> all_book_list;
    List<SlideShow> libraryShowsList;//轮播图
    private Banner banner;
    private STATE abl_state;
    private Context context;
    //展开、折叠、中间
    private enum STATE {
        EXPANDED,
        COLLAPSED,
        INTERNEDIATE

    }
    private AppBarLayout abl_library;
    private MyGridView gridView;

    private LibraryInfoFragment fragment;
    //回退标记
    public static int i = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initViews();
        initCollapsingToolBarlayout();//处理界面折叠
        initGridView();//初始化网格按钮
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.activity_library;
    }

    //初始化view
    private void initViews() {
//        ly_back = (LinearLayout) findViewById(R.id.ly_back);
//        ly_back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed();
//            }
//        });

        getInfo_Banner();
        searchView = (SearchView) findViewById(R.id.sv);
        searchView.setSubmitButtonEnabled(true);
        recyclerView = (RecyclerView) findViewById(R.id.rv_book);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //book_list=null;
                BookUtil.page = 1;
                BookUtil.all_books = 0;
                initData(s);
                setAblExpanded(false);
                //搜索后失去焦点
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

    }
    //获取轮播图 信息
    private void getInfo_Banner() {
        LibraryPictureUtil.get_library_pic_info(new CallBackListener() {
            @Override
            public void onSuccess(List<SlideShow> list) {
                libraryShowsList = new ArrayList<>();
                libraryShowsList = LibraryPictureUtil.libraryShowsList;

                initBanner();
                Toast.show(context, "加载成功！", 1000);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

    }

    private void initData(final String str) {
        book_list = new ArrayList<>();
        all_book_list = new ArrayList<>();
        BookUtil.get_book_info(str, new BookUtil.list_complete() {
            @Override
            public void success(List<Book> list) {
                book_list = list;
                all_book_list.addAll(book_list);
                shown += book_list.size();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initRecycleView(str);
                    }
                });

            }
        });

    }


    private void initRecycleView(final String str) {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final BookAdapter bookAdapter = new BookAdapter(R.layout.item_book, book_list);

        bookAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //如果状态不为折叠时，统一改为折叠
                if (abl_state == STATE.EXPANDED || abl_state == STATE.INTERNEDIATE) {
                    setAblExpanded(false);
                }
                Bundle bundle = new Bundle();
                bundle.putString("referer_url", BookUtil.referer_url);
                bundle.putString("item_url", all_book_list.get(position).getUrl());
                BookItemFragment fragment = new BookItemFragment();
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_book, fragment).addToBackStack("detail").commit();


            }
        });

        //添加动画
        bookAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //该适配器提供了5种动画效果（渐显、缩放、从下到上，从左到右、从右到左）
        // ALPHAIN SCALEIN SLIDEIN_BOTTOM SLIDEIN_LEFT SLIDEIN_RIGHT

        //设置重复执行动画
        bookAdapter.isFirstOnly(false);

        //bookAdapter.disableLoadMoreIfNotFullPage();
        //上拉加载（设置这个监听就表示有上拉加载功能了）
        bookAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (shown >= BookUtil.all_books) {
                    //数据全部加载完毕
                    bookAdapter.loadMoreEnd();

                } else {
                    //成功获取数据
                    if (true) {
                        BookUtil.get_book_info(str, new BookUtil.list_complete() {
                            @Override
                            public void success(final List<Book> list) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        shown = shown + list.size();
                                        all_book_list.addAll(list);
                                        bookAdapter.addData(list);
                                        bookAdapter.loadMoreComplete();
                                    }
                                });
                            }
                        });

                    } else {
                        //获取更多数据失败
                        bookAdapter.loadMoreFail();
                    }
                }
            }
        }, recyclerView);


        recyclerView.setAdapter(bookAdapter);

    }


    public void initBanner() {
       this.runOnUiThread(new Runnable() {
          @Override
          public void run() {
              banner = (Banner) findViewById(R.id.banner);
              //设置banner样式
              banner.setBannerStyle(BannerConfig.NUM_INDICATOR_TITLE);
              //设置图片加载器
              banner.setImageLoader(new GlideImageLoader());

              List<String> url = new ArrayList<>();
              List<String> title = new ArrayList<>();
              final List<String> detail_url = new ArrayList<>();

              for (SlideShow slideShow : libraryShowsList) {
                  url.add(slideShow.getImg_url());
                  title.add(slideShow.getTitle());
                  detail_url.add(slideShow.getDetail_url());
              }
              banner.setImages(url);
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
                      //此时需要暂停轮播。否则返回时与当前点击的轮播页面不一致
                      banner.stopAutoPlay();
                      Intent i = new Intent(context, NewsInfoActivity.class);
                      i.putExtra("url", detail_url.get(position));
                      startActivity(i);
                      overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                  }
              });
              //banner设置方法全部调用完毕时最后调用
              banner.start();
          }
      });


    }

    private void initCollapsingToolBarlayout() {
        abl_library = (AppBarLayout) findViewById(R.id.abl_library);
        abl_library.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
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
                        if (abl_state == STATE.COLLAPSED) {
                            //playButton.setVisibility(View.GONE);//由折叠变为中间状态时隐藏播放按钮
                        }
                        // collapsingToolbarLayout.setTitle("INTERNEDIATE");//设置title为INTERNEDIATE
                        abl_state = STATE.INTERNEDIATE;//修改状态标记为中间
                    }
                }
            }
        });


    }

    public Object getObject(String name) {
        return getResources().getIdentifier(name, "drawable", getPackageName());

    }

    private void initGridView() {
        gridView = (MyGridView) findViewById(R.id.gv_library);
        String text[] = {"图书馆简介", "开放时间", "馆藏布局", "借阅指南", "机构联系", "热门推荐"};
        ArrayList<HashMap<String, Object>> arrayList = new ArrayList<HashMap<String, Object>>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("image", getObject("library_general"));
        map.put("text", text[0]);
        arrayList.add(map);
        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("image", getObject("library_time"));
        map1.put("text", text[1]);
        arrayList.add(map1);
        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("image", getObject("library_layout"));
        map2.put("text", text[2]);
        arrayList.add(map2);
        HashMap<String, Object> map3 = new HashMap<>();
        map3.put("image", getObject("library_rules"));
        map3.put("text", text[3]);
        arrayList.add(map3);
        HashMap<String, Object> map4 = new HashMap<>();
        map4.put("image", getObject("library_phone"));
        map4.put("text", text[4]);
        arrayList.add(map4);
        HashMap<String, Object> map5 = new HashMap<>();
        map5.put("image", getObject("library_recommend"));
        map5.put("text", text[5]);
        arrayList.add(map5);


        //适配器
        SimpleAdapter adapter = new SimpleAdapter(this,
                arrayList,
                R.layout.item_grid,
                new String[]{"image", "text"},
                new int[]{R.id.iv_grid, R.id.tv_grid});


        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int postion, long id) {
                fragment = new LibraryInfoFragment();
                Bundle b = new Bundle();
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_book, fragment).addToBackStack(null);
                //如果状态不为折叠时，统一改为折叠
                if (abl_state == STATE.EXPANDED || abl_state == STATE.INTERNEDIATE) {
                    setAblExpanded(false);
                }
                switch (postion) {
                    case 0://图书馆概况
                        String url = "http://tsg.asc.jx.cn/bggk/tsggk.htm";
                        b.putString("url", url);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 1://开放时间
                        String url1 = "http://tsg.asc.jx.cn/bggk/kfsj.htm";
                        b.putString("url", url1);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 2://管内布局
                        String url2 = "http://tsg.asc.jx.cn/gzzy/gzfb.htm";
                        b.putString("url", url2);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 3://规章制度--借阅指南
                        String url3 = "http://tsg.asc.jx.cn/fwzn/tsgjyzn.htm";
                        b.putString("url", url3);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 4://联系我们
                        String url4 = "http://tsg.asc.jx.cn/bggk/jgsz.htm";
                        b.putString("url", url4);
                        fragment.setArguments(b);
                        transaction.commit();
                        break;
                    case 5://热门推荐--新书推荐
                        BookRecommendUtil.get_recommend_list(new BookRecommendUtil.call_categories() {
                            @Override
                            public void success(final List<String> list) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        new MaterialDialog.Builder(LibraryActivity.this)
                                                .title("选择分类")
                                                .content("统计范围：2个月　统计方式：借阅次数")
                                                .negativeText("取消")
                                                .items(list)
                                                .itemsCallback(new MaterialDialog.ListCallback() {
                                                    @Override
                                                    public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                                        BookRecommendUtil.get_book_recommend(BookRecommendUtil.categories_url.get(position), new BookRecommendUtil.call_recommend() {
                                                            @Override
                                                            public void success(final List<Book_recommend> list) {
                                                                runOnUiThread(new Runnable() {
                                                                    @Override
                                                                    public void run() {
                                                                        initRecycleView_recommend(list);
                                                                    }
                                                                });
                                                            }
                                                        });
                                                    }
                                                })
                                                .show();
                                    }
                                });
                            }
                        });


                        break;

                }
            }
        });

    }


    public void onBackPressed() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count == 1) {
            String tag = getSupportFragmentManager().getBackStackEntryAt(0).getName();
            super.onBackPressed();
            searchView.setVisibility(View.VISIBLE);
            searchView.clearFocus();
            recyclerView.setVisibility(View.VISIBLE);

            if (tag != null && tag.equals("detail")) {
                return;
            }
            if (abl_library != null) {
                setAblExpanded(true);
            }
            return;
        }
        if (count == 0) {
            if (abl_state != STATE.EXPANDED) {
                setAblExpanded(true);
            } else {
                startActivity(new Intent(LibraryActivity.this, MainActivity.class));
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
                super.onBackPressed();
            }

        }


    }

    private void initRecycleView_recommend(List<Book_recommend> list) {
        //创建布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        final BookRecommendAdapter bookRecommendAdapter = new BookRecommendAdapter(R.layout.item_book_recommend, list);

        bookRecommendAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                //如果状态不为折叠时，统一改为折叠
                if (abl_state == STATE.EXPANDED || abl_state == STATE.INTERNEDIATE) {
                    setAblExpanded(false);
                }
                Bundle bundle = new Bundle();
                bundle.putString("referer_url", BookRecommendUtil.categories_url.get(position));
                bundle.putString("item_url", BookRecommendUtil.list.get(position).getUrl());
                BookItemFragment fragment = new BookItemFragment();
                fragment.setArguments(bundle);
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.replace(R.id.fl_book, fragment).addToBackStack("detail").commit();


            }
        });

        //添加动画
        bookRecommendAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        //该适配器提供了5种动画效果（渐显、缩放、从下到上，从左到右、从右到左）
        // ALPHAIN SCALEIN SLIDEIN_BOTTOM SLIDEIN_LEFT SLIDEIN_RIGHT

        //设置重复执行动画
        bookRecommendAdapter.isFirstOnly(false);

        //bookAdapter.disableLoadMoreIfNotFullPage();
        //上拉加载（设置这个监听就表示有上拉加载功能了）
        bookRecommendAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                // if (shown >= BookUtil.all_books) {
                //数据全部加载完毕
                bookRecommendAdapter.loadMoreEnd();

//                } else {
//                    //成功获取数据
//                    if (true) {
//                        BookUtil.get_book_info(str, new BookUtil.list_complete() {
//                            @Override
//                            public void success(final List<Book> list) {
//                                runOnUiThread(new Runnable() {
//                                    @Override
//                                    public void run() {
//                                        shown = shown + list.size();
//                                        all_book_list.addAll(list);
//                                        bookAdapter.addData(list);
//                                        bookAdapter.loadMoreComplete();
//                                    }
//                                });
//                            }
//                        });
//
//                    } else {
//                        //获取更多数据失败
//                        bookAdapter.loadMoreFail();
//                    }
//                }
            }
        }, recyclerView);
        recyclerView.setAdapter(bookRecommendAdapter);
    }

    public void setAblExpanded(Boolean b) {
        if (b) {
            abl_library.setExpanded(true);
            abl_state = STATE.EXPANDED;
        } else {
            abl_library.setExpanded(false);
            abl_state = STATE.COLLAPSED;
        }

    }
}