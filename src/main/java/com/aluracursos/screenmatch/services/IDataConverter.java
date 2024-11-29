package com.aluracursos.screenmatch.services;

public interface IDataConverter {
    <T> T getData(String json, Class<T> clase);
}
