package com.example.pokemonapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class PokemonsResponse {
    public int count;
    @SerializedName("results")
    public List<Pokemon> pokemons = new ArrayDeque<>();
}
