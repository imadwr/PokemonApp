package com.example.pokemonapp.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.pokemonapp.R;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.BitSet;
import java.util.List;

public class PokemonsListViewModel extends ArrayAdapter <Pokemon> {
    private int resource;
    public PokemonsListViewModel(@NonNull Context context, int resource, List<Pokemon> data) {
        super(context, resource, data);
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listViewItem = convertView;
        if(listViewItem == null){
            listViewItem = LayoutInflater.from(getContext()).inflate(resource, parent, false);
        }
        ImageView imageViewPokemon = listViewItem.findViewById(R.id.imageViewPokemon);
        TextView textViewName = listViewItem.findViewById(R.id.textViewName);
        textViewName.setText(getItem(position).name);

        try {
            String imgUrlString = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + (position+1) +".png";
            URL imgUrl = new URL(imgUrlString);
            Bitmap bitmap = BitmapFactory.decodeStream(imgUrl.openStream());
            imageViewPokemon.setImageBitmap(bitmap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return listViewItem;
    }
}
