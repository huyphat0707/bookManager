package hendro.smart_in.duan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import hendro.smart_in.duan.R;

public class MainActivity extends AppCompatActivity {
    ImageView imgNguoiDung, imgTheLoai, imgSach, imgHoaDon, imgTopSach, imgThongKe;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imgNguoiDung = findViewById(R.id.viewNguoiDung);
        imgTheLoai = findViewById(R.id.viewTheLoai);
        imgSach = findViewById(R.id.viewSach);
        imgHoaDon = findViewById(R.id.viewTopSach);
        imgTopSach = findViewById(R.id.viewTopSach);
        imgThongKe = findViewById(R.id.viewThongKe);

    }


    public void ViewNguoiDung(View view) {
        Intent intent = new Intent(MainActivity.this, ListUserActivity.class);
        startActivity(intent);
    }

    public void ViewTheLoai(View view) {
        Intent intent = new Intent(MainActivity.this, ListTheLoaiActivity.class);
        startActivity(intent);
    }

    public void ViewListBookActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ListBookActivity.class);
        startActivity(intent);
    }

    public void ViewListHoaDon(View view) {
        Intent intent = new Intent(MainActivity.this, ListHoaDonActivity.class);
        startActivity(intent);
    }

    public void ViewTopSach(View view) {
        Intent intent = new Intent(MainActivity.this, TopBookActivity.class);
        startActivity(intent);
    }

    public void ViewThongKeActivity(View view) {
        Intent intent = new Intent(MainActivity.this, ThongKeActivity.class);
        startActivity(intent);
    }
}
