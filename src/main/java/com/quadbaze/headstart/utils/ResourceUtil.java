package com.quadbaze.headstart.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

/**
 * @author: Olatunji O. Longe
 * @created: 20 Oct, 2018, 10:39 PM
 */

@Component
public class ResourceUtil {

    private ResourceLoader resourceLoader;

    @Autowired
    public ResourceUtil(ResourceLoader resourceLoader){
        this.resourceLoader = resourceLoader;
    }

    /**
     * Reads resource file as a string.
     * @param resourcePath the path to the resource file
     * @return the file's contents or null if the file could not be opened
     */
    public String getResourceContent(String resourcePath) {
        try {
            InputStream is = resourceLoader.getResource(resourcePath).getInputStream();
            if (is != null) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
