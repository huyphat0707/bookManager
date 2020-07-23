package hendro.smart_in.duan.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import hendro.smart_in.duan.Class.NguoiDung;
import hendro.smart_in.duan.DAO.NguoiDungDAO;
import hendro.smart_in.duan.R;


public class NguoiDungAdapter extends BaseAdapter {
    List<NguoiDung> listND;
    public Activity context;
    public LayoutInflater inflater;
    NguoiDungDAO nguoiDungDAO;

    public NguoiDungAdapter( Activity context, List<NguoiDung> listND) {
        super();
        this.listND = listND;
        this.context = context;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        nguoiDungDAO = new NguoiDungDAO(context);
    }

    @Override
    public int getCount() {
        return listND.size();
    }

    @Override
    public Object getItem(int i) {
        return listND.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public static class ViewHolder{
        ImageView img;
        TextView txtName;
        TextView txtPhone;
        ImageView imgDelete;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view = inflater.inflate(R.layout.line_user, null);
            holder.img = (ImageView) view.findViewById(R.id.im_logo);
            holder.txtName = (TextView) view.findViewById(R.id.tvName);
            holder.txtPhone = (TextView) view.findViewById(R.id.tvPhone);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imDelete);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    nguoiDungDAO.deleteNguoiDungByID(listND.get(i).getUserName());
                    listND.remove(i);
                    notifyDataSetChanged();
                    Toast.makeText(context , "Xóa người dùng thành công ", Toast.LENGTH_SHORT).show();
                }
            });
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        NguoiDung entry  = listND.get(i);

//        if (i % 3 == 0){
//         holder.img.setImageResource(R.drawable.emone);
//        }else if (i % 3 == 1){
//            holder.img.setImageResource(R.drawable.emtwo);
//        }else {
//            holder.img.setImageResource(R.drawable.emthree);
//        }
        holder.txtName.setText(entry.getHoTen());
        holder.txtPhone.setText(entry.getPhone());
        return view;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<NguoiDung> items){
        this.listND = items;
        notifyDataSetChanged();
    }


}
