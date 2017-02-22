package com.ambeyindustry.pokedox;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class PokemonAdapter extends ArrayAdapter<Pokemon> {
    public PokemonAdapter(Context context, List<Pokemon> pokemons) {
        super(context, 0, pokemons);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.activity_list_item, parent, false);
        }
        Pokemon pokemon = getItem(position);

        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.name);
        magnitudeTextView.setText(pokemon.getName());

        ImageView imageView = (ImageView) listItemView.findViewById(R.id.img_small);
        Picasso.with(getContext()).load(pokemon.getImageURI()).into(imageView);

        return listItemView;
    }
}
