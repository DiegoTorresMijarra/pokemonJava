package org.pokemon.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class Pokemon {
	private String img;
	private double egg;//en km -1= "Not in Eggs"
	private String candy;
	private String num;
	private double weight;//en kg
	private List<String> type;
	private List<String> weaknesses;
	private String name;
	private double avgSpawns;
	private List<Double> multipliers;//si .get(0)=-1 significa q es null
	private int id;//podria ser UUID
	private String spawnTime;//intento parsearla pero me da errores
	private double height;//en m
	private double spawnChance;
	private List<EvolutionItem> prevEvolution;
	private int candyCount;//no aparece=-1
	private List<EvolutionItem> nextEvolution;

	public Pokemon(JsonObject jo) {
		id=jo.get("id").getAsInt();
		img=jo.get("img").toString();
		egg=procesarEgg(jo.get("egg").toString());
		candy=jo.get("candy").toString();
		num=jo.get("num").toString().replace((char)34,(char)32).trim();
		weight=Double.parseDouble(jo.get("weight").toString()
				.replaceAll("kg","").replace((char)34,(char)32));
		height=Double.parseDouble(jo.get("height").toString()
				.replaceAll("m","").replace((char)34,(char)32));
		type= procesarTypeWeak(jo.get("type"));
		weaknesses=procesarTypeWeak(jo.get("weaknesses"));
		name=jo.get("name").toString().replace((char)34,(char)32).strip();
		avgSpawns=jo.get("avg_spawns").getAsDouble();
		spawnChance=jo.get("spawn_chance").getAsDouble();
		multipliers= procesarMultipliers(jo.get("multipliers").toString());
		spawnTime=jo.get("spawn_time").toString();
		candyCount=procesarCandyCount(jo.get("candy_count"));
		nextEvolution=procesarEvol(jo.get("next_evolution"));
		prevEvolution=procesarEvol(jo.get("prev_evolution"));
	}
	private int procesarCandyCount(JsonElement jo){
		if(jo==null)
			return -1;
		else
			return jo.getAsInt();
	}
	private List<Double>procesarMultipliers(String jo){
		ArrayList<Double> res=new ArrayList<>();
		if(jo.compareTo("null")==0){
			double e=-1;
			res.add(e);
		}
		else {
			String[] arr=jo.replace((char)91,(char)32).replace((char)93,(char)32).split(",");
			for (String s:arr){
				res.add(Double.parseDouble(s));
			}
		}
		return res;
	}
	private double procesarEgg(String jo){
		try { //le he hecho un try catch, porq en el json esta un "Omanyte Candy" en la propiedad egg
			jo=jo.replaceAll("Not in Eggs","")
					.replace((char)34,(char)32)
					.replaceAll("km","")
					.strip();
			return Double.parseDouble(jo);
		}catch (NumberFormatException e){
			return -1;
		}
	}
	private List<EvolutionItem> procesarEvol(JsonElement jo){
		if (jo==null)
			return null;
		List<EvolutionItem> nx=new ArrayList<>();
		if(!jo.isJsonArray()){
			nx.add(new EvolutionItem(jo));
		}
		else {
			for (JsonElement o:jo.getAsJsonArray()){
				nx.add(new EvolutionItem(o.getAsJsonObject()));
			}
		}
		return nx;
	}
	private List<String> procesarTypeWeak(JsonElement jo){
		return Arrays.stream(jo.toString()
				.replace((char)91,(char)32).replace((char)93,(char)32).replace((char)34,(char)32)
				.strip().split(" , ")).toList();
	}
	public int sumatorioDebilidades(){
		int sum=0;
		try {
			sum+=this.getWeaknesses().size();
		}catch (NullPointerException ignored){}
		return sum;
	}
	public int sumatorioEvoluciones(){
		int sum=0;
		try {
			sum+=this.getPrevEvolution().size();
		}catch (NullPointerException ignored){}
		try{
			sum+=this.getNextEvolution().size();
		}catch (NullPointerException ignored){}
		return sum;
	}
	@Override
	public String toString() {
		String nxEvol="";
		String anEvol="";
		if(nextEvolution==null)
			nxEvol="No tiene mas evoluciones";
		else{
			for(EvolutionItem e: nextEvolution)
				nxEvol=nxEvol.concat(" "+e.toString());
		}
		if (prevEvolution==null)
			anEvol="No evoluciona de nadie";
		else {
			for(EvolutionItem e: prevEvolution)
				anEvol=anEvol.concat(" "+e.toString());
		}

		return "Pokemon{" +
				"name='" + name + '\'' +
				", egg=" + egg +
				", candy='" + candy + '\'' +
				", num='" + num + '\'' +
				", weight=" + weight +
				", type=" + type +
				", weaknesses=" + weaknesses +
				", img=" + img +  '\'' +
				", avgSpawns=" + avgSpawns +
				", multipliers=" + multipliers +
				", id=" + id +
				", spawnTime='" + spawnTime + '\'' +
				", height=" + height +
				", spawnChance=" + spawnChance +
				", prevEvolution=" + anEvol +
				", candyCount=" + candyCount +
				", nextEvolution=" + nxEvol +
				'}';
	}
	//me vuelve a obligaar a crear los getter...

	public double getWeight() {
		return weight;
	}

	public String getNum() {
		return num;
	}

	public String getName() {
		return name;
	}

	public int getId() {
		return id;
	}

	public double getHeight() {
		return height;
	}

	public String getImg() {
		return img;
	}

	public double getEgg() {
		return egg;
	}

	public String getCandy() {
		return candy;
	}

	public List<String> getType() {
		return type;
	}

	public List<String> getWeaknesses() {
		return weaknesses;
	}

	public double getAvgSpawns() {
		return avgSpawns;
	}

	public List<Double> getMultipliers() {
		return multipliers;
	}

	public String getSpawnTime() {
		return spawnTime;
	}

	public double getSpawnChance() {
		return spawnChance;
	}

	public List<EvolutionItem> getPrevEvolution() {
		return prevEvolution;
	}

	public int getCandyCount() {
		return candyCount;
	}

	public List<EvolutionItem> getNextEvolution() {
		return nextEvolution;
	}
}