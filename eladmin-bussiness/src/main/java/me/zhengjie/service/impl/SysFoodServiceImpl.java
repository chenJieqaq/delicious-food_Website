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
package me.zhengjie.service.impl;

import me.zhengjie.domain.SysFood;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.SysFoodRepository;
import me.zhengjie.service.SysFoodService;
import me.zhengjie.service.dto.SysFoodDto;
import me.zhengjie.service.dto.SysFoodQueryCriteria;
import me.zhengjie.service.mapstruct.SysFoodMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.hutool.core.util.IdUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import me.zhengjie.utils.PageUtil;
import me.zhengjie.utils.QueryHelp;
import java.util.List;
import java.util.Map;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
* @website https://eladmin.vip
* @description 服务实现
* @author chenjie
* @date 2023-03-10
**/
@Service
@RequiredArgsConstructor
public class SysFoodServiceImpl implements SysFoodService {

    private final SysFoodRepository sysFoodRepository;
    private final SysFoodMapper sysFoodMapper;

    @Override
    public Map<String,Object> queryAll(SysFoodQueryCriteria criteria, Pageable pageable){
        Page<SysFood> page = sysFoodRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(sysFoodMapper::toDto));
    }

    @Override
    public List<SysFoodDto> queryAll(SysFoodQueryCriteria criteria){
        return sysFoodMapper.toDto(sysFoodRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public SysFoodDto findById(String foodInstruction) {
        SysFood sysFood = sysFoodRepository.findById(foodInstruction).orElseGet(SysFood::new);
        ValidationUtil.isNull(sysFood.getFoodInstruction(),"SysFood","foodInstruction",foodInstruction);
        return sysFoodMapper.toDto(sysFood);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public SysFoodDto create(SysFood resources) {
        resources.setFoodInstruction(IdUtil.simpleUUID()); 
        return sysFoodMapper.toDto(sysFoodRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(SysFood resources) {
        SysFood sysFood = sysFoodRepository.findById(resources.getFoodInstruction()).orElseGet(SysFood::new);
        ValidationUtil.isNull( sysFood.getFoodInstruction(),"SysFood","id",resources.getFoodInstruction());
        sysFood.copy(resources);
        sysFoodRepository.save(sysFood);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String foodInstruction : ids) {
            sysFoodRepository.deleteById(foodInstruction);
        }
    }

    @Override
    public void download(List<SysFoodDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (SysFoodDto sysFood : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put("编号", sysFood.getFoodNo());
            map.put("名字", sysFood.getFoodName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}