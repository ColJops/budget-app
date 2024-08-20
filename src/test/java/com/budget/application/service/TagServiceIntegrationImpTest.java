package com.budget.application.service;

import com.budget.application.entity.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest
class TagServiceIntegrationImpTest {

    @Autowired
    private TagService tagService;
    private TagRepository tagRepository;
    private String tagName;

    @BeforeEach
    void setUp() throws Exception {
        TestUtils testUtils = new TestUtils();
        tagName = testUtils.getRandomTextFromUUID();
        for (int i = 0; i<10; i++) {
            tagService.createTag(testUtils.getRandomTextFromUUID());
        }
    }

    @Test
    void getTagByName() {
        String retreivedTagName = tagService.getAllTags().get().getFirst().getName();
        Optional<Tag> retreivedTagByName = tagService.getTagByName(retreivedTagName);
        assertTrue(retreivedTagByName.isPresent());
        assertEquals(retreivedTagByName.get().getName(), retreivedTagName);
    }

    @Test
    void getAllTags() {
        assertTrue(!tagService.getAllTags().get().isEmpty());
        assertTrue(tagService.getAllTags().get().contains(tagName));
        assertTrue(tagService.getAllTags().get().size() >= 10);
    }

    @Test
    void deleteTag() {
        Long retreivedTagId = tagService.getAllTags().get().getFirst().getId();
        tagService.deleteTag(retreivedTagId);
        Optional<Tag> foundById = tagRepository.findById(retreivedTagId);
        assertFalse(foundById.isPresent());
    }

    @Test
    void testCreateTag() {
        Tag createdTag = tagService.createTag(tagName);
        assertEquals(createdTag.getName(), tagName);
    }
}