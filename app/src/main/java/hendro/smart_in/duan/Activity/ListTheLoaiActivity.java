package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import hendro.smart_in.duan.Adapter.TheLoaiAdapter;
import hendro.smart_in.duan.Class.TheLoai;
import hendro.smart_in.duan.DAO.TheLoaiDAO;
import hendro.smart_in.duan.R;


public class ListTheLoaiActivity extends AppCompatActivity {
    public static List<TheLoai> dsTheLoai = new ArrayList<>();
    ListView lvTheLoai;
    TheLoaiAdapter adapter = null;
    TheLoaiDAO theLoaiDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_the_loai);
        setTitle("Thể Loại");
        lvTheLoai = findViewById(R.id.lvTheLoai);
        theLoaiDAO = new TheLoaiDAO(ListTheLoaiActivity.this);
        dsTheLoai = theLoaiDAO.getAllTheLoai();
        adapter = new TheLoaiAdapter(this, dsTheLoai);
        lvTheLoai.setAdapter(adapter);

        lvTheLoai.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListTheLoaiActivity.this, TheLoaiDetailActivity.class);
                Bundle bd = new Bundle();
                bd.putString("MATHELOAI", dsTheLoai.get(i).getMaTheLoai());
                bd.putString("TENTHELOAI", dsTheLoai.get(i).getTenTheLoai());
                bd.putString("VITRI",String.valueOf( dsTheLoai.get(i).getViTri()));
                bd.putString("MOTA", dsTheLoai.get(i).getMoTa() ) ;
                intent.putExtras(bd);
                startActivity(intent);
            }
        });

    }
    @Override
    protected void onResume() {
        super.onResume();
        dsTheLoai.clear();
        dsTheLoai = theLoaiDAO.getAllTheLoai ();
        adapter.changeDataset(theLoaiDAO.getAllTheLoai());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_the_loai,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_them_the_loai:
                Intent intent = new Intent(ListTheLoaiActivity.this, TheLoaiActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_home_listTL:
                Intent intent1 = new Intent(ListTheLoaiActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
