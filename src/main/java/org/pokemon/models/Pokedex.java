package org.pokemon.models;

import java.util.List;
import lombok.Data;

@Data
public class Pokedex {
	private List<Pokemon> pokemon;
}