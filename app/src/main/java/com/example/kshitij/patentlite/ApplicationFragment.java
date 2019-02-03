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
                                    TextView name = rootview.findViewById(R.id.nameContract);
                                    name.setText(value);
                                }
                            }
                            JSONArray contractActions = object.getJSONArray("contractActions");
                            JSONObject first = contractActions.getJSONObject(0);
                            JSONArray params = first.getJSONArray("parameters");
                            for(int i =0;i<2;i++){
                                JSONObject temp = params.getJSONObject(i);
                                String value = temp.getString("value");
                                if(i==0){
                                    TextView description = rootview.findViewById(R.id.description);
                                    description.setText(value);
                                } else {
                                    TextView claims = rootview.findViewById(R.id.numberofClaims);
                                    claims.setText("Number of Claims :- " + value);
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
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/10")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTYyOTQyLCJuYmYiOjE1NDkxNjI5NDIsImV4cCI6MTU0OTE2Njg0MiwiYWNyIjoiMSIsImFpbyI6IjQySmdZR0Q4ZmZPdmFzNGVOcjRETC9QZFhDTFlQbjFXTlpUb2VyNWpwWGs0bThXNjZ5VUEiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6IkFwcGxpY2FudCIsIm9pZCI6IjgxNjE2YzE4LTdlMGEtNDgxMC05NjEzLTdmNjQ0YjgwYmQzZSIsInNjcCI6IlVzZXIuUmVhZCBVc2VyLlJlYWRCYXNpYy5BbGwiLCJzdWIiOiJYRHNfaGJIZHJxR1RYaDJxWUxUYWxocjdxTFlJSXF0M0dTSXRiZ2d5dnY0IiwidGlkIjoiNDlkYjAxODgtNWY4YS00M2U0LTk3MDgtOGY4MjQwOGRkMDk4IiwidW5pcXVlX25hbWUiOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJlTzhieUhEUlJVcVBkSXpITjJ3Z0FBIiwidmVyIjoiMS4wIn0.Q-1Dl983xCTuA2wHrexmE7Wz6XYyJEBR6Sh2Pz10UdxVFnb6oplEyDicPACvO_QH00_Xket77duvABtQw8b82qh1lHomtNM_hJcK7eVjIztEqj7diHIx_gcuMiuKF5W-Ja7mMnrrBal2L6s_LZMjzmv78kqcNiw-iGtmoan6MdWSOC5D7eCeWFhImWKS2LaZlZJMF6JYz3dlkPhJkrKdbm4tZNNNy1siTMvFtUMVUUQLufIF4GI3PhJt70MCXkK8FGRNFEGS0tIn1KcZnmvaEbQ3qPjzGJgzizRBKlRKupAb2lqG6AP8aAxs3K3eaA0kuXtaABRUzxXAiXYEDP0BzA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "025fa8b6-ba0c-4ef3-adcd-ac23585aa410")
                    .build();


        } else if (role==2){
            //Appraiser
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/11")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTYyOTQyLCJuYmYiOjE1NDkxNjI5NDIsImV4cCI6MTU0OTE2Njg0MiwiYWNyIjoiMSIsImFpbyI6IjQySmdZR0Q4ZmZPdmFzNGVOcjRETC9QZFhDTFlQbjFXTlpUb2VyNWpwWGs0bThXNjZ5VUEiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6IkFwcGxpY2FudCIsIm9pZCI6IjgxNjE2YzE4LTdlMGEtNDgxMC05NjEzLTdmNjQ0YjgwYmQzZSIsInNjcCI6IlVzZXIuUmVhZCBVc2VyLlJlYWRCYXNpYy5BbGwiLCJzdWIiOiJYRHNfaGJIZHJxR1RYaDJxWUxUYWxocjdxTFlJSXF0M0dTSXRiZ2d5dnY0IiwidGlkIjoiNDlkYjAxODgtNWY4YS00M2U0LTk3MDgtOGY4MjQwOGRkMDk4IiwidW5pcXVlX25hbWUiOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJlTzhieUhEUlJVcVBkSXpITjJ3Z0FBIiwidmVyIjoiMS4wIn0.Q-1Dl983xCTuA2wHrexmE7Wz6XYyJEBR6Sh2Pz10UdxVFnb6oplEyDicPACvO_QH00_Xket77duvABtQw8b82qh1lHomtNM_hJcK7eVjIztEqj7diHIx_gcuMiuKF5W-Ja7mMnrrBal2L6s_LZMjzmv78kqcNiw-iGtmoan6MdWSOC5D7eCeWFhImWKS2LaZlZJMF6JYz3dlkPhJkrKdbm4tZNNNy1siTMvFtUMVUUQLufIF4GI3PhJt70MCXkK8FGRNFEGS0tIn1KcZnmvaEbQ3qPjzGJgzizRBKlRKupAb2lqG6AP8aAxs3K3eaA0kuXtaABRUzxXAiXYEDP0BzA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "025fa8b6-ba0c-4ef3-adcd-ac23585aa410")
                    .build();


        } else if(role==3){
            //Inspector
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/11")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTYyOTQyLCJuYmYiOjE1NDkxNjI5NDIsImV4cCI6MTU0OTE2Njg0MiwiYWNyIjoiMSIsImFpbyI6IjQySmdZR0Q4ZmZPdmFzNGVOcjRETC9QZFhDTFlQbjFXTlpUb2VyNWpwWGs0bThXNjZ5VUEiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6IkFwcGxpY2FudCIsIm9pZCI6IjgxNjE2YzE4LTdlMGEtNDgxMC05NjEzLTdmNjQ0YjgwYmQzZSIsInNjcCI6IlVzZXIuUmVhZCBVc2VyLlJlYWRCYXNpYy5BbGwiLCJzdWIiOiJYRHNfaGJIZHJxR1RYaDJxWUxUYWxocjdxTFlJSXF0M0dTSXRiZ2d5dnY0IiwidGlkIjoiNDlkYjAxODgtNWY4YS00M2U0LTk3MDgtOGY4MjQwOGRkMDk4IiwidW5pcXVlX25hbWUiOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJlTzhieUhEUlJVcVBkSXpITjJ3Z0FBIiwidmVyIjoiMS4wIn0.Q-1Dl983xCTuA2wHrexmE7Wz6XYyJEBR6Sh2Pz10UdxVFnb6oplEyDicPACvO_QH00_Xket77duvABtQw8b82qh1lHomtNM_hJcK7eVjIztEqj7diHIx_gcuMiuKF5W-Ja7mMnrrBal2L6s_LZMjzmv78kqcNiw-iGtmoan6MdWSOC5D7eCeWFhImWKS2LaZlZJMF6JYz3dlkPhJkrKdbm4tZNNNy1siTMvFtUMVUUQLufIF4GI3PhJt70MCXkK8FGRNFEGS0tIn1KcZnmvaEbQ3qPjzGJgzizRBKlRKupAb2lqG6AP8aAxs3K3eaA0kuXtaABRUzxXAiXYEDP0BzA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "025fa8b6-ba0c-4ef3-adcd-ac23585aa410")
                    .build();


        } else {
            //Admin
            request = new Request.Builder()
                    .url("https://litehai-vtt6wd-api.azurewebsites.net/api/v1/contracts/11")
                    .get()
                    .addHeader("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsIng1dCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCIsImtpZCI6Ii1zeE1KTUxDSURXTVRQdlp5SjZ0eC1DRHh3MCJ9.eyJhdWQiOiI2MzRjOGFjYS1mZmE1LTQyMmMtODM5My0zZjQzYTA4ZDY3ZDkiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC80OWRiMDE4OC01ZjhhLTQzZTQtOTcwOC04ZjgyNDA4ZGQwOTgvIiwiaWF0IjoxNTQ5MTYyOTQyLCJuYmYiOjE1NDkxNjI5NDIsImV4cCI6MTU0OTE2Njg0MiwiYWNyIjoiMSIsImFpbyI6IjQySmdZR0Q4ZmZPdmFzNGVOcjRETC9QZFhDTFlQbjFXTlpUb2VyNWpwWGs0bThXNjZ5VUEiLCJhbXIiOlsicHdkIl0sImFwcGlkIjoiNjM0YzhhY2EtZmZhNS00MjJjLTgzOTMtM2Y0M2EwOGQ2N2Q5IiwiYXBwaWRhY3IiOiIwIiwiaXBhZGRyIjoiNDkuMzQuNzAuMTk1IiwibmFtZSI6IkFwcGxpY2FudCIsIm9pZCI6IjgxNjE2YzE4LTdlMGEtNDgxMC05NjEzLTdmNjQ0YjgwYmQzZSIsInNjcCI6IlVzZXIuUmVhZCBVc2VyLlJlYWRCYXNpYy5BbGwiLCJzdWIiOiJYRHNfaGJIZHJxR1RYaDJxWUxUYWxocjdxTFlJSXF0M0dTSXRiZ2d5dnY0IiwidGlkIjoiNDlkYjAxODgtNWY4YS00M2U0LTk3MDgtOGY4MjQwOGRkMDk4IiwidW5pcXVlX25hbWUiOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1cG4iOiJhcHBsaWNhbnRAaW5zaXlhaGhham9vcmlnbWFpbC5vbm1pY3Jvc29mdC5jb20iLCJ1dGkiOiJlTzhieUhEUlJVcVBkSXpITjJ3Z0FBIiwidmVyIjoiMS4wIn0.Q-1Dl983xCTuA2wHrexmE7Wz6XYyJEBR6Sh2Pz10UdxVFnb6oplEyDicPACvO_QH00_Xket77duvABtQw8b82qh1lHomtNM_hJcK7eVjIztEqj7diHIx_gcuMiuKF5W-Ja7mMnrrBal2L6s_LZMjzmv78kqcNiw-iGtmoan6MdWSOC5D7eCeWFhImWKS2LaZlZJMF6JYz3dlkPhJkrKdbm4tZNNNy1siTMvFtUMVUUQLufIF4GI3PhJt70MCXkK8FGRNFEGS0tIn1KcZnmvaEbQ3qPjzGJgzizRBKlRKupAb2lqG6AP8aAxs3K3eaA0kuXtaABRUzxXAiXYEDP0BzA")
                    .addHeader("cache-control", "no-cache")
                    .addHeader("Postman-Token", "025fa8b6-ba0c-4ef3-adcd-ac23585aa410")
                    .build();

        }
        return request;
    }

}
