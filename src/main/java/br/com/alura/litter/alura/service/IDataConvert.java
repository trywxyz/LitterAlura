package br.com.alura.litter.alura.service;

public interface IDataConvert {
    <T> T  obterDados(String json, Class<T> classe);
}
