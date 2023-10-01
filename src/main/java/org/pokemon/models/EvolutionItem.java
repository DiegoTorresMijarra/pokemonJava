package org.pokemon.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EvolutionItem{
    private String num;
    private String name;

    public EvolutionItem(JsonElement jo) {
        JsonObject obj=jo.getAsJsonObject();
        this.num = obj.get("num").toString().replace((char)34,(char)32).trim();
        this.name =obj.get("name").toString().replace((char)34,(char)32).trim();
    }
//me obliga a crearlos, por q no funciona el lombock
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
    @Override
    public String toString() {
        return "{name='" + name + '\'' +
                ",num=" + num + '\''+
                '}';
    }
    @Override
    public boolean equals(Object ev){
        if(ev.getClass()==EvolutionItem.class)
            return this.name.compareTo(((EvolutionItem)ev).getName())==0&&this.num.compareTo(((EvolutionItem)ev).getNum())==0;
        else
            return false;
    }
}
