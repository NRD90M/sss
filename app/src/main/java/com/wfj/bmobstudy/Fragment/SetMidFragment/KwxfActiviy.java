package com.wfj.bmobstudy.Fragment.SetMidFragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.wfj.bmobstudy.Activity.AppBaseActivity;
import com.wfj.bmobstudy.Adapter.KwxfAdapter;
import com.wfj.bmobstudy.Bean.Kwxf;
import com.wfj.bmobstudy.Constant.UstsValue;
import com.wfj.bmobstudy.R;
import com.wfj.bmobstudy.Utils.HttpUtils;
import com.wfj.bmobstudy.Utils.IsEmptyUtils;
import com.wfj.bmobstudy.Utils.ShowDialogUtil;
import com.wfj.bmobstudy.Utils.ShowOrHiddenUtil;
import com.wfj.bmobstudy.Utils.Toast;
import com.vondear.rxtools.view.RxToast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * @description 学分查询
 * @date: 2020/4/26
 * @author: a */
public class KwxfActiviy extends AppBaseActivity {
    private Context context;
    private LinearLayout ly_back;
    private EditText et_kwxf_code;
    private ImageView iv_kwxf_code;
    private Button btn_kwxf_get_code;
    private Button btn_kwxf_select;
    private LinearLayout ly_kwxf_select;
    private ListView lv_kwxf_list;
    private List<Kwxf> kwxf_list;
    private String cookie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        intiViews();

    }

    /**
     * 返回 Activity Layout
     *
     * @return Layout ID
     */
    @Override
    public int setLayoutView() {
        return R.layout.fragment_kwxf;
    }

    private void intiViews() {
        et_kwxf_code = (EditText) findViewById(R.id.et_kwxf_code);
        iv_kwxf_code = (ImageView) findViewById(R.id.iv_kwxf_code);
        btn_kwxf_get_code = (Button) findViewById(R.id.btn_kwxf_get_code);
        btn_kwxf_get_code.setOnClickListener(new GetCodeOnClickListener());
        btn_kwxf_select = (Button) findViewById(R.id.btn_kexf_select);
        btn_kwxf_select.setOnClickListener(new SelectOnClickListener());
        ly_kwxf_select = (LinearLayout) findViewById(R.id.ly_kwxf_select);
        lv_kwxf_list = (ListView) findViewById(R.id.lv_kwxf);

    }


    private class GetCodeOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            if (TextUtils.isEmpty(IsEmptyUtils.get_st_id())) {
                RxToast.success("请先到设置中进行学生身份验证！");
                return;
            }
            getVerifyCode();

        }
    }

    private class SelectOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            //手动隐藏软键盘
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            ShowDialogUtil.showProgressDialog(context, "正在查询...");
            login();
        }
    }

    private void getVerifyCode() {
        ShowDialogUtil.showProgressDialog(context, "正在获取验证码");
        HttpUtils.sendGetRequest(UstsValue.kwxfcx_code, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                byte[] bytes = response.body().bytes();
                final Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                String cookie0 = response.header("Set-Cookie");
                cookie = cookie0.substring(0, cookie0.length() - 8);
                getParent().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        ShowDialogUtil.closeProgressDialog();
                        iv_kwxf_code.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void login() {
        String st_id = (String) BmobUser.getObjectByKey("st_id");
        String st_pd = (String) BmobUser.getObjectByKey("st_pd");
        String st_code = et_kwxf_code.getText().toString().trim();
        if (TextUtils.isEmpty(st_code)) {
            ShowDialogUtil.closeProgressDialog();
            RxToast.error("请先输入验证码！");
            return;
        }
        final RequestBody requestBody = new FormBody.Builder()
                .add("__VIEWSTATE", "dDw1NzgzNTg2OTc7Oz6jcqWq650/zED0qcB+UtqbyQB/Cg==")
                .add("__VIEWSTATEGENERATOR", "FCD7F2DE")
                .add("code_id", st_code)
                .add("Password", st_pd)
                .add("Submit1", "%E7%99%BB++%E5%BD%95")
                .add("UserName", st_id)
                .build();
        final Request request = new Request.Builder()
                .url(UstsValue.kwxfcx)
                .post(requestBody)
                .addHeader("Cookie", cookie)
                .build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ShowDialogUtil.closeProgressDialog();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String res = response.request().url().toString();
                getParent().runOnUiThread(new Runnable() {
                    public void run() {
                        if (res.equals(UstsValue.kwxfcx_main)) {
                            Toast.show(context, "登陆成功！", 0);
                            get_detail_info_html();
                        } else {
                            Toast.show(context, "登陆失败！", 0);
                        }
                    }
                });


            }
        });
    }

    private void get_detail_info_html() {
        final Request request = new Request.Builder()
                .url(UstsValue.kwxfcx_detail)
                .addHeader("Cookie", cookie)
                .build();
        new OkHttpClient().newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String res = response.body().string();
                final Document document = Jsoup.parse(res);
                getParent().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        get_detail_info(document);
                        ly_kwxf_select.setVisibility(View.GONE);
                        show_info();

                    }
                });

            }
        });

    }

    private void get_detail_info(Document document) {
        Elements element = document.getElementsByAttributeValue("class", "datelist").select("td");
        kwxf_list = new ArrayList<>();
        int i = 0;
        Kwxf k = null;
        StringBuilder s = null;
        for (Element e : element) {
            if (i > 7) {
                Log.e("tag", e.toString());
                if (i % 8 == 0) {
                    k = new Kwxf();
                    s = new StringBuilder();
                    s.append(e.text());
                }
                if (i % 8 == 1) {
                    s.append("-" + e.text());
                    k.setTime(s.toString());
                }
                if (i % 8 == 4) {
                    s = new StringBuilder();
                    s.append(e.text());
                }
                if (i % 8 == 5) {
                    s.append("-" + e.text());
                    k.setName(s.toString());
                }
                if (i % 8 == 6) {
                    s = new StringBuilder();
                    if (e.text().startsWith(".")) {
                        s.append("0" + e.text());
                    } else {
                        s.append(e.text());
                    }
                    k.setGrade(Double.parseDouble(s.toString()));
                }
                if (i % 8 == 7) {
                    s = new StringBuilder();
                    s.append(e.text());
                    k.setPass(e.text());
                    kwxf_list.add(k);
                }

            }

            i = i + 1;
        }


    }

    private void show_info() {
        ShowDialogUtil.closeProgressDialog();
        lv_kwxf_list.setAdapter(new KwxfAdapter(context, R.layout.item_kwxf, kwxf_list));
    }

}
