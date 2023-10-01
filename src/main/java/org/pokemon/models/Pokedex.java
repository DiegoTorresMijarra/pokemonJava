package org.pokemon.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Pokedex {
	private List<Pokemon> pokemon=new ArrayList<>();
	public List<Pokemon> getPokemon() {
		return pokemon;
	}
	public Stream<Pokemon> obtenerStream(){
		return this.pokemon.stream();
	}
	public List<Integer> buscarEvoluciones(EvolutionItem ev){
		List<Integer> res=new ArrayList<>();
		for(int i=0;i<pokemon.size();i++){
			Pokemon p=pokemon.get(i);
			if (p.getPrevEvolution().contains(ev)||p.getNextEvolution().contains(ev))
				res.add(i);
		}
		return res;
	}
}