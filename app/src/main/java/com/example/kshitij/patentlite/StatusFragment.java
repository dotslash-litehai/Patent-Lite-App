package com.example.kshitij.patentlite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class StatusFragment extends Fragment { ;

    private View rootview;
    private TextView text;
    private String id;
    private String timestamp;
    private String status;

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
                .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/9")
                .get()
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTQ1NDExLCJuYmYiOjE1NDkxNDU0MTEsImV4cCI6MTU0OTE0OTMxMSwiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhLQUFBQW1QYXBYdmxwcDdHTVk2TlBETXYyQVhYMFMzdS92LythYzFmeDB0c2F6cVpEVk94SWxtZlkwZkowandVNHFoRXdsTFlOa3h2Y1pkRWFMVWRuSlI0ZWVBPT0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiZW1haWwiOiJpbnNpeWFoaGFqb29yaUBnbWFpbC5jb20iLCJmYW1pbHlfbmFtZSI6Ikhham9vcmkiLCJnaXZlbl9uYW1lIjoiSW5zaXlhaCIsImlkcCI6ImxpdmUuY29tIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6Ikluc2l5YWggSGFqb29yaSIsIm9pZCI6Ijg2ZTJkMGQwLWVkMjEtNGFkYS1hOTM0LTBmMGRlMTk0NTVhMCIsInJvbGVzIjpbIkFkbWluaXN0cmF0b3IiXSwic2NwIjoiVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCIsInN1YiI6InRUY24yRFExb25TYjhhYlNTMjZPQ1g0UVRDZERaNDRsZzBaVEJhbFcwdHciLCJ0aWQiOiI0OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgiLCJ1bmlxdWVfbmFtZSI6ImxpdmUuY29tI2luc2l5YWhoYWpvb3JpQGdtYWlsLmNvbSIsInV0aSI6InJObFdsZDM0Z0V5RFFZbHF6YllhQUEiLCJ2ZXIiOiIxLjAifQ.lu25uj_djlB38sE78Z4L7-9V_jCbNXoVw4GRroygq2zAVPt7DVng4VB4K71JYIdBQjTraL2uW9wi8iHI8jDDM0-GwY6AgPPvFvYUa50HpOtorNtPwFfZehteH3MKMawuF_sQ6NOaUanLKJFQnVT27es-B0IADw680msWM2mP4ADXMrWzI5cbw8PsfdRZlLsRmWTIKe8EcTfTPpH6yFDZWdWyU49k6YWfaTsIJQg5qFCPZvhl8dPeMzqhVhx0aLNqt5mAVKsZVcCjXCTLef_iQozB10hSRKrGsRvFlqHkVU6VBx03QTcTBNsDYZF0v3R1nKPyK6kzpuw7HW4ciaKYcA")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "a7b3fabe-bb03-4a07-b777-efeafd878029")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                final String myResponse = response.body().string();
                try {

                    JSONObject obj = new JSONObject(myResponse);
                    id = obj.getString("id");
                    timestamp = obj.getString("timestamp");
                    status = obj.getString("provisioningStatus");
                    Log.d("My App", obj.toString());
                    Log.d("phonetype value ", obj.getString("phonetype"));

                } catch (Throwable tx) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + myResponse + "\"");
                }

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView id_view = rootview.findViewById(R.id.patentID);
                        id_view.setText(id);
                        TextView status_view = rootview.findViewById(R.id.status);
                        status_view.setText(status);
                        TextView timestamp_view = rootview.findViewById(R.id.timestamp);
                        timestamp_view.setText(timestamp);
                    }
                });

            }
        });
    }

}
