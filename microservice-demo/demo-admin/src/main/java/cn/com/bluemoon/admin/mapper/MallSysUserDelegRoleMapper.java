package cn.com.bluemoon.admin.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleExampleVo;
import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleKeyVo;
import cn.com.bluemoon.admin.domain.vo.MallSysUserDelegRoleVo;

public interface MallSysUserDelegRoleMapper {
    int countByExample(MallSysUserDelegRoleExampleVo example);

    int deleteByExample(MallSysUserDelegRoleExampleVo example);

    int deleteByPrimaryKey(MallSysUserDelegRoleKeyVo key);

    int insert(MallSysUserDelegRoleVo record);

    int insertSelective(MallSysUserDelegRoleVo record);

    List<MallSysUserDelegRoleVo> selectByExample(MallSysUserDelegRoleExampleVo example);

    MallSysUserDelegRoleVo selectByPrimaryKey(MallSysUserDelegRoleKeyVo key);

    int updateByExampleSelective(@Param("record") MallSysUserDelegRoleVo record, @Param("example") MallSysUserDelegRoleExampleVo example);

    int updateByExample(@Param("record") MallSysUserDelegRoleVo record, @Param("example") MallSysUserDelegRoleExampleVo example);

    int updateByPrimaryKeySelective(MallSysUserDelegRoleVo record);

    int updateByPrimaryKey(MallSysUserDelegRoleVo record);

    
	List<Map<String, String>> getAuthorizedRoleListForDelegation(String userId);

	List<Map<String, String>> getUnauthorizedRoleListForDelegation(String userId);

	List<Map<String, String>> getUnauthorizedRoleListForProxyAuthor(
			@Param("userId")String operatorId, @Param("currentUserId")String currentUserId);
}