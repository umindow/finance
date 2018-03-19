package com.supervise.controller.vo;

import lombok.Data;

/**
 *
 * Created by ${admin} on 2018/2/3.
 */
@Data
public class BankCreditQueryVo {
    private String creditStartDate;//格式 "yyyy-MM-dd"
    private String creditEndDate;//格式 "yyyy-MM-dd"
    private String batchDate;//格式 "yyyy-MM-dd"
}
