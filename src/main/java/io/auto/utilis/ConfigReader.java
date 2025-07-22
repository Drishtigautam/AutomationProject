package io.auto.utilis;

import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

public class ConfigReader {

    private final Properties properties;
    public ConfigReader(){
        properties = new Properties();

        try(InputStream inputStream =getClass().getClassLoader().getResourceAsStream("application.properties")) {
            if(inputStream == null) {
              //Logger debug statement
                return;
            }
            properties.load(inputStream);
        }
            catch(Exception e){
             //Logger error statement
              throw new RuntimeException(e);
            }
        }
    public String getProperty(String key){
     return properties.getProperty(key);
    }

}

