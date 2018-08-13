package cn.com.bluemoon.admin.domain.vo;

public class SysDictTypeVo {
    private String dicttypeid;

    private String dicttypename;

    public String getDicttypeid() {
        return dicttypeid;
    }

    public void setDicttypeid(String dicttypeid) {
        this.dicttypeid = dicttypeid == null ? null : dicttypeid.trim();
    }

    public String getDicttypename() {
        return dicttypename;
    }

    public void setDicttypename(String dicttypename) {
        this.dicttypename = dicttypename == null ? null : dicttypename.trim();
    }
}