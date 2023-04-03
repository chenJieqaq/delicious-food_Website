/*
*  Copyright 2019-2020 Zheng Jie
*
*  Licensed under the Apache License, Version 2.0 (the "License");
*  you may not use this file except in compliance with the License.
*  You may obtain a copy of the License at
*
*  http://www.apache.org/licenses/LICENSE-2.0
*
*  Unless required by applicable law or agreed to in writing, software
*  distributed under the License is distributed on an "AS IS" BASIS,
*  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*  See the License for the specific language governing permissions and
*  limitations under the License.
*/
package me.zhengjie.service.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenjie
* @date 2023-04-03
**/
@Data
public class SalaryDto implements Serializable {

    /** 员工编号 */
    private Integer deployNo;

    /** 基础薪资 */
    private BigDecimal basic;

    /** 绩效 */
    private BigDecimal performance;

    /** 总薪资 */
    private BigDecimal total;

    /** 是否删除 */
    private String isdelete;

    /** 创建人 */
    private String createby;

    /** 更新人 */
    private String updateby;

    /** 员工姓名 */
    private String deployeeName;
}