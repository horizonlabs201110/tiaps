package cn.npnt.tiaps.base.util;

import java.util.concurrent.Callable;

import org.apache.log4j.Logger;

import com.sun.corba.se.spi.orbutil.threadpool.Work;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2011 2011-12-28 下午3:23:26
 * @description TODO
 */
public abstract class Worker implements Callable<String> {

	private static Logger logger = Logger.getLogger(Work.class);
	/**
	 * @functionName doWorker
	 * @explain 具体执行的抽象方法， 该方法需要具体任务继承来编写具体业务逻辑
	 * 
	 */
	public abstract String doWorker() throws Exception;

	public String call() throws Exception {
		String imgName = doWorker();
		logger.info(imgName);
		return imgName;
	}
}
