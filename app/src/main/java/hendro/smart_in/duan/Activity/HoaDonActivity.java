package hendro.smart_in.duan.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;


import hendro.smart_in.duan.Class.HoaDon;
import hendro.smart_in.duan.DAO.HoaDonDAO;
import hendro.smart_in.duan.R;


public class HoaDonActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener  {
    EditText edNgayMua, edMaHoaDon;
    HoaDonDAO hoaDonDAO;
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don);
        edNgayMua = (EditText) findViewById(R.id.edNgayMua);
        edMaHoaDon = (EditText) findViewById(R.id.edMaHoaDon);

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
                Intent intent = new Intent(HoaDonActivity.this, MainActivity.class);
                startActivity(intent);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    public void AddHoaDon(View view) {

        hoaDonDAO = new HoaDonDAO(HoaDonActivity.this);
        try {
            if (validation() < 0) {
                Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            }else {
                HoaDon hoaDon = new HoaDon(
                        edMaHoaDon.getText().toString(),
                     sdf.parse(  edNgayMua.getText().toString()));
                if (hoaDonDAO.insertHoaDon(hoaDon)>0){
                    Toast.makeText(getApplicationContext(),"thêm thành công ",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(HoaDonActivity.this, HoaDonDetailActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("MAHOADON", edMaHoaDon.getText().toString());
                            intent.putExtras(bundle);
                            startActivity(intent);

                }else {
                    Toast.makeText(getApplicationContext(),"Thêm Thất Bại", Toast.LENGTH_SHORT).show();

                }
            }
        }catch (Exception e){
            Log.e("Error",e.toString());


        }

    }

    public int validation(){
        if (edMaHoaDon.getText().toString().isEmpty()||edNgayMua.getText().toString().isEmpty() ){
            return -1;
        }
            return 1;
    }

    public void DatePicker(View view) {
        DatePickerFragment fragment = new DatePickerFragment();
        fragment.show(getSupportFragmentManager(),"date");

    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year, month, day);
        setDate(cal);
    }

    private void setDate(Calendar calendar) {
        edNgayMua.setText(sdf.format(calendar.getTime()));
    }
    public static class DatePickerFragment extends DialogFragment {
        @RequiresApi(api = Build.VERSION_CODES.N)
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(),
                    (DatePickerDialog.OnDateSetListener)
                    getActivity(),year,month,day);
        }
    }
}
