package com.dincher.project.scm.service;


import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dincher.common.utils.StringUtils;
import com.dincher.common.utils.XmlUtil;
import com.dincher.framework.web.domain.ResponseEntity;
import com.dincher.framework.web.util.ResponseUtil;
import com.dincher.project.scm.domain.PatientRecords;
import com.dincher.project.scm.domain.vo.ConfirmVO;
import com.dincher.project.scm.mapper.PatientRecordsMapper;
import com.dincher.project.system.domain.entity.DictData;
import com.dincher.project.system.domain.entity.User;
import com.dincher.project.system.domain.vo.UserRegionVO;
import com.dincher.project.system.domain.vo.UserVO;
import com.dincher.project.system.mapper.DictDataMapper;
import com.dincher.project.system.mapper.UserRegionMapper;
import com.dincher.project.system.service.UserService;
import com.ewell.sdk.business.EwellServiceTool;
import com.ewell.sdk.domain.MessageEntity;
import com.ewell.sdk.exception.SDKException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Slf4j
@Service
@Transactional
public class PatientRecordsService extends BaseService<PatientRecords> {

    @Autowired
    private PatientRecordsMapper patientRecordsMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRegionMapper userRegionMapper;

    @Autowired
    private DictDataMapper dictDataMapper;


    //查询队列管理器名称
    @Value("${his.queueManageName}")
    private String queueManageName;

    //查询数据通道
    @Value("${his.channel}")
    private String channel;


    //核对确认队列名
    @Value("${his.checkName}")
    private String checkName;

    //数据通道
    @Value("${his.checkChannel}")
    private String checkChannel;


    //分页查询
    public Page<PatientRecords> selectPageList(PatientRecords patientRecords) {
        Page page = new Page(patientRecords.getPageNo(), patientRecords.getPageSize());

        QueryWrapper<PatientRecords> queryWrapper = new QueryWrapper<>();

        //类型 1管理员 2普通技师 3科主任
        //权限 普通技师只能看自己的  科主任可以看本科室的  管理员可以看所有
        UserVO userVO = userService.getPersonalInfo();
        if (2 == userVO.getType()) {
            queryWrapper.eq("pat_verify_name", userVO.getUserName());
        } else if (3 == userVO.getType()) {
            QueryWrapper<UserRegionVO> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("user_id", userVO.getUserId());
            queryWrapper2.eq("delete_flag", "0");
            List<UserRegionVO> userRegionVOList = userRegionMapper.selectList(queryWrapper2);
            List<String> stringList = userRegionVOList.stream()
                    .map(UserRegionVO::getRegionValue)
                    .collect(Collectors.toList());
            queryWrapper.in("ckdpt_code", stringList);
        }

        if (StringUtils.isNotEmpty(patientRecords.getPat_verify_name())) {
            queryWrapper.like("pat_verify_name", patientRecords.getPat_verify_name());
        }




        if (StringUtils.isNotEmpty(patientRecords.getPersonName())) {
            queryWrapper.like("personName", patientRecords.getPersonName());
        }

        if (StringUtils.isNotEmpty(patientRecords.getNumber())) {
            queryWrapper.eq("number", patientRecords.getNumber());
        }


        if (StringUtils.isNotEmpty(patientRecords.getCkdpt_code())) {
            queryWrapper.eq("ckdpt_code", patientRecords.getCkdpt_code());
        }


        if (StringUtils.isNotEmpty(patientRecords.getPat_verify_code())) {
            queryWrapper.eq("pat_verify_code", patientRecords.getPat_verify_code());
        }

        if (StringUtils.isNotEmpty(patientRecords.getRpc_code())) {
            queryWrapper.like("rpc_code", patientRecords.getRpc_code());
        }

        if (StringUtils.isNotEmpty(patientRecords.getProj_name())) {
            queryWrapper.like("proj_name", patientRecords.getProj_name());
        }

        if (StringUtils.isNotEmpty(patientRecords.getType())) {
            queryWrapper.eq("type", patientRecords.getType());
        }

        if (StringUtils.isNotEmpty(patientRecords.getCkpt_name())) {
            queryWrapper.like("ckpt_name", patientRecords.getCkpt_name());
        }


        //核对时间
        if (StringUtils.isNotEmpty(patientRecords.getStartTime())) {
            queryWrapper.ge("DATE_FORMAT(pat_verify_time, '%Y-%m-%d')", patientRecords.getStartTime());
        }
        //核对时间
        if (StringUtils.isNotEmpty(patientRecords.getEndTime())) {
            queryWrapper.le("DATE_FORMAT(pat_verify_time, '%Y-%m-%d')", patientRecords.getEndTime());
        }
        return patientRecordsMapper.selectPage(page, queryWrapper);
    }


    /**
     * 调用第三方查询接口
     *
     * @author wzn
     * @date 2025/06/21 10:45
     */
    public List<Map<String, Object>> encapsulateData(String bar_code, String ckdpt_code) throws Exception {

        if (!bar_code.startsWith("A7") && !bar_code.startsWith("B7")) {
            log.error("\n" + "条形码不正确" + bar_code);
            return new ArrayList<>();
        }

        EwellServiceTool ewellServiceTool = new EwellServiceTool();
        String body = this.querryBody(bar_code, ckdpt_code);
        MessageEntity messageEntity = ewellServiceTool.composePutAndGetMsg(queueManageName, channel, 5000, body);
        String msgStr = messageEntity.getMsg();
        log.info("\n" + "messageEntity.getMsg()" + messageEntity.getMsg());

        log.info("\n" + JSONObject.toJSONString(messageEntity));
        //处理消息
        msgStr = msgStr.replace("<![CDATA[<msg><body><row action=\"select\">", " ");
        msgStr = msgStr.replace("</row></body></msg>]]>", " ");
        JSONObject jsonObject = XmlUtil.xmlToJson(msgStr);

        //封装数据
        List<Map<String, Object>> mapList = new ArrayList<>();
        if (null != jsonObject.getJSONArray("MsgInfo")) {
            JSONArray msgInfo = jsonObject.getJSONArray("MsgInfo").getJSONObject(0).getJSONArray("Msg");

            List<PatientRecords> list1 = new ArrayList<>();
            List<PatientRecords> list2 = new ArrayList<>();

            String name = "";
            String sex = "";
            log.info("\n" + "msgInfo" + msgInfo);
            if (null != msgInfo && msgInfo.size() > 0) {
                for (int i = 0; i < msgInfo.size(); i++) {

                    JSONObject jsonObjectData = msgInfo.getJSONObject(i);

                    if (!ckdpt_code.equals(jsonObjectData.getString("EXECUT_DEPT_CODE"))) {
                        continue;
                    }

                    //状态筛选 ,新平台检查状态1提交申清2微销申请3预约4取消预约5检查登记6取消登记7检查执行&图像完成 9报告完成10报告审核1报到 12取消报到13日缴费 14日退费 15报道发布
                    //只需要 5
                    if(StringUtils.isNotEmpty(jsonObjectData.getString("RISJCZT"))){
                        if (!"5".equals(jsonObjectData.getString("RISJCZT"))
                        ) {
                            continue;
                        }
                    }else {
                        log.error("\n" + "列表状态是空" );
                        continue;
                    }



                    PatientRecords patientRecords = new PatientRecords();
                    patientRecords.setRpt_no(jsonObjectData.getString("ELECTR_REQUISITION_NO"));

                    QueryWrapper<PatientRecords> qw = new QueryWrapper<>();
                    qw.eq("rpt_no", jsonObjectData.getString("ELECTR_REQUISITION_NO"));
                    qw.eq("isDel", "0");
                    PatientRecords records = patientRecordsMapper.selectOne(qw);
                    if (null == records) {
                        patientRecords.setAgainConfirm("0");
                    } else {
                        patientRecords.setAgainConfirm("1");
                    }

                    if (StringUtils.isNotEmpty(jsonObjectData.getString("OUTHOSP_NO"))) {
                        patientRecords.setNumber(jsonObjectData.getString("OUTHOSP_NO"));
                        patientRecords.setType("1");
                    }

                    if (StringUtils.isNotEmpty(jsonObjectData.getString("INHOSP_NO"))) {
                        patientRecords.setNumber(jsonObjectData.getString("INHOSP_NO"));
                        patientRecords.setType("3");
                    }

                    patientRecords.setBar_code(bar_code);

                    name = jsonObjectData.getString("PAT_NAME");
                    sex = jsonObjectData.getString("PHYSI_SEX_NAME");
                    patientRecords.setPersonName(jsonObjectData.getString("PAT_NAME"));
                    patientRecords.setSex(jsonObjectData.getString("PHYSI_SEX_NAME"));
                    patientRecords.setAge(jsonObjectData.getString("AGE"));
                    patientRecords.setRpt_dept(jsonObjectData.getString("APPLY_DEPT_NAME"));
                    patientRecords.setRpc_code(jsonObjectData.getString("APPLY_DEPT_CODE"));
                    patientRecords.setProj_name(jsonObjectData.getString("EXAM_APPLY_ITEM_NAME"));

                    patientRecords.setCkpt_name(jsonObjectData.getString("EXAM_PART_DESCR"));
                    patientRecords.setCkdpt_code(jsonObjectData.getString("EXECUT_DEPT_CODE"));
                    patientRecords.setCkdpt_name(jsonObjectData.getString("EXECUT_DEPT_NAME"));
                    patientRecords.setPat_verify_dept_code(jsonObjectData.getString("PAT_VERIFY_DEPT_CODE"));
                    patientRecords.setPat_verify_dept_name(jsonObjectData.getString("PAT_VERIFY_DEPT_NAME"));
                    patientRecords.setPat_verify_code(jsonObjectData.getString("PAT_VERIFY_CODE"));
                    patientRecords.setPat_verify_name(jsonObjectData.getString("PAT_VERIFY_NAME"));
                    if(StringUtils.isNotEmpty(jsonObjectData.getString("PAT_VERIFY_TIME"))){


                        // 定义格式化器
                        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

                        // 解析为 LocalDateTime
                        LocalDateTime localDateTime = LocalDateTime.parse(jsonObjectData.getString("PAT_VERIFY_TIME"), formatter);

                        // 转换为 Date 类型
                        Date date = Date.from(localDateTime.atZone(java.time.ZoneId.systemDefault()).toInstant()) ;
                        patientRecords.setPat_verify_time(date);
                    }

                    //todo //检查地点缺失
                    patientRecords.setPos_name("");
                    patientRecords.setCost(jsonObjectData.getString("TOTAL_MONEY"));
                    if (StringUtils.isNotEmpty(jsonObjectData.getString("RESERVE_EXAM_DATE"))) {
                        patientRecords.setAppoint_time(DateUtil.parseDate(jsonObjectData.getString("RESERVE_EXAM_DATE")));
                    }
                    patientRecords.setApp_remark(jsonObjectData.getString("NOTE"));

                    if ("1".equals(patientRecords.getType())) {
                        list1.add(patientRecords);
                    } else if ("3".equals(patientRecords.getType())) {
                        list2.add(patientRecords);
                    }
                }
            }


            if (!CollectionUtils.isEmpty(list1)) {
                Map<String, Object> map1 = new HashMap<>();
                //门诊
                map1.put("type", "1");
                map1.put("name", name);
                map1.put("sex", sex);
                map1.put("list", list1);
                map1.put("number", list1.get(0).getNumber());
                mapList.add(map1);
            }


            if (!CollectionUtils.isEmpty(list2)) {
                //住院
                Map<String, Object> map3 = new HashMap<>();
                map3.put("type", "3");
                map3.put("name", name);
                map3.put("sex", sex);
                map3.put("list", list2);
                map3.put("number", list2.get(0).getNumber());
                mapList.add(map3);
            }

        }


        return mapList;
    }

    public static void main(String[] args) {

        String date = "2025-07-09T10:45:28" ;



        String msgStr = "<ESBEntry><MessageHeader><Fid>BS35004</Fid><OrderNo>BS35004S84001</OrderNo><SourceSysCode>S00</SourceSysCode><TargetSysCode>S84</TargetSysCode><HospCode>GH01</HospCode><MsgDate>2025-07-01 14:34:58.177</MsgDate></MessageHeader><RetInfo><RetCode>1</RetCode><RetCon>query success</RetCon></RetInfo><ResponseOption><currentPage>1</currentPage><totalPage>1</totalPage></ResponseOption><MsgInfo><Msg><![CDATA[<msg><body><row action=\"select\"><PAT_VERIFY_DEPT_CODE/><PAT_VERIFY_DEPT_NAME/><PAT_VERIFY_CODE/><PAT_VERIFY_NAME/><VISIT_CARD_NO>070096979</VISIT_CARD_NO><OUTHOSP_NO>20000113</OUTHOSP_NO><INHOSP_NO/><ELECTR_REQUISITION_NO>2026877</ELECTR_REQUISITION_NO><ORDER_NO/><EXAM_CATEG_NAME>CT</EXAM_CATEG_NAME><EXAM_APPLY_ITEM_NAME>CT口咽平扫</EXAM_APPLY_ITEM_NAME><RESERVE_EXAM_DATE/><TOTAL_MONEY>339.4</TOTAL_MONEY><APPLY_DEPT_CODE>913471</APPLY_DEPT_CODE><APPLY_DEPT_NAME>东院急诊内科诊室</APPLY_DEPT_NAME><EXECUT_DEPT_NAME>东院CT室</EXECUT_DEPT_NAME><PAT_NAME>刘晓宁</PAT_NAME><PHYSI_SEX_NAME>男</PHYSI_SEX_NAME><AGE>48岁</AGE><JCBWDM>10119</JCBWDM></row></body></msg>]]></Msg></MsgInfo></ESBEntry>";

        // 将 XML 转换为 JsonNode 对象
        try {
            msgStr = msgStr.replace("<![CDATA[<msg><body><row action=\"select\">", " ");
            msgStr = msgStr.replace("</row></body></msg>]]>", " ");

            //处理消息
            msgStr = msgStr.replace("<![CDATA[<msg><body><row action=\"select\">", " ");
            msgStr = msgStr.replace("</row></body></msg>]]>", " ");
            JSONObject jsonObject = XmlUtil.xmlToJson(msgStr);

            //封装数据
            List<Map<String, Object>> mapList = new ArrayList<>();
            if (null != jsonObject.getJSONArray("MsgInfo")) {
                JSONArray msgInfo = jsonObject.getJSONArray("MsgInfo").getJSONObject(0).getJSONArray("Msg");

                List<PatientRecords> list1 = new ArrayList<>();
                List<PatientRecords> list2 = new ArrayList<>();

                String name = "";
                String sex = "";
                log.info("\n" + "msgInfo" + msgInfo);
                if (null != msgInfo && msgInfo.size() > 0) {
                    for (int i = 0; i < msgInfo.size(); i++) {

                        JSONObject jsonObjectData = msgInfo.getJSONObject(i);

//                        if(!ckdpt_code.equals(jsonObjectData.getString("APPLY_DEPT_CODE"))){
//                            continue;
//                        }
                        PatientRecords patientRecords = new PatientRecords();
                        patientRecords.setRpt_no(jsonObjectData.getString("ELECTR_REQUISITION_NO"));
                        if (StringUtils.isNotEmpty(jsonObjectData.getString("OUTHOSP_NO"))) {
                            patientRecords.setNumber(jsonObjectData.getString("OUTHOSP_NO"));
                            patientRecords.setType("1");
                        }

                        if (StringUtils.isNotEmpty(jsonObjectData.getString("INHOSP_NO"))) {
                            patientRecords.setNumber(jsonObjectData.getString("INHOSP_NO"));
                            patientRecords.setType("3");
                        }

//                        patientRecords.setBar_code(bar_code);

                        name = jsonObjectData.getString("PAT_NAME");
                        sex = jsonObjectData.getString("PHYSI_SEX_NAME");
                        patientRecords.setPersonName(jsonObjectData.getString("PAT_NAME"));
                        patientRecords.setSex(jsonObjectData.getString("PHYSI_SEX_NAME"));
                        patientRecords.setAge(jsonObjectData.getString("AGE"));
                        patientRecords.setRpt_dept(jsonObjectData.getString("APPLY_DEPT_NAME"));
                        patientRecords.setRpc_code(jsonObjectData.getString("APPLY_DEPT_CODE"));
                        patientRecords.setProj_name(jsonObjectData.getString("EXAM_APPLY_ITEM_NAME"));

                        patientRecords.setCkpt_name(jsonObjectData.getString("EXAM_PART_DESCR"));
                        patientRecords.setCkdpt_code(jsonObjectData.getString("EXECUT_DEPT_CODE"));
                        patientRecords.setCkdpt_name(jsonObjectData.getString("EXECUT_DEPT_NAME"));

                        //todo //检查地点缺失
                        patientRecords.setPos_name("");
                        patientRecords.setCost(jsonObjectData.getString("TOTAL_MONEY"));
                        if (StringUtils.isNotEmpty(jsonObjectData.getString("RESERVE_EXAM_DATE"))) {
                            patientRecords.setAppoint_time(DateUtil.parseDate(jsonObjectData.getString("RESERVE_EXAM_DATE")));
                        }
                        patientRecords.setApp_remark(jsonObjectData.getString("NOTE"));

                        if ("1".equals(patientRecords.getType())) {
                            list1.add(patientRecords);
                        } else if ("3".equals(patientRecords.getType())) {
                            list2.add(patientRecords);
                        }
                    }
                }


                Map<String, Object> map1 = new HashMap<>();
                //门诊
                map1.put("type", "1");
                map1.put("name", name);
                map1.put("sex", sex);
                map1.put("list", list1);
                mapList.add(map1);

                //住院
                Map<String, Object> map3 = new HashMap<>();
                map3.put("type", "3");
                map3.put("name", name);
                map3.put("sex", sex);
                map3.put("list", list2);
                mapList.add(map3);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        // 将 JsonNode 对象转换为 JSON 字符串
    }

    /**
     * 确认/再次确认按钮
     *
     * @author wzn
     * @date 2025/06/21 11:40
     */
    public ResponseEntity confirm(ConfirmVO confirmVO) throws SDKException {


        User user = userService.getPersonalInfo();
        for (PatientRecords pr : confirmVO.getPatientRecordsList()) {

            if (StringUtils.isBlank(pr.getType())) {
                return ResponseUtil.error("参数有误,type类型必传!");
            }

            if ("1".equals(pr.getType())) {
                if (!confirmVO.getCode().equals(pr.getCkdpt_code())) {
                    return ResponseUtil.error("患者身份核对失败,您所在科室与检查科室不一致");
                }
            }


            if ("3".equals(pr.getType())) {
                if (!confirmVO.getWristbandCode().equals(pr.getNumber())) {
                    return ResponseUtil.error("患者身份核对失败,申请单与患者信息不一致，请重新扫描患者腕带");
                }
            }

        }

        //创建连接对象
        EwellServiceTool ewellServiceTool = new EwellServiceTool();

//        //获取连接
//        String cid = ewellServiceTool.connect(checkName);


        for (PatientRecords pr : confirmVO.getPatientRecordsList()) {
            //再次确认
            QueryWrapper<PatientRecords> qw = new QueryWrapper<>();
            qw.eq("rpt_no", pr.getRpt_no());
            qw.eq("isDel", "0");
            PatientRecords records = patientRecordsMapper.selectOne(qw);

            pr.setPat_verify_dept_code(confirmVO.getCode());

            QueryWrapper<DictData> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("status", "0");
            queryWrapper2.eq("delete_flag", "0");
            queryWrapper2.eq("dict_type", "user_dept");
            queryWrapper2.eq("dict_value", confirmVO.getCode());
            DictData dictData = dictDataMapper.selectOne(queryWrapper2);

            if (null != dictData) {
                pr.setPat_verify_dept_name(dictData.getDictLabel());
            }

            pr.setPat_verify_code(user.getUserAccount());
            pr.setPat_verify_name(user.getUserName());
            pr.setPat_verify_time(new Date());

            if (null == records) {
                //新增记录
                pr.setCreateUser(user.getUserId() + "");
                pr.setCreateTime(new Date());
                pr.setIsDel(0);

                patientRecordsMapper.insert(pr);
            } else {
                //再次确认  更新核实时间

                records.setPat_verify_time(new Date());
                records.setPat_verify_code(user.getUserAccount());
                records.setPat_verify_name(user.getUserName());

                patientRecordsMapper.updateById(records);
            }


            String body = this.splicingBody(pr);
            //调用第三方接口确认
//            String msgId = this.putMsg(body, ewellServiceTool);

            MessageEntity msg = ewellServiceTool.composePutAndGetMsg(checkName, checkChannel, 5000,body);
            log.info("\n"+"推送返回msg"+msg);

            if (null != msg) {
                //todo  返回成功 更改状态
                QueryWrapper<PatientRecords> queryWrapper = new QueryWrapper<>();
                queryWrapper.eq("rpt_no", pr.getRpt_no());
                PatientRecords patientRecords1 = patientRecordsMapper.selectOne(queryWrapper);
                patientRecords1.setStatus("1");
                patientRecordsMapper.updateById(patientRecords1);
            }


        }

        return ResponseUtil.success("患者信息核对成功，后台会持续确认，请您继续操作");
    }


    /**
     * @author wzn
     * @date 2025/06/21 14:50
     */
    public void updateStatus() throws SDKException {
        QueryWrapper<PatientRecords> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("isDel", "0");
        queryWrapper.ne("status", "1");
        List<PatientRecords> patientRecordsList = patientRecordsMapper.selectList(queryWrapper);
        if (!CollectionUtils.isEmpty(patientRecordsList)) {

            //创建连接对象
            EwellServiceTool ewellServiceTool = new EwellServiceTool();

            //获取连接
            String cid = ewellServiceTool.connect(checkName);

            for (PatientRecords pr : patientRecordsList) {

                String body = this.splicingBody(pr);
                //调用第三方接口确认
                String msgId = this.putMsg(body, ewellServiceTool);
                if (StringUtils.isNotBlank(msgId)) {
                    //成功之后修改状态
                    pr.setStatus("1");
                    patientRecordsMapper.updateById(pr);
                }
            }


        }
    }


    public String putMsg(String msgbody, EwellServiceTool ewellServiceTool) throws SDKException {
        //放入消息
        return ewellServiceTool.putMsg(checkChannel, msgbody);
    }


    public String splicingBody(PatientRecords records) {
        String body = "<ESBEntry>\n" +
                "   <AccessControl>\n" +
                "      <UserName>ZNZY</UserName>\n" +
                "      <Password>ZNZY</Password>\n" +
                "      <Fid>PS04002</Fid>\n" +
                "      <OrderNo>PS04002S01001</OrderNo>\n" +
                "      <SysFlag>1</SysFlag>\n" +
                "   </AccessControl>\n" +
                "   <MessageHeader>\n" +
                "      <Fid>PS04002</Fid>\n" +
                "      <OrderNo>PS04002S01001</OrderNo>\n" +
                "      <SourceSysCode>S84</SourceSysCode>\n" +
                "      <TargetSysCode>S00</TargetSysCode>\n" +
                "      <HospCode>GH01</HospCode>\n" +
                "      <ReturnFlag>-1</ReturnFlag>\n" +
                "      <MsgDate>" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "</MsgDate>\n" +
                "   </MessageHeader>\n" +
                "   <MsgInfo>\n" +
                "      <Msg  lastUpdate =\"" + DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss") + "\" action=\"update\">\n" +
                "        <RPT_NO>" + records.getRpt_no() + "</RPT_NO>\n" +
                "        <PAT_VERIFY_DEPT_CODE>" + records.getPat_verify_dept_code() + "</PAT_VERIFY_DEPT_CODE>\n" +
                "        <PAT_VERIFY_DEPT_NAME>" + records.getPat_verify_dept_name() + "</PAT_VERIFY_DEPT_NAME>\n" +
                "        <PAT_VERIFY_CODE>" + records.getPat_verify_code() + "</PAT_VERIFY_CODE>\n" +
                "        <PAT_VERIFY_NAME>" + records.getPat_verify_name() + "</PAT_VERIFY_NAME>\n" +
                "        <PAT_VERIFY_TIME>" + DateUtil.format(records.getPat_verify_time(), "yyyy-MM-dd HH:mm:ss") + "</PAT_VERIFY_TIME>\n" +
                "      </Msg>\n" +
                "   </MsgInfo>\n" +
                "</ESBEntry>";

        log.info("\nbody====="+body);
        return body;
    }


    public String querryBody(String bar_code, String deptCode) {
        String body = "<ESBEntry>\n" +
                "        <AccessControl>\n" +
                "          <SysFlag>1</SysFlag>\n" +
                "          <UserName>ZNZY</UserName>\n" +
                "          <Password>ZNZY</Password>\n" +
                "          <Fid>BS35004</Fid>\n" +
                "          <OrderNo>BS35004S84001</OrderNo>\n" +
                "        </AccessControl>\n" +
                "        <MessageHeader>\n" +
                "          <Fid>BS35004</Fid>\n" +
                "          <OrderNo>BS35004S84001</OrderNo>\n" +
                "          <SourceSysCode>S84</SourceSysCode>\n" +
                "          <TargetSysCode>S00</TargetSysCode>\n" +
                "          <HospCode>GH01</HospCode>\n" +
                "          <MsgDate>2025-06-27 10:57:07</MsgDate>\n" +
                "        </MessageHeader>\n" +
                "        <RequestOption>\n" +
                "          <triggerData>0</triggerData>\n" +
                "          <dataAmount>500</dataAmount>\n" +
                "        </RequestOption>\n" +
                "        <MsgInfo flag=\"1\" >\n" +
                "          <Msg></Msg>\n" +
                "          <distinct value=\"0\"/>\n";


        //bar_code
        if (bar_code.startsWith("A7")) {
            //his那边说A7开头+电子申请单编号      门诊是B7+门诊流水号
            body += "          <query item=\"ELECTR_REQUISITION_NO\" compy=\" = \"           value=\" '" + bar_code.substring(2) + "' \" splice=\"AND\"/>\n";
        }

        if (bar_code.startsWith("B7")) {
            //his那边说A7开头+电子申请单编号      门诊是B7+门诊流水号
            body += "          <query item=\"OUTHOSP_NO\" compy=\" = \"           value=\" '" + bar_code.substring(2) + "' \" splice=\"AND\"/>\n";
        }


        //执行科室
        body += "          <query item=\"EXECUT_DEPT_CODE\" compy=\" = \"           value=\" '" + deptCode + "' \" splice=\"AND\"/>\n";

        body += "        </MsgInfo>\n" +
                "        <GroupInfo flag=\"0\">\n" +
                "          <AS ID=\"\" linkField=\"\"/>\n" +
                "        </GroupInfo>\n" +
                "</ESBEntry>";
        return body;
    }


}
