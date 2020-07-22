package com.example.myapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class RecycleViewAdapter  extends RecyclerView.Adapter <RecycleViewAdapter.MyViewHolder> implements Filterable {
  private Context mcontext;
  private List<thing>mdata;
  RequestOptions option;
    private List<thing>mdatafull;




    public RecycleViewAdapter(Context mcontext, List<thing> mdata) {
        this.mcontext = mcontext;
        this.mdata = mdata;
        option=new RequestOptions().centerCrop().placeholder(R.drawable.loading_image).error(R.drawable.loading_image);
        mdatafull=new ArrayList<>(mdata);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater minflater =LayoutInflater.from(mcontext);
        view=minflater.inflate(R.layout.cardview_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {

        holder.tv_thing_price.setText("₹"+mdata.get(position).getPrice());
        holder.tv_thing_title.setText(mdata.get(position).getThing_title());


        Glide.with(mcontext).load(mdata.get(position).getImage_url1()).apply(option).into(holder.img_thing_thumbnail);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(mcontext,moreinfo.class);
                intent.putExtra("price",mdata.get(position).getPrice());
                intent.putExtra("category",mdata.get(position).getCategory());
                intent.putExtra("Description",mdata.get(position).getDescription());
                intent.putExtra("image_url1",mdata.get(position).getImage_url1());
                intent.putExtra("image_url2",mdata.get(position).getImage_url2());
                intent.putExtra("image_url3",mdata.get(position).getImage_url3());
                intent.putExtra("hostel_block",mdata.get(position).getHostel_block());
                intent.putExtra("item_id",mdata.get(position).getItem_id());
                mcontext.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }

    @Override
    public Filter getFilter() {
        return mdatafilter;
    }
    private Filter mdatafilter=new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            List<thing>filteredlist=new ArrayList<>();

            if(constraint==null||constraint.length()==0){
                filteredlist.addAll(mdatafull);

            }else{
                String filterpattern=constraint.toString().toLowerCase().trim();

                for(thing item : mdatafull){
                    if(item.getThing_title().toLowerCase().contains(filterpattern)){
                        filteredlist.add(item);
                    }
                }
            }
            FilterResults results=new FilterResults();
            results.values=filteredlist;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            mdata.clear();
            mdata.addAll((Collection<? extends thing>) results.values);
            notifyDataSetChanged();

        }
    };


    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tv_thing_price,tv_thing_title;
        ImageView img_thing_thumbnail;
        CardView cardView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_thing_price=itemView.findViewById(R.id.thing_price);
            img_thing_thumbnail=itemView.findViewById(R.id.image_thing);
            cardView=itemView.findViewById(R.id.cardview_id);
            tv_thing_title=itemView.findViewById(R.id.thing_title);
        }
    }

}
