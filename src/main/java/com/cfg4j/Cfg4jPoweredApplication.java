package com.cfg4j;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.cfg4j.provider.ConfigurationProvider;
import org.cfg4j.provider.ConfigurationProviderBuilder;
import org.cfg4j.source.ConfigurationSource;
import org.cfg4j.source.git.GitConfigurationSourceBuilder;

public class Cfg4jPoweredApplication {

    // Change this interface to whatever you want
    public interface SampleConfig {
        Integer birthYear();

        List<String> friends();

        URL homepage();

        Map<String, Character> grades();
    }

    public static void main(String... args) {
        ConfigurationSource source = new GitConfigurationSourceBuilder().withRepositoryURI(
                "https://github.com/cfg4j/cfg4j-git-sample-config.git").build();

        ConfigurationProvider provider = new ConfigurationProviderBuilder()
                .withConfigurationSource(source).build();

        SampleConfig config = provider.bind("reksio", SampleConfig.class);

        // Use it!
        System.out.println(config.homepage());
    }

}