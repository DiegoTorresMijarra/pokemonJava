package org.pokemon.models;

import lombok.Data;

@Data
public class PrevEvolutionItem{
	private String num;
	private String name;
	@Override
	public String toString() {
		return "Evolucion{" +
				"num=" + num +
				", name='" + name + '\'' +
				'}';
	}
}