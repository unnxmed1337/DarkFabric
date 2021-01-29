package com.github.darkfabric.injection.interfaces;

import net.minecraft.client.User;

import java.net.Proxy;

public interface IMixinMinecraft {
    void setUser(User user);
    void setProxy(Proxy proxy);
}