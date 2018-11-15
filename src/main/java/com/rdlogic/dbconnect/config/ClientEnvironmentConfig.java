package com.rdlogic.dbconnect.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertiesPropertySource;

import javax.inject.Inject;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Configuration
public class ClientEnvironmentConfig {


    private final String clientId;
    private final String clientEnv;
    private final String clientCode;
    private static final Pattern clientRegexPattern = Pattern.compile("(?<code>\\w{2})rdm(?<env>dev|prd|trn)");
    @Inject
    private ConfigurableEnvironment environment;

    public ClientEnvironmentConfig(@Value("${rdl.client}")String clientId) {
        this.clientId = clientId;
        Matcher clientParseMatcher = clientRegexPattern.matcher(clientId);
        if (clientParseMatcher.find()) {
            clientCode = clientParseMatcher.group("code");
            clientEnv = clientParseMatcher.group("env");
        } else {
            clientEnv = "dev";
            clientCode = "";
        }
        Properties clientProps = new Properties();
        clientProps.setProperty("client.env", clientEnv);
        clientProps.setProperty("client.code", clientCode);
//        environment.getPropertySources().addFirst(new PropertiesPropertySource("rdl.active", clientProps));
    }

    public String getClientEnv() {
        return clientEnv;
    }

    public String getClientCode() {
        return clientCode;
    }
}
