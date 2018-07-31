package example.am.alarstudiostestone.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import example.am.alarstudiostestone.R;
import example.am.alarstudiostestone.models.Data;

/**
 * Created by Khach on 31-Jul-18.
 */

public class DataLoaderAdapter extends ArrayAdapter<Data> {
    private Context context;
    private int resourceId;

    public DataLoaderAdapter(@NonNull Context context, int resource, @NonNull List<Data> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resourceId = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        int imageResourceId = getItem(position).getImageResourceId();
        String name = getItem(position).getName();

        Data data = new Data(imageResourceId, name);

        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(resourceId, parent, false);
            holder = new ViewHolder();
            holder.iv_data = convertView.findViewById(R.id.iv_data);
            holder.tv_name = convertView.findViewById(R.id.tv_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.iv_data.setImageResource(data.getImageResourceId());
        holder.tv_name.setText(data.getName());
        return convertView;
    }

    static class ViewHolder {
        ImageView iv_data;
        TextView tv_name;
    }
}
