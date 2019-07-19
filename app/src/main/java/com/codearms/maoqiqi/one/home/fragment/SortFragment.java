package com.codearms.maoqiqi.one.home.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codearms.maoqiqi.base.BaseFragment;
import com.codearms.maoqiqi.one.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Link: https://github.com/maoqiqi/AndroidUtils
 * Author: fengqi.mao.march@gmail.com
 * Date: 2019-07-16 17:11
 */
public class SortFragment extends BaseFragment {

    @Override
    protected View createView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container) {
        return inflater.inflate(R.layout.fragment_sort, container, false);
    }

    @Override
    protected void initViews(@Nullable Bundle savedInstanceState) {
        super.initViews(savedInstanceState);

        List<String> list = new ArrayList<>();
        list.add("Android");
        list.add("iOS");
        list.add("前端");
        list.add("拓展资源");
        list.add("瞎推荐");
        list.add("福利");
        list.add("休息视频");

        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);

        SortAdapter adapter = new SortAdapter(getContext(), list);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SortCallBack(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    class SortAdapter extends RecyclerView.Adapter<ViewHolder> implements ItemTouchHelperAdapter {

        Context context;
        List<String> beans;

        SortAdapter(Context context, List<String> beans) {
            this.context = context;
            this.beans = beans;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.item_sort, parent, false);
            return new ViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            if (position != 0) {
                holder.iv_icon.setImageResource(R.drawable.icon_sort_item);
            } else {
                holder.iv_icon.setImageResource(R.drawable.icon_fix);
            }
            holder.tv_name.setText(beans.get(position));
            holder.itemView.setOnClickListener(v -> {
                // CommonUtils.vibrate(getMContext(), 100);
            });
        }

        @Override
        public int getItemCount() {
            return beans.size();
        }

        @Override
        public void onItemMove(int fromPosition, int toPosition) {
            if (fromPosition != 0 && toPosition != 0) {
                Collections.swap(beans, fromPosition, toPosition);
                notifyItemMoved(fromPosition, toPosition);
                Log.e("info", saveCategoryString());
            } else {
                Toast.makeText(context, "第一个条目不允许拖动", Toast.LENGTH_SHORT).show();
            }
        }

        @Override
        public void onItemDismiss(int position) {
            beans.remove(position);
            notifyItemRemoved(position);
        }

        private String saveCategoryString() {
            StringBuilder builder = new StringBuilder();
            List<String> list = beans;
            for (int i = 0; i < list.size(); i++) {
                if (i != list.size() - 1) {
                    builder.append(list.get(i));
                    builder.append("|");
                } else {
                    builder.append(list.get(i));
                }
            }
            return builder.toString();
        }
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {

        ImageView iv_icon;
        TextView tv_name;

        ViewHolder(View itemView) {
            super(itemView);
            iv_icon = itemView.findViewById(R.id.iv_icon);
            tv_name = itemView.findViewById(R.id.tv_name);
        }
    }

    public class SortCallBack extends ItemTouchHelper.Callback {

        private ItemTouchHelperAdapter adapter;

        SortCallBack(ItemTouchHelperAdapter adapter) {
            this.adapter = adapter;
        }

        @Override
        public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
            int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
            int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
            return makeMovementFlags(dragFlags, swipeFlags);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            adapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
            return true;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            adapter.onItemDismiss(viewHolder.getAdapterPosition());
        }

        @Override
        public boolean isLongPressDragEnabled() {
            return true;
        }

        @Override
        public boolean isItemViewSwipeEnabled() {
            return false;
        }
    }

    public interface ItemTouchHelperAdapter {

        void onItemMove(int fromPosition, int toPosition);

        void onItemDismiss(int position);
    }
}