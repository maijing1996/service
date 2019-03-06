package com.mj.repositories;

import com.mj.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Created by mj on 2018-03-09.
 */
@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Integer> {

    @Modifying
    @Query(value="insert into attachment(attachment_name, attachment_url,goods_id) values (?1,?2,?3) ", nativeQuery = true)
    void saveAttachmentByParams (String attachmentName, String attachmentUrl, Integer goodsId);
}
