package hendro.smart_in.duan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.R;



public class LoginActivity extends AppCompatActivity {
    EditText edUserName, edPassword;
    Button btnLogin, btnCancel;
    CheckBox chkRememberPass;
    String strUser, strPass;
    NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        edUserName = (EditText) findViewById(R.id.edUserName);
        edPassword = (EditText) findViewById(R.id.edPassWord);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCancel = (Button) findViewById(R.id.btnCancel);
        chkRememberPass = (CheckBox) findViewById(R.id.chkRememberPass);
        nguoiDungDAO = new NguoiDungDAO(LoginActivity.this);
    }



//    public void checkLogin(View view) {
//        strUser = edUserName.getText().toString();
//        strPass = edPassword.getText().toString();
//
//        if (strUser.isEmpty() || strPass.isEmpty()){
//            Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống !", Toast.LENGTH_LONG).show();
//        }else {
//            if(strUser.equalsIgnoreCase("admin")&&strPass.equalsIgnoreCase("admin")){
//                rememberUser(strUser, strPass, chkRememberPass.isChecked());
//                Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
//                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                startActivity(intent);
//
//            }else {
//                Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không đúng", Toast.LENGTH_LONG).show();
//            }
//        }
//
//
//    }
public void checkLogin(View view) {
    strUser = edUserName.getText().toString();
    strPass = edPassword.getText().toString();
    boolean checkLG = nguoiDungDAO.Luu(strUser, strPass);
    if (strUser.isEmpty() || strPass.isEmpty()){
        Toast.makeText(getApplicationContext(), "Tên đăng nhập và mật khẩu không được bỏ trống !", Toast.LENGTH_LONG).show();
    }else {
        if(checkLG){
            rememberUser(strUser, strPass, chkRememberPass.isChecked());
            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else if (strUser.equalsIgnoreCase("admin")&&strPass.equalsIgnoreCase("admin")){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
        }
        else {
            Toast.makeText(getApplicationContext(), "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_LONG).show();
        }
    }

}

    public void rememberUser(String u, String pass, boolean status){
        SharedPreferences pref =    getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status){
            editor.clear();
        } else {
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", pass);
            editor.putBoolean("REMEMBER", status);
        }

        editor.commit();
    }
}
