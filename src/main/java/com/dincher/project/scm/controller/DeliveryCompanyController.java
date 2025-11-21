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
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.*;
//import javax.annotation.Resource;
//import java.util.List;
//import com.dincher.project.scm.domain.vo.DeliveryCompanyVO;
//import com.dincher.project.scm.service.DeliveryCompanyService;
//
///**
// * 配送单位(scm_delivery_company)控制层
// *
// * @author wangbin
// * @date 2023-12-06 11:45:06
// */
//@RestController
//@RequestMapping("/deliveryCompany")
//@Api(tags = "配送单位(scm_delivery_company)APi")
//public class DeliveryCompanyController extends BaseController{
//
//    @Resource
//    private DeliveryCompanyService deliveryCompanyService;
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 11:45:06
//     *
//     */
//    @PostMapping(value = "/page")
//    @ApiOperation(value = "分页查询")
//    public TableDataInfo<List<DeliveryCompanyVO>> page(@RequestBody RequestQueryBody<DeliveryCompanyVO> requestQueryBody){
//        startPage(requestQueryBody);
//        List<DeliveryCompanyVO> DeliveryCompanyVOList = deliveryCompanyService.selectDataList(requestQueryBody.getParam());
//        return getDataTable(DeliveryCompanyVOList);
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 11:45:06
//     *
//     */
//    @PostMapping(value = "/list")
//    @ApiOperation(value = "列表查询（非分页）")
//    public ResponseEntity<List<DeliveryCompanyVO>> list(@RequestBody DeliveryCompanyVO deliveryCompanyVO){
//        return ResponseUtil.success(deliveryCompanyService.selectDataList(deliveryCompanyVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 11:45:06
//     *
//     */
//    @GetMapping("{id}")
//    @ApiOperation(value = "通过主键查询单条数据")
//    public ResponseEntity<DeliveryCompanyVO> selectOne(@PathVariable Integer id) {
//        return ResponseUtil.success(deliveryCompanyService.getById(id));
//    }
//
//   /**
//     * @author wangbin
//     * @date 2023-12-06 11:45:06
//     *
//     */
//    @PostMapping(value ="/save")
//    @ApiOperation(value = "新增数据")
//    public ResponseEntity insert(@RequestBody DeliveryCompanyVO deliveryCompanyVO) {
//        return ResponseUtil.success(deliveryCompanyService.saveDeliveryCompanyVO(deliveryCompanyVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 11:45:06
//     *
//     */
//    @PutMapping(value ="/update")
//    @ApiOperation(value = "修改数据")
//    public ResponseEntity update(@RequestBody DeliveryCompanyVO deliveryCompanyVO) {
//        return ResponseUtil.success(deliveryCompanyService.updateById(deliveryCompanyVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-06 11:45:06
//     *
//     */
//    @DeleteMapping(value ="/delete")
//    @ApiOperation(value = "删除数据")
//    public ResponseEntity delete(@RequestBody List<Integer> ids) {
//        return ResponseUtil.success(deliveryCompanyService.removeByIds(ids));
//    }
//
//    @PostMapping(value ="/generateToken")
//    @ApiOperation(value = "生成token")
//    public ResponseEntity<DeliveryCompanyVO> generateToken(@ApiParam(value = "生成token参数", required = true)@RequestBody DeliveryCompanyVO deliveryCompanyVO) {
//        return ResponseUtil.success(deliveryCompanyService.generateToken(deliveryCompanyVO));
//    }
//
//    @PostMapping(value ="/seeToken")
//    @ApiOperation(value = "查看token")
//    public ResponseEntity<DeliveryCompanyVO> seeToken(@ApiParam(value = "查看token参数", required = true)@RequestBody DeliveryCompanyVO deliveryCompanyVO) {
//        return ResponseUtil.success(deliveryCompanyService.seeToken(deliveryCompanyVO));
//    }
//
//    @PostMapping(value ="/generateTokenAgain")
//    @ApiOperation(value = "重新生成token")
//    public ResponseEntity<DeliveryCompanyVO> generateTokenAgain(@ApiParam(value = "重新生成token参数", required = true)@RequestBody DeliveryCompanyVO deliveryCompanyVO) {
//        return ResponseUtil.success(deliveryCompanyService.generateTokenAgain(deliveryCompanyVO));
//    }
//
//
//}
//
