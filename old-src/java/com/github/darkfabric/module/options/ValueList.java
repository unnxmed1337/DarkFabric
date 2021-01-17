package com.github.darkfabric.module.options;

import java.util.Arrays;
import java.util.List;

/**
 * ValueList
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 09/11/2019 - 11:41 am
 */
public class ValueList extends Value {
    public ValueList(String... list) {
        this(Arrays.asList(list));
    }

    public ValueList(List<String> list) {
        super(list);
    }
}