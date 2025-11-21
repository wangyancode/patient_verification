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
//import com.dincher.project.scm.domain.vo.HisDetailVO;
//import com.dincher.project.scm.service.HisDetailService;
//
///**
// * 药品的操作记录信息(scm_his_detail)控制层
// *
// * @author wangbin
// * @date 2023-12-19 14:41:17
// */
//@RestController
//@RequestMapping("/hisDetail")
//@Api(tags = "药品的操作记录信息(scm_his_detail)APi")
//public class HisDetailController extends BaseController{
//
//    @Resource
//    private HisDetailService hisDetailService;
//
//    /**
//     * @author wangbin
//     * @date 2023-12-19 14:41:17
//     *
//     */
//    @PostMapping(value = "/page")
//    @ApiOperation(value = "分页查询")
//    public TableDataInfo<List<HisDetailVO>> page(@RequestBody RequestQueryBody<HisDetailVO> requestQueryBody){
//        startPage(requestQueryBody);
//        List<HisDetailVO> HisDetailVOList = hisDetailService.selectDataList(requestQueryBody.getParam());
//        return getDataTable(HisDetailVOList);
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-19 14:41:17
//     *
//     */
//    @PostMapping(value = "/list")
//    @ApiOperation(value = "列表查询（非分页）")
//    public ResponseEntity<List<HisDetailVO>> list(@RequestBody HisDetailVO hisDetailVO){
//        return ResponseUtil.success(hisDetailService.selectDataList(hisDetailVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-19 14:41:17
//     *
//     */
//    @GetMapping("{id}")
//    @ApiOperation(value = "通过主键查询单条数据")
//    public ResponseEntity<HisDetailVO> selectOne(@PathVariable Integer id) {
//        return ResponseUtil.success(hisDetailService.getById(id));
//    }
//
//   /**
//     * @author wangbin
//     * @date 2023-12-19 14:41:17
//     *
//     */
//    @PostMapping(value ="/save")
//    @ApiOperation(value = "新增数据")
//    public ResponseEntity insert(@RequestBody HisDetailVO hisDetailVO) {
//        return ResponseUtil.success(hisDetailService.save(hisDetailVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-19 14:41:17
//     *
//     */
//    @PutMapping(value ="/update")
//    @ApiOperation(value = "修改数据")
//    public ResponseEntity update(@RequestBody HisDetailVO hisDetailVO) {
//        return ResponseUtil.success(hisDetailService.updateById(hisDetailVO));
//    }
//
//    /**
//     * @author wangbin
//     * @date 2023-12-19 14:41:17
//     *
//     */
//    @DeleteMapping(value ="/delete")
//    @ApiOperation(value = "删除数据")
//    public ResponseEntity delete(@RequestBody List<Integer> ids) {
//        return ResponseUtil.success(hisDetailService.removeByIds(ids));
//    }
//}
//
