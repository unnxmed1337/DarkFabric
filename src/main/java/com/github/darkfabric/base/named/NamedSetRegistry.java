package com.github.darkfabric.base.named;

import com.github.darkfabric.base.SetRegistry;
import com.github.darkfabric.base.interfaces.INameable;

public abstract class NamedSetRegistry<V extends INameable> extends SetRegistry<V> {

    public V getByName(String name, boolean ignoreCase) {
        return getObjects().stream().filter(v -> (ignoreCase ? v.getName().equalsIgnoreCase(name)
                : v.getName().equals(name))).findFirst().orElse(null);
    }

}