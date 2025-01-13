package br.com.alura.litter.alura.service;

public interface IDataConvert {
    <T> T getData(String json, Class<T> tClass);
}
