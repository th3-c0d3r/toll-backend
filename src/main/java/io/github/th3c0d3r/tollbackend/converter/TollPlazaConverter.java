package io.github.th3c0d3r.tollbackend.converter;

import io.github.th3c0d3r.tollbackend.dto.TollPlazaDto;
import io.github.th3c0d3r.tollbackend.entity.TollPlaza;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.print.attribute.standard.Destination;
import java.util.ArrayList;
import java.util.List;

@Service
public class TollPlazaConverter {

    @Autowired
    private ModelMapper modelMapper;

    public TollPlazaDto convertEntityToDto(TollPlaza tollPlaza){
        return modelMapper.map(tollPlaza, TollPlazaDto.class);
    }

    public List<TollPlazaDto> convertEntityToDto(List<TollPlaza> tollPlazaList){
        List<TollPlazaDto> tollPlazaDtoList = new ArrayList<>();
        for (TollPlaza tollPlaza : tollPlazaList){
            tollPlazaDtoList.add(convertEntityToDto(tollPlaza));
        }
        return tollPlazaDtoList;
    }

    public TollPlaza convertDtoToEntity(TollPlazaDto tollPlazaDto){
        TollPlaza tollPlaza = modelMapper.map(tollPlazaDto, TollPlaza.class);
        tollPlaza.setDeleted(false);
        return tollPlaza;
    }

    public List<TollPlaza> convertDtoToEntity(List<TollPlazaDto> tollPlazaDtoList){
        List<TollPlaza> tollPlazaList = new ArrayList<>();
        for (TollPlazaDto tollPlazaDto : tollPlazaDtoList){
            tollPlazaList.add(convertDtoToEntity(tollPlazaDto));
        }
        return tollPlazaList;
    }

    public void applyChanges(TollPlaza tollPlaza, TollPlazaDto tollPlazaDto){
        modelMapper.map(tollPlazaDto,tollPlaza);
    }

    public void applyChanges(TollPlaza tollPlazaFromDB, TollPlaza tollPlaza){
        modelMapper.map(tollPlaza,tollPlazaFromDB);
    }

}
