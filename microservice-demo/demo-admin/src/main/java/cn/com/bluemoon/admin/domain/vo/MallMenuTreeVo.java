package cn.com.bluemoon.admin.domain.vo;

import java.util.List;

/**
 * Created by lsh on 2017/8/16.
 */
public class MallMenuTreeVo {
    private String id;
    private String text;
    private String value;
    private String img;
    private String parentnodes;
    private boolean showcheck;
    private boolean isexpand;
    private boolean complete;
    private boolean hasChildren;
    private List<MallMenuTreeVo> ChildNodes ;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getParentnodes() {
        return parentnodes;
    }

    public void setParentnodes(String parentnodes) {
        this.parentnodes = parentnodes;
    }

    public boolean isShowcheck() {
        return showcheck;
    }

    public void setShowcheck(boolean showcheck) {
        this.showcheck = showcheck;
    }

    public boolean isIsexpand() {
        return isexpand;
    }

    public void setIsexpand(boolean isexpand) {
        this.isexpand = isexpand;
    }

    public boolean isComplete() {
        return complete;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

    public boolean isHasChildren() {
        return hasChildren;
    }

    public void setHasChildren(boolean hasChildren) {
        this.hasChildren = hasChildren;
    }

    public List<MallMenuTreeVo> getChildNodes() {
        return ChildNodes;
    }

    public void setChildNodes(List<MallMenuTreeVo> childNodes) {
        ChildNodes = childNodes;
    }
}
