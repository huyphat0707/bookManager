package hendro.smart_in.duan.DAO;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Class.NguoiDung;
import hendro.smart_in.duan.DatabaseHelper;


public class NguoiDungDAO {
    private SQLiteDatabase database;
    private DatabaseHelper dbHelper;

    public static final String TABLE_NAME = "NguoiDung";
    public static final String SQL_NGUOI_DUNG = "CREATE TABLE NguoiDung (username text primary key, password text, phone text, hoten text);";
    public static final String TAG = "NguoiDungDAO";
    public NguoiDungDAO(Context context){
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public int insertNguoiDung(NguoiDung nguoiDung){
        ContentValues values = new ContentValues();
        values.put("username", nguoiDung.getUserName());
        values.put("password",nguoiDung.getPassword());
        values.put("phone", nguoiDung.getPhone());
        values.put("hoten",nguoiDung.getHoTen());
        try {
            if(database.insert(TABLE_NAME, null, values) == -1){
                return -1;
            }
        }catch (Exception ex){
            Log.e(TAG,ex.toString());
        }
        return 1;
    }

    public List<NguoiDung> getAllNguoiDung(){
        List<NguoiDung> dsNguoiDung = new ArrayList<>();
        Cursor cursor = database.query(TABLE_NAME,null,null,null,null,null,null);
        cursor.moveToFirst();
        while (cursor.isAfterLast() == false){
            NguoiDung ee = new NguoiDung();
            ee.setUserName(cursor.getString(0));
            ee.setPassword(cursor.getString(1));
            ee.setPhone(cursor.getString(2));
            ee.setHoTen(cursor.getString(3));
            dsNguoiDung.add(ee);
            Log.d("//====",ee.toString());
            cursor.moveToNext();
        }
        cursor.close();
        return dsNguoiDung;
    }

    public int updateNguoiDung(NguoiDung nd){
        ContentValues values = new ContentValues();
        values.put("username", nd.getUserName());
        values.put("password", nd.getPassword());
        values.put("phone",nd.getPhone());
        values.put("hoten",nd.getHoTen());
        int result = database.update(TABLE_NAME,values, "username=?", new String[]{nd.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int changePasswordNguoiDung(NguoiDung nd){
        ContentValues values = new ContentValues();
        values.put("username",nd.getUserName());
        values.put("password",nd.getPassword());
        int result = database.update(TABLE_NAME, values, "username=?", new String[]{nd.getUserName()});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    public int updateInfoNguoiDung(String username, String phone, String name){
        ContentValues values = new ContentValues();
        values.put("phone", phone);
        values.put("hoten", name);
        int result = database.update(TABLE_NAME, values, "username=?", new String[]{username});
        if (result == 0){
            return  -1;
        }
        return 1;
    }

    //DELETE
    public int deleteNguoiDungByID(String username){
        int result = database.delete(TABLE_NAME, "username=?", new String[]{username});
        if (result == 0){
            return -1;
        }
        return 1;
    }

    //CHECK LOGIN
    public int checkLogin (String username, String password){
        int result = database.delete(TABLE_NAME, "username=? AND password=?", new String[]{username, password});
        if (result == 0){
            return -1;
        }
        return 1;
    }
    public Boolean Luu(String user,String pass){
        database = dbHelper.getWritableDatabase();
        Cursor cursor = database.rawQuery("select * from NguoiDung where username=? AND password=?",new String[]{user,pass});
        if(cursor.getCount()>0){
            return true;
        }else {
            return false;
        }
    }
}
