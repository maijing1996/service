package com.gjj.repositories;

import com.gjj.models.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by gjj on 2018-03-09.
 */
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {
}
