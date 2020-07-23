package hendro.smart_in.duan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.R;

public class BookDetailActivity extends AppCompatActivity {
    EditText edTenSach;
    EditText edGiaBan;
    EditText edSoLuong;
SachDAO sachDAO;
String TenSach,SoLuong,GiaBan,Masach,MaTheLoai,NXB, TacGia;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CHI TIẾT SÁCH");
        setContentView(R.layout.activity_book_detail);
        edTenSach = (EditText) findViewById(R.id.edTenSach_CT);
        edGiaBan = (EditText) findViewById(R.id.edGiaBan_CT);
        edSoLuong = (EditText) findViewById(R.id.edSoLuong_CT);
         sachDAO= new SachDAO(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        Masach = bundle.getString("MASACH");
        MaTheLoai= bundle.getString("MATHELOAI");
        NXB = bundle.getString("NXB");
        TacGia = bundle.getString("TACGIA");
        GiaBan = bundle.getString("GIABAN");
        TenSach = bundle.getString("TENSACH");
        SoLuong = bundle.getString("SOLUONG");

        edTenSach.setText(TenSach);
        edGiaBan.setText(GiaBan);
        edSoLuong.setText(SoLuong);

    }
    public void updateBook(View view){
     if (sachDAO.updateSach(Masach,edTenSach.getText().toString(),MaTheLoai,TacGia,NXB,
             edGiaBan.getText().toString(),
        edSoLuong.getText().toString())>0){
            Toast.makeText(getApplicationContext(),"Lưu thành công ",Toast.LENGTH_SHORT).show();
            finish();
    }else {
         Toast.makeText(getApplicationContext(),"Lưu thất bại ",Toast.LENGTH_SHORT).show();

     }
    }


    public void huysach(View view) {
        finish();
    }
}
