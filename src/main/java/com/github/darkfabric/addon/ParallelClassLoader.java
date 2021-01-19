package com.github.darkfabric.addon;

import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandlerFactory;

public class ParallelClassLoader extends URLClassLoader {

    static {
        registerAsParallelCapable();
    }

    public ParallelClassLoader(ClassLoader parent, URL... urls) {
        super(urls, parent);
    }

}