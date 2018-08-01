package com.sam.testsecurityrest;

import java.util.HashMap;
import java.util.Map;

public class JsonBuilder<K,V> {

    private Map<K, V> map;

    public JsonBuilder(Map<K, V> map) {
        this.map = map;
    }

    public JsonBuilder() {
        this.map = new HashMap<K,V>();
    }

    public void addKeyValue(K key, V value) {
        map.put(key, value);
    }

    public JsonBuilder<K,V> addKeyValueThis(K key, V value) {
        map.put(key, value);
        return this;
    }

    public Map<K,V> getJson() {
        return map;
    }

}
