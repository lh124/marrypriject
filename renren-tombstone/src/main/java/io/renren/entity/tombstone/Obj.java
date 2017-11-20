package io.renren.entity.tombstone;

import java.util.List;

public class Obj {
	
	private Integer id;
	
	private Integer parentid;
	
	private List<TombstoneDeadEntity> fm;
	
	private List<Obj> zn;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentid() {
		return parentid;
	}

	public void setParentid(Integer parentid) {
		this.parentid = parentid;
	}

	public List<TombstoneDeadEntity> getFm() {
		return fm;
	}

	public void setFm(List<TombstoneDeadEntity> fm) {
		this.fm = fm;
	}

	public List<Obj> getZn() {
		return zn;
	}

	public void setZn(List<Obj> zn) {
		this.zn = zn;
	}

}
