package org.pokemon.models;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import lombok.Data;

@Data
public class EvolutionItem {
    private String num;
    private String name;

    public EvolutionItem(JsonElement jo) {
        JsonObject obj=jo.getAsJsonObject();
        this.num = obj.get("num").toString().replace((char)34,(char)32).trim();
        this.name =obj.get("name").toString().replace((char)34,(char)32).trim();
    }
    @Override
    public String toString() {
        return "{name='" + name + '\'' +
                ",num=" + num + '\''+
                '}';
    }
}
