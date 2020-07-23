package hendro.smart_in.duan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hendro.smart_in.duan.Class.NguoiDung;
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.R;


public class ChangePasswordActivity extends AppCompatActivity {
EditText edpass, edrepass,edusername;
Button btn;
NguoiDungDAO nguoiDungDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        setTitle("ĐỔI MẬT KHẨU");
        edpass = findViewById(R.id.edPassword_DMK);
        edrepass = findViewById(R.id.edRePassword_DMK);
        edusername = findViewById(R.id.edUserName_DMK);
        btn = findViewById(R.id.btnChangePass);
    }
    public int validateform(){
        int check = 1;
        if (edpass.getText().length() == 0||edrepass.getText().length() == 0){
            Toast.makeText(getApplicationContext(),"bạn phải điền đầy đủ thông tin",Toast.LENGTH_SHORT).show();
            check = -1;
        }else {
            String pass = edpass.getText().toString();
            String repass = edrepass.getText().toString();
            if (!pass.equals(repass)){
                Toast.makeText(getApplicationContext(),"Mật khẩu không trùng khớp",Toast.LENGTH_SHORT).show();
               check = -1;
            }
        }
        return check;
    }
    public  void changePassword(View view){
        String strusername = edusername.getText().toString();
        nguoiDungDAO = new NguoiDungDAO(ChangePasswordActivity.this);
        NguoiDung user = new NguoiDung(strusername, edpass.getText().toString(),"","");
        try {
            if (validateform()>0){
                if (nguoiDungDAO.changePasswordNguoiDung(user)>0){
                    Toast.makeText(getApplicationContext(),"Lưu Thành Công",Toast.LENGTH_SHORT).show();

                }else {
                    Toast.makeText(getApplicationContext(),"Lưu thất bại ", Toast.LENGTH_SHORT).show();

                }
            }finish();

        }catch (Exception ex ){
            Log.e("Error", ex.toString());
        }
    }
}
