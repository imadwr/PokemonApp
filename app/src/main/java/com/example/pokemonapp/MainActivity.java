package com.example.pokemonapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.pokemonapp.model.Pokemon;
import com.example.pokemonapp.model.PokemonsListViewModel;
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

    List<Pokemon> data = new ArrayList<>();
    public static final String POKEMON_NAME_PARAM = "pokemon.name";
    public static final String POKEMON_ID = "pokemon.id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);


        ListView listViewPokemons = findViewById(R.id.listviewPokemons);

        //ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, data);
        PokemonsListViewModel listViewModel = new PokemonsListViewModel(this, R.layout.pokemons_list_view_layout, data);
        listViewPokemons.setAdapter(listViewModel);

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
                    data.add(pokemon);
                }
                listViewModel.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<PokemonsResponse> call, Throwable t) {
                Log.e("error", "Error");
            }
        });

        listViewPokemons.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String pokemonName = data.get(i).name;
                Log.i("info", pokemonName);
                Intent intent = new Intent(getApplicationContext(), PokemonDetailsActivity.class);
                intent.putExtra(POKEMON_NAME_PARAM, pokemonName);
                intent.putExtra(POKEMON_ID, i+1);
                startActivity(intent);
            }
        });
    }
}