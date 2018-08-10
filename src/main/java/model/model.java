package model;

import javafx.beans.property.SimpleStringProperty;

public class model {
    private final SimpleStringProperty Code;
    private final SimpleStringProperty Value;


    public model(String acode, String aname) {
        this.Code   = new SimpleStringProperty(acode);
        this.Value   = new SimpleStringProperty(aname);

    }

    public String getCode() {
        return Code.get();
    }

    public void  setCode(String aname) {
        Code.set(aname);
    }

    public String getValue() {
        return Value.get();
    }

    public void  setValue(String aname) {
        Value.set(aname);
    }



}