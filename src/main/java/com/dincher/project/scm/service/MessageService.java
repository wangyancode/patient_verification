package com.dincher.project.scm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dincher.project.scm.domain.vo.MessageVO;
import java.util.List;

/**
 * 消息(scm_message)业务接口
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
public interface MessageService extends IService<MessageVO> {

    public List<MessageVO> selectDataList(MessageVO messageVO);

    public List<MessageVO> allDataQueryPage(MessageVO messageVO);

}

