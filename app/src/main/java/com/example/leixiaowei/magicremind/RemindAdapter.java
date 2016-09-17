package com.example.leixiaowei.magicremind;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leixiaowei on 16/9/15.
 */
public class RemindAdapter extends BaseAdapter {
    private List<RemindItem> remindItems;
    private Context context;

    public RemindAdapter(Context context, RemindTable remindTable) {
        this(context, remindTable != null ? remindTable.getRemindItems() : new ArrayList<>());
    }

    public RemindAdapter(Context context, List<RemindItem> remindItems) {
        this.context = context;
        if (remindItems == null) {
            remindItems = new ArrayList<>();
        }
        this.remindItems = remindItems;
    }

    @Override
    public int getCount() {
        return remindItems.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder(parent.getContext());
            convertView = viewHolder.itemView;
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.setData(remindItems.get(position)).setDeleteListener(v -> {
            remindItems.remove(position);
            notifyDataSetChanged();
        });

        return viewHolder.itemView;
    }


    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        RemindFileUtil.saveObject(context, new RemindTable(remindItems));
    }

    static class ViewHolder {
        View itemView;
        TextView content;
        ImageView delete;

        View.OnClickListener deleteListener;

        ViewHolder(Context context) {
            itemView = View.inflate(context, R.layout.remind_item, null);
            content = (TextView) itemView.findViewById(R.id.content);
            delete = (ImageView) itemView.findViewById(R.id.delete);

            delete.setOnClickListener(v -> {
                if (deleteListener != null) {
                    deleteListener.onClick(v);
                }
            });
        }

        ViewHolder setDeleteListener(View.OnClickListener deleteListener) {
            this.deleteListener = deleteListener;
            return this;
        }

        ViewHolder setData(RemindItem remindItem) {
            content.setText(remindItem.remindText);
            return this;
        }
    }
}
