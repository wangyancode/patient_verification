package com.dincher.project.scm.service.impl;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.beust.ah.A;
import com.dincher.common.constant.HttpStatus;
import com.dincher.common.exception.CustomException;
import com.dincher.common.utils.SecurityUtils;
import com.dincher.common.utils.StringUtils;
import com.dincher.common.utils.bean.BeanUtils;
import com.dincher.project.scm.domain.DTO.*;
import com.dincher.project.scm.domain.entity.DeliveryOrderDetails;
import com.dincher.project.scm.domain.vo.*;
import com.dincher.project.scm.mapper.DeliveryOrderDetailsMapper;
import com.dincher.project.scm.mapper.DeliveryOrderMapper;
import com.dincher.project.scm.mapper.SynchronizeHisAfterAcceptanceMapper;
import com.dincher.project.scm.service.*;
import com.dincher.project.scm.task.HisDetailSaveTask;
import com.dincher.project.system.domain.vo.UserVO;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

import org.springframework.transaction.annotation.Transactional;

/**
 * 配送单(scm_delivery_order)业务实现类
 *
 * @author wangbin
 * @date 2023-12-06 11:45:08
 */
@Service
@Slf4j
public class DeliveryOrderServiceImpl extends ServiceImpl<DeliveryOrderMapper, DeliveryOrderVO> implements DeliveryOrderService {
    @Resource
    DeliveryOrderMapper deliveryOrderMapper;

    @Resource
    private DeliveryOrderDetailsService deliveryOrderDetailsService;

    @Resource
    private DeliveryCompanyService deliveryCompanyService;

    @Resource
    private HisDetailService hisDetailService;

    @Resource
    private DeliveryOrderDetailsMapper deliveryOrderDetailsMapper;

    @Resource
    private SynchronizeHisAfterAcceptanceMapper synchronizeHisAfterAcceptanceMapper;

    @Autowired
    private HisDetailSaveTask hisDetailSaveTask;

    @Autowired
    private HisUnsynchronizedDeliveryOrderDetailsService hisUnsynchronizedDeliveryOrderDetailsService;

    /**
     * @param deliveryOrderVO
     * @return List<DeliveryOrder>
     * @description //TODO  查询数据
     * @author wangbin
     * @date 2023-12-06 11:45:08
     */
    @Override
    @Transactional
    public List<DeliveryOrderVO> selectDataList(DeliveryOrderVO deliveryOrderVO) {
        return deliveryOrderMapper.selectDataList(deliveryOrderVO);
    }

    @Override
    @Transactional
    public List<DeliveryOrderQueryReturnDTO> allStatusQueryPage(DeliveryOrderQueryDTO deliveryOrderQueryDTO) {
        return deliveryOrderMapper.allStatusQueryPage(deliveryOrderQueryDTO);
    }

    @Override
    @Transactional
    public List<DeliveryOrderQueryResponseDTO> queryDetails(DeliveryOrderDetailRequestDTO deliveryOrderDetailRequestDTO) {
        //未合并
        List<DeliveryOrderQueryResponseDTO> returnDeliveryOrderList = new ArrayList<>();
        if ("1".equals(deliveryOrderDetailRequestDTO.getIsMerge())) {
            DeliveryOrderQueryResponseDTO deliveryOrderVO = deliveryOrderMapper.queryOrderAndInvoiceNo(deliveryOrderDetailRequestDTO);
            DeliveryOrderDetailGroupQueryDTO deliveryOrderDetailGroupQueryDTO = new DeliveryOrderDetailGroupQueryDTO();
            deliveryOrderDetailGroupQueryDTO.setDrugCheckStatus(deliveryOrderDetailRequestDTO.getDrugCheckStatus());
            List<String> boxNos = new ArrayList<>();
            String boxNo = deliveryOrderVO.getBoxNo();
            boxNos.add(boxNo);
            deliveryOrderDetailGroupQueryDTO.setBoxNos(boxNos);
            List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOS = deliveryOrderDetailsMapper.selectDataGroupByBoxNoAndApprovalNoNotGroup(deliveryOrderDetailGroupQueryDTO);
            //计算效期小于半年（180天）
            deliveryOrderDetailsVOS.forEach(dod ->{
                Date validDate = dod.getValidDate();
                Date dateNow = new Date();
                if(null==validDate){
                    dod.setIsInValidity(1);
                }else if(validDate.before(dateNow)){
                    dod.setIsInValidity(0);
                }else {
                    long diff = validDate.getTime() - dateNow.getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    if(days<=180){
                        dod.setIsInValidity(0);
                    }else {
                        dod.setIsInValidity(1);
                    }
                }
            });
            deliveryOrderVO.setDeliveryOrderDetailsVOS(deliveryOrderDetailsVOS);
            returnDeliveryOrderList.add(deliveryOrderVO);
        }
        //合并
        else {
            if(StringUtils.isBlank(deliveryOrderDetailRequestDTO.getGroupNo())){
                throw new CustomException("组号为空!");
            }
            //待验收
            if ("1".equals(deliveryOrderDetailRequestDTO.getDrugCheckStatus())) {
                DeliveryOrderQueryResponseDTO deliveryOrderVO = deliveryOrderMapper.queryOrderAndInvoiceNo(deliveryOrderDetailRequestDTO);
                DeliveryOrderDetailGroupQueryDTO deliveryOrderDetailGroupQueryDTO = new DeliveryOrderDetailGroupQueryDTO();
                deliveryOrderDetailGroupQueryDTO.setDrugCheckStatus(deliveryOrderDetailRequestDTO.getDrugCheckStatus());
                List<DeliveryOrderVO> deliveryOrderVOS = deliveryOrderMapper.selectList(new QueryWrapper<DeliveryOrderVO>()
                        .eq("group_no", deliveryOrderVO.getGroupNo())
                        .select("box_no"));
                List<String> boxNos = deliveryOrderVOS.stream().map(DeliveryOrderVO::getBoxNo).collect(Collectors.toList());
                deliveryOrderDetailGroupQueryDTO.setBoxNos(boxNos);
                List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOS = deliveryOrderDetailsMapper.selectDataGroupByBoxNoAndApprovalNo(deliveryOrderDetailGroupQueryDTO);
                //计算效期小于半年（180天）
                deliveryOrderDetailsVOS.forEach(dod ->{
                    Date validDate = dod.getValidDate();
                    Date dateNow = new Date();
                    if(null==validDate){
                        dod.setIsInValidity(1);
                    }else if(validDate.before(dateNow)){
                        dod.setIsInValidity(0);
                    }else {
                        long diff = validDate.getTime() - dateNow.getTime();
                        long days = diff / (1000 * 60 * 60 * 24);
                        if(days<=180){
                            dod.setIsInValidity(0);
                        }else {
                            dod.setIsInValidity(1);
                        }
                    }
                });
                deliveryOrderVO.setDeliveryOrderDetailsVOS(deliveryOrderDetailsVOS);
                returnDeliveryOrderList.add(deliveryOrderVO);
            }
            //已验收（多条）
            else if ("2".equals(deliveryOrderDetailRequestDTO.getDrugCheckStatus())) {
                List<DeliveryOrderQueryResponseDTO> deliveryOrderVOs = deliveryOrderMapper.queryOrderAndInvoiceNoAll(deliveryOrderDetailRequestDTO);
                deliveryOrderVOs.forEach(x -> {
                    DeliveryOrderDetailGroupQueryDTO deliveryOrderDetailGroupQueryDTO = new DeliveryOrderDetailGroupQueryDTO();
                    deliveryOrderDetailGroupQueryDTO.setDrugCheckStatus(deliveryOrderDetailRequestDTO.getDrugCheckStatus());
                    List<String> boxNos = new ArrayList<>();
                    String boxNo = x.getBoxNo();
                    boxNos.add(boxNo);
                    deliveryOrderDetailGroupQueryDTO.setBoxNos(boxNos);
                    List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOS = deliveryOrderDetailsMapper.selectDataGroupByBoxNoAndApprovalNo(deliveryOrderDetailGroupQueryDTO);
                    //计算效期小于半年（180天）
                    deliveryOrderDetailsVOS.forEach(dod ->{
                        Date validDate = dod.getValidDate();
                        Date dateNow = new Date();
                        if(null==validDate){
                            dod.setIsInValidity(1);
                        }else if(validDate.before(dateNow)){
                            dod.setIsInValidity(0);
                        }else {
                            long diff = validDate.getTime() - dateNow.getTime();
                            long days = diff / (1000 * 60 * 60 * 24);
                            if(days<=180){
                                dod.setIsInValidity(0);
                            }else {
                                dod.setIsInValidity(1);
                            }
                        }
                    });
                    x.setDeliveryOrderDetailsVOS(deliveryOrderDetailsVOS);
                });
                returnDeliveryOrderList.addAll(deliveryOrderVOs);
            }

        }
        return returnDeliveryOrderList;
    }

    @Override
    @Transactional
    public List<DeliveryOrderQueryResponseDTO> queryDetailsAllBox(DeliveryOrderDetailRequestDTO deliveryOrderDetailRequestDTO) {

        deliveryOrderDetailRequestDTO.setBoxNo(null);
        List<DeliveryOrderQueryResponseDTO> returnDeliveryOrderList = new ArrayList<>();

        List<DeliveryOrderQueryResponseDTO> deliveryOrderVOs = deliveryOrderMapper.queryOrderAndInvoiceNoAll(deliveryOrderDetailRequestDTO);
        deliveryOrderVOs.forEach(x -> {
            DeliveryOrderDetailGroupQueryDTO deliveryOrderDetailGroupQueryDTO = new DeliveryOrderDetailGroupQueryDTO();
            deliveryOrderDetailGroupQueryDTO.setDrugCheckStatus(deliveryOrderDetailRequestDTO.getDrugCheckStatus());
            List<String> boxNos = new ArrayList<>();
            String boxNo = x.getBoxNo();
            boxNos.add(boxNo);
            deliveryOrderDetailGroupQueryDTO.setBoxNos(boxNos);
            List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOS = deliveryOrderDetailsMapper.selectDataGroupByBoxNoAndApprovalNo(deliveryOrderDetailGroupQueryDTO);
            //计算效期小于半年（180天）
            deliveryOrderDetailsVOS.forEach(dod ->{
                Date validDate = dod.getValidDate();
                Date dateNow = new Date();
                if(null==validDate){
                    dod.setIsInValidity(1);
                }else if(validDate.before(dateNow)){
                    dod.setIsInValidity(0);
                }else {
                    long diff = validDate.getTime() - dateNow.getTime();
                    long days = diff / (1000 * 60 * 60 * 24);
                    if(days<=180){
                        dod.setIsInValidity(0);
                    }else {
                        dod.setIsInValidity(1);
                    }
                }
            });
            x.setDeliveryOrderDetailsVOS(deliveryOrderDetailsVOS);
        });
        returnDeliveryOrderList.addAll(deliveryOrderVOs);

        return returnDeliveryOrderList;
    }

    @Override
    @Transactional
    public DeliveryOrderVO checkDrugs(CheckDrugsRequestDTO checkDrugsRequestDTO) {
        List<HisDetailVO> hisDetailVOBatchSaveOrUpdate=new ArrayList<>();
        UserVO user = SecurityUtils.getLoginUser().getUser();
        DeliveryOrderVO deliveryOrderVO = deliveryOrderMapper.selectById(checkDrugsRequestDTO.getBoxNo());
        if (null == deliveryOrderVO) {
            throw new CustomException("该箱子不存在!");
        }
        if ("3".equals(deliveryOrderVO.getCheckStatus())) {
            throw new CustomException("该箱子为已验收状态，不能验收!");
        }
        List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOs = checkDrugsRequestDTO.getDeliveryOrderDetailsVOs();
        if (CollectionUtils.isEmpty(deliveryOrderDetailsVOs)) {
            throw new CustomException("请选择药品!");
        }
        deliveryOrderDetailsVOs.forEach(x ->{
            x.setDrugCheckStatus("2");
            x.setDrugCheckDatetime(new Date());
            x.setCheckUserId(user.getUserId());
        });
        deliveryOrderDetailsService.updateBatchById(deliveryOrderDetailsVOs);
        //判断是否全部验收
        long count = deliveryOrderDetailsService.count(new QueryWrapper<DeliveryOrderDetailsVO>()
                .eq("box_no", deliveryOrderVO.getBoxNo())
                .eq("drug_check_status", "1"));
        if (count <= 0) {
            deliveryOrderVO.setCheckStatus("3");
            deliveryOrderVO.setCheckDatetime(new Date());
            deliveryOrderVO.setIsChecked("0");
            this.updateById(deliveryOrderVO);
        } else {
            deliveryOrderVO.setCheckStatus("2");
            deliveryOrderVO.setCheckDatetime(new Date());
            deliveryOrderVO.setIsChecked("1");
            this.updateById(deliveryOrderVO);
        }

        //插入记录
        deliveryOrderDetailsVOs.forEach(x ->{
//            DeliveryOrderDetailsVO deliveryOrderDetailsVO = deliveryOrderDetailsService.getById(x.getId());
            HisDetailVO hisDetailVO = new HisDetailVO();
            hisDetailVO.setDetailId(x.getId());
            hisDetailVO.setBoxNo(x.getBoxNo());
            hisDetailVO.setDrugName(x.getDrugName());
            hisDetailVO.setOrderCount(x.getOrderCount());
            hisDetailVO.setDistributeCount(x.getDistributeCount());
            hisDetailVO.setUnit(x.getUnit());
            hisDetailVO.setDrugSpecs(x.getDrugSpecs());
            hisDetailVO.setLicenseNo(x.getLicenseNo());
            hisDetailVO.setApprovalNo(x.getApprovalNo());
            hisDetailVO.setDrugCheckStatus("2");
            hisDetailVO.setDrugCheckDatetime(new Date());
            hisDetailVO.setCheckCount(x.getCheckCount());
            hisDetailVO.setCheckUserId(user.getUserId());
            hisDetailVO.setSupplierCode(x.getSupplierCode());
            hisDetailVO.setSupplierName(x.getSupplierName());
            hisDetailVO.setInvoiceNo(x.getInvoiceNo());
            hisDetailVO.setGroupNo(deliveryOrderVO.getGroupNo());
            hisDetailVO.setDistributeCode(deliveryOrderVO.getDistributeCode());
            hisDetailVO.setDeleteFlag(0);
            hisDetailVOBatchSaveOrUpdate.add(hisDetailVO);
        });
        if(CollectionUtils.isNotEmpty(hisDetailVOBatchSaveOrUpdate)){
            hisDetailService.saveOrUpdateBatch(hisDetailVOBatchSaveOrUpdate);
        }

        //全部验收完同步数据到his
        if (count <= 0) {
            //异步方法
            SynchronizeHisAfterAcceptanceDTO synchronizeHisAfterAcceptanceDTO = new SynchronizeHisAfterAcceptanceDTO();
            synchronizeHisAfterAcceptanceDTO.setBoxNo(checkDrugsRequestDTO.getBoxNo());
            if(StringUtils.isNotBlank(deliveryOrderVO.getGroupNo())){
                synchronizeHisAfterAcceptanceDTO.setGroupNo(deliveryOrderVO.getGroupNo());
            }else {
                synchronizeHisAfterAcceptanceDTO.setGroupNo(null);
            }
            hisDetailSaveTask.updateBatchByDetailIdAndInvoiceNoOracle(synchronizeHisAfterAcceptanceDTO);

        }

        return deliveryOrderVO;
    }

    @Override
    @Transactional
    public MergeCheckDrugsRequestDTO mergeCheckDrugs(MergeCheckDrugsRequestDTO mergeCheckDrugsRequestDTO) {
        List<HisDetailVO> hisDetailVOBatchSaveOrUpdate=new ArrayList<>();
        Set<String> boxNosAll =new HashSet<>();
                UserVO user = SecurityUtils.getLoginUser().getUser();
        String groupNo = mergeCheckDrugsRequestDTO.getGroupNo();
        List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOs = mergeCheckDrugsRequestDTO.getDeliveryOrderDetailsVOs();
        if (CollectionUtils.isEmpty(deliveryOrderDetailsVOs)) {
            throw new CustomException("请选择药品!");
        }
        deliveryOrderDetailsVOs.forEach(x -> {
            String drugName = x.getDrugName();
            x.setGroupNo(mergeCheckDrugsRequestDTO.getGroupNo());
//            BigDecimal distributeCount = x.getDistributeCount();
            Integer boxCount = x.getBoxCount();
            BigDecimal checkCount = x.getCheckCount();
            //验证数量
//            BigDecimal multiply = distributeCount.multiply(new BigDecimal(boxCount));
            BigDecimal multiply = new BigDecimal(x.getAllDistributeCount());
            int i = multiply.compareTo(checkCount);
            List<DeliveryOrderDetailsVO> deliveryOrderDetailsVOsCheck =new ArrayList<>();
            if (boxCount != 1) {
                if (i != 0) {
                    throw new CustomException(drugName + "数量错误!");
                }
                deliveryOrderDetailsVOsCheck = deliveryOrderMapper.reverseCheckSpecificDrugDetails(x);
            }else {
                deliveryOrderDetailsVOsCheck.add(x);
            }

            //反查具体的药品明细
            x.setGroupNo(groupNo);
            if (CollectionUtils.isEmpty(deliveryOrderDetailsVOsCheck)) {
                return;
            }
            deliveryOrderDetailsVOsCheck.forEach(deliveryOrderDetailsUpdate -> {
                deliveryOrderDetailsUpdate.setDrugCheckStatus("2");
                deliveryOrderDetailsUpdate.setDrugCheckDatetime(new Date());
                deliveryOrderDetailsUpdate.setCheckCount(deliveryOrderDetailsUpdate.getDistributeCount());
                deliveryOrderDetailsUpdate.setCheckUserId(user.getUserId());
            });

            deliveryOrderDetailsService.updateBatchById(deliveryOrderDetailsVOsCheck);
            Set<String> boxNos = deliveryOrderDetailsVOsCheck.stream().map(DeliveryOrderDetailsVO::getBoxNo).collect(Collectors.toSet());
            boxNosAll.addAll(boxNos);

            //插入记录
            deliveryOrderDetailsVOsCheck.forEach(hisDetail ->{
                HisDetailVO hisDetailVO = new HisDetailVO();
                hisDetailVO.setDetailId(hisDetail.getId());
                hisDetailVO.setBoxNo(hisDetail.getBoxNo());
                hisDetailVO.setDrugName(hisDetail.getDrugName());
                hisDetailVO.setOrderCount(hisDetail.getOrderCount());
                hisDetailVO.setDistributeCount(hisDetail.getDistributeCount());
                hisDetailVO.setUnit(hisDetail.getUnit());
                hisDetailVO.setDrugSpecs(hisDetail.getDrugSpecs());
                hisDetailVO.setLicenseNo(hisDetail.getLicenseNo());
                hisDetailVO.setApprovalNo(hisDetail.getApprovalNo());
                hisDetailVO.setDrugCheckStatus("2");
                hisDetailVO.setDrugCheckDatetime(new Date());
                hisDetailVO.setCheckCount(hisDetail.getCheckCount());
                hisDetailVO.setCheckUserId(user.getUserId());
                hisDetailVO.setSupplierCode(hisDetail.getSupplierCode());
                hisDetailVO.setSupplierName(hisDetail.getSupplierName());
                hisDetailVO.setInvoiceNo(hisDetail.getInvoiceNo());
                DeliveryOrderVO deliveryOrderVO = this.getById(hisDetail.getBoxNo());
                hisDetailVO.setGroupNo(deliveryOrderVO.getGroupNo());
                hisDetailVO.setDistributeCode(deliveryOrderVO.getDistributeCode());
                hisDetailVO.setDeleteFlag(0);
                hisDetailVOBatchSaveOrUpdate.add(hisDetailVO);
            });

        });
        //判断是否全部验收
        //@ApiModelProperty(value = "是否验收完0完1未完")
        String isChecked = "0";
        for(String boxnoStr:boxNosAll){
            long count = deliveryOrderDetailsService.count(new QueryWrapper<DeliveryOrderDetailsVO>()
                    .eq("box_no", boxnoStr)
                    .eq("drug_check_status", "1"));
            DeliveryOrderVO deliveryOrderVO = this.getById(boxnoStr);
            if (count <= 0) {
                deliveryOrderVO.setCheckStatus("3");
                deliveryOrderVO.setCheckDatetime(new Date());
                this.updateById(deliveryOrderVO);
            } else {
                deliveryOrderVO.setCheckStatus("2");
                deliveryOrderVO.setCheckDatetime(new Date());
                isChecked = "1";
                this.updateById(deliveryOrderVO);
            }
        }
        mergeCheckDrugsRequestDTO.setIsChecked(isChecked);
        if(CollectionUtils.isNotEmpty(hisDetailVOBatchSaveOrUpdate)){
            hisDetailService.saveOrUpdateBatch(hisDetailVOBatchSaveOrUpdate);
        }

        //全部验收完同步数据到his
        if (isChecked.equals( "0")) {
            //异步方法
            SynchronizeHisAfterAcceptanceDTO synchronizeHisAfterAcceptanceDTO = new SynchronizeHisAfterAcceptanceDTO();
            synchronizeHisAfterAcceptanceDTO.setGroupNo(groupNo);
            hisDetailSaveTask.updateBatchByDetailIdAndInvoiceNoOracle(synchronizeHisAfterAcceptanceDTO);
        }

        return mergeCheckDrugsRequestDTO;
    }

    @Override
    @Transactional
    public DeliveryOrderVO synchronizeData(SynchornizeDataRequestDTO synchornizeDataRequestDTO, HttpServletRequest request) {
        //验证token
        String token = request.getHeader("token");
        if(StringUtils.isEmpty(token)){
            throw new CustomException("传参缺少token!", HttpStatus.MISSINGTOKENFORPASSINGPARAMETERS);
        }
        String distributeCode = synchornizeDataRequestDTO.getDistributeCode();
        List<DeliveryCompanyVO> deliveryCompanyVOs = deliveryCompanyService.list(new QueryWrapper<DeliveryCompanyVO>()
                .eq("distribute_code", distributeCode));
        if(CollectionUtils.isEmpty(deliveryCompanyVOs)){
            throw new CustomException("配送企业不存在!", HttpStatus.DELIVERYCOMPANYDOESNOTEXIST);
        }
        if(deliveryCompanyVOs.size()>1){
            throw new CustomException("配送企业存在重复，存在多条!", HttpStatus.ERROR);
        }
        DeliveryCompanyVO deliveryCompanyVO = deliveryCompanyVOs.get(0);
        String tokenQuery = deliveryCompanyVO.getToken();
        if(!token.equals(tokenQuery)){
            throw new CustomException("配送企业token错误!", HttpStatus.DELIVERYENTERPRISETOKENERROR);
        }
        if(null==synchornizeDataRequestDTO){
            throw new CustomException("配送单信息错误!", HttpStatus.DELIVERYNOTEINFORMATIONERROR);
        }
        List<SynchornizeDataDetailsRequestDTO> details = synchornizeDataRequestDTO.getDetails();
        if(CollectionUtils.isEmpty(details)){
            throw new CustomException("配送单缺少明细!", HttpStatus.DELIVERYNOTEMISSINGDETAILS);
        }

        DeliveryOrderVO deliveryOrderVO = this.getById(synchornizeDataRequestDTO.getBoxNo());
        if(null!=deliveryOrderVO){
            throw new CustomException("配送单已存在!", HttpStatus.DELIVERYNOTEALREADYEXISTS);
        }
        DeliveryOrderVO deliveryOrderVOSave = BeanUtils.copyProperties(synchornizeDataRequestDTO, DeliveryOrderVO.class);
        this.save(deliveryOrderVOSave);
        List<DeliveryOrderDetailsVO> detailsListSave = BeanUtils.copyList(details, DeliveryOrderDetailsVO.class);
        detailsListSave.forEach(x ->{
            x.setBoxNo(deliveryOrderVOSave.getBoxNo());
        });
        deliveryOrderDetailsService.saveBatch(detailsListSave);




        return deliveryOrderVO;
    }

    @Override
    @Transactional
    public DeliveryOrderQueryCountDTO allStatusQueryCount(DeliveryOrderQueryDTO requestQueryBody) {
        DeliveryOrderQueryCountDTO deliveryOrderQueryCountDTO = deliveryOrderMapper.allStatusQueryCount(requestQueryBody);
        if(null==deliveryOrderQueryCountDTO){
            deliveryOrderQueryCountDTO=new DeliveryOrderQueryCountDTO();
        }
        return deliveryOrderQueryCountDTO;
    }

    @Override
    public void synchronizeHisAfterAcceptance(SynchronizeHisAfterAcceptanceDTO synchronizeHisAfterAcceptanceDTO) {
        //所有箱子验收完，detailid和发票号一样，fnode=0 or 1 才去更新状态，状态改为2
        if (null!=synchronizeHisAfterAcceptanceDTO.getGroupNo()) {
            //判断有没有验收完
            List<DeliveryOrderVO> deliveryOrderVOS = this.list(new QueryWrapper<DeliveryOrderVO>()
                    .eq("group_no", synchronizeHisAfterAcceptanceDTO.getGroupNo()).select("box_no"));
            List<String> boxNos = deliveryOrderVOS.stream().map(DeliveryOrderVO::getBoxNo).collect(Collectors.toList());
            List<DeliveryOrderDetailsVO> listDeliveryOrderDetailsVO = deliveryOrderDetailsService.list(new QueryWrapper<DeliveryOrderDetailsVO>()
                    .in("box_no", boxNos));
            for(DeliveryOrderDetailsVO deliveryOrderDetailsVO:listDeliveryOrderDetailsVO){
                if("1".equals(deliveryOrderDetailsVO.getDrugCheckStatus())){
                    log.info(" 校验成组没有验收完---组号："+synchronizeHisAfterAcceptanceDTO.getGroupNo() );
                    return;
                }
            }
            //成组

            listDeliveryOrderDetailsVO.forEach(x ->{
                String orderdetailId = x.getOrderdetailId();
                List<DeliveryOrderDetailsVO> list = deliveryOrderDetailsService.list(
                        new QueryWrapper<DeliveryOrderDetailsVO>()
                                .eq("orderdetail_id", orderdetailId)
                                .eq("drug_check_status", "1"));
                //判断订单明细的维度是否都验收完
                if(CollectionUtils.isEmpty(list)){
                    List<DeliveryOrderDetailsVO> listAllCheck = deliveryOrderDetailsService.list(
                            new QueryWrapper<DeliveryOrderDetailsVO>()
                                    .eq("orderdetail_id", orderdetailId)
                                    .eq("drug_check_status", "2"));
                    listAllCheck.forEach(y ->{
                        if(StringUtils.isNotBlank(y.getOrderdetailId())&&StringUtils.isNotBlank(y.getInvoiceNo())){
                            //查询his数据在不在
                            //1在，直接修改
                            //2不在，先存到中间表（可能会有重复数据），5分钟执行一次上面步骤
                            List<HisUnsynchronizedOracleDataDTO> hisUnsynchronizedOracleDataDTOS = synchronizeHisAfterAcceptanceMapper.selectDataByDetailIdAndInvoiceNoOracle(y.getOrderdetailId(), y.getInvoiceNo());
                            if(CollectionUtils.isNotEmpty(hisUnsynchronizedOracleDataDTOS)){
                                synchronizeHisAfterAcceptanceMapper.updateBatchByDetailIdAndInvoiceNoOracle(y.getOrderdetailId(), y.getInvoiceNo());
                                //删除中间表数据
                                hisUnsynchronizedDeliveryOrderDetailsService.deleteDataById(y.getId());
                            }else {
                                HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVOById = hisUnsynchronizedDeliveryOrderDetailsService.getById(y.getId());
                                if(null==hisUnsynchronizedDeliveryOrderDetailsVOById){
                                    HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVOSave = BeanUtils.copyProperties(y, HisUnsynchronizedDeliveryOrderDetailsVO.class);
                                    hisUnsynchronizedDeliveryOrderDetailsService.save(hisUnsynchronizedDeliveryOrderDetailsVOSave);

                                }

                            }
                        }
                    });
                }
            });
        }else {
            List<DeliveryOrderDetailsVO> listDeliveryOrderDetailsVO = deliveryOrderDetailsService.list(new QueryWrapper<DeliveryOrderDetailsVO>()
                    .eq("box_no", synchronizeHisAfterAcceptanceDTO.getBoxNo()));
            listDeliveryOrderDetailsVO.forEach(x ->{
                String orderdetailId = x.getOrderdetailId();
                List<DeliveryOrderDetailsVO> list = deliveryOrderDetailsService.list(
                        new QueryWrapper<DeliveryOrderDetailsVO>()
                                .eq("orderdetail_id", orderdetailId)
                                .eq("drug_check_status", "1"));
                //判断订单明细的维度是否都验收完
                if(CollectionUtils.isEmpty(list)){
                    List<DeliveryOrderDetailsVO> listAllCheck = deliveryOrderDetailsService.list(
                            new QueryWrapper<DeliveryOrderDetailsVO>()
                                    .eq("orderdetail_id", orderdetailId)
                                    .eq("drug_check_status", "2"));
                    listAllCheck.forEach(y ->{
                        if(StringUtils.isNotBlank(y.getOrderdetailId())&&StringUtils.isNotBlank(y.getInvoiceNo())){
                            //查询his数据在不在
                            //1在，直接修改
                            //2不在，先存到中间表（可能会有重复数据），5分钟执行一次上面步骤
                            List<HisUnsynchronizedOracleDataDTO> hisUnsynchronizedOracleDataDTOS = synchronizeHisAfterAcceptanceMapper.selectDataByDetailIdAndInvoiceNoOracle(y.getOrderdetailId(), y.getInvoiceNo());
                            if(CollectionUtils.isNotEmpty(hisUnsynchronizedOracleDataDTOS)){
                                synchronizeHisAfterAcceptanceMapper.updateBatchByDetailIdAndInvoiceNoOracle(y.getOrderdetailId(), y.getInvoiceNo());
                                //删除中间表数据
                                hisUnsynchronizedDeliveryOrderDetailsService.deleteDataById(y.getId());
                            }else {
                                HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVOById = hisUnsynchronizedDeliveryOrderDetailsService.getById(y.getId());
                                if(null==hisUnsynchronizedDeliveryOrderDetailsVOById){
                                    HisUnsynchronizedDeliveryOrderDetailsVO hisUnsynchronizedDeliveryOrderDetailsVOSave = BeanUtils.copyProperties(y, HisUnsynchronizedDeliveryOrderDetailsVO.class);
                                    hisUnsynchronizedDeliveryOrderDetailsService.save(hisUnsynchronizedDeliveryOrderDetailsVOSave);

                                }

                            }
                        }
                    });
                }

            });
        }





    }

}

