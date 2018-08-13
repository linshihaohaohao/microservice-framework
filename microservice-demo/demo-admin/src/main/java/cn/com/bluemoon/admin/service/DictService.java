package cn.com.bluemoon.admin.service;

import java.util.List;

import cn.com.bluemoon.admin.domain.vo.SysDictEntryVo;
import cn.com.bluemoon.admin.domain.vo.SysDictInfoVo;
import cn.com.bluemoon.admin.domain.vo.SysDictTypeVo;

public interface DictService {

	/**
	 * pc端用获取业务字典   
	 * <p>Description:</p>
	 * DictService.java  
	 * return:List<DictInfo>  dictID  dictName
	 */
	public List<SysDictInfoVo> getDictInfoByTypePc(String type, int status) throws Exception;
	
	/**
	 * <p>Description:获取业务字典类型</p>
	 * DictService.java
	 * return:void
	 */
	public List<SysDictTypeVo> getDictTypeList(String dicttypeId, String dicttypeName,int pageNo,int pageSize,String sortName,String sortOrder) throws Exception;
	
	/**
	 * <p>Description:获取业务字典类型总数</p>
	 * DictService.java
	 * return:void
	 */
	public int getDictTypeCount(String dicttypeId, String dicttypeName) throws Exception;
	
	/**
	 * <p>Description:添加业务字典类型</p>
	 * DictService.java
	 * return:void
	 */
	public boolean addDictType(SysDictTypeVo mallSysDictType) throws Exception;
	
	/**
	 * <p>Description:添加业务字典选项</p>
	 * DictService.java
	 * return:void
	 */
	public boolean addDictEntry(SysDictEntryVo mallSysDictEntry) throws Exception;
	
	/**
	 * <p>Description:删除业务字典类型，同时删除业务字典全部子选项</p>
	 * DictService.java
	 * return:void
	 */
	public void deleteDictType(List<SysDictTypeVo> list) throws Exception;
	
	/**
	 * <p>Description:删除业务字典子选项</p>
	 * @param DICTTYPEID   字典类型
	 * @param DICTID   子选项ID
	 * DictService.java
	 * return:void
	 */
	public void deleteDictEntry(List<SysDictEntryVo> list) throws Exception;
	
	/**
	 * <p>Description:根据业务字典子类型和子类型删除字典子选项</p>
	 * DictService.java
	 * return:boolean
	 */
	public boolean deleteDictEntry(String DICTTYPEID, String DICTID) throws Exception;
	
	/**
	 * <p>Description:根据业务字典类型删除子选项</p>
	 * DictService.java
	 * return:boolean
	 */
	public boolean deleteDictEntry(String DICTTYPEID) throws Exception;
	
	/**
	 * <p>Description:刷新业务字典的缓存</p>
	 * DictService.java
	 * return:boolean
	 */
	public boolean reloadDictCache(String DICTTYPEID) throws Exception;
	
	/**
	 * <p>Description:更新业务字典名称</p>
	 * DictService.java
	 * return:boolean
	 */
	public boolean updateDictType(SysDictTypeVo mallSysDictType) throws Exception;
	
	/**
	 * <p>Description:更新业务字典子选项</p>
	 * DictService.java
	 * return:boolean
	 */
	public boolean updateDictEntry(SysDictEntryVo mallSysDictEntry) throws Exception;
	
	/**
	 * <p>Description:检查业务字典项是否已经存在</p>
	 * DictService.java
	 * return:boolean
	 */
	public boolean checkDictEntry( String DICTTYPEID, String DICTID ) throws Exception;
	
	/**
	 * 根据业务字典编码和业务子类型编码获取有效的业务字典信息
	 * @param distTypeId 业务字典编码 
	 * @param dictId 业务子类型编码
	 */
	public SysDictEntryVo getMallSysDictByDistTypeIdAndDictId(String distTypeId,String dictId);
	
	/**
	 * 根据业务字典类型获取业务字典项信息
	 * DictService.java
	 * return:void
	 */
	public List<SysDictEntryVo> getDictEntryList(String dicttypeId,int pageNo,int pageSize,String sortName,String sortOrder) throws Exception;
	
	public List<SysDictEntryVo> getDictEntryListToSelect(String dicttypeId) throws Exception;
	
	public int getDictEntryCount(String dicttypeId) throws Exception;
	
}
