package org.pokemon.controllers;

import com.google.gson.Gson;
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
    private static final String dataPath= Paths.get("").toAbsolutePath().toString()+ File.separator + "data";
    private static final String jsonFile="pokemon1.json";
    public static ProcesadorJson getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorJson();
        }
        return instancia;
    }

    ProcesadorJson(){
        pokedex=new Pokedex();
        cargarPokedex();
    }

    private void cargarPokedex() {
        String file=dataPath+File.separator+jsonFile;
        Gson gson =new Gson();

        try (FileReader reader = new FileReader(file)) {
            // Deserializar el JSON a una lista de objetos Pokemon, decimos cómo es el tipo de lista
            Type streamPokemon = new TypeToken<List<Pokemon>>(){}.getType();
            // Leemos el JSON y lo convertimos a una lista de objetos Pokemon una vez definido el tipo
            pokedex = gson.fromJson(reader, streamPokemon);
        } catch (IOException e) {
            System.out.println("aa");
            e.printStackTrace();
        }
    }
/*
    List <Pokemon> forEach(Function<Pokemon,Boolean> fun){
        List<Pokemon> result=new ArrayList<>();
        getPokedex().getPokemon().stream().forEach(e->{
            if (fun.apply(e))
                result.add(e);
        });
        return result;
    }
*/
    public static void main(String[] args) {
        ProcesadorJson pj=new ProcesadorJson();
        //System.out.println(pj.getPokedex().getPokemon().get(1).toString());
        //List<Pokemon> res=pj.forEach(e->e.getName().equals("Pikachu"));
        //res.forEach(e->System.out.println(e.toString()));
    }
}
