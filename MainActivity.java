package bwei.com.okhttpdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String url = "http://m.yunifang.com/yunifang/mobile/home?random=84831&encode=9dd34239798e8cb22bf99a75d1882447";
    String url2 = "http://admin.wap.china.com/user/NavigateTypeAction.do?processID=getNavigateNews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.button_a).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpGET_a();
            }
        });
        findViewById(R.id.button_b).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpGET_b();
            }
        });
        findViewById(R.id.button_c).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpPost_b();
            }
        });
        findViewById(R.id.button_d).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOkHttpPost_a();
            }
        });


    }

    //get同步请求
    public void getOkHttpGET_a() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)

                        .build();


                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    final String string = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(MainActivity.this, string + "请求成功!", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }

    //get异步请求
    private void getOkHttpGET_b() {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url)

                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "请求成功!", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    //post异步请求
    public void getOkHttpPost_a() {

        RequestBody body = new FormBody
                .Builder()
                .add("page", "1")
                .add("code", "news")
                .add("pageSize", "20")
                .add("parentid", "0")
                .add("type", "1")
                .build();

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url2)
                .post(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, string + "请求成功!", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

    }

    //post同步请求
    public void getOkHttpPost_b() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                RequestBody body = new FormBody
                        .Builder()
                        .add("page", "1")
                        .add("code", "news")
                        .add("pageSize", "20")
                        .add("parentid", "0")
                        .add("type", "1")
                        .build();
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url2)
                        .post(body)
                        .build();


                try {
                    okhttp3.Response response = client.newCall(request).execute();
                    final String string = response.body().string();

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            Log.i("json++++++>", string);
                            Toast.makeText(MainActivity.this, string + "", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


    }
}
