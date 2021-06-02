package com.jl.tcs.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.jl.tcs.R;
import com.jl.tcs.model.RankBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class RankAdapter extends RecyclerView.Adapter<RankAdapter.ViewHolder> {

    private final Context mContext;
    private List<RankBean> mList;

    public RankAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public List<RankBean> getList() {
        return mList;
    }

    public void setList(List<RankBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @NotNull
    @Override
    //将item_dialog_rank .xml实例化，并以ViewHolder的形式呈现
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dialog_rank, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.usernameView.setText(mList.get(position).getUsername());
        holder.scoreView.setText(mList.get(position).getScore());
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView scoreView;
        private final TextView usernameView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.tv_username);
            scoreView = itemView.findViewById(R.id.tv_score);
        }
    }
}
