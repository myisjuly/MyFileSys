package com.myFileSys.common.model;

import com.jfinal.plugin.activerecord.Db;
import com.myFileSys.common.model.base.BaseGroup;

/**
 * Generated by JFinal.
 */
@SuppressWarnings("serial")
public class Group extends BaseGroup<Group> {
	
	public int getMaxId(){
		return Db.queryInt("select max(id) from t_group");
	}
}
