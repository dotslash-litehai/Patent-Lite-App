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
    private int role;

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        role = getArguments().getInt("role");
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

        Request request = request();

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
                    timestamp = timestamp.substring(0,10);
                    status = obj.getString("provisioningStatus");
                    Log.d("My App", obj.toString());
                    Log.d("phonetype value ", obj.getString("phonetype"));

                } catch (Throwable tx) {
                    Log.e("My App", "Could not parse malformed JSON: \"" + myResponse + "\"");
                }

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        rootview.findViewById(R.id.constraint).setVisibility(View.VISIBLE);
                        rootview.findViewById(R.id.infoLayout).setVisibility(View.VISIBLE);
                        rootview.findViewById(R.id.progressBar).setVisibility(View.GONE);
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

    public Request request(){
        Request request;
        if(role==1){
            //Applicant
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/8")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTYwMTgxLCJuYmYiOjE1NDkxNjAxODEsImV4cCI6MTU0OTE2NDA4MSwiYWNyIjoiMSIsImFpbyI6IjQySmdZSmhuNmxKNnZvYzUyZnY2dEpWbmhXNUt0c3J6Wnk5K2Y5MzE4clBJN2EwMTUzSUIiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6IkFwcGxpY2FudCIsIm9pZCI6IjgxNjE2YzE4LTdlMGEtNDgxMC05NjEzLTdmNjQ0YjgwYmQzZSIsInNjcCI6IlVzZXIuUmVhZCBVc2VyLlJlYWRCYXNpYy5BbGwiLCJzdWIiOiJYRHNfaGJIZHJxR1RYaDJxWUxUYWxocjdxTFlJSXF0M0dTSXRiZ2d5dnY0IiwidGlkIjoiNDlkYjAxODgtNWY4YS00M2U0LTk3MDgtOGY4MjQwOGRkMDk4IiwidW5pcXVlX25hbWUiOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJ1MDZqaEFtNEJrU1Fhb2JEd2RrbEFBIiwidmVyIjoiMS4wIn0.nHf7bB_geMM1GbGewHaT6oK9mQolueozmPvV6jFghp3FbvVG6lT6fwVEqzTW3ZUmHDHkHJmDdwWLMlFapQZfL93y9b3obQrOhqEG1_bn_aJIhQe-lDrSR_FmhvcIuVzj4NR_kQziP0YAcrM4d_KFWpUvLSkmlJGvem8a8uqpKxeornarORdXTHqZr-RoOZYD-BXtgzA5r3a4ud6i3SJYE_eist0dNsdXxjmuknmNw63j9-oaV1Xm3Fd2i-aRWMDmyitz2IolThWMFuZvdWJX00cHe6xLdFLjqYV42Db0VllfIx0HT0vDskSG3JuQe136U_VadpIk_6k1yeFwjkwBng")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "2931bd32-018f-45de-b553-3eee06aa0b3f")
                    .build();

        } else if (role==2){
            //Appraiser
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/users")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTEzMjM3LCJuYmYiOjE1NDkxMTMyMzcsImV4cCI6MTU0OTExNzEzNywiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhLQUFBQXpBWC9ZMlBOVkVMQWhBTE9KYkVpejFYQjVqSDMwRWVMdnNXSDROZ3llNm05U0FXeW9LbFdSUGpnVzVqTjRoN3hxTTJBQzVxS1plRk1zOWVBK2orMDlBPT0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiZW1haWwiOiJpbnNpeWFoaGFqb29yaUBnbWFpbC5jb20iLCJmYW1pbHlfbmFtZSI6Ikhham9vcmkiLCJnaXZlbl9uYW1lIjoiSW5zaXlhaCIsImlkcCI6ImxpdmUuY29tIiwiaXBhZGRyIjoiNDkuMzQuMTM5LjE5IiwibmFtZSI6Ikluc2l5YWggSGFqb29yaSIsIm9pZCI6Ijg2ZTJkMGQwLWVkMjEtNGFkYS1hOTM0LTBmMGRlMTk0NTVhMCIsInJvbGVzIjpbIkFkbWluaXN0cmF0b3IiXSwic2NwIjoiVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCIsInN1YiI6InRUY24yRFExb25TYjhhYlNTMjZPQ1g0UVRDZERaNDRsZzBaVEJhbFcwdHciLCJ0aWQiOiI0OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgiLCJ1bmlxdWVfbmFtZSI6ImxpdmUuY29tI2luc2l5YWhoYWpvb3JpQGdtYWlsLmNvbSIsInV0aSI6ImxLa01PMmNOVlVpQnV1Qmdrd1lVQUEiLCJ2ZXIiOiIxLjAifQ.bK3B4QEpeyTOxoCW__x2jNETa9cFeZrEJ3e10bpGPLuk6ydhIGIebkX7i8mj2nUjHPosd64uvVS5Vpn35zEz-PQanoj1E71kHQ0UM0_M0l9cIYWR9XkyjxKAD29phMmL9e0sbhjYSlHW6ybtHyPgHLiqkw7Ne5rMsd-wakEje9O1qNG_nYWQm1ZNvB2F6IK4Q7TGsssHg_1Mhq5Wf_1hUrMmstK6ZpdT-YhrcAT3Kqm18C9RJIgRFEeHYkXGJNn8FzKBbwkO6o2rbbmwqvzI5gx4oOJHlmubeWfdBdttlPr9fECqV9olCY4nQSJNVitfKnPS5YJDl8fii9dicytgKA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "a16994ee-4887-46d8-999e-ab074bff395b")
                    .build();

        } else if(role==3){
            //Inspector
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/users")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTEzMjM3LCJuYmYiOjE1NDkxMTMyMzcsImV4cCI6MTU0OTExNzEzNywiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhLQUFBQXpBWC9ZMlBOVkVMQWhBTE9KYkVpejFYQjVqSDMwRWVMdnNXSDROZ3llNm05U0FXeW9LbFdSUGpnVzVqTjRoN3hxTTJBQzVxS1plRk1zOWVBK2orMDlBPT0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiZW1haWwiOiJpbnNpeWFoaGFqb29yaUBnbWFpbC5jb20iLCJmYW1pbHlfbmFtZSI6Ikhham9vcmkiLCJnaXZlbl9uYW1lIjoiSW5zaXlhaCIsImlkcCI6ImxpdmUuY29tIiwiaXBhZGRyIjoiNDkuMzQuMTM5LjE5IiwibmFtZSI6Ikluc2l5YWggSGFqb29yaSIsIm9pZCI6Ijg2ZTJkMGQwLWVkMjEtNGFkYS1hOTM0LTBmMGRlMTk0NTVhMCIsInJvbGVzIjpbIkFkbWluaXN0cmF0b3IiXSwic2NwIjoiVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCIsInN1YiI6InRUY24yRFExb25TYjhhYlNTMjZPQ1g0UVRDZERaNDRsZzBaVEJhbFcwdHciLCJ0aWQiOiI0OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgiLCJ1bmlxdWVfbmFtZSI6ImxpdmUuY29tI2luc2l5YWhoYWpvb3JpQGdtYWlsLmNvbSIsInV0aSI6ImxLa01PMmNOVlVpQnV1Qmdrd1lVQUEiLCJ2ZXIiOiIxLjAifQ.bK3B4QEpeyTOxoCW__x2jNETa9cFeZrEJ3e10bpGPLuk6ydhIGIebkX7i8mj2nUjHPosd64uvVS5Vpn35zEz-PQanoj1E71kHQ0UM0_M0l9cIYWR9XkyjxKAD29phMmL9e0sbhjYSlHW6ybtHyPgHLiqkw7Ne5rMsd-wakEje9O1qNG_nYWQm1ZNvB2F6IK4Q7TGsssHg_1Mhq5Wf_1hUrMmstK6ZpdT-YhrcAT3Kqm18C9RJIgRFEeHYkXGJNn8FzKBbwkO6o2rbbmwqvzI5gx4oOJHlmubeWfdBdttlPr9fECqV9olCY4nQSJNVitfKnPS5YJDl8fii9dicytgKA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "a16994ee-4887-46d8-999e-ab074bff395b")
                    .build();

        } else {
            //Admin
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/users")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTEzMjM3LCJuYmYiOjE1NDkxMTMyMzcsImV4cCI6MTU0OTExNzEzNywiYWNyIjoiMSIsImFpbyI6IkFVUUF1LzhLQUFBQXpBWC9ZMlBOVkVMQWhBTE9KYkVpejFYQjVqSDMwRWVMdnNXSDROZ3llNm05U0FXeW9LbFdSUGpnVzVqTjRoN3hxTTJBQzVxS1plRk1zOWVBK2orMDlBPT0iLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiZW1haWwiOiJpbnNpeWFoaGFqb29yaUBnbWFpbC5jb20iLCJmYW1pbHlfbmFtZSI6Ikhham9vcmkiLCJnaXZlbl9uYW1lIjoiSW5zaXlhaCIsImlkcCI6ImxpdmUuY29tIiwiaXBhZGRyIjoiNDkuMzQuMTM5LjE5IiwibmFtZSI6Ikluc2l5YWggSGFqb29yaSIsIm9pZCI6Ijg2ZTJkMGQwLWVkMjEtNGFkYS1hOTM0LTBmMGRlMTk0NTVhMCIsInJvbGVzIjpbIkFkbWluaXN0cmF0b3IiXSwic2NwIjoiVXNlci5SZWFkIFVzZXIuUmVhZEJhc2ljLkFsbCIsInN1YiI6InRUY24yRFExb25TYjhhYlNTMjZPQ1g0UVRDZERaNDRsZzBaVEJhbFcwdHciLCJ0aWQiOiI0OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgiLCJ1bmlxdWVfbmFtZSI6ImxpdmUuY29tI2luc2l5YWhoYWpvb3JpQGdtYWlsLmNvbSIsInV0aSI6ImxLa01PMmNOVlVpQnV1Qmdrd1lVQUEiLCJ2ZXIiOiIxLjAifQ.bK3B4QEpeyTOxoCW__x2jNETa9cFeZrEJ3e10bpGPLuk6ydhIGIebkX7i8mj2nUjHPosd64uvVS5Vpn35zEz-PQanoj1E71kHQ0UM0_M0l9cIYWR9XkyjxKAD29phMmL9e0sbhjYSlHW6ybtHyPgHLiqkw7Ne5rMsd-wakEje9O1qNG_nYWQm1ZNvB2F6IK4Q7TGsssHg_1Mhq5Wf_1hUrMmstK6ZpdT-YhrcAT3Kqm18C9RJIgRFEeHYkXGJNn8FzKBbwkO6o2rbbmwqvzI5gx4oOJHlmubeWfdBdttlPr9fECqV9olCY4nQSJNVitfKnPS5YJDl8fii9dicytgKA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "a16994ee-4887-46d8-999e-ab074bff395b")
                    .build();

        }
        return request;
    }

}
