package com.example.mariaventura.pruebaframe.Activity;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Src.Offer;

import java.util.List;

/**
 * Adaptador del recycler view
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder>
        implements ItemClickListener {

    /**
     * Lista de objetos {@link Offer} que representan la fuente de datos
     * de inflado
     */
    private List<Offer> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;


    public PostAdapter(List<Offer> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PostViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);
        return new PostViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(PostViewHolder viewHolder, int i) {
        viewHolder.name.setText(items.get(i).getName());
        viewHolder.filtro.setText(items.get(i).getTags().get(0));
        viewHolder.fecha.setText(items.get(i).getTimestamp().toString());
        viewHolder.precio.setText(Float.toString( items.get(i).getPrice() ));
    }

    /**
     * Sobrescritura del método de la interfaz {@link ItemClickListener}
     *
     * @param view     item actual
     * @param position posición del item actual
     */
    @Override
    public void onItemClick(View view, int position) {
        DetailActivity.launch(
                (Activity) context, String.valueOf(items.get(position).getId()));
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView name;
        public TextView filtro;
        public TextView fecha;
        public TextView precio;
        public ItemClickListener listener;

        public PostViewHolder(View v, ItemClickListener listener) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
            filtro = (TextView) v.findViewById(R.id.filtro);
            fecha = (TextView) v.findViewById(R.id.fecha);
            precio = (TextView) v.findViewById(R.id.precio);
            this.listener = listener;
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onItemClick(v, getAdapterPosition());
        }
    }
}


interface ItemClickListener {
    void onItemClick(View view, int position);
}


