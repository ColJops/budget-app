package com.budget.application.service;

import com.budget.application.entity.Tag;
import com.budget.application.repository.TagRepository;
import com.budget.application.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class TagServiceImpTest {

    @InjectMocks
    private TagServiceImp tagService;
    @Mock
    private TagRepository tagRepository;

    private String newTagName;



    @BeforeEach
    void setUp() throws Exception {
        TestUtils testUtils = new TestUtils();
        List<Tag> allGeneratedTestTags = testUtils.generateTestTags(10, true);
        Tag generatedTag = allGeneratedTestTags.getFirst();
        newTagName = generatedTag.getName();
        Mockito.when(tagRepository.save(Mockito.any(Tag.class))).thenReturn(generatedTag);
        Mockito.when(tagRepository.findAll()).thenReturn(allGeneratedTestTags);
        Mockito.when(tagRepository.findByName(newTagName)).thenReturn(List.of(generatedTag));
    }

    @Test
    void getTagByName() {
        Tag retreivedTag = tagService.getTagByName(newTagName).get();
        assertEquals(retreivedTag.getName(), newTagName);

    }

    @Test
    void getAllTags() {
        List<Tag> allTags = tagService.getAllTags().isPresent() ? tagService.getAllTags().get() : null;
        assertNotNull(allTags);
        assertFalse(allTags.isEmpty());
        assertEquals(allTags.size(), 10);
    }

    @Test
    void deleteTag() {
        Tag tag = tagService.getAllTags().get().getFirst();
        tagService.deleteTag(tag.getId());
        Optional<Tag> tagWhichShouldNotExist = tagRepository.findById(tag.getId());
        assertFalse(tagWhichShouldNotExist.isPresent());
    }

    @Test
    void createTag() {
       Tag createdTag = tagService.createTag(newTagName);
       assertNotNull(createdTag);
       assertEquals(createdTag.getName(), newTagName);
    }
}