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
            System.out.println(f.delete());
        try(FileWriter fr=new FileWriter(f))  {
            BufferedWriter wr=new BufferedWriter(fr);
            wr.write("id, num, name, height, width");
            wr.newLine();
            for(Pokemon p:pokedex.getPokemon()){
                wr.write(p.getId()+","+p.getNum()+","+p.getHeight()+","+p.getWeight());
                wr.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
