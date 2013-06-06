package cn.npnt.tiaps.base.vo;

import org.apache.commons.lang.StringUtils;

import cn.npnt.tiaps.constants.Constants;

public class QueryParamVO {

	public String accessId;//用户id
	public Long userId;//用户id
	public Integer id; //id
	public Integer pageSize;	//每一页展示多少条记录
	public Integer pageNO;	//需要的数据是第几页
	public String orderBy;	//按照什么来排序
	public String desc;	//正序还是倒序
	
	public Long sinceId;//欲寻找到比这个id还要小的
	public Long maxId;//欲寻找到比这个id还要大的
	
	public QueryParamVO(){
		this.pageSize = Constants.DEFAULT_RECOMMENDERS_NUM;
		this.pageNO = 1;
		this.orderBy = " id ";
		this.desc = " desc ";
	}
	
	public QueryParamVO(Integer id,Integer pageSize,Integer pageNO,String orderBy,String desc){
		if(id != null){
			this.id = id;
		}
		if(pageSize != null){
			this.pageSize = pageSize;
		}else{
			this.pageSize =  Constants.DEFAULT_RECOMMENDERS_NUM;
		}
		if(pageNO != null){
			this.pageNO = pageNO;
		}else{
			this.pageNO = 1;
		}
		if(StringUtils.isNotBlank(orderBy)){
			this.orderBy = orderBy;
		}else{
			this.orderBy = " id ";
		}
		if(StringUtils.isNotBlank(desc)){
			this.desc = desc;
		}else{
			this.desc = " desc ";
		}
	}
}
