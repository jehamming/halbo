package com.hamming.halbo.client.interfaces;

import com.hamming.halbo.model.dto.ContinentDto;

public interface ContinentListener {

    public void continentAdded(ContinentDto continent);
    public void continentDeleted(ContinentDto continent);
}
