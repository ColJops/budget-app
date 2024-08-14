package com.budget.application.service;

import com.budget.application.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagService {

    public Optional<Tag> getTagByName(String tagName);
    public Optional<List<Tag>> getAllTags();
    public void deleteTag(Long id);
    public Tag createTag(String tagName);
}
