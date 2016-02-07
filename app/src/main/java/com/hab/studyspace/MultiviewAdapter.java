package com.hab.studyspace;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by yidanzeng on 2/7/16.
 */
public class MultiviewAdapter extends RecyclerView.Adapter<MultiviewAdapter.ViewHolder>{
    private List<Space> rooms;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        CardView cv;
        TextView name;
        TextView capacity;
        ImageView images; // list view in the future?

        public ViewHolder(View v) {
            super(v);
            cv = (CardView)itemView.findViewById(R.id.cv);
            name = (TextView)itemView.findViewById(R.id.room_name);
            capacity = (TextView)itemView.findViewById(R.id.room_capacity);
            images = (ImageView)itemView.findViewById(R.id.room_photoId);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MultiviewAdapter(List<Space> rooms) {
        this.rooms = rooms;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public MultiviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_multicards, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element

        holder.name.setText(rooms.get(position).name);
        holder.capacity.setText(rooms.get(position).capacity+"");
        holder.images.setImageResource(rooms.get(position).photoId);

        System.out.println("NAME " + holder.name.toString());
        System.out.println("CAPACITY " + holder.capacity.toString());
        System.out.println("PHOTO " + holder.images.toString());

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return rooms.size() ;
    }

}
