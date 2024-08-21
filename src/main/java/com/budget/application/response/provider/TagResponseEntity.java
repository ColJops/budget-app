package com.budget.application.response.provider;

import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

public class TagResponseEntity extends ResponseEntity<TagsList> {

    public TagResponseEntity(TagsList body, HttpStatusCode status) {
        super(body, status);
    }
}
