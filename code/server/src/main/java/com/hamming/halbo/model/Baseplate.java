package com.hamming.halbo.model.dto.intern;

// A baseplate is like a lego baseplate. There are several types of plates, with grass or with road parts, etc.
public class Baseplate extends BasicObject   {

    private BaseplateType type ;

    public Baseplate(String id) {
        super(id);
    }

    public boolean addConstruction(String constructionID) {
        // TODO Implement - How to position Contructionss ?
        return true;
    }

    @Override
    public String toString() {
        return "Baseplate{" +
                "type=" + type +
                super.toString() +
                '}';
    }
}
