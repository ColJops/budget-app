package com.budget.application.response.provider;

import com.budget.application.entity.Tag;
import com.budget.application.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class TagResponseProvider {

    private TagService tagService;

    public TagResponseEntity getAllTags() {
        TagResponseEntity response = null;
        try {
            TagsList tagsList = new TagsList(tagService.getAllTags().get());
            response = new TagResponseEntity(tagsList, HttpStatus.OK);
        } catch (Exception e) {
            response = new TagResponseEntity(new TagsList(), HttpStatus.NO_CONTENT);
        }
        return response;
    }

    public TagResponseEntity createTag(String tagName) {
        TagResponseEntity response = null;
        try {
            Tag createdTag = tagService.createTag(tagName);
            response = new TagResponseEntity(new TagsList(Arrays.asList(createdTag)), HttpStatus.OK);
        } catch (Exception e) {
            response = new TagResponseEntity(new TagsList(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
        return response;
    }

    public TagResponseEntity deleteTag(Long tagId) {
        TagResponseEntity response = null;
        try {
            tagService.deleteTag(tagId);
            response = new TagResponseEntity(new TagsList(), HttpStatus.OK);
        } catch (Exception e) {
            response = new TagResponseEntity(new TagsList(), HttpStatus.BAD_REQUEST);
        }
        return response;
    }
}
