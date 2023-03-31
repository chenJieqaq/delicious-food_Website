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

import me.zhengjie.domain.Depoloyee information;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.Depoloyee informationRepository;
import me.zhengjie.service.Depoloyee informationService;
import me.zhengjie.service.dto.Depoloyee informationDto;
import me.zhengjie.service.dto.Depoloyee informationQueryCriteria;
import me.zhengjie.service.mapstruct.Depoloyee informationMapper;
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
* @date 2023-03-30
**/
@Service
@RequiredArgsConstructor
public class Depoloyee informationServiceImpl implements Depoloyee informationService {

    private final Depoloyee informationRepository depoloyee informationRepository;
    private final Depoloyee informationMapper depoloyee informationMapper;

    @Override
    public Map<String,Object> queryAll(Depoloyee informationQueryCriteria criteria, Pageable pageable){
        Page<Depoloyee information> page = depoloyee informationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(depoloyee informationMapper::toDto));
    }

    @Override
    public List<Depoloyee informationDto> queryAll(Depoloyee informationQueryCriteria criteria){
        return depoloyee informationMapper.toDto(depoloyee informationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public Depoloyee informationDto findById(String id) {
        Depoloyee information depoloyee information = depoloyee informationRepository.findById(id).orElseGet(Depoloyee information::new);
        ValidationUtil.isNull(depoloyee information.getId(),"Depoloyee information","id",id);
        return depoloyee informationMapper.toDto(depoloyee information);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Depoloyee informationDto create(Depoloyee information resources) {
        resources.setId(IdUtil.simpleUUID()); 
        return depoloyee informationMapper.toDto(depoloyee informationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Depoloyee information resources) {
        Depoloyee information depoloyee information = depoloyee informationRepository.findById(resources.getId()).orElseGet(Depoloyee information::new);
        ValidationUtil.isNull( depoloyee information.getId(),"Depoloyee information","id",resources.getId());
        depoloyee information.copy(resources);
        depoloyee informationRepository.save(depoloyee information);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            depoloyee informationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<Depoloyee informationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (Depoloyee informationDto depoloyee information : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" deployeeNo",  depoloyee information.getDeployeeNo());
            map.put(" iphone",  depoloyee information.getIphone());
            map.put(" address",  depoloyee information.getAddress());
            map.put(" email",  depoloyee information.getEmail());
            map.put(" sex",  depoloyee information.getSex());
            map.put(" createBy",  depoloyee information.getCreateBy());
            map.put(" createTime",  depoloyee information.getCreateTime());
            map.put(" updateBy",  depoloyee information.getUpdateBy());
            map.put(" updateTime",  depoloyee information.getUpdateTime());
            map.put(" isDelete",  depoloyee information.getIsDelete());
            map.put(" deployeeName",  depoloyee information.getDeployeeName());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}