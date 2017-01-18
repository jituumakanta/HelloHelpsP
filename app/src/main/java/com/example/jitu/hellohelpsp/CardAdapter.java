package com.example.jitu.hellohelpsp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.List;



public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    //Imageloader to load image
    private ImageLoader imageLoader;
    private Context context;
    //List to store all superheroes
    List<SuperHero> superHeroes;
    //Constructor of this class
    public CardAdapter(List<SuperHero> superHeroes, Context context){
        super();
        //Getting all superheroes
        this.superHeroes = superHeroes;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.superheroes_list, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        //Getting the particular item from the list
        final SuperHero superHero =  superHeroes.get(position);

        //Loading image from url
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(superHero.getImageUrl(), ImageLoader.getImageListener(holder.imageView, R.drawable.image, android.R.drawable.ic_dialog_alert));

        //Showing data on the views
        holder.imageView.setImageUrl(superHero.getImageUrl(), imageLoader);
        holder.textViewName.setText(superHero.getName());
        holder.textViewPublisher.setText(superHero.getPublisher());
        holder.textViewUserinfo.setText(superHero.getUserinfo());

//***********************************************************************************************************************************
//event listener of each item in the recycle view//calling of ProviderDetails class
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(context.getApplicationContext(), "Name is "+superHero.getName()+"service is "+superHero.getPublisher()+"hbfgh"+superHero.getImageUrl(), Toast.LENGTH_LONG).show();
                Intent newintent = new Intent();
                newintent.setClass(context,ProviderDetails.class);
                newintent.putExtra("Value1", superHero.getName());
                newintent.putExtra("Value2", superHero.getPublisher());
                newintent.putExtra("Value3", superHero.getImageUrl());
                context.startActivity(newintent);
            }
        });
//**************************************************************************************************************************************
    }
    @Override
    public int getItemCount() {
        return superHeroes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        //Views
        public NetworkImageView imageView;
        public TextView textViewName;
        public TextView textViewPublisher;
        public TextView textViewUserinfo;
        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewPublisher = (TextView) itemView.findViewById(R.id.textViewPublisher);
            textViewUserinfo=(TextView) itemView.findViewById(R.id.textViewUserinfo);
        }
    }
}
