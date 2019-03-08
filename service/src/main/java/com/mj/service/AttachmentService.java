package com.mj.service;

import com.mj.model.Attachment;

public interface AttachmentService {

    void saveAttachment(Attachment attachment);

    void saveAttachmentByParams(String attachmentName, String attachmentUrl, Integer goodId);
}
