package com.mj.controllers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.PageInfo;
import com.mj.enums.ErrorCode;
import com.mj.enums.ErrorMessage;
import com.mj.exceptions.BusinessException;
import com.mj.exceptions.UnAuthorizedException;
import com.mj.model.Attachment;
import com.mj.model.Goods;
import com.mj.model.Subscribe;
import com.mj.model.User;
import com.mj.service.Impl.AttachmentServiceImpl;
import com.mj.service.Impl.GoodsServiceImpl;
import com.mj.service.Impl.SubscribeServiceImpl;
import com.mj.service.Impl.UserServiceImpl;
import com.mj.utils.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.*;

/**
 * Created by mj on 2018-03-06.
 */
@RestController
@ResponseBody
public class GoodsController {

    @Value("${spring.img.location}")
    private String location;

    @Value("${spring.img.url}")
    private String imagesUrl;

    @Autowired
    private GoodsServiceImpl goodsService;

    @Autowired
    private AttachmentServiceImpl attachmentService;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private SubscribeServiceImpl subscribeService;

    @GetMapping("/goods/{id}")
    public ResponseEntity<?> getGoodsById(@PathVariable Integer id) {

        Goods goods = goodsService.getGoodsById(id);
        User user = userService.getUser(goods.getUserId());
        List<Attachment> list = attachmentService.findAttachmentByGoodsId(id);
        goods.setAttachments(list);
        goods.setUser(user);
        return ResponseEntity.ok(goods);
    }


    @GetMapping("/goods")
    public ResponseEntity<?> getGoods(@RequestParam(required = false, value = "id") Integer id,
                                      @RequestParam(required = false, value = "goodsName") String goodsName,
                                      @RequestParam(required = false, value = "type") String type,
                                      @RequestParam(required = false, value = "customerId") Integer customerId,
                                      @RequestParam(required = false, value = "pageNum") Integer pageNum,
                                      @RequestParam(required = false, value = "pageSize") Integer pageSize) {
//        Sort sort1 = new Sort(Sort.Direction.DESC, "bulletinDate");
//        Pageable pageable = new PageRequest(pageNum, pageSize, sort1);
//        Page<Goods> goods = goodsService.getGoods(id, pageable);
        PageInfo<Goods> goods = goodsService.getGoods(id, goodsName, type, customerId, pageNum, pageSize);
        return ResponseEntity.ok(goods);
    }

    /**
     * 发布商品
     * @param uid
     * @param jsonNode
     * @return
     */
    @Transactional
    @PostMapping("/goods/publish/{uid}")
    public ResponseEntity<?> publishGoodsInfo(@PathVariable Integer uid, @RequestBody JsonNode jsonNode) {
        Goods goods;
        User user = userService.getUser(uid);
        try {
            goods = new ObjectMapper().readValue(jsonNode.traverse(), Goods.class);
            goods.setBulletinDate(new Date());
            goods.setUser(user);
//            newGoods = goodsService.saveGoods(goods);

        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        goods.setUserId(uid);
        goodsService.saveGoods(goods);
        Integer id = goods.getId();
        return ResponseEntity.ok(id);

    }


    @PostMapping("/goods/images/upload")
    @Transactional
    public ResponseEntity<?> publishGoodsImages(HttpServletRequest request, @RequestParam(name = "file", required = false) MultipartFile multipartFiles) throws Exception {
        Goods newGoods ;
        String goodsId = request.getParameter("goods");
        try {
            Integer id =  Integer.valueOf(goodsId);
            if (multipartFiles != null) {
                uploadFile(multipartFiles, id);
            }

        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        return ResponseEntity.ok(null);
    }

    public List uploadFile(MultipartFile multipartFiles, Integer goodsId) throws Exception {
        String url = null;
        try {
//            for (int i = 0; i < multipartFiles.length; i++) {
                MultipartFile multipartFile = multipartFiles;
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
                Attachment attachment = Attachment.builder().build();
                file_name = ImageUtil.saveImg(multipartFile, filePath);
                if (!"".equals(file_name)) {
                    url = imagesUrl + file_name;
//                    urlList.add(url);
                    attachmentService.saveAttachmentByParams(file_name, url, goodsId);
//                    attachment.setAttachmentName(file_name);
//                    attachment.setAttachmentUrl(url);
//                    attachment.setGoods(goods);
//                    attachmentService.saveAttachment(attachment);
                }
//            }
        } catch (IOException e) {
//            goodsService.deleteGoods(goods);
            throw new BusinessException(ErrorCode.SAVE_IMG_ERROE, ErrorMessage.SAVE_IMG_ERROE);
        }
        return null;
    }

    /**
     * 商品交易
     * @param jsonNode
     * @return
     */
    @PostMapping("/goods/goodsDeal")
    public ResponseEntity<?> goodsDeal(@RequestBody JsonNode jsonNode) {
        Map map = new HashMap<>();
        try {
            map = new ObjectMapper().readValue(jsonNode.traverse(), Map.class);
        } catch (IOException e) {
            throw new UnAuthorizedException(ErrorCode.JSON_TO_OBJECT_ERROR, ErrorMessage.ERROR_CHANGE_TYPE);
        }
        Integer goodsId = Integer.valueOf(map.get("goodsId").toString().trim());
        Integer customerId = Integer.valueOf(map.get("customerId").toString().trim());
        Goods goods = goodsService.getGoodsById(goodsId);
        goods.setCustomerId(customerId);
        goodsService.saveGoods(goods);
        return ResponseEntity.ok("保存成功！");
    }

    /**
     * 删除商品
     * @param id
     * @return
     */
    @PostMapping("/goods/delete/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable Integer id) {
        goodsService.deleteGoods(id);
        return ResponseEntity.ok(null);
    }

    /**
     * 获取订阅
     * @param id
     * @param goodsName
     * @param type
     * @return
     */
    @GetMapping("/goods/getFollow")
    public ResponseEntity<?> getFollowGoods(@RequestParam(required = false, value = "id") Integer id,
                                            @RequestParam(required = false, value = "goodsName") String goodsName,
                                            @RequestParam(required = false, value = "type") String type) {
        List list = new ArrayList();
        List<Subscribe> subscribeList = subscribeService.getSubscribe(id, "");
        for (Subscribe subscribe : subscribeList) {
            List<Goods> goodsList = goodsService.getAllGoodsByUser(subscribe.getId(),goodsName,type);
            list.addAll(goodsList);
        }
        return ResponseEntity.ok(list);

    }


}
