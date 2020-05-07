package com.wfj.bmobstudy.Fragment.SetMidFragment;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.wfj.bmobstudy.Bean.User;
import com.wfj.bmobstudy.Constant.UstsValue;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.ClientInstance;
import com.wfj.bmobstudy.Utils.HttpUtils;
import com.wfj.bmobstudy.Utils.IsEmptyUtils;
import com.wfj.bmobstudy.Utils.ShowDialogUtil;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.wfj.bmobstudy.Utils.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @description 验证学生身份界面
 * @date: 2020/4/26
 * @author: a */
public class StudentVerifyFragment extends Fragment {
    private LinearLayout ly_back;
    private TextView tv_info;
    private EditText et_stu_id;
    private EditText et_stu_pd;
    private EditText et_code;
    private ImageView iv_code;
    private Button btn_get_code;
    private Button btn_verify;
    private String stu_info_url;
    private OkHttpClient client;
    //点击教务系统网络地址的随机数值
    private String random_string;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_student_verify, container, false);
        initViews(v);
        return v;
    }

    private void initViews(View v) {
        ly_back = (LinearLayout) v.findViewById(R.id.ly_back);
        ly_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        ShowOrHiddenUtil.hidden_home_bottom(getActivity());
        tv_info = (TextView) v.findViewById(R.id.tv_info);
        et_stu_id = (EditText) v.findViewById(R.id.et_stu_id);
        et_stu_pd = (EditText) v.findViewById(R.id.et_stu_pd);
        IsEmptyUtils.judeg_is_null(tv_info, "st_id", "验证学生身份", "修改已验证的学生身份", et_stu_id);
        et_code = (EditText) v.findViewById(R.id.et_code);//写入的验证码
        iv_code = (ImageView) v.findViewById(R.id.iv_code);//显示的验证码
        btn_get_code = (Button) v.findViewById(R.id.btn_get_code);//获取验证码按钮
        btn_get_code.setOnClickListener(new getCodeOnClickListener());
        btn_verify = (Button) v.findViewById(R.id.btn_verify);//验证按钮
        btn_verify.setOnClickListener(new LoginOnClickListener());


    }

    private class getCodeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            ShowDialogUtil.showProgressDialog(getActivity(), "正在获取验证码...");
            //getVerifCode();
            get_url_string();
        }
    }

    private class LoginOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            String name = et_stu_id.getText().toString().trim();
            String pwd = et_stu_pd.getText().toString().trim();
            String code = et_code.getText().toString().trim();
            login(name, pwd, code);
        }
    }

    /**
     * 获取点击教务管理系统后的重定向的地址，以此作为基地址
     */
    public void get_url_string() {
        client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(UstsValue.two_options_click)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                //提取随机的字符串的值
                Date dt = new Date();
                String url_string = response.request().url().toString()+"?t="+dt.getSeconds();

//                String a = url_string.split("\\(")[1];
//                String b = a.split("\\)")[0];
                random_string = url_string;
                getVerifCode();
            }
        });

    }

    /**
     * 获取验证码
     */
    private void getVerifCode() {
        String code_url = random_string;
        HttpUtils.sendGetRequest(code_url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                        iv_code.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    /**
     * 尝试登录
     */
    private void login(final String name, final String pwd, String code) {
//        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(pwd) || TextUtils.isEmpty(code)) {
//            Toast.show(getActivity(), "列表项不能为空!", 1);
//            return;
//        }
        ShowDialogUtil.showProgressDialog(getActivity(), "正在验证...");
        RequestBody body = new FormBody.Builder()
                .add("__VIEWSTATE", "/wEPDwULLTE0ODQyODIyNTBkZM6Do/HM9LKr0gOBzrM9ipj/wQ74Nn/AtoZflBbl7AUN")
                .add("__VIEWSTATEGENERATOR", "56911C19")
                .add("__EVENTVALIDATION","/wEdAAJaXCKP/JV8ptIbXj6Bxq3NZ5IuKWa4Qm28BhxLxh2oFLnU/fLbvTaQmXeaLfYvSIkf9WpKFbKVGjVEdX1qeJgY")
                .add("pcInfo","Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36undefined5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/79.0.3945.88 Safari/537.36 SN:NULL")
                .add("txt_mm_expression","")
                .add("txt_mm_length","")
                .add("txt_mm_userzh","")
                .add("typeName","学生")
                .add("dsdsdsdsdxcxdfgfg","")
                .add("fgfggfdgtyuuyyuuckjg","")

//                .add("Button1", "")
//                .add("hidPdrs", "")
//                .add("hidsc", "")
//                .add("lbLanguage", "")
//                .add("RadioButtonList1", "%D1%A7%C9%FA")
//                .add("Textbox1", "")
                .add("txt_psasas", "smc231105?")//密码
                .add("txt_sdertfgsadscxcadsads", code)//验证码
                .add("txt_asmcdefsddsd", "08070116222")//账号
                .build();
        String login_url = "http://jw.asc.jx.cn/_data/login_home.aspx";
        HttpUtils.sendPostRequest(login_url, body, new okhttp3.Callback() {

            public void onFailure(Call call, IOException e) {
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                        Toast.show(getActivity(), "验证失败！", 0);
                    }
                });
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String url = response.request().url().toString();
                Log.e("tag", url);
                UstsValue.login_success_url = "http://jw.asc.jx.cn/MAINFRM.aspx";
                //String id = url.substring(url.length() - 11, url.length());
                UstsValue.login_success_main_html = "http://jw.asc.jx.cn/MAINFRM.aspx";
                Log.e("tag", "urlurlurl" + url);
                Log.e("tag", "et_stu_id" + et_stu_id.getText().toString());
//                if (url.contains(et_stu_id.getText().toString().trim())) {
                    User u = BmobUser.getCurrentUser(User.class);
                    String objectId = u.getObjectId();
                    Log.e("tagg", name + "    name");
                    Log.e("tagg", pwd + "    pwd");
                    u.setSt_id(name);
                    u.setSt_pd(pwd);
                    u.update(objectId, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if (e == null) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.show(getActivity(), "验证成功！", 0);
                                        ShowDialogUtil.closeProgressDialog();
                                        get_info_html();
                                        getActivity().onBackPressed();
                                    }
                                });
                            }
                        }
                    });
//                } else {
//                    get_url_string();
//                    getActivity().runOnUiThread(new Runnable() {
//                        @Override
//                        public void run() {
//                            ShowDialogUtil.closeProgressDialog();
//                            Toast.show(getActivity(), "验证失败！", 0);
//                            et_code.setText("");
//
//                        }
//                    });
//                }
            }
        });
    }

    /**
     * 获取个人信息地址
     */
    private void get_info_url() {
//        Document document = Jsoup.parse(UstsValue.login_success_main_html);
//        Elements element1 = document.getElementsByAttributeValue("frame", "frmbody");
        Document document = null;
        try {
            document = Jsoup.connect(UstsValue.login_success_main_html).timeout(5000).get();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Elements elements = document.select("td[value]");
        for (Element element : elements) {
            if (element.text().equals("基本信息")) {
                stu_info_url = "http://jw.asc.jx.cn/ "+ element.attr("value");
                Log.e("wenfujin", "get_info_url: "+stu_info_url );
            }
        }
        Log.e("tag", stu_info_url);
    }

    /**
     * 获取个人信息的网页html
     */
    private void get_info_html() {
        get_info_url();
        client = ClientInstance.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                client = ClientInstance.getInstance();
                Request request = new Request.Builder()
                        .url(stu_info_url)
                        .addHeader("Host", "jw.usts.edu.cn")
                        .addHeader("Referer", UstsValue.login_success_url)
                        .addHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.3; WOW64; Trident/7.0; rv:11.0) like Gecko")
                        .build();
                client.newCall(request).enqueue(new Callback() {

                    public void onFailure(Call call, IOException e) {
                    }

                    public void onResponse(Call call, Response response) throws IOException {
                        UstsValue.stu_info_html = response.body().string();
                        //Log.e("tag", UstsValue.stu_info_html);
                        set_stu_info();
                    }
                });
            }
        }).start();
    }

    /**
     * 获取信息后，保存
     */
    private void set_stu_info() {
        Document document = Jsoup.parse(UstsValue.stu_info_html);
        Elements elements = document.select("span[id]");
        String st_name = "";
        String sex = "";
        String department = "";
        String major = "";
        String st_identity = "";
        String address = "";
        int grade = 0;
        String classroom = "";
        for (Element element : elements) {
            String id = element.attr("id");
            if (id.equals("xm")) {
                st_name = element.text();
            }
            if (id.equals("lbl_xb")) {
                sex = element.text();
            }
            if (id.equals("lbl_sfzh")) {
                st_identity = element.text();
            }
            if (id.equals("lbl_xy")) {
                department = element.text();
            }
            if (id.equals("lbl_jtszd")) {
                address = element.text();
            }
            if (id.equals("lbl_zymc")) {
                major = element.text();
            }
            if (id.equals("lbl_dqszj")) {
                grade = Integer.parseInt(element.text());
            }
            if (id.equals("lbl_xzb")) {
                classroom = element.text();
            }
        }
        User u = BmobUser.getCurrentUser(User.class);
        String objectId = u.getObjectId();
        u.setAcademy(department);
        u.setAddress(address);
        u.setClassroom(classroom);
        u.setGrade(grade);
        u.setMajor(major);
        u.setSt_identity(st_identity);
        u.setSt_name(st_name);
        u.setSex(sex);
        u.update(objectId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
            }
        });
    }


}
