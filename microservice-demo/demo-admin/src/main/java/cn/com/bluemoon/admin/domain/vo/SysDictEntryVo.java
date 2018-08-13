package cn.com.bluemoon.admin.domain.vo;

public class SysDictEntryVo {
	
	
    private String dictname;

    private Integer status;

    private Integer sortno;
    
    private String dicttypeid;

    private String dictid;

    public String getDicttypeid() {
        return dicttypeid;
    }

    public void setDicttypeid(String dicttypeid) {
        this.dicttypeid = dicttypeid == null ? null : dicttypeid.trim();
    }

    public String getDictid() {
        return dictid;
    }

    public void setDictid(String dictid) {
        this.dictid = dictid == null ? null : dictid.trim();
    }

    public String getDictname() {
        return dictname;
    }

    public void setDictname(String dictname) {
        this.dictname = dictname == null ? null : dictname.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getSortno() {
        return sortno;
    }

    public void setSortno(Integer sortno) {
        this.sortno = sortno;
    }
}