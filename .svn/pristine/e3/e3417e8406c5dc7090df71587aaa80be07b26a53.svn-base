package com.dincher.project.scm.controller;


import com.dincher.framework.web.controller.BaseController;
import com.dincher.framework.web.domain.RequestQueryBody;
import com.dincher.framework.web.domain.AjaxResult;
import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.page.TableDataInfo;
import com.dincher.framework.web.util.ResponseUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import java.util.List;
import com.dincher.project.scm.domain.vo.MessageVO;
import com.dincher.project.scm.service.MessageService;

/**
 * 消息(scm_message)控制层
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@RestController
@RequestMapping("/message")
@Api(tags = "消息(scm_message)APi")
public class MessageController extends BaseController{

    @Resource
    private MessageService messageService;

    /** 
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     *
     */
    @PostMapping(value = "/page")
    @ApiOperation(value = "分页查询")
    public TableDataInfo<List<MessageVO>> page(@RequestBody RequestQueryBody<MessageVO> requestQueryBody){
        startPage(requestQueryBody);
        List<MessageVO> MessageVOList = messageService.allDataQueryPage(requestQueryBody.getParam());
        return getDataTable(MessageVOList);
    }
    
    /** 
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     *
     */
    @PostMapping(value = "/list")
    @ApiOperation(value = "列表查询（非分页）")
    public ResponseEntity<List<MessageVO>> list(@RequestBody MessageVO messageVO){
        return ResponseUtil.success(messageService.selectDataList(messageVO));
    }

    /**
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     *
     */
    @GetMapping("{id}")
    @ApiOperation(value = "通过主键查询单条数据")
    public ResponseEntity<MessageVO> selectOne(@PathVariable Integer id) {
        return ResponseUtil.success(messageService.getById(id));
    }

   /** 
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     *
     */
    @PostMapping(value ="/save")
    @ApiOperation(value = "新增数据")
    public ResponseEntity insert(@RequestBody MessageVO messageVO) {
        return ResponseUtil.success(messageService.save(messageVO));
    }
    
    /** 
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     *
     */
    @PutMapping(value ="/update")
    @ApiOperation(value = "修改数据")
    public ResponseEntity update(@RequestBody MessageVO messageVO) {
        return ResponseUtil.success(messageService.updateById(messageVO));
    }
    
    /** 
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     *
     */
    @DeleteMapping(value ="/delete")
    @ApiOperation(value = "删除数据")
    public ResponseEntity delete(@RequestBody List<Integer> ids) {
        return ResponseUtil.success(messageService.removeByIds(ids));
    }  
}

