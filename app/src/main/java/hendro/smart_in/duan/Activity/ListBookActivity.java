package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;
import hendro.smart_in.duan.Adapter.BookAdapter;
import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.R;

public class ListBookActivity extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    EditText edSearchBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_book);
        setTitle("QUẢN LÝ SÁCH");
        sachDAO = new SachDAO(ListBookActivity.this);
         dsSach = sachDAO.getAllSach();
         lvBook = (ListView) findViewById(R.id.lvBook);
         adapter = new BookAdapter(this, dsSach);
         lvBook.setAdapter(adapter);
         lvBook.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                 Sach sach = (Sach) adapterView.getItemAtPosition(i);
                 Intent intent = new Intent(ListBookActivity.this, BookDetailActivity.class);
                 Bundle b = new Bundle();
                 b.putString("MASACH", sach.getMaSach());
                 b.putString("MATHELOAI", sach.getMaTheLoai());
                 b.putString("TENSACH", sach.getTenSach());
                 b.putString("TACGIA", sach.getTacGia());
                 b.putString("NXB", sach.getNXB());
                 b.putString("GIABAN", String.valueOf(sach.getGiaBan()));
                 b.putString("SOLUONG", String.valueOf(sach.getSoLuong()));
                 intent.putExtras(b);
                 startActivity(intent);
             }
         });
         lvBook.setTextFilterEnabled(true);
        edSearchBook =  findViewById(R.id.edSearchBook);
        edSearchBook.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                System.out.println("Text [" + s + "] - Start [" + start + "] - Before [" + before + "] - Count [" + count + "]");
                if (count < before) {
                    adapter.resetData();
                }
                adapter.getFilter().filter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_list_book,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_them_sach:
                Intent intent = new Intent(ListBookActivity.this, BookActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_home_listB:
                Intent intent1 = new Intent(ListBookActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//        dsSach.clear();
//        dsSach = sachDAO.getAllSach();
//        adapter.changeDataset(dsSach);
//    }
}
