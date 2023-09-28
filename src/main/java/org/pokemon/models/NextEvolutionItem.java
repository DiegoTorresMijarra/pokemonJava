package org.pokemon.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NextEvolutionItem {
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