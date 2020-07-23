package hendro.smart_in.duan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.R;

public class UserDetailActivity extends AppCompatActivity {
EditText edfullname, edPhone;
NguoiDungDAO nguoiDungDAO;
String userName,fullname,phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("CHI TIẾT NGƯỜI DÙNG");
        setContentView(R.layout.activity_user_detail);
        edfullname = findViewById(R.id.edfullname);
        edPhone = findViewById(R.id.edphone);
        nguoiDungDAO = new NguoiDungDAO(this);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        fullname = bundle.getString("FULLNAME");
        phone = bundle.getString("PHONE");
        userName = bundle.getString("USERNAME");

        edfullname.setText(fullname);
        edPhone.setText(phone);


    }
    public void updateUser(View view){
        if (nguoiDungDAO.updateInfoNguoiDung(
                userName,edPhone.getText().toString(),
                edfullname.getText().toString())> 0 ){
            Toast.makeText(getApplicationContext(),"Lưu thành công ",Toast.LENGTH_SHORT).show();

        }
    }
    public  void Huy(View view){
            finish();
    }
}
