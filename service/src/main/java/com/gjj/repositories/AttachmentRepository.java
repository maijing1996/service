package com.gjj.repositories;

import com.gjj.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by gjj on 2018-03-09.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
