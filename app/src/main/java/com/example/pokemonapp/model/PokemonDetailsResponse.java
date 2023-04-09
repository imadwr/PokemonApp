package com.example.pokemonapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import kotlin.collections.ArrayDeque;

public class PokemonDetailsResponse {
    public int weight;
    public int height;
    @SerializedName("types")
    public List<PokemonType> pokemonTypes = new ArrayDeque<>();
    @SerializedName("stats")
    public List<PokemonStats> pokemonStats = new ArrayDeque<>();
}
