package org.pokemon.models;

import java.util.List;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
	private List<Object> multipliers;
	private int id;
	private String spawnTime;
	private String height;
	private Object spawnChance;
	private List<PrevEvolutionItem> prevEvolution;
	private int candyCount;
	private List<NextEvolutionItem> nextEvolution;
}