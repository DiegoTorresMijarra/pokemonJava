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

/**
 * Clase que representa un procesador de json
 * @author Daniel y Diego
 * @version 1.0
 * @see lombok
 * @see Pokemon
 * @see Pokedex
 * @see JsonObject
 */
@Getter
public class ProcesadorJson {
    /**
     * instancia del procesador
     */
    private static ProcesadorJson instancia;
    /**
     * pokedex donde se almacenan los datos
     */
    private Pokedex pokedex;
    /**
     * path a la carpeta foco del procesador
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    /**
     * nombre del archivo json a procesar
     */
    private static final String jsonFile="pokemon.json";

    /**
     * devuelve la instancia del procesador, si es null la genera
     * @return ProcesadorJson
     */
    public static ProcesadorJson getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorJson();
        }
        return instancia;
    }

    /**
     * Constructor de un Procesador de Json, llama a cargarPokedex()
     */
    public ProcesadorJson(){
        pokedex=new Pokedex();
        cargarPokedex();
    }

    /**
     * Procesa las lineas de fichero y vierte los datos obtenidos por el jsonObjet al constructor de Pokemons, lo haria con lombock
     * @see Pokedex
     * @see Pokemon
     * @see Gson
     * @see JsonObject
     */
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

    /**
     * Obtiene una Lista'\<'Pokemon> de la pokedex, una vez se le ha aplicado una funcion. <br>
     * no es el metodo mas util, ya que existen otras herramientas, pero ahorra algunos pasos
     * @param fun Function'\<'Pokemon, Boolean>
     * @return List '\<'Pokemon>
     */
    public List <Pokemon> forEach(Function<Pokemon, Boolean> fun){
        return new ArrayList<>(getPokedex().getPokemon().stream().filter(fun::apply).toList());
    }
    /**
     *obtiene la pokedex del procesador, el lombock no me va bien
     * @return Pokedex
     */
    public Pokedex getPokedex() {
        return pokedex;
    }
}
