package org.pokemon.models;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;


/**
 * Clase que representa un elemento Pokemons
 * @author Daniel y Diego
 * @version 1.0
 * @see lombok
 * @see JsonObject
 */
@Data
public class Pokemon {
	/**
	 * String con la url a la img del pok
	 */
	private String img;
	/**
	 * km en eclosionar el huevo del pokemon <br>
	 * en km; -1= "Not in Eggs"
	 */
	private double egg;
	/**
	 * string con el nombre del caramelo asociado al pok
	 */
	private String candy;
	/**
	 * String del id del Pok, podria ser candidato a PrimaryKey o UUID
	 */
	private String num;
	/**
	 * double del peso del pokemon en Kg
	 */
	private double weight;
	/**
	 * Lista de los tipos del pokemon
	 */
	private List<String> type;
	/**
	 * lista de las debilidades del pokemon
	 */
	private List<String> weaknesses;
	/**
	 * nombre del pok
	 */
	private String name;
	/**
	 * media de aparicion de este
	 */
	private double avgSpawns;
	/**
	 * no tengo muy claro que es, pero hace referencia al ratio de captura del pokemon de alguna forma <br>
	 * si .get(0)=-1 significa q es null
	 */
	private List<Double> multipliers;
	/**
	 * Primary Key Pok, id q lo representa
	 */
	private int id;//podria ser UUID
	/**
	 * Date a la q aparece el poke? <br>
	 * intento parsearla pero me da errores pero me da bastantes errores
	 */
	private String spawnTime;
	/**
	 * altura en m del pokemon
	 */
	private double height;
	/**
	 * probabilidad de aparicion de este
	 */
	private double spawnChance;
	/**
	 * List'\<'EvolutionItem> con las evoluciones previas de este
	 */
	private List<EvolutionItem> prevEvolution;
	/**
	 * numero de caramelos <br>
	 * no aparece=-1
	 */
	private int candyCount;
	/**
	 * List'\<'EvolutionItem> con las evoluciones futuras de este
	 */
	private List<EvolutionItem> nextEvolution;

	/**
	 * Constructor a traves de un JsonObject, obtiene uno a uno y va procesando o llamando a procesadores
	 * @param jo JsonObject
	 * @see JsonObject
	 */
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

	/**
	 * procesa el JsonElement para obtener un int
	 * @param jo
	 * @return int
	 * @see JsonElement
	 */
	private int procesarCandyCount(JsonElement jo){
		if(jo==null)
			return -1;
		else
			return jo.getAsInt();
	}

	/**
	 * Procesa los posibles multiplicadores del Pok
	 * @param jo Valor del atributo
	 * @return parseado a List'\<'Double>
	 */
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
	/**
	 * procesa el JsonElement para obtener un double
	 * @param jo
	 * @return double
	 * @see JsonElement
	 */
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
	/**
	 * procesa el JsonElement para obtener un List'\<'EvolutionItem>
	 * @param jo JsonElement
	 * @return List'\<'EvolutionItem>
	 * @see JsonElement
	 */
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

	/**
	 * Procesa los tipos y debilidades del pok para obtener un List'\<'EvolutionItem>
	 * @param jo JsonElement
	 * @return List'\<'EvolutionItem>
	 * @see JsonElement
	 */
	private List<String> procesarTypeWeak(JsonElement jo){
		return Arrays.stream(jo.toString()
				.replace((char)91,(char)32).replace((char)93,(char)32).replace((char)34,(char)32)
				.strip().split(" , ")).toList();
	}

	/**
	 * sumatorio de las debilidades de un pokemon, es para cribar nulos.size()
	 * @return int
	 */
	public int sumatorioDebilidades(){
		int sum=0;
		try {
			sum+=this.getWeaknesses().size();
		}catch (NullPointerException ignored){}
		return sum;
	}

	/**
	 * sumatorio de las evoluciones de un pokemon, es para cribar nulos.size()
	 * @return int
	 */
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

	/**
	 * metodo que devuelve un string de los atributos del pokemon
	 * @return String
	 */
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

	public Pokemon() {
	}
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

	public void setNum(String num) {
		this.num = num;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setHeight(double height) {
		this.height = height;
	}
}