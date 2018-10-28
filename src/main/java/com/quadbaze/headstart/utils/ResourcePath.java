package com.quadbaze.headstart.utils;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Olatunji on 6/2/2016.
 */
public final class ResourcePath {

    /**
     * Static & un-secured resource paths
     */
    public static final List<String> CONTEXT_PERMITTED_PATHS = Arrays.asList(
            "/",
            "/js/**",
            "/css/**",
            "/images/**",
            "/fonts/**",
            "/plugins/**",
            "/login/**"
    );

}