package cn.com.bluemoon.admin.domain.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title:MenuTree.java</p>
 * <p>Description:菜单树展现实体</p>
 * @author Guoqing
 * @date 2016-3-29
 */
public class MenuTreeVo implements Serializable {
	
	private List<MenuTreeNode> menuTreeRootNodeList;
	
	public MenuTreeVo(){
		this.menuTreeRootNodeList =  new ArrayList();
	}
	
	public void addMenuTreeRootNode(MenuTreeNode menuTreeNode){
		this.menuTreeRootNodeList.add(menuTreeNode);
	}
	
	public List<MenuTreeNode> getMenuTreeRootNodeList(){
		return this.menuTreeRootNodeList;
	}
	
	public static class MenuTreeNode implements Serializable{
		
		private List<MenuTreeNode> childrenMenuTreeNodeList = null;
		private transient MenuTreeNode parentMenuTreeNode;
		private Integer menuId;
		private String menuName;
		private String menuCode;
		private String menuLabel;
		private Integer ifleaf;
		private Integer menuLevel;
		private Integer displayOrder;
		private String functionPath;
		private String functionDesc;
		private String menuType;
		private String parentsId;
		private String parentsCode;
		private String menuSeq;
		private String appCode;
		
		public MenuTreeNode()
	    {
	    }

	    public MenuTreeNode(Integer menuId, MenuTreeNode parentMenuTreeNode) {
	      this.menuId = menuId;
	      this.parentMenuTreeNode = parentMenuTreeNode;
	      if (this.parentMenuTreeNode != null)
	        this.parentMenuTreeNode.addChildMenuTreeNode(this);
	    }
	    
	    public void addChildMenuTreeNode(MenuTreeNode menuTreeNode) {
	        if (this.childrenMenuTreeNodeList == null) {
	          this.childrenMenuTreeNodeList = new ArrayList();
	        }
	        this.childrenMenuTreeNodeList.add(menuTreeNode);
	      }

		
		public List<MenuTreeNode> getChildrenMenuTreeNodeList() {
			return childrenMenuTreeNodeList;
		}
		public void setChildrenMenuTreeNodeList(
				List<MenuTreeNode> childrenMenuTreeNodeList) {
			this.childrenMenuTreeNodeList = childrenMenuTreeNodeList;
		}
		public MenuTreeNode getParentMenuTreeNode() {
			return parentMenuTreeNode;
		}
		public void setParentMenuTreeNode(MenuTreeNode parentMenuTreeNode) {
			this.parentMenuTreeNode = parentMenuTreeNode;
		}
		public Integer getMenuId() {
			return menuId;
		}
		public void setMenuId(Integer menuId) {
			this.menuId = menuId;
		}
		public String getMenuName() {
			return menuName;
		}
		public void setMenuName(String menuName) {
			this.menuName = menuName;
		}
		public String getMenuCode() {
			return menuCode;
		}
		public void setMenuCode(String menuCode) {
			this.menuCode = menuCode;
		}
		public String getMenuLabel() {
			return menuLabel;
		}
		public void setMenuLabel(String menuLabel) {
			this.menuLabel = menuLabel;
		}
		public Integer getIfleaf() {
			return ifleaf;
		}
		public void setIfleaf(Integer ifleaf) {
			this.ifleaf = ifleaf;
		}
		public Integer getMenuLevel() {
			return menuLevel;
		}
		public void setMenuLevel(Integer menuLevel) {
			this.menuLevel = menuLevel;
		}
		public Integer getDisplayOrder() {
			return displayOrder;
		}
		public void setDisplayOrder(Integer displayOrder) {
			this.displayOrder = displayOrder;
		}
		public String getFunctionPath() {
			return functionPath;
		}
		public void setFunctionPath(String functionPath) {
			this.functionPath = functionPath;
		}
		public String getFunctionDesc() {
			return functionDesc;
		}
		public void setFunctionDesc(String functionDesc) {
			this.functionDesc = functionDesc;
		}
		public String getMenuType() {
			return menuType;
		}
		public void setMenuType(String menuType) {
			this.menuType = menuType;
		}
		public String getParentsId() {
			return parentsId;
		}
		public void setParentsId(String parentsId) {
			this.parentsId = parentsId;
		}
		public String getParentsCode() {
			return parentsCode;
		}
		public void setParentsCode(String parentsCode) {
			this.parentsCode = parentsCode;
		}
		public String getMenuSeq() {
			return menuSeq;
		}
		public void setMenuSeq(String menuSeq) {
			this.menuSeq = menuSeq;
		}
		public String getAppCode() {
			return appCode;
		}
		public void setAppCode(String appCode) {
			this.appCode = appCode;
		}
	
		
	}

}
