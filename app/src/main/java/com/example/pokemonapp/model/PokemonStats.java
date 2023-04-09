package com.example.pokemonapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Dictionary;

public class PokemonStats {
    @SerializedName("base_stat")
    public int baseStat;
    @SerializedName("stat")
    public PokemonStatDetail stat;
}
