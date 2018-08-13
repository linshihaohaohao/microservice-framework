package cn.com.bluemoon.admin.domain.model;

import javax.persistence.*;

@Table(name = "admin_sys_menu")
public class AdminSysMenu {
    /**
     * 菜单ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    /**
     * 菜单编码
     */
    @Column(name = "menu_code")
    private String menuCode;

    /**
     * 菜单名称
     */
    @Column(name = "menu_name")
    private String menuName;

    /**
     * 菜单显示的名称
     */
    @Column(name = "menu_label")
    private String menuLabel;

    /**
     * 是否子叶 0否 1是
     */
    private String ifleaf;

    /**
     * 菜单级别
     */
    @Column(name = "menu_level")
    private Integer menuLevel;

    /**
     * 菜单排序
     */
    @Column(name = "display_order")
    private Integer displayOrder;

    /**
     * 菜单功能路径
     */
    @Column(name = "function_path")
    private String functionPath;

    /**
     * 功能描述
     */
    @Column(name = "function_desc")
    private String functionDesc;

    /**
     * 菜单类型 app:app类菜单 pc:pc类菜单
     */
    @Column(name = "menu_type")
    private String menuType;

    /**
     * 图标的路径
     */
    @Column(name = "icon_img")
    private String iconImg;

    /**
     * vw链接(app时连接页面)
     */
    @Column(name = "menu_url")
    private String menuUrl;

    /**
     * 父节点ID
     */
    @Column(name = "parents_id")
    private Integer parentsId;

    /**
     * 父节点编码
     */
    @Column(name = "parents_code")
    private String parentsCode;

    /**
     * 菜单序列
     */
    @Column(name = "menu_seq")
    private String menuSeq;

    /**
     * 所属应用类型
     */
    @Column(name = "app_code")
    private String appCode;

    /**
     * 所属子系统编码
     */
    @Column(name = "sys_code")
    private String sysCode;

    /**
     * 获取菜单ID
     *
     * @return menu_id - 菜单ID
     */
    public Long getMenuId() {
        return menuId;
    }

    /**
     * 设置菜单ID
     *
     * @param menuId 菜单ID
     */
    public void setMenuId(Long menuId) {
        this.menuId = menuId;
    }

    /**
     * 获取菜单编码
     *
     * @return menu_code - 菜单编码
     */
    public String getMenuCode() {
        return menuCode;
    }

    /**
     * 设置菜单编码
     *
     * @param menuCode 菜单编码
     */
    public void setMenuCode(String menuCode) {
        this.menuCode = menuCode;
    }

    /**
     * 获取菜单名称
     *
     * @return menu_name - 菜单名称
     */
    public String getMenuName() {
        return menuName;
    }

    /**
     * 设置菜单名称
     *
     * @param menuName 菜单名称
     */
    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    /**
     * 获取菜单显示的名称
     *
     * @return menu_label - 菜单显示的名称
     */
    public String getMenuLabel() {
        return menuLabel;
    }

    /**
     * 设置菜单显示的名称
     *
     * @param menuLabel 菜单显示的名称
     */
    public void setMenuLabel(String menuLabel) {
        this.menuLabel = menuLabel;
    }

    /**
     * 获取是否子叶 0否 1是
     *
     * @return ifleaf - 是否子叶 0否 1是
     */
    public String getIfleaf() {
        return ifleaf;
    }

    /**
     * 设置是否子叶 0否 1是
     *
     * @param ifleaf 是否子叶 0否 1是
     */
    public void setIfleaf(String ifleaf) {
        this.ifleaf = ifleaf;
    }

    /**
     * 获取菜单级别
     *
     * @return menu_level - 菜单级别
     */
    public Integer getMenuLevel() {
        return menuLevel;
    }

    /**
     * 设置菜单级别
     *
     * @param menuLevel 菜单级别
     */
    public void setMenuLevel(Integer menuLevel) {
        this.menuLevel = menuLevel;
    }

    /**
     * 获取菜单排序
     *
     * @return display_order - 菜单排序
     */
    public Integer getDisplayOrder() {
        return displayOrder;
    }

    /**
     * 设置菜单排序
     *
     * @param displayOrder 菜单排序
     */
    public void setDisplayOrder(Integer displayOrder) {
        this.displayOrder = displayOrder;
    }

    /**
     * 获取菜单功能路径
     *
     * @return function_path - 菜单功能路径
     */
    public String getFunctionPath() {
        return functionPath;
    }

    /**
     * 设置菜单功能路径
     *
     * @param functionPath 菜单功能路径
     */
    public void setFunctionPath(String functionPath) {
        this.functionPath = functionPath;
    }

    /**
     * 获取功能描述
     *
     * @return function_desc - 功能描述
     */
    public String getFunctionDesc() {
        return functionDesc;
    }

    /**
     * 设置功能描述
     *
     * @param functionDesc 功能描述
     */
    public void setFunctionDesc(String functionDesc) {
        this.functionDesc = functionDesc;
    }

    /**
     * 获取菜单类型 app:app类菜单 pc:pc类菜单
     *
     * @return menu_type - 菜单类型 app:app类菜单 pc:pc类菜单
     */
    public String getMenuType() {
        return menuType;
    }

    /**
     * 设置菜单类型 app:app类菜单 pc:pc类菜单
     *
     * @param menuType 菜单类型 app:app类菜单 pc:pc类菜单
     */
    public void setMenuType(String menuType) {
        this.menuType = menuType;
    }

    /**
     * 获取图标的路径
     *
     * @return icon_img - 图标的路径
     */
    public String getIconImg() {
        return iconImg;
    }

    /**
     * 设置图标的路径
     *
     * @param iconImg 图标的路径
     */
    public void setIconImg(String iconImg) {
        this.iconImg = iconImg;
    }

    /**
     * 获取vw链接(app时连接页面)
     *
     * @return menu_url - vw链接(app时连接页面)
     */
    public String getMenuUrl() {
        return menuUrl;
    }

    /**
     * 设置vw链接(app时连接页面)
     *
     * @param menuUrl vw链接(app时连接页面)
     */
    public void setMenuUrl(String menuUrl) {
        this.menuUrl = menuUrl;
    }

    /**
     * 获取父节点ID
     *
     * @return parents_id - 父节点ID
     */
    public Integer getParentsId() {
        return parentsId;
    }

    /**
     * 设置父节点ID
     *
     * @param parentsId 父节点ID
     */
    public void setParentsId(Integer parentsId) {
        this.parentsId = parentsId;
    }

    /**
     * 获取父节点编码
     *
     * @return parents_code - 父节点编码
     */
    public String getParentsCode() {
        return parentsCode;
    }

    /**
     * 设置父节点编码
     *
     * @param parentsCode 父节点编码
     */
    public void setParentsCode(String parentsCode) {
        this.parentsCode = parentsCode;
    }

    /**
     * 获取菜单序列
     *
     * @return menu_seq - 菜单序列
     */
    public String getMenuSeq() {
        return menuSeq;
    }

    /**
     * 设置菜单序列
     *
     * @param menuSeq 菜单序列
     */
    public void setMenuSeq(String menuSeq) {
        this.menuSeq = menuSeq;
    }

    /**
     * 获取所属应用类型
     *
     * @return app_code - 所属应用类型
     */
    public String getAppCode() {
        return appCode;
    }

    /**
     * 设置所属应用类型
     *
     * @param appCode 所属应用类型
     */
    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    /**
     * 获取所属子系统编码
     *
     * @return sys_code - 所属子系统编码
     */
    public String getSysCode() {
        return sysCode;
    }

    /**
     * 设置所属子系统编码
     *
     * @param sysCode 所属子系统编码
     */
    public void setSysCode(String sysCode) {
        this.sysCode = sysCode;
    }
}