package com.hamming.halbo.factories;

import com.hamming.halbo.model.Baseplate;
import com.hamming.halbo.model.City;
import com.hamming.halbo.model.Continent;
import com.hamming.halbo.model.World;
import com.hamming.halbo.model.dto.BaseplateDto;
import com.hamming.halbo.model.dto.CityDto;
import com.hamming.halbo.model.dto.ContinentDto;
import com.hamming.halbo.model.dto.WorldDto;

import java.util.ArrayList;
import java.util.List;

public class DTOFactory {

    private List<Continent> dtos;
    private static DTOFactory instance;

    private DTOFactory() {
    };


    public static DTOFactory getInstance() {
        if ( instance == null ) {
            instance = new DTOFactory();
        }
        return instance;
    }

    public  WorldDto getWorldDto(World w) {
        WorldDto dto = new WorldDto(w.getId().toString(), w.getName(), w.getCreator().getId().toString(), w.getOwner().getId().toString());
        return dto;
    }

    public ContinentDto getContinentDto(Continent c) {
        ContinentDto dto;
        if (c.getSenator() == null ) {
            dto = new ContinentDto(c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), null);
        } else {
            dto = new ContinentDto(c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), c.getSenator().getId().toString());
        }
        return dto;
    }

    public CityDto getCityDto(City c) {
        CityDto dto;
        if (c.getMayor() == null ) {
            dto = new CityDto(c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), null);
        } else {
            dto = new CityDto(c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), c.getMayor().getId().toString());
        }
        return dto;
    }


    public BaseplateDto getBaseplateDto(Baseplate b) {
        BaseplateDto dto = new BaseplateDto(b.getId().toString(), b.getName(), b.getCreator().getId().toString(), b.getOwner().getId().toString());
        return dto;
    }




}
