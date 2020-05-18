package com.wfj.bmobstudy.Fragment.Education;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wfj.bmobstudy.Adapter.EducationOptionalCourseAdapter;
import com.wfj.bmobstudy.Bean.EducationOptionalCourse;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.Education.EducationOptionalCourseListUtil;
import com.vondear.rxtools.view.RxToast;

import java.util.List;

/**
 * @description 教务管理界面
 * @date: 2020/4/26
 * @author: a */
public class EducationFourFragment extends Fragment {
    private View rootView;
    private String current_url;
    private RecyclerView rcv_education;
    private int all_number;
    private int shown = 0;
    private int current_page = 1;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_item_education, container, false);
            rootView = v;
            current_url = getArguments().getString("url");
            initRecyclerView(v);
            initView(v);
        }
        return rootView;

    }

    public static EducationFourFragment getInstance(String url) {
        EducationFourFragment fragment = new EducationFourFragment();
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        fragment.setArguments(bundle);
        return fragment;

    }

    private void initView(View v) {
        EducationOptionalCourseListUtil.get_list(current_url, new EducationOptionalCourseListUtil.get_ListCall() {
            @Override
            public void success(final List<EducationOptionalCourse> list) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initData(list);
                        all_number = list.size();
                        shown = shown + list.size();
//                        current_page++;
                    }
                });

            }

            @Override
            public void fail() {
//                RxToast.error("教务公告获取失败！");
            }
        });
    }

    private void initRecyclerView(View v) {
        rcv_education = (RecyclerView) v.findViewById(R.id.rcv_education);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_education.setLayoutManager(manager);
    }

    private void initData(final List<EducationOptionalCourse> list) {
        final EducationOptionalCourseAdapter adapter = new EducationOptionalCourseAdapter(R.layout.item_education_optional_course, list);
        adapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                EducationWebFragment fragment = new EducationWebFragment();
                Bundle bundle = new Bundle();
                bundle.putString("url", list.get(position).getUrl());
                bundle.putSerializable("item", list.get(position));
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.fl_content, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (shown >= all_number) {
                    adapter.loadMoreEnd();
                } else {
                    EducationOptionalCourseListUtil.get_list(current_url, new EducationOptionalCourseListUtil.get_ListCall() {
                        @Override
                        public void success(final List<EducationOptionalCourse> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.addData(list);
                                    adapter.loadMoreComplete();
                                    shown = shown + list.size();
//                                    current_page++;
                                }
                            });
                        }

                        @Override
                        public void fail() {
                            RxToast.error("拉取教务信息失败！");
                        }
                    });
                }
            }
        }, rcv_education);
        rcv_education.setAdapter(adapter);
    }

    private String get_true_url() {
        String true_url = "";
        if (current_page == 1) {
            true_url = current_url;
        } else {
            //总页数,每页18条信息
            int all_page = (all_number % 18) == 0 ? (all_number / 18) : (all_number / 18 + 1);
            String suffix_url = "/" + (all_page + 1 - current_page) + ".htm";

            //公告动态模块
            if (current_url.contains("/xszl/shxqgxkjj")) {
                true_url = "http://jwch.usts.edu.cn/xszl/shxqgxkjj" + suffix_url;
            } else if (current_url.contains("/xszl/jfxqgxkjj")) {
                true_url = "http://jwch.usts.edu.cn/xszl/jfxqgxkjj" + suffix_url;
            } else if (current_url.contains("/xszl/tpxqgxkjj")) {
                true_url = "http://jwch.usts.edu.cn/xszl/tpxqgxkjj" + suffix_url;
            }
        }
        Log.e("true_page", true_url);
        return true_url;
    }
}
