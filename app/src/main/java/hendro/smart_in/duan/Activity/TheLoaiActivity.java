package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hendro.smart_in.duan.Class.NguoiDung;
import hendro.smart_in.duan.Class.TheLoai;
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.DAO.TheLoaiDAO;
import hendro.smart_in.duan.R;


public class TheLoaiActivity extends AppCompatActivity {
    Button btnThemTL;
    TheLoaiDAO theLoaiDAO;
    EditText edMTL, edTenTL, edvitri, edmota;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_the_loai);
        edMTL = findViewById(R.id.edMaTheLoai);
        edTenTL = findViewById(R.id.edTenTheLoai);
        edvitri = findViewById(R.id.edViTri);
        edmota = findViewById(R.id.edMoTa);
        btnThemTL = findViewById(R.id.btnAddTheLoai);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_home:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void addTheLoai(View view) {
        theLoaiDAO = new TheLoaiDAO(TheLoaiActivity.this);
        TheLoai theLoai = new TheLoai (
                edMTL.getText().toString(),
                edTenTL.getText().toString(),
                edmota.getText().toString(),
               Integer.parseInt( edvitri.getText().toString()));

        try {
            if (validateForm()>0){
                if (theLoaiDAO.insertTheloai(theLoai)>0){
                    Toast.makeText(getApplicationContext(),"Thêm thể loại thành công ",Toast.LENGTH_LONG).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();

                }
            }
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }


    }


    public void showTheLoai(View view) {
        Intent intent = new Intent(TheLoaiActivity.this, ListTheLoaiActivity.class);
        startActivity(intent);
    }
    public int validateForm(){
        int check = 1;
        if (edMTL.getText().length() == 0 || edTenTL.getText().length()==0
                ||edmota.getText().length()==0||edvitri.getText().length()==0
               ){
            Toast.makeText(getApplicationContext(),"bạn phải điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
            check = -1;

        }
        return check;
    }
}
