package com.example.kshitij.patentlite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StatusFragment extends Fragment { ;

    private View rootview;
    private TextView text;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_status, container, false);
        text = rootview.findViewById(R.id.status);
        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rootview;
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/1")
                .get()
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTEzMjM3LCJuYmYiOjE1NDkxMTMyMzcsImV4cCI6MTU0OTExNzEzNywiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhLQUFBQXpBWC9ZMlBOVkVMQWhBTE9KYkVpejFYQjVqSDMwRWVMdnNXSDROZ3llNm05U0FXeW9LbFdSUGpnVzVqTjRoN3hxTTJBQzVxS1plRk1zOWVBK2orMDlBPT0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiZW1haWwiOiJpbnNpeWFoaGFqb29yaUBnbWFpbC5jb20iLCJmYW1pbHlfbmFtZSI6Ikhham9vcmkiLCJnaXZlbl9uYW1lIjoiSW5zaXlhaCIsImlkcCI6ImxpdmUuY29tIiwiaXBhZGRyIjoiNDkuMzQuMTM5LjE5IiwibmFtZSI6Ikluc2l5YWggSGFqb29yaSIsIm9pZCI6Ijg2ZTJkMGQwLWVkMjEtNGFkYS1hOTM0LTBmMGRlMTk0NTVhMCIsInJvbGVzIjpbIkFkbWluaXN0cmF0b3IiXSwic2NwIjoiVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCIsInN1YiI6InRUY24yRFExb25TYjhhYlNTMjZPQ1g0UVRDZERaNDRsZzBaVEJhbFcwdHciLCJ0aWQiOiI0OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgiLCJ1bmlxdWVfbmFtZSI6ImxpdmUuY29tI2luc2l5YWhoYWpvb3JpQGdtYWlsLmNvbSIsInV0aSI6ImxLa01PMmNOVlVpQnV1Qmdrd1lVQUEiLCJ2ZXIiOiIxLjAifQ.bK3B4QEpeyTOxoCW__x2jNETa9cFeZrEJ3e10bpGPLuk6ydhIGIebkX7i8mj2nUjHPosd64uvVS5Vpn35zEz-PQanoj1E71kHQ0UM0_M0l9cIYWR9XkyjxKAD29phMmL9e0sbhjYSlHW6ybtHyPgHLiqkw7Ne5rMsd-wakEje9O1qNG_nYWQm1ZNvB2F6IK4Q7TGsssHg_1Mhq5Wf_1hUrMmstK6ZpdT-YhrcAT3Kqm18C9RJIgRFEeHYkXGJNn8FzKBbwkO6o2rbbmwqvzI5gx4oOJHlmubeWfdBdttlPr9fECqV9olCY4nQSJNVitfKnPS5YJDl8fii9dicytgKA")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "070895f8-6d6a-4881-b59a-5a25d9d1d3b2")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        text.setText(myResponse);
                    }
                });

            }
        });
    }

}