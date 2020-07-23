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

import hendro.smart_in.duan.Class.NguoiDung;
import hendro.smart_in.duan.Class.TheLoai;
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.DAO.TheLoaiDAO;
import hendro.smart_in.duan.R;


public class TheLoaiAdapter extends BaseAdapter {
    List<TheLoai> listTL;
    public Activity context;
    public LayoutInflater inflater;
    TheLoaiDAO theLoaiDAO;

    public TheLoaiAdapter(Activity context, List<TheLoai> listTL) {
        super();
        this.listTL = listTL;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        theLoaiDAO = new TheLoaiDAO(context);
    }

    @Override
    public int getCount() {
        return listTL.size();
    }

    @Override
    public Object getItem(int i) {
        return listTL.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder{
        ImageView img;
        TextView txtStt;
        TextView txtTheloai;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.line_theloai, null);
            holder.img = (ImageView) convertView.findViewById(R.id.im_logo_TL);
            holder.txtStt = (TextView) convertView.findViewById(R.id.stt_TL);
            holder.txtTheloai = (TextView) convertView.findViewById(R.id.loaisach_TL);
            holder.imgDelete = (ImageView) convertView.findViewById(R.id.imgDelete_TL);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    theLoaiDAO = new TheLoaiDAO(context);
                    int result = theLoaiDAO.deleteTheLoaiByID(listTL.get(i).getMaTheLoai());

                    if (result < 1){
                        Toast.makeText(context, "Xoa khong thanh cong", Toast.LENGTH_SHORT).show();
                    }else {
                        listTL.remove(i);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Xóa thể loại thành công ", Toast.LENGTH_SHORT).show();
                        Log.e(null, "onClick: "+result);
                    }
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
            TheLoai entry = (TheLoai) listTL.get(i);
//            holder.img.setImageResource(R.drawable.theloai);
            holder.txtStt.setText("Mã Thể Loại: "+entry.getMaTheLoai());
            holder.txtTheloai.setText("Tên Thể Loại "+entry.getTenTheLoai());

        return convertView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<TheLoai> items){
        this.listTL = items;
        notifyDataSetChanged();
    }


}
