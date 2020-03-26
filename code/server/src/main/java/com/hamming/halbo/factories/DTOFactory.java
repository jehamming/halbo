package com.hamming.halbo.factories;

import com.hamming.halbo.model.*;
import com.hamming.halbo.model.dto.*;

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

    public ContinentDto getContinentDto(String worldID, Continent c) {
        ContinentDto dto;
        if (c.getSenator() == null ) {
            dto = new ContinentDto(worldID, c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), null);
        } else {
            dto = new ContinentDto(worldID, c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), c.getSenator().getId().toString());
        }
        return dto;
    }

    public CityDto getCityDto(String continentID, City c) {
        CityDto dto;
        String mayorId = null;
        if (c.getMayor() != null ) {
            mayorId = c.getMayor().getId().toString();
        }
        dto = new CityDto(continentID, c.getId().toString(), c.getName(), c.getCreator().getId().toString(), c.getOwner().getId().toString(), mayorId, c.getCityGrid().getSize());
        return dto;
    }

    public UserDto getUserDTO(User u) {
        UserDto dto = new UserDto(u.getId().toString(), u.getName(), u.getEmail());
        return dto;
    }

    public CityBaseplateDto getCityBaseplateDto(String cityId, CityBaseplate cb) {
        CityBaseplateDto dto = new CityBaseplateDto(cityId, cb.getBaseplate().getId().toString(), cb.getRow(), cb.getCol());
        return dto;
    }

    public UserLocationDto getUserLocationDTO(UserLocation loc) {
            String userId = loc.getUser().getId().toString();
            String worldId = loc.getWorld().getId().toString();
            String continentId = loc.getContinent().getId().toString();
            String cityId = loc.getCity().getId().toString();
            String baseplateId = loc.getBaseplate().getId().toString();
            UserLocationDto dto = new UserLocationDto(loc.getSequence(), userId, worldId, continentId, cityId, baseplateId, loc.getX(), loc.getY(), loc.getZ(), loc.getPitch(), loc.getYaw());
        return dto;
    }

    public BaseplateDto getBaseplateDto(Baseplate b) {
        BaseplateDto dto = new BaseplateDto(b.getId().toString(), b.getName(), b.getCreator().getId().toString(), b.getOwner().getId().toString(), b.getSize());
        return dto;
    }




}
