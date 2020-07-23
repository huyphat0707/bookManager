package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Adapter.NguoiDungAdapter;
import hendro.smart_in.duan.Class.NguoiDung;
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.R;

public class ListUserActivity extends AppCompatActivity {
    public static List<NguoiDung> dsNguoiDung = new ArrayList<>();
    ListView lvNguoiDung;
    NguoiDungAdapter adapter = null;
    NguoiDungDAO nguoiDungDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_user);
        setTitle("Người Dùng");
        lvNguoiDung = (ListView) findViewById(R.id.lvNguoiDung);
        nguoiDungDAO = new NguoiDungDAO(ListUserActivity.this);
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter = new NguoiDungAdapter(ListUserActivity.this, dsNguoiDung);
        lvNguoiDung.setAdapter(adapter);
        lvNguoiDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListUserActivity.this, UserDetailActivity.class);
                Bundle bd = new Bundle();
                bd.putString("USERNAME", dsNguoiDung.get(i).getUserName());
                bd.putString("PHONE", dsNguoiDung.get(i).getPhone());
                bd.putString("FULLNAME", dsNguoiDung.get(i).getHoTen());
                intent.putExtras(bd);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        dsNguoiDung.clear();
        dsNguoiDung = nguoiDungDAO.getAllNguoiDung();
        adapter.changeDataset(nguoiDungDAO.getAllNguoiDung());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_user,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_add_user:
                Intent intent = new Intent(ListUserActivity.this, UserActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_doi_mk:
                Intent intent1 = new Intent(ListUserActivity.this, ChangePasswordActivity.class);
                startActivity(intent1);
                break;
            case R.id.menu_dang_xuat:
//                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
//                SharedPreferences.Editor edit = pref.edit();
//                edit.clear();
//                edit.commit();
//                Intent intent2 = new Intent(ListUserActivity.this, LoginActivity.class);
//                startActivity(intent2);
                showAlertDialog();
                break;
            case  R.id.menu_home_luser:
                Intent intent3 = new Intent(ListUserActivity.this, MainActivity.class);
                startActivity(intent3);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ListUserActivity.this);
        builder.setTitle("QUESTION ???");
        builder.setIcon(R.drawable.ic_warning_black_24dp);
        builder.setMessage("Are you sure want to log out ?");
        builder.setCancelable(false);
        builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(ListUserActivity.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
                SharedPreferences.Editor edit = pref.edit();
                edit.clear();
                edit.commit();
                Intent intent2 = new Intent(ListUserActivity.this, LoginActivity.class);
                startActivity(intent2);

            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();

    }
}
