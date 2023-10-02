package org.pokemon.controllers;

import lombok.Getter;
import lombok.Setter;
import org.pokemon.models.Pokedex;
import org.pokemon.models.Pokemon;

import java.io.*;
import java.nio.file.Paths;

/**
 * Clase que representa un procesador de csv a partir de un ProcesadorJson
 * @author Daniel y Diego
 * @version 1.0
 * @see lombok
 * @see ProcesadorJson
 */
@Getter
@Setter

public class ProcesadorCsv {
    /**
     * instancia Procesador
     */
    private static ProcesadorCsv instancia;
    /**
     * Pokedex del Procesador
     */
    private Pokedex pokedex;
    /**
     * path a la carpeta foco del procesador
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    /**
     * nombre del archivo csv creado
     */
    private static final String csvFile="pokemon.csv";

    /**
     * devuelve la instancia del procesador, si esta es null la inicia
     * @return ProcesadorCsv
     */
    public static ProcesadorCsv getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorCsv();
        }
        return instancia;
    }

    /**
     * constructor de procesador <br>
     * obtendra la Pokedex de la instancia del ProcesadorJson, si esta fueran null llamaria a calcularla<br>
     * llama a exportarCsv(), para generar el doc
     * @see ProcesadorJson
     */
    ProcesadorCsv (){
        pokedex=ProcesadorJson.getInstancia().getPokedex();
        exportarCsv();
    }

    /**
     * Exporta los datos de la pokedex a un nuevo archivo csv, pero solo guarda los valores "id, num, name, height, width" de los Pokemons
     */
    private void exportarCsv(){
        String archivoCsv=dataPath+File.separator+csvFile;
        File f=new File(archivoCsv);
        if (f.exists())
            f.delete();
        try(FileWriter fr=new FileWriter(f))  {
            fr.write("id, num, name, height, width \n");
            for(Pokemon p:pokedex.getPokemon()){
                fr.write(p.getId()+","+p.getNum()+","+p.getName()+","+p.getHeight()+","+p.getWeight()+'\n');
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Archivo creado correctamente");
    }

    /**
     * Lee el csv y lo muestra por pantalla
     */
    public void mostrarCsvPantalla(){
        String archivoCsv=dataPath+File.separator+csvFile;
        File f=new File(archivoCsv);
        if (f.exists())
            System.out.println("Leyendo el archivo csv");
        System.out.println("-----------------------");
        try(BufferedReader fr= new BufferedReader(new FileReader(f))){
            while(fr.ready()){
                System.out.println(fr.readLine());
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("-----------------------");
    }

    /**
     * getter pokedex, no me funciona bien el lombock
     * @return Pokedex
     * @see Pokedex
     */
    public Pokedex getPokedex() {
        return pokedex;
    }
}
