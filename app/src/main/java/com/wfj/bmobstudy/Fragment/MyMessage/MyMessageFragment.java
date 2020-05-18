package com.wfj.bmobstudy.Fragment.MyMessage;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wfj.bmobstudy.Adapter.MyMessageAdapter;
import com.wfj.bmobstudy.Bean.Message;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.MyMessageUtil;
import com.wfj.bmobstudy.Utils.ShowDialogUtil;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;

import java.util.List;

/**
 * @description 我的消息
 * @date: 2020/4/26
 * @author: a */
public class MyMessageFragment extends Fragment {
    private LinearLayout ly_back;
    private RecyclerView rcv_message;
    private View rootView;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (rootView == null) {
            View v = inflater.inflate(R.layout.fragment_my_message, null, false);
            rootView = v;
            MyMessageUtil.shown = 0;
            MyMessageUtil.current_page = 0;
            initView(v);
            initData();
        }
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        return rootView;
    }

    private void initView(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });

        ShowDialogUtil.showProgressDialog(getActivity(), "正在获取消息...");
        initRecyclerView(v);
        MyMessageUtil.get_message_list(new MyMessageUtil.get_messageCall() {
            @Override
            public void success(List<Message> list) {
                initRecyclerViewData(list);
            }

            @Override
            public void fail() {
            }
        });
    }

    private void initRecyclerView(View v) {
        rcv_message = (RecyclerView) v.findViewById(R.id.rcv_message);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        rcv_message.setLayoutManager(manager);
    }

    private void initRecyclerViewData(final List<Message> list) {
        final MyMessageAdapter adapter = new MyMessageAdapter(R.layout.item_my_message, list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(true);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                FragmentManager manager = getActivity().getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                MessageDetailFragment fragment = new MessageDetailFragment();
                Bundle bundle = new Bundle();
                bundle.putSerializable("item", list.get(position));
                fragment.setArguments(bundle);
                transaction
                        .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                        .replace(R.id.viewPager, fragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (MyMessageUtil.shown >= MyMessageUtil.all) {
                    adapter.loadMoreEnd();
                } else {
                    MyMessageUtil.get_message_list(new MyMessageUtil.get_messageCall() {
                        @Override
                        public void success(final List<Message> list) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    adapter.addData(list);
                                    adapter.loadMoreComplete();
                                }
                            });
                        }

                        @Override
                        public void fail() {
                            adapter.loadMoreFail();
                        }
                    });
                }
            }
        }, rcv_message);
        rcv_message.setAdapter(adapter);

    }

    private void initData() {
        //获取消息总数
        MyMessageUtil.get_all(new MyMessageUtil.get_allCall() {
            @Override
            public void success() {
                //成功之后关闭dialog
                ShowDialogUtil.closeProgressDialog();
            }
        });
    }
}
