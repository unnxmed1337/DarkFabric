package com.github.darkfabric.discord;

import com.github.darkfabric.base.interfaces.INameable;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Setter
@Getter
public class Presence implements INameable {

    private String appId = getClass().getAnnotation(Info.class).appId();
    private String name = getClass().getAnnotation(Info.class).name();
    private String bigImageName = getClass().getAnnotation(Info.class).bigImageName();
    private String bigImageText = getClass().getAnnotation(Info.class).bigImageText();
    private String smallImageName = getClass().getAnnotation(Info.class).smallImageName();
    private String smallImageText = getClass().getAnnotation(Info.class).smallImageText();
    private String detailsOne = getClass().getAnnotation(Info.class).detailsOne();
    private String detailsTwo = getClass().getAnnotation(Info.class).detailsTwo();

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Info {
        String appId();

        @NotNull String name();

        String bigImageName();

        String bigImageText();

        String smallImageName();

        String smallImageText();

        String detailsOne();

        String detailsTwo();
    }

}