package com.wfj.bmobstudy.Activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wfj.bmobstudy.Adapter.ExercisesListItemAdapter;
import com.wfj.bmobstudy.Adapter.VideoListItemAdapter;
import com.wfj.bmobstudy.Bean.ExercisesBean;
import com.wfj.bmobstudy.Bean.VideoBean;
import com.wfj.bmobstudy.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VideoListActivity extends Activity{
    private TextView tvIntro;
    private TextView tvVideo;
    private TextView tv_test;
    private ListView lvVideoList;
    private ListView lv_list;
    private ScrollView svChapterIntro;
    private TextView tvChapterIntro;
    private List<VideoBean> videoList;
    private int chapterId;
    private String intro;
    private VideoListItemAdapter adapter;
    private ExercisesListItemAdapter exercisesListItemAdapter;
    private List<ExercisesBean> ebl; //列表集合

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);

        chapterId = getIntent().getIntExtra("id", 0);
        intro = getIntent().getStringExtra("intro");

        initData();
        initView();
    }

    //把json文件转换为字符串
    private String read(InputStream is){
        BufferedReader reader = null;
        StringBuilder sb = null;
        String line = null;

        try{
            sb = new StringBuilder();//实例化一个StringBuilder对象
            //用InputStreamReader把in这个字节流转化成字符流BufferReader
            reader = new BufferedReader(new InputStreamReader(is));

            while ((line = reader.readLine()) != null){
                sb.append(line);
                sb.append("\n");
            }
        }catch (IOException e){
            e.printStackTrace();
            return "";
        }finally {
            if (is != null){
                try{
                    is.close();
                    if (reader != null){
                        reader.close();
                    }
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }

    //解析json
    private void initData() {
        //JSONArray jsonArray;
        JSONArray jsonArray,jsonArray1;
        List<String> list = new ArrayList<String>();
        List<VideoBean> list1 = new ArrayList<VideoBean>();

        try{
            InputStream is = getResources().getAssets().open("data.json");
            jsonArray = new JSONArray(read(is));
            videoList = new ArrayList<VideoBean>();
            for (int i = 0; i < jsonArray.length(); i++){
                VideoBean bean = new VideoBean();
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                if (jsonObj.getInt("chapterId") == chapterId){
                    bean.chapterId = jsonObj.getInt("chapterId");

                    /*bean.videoId = Integer.parseInt(jsonObj.getString("videoId"));
                    bean.title = jsonObj.getString("title");
                    bean.secondTitle = jsonObj.getString("secondTitle");
                    bean.videoPath = jsonObj.getString("videoPath");
                    videoList.add(bean);*/
                    String ss =jsonObj.getString("data");
                    jsonArray1 = new JSONArray(ss);
                    for (int j=0;j<jsonArray1.length();j++){
                        JSONObject jsonObject = (JSONObject) jsonArray1.get(j);

                        Iterator<String> iterator = jsonObject.keys();
                        while (iterator.hasNext()){
                            String key = iterator.next();
                            String value = jsonObject.getString(key);
                            list.add(value);
                        }
                        bean.videoId = Integer.parseInt(list.get(0));
                        bean.title = list.get(1);
                        bean.secondTitle = list.get(2);
                        bean.videoPath = list.get(3);
                        videoList.add(bean);
                        bean = new VideoBean();
                        list.clear();
                        Log.i("Ss",videoList.toString());
                    }

                }
                bean = null;
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }

        //静态题目列表界面
        ebl = new ArrayList<ExercisesBean>();
        for (int i=0;i<10;i++){
            ExercisesBean bean = new ExercisesBean();
            bean.id=(i+1);
            switch (i){
                case 0:
                    bean.title="第1章 Android基础入门";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 1:
                    bean.title="第2章 Android UI开发";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 2:
                    bean.title="第3章 Activity";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 3:
                    bean.title="第4章 数据存储";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 4:
                    bean.title="第5章 SQLite数据库";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 5:
                    bean.title="第6章 广播接收者";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 6:
                    bean.title="第7章 服务";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 7:
                    bean.title="第8章 内容提供者";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 8:
                    bean.title="第9章 网络编程";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                case 9:
                    bean.title="第10章 高级编程";
                    bean.content="共计5题";
                    bean.background=(R.drawable.exercises_bg_1);
                    break;
                default:
                    break;
            }
            ebl.add(bean);
        }
    }

    private void initView() {
        tvIntro = (TextView) findViewById(R.id.tv_intro);
        tvVideo = (TextView) findViewById(R.id.tv_video);
        tv_test = (TextView)findViewById(R.id.tv_test);
        lvVideoList = (ListView) findViewById(R.id.lv_video_list);
        lv_list = (ListView)findViewById(R.id.lv_list);
        svChapterIntro = (ScrollView) findViewById(R.id.sv_chapter_intro);
        tvChapterIntro = (TextView) findViewById(R.id.tv_chapter_intro);
        adapter = new VideoListItemAdapter(this);
        lvVideoList.setAdapter(adapter);
        adapter.setData(videoList);
        exercisesListItemAdapter = new ExercisesListItemAdapter(this);
        lv_list.setAdapter(exercisesListItemAdapter);
        exercisesListItemAdapter.setData(ebl);

        tvChapterIntro.setText(intro);
        tvIntro.setBackgroundColor(Color.parseColor("#30B4FF"));
        tvVideo.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tv_test.setBackgroundColor(Color.parseColor("#FFFFFF"));
        tvIntro.setTextColor(Color.parseColor("#FFFFFF"));
        tvVideo.setTextColor(Color.parseColor("#000000"));
        tv_test.setTextColor(Color.parseColor("#000000"));

        tvIntro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvVideoList.setVisibility(View.GONE);
                svChapterIntro.setVisibility(View.VISIBLE);
                lv_list.setVisibility(View.GONE);

                tvIntro.setBackgroundColor(Color.parseColor("#30B4FF"));
                tvVideo.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_test.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tvIntro.setTextColor(Color.parseColor("#FFFFFF"));
                tvVideo.setTextColor(Color.parseColor("#000000"));
                tv_test.setTextColor(Color.parseColor("#000000"));

            }
        });
        tvVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvVideoList.setVisibility(View.VISIBLE);
                svChapterIntro.setVisibility(View.GONE);
                lv_list.setVisibility(View.GONE);

                tvIntro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvVideo.setBackgroundColor(Color.parseColor("#30B4FF"));
                tv_test.setBackgroundColor(Color.parseColor("#FFFFFF"));

                tvIntro.setTextColor(Color.parseColor("#000000"));
                tvVideo.setTextColor(Color.parseColor("#FFFFFF"));
                tv_test.setTextColor(Color.parseColor("#000000"));

            }
        });
        tv_test.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lvVideoList.setVisibility(View.GONE);
                svChapterIntro.setVisibility(View.GONE);
                lv_list.setVisibility(View.VISIBLE);

                tvIntro.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tvVideo.setBackgroundColor(Color.parseColor("#FFFFFF"));
                tv_test.setBackgroundColor(Color.parseColor("#30B4FF"));

                tvIntro.setTextColor(Color.parseColor("#000000"));
                tvVideo.setTextColor(Color.parseColor("#000000"));
                tv_test.setTextColor(Color.parseColor("#FFFFFF"));
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);
        //把视频详情界面传递过来的被点击视频的位置传递回去
        if (data != null){
            int position = data.getIntExtra("position", 0);
            //设置被选中的位置
            adapter.setSelectedPosition(position);
            // 选项卡被选中时所有图标的颜色值
            lvVideoList.setVisibility(View.VISIBLE);
            svChapterIntro.setVisibility(View.GONE);
            tvIntro.setBackgroundColor(Color.parseColor("#FFFFFF"));
            tvVideo.setBackgroundColor(Color.parseColor("#30B4FF"));
            tvIntro.setTextColor(Color.parseColor("#000000"));
            tvVideo.setTextColor(Color.parseColor("#FFFFFF"));
        }
    }

}
