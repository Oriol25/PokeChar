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
import org.insbaixcamp.pokechar.model.PokedexBasic;

public class PokedexAdapter extends RecyclerView.Adapter<PokedexAdapter.NumberViewHolder> {
    private PokedexBasic[] pokedexBasics;

    final private ListItemClickListener mOnClickListener;

    private static String TAG = "PokeChar/PokedexAdapter";

    public PokedexAdapter(PokedexBasic[] pokedexBasics, ListItemClickListener listener){
        this.pokedexBasics = pokedexBasics;
        mOnClickListener = listener;
    }

    public interface ListItemClickListener{
        void onListItemClick(int clickedItemIndex);
    }

    @NonNull
    @Override
    public NumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context mContext = parent.getContext();
        int layoutIdForListItem = R.layout.item_pokedex;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false; //Sirve para no poblar rapidamente la vista al padre

        View view = inflater.inflate(layoutIdForListItem,parent,shouldAttachToParentImmediately);
        NumberViewHolder viewHolder = new NumberViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull NumberViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return pokedexBasics.length;
    }

    class NumberViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName;
        TextView tvId;
        ImageButton ibFoto;

        public NumberViewHolder(View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvNamePokedex);
            tvId = itemView.findViewById(R.id.tvIdPokedex);
            ibFoto = itemView.findViewById(R.id.ibFotoPokedex);

            tvName.setOnClickListener(this);
            tvId.setOnClickListener(this);
            ibFoto.setOnClickListener(this);

        }

        void bind(int listIndex){

            String str = pokedexBasics[listIndex].getName();

            str = str.toUpperCase().charAt(0) + str.substring(1).toLowerCase();

            //boolean fin = false;
            //int i = 0;
            String name = "";

//            while (!fin) {
//
//                if (!str.equals("Jangmo-o") && !str.equals("Hakamo-o") && !str.equals("Kommo-o")) {
//                    if (str.charAt(i) != '-' && str.length() != (i)) {
//                        name = name + str.charAt(i);
//                        Log.i(TAG, name + " " + str.charAt(i));
//                    } else {
//                        fin = true;
//                    }
//                } else {
//                    fin = true;
//                    name = str;
//                }
//
//                i++;
//
//            }

            tvName.setText(str);
            tvId.setText(pokedexBasics[listIndex].getId() + " - ");
            Picasso.get().load(pokedexBasics[listIndex].getUrlImage()).into(ibFoto);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);
        }
    }
}