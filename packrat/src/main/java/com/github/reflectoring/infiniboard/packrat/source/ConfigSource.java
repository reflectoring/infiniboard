package com.github.reflectoring.infiniboard.packrat.source;

import javafx.beans.property.StringProperty;
import org.springframework.data.annotation.Id;

/**
 * Rootclass of all possible and updateable sources of configs
 */
public abstract class ConfigSource {

    @Id
    private int id;

    private long seq;

    private String name;

    private int updateInterval;

    public ConfigSource(int updateInterval){
        this.updateInterval = updateInterval;
    }

    public int getId() {
        return id;
    }

    public long getSeq() {
        return seq;
    }

    public String getName(){
        return String.valueOf(id);
    }

    public int getUpdateInterval(){
        return this.updateInterval;
    }

    public void setUpdateInterval(int updateInterval) {
        this.updateInterval = updateInterval;
    }


}
