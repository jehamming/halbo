package com.hamming.halbo.factories;

// This is a factory to Load, Store and convert LDRAW format files.
public class LDrawFactory {


    private static LDrawFactory instance;

    private LDrawFactory() {
        initialize();
    };

    private void initialize() {
        // TODO Implement reading the complete.zip?
    }

    public static LDrawFactory getInstance() {
        if ( instance == null ) {
            instance = new LDrawFactory();
        }
        return instance;
    }
}
