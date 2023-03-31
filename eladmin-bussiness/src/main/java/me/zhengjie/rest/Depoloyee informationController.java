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
import me.zhengjie.domain.Depoloyee information;
import me.zhengjie.service.Depoloyee informationService;
import me.zhengjie.service.dto.Depoloyee informationQueryCriteria;
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
* @date 2023-03-30
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "deployee_information管理")
@RequestMapping("/api/depoloyee information")
public class Depoloyee informationController {

    private final Depoloyee informationService depoloyee informationService;

    @Log("导出数据")
    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('depoloyee information:list')")
    public void exportDepoloyee information(HttpServletResponse response, Depoloyee informationQueryCriteria criteria) throws IOException {
        depoloyee informationService.download(depoloyee informationService.queryAll(criteria), response);
    }

    @GetMapping
    @Log("查询deployee_information")
    @ApiOperation("查询deployee_information")
    @PreAuthorize("@el.check('depoloyee information:list')")
    public ResponseEntity<Object> queryDepoloyee information(Depoloyee informationQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(depoloyee informationService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
    @Log("新增deployee_information")
    @ApiOperation("新增deployee_information")
    @PreAuthorize("@el.check('depoloyee information:add')")
    public ResponseEntity<Object> createDepoloyee information(@Validated @RequestBody Depoloyee information resources){
        return new ResponseEntity<>(depoloyee informationService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
    @Log("修改deployee_information")
    @ApiOperation("修改deployee_information")
    @PreAuthorize("@el.check('depoloyee information:edit')")
    public ResponseEntity<Object> updateDepoloyee information(@Validated @RequestBody Depoloyee information resources){
        depoloyee informationService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    @Log("删除deployee_information")
    @ApiOperation("删除deployee_information")
    @PreAuthorize("@el.check('depoloyee information:del')")
    public ResponseEntity<Object> deleteDepoloyee information(@RequestBody String[] ids) {
        depoloyee informationService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}