package com.ambeyindustry.pokedox;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

public class PokemonListLoader extends AsyncTaskLoader<List<Pokemon>> {

    public PokemonListLoader(Context context) {
        super(context);
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<Pokemon> loadInBackground() {
        return HttpUtil.getAllPokemons();
    }
}
