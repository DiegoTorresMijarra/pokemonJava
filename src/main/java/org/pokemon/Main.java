package org.pokemon;

import org.pokemon.controllers.ProcesadorJson;
import org.pokemon.models.EvolutionItem;
import org.pokemon.models.Pokemon;

import java.util.*;
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
        Pokemon resul=pj.getPokedex().obtenerStream().filter(p->p.getWeaknesses()!=null)
                .max(Comparator.comparingInt(Pokemon::sumatorioDebilidades))
                .get();
        System.out.println(resul.getName()+" "+resul.getWeaknesses().toString());

        System.out.println("Consulta:Pokemon con menos evoluciones.");
        System.out.println(pj.getPokedex().obtenerStream()
                .min(Comparator.comparingInt(Pokemon::sumatorioEvoluciones)).get().getName());

        System.out.println("Consulta:Pokemon con una evolución que no es de tipo fire.");
        pj.getPokedex().obtenerStream().filter(p->!p.getType().contains("Fire")&&p.getPrevEvolution()!=null&&p.getPrevEvolution().size()==1)
                .forEach(pok->{
                    EvolutionItem ev=new EvolutionItem(pok.getName(), pok.getNum());
                    pj.forEach(p->p.getNextEvolution()!=null&&p.getNextEvolution().contains(ev)).stream().map(Pokemon::getName).forEach(System.out::println);
                });

        System.out.println("Consulta:Pokemon más pesado.");
        System.out.println(pj.getPokedex().obtenerStream().max(Comparator.comparingDouble(Pokemon::getWeight)).get().getName());

        System.out.println("Consulta:Pokemon más alto.");
        System.out.println(pj.getPokedex().obtenerStream().max(Comparator.comparingDouble(Pokemon::getHeight)).get().getName());

        System.out.println("Consulta:Pokemon con el nombre mas largo.");
        System.out.println(pj.getPokedex().obtenerStream().map(Pokemon::getName)
                .max(Comparator.comparingInt(String::length)).get());

        System.out.println("Consulta:Media de peso de los pokemons.");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::getWeight).average().getAsDouble());

        System.out.println("Consulta:Media de altura de los pokemons.");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::getHeight).average().getAsDouble());

        System.out.println("Consulta:Media de evoluciones de los pokemons.");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::sumatorioEvoluciones).average().getAsDouble());

        System.out.println("Consulta:Media de debilidades de los pokemons.");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::sumatorioDebilidades).average().getAsDouble());

        System.out.println("Consulta:Pokemons agrupados por tipo.");
        pj.getPokedex().obtenerStream().map(Pokemon::getType).distinct().forEach(tipo->{
            System.out.println(tipo.toString());
            pj.forEach(pok->pok.getType().equals(tipo))
                    .forEach(p->{
                             System.out.println(p.getName());
            });
        });

        System.out.println("Consulta:Numero de pokemons agrupados por debilidad.");
        pj.getPokedex().obtenerStream().map(Pokemon::getWeaknesses).distinct().forEach(debil->{
            System.out.println(debil.toString());
            pj.forEach(pok->pok.getWeaknesses().equals(debil))
                    .forEach(p->{
                        System.out.println(p.getName());
                    });
        });
        System.out.println("Consulta:Pokemons agrupados por número de evoluciones.");
        pj.getPokedex().obtenerStream().map(Pokemon::sumatorioEvoluciones).distinct().forEach(e->{
            System.out.println(e+" evolucion(es):");
            pj.forEach(pok->pok.sumatorioEvoluciones()==e)
                    .forEach(p->{
                        System.out.println(p.getName());
                    });
        });
        System.out.println("Consulta:Sacar la debilidad más común.");
        String debilMasComun="";
        long count=0;
        List<String> debilidades=new ArrayList<>();
        pj.getPokedex().obtenerStream().map(Pokemon::getWeaknesses).forEach(debilidades::addAll);
        for (String d : debilidades) {
            long e = debilidades.stream().filter(s -> s.equals(d)).count();
            if (count <= e) {
                count = e;
                debilMasComun = d;
            }
        }
        System.out.println("Debilidad mas comun: "+debilMasComun);
    }
}