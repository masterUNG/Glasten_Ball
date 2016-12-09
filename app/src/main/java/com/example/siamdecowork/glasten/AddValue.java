package com.example.siamdecowork.glasten;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

/**
 * Created by masterUNG on 12/6/2016 AD.
 */

public class AddValue extends AsyncTask<Void, Void, String> {

    private Context context;
    private static final String urlPHP = "http://swiftcodingthai.com/ball1/add_glasten.php";
    private String nameString, typeString, image1String, image2String,
            image3String, image4String, image5String, widthString,
            heightString, addString, detailString;

    public AddValue(Context context,
                    String nameString,
                    String typeString,
                    String image1String,
                    String image2String,
                    String image3String,
                    String image4String,
                    String image5String,
                    String widthString,
                    String heightString,
                    String addString,
                    String detailString) {
        this.context = context;
        this.nameString = nameString;
        this.typeString = typeString;
        this.image1String = image1String;
        this.image2String = image2String;
        this.image3String = image3String;
        this.image4String = image4String;
        this.image5String = image5String;
        this.widthString = widthString;
        this.heightString = heightString;
        this.addString = addString;
        this.detailString = detailString;
    }

    @Override
    protected String doInBackground(Void... voids) {

        try {

            showLog();

            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody requestBody = new FormEncodingBuilder()
                    .add("isAdd", "true")
                    .add("Name", nameString)
                    .add("Type", typeString)
                    .add("Image1", image1String)
                    .add("Image2", image2String)
                    .add("Image3", image3String)
                    .add("Image4", image4String)
                    .add("Image5", image5String)
                    .add("Width", widthString)
                    .add("Height", heightString)
                    .add("Add", addString)
                    .add("Detail", detailString)
                    .build();
            Request.Builder builder = new Request.Builder();
            Request request = builder.url(urlPHP).post(requestBody).build();
            Response response = okHttpClient.newCall(request).execute();
            return response.body().string();

        } catch (Exception e) {
            Log.d("6decV3", "e doIn ==> " + e.toString());
            return null;
        }


    }

    private void showLog() {
        Log.d("6decV3", "" + nameString);
        Log.d("6decV3", "" + typeString);
        Log.d("6decV3", "" + image1String);
        Log.d("6decV3", "" + image2String);
        Log.d("6decV3", "" + image3String);
        Log.d("6decV3", "" + image4String);
        Log.d("6decV3", "" + image5String);
        Log.d("6decV3", "" + widthString);
        Log.d("6decV3", "" + heightString);
        Log.d("6decV3", "" + addString);
        Log.d("6decV3", "" + detailString);
    }
}   // Main Class
