package hendro.smart_in.duan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Class.HoaDon;
import hendro.smart_in.duan.Class.HoaDonChiTiet;
import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.DatabaseHelper;

public class HoaDonChiTietDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "HoaDonChiTiet";
    public static final String SQL_HOA_DON_CHI_TIET = "CREATE TABLE HoaDonChiTiet (maHDCT INTEGER PRIMARY KEY AUTOINCREMENT," +
            "mahoadon text NOT NULL, masach text NOT NULL, soluong INTEGER);";
    public static  final String TAG = "HoaDonChiTiet";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    public HoaDonChiTietDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public int insertHoaDonChiTiet (HoaDonChiTiet hdct){
        ContentValues values = new ContentValues();
        values.put("mahoadon", hdct.getHoaDon().getMaHoaDon());
        values.put("masach", hdct.getSach().getMaSach());
        values.put("soluong", hdct.getSoLuongMua());
        try {
            if (database.insert(TABLE_NAME, null, values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public List<HoaDonChiTiet> getAllHoaDonChiTietByID(String maHoaDon){
        List<HoaDonChiTiet> dsHoaDonChiTiet = new ArrayList<>();
        String sSQL = "SELECT maHDCT, HoaDon.mahoadon, HoaDon.ngaymua, Sach.masach, Sach.matheloai," +
                " Sach.tensach, Sach.tacgia, Sach.NXB, Sach.giaban, Sach.soluong, HoaDonChiTiet.soluong " +
                "FROM HoaDonChiTiet INNER JOIN HoaDOn ON HoaDonChiTiet.mahoadon = HoaDon.mahoadon " +
                "INNER JOIN Sach ON Sach.masach = HoaDonChiTiet.masach where HoaDonChiTiet.mahoadon= '" +  maHoaDon + "'";
        Cursor c = database.rawQuery(sSQL, null);
        c.moveToFirst();
        try {
            while (c.isAfterLast() == false){
                HoaDonChiTiet ee = new HoaDonChiTiet();
                ee.setMaHDCT(c.getInt(0));
                ee.setHoaDon(new HoaDon(c.getString(1),sdf.parse(c.getString(2))));
                ee.setSach(new Sach(c.getString(3), c.getString(4),c.getString(5),c.getString(6), c.getString(7), c.getInt(8), c.getInt(9)));
                ee.setSoLuongMua(c.getInt(10));
                dsHoaDonChiTiet.add(ee);
                Log.d("//====", ee.toString());
                c.moveToNext();
            }
            c.close();
        }catch (Exception e){
            Log.d(TAG, e.toString());
        }
        return dsHoaDonChiTiet;
    }

    public int updateHoaDonChiTiet (HoaDonChiTiet hdct){
        ContentValues values = new ContentValues();
        values.put("maHDCT", hdct.getMaHDCT());
        values.put("mahoadon", hdct.getHoaDon().getMaHoaDon());
        values.put("masach", hdct.getSach().getMaSach());
        values.put("soluong",hdct.getSoLuongMua());
        int result = database.update(TABLE_NAME, values, "maHDCT=?", new String[]{String.valueOf(hdct.getMaHDCT())});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int deleteHoaDonChiTietByID(int maHDCT){
        int result = database.delete(TABLE_NAME,"maHDCT=?",new String[]{String.valueOf(maHDCT)});
        if (result == 0)
            return -1;
        Log.e(TAG, "deleteHoaDonChiTietByID: "+result);
        return 1;
    }

    public boolean checkHoaDon(String maHoaDon){
        String[] columns = {"mahoadon"};
        String selection = "mahoadon=?";
        String[] selectionArgs = {maHoaDon};
        Cursor c = null ;
        try {
            c = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
            int i = c.getCount();
            c.close();
            if (i <= 0){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public double getDoanhThuTheoNgay(){
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) FROM (SELECT SUM(Sach.giaban * HoaDonChiTiet.soluong) as 'tongtien'" +
                "FROM HoaDon INNER JOIN HoaDonChiTiet ON HoaDon.mahoadon = HoaDonChiTiet.mahoadon " +
                "INNER JOIN Sach ON HoaDonChiTiet.masach = Sach.masach where HoaDon.ngaymua = date('now') GROUP BY HoaDonChiTiet.masach)tmp";
        Cursor c = database.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoThang(){
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien ) FROM (SELECT SUM(Sach.giaban * HoaDonChiTiet.soluong) as 'tongtien' " +
                "FROM HoaDon INNER JOIN HoaDonChiTiet ON HoaDon.mahoadon = HoaDonChiTiet.mahoadon " +
                " INNER JOIN Sach ON HoaDonChiTiet.masach = Sach.masach where strftime('%m', HoaDon.ngaymua) = strftime('%m','now') GROUP BY HoaDonChiTiet.masach)tmp";
        Cursor c = database.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }

    public double getDoanhThuTheoNam(){
        double doanhThu = 0;
        String sSQL = "SELECT SUM(tongtien) FROM (SELECT SUM(Sach.giaban * HoaDonChiTiet.soluong) as 'tongtien' " +
                "FROM HoaDon INNER JOIN HoaDonChiTiet ON HoaDon.mahoadon = HoaDonChiTiet.mahoadon " +
                " INNER JOIN Sach ON HoaDonChiTiet.masach = Sach.masach where strftime('%Y', HoaDon.ngaymua) = strftime('%Y','now') GROUP BY HoaDonChiTiet.masach)tmp";
        Cursor c = database.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            doanhThu = c.getDouble(0);
            c.moveToNext();
        }
        c.close();
        return doanhThu;
    }
}