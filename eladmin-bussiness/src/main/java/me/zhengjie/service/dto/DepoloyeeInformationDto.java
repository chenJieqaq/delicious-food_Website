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
import java.io.Serializable;

/**
* @website https://eladmin.vip
* @description /
* @author chenjie
* @date 2023-03-31
**/
@Data
public class DepoloyeeInformationDto implements Serializable {

    private String id;

    private String deployeeNo;

    private String deployeeName;

    private String iphone;

    private String address;

    private String email;

    private String sex;

    private String createBy;

    private String createTime;

    private String updateBy;

    private String updateTime;

    private String isDelete;
}