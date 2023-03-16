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

import lombok.extern.java.Log;
//import me.zhengjie.annotation.Log;
import me.zhengjie.domain.SysFood;
import me.zhengjie.service.SysFoodService;
import me.zhengjie.service.dto.SysFoodQueryCriteria;
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
* @date 2023-03-10
**/
@RestController
@RequiredArgsConstructor
@Api(tags = "foodlist管理")
@RequestMapping("/api/sysFood")
public class SysFoodController {

    private final SysFoodService sysFoodService;

    @ApiOperation("导出数据")
    @GetMapping(value = "/download")
    @PreAuthorize("@el.check('sysFood:list')")
    public void exportSysFood(HttpServletResponse response, SysFoodQueryCriteria criteria) throws IOException {
        sysFoodService.download(sysFoodService.queryAll(criteria), response);
    }

    @GetMapping
//    @Log("查询foodlist")
    @ApiOperation("查询foodlist")
    @PreAuthorize("@el.check('sysFood:list')")
    public ResponseEntity<Object> querySysFood(SysFoodQueryCriteria criteria, Pageable pageable){
        return new ResponseEntity<>(sysFoodService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @PostMapping
//    @Log("新增foodlist")
    @ApiOperation("新增foodlist")
    @PreAuthorize("@el.check('sysFood:add')")
    public ResponseEntity<Object> createSysFood(@Validated @RequestBody SysFood resources){
        return new ResponseEntity<>(sysFoodService.create(resources),HttpStatus.CREATED);
    }

    @PutMapping
//    @Log("修改foodlist")
    @ApiOperation("修改foodlist")
    @PreAuthorize("@el.check('sysFood:edit')")
    public ResponseEntity<Object> updateSysFood(@Validated @RequestBody SysFood resources){
        sysFoodService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
//    @Log("删除foodlist")
    @ApiOperation("删除foodlist")
    @PreAuthorize("@el.check('sysFood:del')")
    public ResponseEntity<Object> deleteSysFood(@RequestBody String[] ids) {
        sysFoodService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
