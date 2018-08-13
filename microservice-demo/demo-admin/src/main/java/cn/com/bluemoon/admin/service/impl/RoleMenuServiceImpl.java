package cn.com.bluemoon.admin.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;
import cn.com.bluemoon.admin.domain.model.AdminSysRoleMenu;
import cn.com.bluemoon.admin.domain.vo.SysRoleMenuKeyVo;
import cn.com.bluemoon.admin.domain.vo.SysRoleMenuVo;
import cn.com.bluemoon.admin.mapper.AdminSysRoleMenuMapper;
import cn.com.bluemoon.admin.service.RoleMenuService;
import cn.com.bluemoon.utils.copy.BeanCopyUtils;

@Service("roleMenuService")
@Transactional
public class RoleMenuServiceImpl implements RoleMenuService{

	@Autowired
	private AdminSysRoleMenuMapper sysRoleMenuMapper;
	
	@Override
	public void deleteRoleMenuByKey(String roleId, String menuId) {
		SysRoleMenuKeyVo key=new SysRoleMenuKeyVo();
		key.setMenuId(menuId);
		key.setRoleId(roleId);
//		roleMenuMapper.deleteByPrimaryKey(key);//删除授权
		sysRoleMenuMapper.deleteByPrimaryKey(key);
	}
	
	@Override
	public void deleteRoleMenuByRoleId(String roleId) {
//		MallSysRoleMenuExampleVo example=new MallSysRoleMenuExampleVo();
//		if(roleId != null && !"".equals(roleId)){
//			example.createCriteria().andRoleIdEqualTo(roleId);
//			roleMenuMapper.deleteByExample(example);
//		}
		
		Example example = new Example(AdminSysRoleMenu.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("roleId", roleId);
		sysRoleMenuMapper.deleteByExample(example);
	}
	
	@Override
	public void deleteRoleMenuByRoleIds(List<String> roleIds) {
		
		Example example = new Example(AdminSysRoleMenu.class);
		Criteria criteria = example.createCriteria();
		criteria.andIn("roleId", roleIds);
		sysRoleMenuMapper.deleteByExample(example);
	}

	@Override
	public void saveRoleMenu(SysRoleMenuVo roleMenu) {
//		roleMenuMapper.save(roleMenu);
		
		AdminSysRoleMenu entity = new AdminSysRoleMenu();
		BeanCopyUtils.copy(roleMenu, entity);
		entity.setMenuId(Long.valueOf(roleMenu.getMenuId()));
		entity.setRoleId(Long.valueOf(roleMenu.getRoleId()));
		
		sysRoleMenuMapper.insert(entity);
	}

	@Override
	public String getMenuListByRoleId(String roleId) {
//		List<MallSysRoleMenuVo> result=new ArrayList<MallSysRoleMenuVo>();
//		MallSysRoleMenuExampleVo example=new MallSysRoleMenuExampleVo();
//		example.createCriteria().andRoleIdEqualTo(roleId);
//		result.addAll(roleMenuMapper.selectByExample(example));
//		String resultStr="";
//		for(MallSysRoleMenuVo roleMenu:result){
//			String menuId=roleMenu.getMenuId();
//			resultStr=resultStr+","+menuId;
//		}
//		return resultStr;
		
		Example example = new Example(AdminSysRoleMenu.class);
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(roleId)){
			criteria.andEqualTo("roleId", roleId);
		}
		List<AdminSysRoleMenu> list = sysRoleMenuMapper.selectByExample(example);
		String resultStr="";
		if (list != null && list.size() > 0) {
			for (AdminSysRoleMenu temp : list) {
				resultStr += "," + temp.getMenuId();
			}
		}
		return resultStr;
	}
	
	@Override
    public void deleteRoleMenuByMenuId(String menuId) {
//        MallSysRoleMenuExampleVo example=new MallSysRoleMenuExampleVo();
//        if(StringUtils.isNotBlank(menuId)){
//            example.createCriteria().andMenuIdEqualTo(menuId);
//            roleMenuMapper.deleteByExample(example);
//        }
    	Example example = new Example(AdminSysRoleMenu.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("menuId", menuId);
		sysRoleMenuMapper.deleteByExample(example);
    }
}
