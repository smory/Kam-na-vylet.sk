package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sk.smoradap.kamnavyletsk.model.Item;

/**
 * Created by psmorada on 19.09.2016.
 */
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.SearchViewHolder> {

    private Context mContext;
    private List<? extends Item> mList;
    private OnItemPickedListener mListener;


    public ItemRecyclerAdapter(Context context, List<? extends Item> list, OnItemPickedListener listener){
        mContext = context;
        mList = list;
        mListener = listener;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        ItemView item = ItemView_.build(mContext);
        parent.addView(item);
        return new SearchViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final SearchViewHolder holder, int position) {
        final Item item = mList.get(position);
        holder.item.setIcon(item.getPreviewImageUrl());
        holder.item.setName(item.getName());
        holder.item.setPlace(item.getTown());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mListener != null){
                    mListener.onItemPicked(mList.get(holder.getAdapterPosition()));
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if(mList == null){
            return 0;
        } else {
            return mList.size();
        }

    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        ItemView item;

        public SearchViewHolder(ItemView itemView) {
            super(itemView);

            item = itemView;
        }
    }

    public interface OnItemPickedListener {
        void onItemPicked(Item item);
    }
}
