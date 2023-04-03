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
package me.zhengjie.rest;

import me.zhengjie.annotation.Log;
import me.zhengjie.domain.DepoloyeeInformation;
import me.zhengjie.service.DepoloyeeInformationService;
import me.zhengjie.service.dto.DepoloyeeInformationQueryCriteria;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
* @website https://eladmin.vip
* @author chenjie
* @date 2023-03-31
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "employee_info管理")
@RequestMapping("/api/depoloyeeInformation")
public class DepoloyeeInformationController {

    private final DepoloyeeInformationService depoloyeeInformationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('depoloyeeInformation:list')")
    public void exportDepoloyeeInformation(HttpServletResponse response, DepoloyeeInformationQueryCriteria criteria) throws IOException {
        depoloyeeInformationService.download(depoloyeeInformationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询employee_info")
    @ApiOperation("查询employee_info")
    @PreAuthorize("@el.check('depoloyeeInformation:list')")
    public ResponseEntity<Object> queryDepoloyeeInformation(DepoloyeeInformationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(depoloyeeInformationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增employee_info")
    @ApiOperation("新增employee_info")
    @PreAuthorize("@el.check('depoloyeeInformation:add')")
    public ResponseEntity<Object> createDepoloyeeInformation(@Validated @RequestBody DepoloyeeInformation resources){
        return new ResponseEntity<>(depoloyeeInformationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改employee_info")
    @ApiOperation("修改employee_info")
    @PreAuthorize("@el.check('depoloyeeInformation:edit')")
    public ResponseEntity<Object> updateDepoloyeeInformation(@Validated @RequestBody DepoloyeeInformation resources){
        depoloyeeInformationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除employee_info")
    @ApiOperation("删除employee_info")
    @PreAuthorize("@el.check('depoloyeeInformation:del')")
    public ResponseEntity<Object> deleteDepoloyeeInformation(@RequestBody String[] ids) {
        depoloyeeInformationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}