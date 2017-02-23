package com.ambeyindustry.pokedox;

import android.text.TextUtils;
import android.util.Log;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class HttpUtil {

    //creates and return URL object from url string
    private static URL getURL(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException exc) {
            Log.e("HttpUtil: ", exc.toString());
        }
        return url;
    }

    //gets data from server
    private static String getDataFromServer(URL url) throws IOException {
        String responseString = "";
        if (url == null) {
            return responseString;
        }
        HttpURLConnection urlConnection = null;
        InputStream stream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == 200) {
                stream = urlConnection.getInputStream();
                responseString = readFromStream(stream).toString();
            } else {
                Log.e("HttpUtil: ", "response code error: " + urlConnection.getResponseCode());
            }
        } catch (IOException exc) {
            responseString = "no internet";
            Log.e("HttpUtil: ", exc.toString());
        }
        return responseString;
    }

    //read data from network stream
    private static StringBuilder readFromStream(InputStream stream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (stream != null) {
            InputStreamReader streamReader = new InputStreamReader(stream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
            bufferedReader.close();
        }
        return output;
    }

    //extract pokemons data from json string and return list
    private static ArrayList<Pokemon> getPokemonsFromJSON(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        ArrayList<Pokemon> pokemons = new ArrayList<>();
        Pokemon pokemon;
        try {
            JSONObject mainObject = new JSONObject(jsonString);
            JSONArray pokemonArray = mainObject.getJSONArray("pokemon");
            for (int i = 0; i < pokemonArray.length(); i++) {
                JSONObject object = (JSONObject) pokemonArray.getJSONObject(i);
                pokemon = new Pokemon(object.getString("resource_uri"), object.getString("name"));
                pokemons.add(pokemon);
            }
        } catch (JSONException exc) {
            Log.e("HttpUtil: ", exc.toString());
        }
        return pokemons;
    }

    //extract pokemon from json string and return pokemon object
    private static Pokemon getPokemonInfoFromJSON(String jsonString) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        } else if (jsonString.equals("no internet")) {
            return new Pokemon(jsonString, jsonString);
        }
        Pokemon pokemon = null;
        String uri, name;
        int hp, exp, height, weight, attack, defense, speed;
        String[] types, abilities, moves;
        Pokemon[] evolutions = null;
        try {
            JSONObject object = new JSONObject(jsonString);
            uri = object.getString("resource_uri");
            name = object.getString("name");
            hp = object.getInt("hp");
            exp = object.getInt("exp");
            height = object.getInt("height");
            weight = object.getInt("weight");
            attack = object.getInt("attack");
            defense = object.getInt("defense");
            speed = object.getInt("speed");

            JSONArray array = object.getJSONArray("types");
            types = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                String value = ((JSONObject) array.getJSONObject(i)).getString("name");
                types[i] = value;
            }

            array = object.getJSONArray("abilities");
            abilities = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                String value = ((JSONObject) array.getJSONObject(i)).getString("name");
                abilities[i] = value;
            }

            array = object.getJSONArray("moves");
            moves = new String[array.length()];
            for (int i = 0; i < array.length(); i++) {
                String value = ((JSONObject) array.getJSONObject(i)).getString("name");
                moves[i] = value;
            }


            array = object.getJSONArray("evolutions");
            if (array.length() > 0) {
                evolutions = new Pokemon[array.length()];
                for (int i = 0; i < array.length(); i++) {
                    String name1 = ((JSONObject) array.getJSONObject(i)).getString("to");
                    String resource_uri = ((JSONObject) array.getJSONObject(i)).getString("resource_uri");
                    evolutions[i] = new Pokemon(resource_uri, name1);
                }
            }

            pokemon = new Pokemon(uri, name, hp, exp, types, height, weight, attack, defense, speed, moves, abilities, evolutions);
        } catch (JSONException exc) {
            Log.e("HttpUtil: ", exc.toString());
        }
        return pokemon;
    }

    //get all Pokemons
    public static List<Pokemon> getAllPokemons() {
        URL url = getURL("http://pokeapi.co/api/v1/pokedex/1/");
        String jsonString = "";
        try {
            jsonString = getDataFromServer(url);
        } catch (IOException exc) {
            Log.e("HttpUtil: ", exc.toString());
        }
        return getPokemonsFromJSON(jsonString);
    }

    //get Pokemon Info
    public static Pokemon getPokemonInfo(String pokemonName) {
        URL url = getURL("http://pokeapi.co/api/v1/pokemon/" + pokemonName.toLowerCase());
        String jsonString = "";
        try {
            jsonString = getDataFromServer(url);
        } catch (IOException exc) {
            Log.e("HttpUtil: ", exc.toString());
        }
        return getPokemonInfoFromJSON(jsonString);
    }
}
