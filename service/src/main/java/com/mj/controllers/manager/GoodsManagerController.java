package com.mj.controllers.manager;

import com.github.pagehelper.PageInfo;
import com.mj.model.Goods;
import com.mj.model.request.GoodRequest;
import com.mj.model.response.LayuiResponse;
import com.mj.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@ResponseBody
@RestController
@RequestMapping("/public/manager/good")
public class GoodsManagerController {

    @Autowired
    private GoodsService goodsService;

    /**
     * 所有的商品
     * @param goodRequest
     * @return
     */
    @PostMapping("/info")
    public LayuiResponse listGood(@RequestBody  GoodRequest goodRequest) {
        LayuiResponse layuiResponse = new LayuiResponse();
        PageInfo<Goods> pageInfo = goodsService.listInfo(goodRequest);
        return layuiResponse.setSuccess("查询成功！", pageInfo.getList(), pageInfo.getTotal());
    }

    /**
     * 单个删除
     * @param goodRequest
     * @return
     */
    @PostMapping("/delete")
    public LayuiResponse delete(@RequestBody GoodRequest goodRequest) {
        LayuiResponse layuiResponse = new LayuiResponse();
        goodsService.deleteGoods(goodRequest.getId());
        return layuiResponse.setSuccess("刪除成功！", null, 0);
    }

    /**
     * 批量删除
     * @param goodRequest
     * @return
     */
    @PostMapping("/deletes")
    public LayuiResponse deletes(@RequestBody GoodRequest goodRequest) {
        LayuiResponse layuiResponse = new LayuiResponse();
        goodsService.deletes(goodRequest.getIds());
        return layuiResponse.setSuccess("刪除成功！", null, 0);
    }
}
