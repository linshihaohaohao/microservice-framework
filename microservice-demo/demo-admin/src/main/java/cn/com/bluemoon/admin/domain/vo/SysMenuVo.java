package cn.com.bluemoon.admin.domain.vo;

public class SysMenuVo {
    private Integer menuId;

    private String menuCode;

    private String menuName;

    private String menuLabel;

    private String ifleaf;

    private Integer menuLevel;

    private Integer displayOrder;

    private String functionPath;

    private String functionDesc;

    private String menuType;

    private String iconImg;

    private String menuUrl;

    private Integer parentsId;

    private String parentsCode;

    private String menuSeq;

    private String appCode;

    public Integer getMenuId() {
        return menuId;
    }

    public void setMenuId(Integer menuId) {
        this.menuId = menuId;
    }

    public String getMenuCode() {
        return menuCode;
    }

    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode == null ? null : menuCode.trim();
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName == null ? null : menuName.trim();
    }

    public String getMenuLabel() {
        return menuLabel;
    }

    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel == null ? null : menuLabel.trim();
    }

    public String getIfleaf() {
        return ifleaf;
    }

    public void setIfleaf(String ifleaf) {
        this.ifleaf = ifleaf == null ? null : ifleaf.trim();
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
        this.functionPath = functionPath == null ? null : functionPath.trim();
    }

    public String getFunctionDesc() {
        return functionDesc;
    }

    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc == null ? null : functionDesc.trim();
    }

    public String getMenuType() {
        return menuType;
    }

    public void setMenuType(String menuType) {
        this.menuType = menuType == null ? null : menuType.trim();
    }

    public String getIconImg() {
        return iconImg;
    }

    public void setIconImg(String iconImg) {
        this.iconImg = iconImg == null ? null : iconImg.trim();
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl == null ? null : menuUrl.trim();
    }

    public Integer getParentsId() {
        return parentsId;
    }

    public void setParentsId(Integer parentsId) {
        this.parentsId = parentsId;
    }

    public String getParentsCode() {
        return parentsCode;
    }

    public void setParentsCode(String parentsCode) {
        this.parentsCode = parentsCode == null ? null : parentsCode.trim();
    }

    public String getMenuSeq() {
        return menuSeq;
    }

    public void setMenuSeq(String menuSeq) {
        this.menuSeq = menuSeq == null ? null : menuSeq.trim();
    }

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode == null ? null : appCode.trim();
    }
}