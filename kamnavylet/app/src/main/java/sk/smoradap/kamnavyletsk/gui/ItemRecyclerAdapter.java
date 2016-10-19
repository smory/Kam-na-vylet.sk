package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sk.smoradap.kamnavyletsk.details.DetailsActivity_;
import sk.smoradap.kamnavyletsk.model.Item;
import sk.smoradap.kamnavyletsk.model.SearchResult;

/**
 * Created by psmorada on 19.09.2016.
 */
public class ItemRecyclerAdapter extends RecyclerView.Adapter<ItemRecyclerAdapter.SearchViewHolder> {

    Context mContext;
    List<? extends Item> mList;

    public ItemRecyclerAdapter(Context context, List<? extends Item> list){
        mContext = context;
        mList = list;
    }

    @Override
    public SearchViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        SearchItem item = SearchItem_.build(mContext);
        parent.addView(item);
        return new SearchViewHolder(item);
    }

    @Override
    public void onBindViewHolder(SearchViewHolder holder, int position) {
        final Item item = mList.get(position);
        holder.item.setIcon(item.getPreviewImageUrl());
        holder.item.setName(item.getName());
        holder.item.setPlace(item.getTown());

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailsActivity_.class);
                i.putExtra(DetailsActivity_.URL, item.getSourceUrl());
                mContext.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class SearchViewHolder extends RecyclerView.ViewHolder{

        SearchItem item;

        public SearchViewHolder(SearchItem itemView) {
            super(itemView);

            item = itemView;
        }
    }
}
