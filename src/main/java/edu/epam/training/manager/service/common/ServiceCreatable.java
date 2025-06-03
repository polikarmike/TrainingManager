package edu.epam.training.manager.service.common;

public interface ServiceCreatable<T>{
    T create(T entity);
}
