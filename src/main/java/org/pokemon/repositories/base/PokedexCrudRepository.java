package org.pokemon.repositories.base;

import org.pokemon.models.Pokedex;
import org.pokemon.models.Pokemon;
import org.pokemon.services.database.DataBaseManager;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Repositorio de Pokemons
 * @author Daniel y Diego
 */
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
