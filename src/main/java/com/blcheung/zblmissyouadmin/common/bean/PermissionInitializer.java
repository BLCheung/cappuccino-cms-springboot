package com.blcheung.zblmissyouadmin.common.bean;

import com.blcheung.zblmissyouadmin.model.CmsPermissionDO;
import com.blcheung.zblmissyouadmin.service.CmsPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;

/**
 * 权限初始化器
 *
 * @author BLCheung
 * @date 2022/1/7 9:15 下午
 */
@Component
public class PermissionInitializer implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    private CmsPermissionService cmsPermissionService;

    @Autowired
    private PermissionMetaCollector collector;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        this.initPermissions();
        this.removeNotUsedPermission();
    }


    private void initPermissions() {
        this.collector.getRouterMap()
                      .values()
                      .forEach(this::createPermissionIfNotExist);
    }

    private void removeNotUsedPermission() {
        List<CmsPermissionDO> cmsPermissionList = this.cmsPermissionService.list();
        Collection<RouterInfo> routerInfoList = this.collector.getRouterMap()
                                                              .values();
        // 把数据库内依然开启的但现在想关闭的权限给关闭
        cmsPermissionList.forEach(cmsPermissionDO -> {
            this.closeUnusedPermission(cmsPermissionDO, routerInfoList);
        });
    }

    private void createPermissionIfNotExist(RouterInfo routerInfo) {
        String module = routerInfo.getModule();
        String router = routerInfo.getRouter();

        CmsPermissionDO cmsPermissionDO = this.cmsPermissionService.lambdaQuery()
                                                                   .eq(CmsPermissionDO::getModule, module)
                                                                   .eq(CmsPermissionDO::getName, router)
                                                                   .one();
        if (cmsPermissionDO == null) {
            CmsPermissionDO newPermissionDO = CmsPermissionDO.builder()
                                                             .module(module)
                                                             .name(router)
                                                             .mount(true)
                                                             .build();
            this.cmsPermissionService.save(newPermissionDO);
        }

        // 把现在想开启但是存在数据库内是关闭了的权限给开启
        if (cmsPermissionDO != null && !cmsPermissionDO.getMount()) {
            cmsPermissionDO.setMount(true);
            this.cmsPermissionService.updateById(cmsPermissionDO);
        }
    }

    private void closeUnusedPermission(CmsPermissionDO cmsPermissionDO, Collection<RouterInfo> routerInfoList) {
        boolean shouldMount = routerInfoList.stream()
                                            .anyMatch(routerInfo -> this.isSamePermission(cmsPermissionDO, routerInfo));
        if (!shouldMount) {
            cmsPermissionDO.setMount(false);
            this.cmsPermissionService.updateById(cmsPermissionDO);
        }
    }

    private Boolean isSamePermission(CmsPermissionDO cmsPermissionDO, RouterInfo routerInfo) {
        return StringUtils.equals(cmsPermissionDO.getModule(), routerInfo.getModule()) &&
               StringUtils.equals(cmsPermissionDO.getName(), routerInfo.getRouter());
    }
}
