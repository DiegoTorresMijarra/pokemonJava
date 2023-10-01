package org.pokemon;

import org.pokemon.controllers.ProcesadorJson;
import org.pokemon.models.Pokemon;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

public class Main {
    public static void main(String[] args) {

        ProcesadorJson pj=new ProcesadorJson();
        List<Pokemon> res=new ArrayList<>();
        System.out.println("Consulta: Obtener el nombre los 10 primeros pokemons");
        res=pj.forEach(e->e.getId()<=10);
        Stream<String>r=res.stream().map(Pokemon::getName);
        r.forEach(System.out::println);

        System.out.println("Consulta:Obtener el nombre de los 5 últimos pokemons");
        int max=pj.getPokedex().getPokemon().size();
        res=pj.forEach(e->e.getId()>max-5);
        Stream<String>r3=res.stream().map(Pokemon::getName);
        r3.forEach(System.out::println);

        System.out.println("Consulta:Obtener los datos de Pikachu.");
        res=pj.forEach(e->e.getName().equals("Pikachu"));
        res.forEach(System.out::println);

        System.out.println("Consulta:Obtener la evolución de Charmander.");
        res=pj.forEach(e->e.getName().equals("Charmander"));
        System.out.println(res.get(0).getNextEvolution().toString());

        System.out.println("Consulta:Obtener el nombre de los pokemons de tipo fire.");
        res=pj.forEach(e->e.getType().contains("Fire"));
        Stream<String>r4=res.stream().map(Pokemon::getName);
        r4.forEach(System.out::println);

        System.out.println("Consulta:Obtener el nombre de los pokemons con debilidad water o electric.");
        res=pj.forEach(e->e.getWeaknesses().contains("Water")||e.getWeaknesses().contains("Electric"));
        Stream<String>r5=res.stream().map(Pokemon::getName);
        r5.forEach(System.out::println);

        System.out.println("Consulta:Numero de pokemons con solo una debilidad.");
        res=pj.forEach(e->e.getWeaknesses().size()==1);
        System.out.println(res.size());

        System.out.println("Consulta:Pokemon con más debilidades.");
        //Pokemon masDebil=pj.getPokedex().getPokemon().stream().map(e->e.getWeaknesses());

        System.out.println("Consulta:Pokemon con menos evoluciones.");
        Optional<Pokemon> menosEv=pj.getPokedex().getPokemon().stream()
                .filter(pok->pok.getNextEvolution()!=null&&pok.getPrevEvolution()!=null)
                .min(Comparator.comparingInt(e->e.getNextEvolution().size()+e.getPrevEvolution().size()));
        if (menosEv.isPresent())
            System.out.println(menosEv.get().getName());

        System.out.println("Consulta:Pokemon con una evolución que no es de tipo fire.");
        pj.forEach(e->e.getNextEvolution().size()==1)//stream pokemons con una evolucion
                .stream().map(ev->ev.getNextEvolution().get(0).getName())//stream de las evoluciones
                .map(nomb->pj.forEach(pok->pok.getName().equals(nomb)&&!pok.getType().contains("Fire")))//cogemos los nombres y buscamos su tipo en la pokedex
                .forEach(System.out::println);

        System.out.println("Consulta:Pokemon más pesado.");

        System.out.println("Consulta:Pokemon más alto.");

        System.out.println("Consulta:Pokemon con el nombre mas largo.");

        System.out.println("Consulta:Media de peso de los pokemons.");

        System.out.println("Consulta:Media de altura de los pokemons.");

        System.out.println("Consulta:Media de evoluciones de los pokemons.");

        System.out.println("Consulta:Media de debilidades de los pokemons.");

        System.out.println("Consulta:Pokemons agrupados por tipo.");

        System.out.println("Consulta:Numero de pokemons agrupados por debilidad.");

        System.out.println("Consulta:Pokemons agrupados por número de evoluciones.");

        System.out.println("Consulta:Sacar la debilidad más común.");




    }
}