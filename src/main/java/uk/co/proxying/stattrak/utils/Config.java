package uk.co.proxying.stattrak.utils;


import uk.co.proxying.stattrak.StatTrak;

/**
 * Created by Kieran Quigley (Proxying) on 30-May-16.
 */
public class Config<T> {

    T t;
    private String property;

    public Config(String property) {
        this.property = property;
    }

    public String getProperty() {
        return property;
    }

    @SuppressWarnings("unchecked")
    public T getValue() {
        return (T) StatTrak.getInstance().getConfig().get(property);
    }
}
