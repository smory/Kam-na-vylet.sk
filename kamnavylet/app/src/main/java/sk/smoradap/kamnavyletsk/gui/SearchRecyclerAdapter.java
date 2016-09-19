package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import sk.smoradap.kamnavyletsk.model.SearchResult;

/**
 * Created by psmorada on 19.09.2016.
 */
public class SearchRecyclerAdapter extends RecyclerView.Adapter<SearchRecyclerAdapter.SearchViewHolder> {

    Context mContext;
    List<SearchResult> mList;

    public SearchRecyclerAdapter(Context context, List<SearchResult> list){
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
        SearchResult r = mList.get(position);
        holder.item.setIcon(r.getFullImageUrl());
        holder.item.setName(r.getName());
        holder.item.setPlace(r.getTown());
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