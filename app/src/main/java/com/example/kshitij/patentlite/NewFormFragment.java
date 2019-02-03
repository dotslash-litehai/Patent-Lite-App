package com.example.kshitij.patentlite;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewFormFragment extends Fragment {

    private View rootview;
    private String response;
    private ProgressBar progressBar;
    private LinearLayout linearLayout;
    private TextView fail_text;
    private Button submit;
    private int role = 1;

    public NewFormFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        role = getArguments().getInt("role");
        rootview = inflater.inflate(R.layout.fragment_new_form, container, false);
        progressBar = rootview.findViewById(R.id.progressBar);
        fail_text = rootview.findViewById(R.id.failed);
        linearLayout = rootview.findViewById(R.id.newContract);
        submit = rootview.findViewById(R.id.submitContract);
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
                .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/capabilities/canCreateContract/1")
                .get()
                .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTc0MTk4LCJuYmYiOjE1NDkxNzQxOTgsImV4cCI6MTU0OTE3ODA5OCwiYWNyIjoiMSIsImFpbyI6IkFTUUEyLzhLQUFBQVVEcXMxUWlkeG9zQVdYOTF2WktDVUw1VC9VTzlXbnpoM3FnSXVoU3AzVTQ9IiwiYW1yIjpbInB3ZCJdLCJhcHBpZCI6IjYzNGM4YWNhLWZmYTUtNDIyYy04MzkzLTNmNDNhMDhkNjdkOSIsImFwcGlkYWNyIjoiMCIsImlwYWRkciI6IjQ5LjM0LjcwLjE5NSIsIm5hbWUiOiJBcHBsaWNhbnQiLCJvaWQiOiI4MTYxNmMxOC03ZTBhLTQ4MTAtOTYxMy03ZjY0NGI4MGJkM2UiLCJzY3AiOiJVc2VyLlJlYWQgVXNlci5SZWFkQmFzaWMuQWxsIiwic3ViIjoiWERzX2hiSGRycUdUWGgycVlMVGFsaHI3cUxZSUlxdDNHU0l0YmdneXZ2NCIsInRpZCI6IjQ5ZGIwMTg4LTVmOGEtNDNlNC05NzA4LThmODI0MDhkZDA5OCIsInVuaXF1ZV9uYW1lIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXBuIjoiYXBwbGljYW50QGluc2l5YWhoYWpvb3JpZ21haWwub25taWNyb3NvZnQuY29tIiwidXRpIjoiVExkcnF6ZjdEVUdJbnRETDhkZ3FBQSIsInZlciI6IjEuMCJ9.Aa7Qn7z84kDpG8hWe4k1cZhGZZLUI-1Rt6nkMwUUeaue_otFPvQfMfWVzoLl7b9h8gnVxh15NSXQoddfXbx-jJkJHFJjMot9gydOrwy5fLtt_rkrXQd6t37y1MpOyiRPMLUhhveQVY6mBF00CCcshbtI7rmvU8Zn9HpqD6NALcwMCQJsHOhbTltgqWzDmKKOHnQ5miAZ3gr2VSIfN-r2P35zp4vSm9q_zN86HtbK-ZjuG9HvFP7SxcY2p3jDonTqpvZJXaPf_s3ZOhsNlDqqfaqFFc2vXLJQrxdRpu9mK_QeGvVcJvOhWrf-A0h6hkSv-bTULFKGCXHajzw5w7B22A")
                .addHeader("cache-control", "no-cache")
                .addHeader("Postman-Token", "9f0ca2ba-f802-48f9-9560-6c9de929763d")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {

                final String myResponse = response.body().string();

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if(myResponse.equals("false")){
                            linearLayout.setVisibility(View.GONE);
                            fail_text.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.GONE);
                        } else {
                            fail_text.setVisibility(View.GONE);
                            linearLayout.setVisibility(View.VISIBLE);
                            submit.setVisibility(View.VISIBLE);
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                });

            }
        });
    }

}
