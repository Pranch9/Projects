package ru.pranch.testtaskrest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.pranch.testtaskrest.model.Artifact;
import ru.pranch.testtaskrest.model.Comment;

@Repository
public interface CommentRepos extends JpaRepository<Comment, Long> {
    Page<Comment> findAllByArtifactId(Artifact artifactId, Pageable pageable);

    @Query("select c from Comment c where c.content like %?1%")
    Page<Comment> findAllByContent(String content, Pageable pageable);
}
