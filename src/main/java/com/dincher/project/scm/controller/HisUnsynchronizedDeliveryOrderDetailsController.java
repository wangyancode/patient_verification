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
//import com.dincher.project.scm.domain.vo.HisUnsynchronizedDeliveryOrderDetailsVO;
//import com.dincher.project.scm.service.HisUnsynchronizedDeliveryOrderDetailsService;
//
///**
// * 配送单明细未同步到his的暂存表(scm_his_unsynchronized_delivery_order_details)控制层
// *
// * @author wangbin
// * @date 2024-05-07 09:15:39
// */
//@RestController
//@RequestMapping("/hisUnsynchronizedDeliveryOrderDetails")
//@Api(tags = "配送单明细未同步到his的暂存表(scm_his_unsynchronized_delivery_order_details)APi")
//public class HisUnsynchronizedDeliveryOrderDetailsController extends BaseController{
//
//    @Resource
//    private HisUnsynchronizedDeliveryOrderDetailsService hisUnsynchronizedDeliveryOrderDetailsService;
//
//    /**
//     * @author wangbin
//     * @date 2024-05-07 09:15:39
//     *
//     */
//    @PostMapping(value = "/page")
//    @ApiOperation(value = "分页查询")
//    public TableDataInfo<List<HisUnsynchronizedDeliveryOrderDetailsVO>> page(@RequestBody RequestQueryBody<HisUnsynchronizedDeliveryOrderDetailsVO> requestQueryBody){
//        startPage(requestQueryBody);
//        List<HisUnsynchronizedDeliveryOrderDetailsVO> HisUnsynchronizedDeliveryOrderDetailsVOList = hisUnsynchronizedDeliveryOrderDetailsService.selectDataList(requestQueryBody.getParam());
//        return getDataTable(HisUnsynchronizedDeliveryOrderDetailsVOList);
//    }
//
//    /**
//     * @author wangbin
//     * @date 2024-05-07 09:15:39
//     *
//     */
//    @PostMapping(value = "/list")
//    @ApiOperation(value = "列表查询（非分页）")
//    public ResponseEntity<List<HisUnsynchronizedDeliveryOrderDetailsVO>> list(@RequestBody HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVO){
//        return ResponseUtil.success(hisUnsynchronizedDeliveryOrderDetailsService.selectDataList(hisUnsynchronizedDeliveryOrderDetailsVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2024-05-07 09:15:39
//     *
//     */
//    @GetMapping("{id}")
//    @ApiOperation(value = "通过主键查询单条数据")
//    public ResponseEntity<HisUnsynchronizedDeliveryOrderDetailsVO> selectOne(@PathVariable Integer id) {
//        return ResponseUtil.success(hisUnsynchronizedDeliveryOrderDetailsService.getById(id));
//    }
//
//   /**
//     * @author wangbin
//     * @date 2024-05-07 09:15:39
//     *
//     */
//    @PostMapping(value ="/save")
//    @ApiOperation(value = "新增数据")
//    public ResponseEntity insert(@RequestBody HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVO) {
//        return ResponseUtil.success(hisUnsynchronizedDeliveryOrderDetailsService.save(hisUnsynchronizedDeliveryOrderDetailsVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2024-05-07 09:15:39
//     *
//     */
//    @PutMapping(value ="/update")
//    @ApiOperation(value = "修改数据")
//    public ResponseEntity update(@RequestBody HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVO) {
//        return ResponseUtil.success(hisUnsynchronizedDeliveryOrderDetailsService.updateById(hisUnsynchronizedDeliveryOrderDetailsVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2024-05-07 09:15:39
//     *
//     */
//    @DeleteMapping(value ="/delete")
//    @ApiOperation(value = "删除数据")
//    public ResponseEntity delete(@RequestBody List<Integer> ids) {
//        return ResponseUtil.success(hisUnsynchronizedDeliveryOrderDetailsService.removeByIds(ids));
//    }
//}
//
