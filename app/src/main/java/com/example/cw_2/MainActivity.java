package com.example.cw_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    protected static final String fileName = "cw_2_image_list.txt";

    private ArrayList<String> imgList;

    private ImageView imgView;
    private Button btnNext, btnPrev;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgView = findViewById(R.id.imageView);
        btnNext = findViewById(R.id.btnNext);
        btnPrev = findViewById(R.id.btnPrev);

        btnNext.setOnClickListener(v -> nextImg());
        btnPrev.setOnClickListener(v -> prevImg());

        imgList = getImgList();
        currentIndex = 0;

        loadImg();

    }

    public ArrayList<String> getImgList(){
        ArrayList<String> arrImg = new ArrayList<>();
        arrImg.add("https://toplist.vn/images/800px/thap-nghieng-pisa-489711.jpg");
        arrImg.add("https://e.khoahoc.tv/photos/image/2016/06/24/kim-tu-thap-650.jpg");

        getImageListFromFile(arrImg);
        Toast.makeText(this, "Get images successfully !!!", Toast.LENGTH_SHORT).show();

        return arrImg;
    }

    public void loadImg(){
        Picasso.get().load(imgList.get(currentIndex)).into(imgView);
    }

    private void nextImg() {
        currentIndex += 1;
        // Check if it is the last img
        if (imgList.size() == currentIndex){
            currentIndex = 0;
        }
        loadImg();
    }

    private void prevImg() {
        currentIndex -= 1;
        // Check if it is the first img
        if (currentIndex == -1){
            currentIndex = imgList.size() - 1;
        }
        loadImg();
    }

    private void getImageListFromFile(ArrayList<String> imageList) {
        try {
            InputStream inputStream = openFileInput(fileName);

            if (inputStream != null) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String url = "";
                while ((url = bufferedReader.readLine()) != null) {
                    imageList.add(url);
                }

                inputStream.close();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "Image not found.", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}