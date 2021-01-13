package com.github.darkforge.base.named;

import com.github.darkforge.base.SetRegistry;
import com.github.darkforge.base.interfaces.INameable;

public abstract class NamedSetRegistry<V extends INameable> extends SetRegistry<V> {

    public V getByName(String name) {
        for (V object : this.getObjects())
            if (object.getName().equalsIgnoreCase(name))
                return object;
        return null;
    }

}