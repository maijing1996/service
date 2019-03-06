package com.mj.service;

import com.mj.model.Attachment;
import com.mj.repositories.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by mj on 2018-03-09.
 */
@Service
public class AttachmentService {

    @Autowired
    private AttachmentRepository attachmentRepository;

    public void saveAttachment(Attachment attachment) {
        attachmentRepository.save(attachment);
    }

    public void saveAttachmentByParams(String attachmentName, String attachmentUrl, Integer goodId) {
        attachmentRepository.saveAttachmentByParams(attachmentName, attachmentUrl, goodId);
    }
}
