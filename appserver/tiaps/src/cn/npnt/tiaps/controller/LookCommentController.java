package cn.npnt.tiaps.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.npnt.tiaps.assemble.vo.CommentBody;
import cn.npnt.tiaps.base.vo.BaseResponseVO;
import cn.npnt.tiaps.base.vo.QueryParamVO;
import cn.npnt.tiaps.constants.Constants;
import cn.npnt.tiaps.entity.Comment;
import cn.npnt.tiaps.entity.CommentAgree;
import cn.npnt.tiaps.entity.Look;
import cn.npnt.tiaps.entity.User;
import cn.npnt.tiaps.service.CommentAgreeService;
import cn.npnt.tiaps.service.CommentService;
import cn.npnt.tiaps.service.FriendshipService;
import cn.npnt.tiaps.service.LookService;
import cn.npnt.tiaps.service.UserService;
import cn.npnt.tiaps.vo.CommentVO;
import cn.npnt.tiaps.vo.UserBrief;

import com.google.gson.Gson;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2012 2012-1-4 下午10:46:55
 * @description 对look进行评论的相关处理接口方法
 */
@Controller
public class LookCommentController {

	private Gson gson = new Gson();
	
	private LookService lookService;
	private CommentService commentService;
	private CommentAgreeService commentAgreeService;
	private FriendshipService friendshipService;
	
	@Autowired
	public void setLookService(LookService lookService){
		this.lookService = lookService;
	}
	@Autowired
	public void setCommentService(CommentService commentService){
		this.commentService = commentService;
	}
	@Autowired
	public void setCommentAgreeService(CommentAgreeService commentAgreeService){
		this.commentAgreeService = commentAgreeService;
	}
	@Autowired
	public void setFriendshipService(FriendshipService friendshipService){
		this.friendshipService = friendshipService;
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-4 下午10:50:58
	 * @description 对look进行评论
	 */
	@RequestMapping(value = "/lookComment/comment_look", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void commentLook(HttpServletRequest request,HttpServletResponse response){
		int ret = 5;
		BaseResponseVO responseVO = new BaseResponseVO();
		if(request.getAttribute("user") != null){
			try{
				User user = (User)request.getAttribute("user");
				long lookId = Long.parseLong(request.getParameter("lookId"));
				String text = request.getParameter("text");
				if(user != null){
					Look look = lookService.getLookByPK(lookId);
					if(look != null){
						Comment comment = new Comment();
						comment.setComment(text);
						comment.setCreateTime(new Date());
						comment.setDeleted(false);
						comment.setLook(look);
						comment.setReportAbuse(0);
						comment.setUser(user);
						comment.setVersion(0);
						comment.setAgreeCount(0);
						commentService.doLookComment(comment);
						//查询该look下的所有的评论
						QueryParamVO param = new QueryParamVO();
						List<Comment> commentList = commentService.getCommentsByLook(param, lookId);
						if(commentList != null && commentList.size() > 0){
							CommentVO vo = null;
							UserBrief userBrief = null;
							List<CommentVO> voList = new ArrayList<CommentVO>();
							for(Comment c : commentList){
								vo = new CommentVO();
								vo.setAgreeCount(c.getAgreeCount());
								vo.setId(c.getId());
								vo.setText(c.getComment());
								if(c.getUser() != null){
									userBrief = new UserBrief();
									userBrief.setId(c.getUser().getId());
									userBrief.setNickname(c.getUser().getNickname());
									userBrief.setGender((byte)c.getUser().getGender());
									userBrief.setIntroduce(c.getUser().getIntroduce());
									userBrief.setProvince(c.getUser().getProvince());
									userBrief.setCity(c.getUser().getCity());
									userBrief.setFigureUrl(c.getUser().getFigureUrl());
									userBrief.setIsMyFollower(friendshipService.isFollower(user.getId(), c.getUser().getId()));
									userBrief.setIsMyFollowing(friendshipService.isFollowing(user.getId(), c.getUser().getId()));
									vo.setUserBrief(userBrief);
									voList.add(vo);
								}
							}
							CommentBody body = new CommentBody();
							body.setComments(voList);
							responseVO.setBody(body);
						}
						ret = 0;
					}else{
						ret = 20;
					}
				}
			}catch(NumberFormatException e){
				ret = 2;
				responseVO = new BaseResponseVO(ret, e);
			}catch(Exception e){
				ret = 1;
				responseVO = new BaseResponseVO(ret, e);
				e.printStackTrace();
			}
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-5 上午11:58:00
	 * @description 对评论进行投票
	 */
	@RequestMapping(value = "/lookComment/vote_comment", method = RequestMethod.POST, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void voteComment(HttpServletRequest request,HttpServletResponse response){
		int ret = 5;
		BaseResponseVO responseVO = new BaseResponseVO();
		if(request.getAttribute("user") != null){
			String commnetIdStr = request.getParameter("commentId");
			if(StringUtils.isNotBlank(commnetIdStr)){
				try{
					User user = (User) request.getAttribute("user");
					long commentId = Long.parseLong(commnetIdStr);
					Comment comment = commentService.getByPK(commentId);
					
					if(comment != null){
						if(comment.getUser().getId().equals(user.getId())){
							ret=42;//不能对自己的评论agree
						}else{
							CommentAgree commentAgree = new CommentAgree();
							commentAgree.setComment(comment);
							commentAgree.setUser(user);
							commentAgree.setVersion(0);
							commentAgreeService.doAgreeWithComment(commentAgree);
							//增加评论的投票次数
							comment = commentService.manageCommentAgreeCount(comment, 1);
							CommentBody body = new CommentBody();
							body.setCommentId(comment.getId());
							body.setAgreeCount(comment.getAgreeCount());
							responseVO.setBody(body);
							ret = 0;
						}
					}else{
						ret = 30;//找不到评论对象
					}
				}catch(NumberFormatException e){
					ret = 2;
					responseVO = new BaseResponseVO(ret, e);
					e.printStackTrace();
				}catch(Exception e){
					ret = 1;
					responseVO = new BaseResponseVO(ret, e);
					e.printStackTrace();
				}
			}else{
				ret = 2;//参数错误，缺少参数
			}
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @company 新和新拓（北京）科技有限公司
	 * @author Recoba Gan
	 * @createDate 2012 2012-1-7 上午9:26:15
	 * @description 获取某一个look的评论数据
	 * 	获取某look的评论，maxId为空获取所有的，否则获取比maxId大的评述
	 */
	@RequestMapping(value = "/lookComment/get_comment", method = RequestMethod.GET, 
			headers = { "version="+ Constants.TIAPS_VERSION_1_0 }
	)
	public void getCommentOnLook(HttpServletRequest request,HttpServletResponse response){
		int ret = 5;
		BaseResponseVO responseVO = new BaseResponseVO();
		if(request.getAttribute("user") != null){
			User user = (User) request.getAttribute("user");
			String lookIdStr = request.getParameter("lookId");
			if(StringUtils.isNotBlank(lookIdStr)){
				try{
					long lookId = Long.parseLong(lookIdStr);
					Look look = lookService.getLookByPK(lookId);
					if(look != null){
						QueryParamVO param = new QueryParamVO();
						Integer page = Integer.parseInt(request.getParameter("page"));
						Integer count = Integer.parseInt(request.getParameter("count"));
						Long maxId = null;
						String maxIdStr = request.getParameter("maxId");//有可能为空
						if(StringUtils.isNotBlank(maxIdStr)){
							maxId = Long.parseLong(maxIdStr);
							param.pageNO = page;
							param.pageSize = count;
						}else{//不分页了
							param.pageNO = -1;
							param.pageSize = -1;
						}
						Comment commentVo = new Comment();
						commentVo.setLook(look);
						List<Comment> commentList = commentService.getCommentWithMaxId(param, maxId, commentVo);
						if(commentList != null && commentList.size() > 0){
							CommentVO vo = null;
							UserBrief userBrief = null;
							List<CommentVO> voList = new ArrayList<CommentVO>();
							for(Comment c : commentList){
								vo = new CommentVO();
								vo.setAgreeCount(c.getAgreeCount());
								vo.setId(c.getId());
								vo.setText(c.getComment());
								if(c.getUser() != null){
									userBrief = new UserBrief();
									userBrief.setId(c.getUser().getId());
									userBrief.setNickname(c.getUser().getNickname());
									userBrief.setGender((byte)c.getUser().getGender());
									userBrief.setIntroduce(c.getUser().getIntroduce());
									userBrief.setProvince(c.getUser().getProvince());
									userBrief.setCity(c.getUser().getCity());
									userBrief.setFigureUrl(c.getUser().getFigureUrl());
									userBrief.setIsMyFollower(friendshipService.isFollower(user.getId(), c.getUser().getId()));
									userBrief.setIsMyFollowing(friendshipService.isFollowing(user.getId(), c.getUser().getId()));
									vo.setUserBrief(userBrief);
									voList.add(vo);
								}
							}
							CommentBody body = new CommentBody();
							body.setComments(voList);
							responseVO.setBody(body);
						}
						ret = 0;
					}else{
						ret = 20;
					}
				}catch(NumberFormatException e){
					e.printStackTrace();
					ret = 2;
					responseVO = new BaseResponseVO(ret, e);
				}catch(Exception e){
					ret = 1;
					responseVO = new BaseResponseVO(ret, e);
					e.printStackTrace();
				}
			}else{
				ret = 2;//参数错了
			}
		}
		responseVO.getHeader().setRet(ret);
		try {
			response.getWriter().print(gson.toJson(responseVO));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
