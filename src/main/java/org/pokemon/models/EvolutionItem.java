package org.pokemon.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

/**
 * Clase que representa un item de evolucion de pokemon, sirve para evoluciones previas y futuras
 * @author Daniel y Diego
 * @version 1.0
 * @see lombok
 * @see JsonElement
 */
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

//me obliga a crearlos, porq no funciona el lombock, genera bad smells...
    public EvolutionItem(String name,String num){
        this.num=num;
        this.name=name;
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }


}
