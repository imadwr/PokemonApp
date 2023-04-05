package com.example.pokemonapp.service;

import com.example.pokemonapp.model.PokemonsResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface PokemonServiceAPI {
    @GET("api/v2/pokemon")
    public Call<PokemonsResponse> getPokemons();
}
