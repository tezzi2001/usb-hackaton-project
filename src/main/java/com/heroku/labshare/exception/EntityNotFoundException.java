package com.heroku.labshare.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Class entity, Object id) {
        super("entity " + entity.getSimpleName() + " with id " + id + " not found");
    }

    public EntityNotFoundException(Class entity) {
        super("entity " + entity.getSimpleName() + " not found");
    }
}
