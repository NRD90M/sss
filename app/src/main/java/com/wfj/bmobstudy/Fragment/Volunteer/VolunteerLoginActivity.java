package com.wfj.bmobstudy.Fragment.Volunteer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.TimeUtils;
import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.CookieManager;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.wfj.bmobstudy.Utils.Volunteer.VolunteerUtil;
import com.vondear.rxtools.view.RxToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @description 志愿者时间
 * @date: 2020/4/26
 * @author: a */
public class VolunteerLoginActivity extends AppBaseActivity {
    private Context context;
    private LinearLayout ly_back;
    private EditText et_phone;
    private EditText et_pd;
    private EditText et_code;
    private ImageView iv_code;
    private Button btn_get_code;
    private Button btn_login;
    private OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.fragment_volunteer_login;
    }


    private void initView() {
        et_phone = (EditText) findViewById(R.id.et_volunteer_phone);
        et_pd = (EditText) findViewById(R.id.et_volunteer_pd);
        et_code = (EditText) findViewById(R.id.et_volunteer_code);
        iv_code = (ImageView) findViewById(R.id.iv_volunteer_code);
        btn_get_code = (Button) findViewById(R.id.btn_volunteer_get_code);
        btn_login = (Button)findViewById(R.id.btn_volunteer_login);
        btn_get_code.setOnClickListener(new OnGetCodeClickListener());
        btn_login.setOnClickListener(new OnLoginClickListener());
        et_phone.setText(SPUtils.getInstance("volunteer").getString("phone", ""));
        et_pd.setText(SPUtils.getInstance("volunteer").getString("pd", ""));
    }

    //打开网址，获得JSESSIONID
    private void open(final idCall idCall) {
        Request request = new Request.Builder()
                .url("http://www.dakaqi.cn/login?returnUrl=/createorg")
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                CookieManager.subCookie(response);
                cookie.JSESSIONID = CookieManager.get_single_cookie("JSESSIONID");
                idCall.success();
                Log.e("tag", cookie.JSESSIONID);
            }
        });
    }

    //获取验证码
    private void get_code() {
        //获得当前时间的毫秒戳
        Long time = TimeUtils.getNowMills();
        Log.e("tag", "long  " + time);
        String url = "http://www.dakaqi.cn/stickyImg.do?t=" + time;
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host", "www.dakaqi.cn")
                .addHeader("Referer", "http://www.dakaqi.cn/login?returnUrl=/createorg")
                .addHeader("Cookie", "JSESSIONID=" + cookie.JSESSIONID)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                getParent().runOnUiThread(new Runnable() {
                    public void run() {
                        iv_code.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    //尝试登陆
    private void login() {
        final String phone = et_phone.getText().toString().trim();
        final String pd = et_pd.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (TextUtils.isEmpty(phone) || TextUtils.isEmpty(pd) || TextUtils.isEmpty(code)) {
            RxToast.error("手机号、密码、验证码不能为空！");
            return;
        }
        RequestBody body = new FormBody.Builder()
                .add("autoLogin", "0")
                .add("checkCode", code)
                .add("mobile", phone)
                .add("password", pd)
                .add("returnUrl", "/createorg")
                .build();
        final Request request = new Request.Builder()
                .url("http://www.dakaqi.cn/login")
                .post(body)
                .addHeader("Host", "www.dakaqi.cn")
                .addHeader("Referer", "http://www.dakaqi.cn/login?returnUrl=/createorg")
                .addHeader("Cookie", "JSESSIONID=" + cookie.JSESSIONID)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                final String state = VolunteerUtil.handle_login_state(res);
                int code = VolunteerUtil.handle_login_code(res);
                if (code == 1) {
                    getParent().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RxToast.success(state);
                        }
                    });
                    record(phone, pd);
                    get_special_url();

                } else {
                    getParent().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            RxToast.error(state);
                        }
                    });
                    if (state.contains("验证码")) {
                        get_code();
                    }
                    if (state.contains("密码")) {
                        getParent().runOnUiThread(new Runnable() {
                            public void run() {
                                et_phone.setText("");
                                et_pd.setText("");
                            }
                        });
                    }
                }
            }
        });
    }

    //每个人的历程url都不一样，需要先拿到url
    private void get_special_url() {
        Request request = new Request.Builder()
                .url("http://www.dakaqi.cn/member/info-center.action")
                .addHeader("Host", "www.dakaqi.cn")
                .addHeader("Referer", "http://www.dakaqi.cn/createorg")
                .addHeader("Cookie", "JSESSIONID=" + cookie.JSESSIONID)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().string());
                String temp = document.select("a[class=subtn]").get(1).attr("href");
                String url = "http://www.dakaqi.cn" + temp;
                get_list(url);

            }
        });
    }

    //模拟点击我的历程
    private void get_list(String url) {
        Request request = new Request.Builder()
                .url(url)
                .addHeader("Host", "www.dakaqi.cn")
                .addHeader("Referer", "http://www.dakaqi.cn/member/info-center.action")
                .addHeader("Cookie", "JSESSIONID=" + cookie.JSESSIONID)
                .build();
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            public void onResponse(Call call, Response response) throws IOException {
                String html = response.body().string();
                Document document = Jsoup.parse(html);
                Element element = document.select("div[class=f_left]").get(1);
                String name = element.select("span").text();
                String time = element.text().split("时间：")[1];
                VolunteerUtil.name = name;
                VolunteerUtil.time = "累计公益时间：" + time;
                VolunteerUtil.get_list(html, new VolunteerUtil.listCall() {
                    @Override
                    public void success() {
                        FragmentManager manager = getSupportFragmentManager();
                        manager.beginTransaction()
                                .setCustomAnimations(R.anim.in_from_right, R.anim.out_to_left)
                                .replace(R.id.viewPager, new VolunteerListFragment())
                                .addToBackStack(null)
                                .commit();
                    }
                });

            }
        });


    }

    //记录当前手机号与密码
    private void record(String phone, String pd) {
        SPUtils.getInstance("volunteer").put("phone", phone);
        SPUtils.getInstance("volunteer").put("pd", pd);
    }


    private class OnGetCodeClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            open(new idCall() {
                @Override
                public void success() {
                    get_code();
                }
            });
        }
    }

    private class OnLoginClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            login();
        }
    }
}


class cookie {
    public static String JSESSIONID = "";
}

interface idCall {
    void success();
}

