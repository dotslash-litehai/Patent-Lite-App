package com.example.kshitij.patentlite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class ApplicationFragment extends Fragment {

    private View rootview;
    private int role;
    private RelativeLayout layout;
    private ProgressBar progressBar;

    public ApplicationFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        role = getArguments().getInt("role");
        rootview = inflater.inflate(R.layout.fragment_application, container, false);
        layout = rootview.findViewById(R.id.patentDetails);
        progressBar = rootview.findViewById(R.id.progressBar);
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

                Objects.requireNonNull(getActivity()).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            progressBar.setVisibility(View.GONE);
                            layout.setVisibility(View.VISIBLE);
                            JSONObject object = new JSONObject(myResponse);
                            JSONArray contarctProperties = object.getJSONArray("contractProperties");
                            for(int i =0;i<2;i++){
                                JSONObject temp = contarctProperties.getJSONObject(i);
                                String value = temp.getString("value");
                                if(i==0){
                                    TextView status = rootview.findViewById(R.id.status);
                                    status.setText(value);
                                    if(Integer.parseInt(value)<2){
                                        ImageView image = rootview.findViewById(R.id.imageView);
                                        image.setImageDrawable(getResources().getDrawable(R.drawable.circle_red));
                                    } else if(Integer.parseInt(value)==5){
                                        ImageView image = rootview.findViewById(R.id.imageView);
                                        image.setImageDrawable(getResources().getDrawable(R.drawable.circle_first));
                                    } else {
                                        ImageView image = rootview.findViewById(R.id.imageView);
                                        image.setImageDrawable(getResources().getDrawable(R.drawable.circle_yellow));
                                    }
                                } else {
                                    TextView name = rootview.findViewById(R.id.abstractDetails);
                                    String abstarct = value.substring(0,8);
                                    abstarct = abstarct + '\n' + value.substring(9) + '\n';
                                    name.setText(abstarct);
                                }
                            }

                        } catch (JSONException e) {
                            Toast.makeText(getContext(),"Error fetching Contracts",Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

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
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/12")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTc0MTk4LCJuYmYiOjE1NDkxNzQxOTgsImV4cCI6MTU0OTE3ODA5OCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhLQUFBQVVEcXMxUWlkeG9zQVdYOTF2WktDVUw1VC9VTzlXbnpoM3FnSXVoU3AzVTQ9IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6IjYzNGM4YWNhLWZmYTUtNDIyYy04MzkzLTNmNDNhMDhkNjdkOSIsImFwcGlkYWNyIjoiMCIsImlwYWRkciI6IjQ5LjM0LjcwLjE5NSIsIm5hbWUiOiJBcHBsaWNhbnQiLCJvaWQiOiI4MTYxNmMxOC03ZTBhLTQ4MTAtOTYxMy03ZjY0NGI4MGJkM2UiLCJzY3AiOiJVc2VyLlJlYWQgVXNlci5SZWFkQmFzaWMuQWxsIiwic3ViIjoiWERzX2hiSGRycUdUWGgycVlMVGFsaHI3cUxZSUlxdDNHU0l0YmdneXZ2NCIsInRpZCI6IjQ5ZGIwMTg4LTVmOGEtNDNlNC05NzA4LThmODI0MDhkZDA5OCIsInVuaXF1ZV9uYW1lIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXBuIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXRpIjoiVExkcnF6ZjdEVUdJbnRETDhkZ3FBQSIsInZlciI6IjEuMCJ9.Aa7Qn7z84kDpG8hWe4k1cZhGZZLUI-1Rt6nkMwUUeaue_otFPvQfMfWVzoLl7b9h8gnVxh15NSXQoddfXbx-jJkJHFJjMot9gydOrwy5fLtt_rkrXQd6t37y1MpOyiRPMLUhhveQVY6mBF00CCcshbtI7rmvU8Zn9HpqD6NALcwMCQJsHOhbTltgqWzDmKKOHnQ5miAZ3gr2VSIfN-r2P35zp4vSm9q_zN86HtbK-ZjuG9HvFP7SxcY2p3jDonTqpvZJXaPf_s3ZOhsNlDqqfaqFFc2vXLJQrxdRpu9mK_QeGvVcJvOhWrf-A0h6hkSv-bTULFKGCXHajzw5w7B22A")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "6a83f16d-f102-46d6-8a94-8adf3ecc370f")
                    .build();


        } else if (role==2){
            //Appraiser
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/12")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTc0MTk4LCJuYmYiOjE1NDkxNzQxOTgsImV4cCI6MTU0OTE3ODA5OCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhLQUFBQVVEcXMxUWlkeG9zQVdYOTF2WktDVUw1VC9VTzlXbnpoM3FnSXVoU3AzVTQ9IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6IjYzNGM4YWNhLWZmYTUtNDIyYy04MzkzLTNmNDNhMDhkNjdkOSIsImFwcGlkYWNyIjoiMCIsImlwYWRkciI6IjQ5LjM0LjcwLjE5NSIsIm5hbWUiOiJBcHBsaWNhbnQiLCJvaWQiOiI4MTYxNmMxOC03ZTBhLTQ4MTAtOTYxMy03ZjY0NGI4MGJkM2UiLCJzY3AiOiJVc2VyLlJlYWQgVXNlci5SZWFkQmFzaWMuQWxsIiwic3ViIjoiWERzX2hiSGRycUdUWGgycVlMVGFsaHI3cUxZSUlxdDNHU0l0YmdneXZ2NCIsInRpZCI6IjQ5ZGIwMTg4LTVmOGEtNDNlNC05NzA4LThmODI0MDhkZDA5OCIsInVuaXF1ZV9uYW1lIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXBuIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXRpIjoiVExkcnF6ZjdEVUdJbnRETDhkZ3FBQSIsInZlciI6IjEuMCJ9.Aa7Qn7z84kDpG8hWe4k1cZhGZZLUI-1Rt6nkMwUUeaue_otFPvQfMfWVzoLl7b9h8gnVxh15NSXQoddfXbx-jJkJHFJjMot9gydOrwy5fLtt_rkrXQd6t37y1MpOyiRPMLUhhveQVY6mBF00CCcshbtI7rmvU8Zn9HpqD6NALcwMCQJsHOhbTltgqWzDmKKOHnQ5miAZ3gr2VSIfN-r2P35zp4vSm9q_zN86HtbK-ZjuG9HvFP7SxcY2p3jDonTqpvZJXaPf_s3ZOhsNlDqqfaqFFc2vXLJQrxdRpu9mK_QeGvVcJvOhWrf-A0h6hkSv-bTULFKGCXHajzw5w7B22A")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "6a83f16d-f102-46d6-8a94-8adf3ecc370f")
                    .build();


        } else if(role==3){
            //Inspector
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/12")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTc0MTk4LCJuYmYiOjE1NDkxNzQxOTgsImV4cCI6MTU0OTE3ODA5OCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhLQUFBQVVEcXMxUWlkeG9zQVdYOTF2WktDVUw1VC9VTzlXbnpoM3FnSXVoU3AzVTQ9IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6IjYzNGM4YWNhLWZmYTUtNDIyYy04MzkzLTNmNDNhMDhkNjdkOSIsImFwcGlkYWNyIjoiMCIsImlwYWRkciI6IjQ5LjM0LjcwLjE5NSIsIm5hbWUiOiJBcHBsaWNhbnQiLCJvaWQiOiI4MTYxNmMxOC03ZTBhLTQ4MTAtOTYxMy03ZjY0NGI4MGJkM2UiLCJzY3AiOiJVc2VyLlJlYWQgVXNlci5SZWFkQmFzaWMuQWxsIiwic3ViIjoiWERzX2hiSGRycUdUWGgycVlMVGFsaHI3cUxZSUlxdDNHU0l0YmdneXZ2NCIsInRpZCI6IjQ5ZGIwMTg4LTVmOGEtNDNlNC05NzA4LThmODI0MDhkZDA5OCIsInVuaXF1ZV9uYW1lIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXBuIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXRpIjoiVExkcnF6ZjdEVUdJbnRETDhkZ3FBQSIsInZlciI6IjEuMCJ9.Aa7Qn7z84kDpG8hWe4k1cZhGZZLUI-1Rt6nkMwUUeaue_otFPvQfMfWVzoLl7b9h8gnVxh15NSXQoddfXbx-jJkJHFJjMot9gydOrwy5fLtt_rkrXQd6t37y1MpOyiRPMLUhhveQVY6mBF00CCcshbtI7rmvU8Zn9HpqD6NALcwMCQJsHOhbTltgqWzDmKKOHnQ5miAZ3gr2VSIfN-r2P35zp4vSm9q_zN86HtbK-ZjuG9HvFP7SxcY2p3jDonTqpvZJXaPf_s3ZOhsNlDqqfaqFFc2vXLJQrxdRpu9mK_QeGvVcJvOhWrf-A0h6hkSv-bTULFKGCXHajzw5w7B22A")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "6a83f16d-f102-46d6-8a94-8adf3ecc370f")
                    .build();


        } else {
            //Admin
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/12")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTc0MTk4LCJuYmYiOjE1NDkxNzQxOTgsImV4cCI6MTU0OTE3ODA5OCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhLQUFBQVVEcXMxUWlkeG9zQVdYOTF2WktDVUw1VC9VTzlXbnpoM3FnSXVoU3AzVTQ9IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6IjYzNGM4YWNhLWZmYTUtNDIyYy04MzkzLTNmNDNhMDhkNjdkOSIsImFwcGlkYWNyIjoiMCIsImlwYWRkciI6IjQ5LjM0LjcwLjE5NSIsIm5hbWUiOiJBcHBsaWNhbnQiLCJvaWQiOiI4MTYxNmMxOC03ZTBhLTQ4MTAtOTYxMy03ZjY0NGI4MGJkM2UiLCJzY3AiOiJVc2VyLlJlYWQgVXNlci5SZWFkQmFzaWMuQWxsIiwic3ViIjoiWERzX2hiSGRycUdUWGgycVlMVGFsaHI3cUxZSUlxdDNHU0l0YmdneXZ2NCIsInRpZCI6IjQ5ZGIwMTg4LTVmOGEtNDNlNC05NzA4LThmODI0MDhkZDA5OCIsInVuaXF1ZV9uYW1lIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXBuIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXRpIjoiVExkcnF6ZjdEVUdJbnRETDhkZ3FBQSIsInZlciI6IjEuMCJ9.Aa7Qn7z84kDpG8hWe4k1cZhGZZLUI-1Rt6nkMwUUeaue_otFPvQfMfWVzoLl7b9h8gnVxh15NSXQoddfXbx-jJkJHFJjMot9gydOrwy5fLtt_rkrXQd6t37y1MpOyiRPMLUhhveQVY6mBF00CCcshbtI7rmvU8Zn9HpqD6NALcwMCQJsHOhbTltgqWzDmKKOHnQ5miAZ3gr2VSIfN-r2P35zp4vSm9q_zN86HtbK-ZjuG9HvFP7SxcY2p3jDonTqpvZJXaPf_s3ZOhsNlDqqfaqFFc2vXLJQrxdRpu9mK_QeGvVcJvOhWrf-A0h6hkSv-bTULFKGCXHajzw5w7B22A")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "6a83f16d-f102-46d6-8a94-8adf3ecc370f")
                    .build();

        }
        return request;
    }

}
