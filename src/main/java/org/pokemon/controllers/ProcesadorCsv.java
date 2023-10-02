package org.pokemon.controllers;

import lombok.Getter;
import lombok.Setter;
import org.pokemon.models.Pokedex;
import org.pokemon.models.Pokemon;

import java.io.*;
import java.nio.file.Paths;

@Getter
@Setter
public class ProcesadorCsv {
    private static ProcesadorCsv instancia;
    private Pokedex pokedex;
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    private static final String csvFile="pokemon.csv";

    public static ProcesadorCsv getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorCsv();
        }
        return instancia;
    }

    ProcesadorCsv (){
        pokedex=ProcesadorJson.getInstancia().getPokedex();
        exportarCsv();
    }
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

    public Pokedex getPokedex() {
        return pokedex;
    }
}
