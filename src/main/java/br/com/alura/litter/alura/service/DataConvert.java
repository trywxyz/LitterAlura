package br.com.alura.litter.alura.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DataConvert implements IDataConvert {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public <T> T getData(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao desserializar JSON: " + e.getMessage(), e);
        }
    }
}
