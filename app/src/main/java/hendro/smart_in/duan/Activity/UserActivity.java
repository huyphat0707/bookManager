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
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.R;


public class UserActivity extends AppCompatActivity {
    Button btnThemND;
    NguoiDungDAO nguoiDungDAO;
    EditText edUser, edPass, edRePass, edPhone, edFullName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        setTitle("Thêm người dùng");
        btnThemND = (Button) findViewById(R.id.btnAddUser);
        edUser = (EditText) findViewById(R.id.edUserNameUser);
        edPass = (EditText) findViewById(R.id.edPassworduser);
        edRePass = (EditText) findViewById(R.id.edRePasswordUser);
        edPhone = (EditText) findViewById(R.id.edPhoneUser);
        edFullName = (EditText) findViewById(R.id.edFullNameUser);
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

    public int validateForm(){
        int check = 1;
        if (edUser.getText().length() == 0 || edFullName.getText().length()==0
        ||edPhone.getText().length()==0||edPass.getText().length()==0
        ||edRePass.getText().length()==0){
            Toast.makeText(getApplicationContext(),"bạn phải điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
            check = -1;
        }else {
            String pass = edPass.getText().toString();
            String repass = edRePass.getText().toString();
            if (!pass.equals(repass)){
                Toast.makeText(getApplicationContext(),"mật khẩu phải trùng khớp",Toast.LENGTH_LONG).show();
            check = -1;
            }

        }
        return check;
    }

    public void showUsers(View view) {
        Intent intent = new Intent(UserActivity.this, ListUserActivity.class);
        startActivity(intent);
    }

    public void addUser(View view) {
        nguoiDungDAO = new NguoiDungDAO(UserActivity.this);
        NguoiDung user = new NguoiDung(
                edUser.getText().toString(),
                edPass.getText().toString(),
                edPhone.getText().toString(),
                edFullName.getText().toString());
        try {
            if (validateForm()>0){
                if (nguoiDungDAO.insertNguoiDung(user)>0){
                    Toast.makeText(getApplicationContext(),"Thêm người dùng thành công ",Toast.LENGTH_LONG).show();
                }else {
                    Toast.makeText(getApplicationContext(),"Thêm thất bại",Toast.LENGTH_LONG).show();
                }
            }
        }catch (Exception ex){
            Log.e("Error",ex.toString());
        }


    }
}
