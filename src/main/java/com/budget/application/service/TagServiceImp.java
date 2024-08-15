package com.budget.application.service;

import com.budget.application.entity.Tag;
import com.budget.application.repository.TagRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImp implements TagService {

    private TagRepository tagRepository;

    public Optional<Tag> getTagByName(String tagName) {
        Tag foundTag = null;
        List<Tag> foundByName = tagRepository.findByName(tagName);
        if (!foundByName.isEmpty()) {
            foundTag = foundByName.get(0);
        }
        return Optional.of(foundTag);
    }

    public Optional<List<Tag>> getAllTags() {
        List<Tag> tags = tagRepository.findAll();
        return Optional.of(tags);
    }

    public void deleteTag(Long id) {
        tagRepository.deleteById(id);

    }

    public Tag createTag(String tagName) {
         return tagRepository.save(new Tag(tagName));
    }
}
