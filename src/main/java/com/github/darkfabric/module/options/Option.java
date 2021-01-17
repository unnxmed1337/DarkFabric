package com.github.darkfabric.module.options;

import com.github.darkfabric.exceptions.ArgumentException;
import com.github.darkfabric.util.chat.ChatUtils;
import com.github.darkfabric.util.render.ColorUtils;
import com.google.common.collect.Lists;
import joptsimple.internal.Strings;
import lombok.AllArgsConstructor;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Option
 *
 * @author Hexeption admin@hexeption.co.uk
 * @since 09/11/2019 - 11:25 am
 */
public class Option {

    public String name, description;
    public Value value;
    public Type type;
    public Map<String, Option> options = new LinkedHashMap<>();
    public int color;

    public Option(String name, String description, Value value, Type type) {
        this(name, description, value, type, (List) null);
    }

    public Option(String name, String description, Value value, Type type, Option[] options) {
        this(name, description, value, type, Arrays.asList(options));
    }

    public Option(String name, String description, Value value, Type type, List<Option> options) {
        this.name = name;
        this.description = description;
        this.value = value;
        this.type = type;
        if (options != null) {
            for (Option option : options) {
                this.options.put(option.name.toLowerCase().replaceAll(" ", ""), option);
            }
        }
        color = ColorUtils.rainbow(1f);
    }

    public static Option get(Map<String, Option> options, String key) {
        return options.get(key);
    }

    public static Option get(Map<String, Option> options, String key, String key2) {
        return options.get(key).options.get(key2);
    }

    public static void setOptionValue(Option option, String arg) throws Exception {
        Object value = null;
        String message = String.format("[v]%s [t]set to [v]%s", option.name, arg);
        switch (option.type) {
            case BOOLEAN:
                try {
                    if (arg.equalsIgnoreCase("true") || arg.equalsIgnoreCase("false")) {
                        value = Boolean.parseBoolean(arg);
                    } else {
                        throw new ArgumentException();
                    }
                } catch (Exception e) {
                    throw new ArgumentException();
                }
                break;
            case CHOICE:
                int is = -1;
                String[] list = ((ValueChoice) option.value).list;
                for (int i = 0; i < list.length; i++) {
                    if (list[i].equalsIgnoreCase(arg)) {
                        is = i;
                    }
                }
                if (is != -1) {
                    value = list[is];
                } else {
                    throw new ArgumentException();
                }
                break;
            case KEYBIND:
                value = arg.toUpperCase();
                break;
            case NUMBER:
                try {
                    value = Double.parseDouble(arg);
                } catch (Exception e) {
                    throw new ArgumentException();
                }
                break;
            case OTHER:
                value = arg;
                break;
            case STRING:
                value = arg;
                break;
            case LIST:
                List<String> l = Lists.newLinkedList((List<String>) option.value.value);
                if (l.contains(arg)) {
                    l.remove(arg);
                    message = String.format("[v]%s [t]removed from [v]%s", arg, option.name);
                } else {
                    l.add(arg);
                    message = String.format("[v]%s [t]added to [v]%s", arg, option.name);
                }
                value = l;
                break;
        }

        if (value != null) {
            option.value.value = value;
            ChatUtils.sendMessageToPlayerSimple(message, true);
        }
    }

    public boolean BOOLEAN() {
        if (value instanceof ValueBoolean)
            return (boolean) value.value;
        return false;
    }

    public double DOUBLE() {
        if (value instanceof ValueDouble)
            return (double) value.value;
        return 0;
    }

    public int INTEGER() {
        return (int) DOUBLE();
    }

    public String STRING() {
        if (type == Type.LIST) {
            return Strings.join(LIST(), ",");
        }
        return value.value.toString();
    }

    public String CHOICE() {
        return STRING();
    }

    public List<String> LIST() {
        return (List<String>) value.value;
    }

    @AllArgsConstructor
    public enum Type {
        BOOLEAN("<true|false>"),
        CHOICE("<%s>"),
        KEYBIND("<key>"),
        LIST("<value>"),
        NUMBER("<number>"),
        OTHER("<value>"),
        STRING("<text>");
        public String usage;
    }
}
