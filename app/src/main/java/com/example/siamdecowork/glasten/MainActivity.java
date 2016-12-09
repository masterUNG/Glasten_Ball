package com.example.siamdecowork.glasten;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jibble.simpleftp.SimpleFTP;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Explicit
    private EditText nameEditText, widthEditText,
            heightEditText, detailEditText;
    private ImageView imageView, pic1ImageView, pic2ImageView,
            pic3ImageView, pic4ImageView, pic5ImageView,
            backImageView, nextImageView;
    private RadioGroup radioGroup;
    private RadioButton doorRadioButton, windowRadioButton;
    private String nameString, widthString, heightString,
            detailString, typeString, imageString,
            imagePathString, imageFileString;
    private boolean imageABoolean = true;
    private ArrayList<String> stringArrayList;
    private int indexAnInt = 0;
    private String[] titleStrings, valueStrings;
    private MyConstant myConstant;
    private Spinner spinner;
    private TextView textView;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Setup
        stringArrayList = new ArrayList<String>();

        //Bind Widget
        bindWidget();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.radioButton:
                        typeString = "Door";
                        break;
                    case R.id.radioButton2:
                        typeString = "Window";
                        break;
                }
            }
        });


        //Image Controller
        pic1ImageView.setOnClickListener(this);
        pic2ImageView.setOnClickListener(this);
        pic3ImageView.setOnClickListener(this);
        pic4ImageView.setOnClickListener(this);
        pic5ImageView.setOnClickListener(this);

        //Back & Next Controller
        backImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Log.d("6decV2", "arrayList size ==> " + stringArrayList.size());
                Log.d("6decV2", "index ==> " + indexAnInt);
                if (stringArrayList.size() > 0) {

                    if (indexAnInt <= 0) {

                        indexAnInt = stringArrayList.size();
                        showImage(indexAnInt);

                    } else {

                        indexAnInt -= 1;
                        showImage(indexAnInt);
                    }

                } else {
                    Toast.makeText(MainActivity.this, "เลือกรูปก่อน", Toast.LENGTH_SHORT).show();
                }



            }   // onClick
        });

        nextImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (stringArrayList.size() > 0) {

                    if (indexAnInt <= stringArrayList.size()) {

                        showImage(indexAnInt);
                        indexAnInt += 1;

                    } else {

                        indexAnInt = 0;
                        showImage(indexAnInt);

                    }

                } else {
                    Toast.makeText(MainActivity.this, "เลือกรูปก่อน", Toast.LENGTH_SHORT).show();
                }

            }   // onClick
        });

        //Create Spinner
        createSpinner();


    }   // Main Method

    private void createSpinner() {

        myConstant = new MyConstant();
        titleStrings = myConstant.getTitleStrings();
        valueStrings = myConstant.getValueStrings();

        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(MainActivity.this,
                android.R.layout.simple_list_item_1, titleStrings);
        spinner.setAdapter(stringArrayAdapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                showText(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void showText(int index) {
        textView.setText(valueStrings[index]);
    }

    private void showImage(int indexAnInt) {

        try {

            String strPathImage = stringArrayList.get(indexAnInt);
            Log.d("6decV1", "strPathImage ==> " + strPathImage);
            File file = new File(strPathImage);
            Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            imageView.setImageBitmap(bitmap);

        } catch (Exception e) {
            Log.d("6decV1", "e showImage ==> " + e.toString());
        }

    }

    private void bindWidget() {

        nameEditText = (EditText) findViewById(R.id.editText);
        widthEditText = (EditText) findViewById(R.id.editText2);
        heightEditText = (EditText) findViewById(R.id.editText3);
        detailEditText = (EditText) findViewById(R.id.editText4);
        imageView = (ImageView) findViewById(R.id.imageView);
        radioGroup = (RadioGroup) findViewById(R.id.ragType);
        doorRadioButton = (RadioButton) findViewById(R.id.radioButton);
        windowRadioButton = (RadioButton) findViewById(R.id.radioButton2);
        pic1ImageView = (ImageView) findViewById(R.id.imageView4);
        pic2ImageView = (ImageView) findViewById(R.id.imageView5);
        pic3ImageView = (ImageView) findViewById(R.id.imageView6);
        pic4ImageView = (ImageView) findViewById(R.id.imageView7);
        pic5ImageView = (ImageView) findViewById(R.id.imageView8);
        backImageView = (ImageView) findViewById(R.id.imageView2);
        nextImageView = (ImageView) findViewById(R.id.imageView3);
        textView = (TextView) findViewById(R.id.textView6);
        spinner = (Spinner) findViewById(R.id.spinner);

    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            imageABoolean = false;

            //Find path of Image
            Uri uri = data.getData();
            imagePathString = myFindPathImage(uri);
            Log.d("16SepV2", "imagePathString ==> " + imagePathString);
            stringArrayList.add(imagePathString);

            //Find name of File Image
            imageFileString = imagePathString.substring(imagePathString.lastIndexOf("/"));
            Log.d("16SepV2", "imageFileString ==> " + imageFileString);

            //SetImage to imageView
            try {

                ImageView[] imageViews = new ImageView[]{pic1ImageView, pic2ImageView,
                        pic3ImageView, pic4ImageView, pic5ImageView};

                Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(uri));
                imageView.setImageBitmap(bitmap);
                imageViews[requestCode].setImageBitmap(bitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }   // if

    }   // onActivity

    private String myFindPathImage(Uri uri) {

        String strResult = null;

        String[] strings = {MediaStore.Video.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, strings,
                null, null, null);

        if (cursor != null) {

            cursor.moveToFirst();
            int intIndex = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            strResult = cursor.getString(intIndex);

        } else {
            strResult = uri.getPath();
        }

        return strResult;
    }

    public void clickSaveDataMain(View view) {

        //Get Value From Edit Text
        nameString = nameEditText.getText().toString().trim();
        widthString = widthEditText.getText().toString().trim();
        heightString = heightEditText.getText().toString().trim();
        detailString = detailEditText.getText().toString().trim();

        //Check Space
        if (nameString.equals("") || widthString.equals("") ||
                heightString.equals("") || detailString.equals("")) {

            // Have Space
            MyAlert myAlert = new MyAlert(this, R.drawable.doremon48,
                    "มีช่องว่าง", "กรุณากรอก ทุกช่อง คะ");
            myAlert.myDialog();
        } else if (!(doorRadioButton.isChecked() || windowRadioButton.isChecked())) {
            // UnCheck RadioButton
            MyAlert myAlert = new MyAlert(this, R.drawable.nobita48,
                    "ยังไม่เลือกชนิด", "กรุณาเลือก ชนิด ด้วยคะ");
            myAlert.myDialog();
        } else if (imageABoolean) {
            // Non Choose Image
            MyAlert myAlert = new MyAlert(this, R.drawable.bird48,
                    "ยังไม่ได้เลือกรูป", "กรุณาคลิกที่รูป และ เลือกรูป");
            myAlert.myDialog();
        } else {
            // Complete Choose Image
            uploadImageToServer();

            uploadStringToServer();

        }   // if


        Log.d("16SepV1", "imageBoolean ==> " + imageABoolean);

    }   // clickSave

    private void uploadStringToServer() {

        String[] imageStrings = new String[5];
        for (int i=0;i<5;i++) {
            imageStrings[i] = "http://swiftcodingthai.com/ball1/Image/question_man.png";
        }

        for (int i=0;i<stringArrayList.size();i++) {
            imageStrings[i] = "http://swiftcodingthai.com/ball1/Image" + findName(stringArrayList.get(i));
        }

        try {

            AddValue addValue = new AddValue(MainActivity.this, nameString, typeString,
                    imageStrings[0], imageStrings[1], imageStrings[2], imageStrings[3],
                    imageStrings[4], widthString, heightString, textView.getText().toString(),
                    detailString);
            addValue.execute();

            if (Boolean.parseBoolean(addValue.get())) {
                Toast.makeText(MainActivity.this, "Success Upload", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(MainActivity.this, "Cannot Upload", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }



    }   // upload

    private String findName(String s) {
        return s.substring(s.lastIndexOf("/"));
    }

    private void uploadImageToServer() {

        //Create Policy
        StrictMode.ThreadPolicy threadPolicy = new StrictMode.ThreadPolicy
                .Builder().permitAll().build();
        StrictMode.setThreadPolicy(threadPolicy);

        for (int i=0;i<stringArrayList.size();i++) {

            try {

                SimpleFTP simpleFTP = new SimpleFTP();
                simpleFTP.connect("ftp.swiftcodingthai.com", 21,
                        "ball1@swiftcodingthai.com", "Abc12345");
                simpleFTP.bin();
                simpleFTP.cwd("Image");
                simpleFTP.stor(new File(stringArrayList.get(i)));
                simpleFTP.disconnect();

                Toast.makeText(MainActivity.this, "Upload Image Finish", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                Log.d("16SepV1", "e ftp ==> " + e.toString());
            }

        }   // for

    }   // uploadImageToServer

    public void clickListDataMain(View view) {

    }

    @Override
    public void onClick(View view) {

        int i = 0;

        switch (view.getId()) {
            case R.id.imageView4:
                i = 0;
                break;
            case R.id.imageView5:
                i = 1;
                break;
            case R.id.imageView6:
                i = 2;
                break;
            case R.id.imageView7:
                i = 3;
                break;
            case R.id.imageView8:
                i = 4;
                break;
        }   // switch

        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(Intent.createChooser(intent,
                "โปรดเลือกรูปภาพ"), i);



    }   // onClick

}   // Main Class