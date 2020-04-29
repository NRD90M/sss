package com.wfj.bmobstudy.Adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.wfj.bmobstudy.Bean.Sign;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.DateUtils;

import java.util.List;

/**
 * @description 描述一下方法的作用
 * @date: 2020/4/26
 * @author:
 */
public class SignHistoryAdapter extends ArrayAdapter<Sign> {
    private int resource;

    public SignHistoryAdapter(Context context, int resource, List<Sign> objects) {
        super(context, resource, objects);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        ViewHolder viewHolder;
        Sign sign = getItem(position);
        if (convertView == null) {
            v = LayoutInflater.from(getContext()).inflate(resource, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_sign_history = (TextView) v.findViewById(R.id.tv_sign_history);
            v.setTag(viewHolder);
        } else {
            v = convertView;
            viewHolder = (ViewHolder) v.getTag();
        }
        viewHolder.tv_sign_history.setText(DateUtils.dateFormat(sign.getSignDate(), 0));

        return v;
    }

    public class ViewHolder {
        TextView tv_sign_history;
    }
}
