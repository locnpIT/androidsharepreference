package com.nguyenphuocloc.a2322_nguyenphuocloc_baitapsharereference;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView txtDiem;
    Button btnXacNhan;
    EditText editSo;
    int diem = 0;
    int soMay;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferences = getSharedPreferences("GamePrefs", MODE_PRIVATE);

        diem = sharedPreferences.getInt("diem", 0);

        soMay = mayRanDom();

        addControls();
        addEvent();

    }

    private int mayRanDom(){
        Random rand = new Random();
        int randomNumber = rand.nextInt(10) + 1;
        return randomNumber;
    }

    private void addControls(){
        txtDiem = findViewById(R.id.txtDiem);
        editSo = findViewById(R.id.txtNhapSo);
        btnXacNhan = findViewById(R.id.btnXacNhan);



    }

    private void addEvent() {
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Kiểm tra xem người dùng đã nhập số chưa
                if (editSo.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập một số", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int soNguoi = Integer.parseInt(editSo.getText().toString());

                    if (soNguoi != soMay) {
                        Toast.makeText(getApplicationContext(), "Bạn đã đoán sai, mời đoán lại", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Bạn đã đoán đúng", Toast.LENGTH_SHORT).show();
                        diem++;
                        txtDiem.setText(String.valueOf(diem));

                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putInt("diem", diem);
                        editor.apply();

                        soMay = mayRanDom();
                    }
                } catch (NumberFormatException e) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập một số hợp lệ", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}