package hendro.smart_in.duan.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.DAO.TheLoaiDAO;
import hendro.smart_in.duan.R;

public class TheLoaiDetailActivity extends AppCompatActivity {
EditText edmatheloai, edtentheloai,edvitri,edmota;
TheLoaiDAO theLoaiDAO;
String matheloai,tentheloai,vitri,mota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CHI TIẾT THỂ LOẠI");
        setContentView(R.layout.activity_the_loai_detail);
        edmatheloai = findViewById(R.id.edMaTheLoai_CT);
        edtentheloai = findViewById(R.id.edTenTheLoai_CT);
        edvitri = findViewById(R.id.edViTri_CT);
        edmota = findViewById(R.id.edMoTa_CT);
         theLoaiDAO = new TheLoaiDAO(this);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        matheloai = bundle.getString("MATHELOAI");
        tentheloai = bundle.getString("TENTHELOAI");
        vitri = bundle.getString("VITRI");
        mota = bundle.getString("MOTA");

        edmatheloai.setText(matheloai);
        edtentheloai.setText(tentheloai);
        edvitri.setText(vitri);
        edmota.setText(mota);


    }

    public void huyTheloai(View view) {
        finish();
    }

    public void UpdateTheLoai(View view) {
        if (theLoaiDAO.updateTheLoai(
             edmatheloai.getText().toString()
                ,edtentheloai.getText().toString(),mota,vitri)>0 ){
            Toast.makeText(getApplicationContext(),"Lưu thành công ",Toast.LENGTH_SHORT).show();
              finish();
        }else {
            Toast.makeText(getApplicationContext(),"Lưu thất bại ",Toast.LENGTH_SHORT).show();

        }
    }
}
