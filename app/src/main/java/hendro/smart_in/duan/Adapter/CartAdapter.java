package hendro.smart_in.duan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import hendro.smart_in.duan.Class.HoaDonChiTiet;
import hendro.smart_in.duan.DAO.HoaDonChiTietDAO;
import hendro.smart_in.duan.R;

public class CartAdapter extends BaseAdapter {
    List<HoaDonChiTiet> ArrayHoaDonChiTiet;
    public Activity context;
    public LayoutInflater inflater;
    HoaDonChiTietDAO hoaDonChiTietDAO;
    private static final String TAG = "CardAdapter";

    public CartAdapter( List<HoaDonChiTiet> ArrayHoaDonChiTiet, Activity context) {
       super();
        this.ArrayHoaDonChiTiet = ArrayHoaDonChiTiet;
        this.context = context;
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
//
//    public CartAdapter(, List<HoaDonChiTiet> dsHDCT) {
//    }

    @Override
    public int getCount() {
        return ArrayHoaDonChiTiet.size();
    }

    @Override
    public Object getItem(int position) {
        return ArrayHoaDonChiTiet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    public static class ViewHolder {
        TextView txtMaSach;
        TextView txtSoLuong;
        TextView txtGiaBia;
        TextView txtThanhTien;
        ImageView imgDelete;     }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if(convertView==null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_hoadonchitietbyid, null);
            holder.txtMaSach = (TextView) convertView.findViewById(R.id.tvMaSach);
            holder.txtSoLuong = (TextView) convertView.findViewById(R.id.tvSoLuong);
            holder.txtGiaBia = (TextView) convertView.findViewById(R.id.tvGiaBia);
            holder.txtThanhTien = (TextView) convertView.findViewById(R.id.tvThanhTien);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.ivDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    hoaDonChiTietDAO = new HoaDonChiTietDAO(context);
                    Log.e(TAG, "onClick: "+ArrayHoaDonChiTiet.get(position).getMaHDCT());
                    hoaDonChiTietDAO.deleteHoaDonChiTietByID(ArrayHoaDonChiTiet.get(position).getMaHDCT());
                    ArrayHoaDonChiTiet.remove(position);
                    notifyDataSetChanged();
                    Toast.makeText(context,"xóa hóa đơn chi tiết thành công", Toast.LENGTH_SHORT).show();

                }
            });
            convertView.setTag(holder);
        }else {
            holder =(ViewHolder) convertView.getTag();
        }
        HoaDonChiTiet _entry = (HoaDonChiTiet) ArrayHoaDonChiTiet.get(position);
        holder.txtMaSach.setText("Mã sách: "+_entry.getSach().getMaSach());
        holder.txtSoLuong.setText("Số lượng: "+_entry.getSoLuongMua());
        holder.txtGiaBia.setText("Giá bán: "+_entry.getSach().getGiaBan() +" vnd");
        holder.txtThanhTien.setText("Thành tiền: "+_entry.getSoLuongMua()*_entry.getSach().getGiaBan()+" vnd");


        return convertView;
    }
    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
    public void changeDataset(List<HoaDonChiTiet> items){
        this.ArrayHoaDonChiTiet = items;
        notifyDataSetChanged();

    }


}
