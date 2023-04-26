package dao;

import entity.Book;

import java.util.List;
import java.util.Optional;

public interface Dao<K,V>{
    List<V> findAll();
    Optional<V> findById(K id);
}
