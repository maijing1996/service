package com.gjj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gjj.enums.ErrorCode;
import com.gjj.enums.ErrorMessage;
import com.gjj.exceptions.BusinessException;
import com.gjj.exceptions.UnAuthorizedException;
import com.gjj.models.Attachment;
import com.gjj.models.Goods;
import com.gjj.services.AttachmentService;
import com.gjj.services.GoodsService;
import com.gjj.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Created by gjj on 2018-03-06.
 */
@RestController
class GoodsController {

    @Value("${spring.img.location}")
    private String location;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private AttachmentService attachmentService;

    @ResponseBody
    @GetMapping("/goods")
    public ResponseEntity<?> getGoods(@RequestParam(required = false, value = "id") Integer id,
                                      @RequestParam(value = "${spring.data.rest.page-param-name}", required = false, defaultValue = "${spring.data.rest.default-page-number}") Integer pageNum,
                                      @RequestParam(value = "${spring.data.rest.limit-param-name}", required = false, defaultValue = "${spring.data.rest.default-page-size}") Integer pageSize,
                                      @RequestParam(value = "${spring.data.rest.sort-param-name}", required = false, defaultValue = "id,desc") String sort) {
        Page<Goods> goods = goodsService.getGoods(id, pageNum, pageSize);
        return ResponseEntity.ok(goods);
    }

    @ResponseBody
    @PostMapping("/goods/publish")
    public ResponseEntity<?> publishGoods(@RequestBody JsonNode jsonNode,
                                          @RequestParam(name = "goodsImage", required = false) MultipartFile[] multipartFiles) throws Exception {
        Goods goods;
        Goods newGoods;
        try {
            goods = new ObjectMapper().readValue(jsonNode.traverse(), Goods.class);
            goods.setBulletinDate(new Date());
            newGoods = goodsService.saveGoods(goods);
            if (multipartFiles != null) {
                List urlList = uploadFile(multipartFiles, newGoods);
            }

        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        return ResponseEntity.ok(newGoods);
    }

//    @ResponseBody
//    @PostMapping("/goods/upload/images")
    public List uploadFile(MultipartFile[] multipartFiles, Goods goods) throws Exception {
        List urlList = new ArrayList();
        String url = null;
        try {
            for (int i = 0; i < multipartFiles.length; i++) {
                MultipartFile multipartFile = multipartFiles[i];
                if (multipartFile.isEmpty()) {
                    throw new BusinessException(ErrorCode.IMG_NOT_EMPTY, ErrorMessage.IMG_NOT_EMPTY);
                }
                String contentType = multipartFile.getContentType();
                if (!contentType.contains("")) {
                    throw new BusinessException(ErrorCode.IMG_FORMAT_ERROR, ErrorMessage.IMG_FORMAT_ERROR);
                }
                String root_fileName = multipartFile.getOriginalFilename();
//            logger.info("上传图片:name={},type={}", root_fileName, contentType);
//            User currentUser = userService.getCurrentUser();   //获取路径
//            User currentUser = userService.getCurrentUser();   //获取路径
//            String return_path = ImageUtil.getFilePath(currentUser);
                String filePath = location;
                String file_name = null;
                Attachment attachment = new Attachment();
                file_name = ImageUtil.saveImg(multipartFile, filePath);
                if ("".equals(file_name)) {
                    url = filePath + File.separator + file_name;
                    urlList.add(url);
                    attachment.setAttachmentName(file_name);
                    attachment.setAttachmentUrl(url);
                    attachment.setGoods(goods);
                    attachmentService.saveAttachment(attachment);
                }

            }
        } catch (IOException e) {
            goodsService.deleteGoods(goods);
            throw new BusinessException(ErrorCode.SAVE_IMG_ERROE, ErrorMessage.SAVE_IMG_ERROE);
        }
        return urlList;
    }

}
