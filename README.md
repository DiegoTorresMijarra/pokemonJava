# pokemonJava

### Miembros del equipo

[Daniel Garrido Muros](https://github.com/Danniellgm03)

[Diego Torres Mijarra](https://github.com/DiegoTorresMijarra)

## Modelos

### Clase pojo Pokemon:

Implementacion clase pojo Pokemon con Lombok

``` java
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
   ```

   ### Clase Pojo EvolucionItem

   Clase que representa un item de evolucion de pokemon, sirve para evoluciones previas y futuras

``` java
@Data
@Builder
public class EvolutionItem{
    /**
     * numero en String de la evolucion, podria generarse en UUID
     */
    private String num;
    /**
     * Nombre de la evolucion
     */
    private String name;

    /**
     * Constructor, a traves de un JSonElement, para ello obtiene los valores y procesa sus resultados
     * @param jo JsonElement
     */
    public EvolutionItem(JsonElement jo) {
        JsonObject obj=jo.getAsJsonObject();
        this.num = obj.get("num").toString().replace((char)34,(char)32).trim();
        this.name =obj.get("name").toString().replace((char)34,(char)32).trim();
    }

    /**
     * metodo que devuelve un String con los valores de los atributos de la evolucion
     * @return String
     */
    @Override
    public String toString() {
        return "{name='" + name + '\'' +
                ",num=" + num + '\''+
                '}';
    }

    /**
     * metodo que devuelve si dos evoluciones son iguales comparando sus atributos uno a uno
     * @param ev evolucion a comparar
     * @return boolean
     * @see Object
     */
    @Override
    public boolean equals(Object ev){
        if(ev.getClass()==EvolutionItem.class)
            return this.name.compareTo(((EvolutionItem)ev).getName())==0&&this.num.compareTo(((EvolutionItem)ev).getNum())==0;
        else
            return false;
    }
    ```

    ### Clase Pokedex
    almacena una List<Pokemon>

```java
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
```

## Controllers
En este paquete se encontraran los procesadores de Json y Csv, para obtener Pokedex y exportarlas.

### Procesador Json
``` java
@Getter
public class ProcesadorJson {
    /**
     * instancia del procesador
     */
    private static ProcesadorJson instancia;
    /**
     * pokedex donde se almacenan los datos
     */
    private Pokedex pokedex;
    /**
     * path a la carpeta foco del procesador
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    /**
     * nombre del archivo json a procesar
     */
    private static final String jsonFile="pokemon.json";

    /**
     * devuelve la instancia del procesador, si es null la genera
     * @return ProcesadorJson
     */
    public static ProcesadorJson getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorJson();
        }
        return instancia;
    }

    /**
     * Constructor de un Procesador de Json, llama a cargarPokedex()
     */
    public ProcesadorJson(){
        pokedex=new Pokedex();
        cargarPokedex();
    }

    /**
     * Procesa las lineas de fichero y vierte los datos obtenidos por el jsonObjet al constructor de Pokemons, lo haria con lombock
     * @see Pokedex
     * @see Pokemon
     * @see Gson
     * @see JsonObject
     */
    private void cargarPokedex() {
        String archivoJson=dataPath+File.separator+jsonFile;
        Gson gson =new Gson();

        try (FileReader reader = new FileReader(archivoJson)) {
            Type streamPoke=new TypeToken<List<JsonObject>>(){}.getType();
            List<JsonObject> e=gson.fromJson(reader,streamPoke);
            e.forEach(item->pokedex.getPokemon().add(new Pokemon(item)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una Lista'\<'Pokemon> de la pokedex, una vez se le ha aplicado una funcion. <br>
     * no es el metodo mas util, ya que existen otras herramientas, pero ahorra algunos pasos
     * @param fun Function'\<'Pokemon, Boolean>
     * @return List '\<'Pokemon>
     */
    public List <Pokemon> forEach(Function<Pokemon, Boolean> fun){
        return new ArrayList<>(getPokedex().getPokemon().stream().filter(fun::apply).toList());
    }
    ```
    ### Procesador Csv

    ``` java 
    @Getter
@Setter
public class ProcesadorCsv {
    /**
     * instancia Procesador
     */
    private static ProcesadorCsv instancia;
    /**
     * Pokedex del Procesador
     */
    private Pokedex pokedex;
    /**
     * path a la carpeta foco del procesador
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator + "data";
    /**
     * nombre del archivo csv creado
     */
    private static final String csvFile="pokemon.csv";

    /**
     * devuelve la instancia del procesador, si esta es null la inicia
     * @return ProcesadorCsv
     */
    public static ProcesadorCsv getInstancia() {
        if (instancia == null) {
            instancia = new ProcesadorCsv();
        }
        return instancia;
    }

    /**
     * constructor de procesador <br>
     * obtendra la Pokedex de la instancia del ProcesadorJson, si esta fueran null llamaria a calcularla<br>
     * llama a exportarCsv(), para generar el doc
     * @see ProcesadorJson
     */
    ProcesadorCsv (){
        pokedex=ProcesadorJson.getInstancia().getPokedex();
        exportarCsv();
    }

    /**
     * Exporta los datos de la pokedex a un nuevo archivo csv, pero solo guarda los valores "id, num, name, height, width" de los Pokemons
     */
    private void exportarCsv(){
        String archivoCsv=dataPath+File.separator+csvFile;
        File f=new File(archivoCsv);
        if (f.exists())
            f.delete();
        try(FileWriter fr=new FileWriter(f))  {
            fr.write("id, num, name, height, width \n");
            for(Pokemon p:pokedex.getPokemon()){
                fr.write(p.getId()+","+p.getNum()+","+p.getName()+","+p.getHeight()+","+p.getWeight()+'\n');
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("Archivo creado correctamente");
    }

    /**
     * Lee el csv y lo muestra por pantalla
     */
    public void mostrarCsvPantalla(){
        String archivoCsv=dataPath+File.separator+csvFile;
        File f=new File(archivoCsv);
        if (f.exists())
            System.out.println("Leyendo el archivo csv");
        System.out.println("-----------------------");
        try(BufferedReader fr= new BufferedReader(new FileReader(f))){
            while(fr.ready()){
                System.out.println(fr.readLine());
            }

        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
        System.out.println("-----------------------");
    }
    ´´´
    ## Repositories.base
    Aqui se almacena la interfaz del repositorio y la implementacion de esta para procesar pokemons

    ### CrudRepository
    Interfaz base del repositorio, implementa las operaciones basicas de consultas a BBDD

``` java 
public interface CrudRepository<T> {

    /**
     * Encontrar todas las entidades
     * @return List Lista de entidades
     * @throws SQLException Excepcion
     */
    List<T> findAll() throws SQLException;

    /**
     * Encontrar una entidad por su id
     * @param id Id de la entidad
     * @return T Entidad
     * @throws SQLException - Excepcion
     */
    Optional<T> findById(Integer id) throws SQLException;

    /**
     * Insertar una entidad
     * @param entity Entidad
     * @return T  Entidad insertada
     * @throws SQLException - Excepcion
     */
    boolean insert(T entity) throws SQLException;

    /**
     * Actualizar una entidad
     * @param entity  Entidad
     * @return T  Entidad actualizada
     * @throws SQLException  Excepcion
     */
    T update(T entity)  throws SQLException ;

    /**
     * Eliminar una entidad
     * @param entity  Entidad
     * @return T  Entidad eliminada
     * @throws SQLException  Excepcion
     */
    T delete(T entity)  throws SQLException ;

}
```
### PokedexCrudRepository
Implementa la interfaz anterior y desarrolla los metodos, findAll(),findById(Integer id),insert(Pokemon entity). Deja a para otro momento delete() y update(); ya que no eran necesarios en este proyecto

``` java
public class PokedexCrudRepository implements CrudRepository<Pokemon> {
    /**
     * Instancia de la base de datos
     * @see DataBaseManager
     */
    private DataBaseManager dataBaseManager;

    /**
     * Constructor
     * @param database - Instancia de la base de datos
     */
    public PokedexCrudRepository(DataBaseManager database){
        dataBaseManager = database;
    }

    /**
     * Devuelve una  List'\<'Pokemon> con los valores de todos los pokemons de la pokedex
     * @return  List'\<'Pokemon>
     * @throws SQLException si no puede ejecutar la sentencia
     * @see DataBaseManager
     */
    @Override
    public List<Pokemon> findAll() throws SQLException {
        dataBaseManager.openConnection();
        String sql = "SELECT * FROM pokemons";
        var result = dataBaseManager.select(sql).orElseThrow(() -> new SQLException("Error al obtener todos los pokemons"));
        List<Pokemon> pokedex = new ArrayList<>();

        while (result.next()) {
            Pokemon pok = new Pokemon();
            pok.setId(result.getInt("id"));
            pok.setNum(result.getString("num"));
            pok.setName(result.getString("name"));
            pok.setHeight(result.getDouble("height"));
            pok.setWeight(result.getDouble("weight"));
            pokedex.add(pok);
        }
        return pokedex;
    }

    /**
     * Optional'\<'Pokemon> con el valor del pokemon con el id dado
     * @param id Id de la entidad
     * @return Optional'\<'Pokemon>
     * @throws SQLException si no puede ejecutar la sentencia
     */
    @Override
    public Optional<Pokemon> findById(Integer id) throws SQLException {
        dataBaseManager.openConnection();
        String sql = "SELECT * FROM pokemons WHERE id=?";
        var result = dataBaseManager.select(sql,id).orElseThrow(() -> new SQLException("Error al obtener el pokemon con id "+id));
        Optional<Pokemon> res = Optional.of(new Pokemon());
        if(result.next()){
            res.get().setId(result.getInt("id"));
            res.get().setNum(result.getString("num"));
            res.get().setName(result.getString("name"));
            res.get().setHeight(result.getDouble("height"));
            res.get().setWeight(result.getDouble("weight"));
        }
        return res;
    }

    /**
     * Inserta un Pokemon
     * @param entity Entidad
     * @return true si se ejecuta correctamente, si salta la excepcion, no devuelve nada mas q la excepcion
     * @throws SQLException si no consigue insertar la entidad
     */
    @Override
    public boolean insert(Pokemon entity) throws SQLException {
        dataBaseManager.openConnection();
        String sql = "INSERT INTO pokemons VALUES( ?, ?, ?, ?, ?)";
        var result = dataBaseManager.insert(sql,
                entity.getId(),
                entity.getNum(),
                entity.getName(),
                entity.getHeight(),
                entity.getWeight()
        ).orElseThrow(() -> new SQLException("Error al insertar el pokemon con id "+entity.getId()));

        return true;//si llega aqui se ha ejecutado todo el cod
    }

    /**
     * No implementados
     * @param entity  Entidad
     * @return
     * @throws SQLException
     */
    @Override
    public Pokemon update(Pokemon entity) throws SQLException {
        return null;
    }
    /**
     * No implementados
     * @param entity  Entidad
     * @return
     * @throws SQLException
     */
    @Override
    public Pokemon delete(Pokemon entity) throws SQLException {
        return null;
    }
}

```
## services.database

Donde se almacenara la clase DateBaseManager, que gestiona la conexion con la BD
### DataBAseMAnager

``` java 
public class DataBaseManager {
    /**
     * String del path de la carpeta foco
     */
    private static final String dataPath= Paths.get("").toAbsolutePath()+ File.separator+"src"+ File.separator+"main"+ File.separator+"resources"+ File.separator;
    /**
     * Instancia de la clase
     * @see DataBaseManager#getInstance()
     */
    private static DataBaseManager instance;

    /**
     * Conexión con la base de datos
     * @see Connection
     */
    private Connection conn;

    /**
     * URL de la base de datos
     * @see String
     */
    private String url;

    /**
     * Puerto de la base de datos
     * @see String
     */
    private String port;

    /**
     * Nombre de la base de datos
     * @see String
     */
    private String name;

    /**
     * URL de conexión con la base de datos
     * @see String
     */
    private String connectionUrl;

    /**
     * Driver de la base de datos
     * @see String
     */
    private String driver;

    /**
     * Indica si se inicializa la base de datos
     * @see boolean
     */
    private boolean initDataBase;

    /**
     * Sentencia preparada
     * @see PreparedStatement
     */
    private PreparedStatement preparedStatement;

    /**
     * Constructor de la clase
     */
    private DataBaseManager(){
        initConfig();
    }

    /**
     * Método que devuelve la instancia de la clase
     * @return DataBaseManager  Instancia de la clase
     */
    public static DataBaseManager getInstance(){
        if (instance == null) {
            instance = new DataBaseManager();
        }
        return instance;
    }


    /**
     * Método que inicializa la configuración de la base de datos
     * @see Properties
     * @see FileInputStream
     * @see IOException
     * @see FileNotFoundException
     * @see RuntimeException
     */
    public void initConfig() {
        String propertiesFile = ClassLoader.getSystemResource("main/config.properties").getFile();
        Properties prop = new Properties();

        try{
            prop.load(new FileInputStream(propertiesFile));

            url = prop.getProperty("database.url");
            port = prop.getProperty("database.port");
            name = prop.getProperty("database.name");
            connectionUrl = prop.getProperty("database.connectionUrl");
            driver = prop.getProperty("database.driver");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    /**
     * Método que abre la conexion a la base de datos
     * @see SQLException
     * @throws SQLException Error al abrir la conexion
     */
    public void openConnection() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            return;
        }
        conn = DriverManager.getConnection(connectionUrl);
    }

    /**
     * Método que cierra la conexion a la base de datos
     * @see SQLException
     */
    public void closeConnection() {
        if(conn != null){
            try {
                conn.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Método que ejecuta una consulta a la base de datos
     * @see ResultSet
     * @see SQLException
     * @param querySQL Consulta a la base de datos
     * @param params Parametros de la consulta
     * @return ResultSet Resultado de la consulta
     */
    private ResultSet executeQuery(String querySQL, Object... params) throws SQLException {
        this.openConnection();
        preparedStatement = conn.prepareStatement(querySQL);
        // Vamos a pasarle los parametros usando preparedStatement
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        return preparedStatement.executeQuery();
    }

    /**
     * Método que ejecuta una consulta select a la base de datos
     * @see ResultSet
     * @see SQLException
     * @param querySQL Consulta a la base de datos
     * @param params Parametros de la consulta
     * @return ResultSet Resultado de la consulta
     * @throws SQLException Error al ejecutar la consulta select
     */
    public Optional<ResultSet> select(String querySQL, Object... params) throws SQLException {
        return Optional.of(executeQuery(querySQL, params));
    }

    /**
     * Método que ejecuta una consulta insert a la base de datos
     * @see ResultSet
     * @see SQLException
     * @param insertSQL Consulta a la base de datos
     * @param params Parametros de la consulta
     * @return ResultSet Resultado de la consulta
     * @throws SQLException Error al ejecutar la consulta insert
     */
    public Optional<ResultSet> insert(String insertSQL, Object... params) throws SQLException {

        preparedStatement = conn.prepareStatement(insertSQL, preparedStatement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < params.length; i++) {
            preparedStatement.setObject(i + 1, params[i]);
        }
        preparedStatement.executeUpdate();
        return Optional.of(preparedStatement.getGeneratedKeys());
    }


    /**
     * Metodo que ejecuta un archivo sql para inicializar la base de datos
     * @see ScriptRunner
     * @see BufferedReader
     * @see FileReader
     * @see PrintWriter
     * @see SQLException
     * @see FileNotFoundException
     * @param sqlFile path archivo sql
     * @param logWriter Indica si se escribe en consola
     * @throws FileNotFoundException Error al encontrar el archivo sql
     * @throws SQLException Error al ejecutar el archivo sql
     */
    public void initData(String sqlFile, boolean logWriter) throws FileNotFoundException, SQLException {
        this.openConnection();
        var sr = new ScriptRunner(conn);
        var reader = new BufferedReader(new FileReader(DataBaseManager.dataPath+sqlFile));
        if (logWriter) {
            sr.setLogWriter(new PrintWriter(System.out));
        } else {
            sr.setLogWriter(null);
        }
        sr.runScript(reader);
    }
}

``` 

## Main
Clase principal del proyecto, donde se produce:
* el procesamiento de informacion de Json
* realiza consultas a estos datos
* exporta a un csv y lo muestra en pantalla
* crea una BBDD para contener los datos y los introduce
* realiza una ultima consulta

``` java
public class Main {
    /**
     * void main del proyecto
     * @param args String[]
     */
    public static void main(String[] args) {
        System.out.println("-------------------------------------------------------------------------");
        System.out.println("---------------------- Comienzo Ejecucion Main --------------------------");
        System.out.println("-------------------------------------------------------------------------");

        ProcesadorJson pj=ProcesadorJson.getInstancia();
        List<Pokemon> res=new ArrayList<>();
        System.out.println("*************************************************************************");
        System.out.println("Consulta: Obtener el nombre los 10 primeros pokemons");
        System.out.println("------------------------------------------------------------------");
        res=pj.forEach(e->e.getId()<=10);
        Stream<String>r=res.stream().map(Pokemon::getName);
        r.forEach(System.out::println);

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Obtener el nombre de los 5 últimos pokemons");
        System.out.println("------------------------------------------------------------------");
        int max=pj.getPokedex().getPokemon().size();
        res=pj.forEach(e->e.getId()>max-5);
        Stream<String>r3=res.stream().map(Pokemon::getName);
        r3.forEach(System.out::println);

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Obtener los datos de Pikachu.");
        System.out.println("------------------------------------------------------------------");
        res=pj.forEach(e->e.getName().equals("Pikachu"));
        res.forEach(System.out::println);

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Obtener la evolución de Charmander.");
        System.out.println("------------------------------------------------------------------");
        res=pj.forEach(e->e.getName().equals("Charmander"));
        System.out.println(res.get(0).getNextEvolution().toString());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Obtener el nombre de los pokemons de tipo fire.");
        System.out.println("------------------------------------------------------------------");
        res=pj.forEach(e->e.getType().contains("Fire"));
        Stream<String>r4=res.stream().map(Pokemon::getName);
        r4.forEach(System.out::println);

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Obtener el nombre de los pokemons con debilidad water o electric.");
        System.out.println("------------------------------------------------------------------");
        res=pj.forEach(e->e.getWeaknesses().contains("Water")||e.getWeaknesses().contains("Electric"));
        Stream<String>r5=res.stream().map(Pokemon::getName);
        r5.forEach(System.out::println);

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Numero de pokemons con solo una debilidad.");
        System.out.println("------------------------------------------------------------------");
        res=pj.forEach(e->e.getWeaknesses().size()==1);
        System.out.println(res.size());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemon con más debilidades.");
        System.out.println("------------------------------------------------------------------");
        Pokemon resul=pj.getPokedex().obtenerStream().filter(p->p.getWeaknesses()!=null)
                .max(Comparator.comparingInt(Pokemon::sumatorioDebilidades))
                .get();
        System.out.println(resul.getName()+" "+resul.getWeaknesses().toString());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemon con menos evoluciones.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream()
                .min(Comparator.comparingInt(Pokemon::sumatorioEvoluciones)).get().getName());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemon con una evolución que no es de tipo fire.");
        System.out.println("------------------------------------------------------------------");
        pj.getPokedex().obtenerStream().filter(p->!p.getType().contains("Fire")&&p.getPrevEvolution()!=null&&p.getPrevEvolution().size()==1)
                .forEach(pok->{
                    EvolutionItem ev=new EvolutionItem(pok.getName(),pok.getNum());
                    pj.forEach(p->p.getNextEvolution()!=null&&p.getNextEvolution().contains(ev)).stream().map(Pokemon::getName).forEach(System.out::println);
                });

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemon más pesado.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().max(Comparator.comparingDouble(Pokemon::getWeight)).get().getName());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemon más alto.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().max(Comparator.comparingDouble(Pokemon::getHeight)).get().getName());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemon con el nombre mas largo.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().map(Pokemon::getName)
                .max(Comparator.comparingInt(String::length)).get());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Media de peso de los pokemons.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::getWeight).average().getAsDouble());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Media de altura de los pokemons.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::getHeight).average().getAsDouble());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Media de evoluciones de los pokemons.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::sumatorioEvoluciones).average().getAsDouble());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Media de debilidades de los pokemons.");
        System.out.println("------------------------------------------------------------------");
        System.out.println(pj.getPokedex().obtenerStream().mapToDouble(Pokemon::sumatorioDebilidades).average().getAsDouble());

        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemons agrupados por tipo.");
        System.out.println("------------------------------------------------------------------");
        pj.getPokedex().obtenerStream().map(Pokemon::getType).distinct().forEach(tipo->{
            System.out.println(tipo.toString());
            pj.forEach(pok->pok.getType().equals(tipo))
                    .forEach(p->{
                             System.out.println(p.getName());
            });
        });
        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Numero de pokemons agrupados por debilidad.");
        System.out.println("------------------------------------------------------------------");
        pj.getPokedex().obtenerStream().map(Pokemon::getWeaknesses).distinct().forEach(debil->{
            System.out.println(debil.toString());
            pj.forEach(pok->pok.getWeaknesses().equals(debil))
                    .forEach(p->{
                        System.out.println(p.getName());
                    });
        });
        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Pokemons agrupados por número de evoluciones.");
        System.out.println("------------------------------------------------------------------");
        pj.getPokedex().obtenerStream().map(Pokemon::sumatorioEvoluciones).distinct().forEach(e->{
            System.out.println(e+" evolucion(es):");
            pj.forEach(pok->pok.sumatorioEvoluciones()==e)
                    .forEach(p->{
                        System.out.println(p.getName());
                    });
        });
        System.out.println("------------------------------------------------------------------");
        System.out.println("Consulta:Sacar la debilidad más común.");
        System.out.println("------------------------------------------------------------------");
        String debilMasComun="";
        long count=0;
        List<String> debilidades=new ArrayList<>();
        pj.getPokedex().obtenerStream().map(Pokemon::getWeaknesses).forEach(debilidades::addAll);
        for (String d : debilidades) {
            long e = debilidades.stream().filter(s -> s.equals(d)).count();
            if (count <= e) {
                count = e;
                debilMasComun = d;
            }
        }
        System.out.println("Debilidad mas comun: "+debilMasComun);
        System.out.println("------------------------------------------------------------------");
        System.out.println("------------------------------------------------------------------");
        System.out.println("Exportando a un CSV");
        ProcesadorCsv pc=ProcesadorCsv.getInstancia();
        pc.mostrarCsvPantalla();

        System.out.println("------------------------------------------------------------------");
        System.out.println("Creando Base de Datos");
        System.out.println("*********************");
        // Obtener la instancia de DatabaseManager y la conexión
        DataBaseManager dbManager = DataBaseManager.getInstance();

        // Ejecutar script SQL desde un archivo
        try {
            dbManager.initData("main/init.sql",true);
        } catch (FileNotFoundException e) {
            System.out.println("aa");
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Insertando Datos");
        System.out.println("*********************");
        PokedexCrudRepository base=new PokedexCrudRepository(dbManager);

        pc.getPokedex().obtenerStream().forEach(p->{
            try {
                base.insert(p);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });

        System.out.println("Obteniendo datos Pikachu(id=25)");
        try {
            Optional<Pokemon> pikachu=base.findById(25);
            System.out.println(pikachu.get().toString());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println("-------------------------------------------------------------------------");
        System.out.println("------------------------ Fin Ejecucion Main -----------------------------");
        System.out.println("-------------------------------------------------------------------------");
    }
}
```

## RESOURCES

Como recursos nos encontraremos los siguientes archivos.

#### config.properties

Datos necesarios para una correcta conexión a la base de datos
```properties
database.url=jdbc:sqlite
database.port=3306
database.name=pokedex
database.connectionUrl=jdbc:sqlite:pokedex.db
database.driver=org.sqlite.JDBC
```

#### init.sql

Que sera el script que cree la base de datos
```sql
DROP TABLE IF EXISTS pokemons;

CREATE TABLE IF NOT EXISTS pokemons(
      id INTEGER PRIMARY KEY,
      num TEXT NOT NULL,
      name TEXT NOT NULL,
      height DOUBLE NOT NULL,
      weight DOUBLE NOT NULL
);
```


## PROBLEMAS EN EL DESARROLLO DEL EJERCICIO

- A la hora de procesar el Json, a pesar de usar la api gson y un constructor de clases Pojo no todo se hacia automaticamente. Y habia algunas propiedades del Pokemon que no se procesaban bien y habia que hacer algun paso intermedio. Para esto se uso JsonObject y JsonElemnte, a si como el uso de apis funcionales. Esto facilito bastante la programación, porque podiamos indicar a la aplicacion que queriamos hacer exactamente con cada elemento del Json. 
- Aun asi, no conseguimos procesar la fecha ni usando distintos Formatter. 
- Otro punto conflictivo ha sido el uso de Lombock. A pesar de ser una muy buena herramienta que facilita la escritura de codigo, mi ide se desconfiguraba constantemente, y no podia aprovechar las herramientas que ofrece. La ejecucion del programa me remitia a excepciones por no encontrar los "simbolos" que si me recogia a la hora de codificar. Adjunto captura, por si el fallo es mio tambien, poder corregirlo.
- Una vez hecho el procesamiento del json, el resto ha sido bastante sencillo, ya que los datos estaban tal y como queriamos para procesarlos de la mejor forma posible.
- El ultimo problema que nos ha surgido ha sido a la creacion de Jar, que hay que hacer los pasos bien para poder generarlo correctamente.
