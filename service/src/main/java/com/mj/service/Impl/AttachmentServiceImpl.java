package com.mj.service.Impl;

import com.mj.mapper.AttachmentMapper;
import com.mj.model.Attachment;
import com.mj.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by mj on 2018-03-09.
 */
@Service
public class AttachmentServiceImpl implements AttachmentService {

    /*@Autowired
    private AttachmentRepository attachmentRepository;*/

    @Autowired
    private AttachmentMapper attachmentMapper;

    public void saveAttachment(Attachment attachment) {
        attachmentMapper.insert(attachment);
    }

    public void saveAttachmentByParams(String attachmentName, String attachmentUrl, Integer goodId) {
        Attachment attachment = Attachment.builder()
                .attachmentName(attachmentName)
                .attachmentUrl(attachmentUrl)
                .goodsId(goodId)
                .build();
        attachmentMapper.insert(attachment);
//        attachmentRepository.saveAttachmentByParams(attachmentName, attachmentUrl, goodId);
    }

    public List<Attachment> findAttachmentByGoodsId(Integer id) {
        Attachment attachment = new Attachment();
        attachment.setGoodsId(id);
        return attachmentMapper.select(attachment);
    }
}
