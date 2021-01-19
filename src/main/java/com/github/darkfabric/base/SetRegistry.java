package com.github.darkfabric.base;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;

public abstract class SetRegistry<V> {

    private final Set<V> objects;

    public SetRegistry(Set<V> objects) {
        this.objects = objects;
    }

    public SetRegistry() {
        this(Sets.newLinkedHashSet());
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

    public Set<V> getObjects() {
        return Collections.unmodifiableSet(this.objects);
    }

}
