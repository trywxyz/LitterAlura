package br.com.alura.litter.alura.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConvert implements IDataConvert{
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> tClass){
        try{
            return objectMapper.readValue(json,tClass);
        } catch (JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }
}
