package io.auto.utilis;

public enum Browser {

    CHROME("chrome"),
    EDGE("egde"),
        FIREFOX("firefox");

    private final String browserName;

    Browser(String browserName){
        this.browserName = browserName;
    }

    public String getBrowserName(){
        return browserName;
    }
}
