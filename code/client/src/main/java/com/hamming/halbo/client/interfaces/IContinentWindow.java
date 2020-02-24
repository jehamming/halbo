package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.ContinentDto;

public interface IContinentWindow {

    public void addContinent(ContinentDto dto);

    public ContinentDto getSelectedContinent();

    public void empty();
}
