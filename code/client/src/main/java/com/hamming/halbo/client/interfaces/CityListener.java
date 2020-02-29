package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.CityDto;

public interface ICityListener {

    public void cityAdded(CityDto city);
    public void cityDeleted(CityDto city);
}
