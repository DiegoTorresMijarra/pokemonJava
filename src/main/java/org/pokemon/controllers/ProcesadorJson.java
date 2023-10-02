package org.pokemon.controllers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;
import org.pokemon.models.Pokedex;
import org.pokemon.models.Pokemon;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Getter
public class ProcesadorJson {
    private static ProcesadorJson instancia;
    private Pokedex pokedex;
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    private static final String jsonFile="pokemon.json";
    public static ProcesadorJson getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorJson();
        }
        return instancia;
    }

    public Pokedex getPokedex() {
        return pokedex;
    }

    public ProcesadorJson(){
        pokedex=new Pokedex();
        cargarPokedex();
    }

    private void cargarPokedex() {
        String archivoJson=dataPath+File.separator+jsonFile;
        Gson gson =new Gson();

        try (FileReader reader = new FileReader(archivoJson)) {
            Type streamPoke=new TypeToken<List<JsonObject>>(){}.getType();
            List<JsonObject> e=gson.fromJson(reader,streamPoke);
            e.forEach(item->pokedex.getPokemon().add(new Pokemon(item)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List <Pokemon> forEach(Function<Pokemon, Boolean> fun){
        return new ArrayList<>(getPokedex().getPokemon().stream().filter(fun::apply).toList());
    }
}
