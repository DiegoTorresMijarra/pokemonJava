package org.pokemon.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class PrevEvolutionItem{
	public static String Builder;
	private String num;
	private String name;

	public PrevEvolutionItem(String num, String name) {
		this.num = num;
		this.name = name;
	}

	@Override
	public String toString() {
		return "Evolucion{" +
				"num=" + num +
				", name='" + name + '\'' +
				'}';
	}
}