package com.wfj.bmobstudy.Fragment.Volunteer;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.wfj.bmobstudy.Adapter.VolunteerAdapter;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.wfj.bmobstudy.Utils.Volunteer.VolunteerUtil;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class VolunteerListFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_name;
    private TextView tv_time;
    private RecyclerView recyclerView;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_volunteer_list, null, false);
        initView(v);
        initRecyclerView(v);
        return v;
    }

    private void initView(View v) {
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        tv_name = (TextView) v.findViewById(R.id.tv_volunteer_name);
        tv_time = (TextView) v.findViewById(R.id.tv_volunteer_time);
        tv_name.setText(VolunteerUtil.name);
        tv_time.setText(VolunteerUtil.time);
    }

    private void initRecyclerView(View v) {
        recyclerView = (RecyclerView) v.findViewById(R.id.rv_volunteer_list);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        initRecyclerViewData();

    }

    private void initRecyclerViewData() {
        VolunteerAdapter adapter = new VolunteerAdapter(R.layout.item_volunteer, VolunteerUtil.list);
        adapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_BOTTOM);
        adapter.isFirstOnly(false);
        recyclerView.setAdapter(adapter);
    }
}
