package sk.smoradap.kamnavyletsk.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import sk.smoradap.kamnavyletsk.R;
import sk.smoradap.kamnavyletsk.api.model.Photo;

/**
 * Created by smora on 05.09.2016.
 */
public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ViewHolder> {

    private List<String> imageUrls;
    private Context context;
    private OnImageClickedInterface callback;

    public ImageRecyclerAdapter(Context context, Collection<? extends Photo> photos, OnImageClickedInterface callback){
        this.context = context;
        this.callback = callback;
        this.imageUrls = new ArrayList<>();
        for(Photo photo : photos){
            imageUrls.add(photo.getUrl());
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater vi = LayoutInflater.from(parent.getContext());
        View v = vi.inflate(R.layout.recycler_image, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.progressBar.setVisibility(View.VISIBLE);
        Glide.with(context)
                .load(imageUrls.get(position))
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                })
                .fitCenter()
                .into(holder.image);
        holder.image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(callback != null){
                    callback.imageClicked(holder.getAdapterPosition());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView image;
        ProgressBar progressBar;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            image = (ImageView) itemView.findViewById(R.id.imageView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress);
            this.itemView = itemView;
        }
    }

    public interface OnImageClickedInterface{
        void imageClicked(int position);
    }


}
