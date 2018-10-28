package com.quadbaze.headstart.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.stereotype.Component;

import java.util.IllegalFormatException;

/**
 * @author: Olatunji O. Longe
 * @created: 18 Oct, 2018, 6:22 AM
 */

@Component
public class Localizer extends MessageSourceAccessor {

    @Autowired
    public Localizer(MessageSource messageSource) {
        super(messageSource, LocaleContextHolder.getLocale());
    }

    /**
     * Returns a string that requires no arguments. This is a
     * shortcut for the {@link #getMessage(String)} method
     * @param key
     * @return
     * @throws NoSuchMessageException
     */
    public String getProperty(String key) throws NoSuchMessageException {
        return this.getMessage(key);
    }

    /**
     * Returns a formatted string using the specified String.format(...) arguments
     * The number of supplied {@code args} must be equal to the number of
     * expected arguments in the format string specified by the {@code key}
     * in the {@code messages.properties} file
     * @param key
     * @param args
     * @return
     * @throws IllegalFormatException
     * @throws NoSuchMessageException
     */
    public String getProperty(String key, String... args) throws IllegalFormatException, NoSuchMessageException {
        return String.format(this.getMessage(key), args);
    }

}
