package com.example.pokemonapp.model;

import com.google.gson.annotations.SerializedName;

import java.util.Dictionary;

public class PokemonType {
    public int slot;
    @SerializedName("type")
    public PokemonTypeDetail type;
}
