package org.pokemon.models;

import java.util.List;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
public class Pokedex {
	private List<Pokemon> pokemon;

	public List<Pokemon> getPokemon() {
		return pokemon;
	}

	public void setPokemon(List<Pokemon> pokemon) {
		this.pokemon = pokemon;
	}
}