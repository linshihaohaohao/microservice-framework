package cn.com.bluemoon.admin.mapper;

import java.util.HashMap;
import java.util.List;

import cn.com.bluemoon.admin.domain.model.AdminSysMenu;
import cn.com.bluemoon.admin.domain.vo.SysMenuVo;
import tk.mybatis.mapper.common.Mapper;

public interface AdminSysMenuMapper extends Mapper<AdminSysMenu> {
	
	List<SysMenuVo> getMenuTree(HashMap<String, Object> params);
    
    int checkUrlAuth(HashMap<String, Object> params);
    
    List<SysMenuVo> getMenuTreeWithoutRole(HashMap<String, Object> params);

    List<SysMenuVo> queryMenuDataById(HashMap<String, Object> params);
}