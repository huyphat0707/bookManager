package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Adapter.BookAdapter;
import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.R;
public class TopBookActivity extends AppCompatActivity {
    public static List<Sach> dsSach = new ArrayList<>();
    ListView lvBook;
    BookAdapter adapter = null;
    SachDAO sachDAO;
    EditText edThang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_book);
        lvBook = (ListView) findViewById(R.id.lvBookTop);
        edThang = (EditText) findViewById(R.id.edThang);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_home:
                Intent intent = new Intent(TopBookActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void ViewSachTop10(View view) {
        if (Integer.parseInt(edThang.getText().toString()) > 13 ||
                Integer.parseInt(edThang.getText().toString()) < 0) {
            Toast.makeText(getApplicationContext(), "Không đúng định dạng tháng (112)", Toast.LENGTH_SHORT).show();
        } else {
            sachDAO = new SachDAO(TopBookActivity.this);
        }
        dsSach = sachDAO.getSachTop10(edThang.getText().toString());
        adapter = new BookAdapter(this, dsSach);
        lvBook.setAdapter(adapter);

}

}
