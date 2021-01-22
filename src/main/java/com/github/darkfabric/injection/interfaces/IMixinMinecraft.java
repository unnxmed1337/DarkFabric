package com.github.darkfabric.injection.interfaces;

import net.minecraft.client.User;

public interface IMixinMinecraft {
    void setUser(User user);
}