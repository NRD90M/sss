package com.wfj.bmobstudy.Fragment.Entry;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.SearchView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.Adapter.EntryCategoryAdapter;
import com.wfj.bmobstudy.Bean.EntryCategory;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.Entry.EntryCategoryUtil;
import com.wfj.bmobstudy.Utils.Entry.EntryUtil;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;


import java.util.ArrayList;
import java.util.List;

/**
 * @description 江理词条
 * @date: 2020/4/26
 * @author: a */
public class EntryCategoryActivity extends AppBaseActivity {
    private LinearLayout ly_back;
    private RecyclerView recyclerView;
    //词条分类的总数
    private int all_number = 0;
    //当前已经显示的词条数目
    private int shown = 0;
    private View rootView;
    private SearchView searchView;
    private Context context;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        initView();
        //存储时间不为当天则删除
        EntryUtil.delete_entry_id();
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.fragment_entry_category;
    }

    private void initView() {
        initRecyclerView(context);
        initSearchView();
    }

    private void initSearchView() {
        searchView = (SearchView) findViewById(R.id.sv_entry_category);
        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                EntryCategoryUtil.get_entry_category_mohu(s, new EntryCategoryUtil.mohu_categoryCall() {
                    @Override
                    public void success(final List<EntryCategory> list) {
                        initSearchViewData(list);
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initSearchViewData(final List<EntryCategory> list) {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                List<String> list1 = new ArrayList<String>();
                for (EntryCategory entryCategory : list) {
                    list1.add(entryCategory.getName() + "      " + entryCategory.getNumber() + "条知识");
                }
                new MaterialDialog.Builder(context)
                        .title("选择词条分类")
                        .negativeText("取消")
                        .items(list1)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                change_to_entryFragment(list, position);
                            }
                        })
                        .show();
            }
        });
    }

    private void initRecyclerView(Context context) {
        recyclerView = (RecyclerView) findViewById(R.id.rv_entry_category);
        LinearLayoutManager manager = new LinearLayoutManager(context);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        EntryCategoryUtil.get_entry_category_number(new EntryCategoryUtil.numberCall() {
            @Override
            public void success(int number) {
                all_number = number;
                EntryCategoryUtil.get_all_entry_category(new EntryCategoryUtil.categoryCall() {
                    public void success(final List<EntryCategory> entryCategoryList) {
                        shown = shown + entryCategoryList.size();
                        getParent().getParent().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                initRecyclerViewData(entryCategoryList, false);
                            }
                        });

                    }

                    public void fail() {

                    }
                });

            }
        });

    }

    private void initRecyclerViewData(final List<EntryCategory> list, boolean isSearch) {
        final EntryCategoryAdapter adapter = new EntryCategoryAdapter(R.layout.item_entry_category, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                change_to_entryFragment(list, position);
            }
        });
        if (!isSearch) {
            adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (shown >= all_number) {
                        adapter.loadMoreEnd();
                    } else {
                        EntryCategoryUtil.get_all_entry_category(new EntryCategoryUtil.categoryCall() {
                            @Override
                            public void success(final List<EntryCategory> entryCategoryList) {
                                getParent().runOnUiThread(new Runnable() {
                                    public void run() {
                                        if (entryCategoryList.size() == 0) {
                                            adapter.loadMoreEnd();
                                            return;
                                        }
                                        adapter.addData(entryCategoryList);
                                        adapter.loadMoreComplete();
                                        shown = shown + entryCategoryList.size();
                                    }
                                });
                            }

                            public void fail() {
                                adapter.loadMoreFail();
                            }
                        });
                    }
                }
            }, recyclerView);
        }
        recyclerView.setAdapter(adapter);
    }

    private void change_to_entryFragment(List<EntryCategory> list, int position) {
        EntryFragment fragment = new EntryFragment();
        Bundle bundle = new Bundle();
        bundle.putString("categoryId", list.get(position).getObjectId());
        bundle.putString("category", list.get(position).getName());
        fragment.setArguments(bundle);
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                .replace(R.id.fl_content, fragment)
                .addToBackStack("EntryFragment")
                .commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EntryCategoryUtil.current_page = 0;
    }
}
