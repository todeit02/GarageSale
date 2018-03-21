package com.example.mariaventura.pruebaframe.Activity;
import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mariaventura.pruebaframe.R;
import com.example.mariaventura.pruebaframe.Src.Person;

import java.util.List;


/**
 * Adaptador del recycler view
 */
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.PersonViewHolder>
        implements ItemClickListener {

    /**
     * Lista de objetos {@link Person} que representan la fuente de datos
     * de inflado
     */
    private List<Person> items;

    /*
    Contexto donde actua el recycler view
     */
    private Context context;


    public PersonAdapter(List<Person> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.post_item, viewGroup, false);
        return new PersonViewHolder(v, this);
    }

    @Override
    public void onBindViewHolder(PersonViewHolder viewHolder, int i) {
        viewHolder.titulo.setText(items.get(i).getName());
        viewHolder.prioridad.setText(items.get(i).getEmail());
        viewHolder.fechaLim.setText(items.get(i).getBirthDate());
        viewHolder.categoria.setText(items.get(i).getNationality());
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
                (Activity) context, items.get(position).getUser());
    }


    public static class PersonViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        // Campos respectivos de un item
        public TextView titulo;
        public TextView prioridad;
        public TextView fechaLim;
        public TextView categoria;
        public ItemClickListener listener;

        public PersonViewHolder(View v, ItemClickListener listener) {
            super(v);
            titulo = (TextView) v.findViewById(R.id.titulo);
            prioridad = (TextView) v.findViewById(R.id.prioridad);
            fechaLim = (TextView) v.findViewById(R.id.fecha);
            categoria = (TextView) v.findViewById(R.id.categoria);
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
