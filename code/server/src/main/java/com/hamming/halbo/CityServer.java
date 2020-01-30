package com.hamming.halbo;

import com.hamming.halbo.datamodel.intern.City;
import com.hamming.halbo.datamodel.intern.User;
import com.hamming.halbo.factories.CityFactory;

import java.util.ArrayList;
import java.util.List;

// This Class is the server that serves a City, meaning a collection of Baseplates with Constructions on them
public class CityServer implements Runnable {

    private boolean running = true;
    private City city;

    public CityServer( City city ) {
        this.city = city;
    }

    @Override
    public void run() {
        while (running) {

        }
    }

    public void stop() {
        running = false;
    }
}
