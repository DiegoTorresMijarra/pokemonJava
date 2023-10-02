package org.pokemon.models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.google.gson.JsonElement;
import lombok.Data;
import lombok.Getter;

/**
 * Clase que representa una lista de Pokemons, es decir un contenedor de estos
 * @author Daniel y Diego
 * @version 1.0
 * @see lombok
 */
@Data
@Getter
public class Pokedex {
	/**
	 * lista donde se guardan los pokemons, tb podria heredar de List'\<'Pokemon>
	 */
	private List<Pokemon> pokemon=new ArrayList<>();

	/**
	 * devuelve la pokedex en forma de Stream'\<'Pokemon>
	 * @return Stream'\<'Pokemon>
	 */
	public Stream<Pokemon> obtenerStream(){
		return this.pokemon.stream();
	}

	/**
	 * lombock...
	 * @return
	 */
	public List<Pokemon> getPokemon() {
		return pokemon;
	}
}