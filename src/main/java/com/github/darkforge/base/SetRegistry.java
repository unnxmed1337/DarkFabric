package com.github.darkforge.base;

import com.google.common.collect.Sets;
import lombok.Getter;

import java.util.Collections;
import java.util.Set;

public abstract class SetRegistry<V> {

    @Getter
    private final Set<V> objects = Collections.synchronizedSet(Sets.newLinkedHashSet());

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

}
