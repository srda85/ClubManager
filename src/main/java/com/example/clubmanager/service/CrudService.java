package com.example.clubmanager.service;

import java.util.List;

public interface CrudService <T, TID, TCREATE, TUPDATE>{

    // CREATE
    T create(TCREATE toInsert);

    // UPDATE
    T update(TID id, TUPDATE toUpdate);

    // READ
    T getOne(TID id);
    List<T> getAll();

    // DELETE
    T delete(TID id);
}
