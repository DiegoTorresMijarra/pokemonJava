package org.pokemon.models;

import java.util.ArrayList;
import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Pokedex {
	private List<Pokemon> pokemon=new ArrayList<>();
	public List<Pokemon> getPokemon() {
		return pokemon;
	}
}