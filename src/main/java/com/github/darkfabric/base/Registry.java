package com.github.darkfabric.base;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public abstract class Registry<V> {

    private final List<V> objects;
    
    public Registry(List<V> objects) {
        this.objects = objects;
    }
    
    public Registry() {
        this(new CopyOnWriteArrayList<>());
    }

    public void register(V... objects) {
        this.objects.addAll(Arrays.asList(objects));
    }

    public void unregister(V... objects) {
        this.objects.removeAll(Arrays.asList(objects));
    }
    
    public void clearList() {
        objects.clear();
    }

    public <E extends V> E getByClass(Class<?> clazz) {
        return this.getBy(object -> object.getClass() == clazz);
    }
    
    public <E extends V> E getBy(Predicate<V> filter) {
        return (E) this.getObjects().stream().filter(filter).findFirst().orElse(null);
    }
    
    public List<V> getObjects() {
        return Collections.unmodifiableList(this.objects);
    }

}
