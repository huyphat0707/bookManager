package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;



import java.util.ArrayList;
import java.util.List;
import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.Class.TheLoai;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.DAO.TheLoaiDAO;
import hendro.smart_in.duan.R;

public class BookActivity extends AppCompatActivity {
    SachDAO sachDAO;
    TheLoaiDAO theLoaiDAO;
    Spinner spnTheLoai;
    EditText edMaSach, edTenSach, edTacGia, edNXB, edGiaBan, edSoLuong;
    String matheloai;
    List<TheLoai> listTheLoai = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        setTitle("Add Book");
        spnTheLoai = (Spinner) findViewById(R.id.spnTheLoai);
        getTheLoai();
        edMaSach = (EditText) findViewById(R.id.edMaSach);
        edTenSach = (EditText) findViewById(R.id.edTenSach);
        edNXB = (EditText) findViewById(R.id.edNXB);
        edTacGia = (EditText) findViewById(R.id.edTacGia);
        edGiaBan = (EditText) findViewById(R.id.edGiaBan);
        edSoLuong = (EditText) findViewById(R.id.edSoLuong);
        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                matheloai = listTheLoai.get(spnTheLoai.getSelectedItemPosition()).  getMaTheLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null){
            edMaSach.setText(b.getString("MASACH"));
            String maTheloai = b.getString("MATHELOAI");
            edTenSach.setText((b.getString("TENSACH")));
            edNXB.setText(b.getString("NXB"));
            edTacGia.setText(b.getString("TACGIA"));
            edGiaBan.setText(b.getString("GIABAN"));
            edSoLuong.setText(b.getString("SOLUONG"));
            spnTheLoai.setSelection(checkPositionTheLoai(maTheloai));
        }
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
                Intent intent = new Intent(BookActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void showSpinner(){
        sachDAO = new SachDAO(BookActivity.this);
        sachDAO.getAllSach();
    }

    public void getTheLoai(){
        theLoaiDAO = new TheLoaiDAO(BookActivity.this);
        listTheLoai = theLoaiDAO.getAllTheLoai();
        ArrayAdapter<TheLoai> dataAdapter = new ArrayAdapter<TheLoai>(this,android.R.layout.simple_spinner_item, listTheLoai);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTheLoai.setAdapter(dataAdapter);
    }

    public void addSach(View view) {
        sachDAO = new SachDAO(BookActivity.this);
        Sach sach = new Sach(edMaSach.getText().toString(),
                matheloai, edTenSach.getText().toString(),
                edTacGia.getText().toString(),
                edNXB.getText().toString(),
                Double.parseDouble(edGiaBan.getText().toString()),
                Integer.parseInt(edSoLuong.getText().toString()));
        try {
            if (sachDAO.insertSach(sach) > 0){
                Toast.makeText(getApplicationContext(), "thêm sách thành công", Toast.LENGTH_LONG).show();
            }else {
                Toast.makeText(getApplicationContext(), "thêm sách thất bại", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Log.e("Error", ex.toString());
        }
    }

    public void showBook(View view) {
        Intent intent = new Intent(BookActivity.this, ListBookActivity.class);
        startActivity(intent);
    }
    public int checkPositionTheLoai(String strTheLoai){
        for (int i = 0; i < listTheLoai.size(); i++){
            if (strTheLoai.equals(listTheLoai.get(i).getMaTheLoai())){
                return i;
            }
        }
        return 0;
    }

}
