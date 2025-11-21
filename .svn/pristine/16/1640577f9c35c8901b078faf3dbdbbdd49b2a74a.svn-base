package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dincher.project.scm.mapper.MessageMapper;
import com.dincher.project.scm.domain.vo.MessageVO;
import com.dincher.project.scm.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;
import org.springframework.transaction.annotation.Transactional;

/**
 * 消息(scm_message)业务实现类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Service
@Slf4j
@Transactional
public class MessageServiceImpl extends ServiceImpl<MessageMapper, MessageVO> implements MessageService {
    @Resource
    MessageMapper messageMapper;
   
     /** 
     * 
     * @description //TODO  查询数据
     * @author wangbin 
     * @date 2023-12-06 11:45:08
     * @param messageVO
     * @return List<Message>
     */
    @Override
    public List<MessageVO> selectDataList(MessageVO messageVO) {
        return messageMapper.selectDataList(messageVO);     
    }

    @Override
    public List<MessageVO> allDataQueryPage(MessageVO messageVO) {
        return messageMapper.allDataQueryPage(messageVO);

    }

}

