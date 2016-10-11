package org.menina.utils;

/**
 * Created by meninaChimp on 2016/10/10 0010.
 */
public enum TimeUnit {

    SECOND("SECOND"),
    MINUTE("MINUTE"),
    HOUR("HOUR");

    private String value;

    TimeUnit(String value){
        this.value = value;
    }

    public String getValue(){
        return this.value;
    }

    public int parseToSeconds(int time){
        if(this.value.equals(TimeUnit.HOUR.getValue())){
            return time * 60 * 60;
        }

        if(this.value.equals(TimeUnit.MINUTE.getValue())){
            return time * 60;
        }

        return time;
    }
}
