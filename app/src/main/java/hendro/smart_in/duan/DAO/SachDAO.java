package hendro.smart_in.duan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.DatabaseHelper;


public class SachDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "Sach";
    public static final String SQL_SACH = "CREATE TABLE Sach (masach text primary key, matheloai text, tensach text, tacgia text, NXB text, giaban double, soluong number);";
    public static final String TAG = "SachDAO";
    public SachDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //INSERT
    public  int insertSach(Sach sach){
        ContentValues values = new ContentValues();
        values.put("masach", sach.getMaSach());
        values.put("matheloai", sach.getMaTheLoai());
        values.put("tensach", sach.getTenSach());
        values.put("tacgia", sach.getTacGia());
        values.put("NXB", sach.getNXB());
        values.put("giaban", sach.getGiaBan());
        values.put("soluong", sach.getSoLuong());
        if (checkPrimaryKey(sach.getMaSach())){
            int result = database.update(TABLE_NAME, values, "masach=?", new String[]{sach.getMaSach()});
            if (result == 0){
                return -1;
            }
        }else {
            try {
                if (database.insert(TABLE_NAME, null, values) == -1){
                    return -1;
                }
            }catch (Exception e){
                Log.e(TAG, e.toString());
            }
        }
        return 1;
    }

    public List<Sach> getAllSach(){
        List<Sach> dsSach = new ArrayList<>();
        Cursor c = database.query(TABLE_NAME, null, null, null, null, null, null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            Sach s = new Sach();
            s.setMaSach(c.getString(0));
            s.setMaTheLoai(c.getString(1));
            s.setTenSach(c.getString(2));
            s.setTacGia(c.getString(3));
            s.setNXB(c.getString(4));
            s.setGiaBan(c.getDouble(5));
            s.setSoLuong(c.getInt(6));
            dsSach.add(s);
            Log.d("//=====", s.toString());
            c.moveToNext();
        }
        c.close();
        return dsSach;
    }

    public int updateSach(String maSach, String maTheLoai, String tenSach, String tacGia, String NXB, String giaBan, String soLuong){
        ContentValues values = new ContentValues();
        values.put("masach", maSach);
        values.put("matheloai", maTheLoai);
        values.put("tensach", tenSach);
        values.put("tacgia", tacGia);
        values.put("NXB", NXB);
        values.put("giaban", giaBan);
        values.put("soluong", soLuong);
        int result = database.update(TABLE_NAME, values, "masach=?", new String[]{maSach});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int deleteSachByID(String maSach){
        int result = database.delete(TABLE_NAME, "masach=?", new String[]{maSach});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public boolean checkPrimaryKey(String strPrimaryKey){
        String[] columns = {"masach"};
        String selection = "masach=?";
        String[] selectionArgs = {strPrimaryKey};
        Cursor cursor = null;
        try {
            cursor = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            cursor.moveToFirst() ;
            int i = cursor.getCount();
            cursor.close();
            if(i <= 0){
                return false;
            }
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }

    public Sach checkBook(String strPrimaryKey){
        Sach s = new Sach();
        String[] columns = {"masach"};
        String selection = "masach=?";
        String[] selectionArgs = {strPrimaryKey};
        Cursor c = null;
        try {
            c = database.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
            c.moveToFirst();
            while (c.isAfterLast() == false){
                s.setMaSach(c.getString(0));
                s.setMaTheLoai(c.getString(1));
                s.setTenSach(c.getString(2));
                s.setTacGia(c.getString(3));
                s.setNXB(c.getString(4));
                s.setGiaBan(c.getDouble(5));
                s.setSoLuong(c.getInt(6));
                Log.d("//=====", s.toString());
                break;
            }
            c.close();
            return s;
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Sach getSachByID(String maSach){
        Sach s = null;
        String selection = "masach=?";
        String[] selectionArgs = {maSach};
        Cursor c = database.query(TABLE_NAME, null, selection, selectionArgs, null, null, null);
        Log.d("getSachByID", "=>" + c.getCount());
        c.moveToFirst();
        while (c.isAfterLast() == false){
            s = new Sach();
            s.setMaSach(c.getString(0));
            s.setMaTheLoai(c.getString(1));
            s.setTenSach(c.getString(2));
            s.setTacGia(c.getString(3));
            s.setNXB(c.getString(4));
            s.setGiaBan(c.getDouble(5));
            s.setSoLuong(c.getInt(6));
            break;
        }
        c.close();
        return s;
     }

     public List<Sach> getSachTop10(String month){
        List<Sach> dsSach = new ArrayList<>();
        if (Integer.parseInt(month) < 10){
            month = "0" + month;
        }

        String sSQL = "SELECT maSach, SUM(soluong) as soluong FROM HoaDonChiTiet INNER JOIN HoaDon " +
         "ON HoaDon.mahoadon = HoaDonChiTiet.mahoadon WHERE strftime('%m',HoaDon.ngaymua) = '"+month+"' " +
                "GROUP BY masach ORDER BY soluong DESC LIMIT 10";
        Cursor c = database.rawQuery(sSQL, null);
        c.moveToFirst();
        while (c.isAfterLast() == false){
            Log.d("//====", c.getString(0));
            Sach s = new Sach();
            s.setMaSach(c.getString(0));
            s.setSoLuong(c.getInt(1));
            s.setGiaBan(c.getInt(0));
            s.setMaTheLoai("");
            s.setTenSach("");
            s.setTacGia("");
            s.setNXB("");
            dsSach.add(s);
            c.moveToNext();
        }
        c.close();
        return dsSach;
     }
}
