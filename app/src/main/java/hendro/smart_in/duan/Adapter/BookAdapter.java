package hendro.smart_in.duan.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import hendro.smart_in.duan.Class.Sach;
import hendro.smart_in.duan.DAO.SachDAO;
import hendro.smart_in.duan.R;

public class BookAdapter extends BaseAdapter implements Filterable {
   List<Sach> arrSach;
   List<Sach> arrSortSach;
   private Filter sachFilter;
   public Activity context;
   public LayoutInflater inflater;
   SachDAO sachDAO;
   public BookAdapter(Activity context , List<Sach> arraySach){
       super();
       this.context = context;
       this.arrSach =arraySach;
       this.arrSortSach = arraySach;
       this.inflater=
               (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
       sachDAO = new SachDAO(context);
   }

    @Override
    public int getCount() {
   return arrSach.size();

    }

    @Override
    public Object getItem(int i) {
        return arrSach.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
       ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            view =inflater.inflate(R.layout.line_book,null);
            holder.img = (ImageView) view.findViewById(R.id.im_logo_Book);
//            holder.txtBookName = (TextView) view.findViewById(R.id.tvNameBook);
            holder.txtBookPrice = (TextView) view.findViewById(R.id.tvPriceBook);
            holder.txtSoLuong = (TextView) view.findViewById(R.id.tvSoLuongBook);
            holder.imgDelete = (ImageView) view.findViewById(R.id.imgDeleteBook);
            holder.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sachDAO.deleteSachByID(arrSach.get(i).getMaSach());
                    arrSach.remove(i);
                    notifyDataSetChanged();
                    Toast.makeText(context , "Xóa sách thành công ", Toast.LENGTH_SHORT).show();

                }
            });
            view.setTag(holder);
        }else {
            holder = (ViewHolder)view.getTag();}
            Sach _entry = (Sach) arrSach.get(i);
            holder.img.setImageResource(R.drawable.bookicon);
//            holder.txtBookName.setText("Tên Sách: " + _entry.getTenSach());
            holder.txtBookPrice.setText("Mã Sách: " +_entry.getMaSach());
            holder.txtSoLuong.setText("Số Lượng: "+_entry.getSoLuong() );

       return view;
    }

    public static class ViewHolder{
       ImageView img;
//       TextView txtBookName;
       TextView txtBookPrice;
       TextView txtSoLuong;
       ImageView imgDelete;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    public void changeDataset(List<Sach> items){
       this.arrSach = items;
       notifyDataSetChanged();
    }
    public void resetData(){
       arrSach = arrSortSach;
    }

    @Override
    public Filter getFilter() {
       if (sachFilter == null)
       { sachFilter = new CustomFilter();}

        return sachFilter;
    }
    private class CustomFilter extends Filter{

        @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
            FilterResults results = new FilterResults();
            if (charSequence == null|| charSequence.length() == 0){
                results.values = arrSortSach;
                results.count = arrSortSach.size();
            }else {
                List<Sach> lsSach = new ArrayList<Sach>();
                for (Sach p: arrSach){
                    if (p.getTenSach().toUpperCase().startsWith(charSequence.toString().toUpperCase()))
                        lsSach.add(p);
                }
                results.values = lsSach;
                results.count = lsSach.size();
            }
            return results;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
        if (filterResults.count == 0 ){
            notifyDataSetInvalidated();
        }else {
            arrSach = (List<Sach>)filterResults.values;
            notifyDataSetChanged();
        }
        }
    }
}
