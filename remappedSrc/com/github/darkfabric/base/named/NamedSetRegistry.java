package com.github.darkfabric.base.named;

import com.github.darkfabric.base.SetRegistry;
import com.github.darkfabric.base.interfaces.INameable;

public abstract class NamedSetRegistry<V extends INameable> extends SetRegistry<V> {

    public V getByName(String name) {
        for (V object : this.getObjects())
            if (object.getName().equalsIgnoreCase(name))
                return object;
        return null;
    }

}