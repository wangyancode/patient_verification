package com.dincher.project.scm.domain.vo;

import com.dincher.project.scm.domain.PatientRecords;
import lombok.Data;

import java.util.List;


@Data
public class ConfirmVO {

    private List<PatientRecords> patientRecordsList ;

    //所选科室
    private String code ;

    //腕带信息
    private String wristbandCode ;

}

