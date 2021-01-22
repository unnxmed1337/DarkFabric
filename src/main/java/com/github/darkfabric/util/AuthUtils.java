/*
 * Copyright (c) 2020 - unnamed and putzefurcht
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.darkfabric.util;

import com.github.darkfabric.injection.interfaces.IMixinMinecraft;
import com.mojang.authlib.Agent;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.yggdrasil.YggdrasilAuthenticationService;
import com.mojang.authlib.yggdrasil.YggdrasilEnvironment;
import com.mojang.authlib.yggdrasil.YggdrasilMinecraftSessionService;
import com.mojang.authlib.yggdrasil.YggdrasilUserAuthentication;
import net.minecraft.client.Minecraft;
import net.minecraft.client.User;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;

public class AuthUtils {

    public static User createPremiumAuthenticatedSession(String email, String password)
            throws AuthenticationException {
        YggdrasilUserAuthentication auth = createUserAuthentication();
        auth.setUsername(email);
        auth.setPassword(password);
        auth.logIn();
        return new User(auth.getSelectedProfile().getName(), auth.getSelectedProfile().getId().toString(),
                auth.getAuthenticatedToken(), "mojang");
    }

    public static YggdrasilUserAuthentication createUserAuthentication() {
        return (YggdrasilUserAuthentication) new YggdrasilAuthenticationService(Proxy.NO_PROXY, "")
                .createUserAuthentication(Agent.MINECRAFT);
    }

    public static void logInIntoSession(String email, String password) throws AuthenticationException {
        YggdrasilMinecraftSessionService service
                = (YggdrasilMinecraftSessionService) Minecraft.getInstance().getMinecraftSessionService();
        setBaseUrl(service, YggdrasilEnvironment.PROD.getSessionHost() + "/session/minecraft/");
        setJoinUrl(service, YggdrasilEnvironment.PROD.getSessionHost() + "/session/minecraft/join");
        setCheckUrl(service, YggdrasilEnvironment.PROD.getSessionHost() + "/session/minecraft/hasJoined");
        ((IMixinMinecraft) Minecraft.getInstance()).setUser(createPremiumAuthenticatedSession(email, password));
    }

    public static void logInCracked(String userName) throws AuthenticationException {
        YggdrasilMinecraftSessionService service
                = (YggdrasilMinecraftSessionService) Minecraft.getInstance().getMinecraftSessionService();
        setBaseUrl(service, YggdrasilEnvironment.PROD.getSessionHost() + "/session/minecraft/");
        setJoinUrl(service, YggdrasilEnvironment.PROD.getSessionHost() + "/session/minecraft/join");
        setCheckUrl(service, YggdrasilEnvironment.PROD.getSessionHost() + "/session/minecraft/hasJoined");
        ((IMixinMinecraft) Minecraft.getInstance()).setUser(createCrackedSession(userName));
    }

    public static User createCrackedSession(String username) {
        return new User(username, "-", "-", "mojang");
    }

    /*
     * The rest of this class is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client/).
     * Copyright (c) 2021 Meteor Development.
     */

    private static void setBaseUrl(YggdrasilMinecraftSessionService service, String url) {
        try {
            Field field = service.getClass().getDeclaredField("baseUrl");
            field.setAccessible(true);
            field.set(service, url);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    private static void setJoinUrl(YggdrasilMinecraftSessionService service, String url) {
        try {
            Field field = service.getClass().getDeclaredField("joinUrl");
            field.setAccessible(true);
            field.set(service, new URL(url));
        } catch (IllegalAccessException | NoSuchFieldException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

    private static void setCheckUrl(YggdrasilMinecraftSessionService service, String url) {
        try {
            Field field = service.getClass().getDeclaredField("checkUrl");
            field.setAccessible(true);
            field.set(service, new URL(url));
        } catch (IllegalAccessException | NoSuchFieldException | MalformedURLException e) {
            e.printStackTrace();
        }
    }

}