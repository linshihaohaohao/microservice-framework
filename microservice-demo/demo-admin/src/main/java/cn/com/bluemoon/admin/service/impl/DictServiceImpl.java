package cn.com.bluemoon.admin.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.alibaba.druid.util.StringUtils;

import cn.com.bluemoon.admin.domain.model.AdminSysDictEntry;
import cn.com.bluemoon.admin.domain.model.AdminSysDictType;
import cn.com.bluemoon.admin.domain.vo.SysDictEntryKeyVo;
import cn.com.bluemoon.admin.domain.vo.SysDictEntryVo;
import cn.com.bluemoon.admin.domain.vo.SysDictInfoVo;
import cn.com.bluemoon.admin.domain.vo.SysDictTypeVo;
import cn.com.bluemoon.admin.mapper.AdminSysDictEntryMapper;
import cn.com.bluemoon.admin.mapper.AdminSysDictTypeMapper;
import cn.com.bluemoon.admin.service.DictService;
import cn.com.bluemoon.redis.service.RedisService;

@Service(value="DictService")
@Transactional
public class DictServiceImpl implements DictService{

	private Logger logger = LoggerFactory.getLogger(DictServiceImpl.class);

//	@Autowired
//	private MallSysDictEntryMapper mallSysDictEntryMapper;	
//	@Autowired
//	private MallSysDictTypeMapper mallSysDictTypeMapper;
	@Autowired
	private AdminSysDictEntryMapper dictEntryMapper;
	@Autowired
	private AdminSysDictTypeMapper dictTypeMapper;
	
	@Autowired
	private RedisService redisService;
	
	@Override
	public List<SysDictInfoVo> getDictInfoByTypePc(String dicttypeId, int status) throws Exception {
//		List<MallSysDictInfoVo> dictInfo = new ArrayList<MallSysDictInfoVo>();		
//		logger.info("------------>>type="+ type);
//		
//		HashMap<String, Object> params = new HashMap<String, Object>();
//		params.put("type", type);
//		params.put("status", status);
//		dictInfo =  mallSysDictEntryMapper.getDictInfoByType(params);
//		return dictInfo;
		List<SysDictInfoVo> dictEntryList = null;
		Example example = new Example(AdminSysDictEntry.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", status);
		if(!StringUtils.isEmpty(dicttypeId)){
			criteria.andEqualTo("dictTypeCode", dicttypeId);
		}
		
		List<AdminSysDictEntry> list = dictEntryMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			dictEntryList = new ArrayList<SysDictInfoVo>();
			for (AdminSysDictEntry temp : list) {
				SysDictInfoVo vo = new SysDictInfoVo();
				vo.setDictID(temp.getDictCode());
				vo.setDictName(temp.getDictName());
				dictEntryList.add(vo);
			}
		}
		
		return dictEntryList;
	}
	
	public List<SysDictTypeVo> getDictTypeList(String dicttypeId, String dicttypeName,int pageNo,int pageSize,String sortName,String sortOrder) throws Exception{
//		MallSysDictTypeExampleVo example = new MallSysDictTypeExampleVo();
//		MallSysDictTypeExampleVo.Criteria criteria = example.createCriteria();
//		PageHelper.startPage(pageNo, pageSize);
//		PageHelper.orderBy(sortName+ " " + sortOrder);
//		if(!StringUtils.isEmpty(dicttypeId)){
//			criteria.andDicttypeidLike("%"+dicttypeId+"%");
//		}
//		if(!StringUtils.isEmpty(dicttypeName)){
//			criteria.andDicttypenameLike("%"+dicttypeName+"%");
//		}
//		return mallSysDictTypeMapper.selectByExample(example);
		
		List<SysDictTypeVo> dictTypeList = null;
		Example example = new Example(AdminSysDictType.class);
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(dicttypeId)){
			criteria.andLike("dictTypeCode", "%"+dicttypeId+"%");
		}
		if(!StringUtils.isEmpty(dicttypeName)){
			criteria.andLike("dictTypeBane", "%"+dicttypeName+"%");
		}
		int offset = pageNo * pageSize;
		RowBounds rowBounds = new RowBounds(offset, pageSize);
//		example.setOrderByClause(sortName + " " + sortOrder);
		
		List<AdminSysDictType> list = dictTypeMapper.selectByExampleAndRowBounds(example, rowBounds);
		if (list != null && list.size() > 0) {
			dictTypeList = new ArrayList<SysDictTypeVo>();
			for (AdminSysDictType temp : list) {
				SysDictTypeVo vo = new SysDictTypeVo();
				vo.setDicttypeid(temp.getDictTypeCode());
				vo.setDicttypename(temp.getDictTypeBane());
				dictTypeList.add(vo);
			}
		}
		
		return dictTypeList;
	}
	
	public int getDictTypeCount(String dicttypeId, String dicttypeName) throws Exception{
//		MallSysDictTypeExampleVo example = new MallSysDictTypeExampleVo();
//		MallSysDictTypeExampleVo.Criteria criteria = example.createCriteria();
//		if(!StringUtils.isEmpty(dicttypeId)){
//			criteria.andDicttypeidLike("%"+dicttypeId+"%");
//		}
//		if(!StringUtils.isEmpty(dicttypeName)){
//			criteria.andDicttypenameLike("%"+dicttypeName+"%");
//		}
//		return mallSysDictTypeMapper.countByExample(example);
		
		Example example = new Example(AdminSysDictType.class);
		Criteria criteria = example.createCriteria();
		if(!StringUtils.isEmpty(dicttypeId)){
			criteria.andLike("dictTypeCode", "%"+dicttypeId+"%");
		}
		if(!StringUtils.isEmpty(dicttypeName)){
			criteria.andLike("dictTypeBane", "%"+dicttypeName+"%");
		}
		
		return dictTypeMapper.selectCountByExample(example);
	}

	@Override
	public boolean addDictType(SysDictTypeVo mallSysDictType) throws Exception {
		if( mallSysDictType == null ){
			return false;
		}
			
//		mallSysDictType.setDicttypeid(mallSysDictType.getDicttypeid().toUpperCase());
//		int flag = mallSysDictTypeMapper.insert(mallSysDictType);
		
		AdminSysDictType entity = new AdminSysDictType();
		entity.setDictTypeCode(mallSysDictType.getDicttypeid().trim().toUpperCase());
		entity.setDictTypeBane(mallSysDictType.getDicttypename().trim());
	
		int flag = dictTypeMapper.insertSelective(entity);
		if( flag > 0 ){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean addDictEntry(SysDictEntryVo mallSysDictEntry) throws Exception {
		if( mallSysDictEntry == null ){
			return false;
		}
			
//		mallSysDictEntry.setDictid(mallSysDictEntry.getDictid());
//		int flag = mallSysDictEntryMapper.insert(mallSysDictEntry);
		AdminSysDictEntry entity = new AdminSysDictEntry();
		entity.setDictTypeCode(mallSysDictEntry.getDicttypeid().trim().toUpperCase());
		entity.setDictCode(mallSysDictEntry.getDictid().trim().toUpperCase());
		entity.setDictName(mallSysDictEntry.getDictname().trim());
		entity.setStatus(mallSysDictEntry.getStatus());
		entity.setSortNo(mallSysDictEntry.getSortno());
		
		int flag = dictEntryMapper.insertSelective(entity);
		if( flag > 0 ){
			return true;
		}else{
			return false;
		}
	}

	@Override
	public void deleteDictType(List<SysDictTypeVo> list) throws Exception {
		if( list.size() > 0 ){
			for( SysDictTypeVo mallSysDictType : list ){
				//先删除业务字典的子选项
				deleteDictEntry(mallSysDictType.getDicttypeid());
				//删除业务字典类型
				dictTypeMapper.deleteByPrimaryKey(mallSysDictType.getDicttypeid());
				//如果存在缓存，则删除缓存
				if( redisService.exists("dict_" + mallSysDictType.getDicttypeid() )){
					redisService.del("dict_" + mallSysDictType.getDicttypeid());
				}
			}
		}
	}

	@Override
	public void deleteDictEntry(List<SysDictEntryVo> list)
			throws Exception {
		if( list.size() > 0 ){
			for( SysDictEntryVo mallSysDictEntry : list ){
				deleteDictEntry(mallSysDictEntry.getDicttypeid(), mallSysDictEntry.getDictid());
			}
		}
	}

	@Override
	public boolean deleteDictEntry(String dicttypeid, String dictid)
			throws Exception {
		SysDictEntryKeyVo key = new SysDictEntryKeyVo();
		key.setDictCode(dictid);
		key.setDictTypeCode(dicttypeid);
		int flag = dictEntryMapper.deleteByPrimaryKey(key);
		if( flag > 0 ){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean deleteDictEntry(String dicttypeid) throws Exception {
//		MallSysDictEntryExampleVo example = new MallSysDictEntryExampleVo();
//		MallSysDictEntryExampleVo.Criteria criteria = example.createCriteria();
//		criteria.andDicttypeidEqualTo(dicttypeid);
//		int flag = mallSysDictEntryMapper.deleteByExample(example);
		Example example = new Example(AdminSysDictEntry.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("dictTypeCode", dicttypeid);
		
		int flag = dictEntryMapper.deleteByExample(example);
		if( flag > 0 ){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean reloadDictCache(String dicttypeid) throws Exception {
		if( dicttypeid == null || "".equals(dicttypeid) ){
			return false;
		}else{
			//如果缓存里面存在该数据
			//先删除缓存里面的业务字典
			if( redisService.exists("dict_" + dicttypeid ) ){
				redisService.del("dict_" + dicttypeid);
			}
			//调用该方法自动将数据刷进缓存
			getDictInfoByTypePc(dicttypeid, 1);
			return true;
		}
			
	}


	@Override
	public boolean updateDictType(SysDictTypeVo mallSysDictType)
			throws Exception {
//		int flag = mallSysDictTypeMapper.updateByPrimaryKey(mallSysDictType);
		
		AdminSysDictType entity = new AdminSysDictType();
		entity.setDictTypeCode(mallSysDictType.getDicttypeid());
		entity.setDictTypeBane(mallSysDictType.getDicttypename());
		
		int flag = dictTypeMapper.updateByPrimaryKeySelective(entity);
		if( flag > 0 ){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean updateDictEntry(SysDictEntryVo mallSysDictEntry)
			throws Exception {
//		int flag = mallSysDictEntryMapper.updateByPrimaryKeySelective(mallSysDictEntry);
		
		AdminSysDictEntry entity = new AdminSysDictEntry();
		entity.setDictCode(mallSysDictEntry.getDictid());
		entity.setDictName(mallSysDictEntry.getDictname());
		entity.setDictTypeCode(mallSysDictEntry.getDicttypeid());
		entity.setSortNo(mallSysDictEntry.getSortno());
		entity.setStatus(mallSysDictEntry.getStatus());
		
		int flag = dictEntryMapper.updateByPrimaryKeySelective(entity);
		if( flag > 0 ){
			return true;
		}else{
			return false;
		}
	}


	@Override
	public boolean checkDictEntry(String dicttypeid, String dictid)
			throws Exception {
		SysDictEntryKeyVo key = new SysDictEntryKeyVo();
		key.setDictCode(dictid);
		key.setDictTypeCode(dicttypeid);
		AdminSysDictEntry mallSysDictEntry = dictEntryMapper.selectByPrimaryKey(key);
		if( mallSysDictEntry == null ){
			return false;
		}else{
			return true;
		}
	}
	
	@Override
	public SysDictEntryVo getMallSysDictByDistTypeIdAndDictId(String distTypeId, String dictId) {
//		MallSysDictEntryExampleVo mallSysDictEntryExample=new MallSysDictEntryExampleVo();
//		mallSysDictEntryExample.createCriteria().andDicttypeidEqualTo(distTypeId).andDictidEqualTo(dictId).andStatusEqualTo(1);
//		List<MallSysDictEntryVo> list=mallSysDictEntryMapper.selectByExample(mallSysDictEntryExample);
		
		Example example = new Example(AdminSysDictEntry.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", 1);
		criteria.andEqualTo("dictTypeCode", distTypeId);
		criteria.andEqualTo("dictCode", dictId);
		
		List<AdminSysDictEntry> list = dictEntryMapper.selectByExample(example);
		
		if(list!=null&&list.size()>0){
//			MallSysDictEntryVo mallSysDictEntry=list.get(0);
			AdminSysDictEntry temp = list.get(0);
			SysDictEntryVo mallSysDictEntry = new SysDictEntryVo();
			mallSysDictEntry.setDictid(temp.getDictCode());
			mallSysDictEntry.setDictname(temp.getDictName());
			mallSysDictEntry.setDicttypeid(temp.getDictTypeCode());
			mallSysDictEntry.setSortno(temp.getSortNo());
			mallSysDictEntry.setStatus(temp.getStatus());
			return mallSysDictEntry;
		}
		return null;
	}
	
	public List<SysDictEntryVo> getDictEntryList(String dicttypeId,int pageNo,int pageSize,String sortName,String sortOrder) throws Exception{
//		MallSysDictEntryExampleVo example = new MallSysDictEntryExampleVo();
//		MallSysDictEntryExampleVo.Criteria criteria = example.createCriteria();
//		PageHelper.startPage(pageNo, pageSize);
//		PageHelper.orderBy(sortName+ " " + sortOrder);
//		if(!StringUtils.isEmpty(dicttypeId)){
//			criteria.andDicttypeidEqualTo(dicttypeId);
//		}
//		return mallSysDictEntryMapper.selectByExample(example);
		
		List<SysDictEntryVo> dictEntryList = null;
		Example example = new Example(AdminSysDictEntry.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", 1);
		if(!StringUtils.isEmpty(dicttypeId)){
			criteria.andEqualTo("dictTypeCode", dicttypeId);
		}
		int offset = pageNo * pageSize;
		RowBounds rowBounds = new RowBounds(offset, pageSize);
//		example.setOrderByClause(sortName + " " + sortOrder);
		
		List<AdminSysDictEntry> list = dictEntryMapper.selectByExampleAndRowBounds(example, rowBounds);
		if (list != null && list.size() > 0) {
			dictEntryList = new ArrayList<SysDictEntryVo>();
			for (AdminSysDictEntry temp : list) {
				SysDictEntryVo vo = new SysDictEntryVo();
				vo.setDictid(temp.getDictCode());
				vo.setDictname(temp.getDictName());
				vo.setDicttypeid(temp.getDictTypeCode());
				vo.setSortno(temp.getSortNo());
				vo.setStatus(temp.getStatus());
				dictEntryList.add(vo);
			}
		}
		
		return dictEntryList;
	}
	
	public List<SysDictEntryVo> getDictEntryListToSelect(String dicttypeId) throws Exception{
//		MallSysDictEntryExampleVo example = new MallSysDictEntryExampleVo();
//		MallSysDictEntryExampleVo.Criteria criteria = example.createCriteria();
//		PageHelper.orderBy("sortno asc");
//		if(!StringUtils.isEmpty(dicttypeId)){
//			criteria.andDicttypeidEqualTo(dicttypeId).andStatusEqualTo(1);
//		}
//		return mallSysDictEntryMapper.selectByExample(example);
		
		List<SysDictEntryVo> dictEntryList = null;
		Example example = new Example(AdminSysDictEntry.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", 1);
		if(!StringUtils.isEmpty(dicttypeId)){
			criteria.andEqualTo("dictTypeCode", dicttypeId);
		}
		
		List<AdminSysDictEntry> list = dictEntryMapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			dictEntryList = new ArrayList<SysDictEntryVo>();
			for (AdminSysDictEntry temp : list) {
				SysDictEntryVo vo = new SysDictEntryVo();
				vo.setDictid(temp.getDictCode());
				vo.setDictname(temp.getDictName());
				vo.setDicttypeid(temp.getDictTypeCode());
				vo.setSortno(temp.getSortNo());
				vo.setStatus(temp.getStatus());
				dictEntryList.add(vo);
			}
		}
		
		return dictEntryList;
	}
	
	public int getDictEntryCount(String dicttypeId) throws Exception{
//		MallSysDictEntryExampleVo example = new MallSysDictEntryExampleVo();
//		MallSysDictEntryExampleVo.Criteria criteria = example.createCriteria();
//		if(!StringUtils.isEmpty(dicttypeId)){
//			criteria.andDicttypeidEqualTo(dicttypeId);
//		}
//		return mallSysDictEntryMapper.countByExample(example);
		
		List<SysDictEntryVo> dictEntryList = null;
		Example example = new Example(AdminSysDictEntry.class);
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("status", 1);
		if(!StringUtils.isEmpty(dicttypeId)){
			criteria.andEqualTo("dictTypeCode", dicttypeId);
		}
		return dictEntryMapper.selectCountByExample(example);
	}
}
