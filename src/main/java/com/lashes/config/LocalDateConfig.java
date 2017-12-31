package com.lashes.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Locale;

@Configuration
public class LocalDateConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        super.addFormatters(registry);
        registry.addFormatterForFieldType(LocalDate.class, new Formatter<LocalDate>() {
            @Override
            public String print(LocalDate localDate, Locale locale) {
                return null;
            }

            @Override
            public LocalDate parse(String s, Locale locale) throws ParseException {
                return null;
            }
        });
    }
}
