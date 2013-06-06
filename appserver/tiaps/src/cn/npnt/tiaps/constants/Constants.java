package cn.npnt.tiaps.constants;

import java.io.File;

/**
* @author lijia 2011-7-4
* 
*/
public interface Constants {
	/************* Path ******************/
	final static String PHOTO_PATH = File.separator+"photos"+File.separator;
	final static  String PHOTO_URL = "/"+"photos"+"/";
	
	
	/************* Vote ******************/
	final static String WEAR = "穿戴 #";
	final static String PENDING = "待定";
	final static String NONE = "暂无";
	final static String LOVE_IT = "好";
	final static String RETURN_IT = "不好";
	
	final static String GOOD_WORDS = "我喜欢这张照片！";
	final static String BAD_WORDS = "我不喜欢这张照片！";
	
	
	/************* Service Code ******************/
	final static String RESPONSE_CODE_SUCCESS = "S01";
	final static String RESPONSE_CODE_FAIL = "F01";

	//look不存在
	final static String MESSAGE_CODE_LOOK_NOENTITY = "L01";
	//profile不存在
	final static String MESSAGE_CODE_PROFILE_NOENTITY = "P01";
	final static String MESSAGE_CODE_PROFILE_EXIST = "P02";
	//review不存在
	final static String MESSAGE_CODE_REVIEW_NOENTITY = "R01";
	//vote不存在
	final static String MESSAGE_CODE_VOTE_NOENTITY = "V01";
	
	/************* Version ******************/
	final static String TRYIT_VERSION_1_0 = "tryit_1.0";
	final static String TRYIT_VERSION_1_1 = "tryit_1.1";
	
	/************ API URI ***************/
	final static String API_URI_POST_LOOK_CREATE = "/tryIt/api/public/look/create";
	final static String API_URI_GET_LOOKS_RECENT = "/tryIt/api/public/looks/recent/";
	final static String API_URI_GET_LOOKS_POPULAR = "/tryIt/api/public/looks/popular/";
	final static String API_URI_GET_PROFILE_ID = "/tryIt/api/public/profile/";
	final static String API_URI_POST_PROFILE_EDIT = "/tryIt/api/public/profile/edit";
	final static String API_URI_GET_LOOKS = "/tryIt/api/public/looks/";
	final static String API_URI_GET_REVIEWS = "/tryIt/api/public/reviews/";
	final static String API_URI_GET_LOOK = "/tryIt/api/public/look/";
	final static String API_URI_POST_REVIEW_QUICK= "/tryIt/api/public/review/quick";
	final static String API_URI_POST_REVIEW = "/tryIt/api/public/review";
	final static String API_URI_POST_VOTE = "/tryIt/api/public/vote";
	final static String API_URI_GET_VOTE_AGREE = "/tryIt/api/public/review/agree/";
	final static String API_URI_DELETE_VOTE = "/tryIt/api/public/vote/";
	final static String API_URI_POST_LOOK_EDIT = "/tryIt/api/public/look/edit";
	final static String API_URI_DELETE_LOOK = "/tryIt/api/public/look/";
	final static String API_URI_GET_LOOK_HIDE = "/tryIt/api/public/look/hide/";	

	
	/**	默认推荐给客户的推荐用户数量 **/
	public static final int DEFAULT_RECOMMENDERS_NUM = 10;
	/** 系统版本号 **/
	public static final String TIAPS_VERSION_1_0 = "tiaps_1.0";
	/**域名**/
	public static final String TIAPS_DOMAIN_NAME = "cn.npnt";
	/**头像目录**/
	final static  String PHOTO_FIGURE_URL = "/images/figure/";
	final static String PHOTO_LOOK_URL = "/images/looks/";
	
	final static int MAX_PHOTO_NUM = 4;//当前支持的一个look中的最多照片数量
}
