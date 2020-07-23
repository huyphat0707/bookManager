package hendro.smart_in.duan;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import hendro.smart_in.duan.DAO.HoaDonChiTietDAO;
import hendro.smart_in.duan.DAO.HoaDonDAO;
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.DAO.TheLoaiDAO;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "dbBookManager";
    public static final int VERSION = 1;
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(NguoiDungDAO.SQL_NGUOI_DUNG);
        database.execSQL(TheLoaiDAO.SQL_THE_LOAI);
        database.execSQL(SachDAO.SQL_SACH);
        database.execSQL(HoaDonDAO.SQL_HOA_DON);
        database.execSQL(HoaDonChiTietDAO.SQL_HOA_DON_CHI_TIET);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("Drop table if exists " + NguoiDungDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + TheLoaiDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + SachDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + HoaDonDAO.TABLE_NAME);
        sqLiteDatabase.execSQL("Drop table if exists " + HoaDonChiTietDAO.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }
}
