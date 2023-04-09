package com.example.pokemonapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonDetailsResponse;
import com.example.pokemonapp.model.PokemonStats;
import com.example.pokemonapp.model.PokemonType;
import com.example.pokemonapp.model.PokemonsResponse;
import com.example.pokemonapp.service.PokemonServiceAPI;

import java.net.URL;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PokemonDetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pokemon_details_layout);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        String pokemonName = intent.getStringExtra(MainActivity.POKEMON_NAME_PARAM);
        int id = intent.getIntExtra(MainActivity.POKEMON_ID, -1);
        TextView textViewNamePokemon = findViewById(R.id.textViewNamePokemon);
        textViewNamePokemon.setText(pokemonName);


        ImageView imageViewPokemon = findViewById(R.id.imageViewPokemon);
        try {
            String imgUrlString = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/" + id +".png";
            URL imgUrl = new URL(imgUrlString);
            Bitmap bitmap = BitmapFactory.decodeStream(imgUrl.openStream());
            imageViewPokemon.setImageBitmap(bitmap);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        PokemonServiceAPI pokemonServiceAPI = retrofit.create(PokemonServiceAPI.class);

        Call<PokemonDetailsResponse> pokeDetailsCall = pokemonServiceAPI.getPokemonDetails(pokemonName);

        pokeDetailsCall.enqueue(new Callback<PokemonDetailsResponse>() {
            @Override
            public void onResponse(Call<PokemonDetailsResponse> call, Response<PokemonDetailsResponse> response) {
                Log.i("info", call.request().url().toString());
                if(!response.isSuccessful()){
                    Log.e("info", String.valueOf(response.code()));
                    Log.i("There is an error", "error");
                    return;
                }

                PokemonDetailsResponse pokemonDetailsResponse = response.body();
                Log.i("info", String.valueOf(pokemonDetailsResponse.weight));
                TextView textViewWeight = findViewById(R.id.textViewWeight);
                textViewWeight.setText("Weight: " + String.valueOf(pokemonDetailsResponse.weight) + " Kg"); // for weight
                TextView textViewHeight = findViewById(R.id.textViewHeight);
                textViewHeight.setText("Height: " + String.valueOf(pokemonDetailsResponse.height) + " M"); // for height

                String typesSting = "Types: ";
                for (PokemonType pt : pokemonDetailsResponse.pokemonTypes){
                    Log.i("info", pt.type.name);
                    typesSting += " " + pt.type.name + "  ";
                }

                TextView textViewTypes = findViewById(R.id.textViewTypes);
                textViewTypes.setText(typesSting);

                String statsString = "";
                for (PokemonStats ps : pokemonDetailsResponse.pokemonStats){
                    statsString += ps.stat.name + ": " + ps.baseStat + "\n";
                }

                TextView textViewPokemonStats = findViewById(R.id.textViewStatsInfo);
                textViewPokemonStats.setText(statsString);


                // Problem with the request to fix later
            }

            @Override
            public void onFailure(Call<PokemonDetailsResponse> call, Throwable t) {

            }
        });
    }
}
