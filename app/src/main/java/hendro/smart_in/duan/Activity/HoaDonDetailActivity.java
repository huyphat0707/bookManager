package hendro.smart_in.duan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import hendro.smart_in.duan.Adapter.CartAdapter;
import hendro.smart_in.duan.Class.HoaDon;
import hendro.smart_in.duan.Class.HoaDonChiTiet;
import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.DAO.HoaDonChiTietDAO;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.R;

public class HoaDonDetailActivity extends AppCompatActivity {
    EditText edMaSach, edMaHoaDon, edSoLuong;
    TextView tvThanhTien;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    SachDAO sachDAO;
    public List<HoaDonChiTiet> dsHDCT = new ArrayList<>();
    ListView lvCart;
    CartAdapter adapter = null;
    double thanhTien = 0;
    Button btnshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoa_don_detail);
        setTitle("CHI TIẾT HOÁ ĐƠN");
        edMaSach =  findViewById(R.id.edMaSach);
        edMaHoaDon =  findViewById(R.id.edMaHoaDon);
        edSoLuong =  findViewById(R.id.edSoLuongMua);
        lvCart =  findViewById(R.id.lvCart);
        tvThanhTien =  findViewById(R.id.tvThanhTien);
        adapter = new CartAdapter(dsHDCT, this);
        lvCart.setAdapter(adapter);
        btnshow = findViewById(R.id.showDetail);
        btnshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HoaDonDetailActivity.this, ListHoaDonActivity.class);
                startActivity(i);
            }
        });

        Intent in = getIntent();
        Bundle b = in.getExtras();
        if (b != null) {
            edMaHoaDon.setText(b.getString("MAHOADON"));
        }
    }

    public void ADDHoaDonCHITIET(View view) {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonDetailActivity.this);
        sachDAO = new SachDAO(HoaDonDetailActivity.this);
        try {
            if (validation()<0){
                Toast.makeText(getApplicationContext(),"Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();

            }else {
                Sach sach = sachDAO.getSachByID(edMaSach.getText().toString());
                if (sach!= null){
                    int pos = checkMaSach(dsHDCT,edMaSach.getText().toString());
                    HoaDon hoaDon = new HoaDon(edMaHoaDon.getText().toString(),new Date());
                    HoaDonChiTiet hoaDonChiTiet = new HoaDonChiTiet(1, hoaDon,sach,Integer.parseInt(edSoLuong.getText().toString()));
                    if (pos>= 0 ){
                        int soluong = dsHDCT.get(pos).getSoLuongMua();
                        hoaDonChiTiet.setSoLuongMua(soluong + Integer.parseInt(edSoLuong.getText().toString()));
                        dsHDCT.set(pos, hoaDonChiTiet);
                    }else {
                        dsHDCT.add(hoaDonChiTiet);
                    }
                    adapter.changeDataset(dsHDCT);
                }else {
                    Toast.makeText(getApplicationContext(),"Mã sách không tồn tại",Toast.LENGTH_SHORT).show();
                }
            }
        }catch (Exception ex)
        {
            Log.e("Error",ex.toString());
        }
    }

    public void thanhToanHoaDon(View view) {
        hoaDonChiTietDAO = new HoaDonChiTietDAO(HoaDonDetailActivity.this);
        thanhTien = 0;
        try {
            for (HoaDonChiTiet hd: dsHDCT){
                hoaDonChiTietDAO.insertHoaDonChiTiet(hd);
                thanhTien = thanhTien+  hd.getSoLuongMua() * hd.getSach().getGiaBan();}
            tvThanhTien.setText("Tổng Tiền: " +thanhTien);
            }catch (Exception ex){
            Log.e("Error", ex.toString());
        }
    }
    public int validation(){
        if (edMaSach.getText().toString().isEmpty()||edSoLuong.getText().toString().isEmpty()|| edMaHoaDon.getText().toString().isEmpty()){
            return -1;
        }
        return 1;
    }
    public int checkMaSach(List<HoaDonChiTiet> lsHD, String maSach){
        int pos = -1;
        for (int i = 0; i < lsHD.size(); i++){
            HoaDonChiTiet hd = lsHD.get(i);
            if (hd.getSach().getMaSach().equalsIgnoreCase(maSach)){
                pos = i;
                break;
            }
        }
        return pos;
    }

}
