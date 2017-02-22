package com.ambeyindustry.pokedox;

import android.content.AsyncTaskLoader;
import android.content.Context;

public class PokemonLoader extends AsyncTaskLoader<Pokemon> {

    private String pokemonName;

    public PokemonLoader(Context context, String name) {
        super(context);
        pokemonName = name;
    }

    @Override
    public void onStartLoading() {
        forceLoad();
    }

    @Override
    public Pokemon loadInBackground() {
        if (pokemonName == null)
            return null;
        return HttpUtil.getPokemonInfo(pokemonName);
    }
}
