package com.example.kshitij.patentlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    TextView txtString;
    public String url= "https://reqres.in/api/users/2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtString= findViewById(R.id.applications);

        try {
            run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void run() throws IOException {

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/users")
                .get()
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTA0NzEzLCJuYmYiOjE1NDkxMDQ3MTMsImV4cCI6MTU0OTEwODYxMywiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhLQUFBQUVpcUp0SEQ2THRrWWFDdWpsMDIvdWx4amUxT1pmS1hYeXJ5TURjaW96d2d1MVMrWnZZZ0ZtM2hMNHBqNzVUTmNmSDJKRHp4VmkvTXZlZklSNEc3SURBPT0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiZW1haWwiOiJpbnNpeWFoaGFqb29yaUBnbWFpbC5jb20iLCJmYW1pbHlfbmFtZSI6Ikhham9vcmkiLCJnaXZlbl9uYW1lIjoiSW5zaXlhaCIsImlkcCI6ImxpdmUuY29tIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6Ikluc2l5YWggSGFqb29yaSIsIm9pZCI6Ijg2ZTJkMGQwLWVkMjEtNGFkYS1hOTM0LTBmMGRlMTk0NTVhMCIsInJvbGVzIjpbIkFkbWluaXN0cmF0b3IiXSwic2NwIjoiVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCIsInN1YiI6InRUY24yRFExb25TYjhhYlNTMjZPQ1g0UVRDZERaNDRsZzBaVEJhbFcwdHciLCJ0aWQiOiI0OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgiLCJ1bmlxdWVfbmFtZSI6ImxpdmUuY29tI2luc2l5YWhoYWpvb3JpQGdtYWlsLmNvbSIsInV0aSI6Il9RQnBLMkZ3TmtPWXVqaWVXWEV6QUEiLCJ2ZXIiOiIxLjAifQ.n6JIp_JAAOrkcsBMV-tlfGlRUhk29XjE54lEVTIS9tnCeIiSG4zLdpJtdxn8h9WrC-wUsjnbkXQC77lTQ_eBTZ2rwSKBQiXcfD0YKAh1RDHM5iXbRElHEcv9cemb9tO8UG0B0eX4ERzCMIX3riwlo59aVPcmcEcnMtHAttgjlegQ3LTHbqX2Dg2uUNmRPOtmO0jT9v-v-ip-reZt_f3fI7tQZOH0OMvhxrOIJHm70TXLseh49XQ9jGrSucjsqRR8pwuMSbvy3b-6Dc89shXRPSP7zMS_HSqGkOqBKZMe5vWYgknz06Min7QgK3qDe7iCvETz8MaK1zrdehnHLhgz_A")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "7d7b163d-a816-481c-a1f5-3eac2cd565e9")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();

                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        txtString.setText(myResponse);
                    }
                });

            }
        });
    }

}