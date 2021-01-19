package com.github.darkfabric.base;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public abstract class Registry<V> {

    private final List<V> objects;
    
    public Registry(List<V> objects) {
        this.objects = objects;
    }
    
    public Registry() {
        this(new CopyOnWriteArrayList<>(););
    }
    
    public void register(V... objects) {
        objects.addAll(Arrays.asList(objects));
    }

    public void unregister(V objects) {
        objects.removeAll(Arrays.asList(objects));
    }
    
        public void clearList() {
        objects.clear();
    }

    public <E extends V> getByClass(Class<?> clazz) {
        return this.getBy(bject -> object.getClass() == clazz);
    }
    
    public <E extends V> getBy(Predicate<V> filter) {
        return (E) this.getObjects().stream().filter(filter).findFirst.orElse(null);
    }
    
    public List<V> getObjects() {
        return Collections.unmodifiableList(this.objects);
    }

}
