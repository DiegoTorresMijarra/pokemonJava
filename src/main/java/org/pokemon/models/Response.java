package org.pokemon.models;

import java.util.List;
import lombok.Data;

@Data
public class Response{
	private List<PokemonItem> pokemon;
}