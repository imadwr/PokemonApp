package com.example.pokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonsResponse;
import com.example.pokemonapp.service.PokemonServiceAPI;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    List<String> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ListView listViewPokemons = findViewById(R.id.listviewPokemons);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        listViewPokemons.setAdapter(arrayAdapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        PokemonServiceAPI pokemonServiceAPI = retrofit.create(PokemonServiceAPI.class);
        Call<PokemonsResponse> callPokemons = pokemonServiceAPI.getPokemons();
        callPokemons.enqueue(new Callback<PokemonsResponse>() {
            @Override
            public void onResponse(Call<PokemonsResponse> call, Response<PokemonsResponse> response) {
                Log.i("info", call.request().url().toString());
                if(!response.isSuccessful()){
                    Log.i("info", String.valueOf(response.code()));
                    return;
                }

                PokemonsResponse pokemonsResponse = response.body();
                for(Pokemon pokemon : pokemonsResponse.pokemons){
                    data.add(pokemon.name);
                }
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PokemonsResponse> call, Throwable t) {
                Log.e("error", "Error");
            }
        });
    }
}