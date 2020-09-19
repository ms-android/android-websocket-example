package com.ms.app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private TextView textView;

    private Handler handler = new Handler(Looper.getMainLooper());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);


        // 创建OKHTTP客户端
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        // 请求
        Request request = new Request.Builder().url("wss://mhw828.com/ws/").build();
        // 连接
        WebSocket webSocket = okHttpClient.newWebSocket(request, new WebSocketListener() {
            @Override
            public void onClosed(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosed(webSocket, code, reason);
            }

            @Override
            public void onClosing(@NotNull WebSocket webSocket, int code, @NotNull String reason) {
                super.onClosing(webSocket, code, reason);
            }

            @Override
            public void onFailure(@NotNull WebSocket webSocket, @NotNull final Throwable t, @Nullable Response response) {
                super.onFailure(webSocket, t, response);

                t.printStackTrace();
                Log.e(TAG, "onOpen: 连接失败");
                Log.e(TAG, "onFailure: ");
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        textView.setText("连接失败:" + t.getMessage());
                    }
                });
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull String text) {
                super.onMessage(webSocket, text);
            }

            @Override
            public void onMessage(@NotNull WebSocket webSocket, @NotNull ByteString bytes) {
                super.onMessage(webSocket, bytes);
            }

            @Override
            public void onOpen(@NotNull final WebSocket webSocket, @NotNull final Response response) {
                super.onOpen(webSocket, response);
                Log.e(TAG, "onOpen: 连接成功");
                handler.post(new Runnable() {
                    @Override
                    public void run() {

                        textView.setText("连接成功 :" + response.code());
                    }
                });
            }
        });


    }
}