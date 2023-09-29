package org.pokemon.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
public class Pokemon {
	private String img;
	private String egg;
	private String candy;
	private String num;
	private String weight;
	private List<String> type;
	private List<String> weaknesses;
	private String name;
	private int avgSpawns;
	private List<String> multipliers;
	private int id;
	private String spawnTime;
	private String height;
	private String spawnChance;
	@Builder.ObtainVia(method = "")
	private List<PrevEvolutionItem> prevEvolution;
	private int candyCount;
	private List<NextEvolutionItem> nextEvolution;

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Pokemon{" +
				"img='" + img + '\'' +
				", egg='" + egg + '\'' +
				", candy='" + candy + '\'' +
				", num='" + num + '\'' +
				", weight='" + weight + '\'' +
				", type=" + type +
				", weaknesses=" + weaknesses +
				", name='" + name + '\'' +
				", avgSpawns=" + avgSpawns +
				", multipliers=" + multipliers +
				", id=" + id +
				", spawnTime='" + spawnTime + '\'' +
				", height='" + height + '\'' +
				", spawnChance=" + spawnChance +
				", prevEvolution=" + prevEvolution +
				", candyCount=" + candyCount +
				", nextEvolution=" + nextEvolution +
				'}';
	}
}