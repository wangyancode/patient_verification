//package com.dincher.project.scm.controller;
//
//
//import com.dincher.framework.web.controller.BaseController;
//import com.dincher.framework.web.domain.RequestQueryBody;
//import com.dincher.framework.web.domain.AjaxResult;
//import com.dincher.framework.web.domain.ResponseEntity;
//import com.dincher.framework.web.page.TableDataInfo;
//import com.dincher.framework.web.util.ResponseUtil;
//import io.swagger.annotations.*;
//import org.springframework.web.bind.annotation.*;
//import javax.annotation.Resource;
//import java.util.List;
//import com.dincher.project.scm.domain.vo.DeliveryOrderDetailsVO;
//import com.dincher.project.scm.service.DeliveryOrderDetailsService;
//
///**
// * 配送单明细(scm_delivery_order_details)控制层
// *
// * @author wangbin
// * @date 2023-12-06 15:36:58
// */
//@RestController
//@RequestMapping("/deliveryOrderDetails")
//@Api(tags = "配送单明细(scm_delivery_order_details)APi")
//public class DeliveryOrderDetailsController extends BaseController{
//
//    @Resource
//    private DeliveryOrderDetailsService deliveryOrderDetailsService;
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 15:36:58
//     *
//     */
//    @PostMapping(value = "/page")
//    @ApiOperation(value = "分页查询")
//    public TableDataInfo<List<DeliveryOrderDetailsVO>> page(@RequestBody RequestQueryBody<DeliveryOrderDetailsVO> requestQueryBody){
//        startPage(requestQueryBody);
//        List<DeliveryOrderDetailsVO> DeliveryOrderDetailsVOList = deliveryOrderDetailsService.selectDataList(requestQueryBody.getParam());
//        return getDataTable(DeliveryOrderDetailsVOList);
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 15:36:58
//     *
//     */
//    @PostMapping(value = "/list")
//    @ApiOperation(value = "列表查询（非分页）")
//    public ResponseEntity<List<DeliveryOrderDetailsVO>> list(@RequestBody DeliveryOrderDetailsVO deliveryOrderDetailsVO){
//        return ResponseUtil.success(deliveryOrderDetailsService.selectDataList(deliveryOrderDetailsVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 15:36:58
//     *
//     */
//    @GetMapping("{id}")
//    @ApiOperation(value = "通过主键查询单条数据")
//    public ResponseEntity<DeliveryOrderDetailsVO> selectOne(@PathVariable Integer id) {
//        return ResponseUtil.success(deliveryOrderDetailsService.getById(id));
//    }
//
//   /**
//     * @author wangbin
//     * @date 2023-12-06 15:36:58
//     *
//     */
//    @PostMapping(value ="/save")
//    @ApiOperation(value = "新增数据")
//    public ResponseEntity insert(@RequestBody DeliveryOrderDetailsVO deliveryOrderDetailsVO) {
//        return ResponseUtil.success(deliveryOrderDetailsService.save(deliveryOrderDetailsVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 15:36:58
//     *
//     */
//    @PutMapping(value ="/update")
//    @ApiOperation(value = "修改数据")
//    public ResponseEntity update(@RequestBody DeliveryOrderDetailsVO deliveryOrderDetailsVO) {
//        return ResponseUtil.success(deliveryOrderDetailsService.updateById(deliveryOrderDetailsVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 15:36:58
//     *
//     */
//    @DeleteMapping(value ="/delete")
//    @ApiOperation(value = "删除数据")
//    public ResponseEntity delete(@RequestBody List<Integer> ids) {
//        return ResponseUtil.success(deliveryOrderDetailsService.removeByIds(ids));
//    }
//}
//
