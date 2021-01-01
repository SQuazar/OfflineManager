package net.flawe.offlinemanager.api;

import java.util.List;

public interface IStorage {
    void init();

    void add(String s);

    void remove(String s);

    void reload();

    boolean hasPlayer(String s);

    List<String> getList();

    List<String> getListForComplete(String[] args);
}
