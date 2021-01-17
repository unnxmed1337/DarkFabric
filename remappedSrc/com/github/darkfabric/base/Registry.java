package com.github.darkfabric.base;

import lombok.Getter;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Registry<V> {

    @Getter
    private final List<V> objects = new CopyOnWriteArrayList<>();

    public void register(V object) {
        objects.add(object);
    }

    public void unregister(V object) {
        objects.remove(object);
    }

    public V getByClass(Class<?> clazz) {
        for (V object : this.getObjects())
            if (object.getClass() == clazz)
                return object;
        return null;
    }

    public void unregisterAllRegisteredObjects() {
        objects.forEach(this::unregister);
    }

}