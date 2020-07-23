package hendro.smart_in.duan.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Class.TheLoai;
import hendro.smart_in.duan.DatabaseHelper;


public class TheLoaiDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "TheLoai";
    public static final String SQL_THE_LOAI = "CREATE TABLE TheLoai (matheloai text primary key, tentheloai text, mota text, vitri int);";
    public static final String TAG = "TheLoaiDAO";

    public TheLoaiDAO(Context context) {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    //INSERT
    public int insertTheloai(TheLoai theLoai) {
        ContentValues values = new ContentValues();
        values.put("matheloai", theLoai.getMaTheLoai());
        values.put("tentheloai", theLoai.getTenTheLoai());
        values.put("mota", theLoai.getMoTa());
        values.put("vitri", theLoai.getViTri());
        try {
            if (database.insert(TABLE_NAME, null, values) == -1) {
                return -1;
            }
        } catch (Exception ex) {
            Log.e(TAG, ex.toString());
        }
        return 1;
    }

    public List<TheLoai> getAllTheLoai() {
        List<TheLoai> dsTheLoai = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false) {
            TheLoai ee = new TheLoai();
            ee.setMaTheLoai(cursor.getString(0));
            ee.setTenTheLoai(cursor.getString(1));
            ee.setMoTa(cursor.getString(2));
            ee.setViTri(cursor.getInt(3));
            dsTheLoai.add(ee);
            Log.d("//====", ee.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return dsTheLoai;
    }

    //UPDATE
    public int updateTheLoai(String matheLoai, String tenTheLoai, String mota, String vitri) {
        ContentValues values = new ContentValues();
        values.put("matheloai", matheLoai);
        values.put("tentheloai",tenTheLoai);
        values.put("mota", mota);
        values.put("vitri", vitri);
        int result = database.update(TABLE_NAME, values, "vitri=?", new String[]{vitri});
        if (result == 0) {
            return -1;
        }
        return 1;
    }

    //DELETE
    public int deleteTheLoaiByID(String matheloai) {
        int result = database.delete(TABLE_NAME, "matheloai=?", new String[]{matheloai});
        if (result == 0) {
            return -1;
        }
        return 1;
    }


}
