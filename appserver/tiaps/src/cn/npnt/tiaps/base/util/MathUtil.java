package cn.npnt.tiaps.base.util;

import java.util.ArrayList;
import java.util.List;


public class MathUtil {

	/**
	 * @param args
	 */
//	public static List<Look> randomFavorite20(List<Look> looks,long profileId){
//		List<Look> favoriteLooks = new ArrayList<Look>();
//		int count = looks.size();
//		if(looks == null || count == 0 || profileId < 0){
//			return favoriteLooks;
//		}
//	
//		if(count <= 20){
//			return looks;
//		}
//		
//		int residue = (int) (profileId % count );
//		int referIndex ;
//		if(residue == 0){
//			referIndex = (int) (profileId / count) - 1;
//		}else{
//			referIndex = residue - 1;
//		}
//		int multiple = count / 20;
//		
//		int leftSpaceIndex = referIndex - multiple;
//		int rightSpaceIndex = referIndex;
//	
//		//添加右边元素
//		for(int i = 0 ; i < 20 ; i++){
//			//添加参照下标
//			if(rightSpaceIndex < count){
//				favoriteLooks.add(looks.get(rightSpaceIndex));		
//				rightSpaceIndex += multiple;
//			}
//			if(leftSpaceIndex > 0) {
//				favoriteLooks.add(looks.get(leftSpaceIndex));
//				leftSpaceIndex -= multiple;
//			}
//		}
//		return favoriteLooks.subList(0, 20);
//	}
//	
//	
//	public static void main(String[] args) {
//		List<Look> looks = new ArrayList<Look>();
//		for(long i = 1L ; i < 29L ; i ++){
//			Look look = new Look();
//			look.setId(i);
//			looks.add(look);
//		}
//		long profileId = 7;
//		List<Look> randomLooks = MathUtil.randomFavorite20(looks, profileId);
//		for(Look l : randomLooks){
//			System.out.print(l.getId() + ",");
//		}
//		System.out.println("");
//		System.out.println(randomLooks.size());
		
//		int[] indexs = MathUtil.random20(arr);
//		for(int i = 0 ; i < indexs.length ; i ++){
//			System.out.print(indexs[i] +",");
//		}
//		System.out.println("\n" + indexs.length);
		
//		int[] indexs;
//		int count = arr.length;
//		if(count <= 20){
//			indexs = new int[count];
//			for(int i = 0 ;i < count ; i++){
//				indexs[i] = i;
//			}
//		}else{
//			indexs = new int[20];
//			int[] temp = new int[count];
//			for(int i = 0 ; i < count ; i ++){
//				temp[i] = -1;
//			}
//			
//			Random r = new Random();
//			int k = 20;
//			
//			for(int i = 0 ; i < k ; i ++ ){
//				int index = r.nextInt(count);
//				if(temp[index] == -1 ) {
//					temp[index] = index;
//				}else{
//					k++;
//				}
//			}
//			int y = 0 ;
//			for(int j = 0 ; j < count ; j ++){
//				if(temp[j] != -1 && y < 20){
//					indexs[y++] = temp[j];
//				}
//			}
//		}
//		return indexs;
		
		
//		//首先查询该用户是否有评论
//		Review lastReview = reviewDao.getLastReviewLookByProfileId(profileId);
//		//如果有最后一次评论,从最后一次评论对应的look品牌和场合各取10条记录
//		
//		if(lastReview != null){
//			Look look = lastReview.getLook();
//			//获取品牌,获取去向
//			String brandWearing = look.getBrandWearing();
//			int whereGoing = look.getWhereGoing();
//			
//			List<Look> bwLooks = lookDao.getLookWithProfileByBrandWearing(brandWearing);
//			List<Look> wgLooks = lookDao.getLookWithProfileByWhereGoing(whereGoing);
//			
//			if(bwLooks.size() + wgLooks.size() < Constants.RECORD_LIMIT){
//				//如果相加的数量小于20条
//				Vote lastVote = voteDao.getLastVoteLookByProfileId(profileId);
//				int shortage = Constants.RECORD_LIMIT - bwLooks.size() - wgLooks.size();
//				
//			}else{
//				favoriteLooks.addAll(bwLooks);
//				favoriteLooks.addAll(wgLooks);
//			}
//			
//		}else{
//			//如果没有评论内容则用用户的ID产生随即算法
////			long startIndex = profileId % (long) Constants.RECORD_LIMIT;
////			lookDao
//			lookDao.getRecentLooksWithProfileAndPhotos(startIndex, offset)
//		}	
//	}

}
