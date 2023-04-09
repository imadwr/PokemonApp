package com.example.pokemonapp.service;

import com.example.pokemonapp.model.PokemonDetailsResponse;
import com.example.pokemonapp.model.PokemonsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface PokemonServiceAPI {
    @GET("api/v2/pokemon")
    public Call<PokemonsResponse> getPokemons();

    @GET("api/v2/pokemon/{pokemonName}")
    public Call<PokemonDetailsResponse> getPokemonDetails(@Path("pokemonName") String name);
}
