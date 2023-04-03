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

import cn.hutool.Hutool;
import me.zhengjie.domain.DepoloyeeInformation;
import me.zhengjie.utils.ValidationUtil;
import me.zhengjie.utils.FileUtil;
import lombok.RequiredArgsConstructor;
import me.zhengjie.repository.DepoloyeeInformationRepository;
import me.zhengjie.service.DepoloyeeInformationService;
import me.zhengjie.service.dto.DepoloyeeInformationDto;
import me.zhengjie.service.dto.DepoloyeeInformationQueryCriteria;
import me.zhengjie.service.mapstruct.DepoloyeeInformationMapper;
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
* @date 2023-03-31
**/
@Service
@RequiredArgsConstructor
public class DepoloyeeInformationServiceImpl implements DepoloyeeInformationService {

    private final DepoloyeeInformationRepository depoloyeeInformationRepository;
    private final DepoloyeeInformationMapper depoloyeeInformationMapper;

    @Override
    public Map<String,Object> queryAll(DepoloyeeInformationQueryCriteria criteria, Pageable pageable){
        Page<DepoloyeeInformation> page = depoloyeeInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder),pageable);
        return PageUtil.toPage(page.map(depoloyeeInformationMapper::toDto));
    }

    @Override
    public List<DepoloyeeInformationDto> queryAll(DepoloyeeInformationQueryCriteria criteria){
        return depoloyeeInformationMapper.toDto(depoloyeeInformationRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root,criteria,criteriaBuilder)));
    }

    @Override
    @Transactional
    public DepoloyeeInformationDto findById(String id) {
        DepoloyeeInformation depoloyeeInformation = depoloyeeInformationRepository.findById(id).orElseGet(DepoloyeeInformation::new);
        ValidationUtil.isNull(depoloyeeInformation.getId(),"DepoloyeeInformation","id",id);
        return depoloyeeInformationMapper.toDto(depoloyeeInformation);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public DepoloyeeInformationDto create(DepoloyeeInformation resources) {

        resources.setId(IdUtil.simpleUUID()); 
        return depoloyeeInformationMapper.toDto(depoloyeeInformationRepository.save(resources));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DepoloyeeInformation resources) {
        DepoloyeeInformation depoloyeeInformation = depoloyeeInformationRepository.findById(resources.getId()).orElseGet(DepoloyeeInformation::new);
        ValidationUtil.isNull( depoloyeeInformation.getId(),"DepoloyeeInformation","id",resources.getId());
        depoloyeeInformation.copy(resources);
        depoloyeeInformationRepository.save(depoloyeeInformation);
    }

    @Override
    public void deleteAll(String[] ids) {
        for (String id : ids) {
            depoloyeeInformationRepository.deleteById(id);
        }
    }

    @Override
    public void download(List<DepoloyeeInformationDto> all, HttpServletResponse response) throws IOException {
        List<Map<String, Object>> list = new ArrayList<>();
        for (DepoloyeeInformationDto depoloyeeInformation : all) {
            Map<String,Object> map = new LinkedHashMap<>();
            map.put(" deployeeNo",  depoloyeeInformation.getDeployeeNo());
            map.put(" deployeeName",  depoloyeeInformation.getDeployeeName());
            map.put(" iphone",  depoloyeeInformation.getIphone());
            map.put(" address",  depoloyeeInformation.getAddress());
            map.put(" email",  depoloyeeInformation.getEmail());
            map.put(" sex",  depoloyeeInformation.getSex());
            map.put(" createBy",  depoloyeeInformation.getCreateBy());
            map.put(" createTime",  depoloyeeInformation.getCreateTime());
            map.put(" updateBy",  depoloyeeInformation.getUpdateBy());
            map.put(" updateTime",  depoloyeeInformation.getUpdateTime());
            map.put(" isDelete",  depoloyeeInformation.getIsDelete());
            list.add(map);
        }
        FileUtil.downloadExcel(list, response);
    }
}