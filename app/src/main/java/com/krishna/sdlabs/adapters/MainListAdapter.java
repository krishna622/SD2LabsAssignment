package com.krishna.sdlabs.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.krishna.sdlabs.R;
import com.krishna.sdlabs.models.CustomResultModel;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

        private final Context mContext;
    private final LayoutInflater layoutInflater;
    private List<CustomResultModel> userModelList;

    public MainListAdapter(Context context,List<CustomResultModel> userModelList) {
        this.userModelList = userModelList;
        mContext = context;
        layoutInflater = LayoutInflater.from(context);

    }

    public void updateUserList(List<CustomResultModel> userModelList){
        this.userModelList.addAll(userModelList);
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView =  null;
        if(viewType == 0){
            itemView= layoutInflater
                    .inflate(R.layout.list_row, parent, false);
            return new ItemViewHolder(itemView);
        }else if(viewType == 1){
            itemView= layoutInflater
                    .inflate(R.layout.chld_list_row_single, parent, false);
            return new SingleItemViewHolder(itemView);
        }else if(viewType == 2){
            itemView = layoutInflater
                    .inflate(R.layout.chld_list_row, parent, false);
            return new MultipleItemViewHolder(itemView);
        }


        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        CustomResultModel userModel = userModelList.get(position);
        if(userModel.getType()==0){
            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;

            Glide.with(mContext)
                    .load(userModel.getCustomUserModel().getUserImage())
                    .into(itemViewHolder.userImageView);
            itemViewHolder.userNameTextView.setText(userModel.getCustomUserModel().getUserName());

        }else if(userModel.getType()==1){
            SingleItemViewHolder singleItemViewHolder = (SingleItemViewHolder) holder;
            Glide.with(mContext)
                    .load(userModel.getSingleUrl())
                    .into(singleItemViewHolder.imageView);

        }else if(userModel.getType()==2){
            MultipleItemViewHolder multipleItemViewHolder = (MultipleItemViewHolder) holder;
            Glide.with(mContext)
                    .load(userModel.getDoubleUrlList().get(0))
                    .into(multipleItemViewHolder.imageView1);
            Glide.with(mContext)
                    .load(userModel.getDoubleUrlList().get(1))
                    .into(multipleItemViewHolder.imageView2);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return userModelList.get(position).getType();
    }

    @Override
    public int getItemCount() {
        return userModelList.size();
    }



    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView userImageView;
        public TextView userNameTextView;

        public ItemViewHolder(View view) {
            super(view);
            userImageView = view.findViewById(R.id.userImage);
            userNameTextView = view.findViewById(R.id.userName);

        }
    }

    public class MultipleItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView1;
        public ImageView imageView2;

        public MultipleItemViewHolder(View view) {
            super(view);
            imageView1 = view.findViewById(R.id.imageView1);
            imageView2 = view.findViewById(R.id.imageView2);

        }
    }

    public class SingleItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public SingleItemViewHolder(View view) {
            super(view);
            imageView = view.findViewById(R.id.imageView);
        }
    }

}
