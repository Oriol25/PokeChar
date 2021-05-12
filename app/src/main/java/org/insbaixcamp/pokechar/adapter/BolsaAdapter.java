package org.insbaixcamp.pokechar.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import org.insbaixcamp.pokechar.R;
import org.insbaixcamp.pokechar.model.BolsaBasic;
import org.insbaixcamp.pokechar.model.PokedexBasic;

public class BolsaAdapter extends RecyclerView.Adapter<BolsaAdapter.NumberViewHolder> {
    private BolsaBasic[] bolsaBasics;

    final private BolsaAdapter.ListItemClickListener mOnClickListener;

    public BolsaAdapter(BolsaBasic[] bolsaBasics, BolsaAdapter.ListItemClickListener listener){
        this.bolsaBasics = bolsaBasics;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public BolsaAdapter.NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context mContext = parent.getContext();
        int layoutIdForListItem = R.layout.item_bolsa;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false; //Sirve para no poblar rapidamente la vista al padre

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        BolsaAdapter.NumberViewHolder viewHolder = new BolsaAdapter.NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BolsaAdapter.NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return bolsaBasics.length;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nItem;
        ImageButton imagenItem;

        public NumberViewHolder(View itemView) {
            super(itemView);

            nItem = itemView.findViewById(R.id.nItem);
            imagenItem = itemView.findViewById(R.id.imagenItem);

            itemView.setOnClickListener(this);

        }

        void bind(int listIndex){
            String name = bolsaBasics[listIndex].getName().toUpperCase().charAt(0) + bolsaBasics[listIndex].getName().substring(1).toLowerCase();
            nItem.setText(name);
            Picasso.get().load(bolsaBasics[listIndex].getUrlImage()).into(imagenItem);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}
