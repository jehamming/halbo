package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;

public interface ICityWindow {

    public void addCity(CityDto dto);

    public CityDto getSelectedCity();

    public void empty();
}
