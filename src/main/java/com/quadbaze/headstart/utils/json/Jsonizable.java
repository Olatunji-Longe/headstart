package com.quadbaze.headstart.utils.json;

import com.google.gson.JsonElement;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author Olatunji O. Longe: Created on (10/05/2018)
 */
@SuppressWarnings("unchecked")
public abstract class Jsonizable extends LinkedHashMap<String, Object> {

    public Jsonizable(){}

    public Jsonizable(Map<String, Object> map){
        this.putAll(map);
    }

    protected <T extends Jsonizable> T assign(String key, Object value){
        this.put(key, value);
        return (T)this;
    }

    public abstract  <T extends Jsonizable> T set(String key, Object value);

    public JsonElement toJson(){
        return GsonUtil.toJson(this);
    }
}

