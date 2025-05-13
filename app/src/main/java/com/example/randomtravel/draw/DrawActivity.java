package com.example.randomtravel.draw;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.divyanshu.draw.widget.DrawView;
import com.example.randomtravel.R;
import com.example.randomtravel.tflite.*;

import java.io.IOException;
import java.util.Locale;
import java.util.Random;

public class DrawActivity extends AppCompatActivity {

    Classifier cls;
    String[] array1 = {"제주도 협재 해수욕장","부산 해운대","속초 해수욕장","거제도 구조라 해수욕장","포항 영일대 해수욕장","태안 안면도 꽃지 해수욕장"
            , "강릉 경포대 해수욕장","완도 명사십리 해수욕장","인천 을왕리 해수욕장", "울산 간절곶 해수욕장"};
    String[] array2 = {"설악산","한라산","지리산","오대산","북한산","속리산","덕유산","팔공산","월악산","치악산"};
    String[] array3 = {"평창 오토캠핑장","가평 자라섬 캠핑장","남해 다랭이마을 캠핑장","강릉 주문진 해변 캠핑장","홍천강 오토캠핑장"
            ,"양양 서핑파크 캠핑장","충주호 캠핑장","무주 반디캠프 캠핑장","포천 캠프통 아일랜드","영월 동강 오토캠핑장"};
    String[] array4 = {"서울숲 공원","한강공원 반포지구","부산 대저생태공원","경주 대릉원","울산 태화강 국가정원","수원 광교호수공원","인천 송도 센트럴파크"
            , "창원 용지공원","제주도 한림공원", "대전 한밭수목원"};
    String[] array5 = {"서울대학교 관악캠퍼스","고려대학교 안암캠퍼스","연세대학교 신촌캠퍼스","경희대학교 서울캠퍼스"
            , "한양대학교 서울캠퍼스","부산대학교","전남대학교","경북대학교","충남대학교","제주대학교"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        DrawView drawView = findViewById(R.id.drawView);
        drawView.setStrokeWidth(100.0f);
        drawView.setBackgroundColor(Color.BLACK);
        drawView.setColor(Color.WHITE);

        TextView resultView = findViewById(R.id.resultView);

        Button classifyBtn = findViewById(R.id.classifyBtn);
        classifyBtn.setOnClickListener(v -> {
            Bitmap image = drawView.getBitmap();

            Pair<Integer, Float> res = cls.classify(image);
            int digit = res.first;

            if (digit < 1 || digit > 5) {
                Toast.makeText(this, "잘못된 입력입니다. 다시 입력하세요.", Toast.LENGTH_SHORT).show();
                resultView.setText("숫자 1~5를 입력하세요.");
            } else {
                String recommendation = getTravelRecommendation(digit);
                resultView.setText(String.format(Locale.ENGLISH, "추천 여행지: %s", recommendation));
            }
        });

        Button clearBtn = findViewById(R.id.clearBtn);
        clearBtn.setOnClickListener(v -> {
            drawView.clearCanvas();
        });

        cls = new Classifier(this);
        try {
            cls.init();
        } catch(IOException ioe) {
            Log.d("DigitClassifier", "failed to init Classifier", ioe);
        }

    }

    @Override
    protected void onDestroy() {
        cls.finish();
        super.onDestroy();
    }

    private String getTravelRecommendation(int digit){
        Random random = new Random();
        switch(digit){
            case 1:
                return array1[random.nextInt(array1.length)];
            case 2:
                return array2[random.nextInt(array2.length)];
            case 3:
                return array3[random.nextInt(array3.length)];
            case 4:
                return array4[random.nextInt(array4.length)];
            case 5:
                return array5[random.nextInt(array5.length)];
            default:
                return "유효하지 않은 입력입니다.";
        }
    }
}