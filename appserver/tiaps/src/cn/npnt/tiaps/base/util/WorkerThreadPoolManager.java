package cn.npnt.tiaps.base.util;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * @company 新和新拓（北京）科技有限公司
 * @author Recoba Gan
 * @createDate 2011 2011-12-28 下午3:23:40
 * @description 异步线程池管理工具类
 */
public class WorkerThreadPoolManager {
	
	private static Logger logger = Logger.getLogger(WorkerThreadPoolManager.class); 
	private WorkerThreadPoolManager() {
	}

	public static WorkerThreadPoolManager getInstance() {
		return wtpm;
	}

	/**
	 * 全局一个静态类，主要是维护一个消息队列处理线程池
	 */
	private static WorkerThreadPoolManager wtpm = new WorkerThreadPoolManager();

	/*
	 * 线程池维护线程的最少数量
	 */
	private final static int CORE_POOL_SIZE = 10;

	/*
	 * 线程池维护线程的最大数量
	 */
	private final static int MAX_POOL_SIZE = 40;

	/*
	 * 线程池维护线程所允许的空闲时间
	 */
	private final static int KEEP_ALIVE_TIME = 0;

	/*
	 * 线程池所使用的缓冲队列大小
	 */
	private final static int WORK_QUEUE_SIZE = 40;

	/*
	 * 消息缓冲队列 该队列中存储了需要执行的worker
	 */
	Queue workerQueue = new LinkedList();

	/**
	 * @explain 当 execute 不能接受某个任务时， 因为超出其界限而没有更多可用的线程或队列槽时， 或者关闭 Executor
	 *          时就可能发生这种情况。 调用该handler将该任务重新放入队列里等待重新执行的机会
	 */
	final RejectedExecutionHandler handler = new RejectedExecutionHandler() {
		public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
			Worker worker = ((Worker) r);
			// log.info( "消息任务放入队列中重新等待执行");
			workerQueue.offer(r);
		}
	};
	/**
	 * @explain 初始化线程池，该池中的每个线程具体来处理
	 *          执行具体的一个worker,当频繁向池中加入执行任务时（即调用executeWorker）
	 *          如果处理线程已经超过MAX_POOL_SIZE所限定的线程数，测试将触发this.handler
	 *          将过多的worker重新放入Queue中等待执行机会
	 */
	final ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
			CORE_POOL_SIZE, MAX_POOL_SIZE, KEEP_ALIVE_TIME, TimeUnit.SECONDS,
			new ArrayBlockingQueue<Runnable>(20),
            new ThreadPoolExecutor.DiscardOldestPolicy());

	/**
	 * @explain 通过Executors创建只有一个线程的调度线程池
	 *          目的：当过频繁向任务池里添加任务后，如果超过MAX_POOL_SIZE所限定的线程数时
	 *          将触发this.handler来重新向queue中添加该任务。这时scheduler定时调度线程
	 *          就会过一定时间来检查queue中是否还有任务,如果有将继续向任务池中添加该worker 进行执行
	 */
	final ScheduledExecutorService scheduler = Executors
			.newScheduledThreadPool(1);

	/**
	 * @explain 访问消息缓存的调度线程， 检查任务队列里是否有没处理的worker(任务)
	 * 
	 */

	final Runnable ValidateWorkerRunOfQueue = new Runnable() {
		public void run() {
			// 查看是否有待定请求，如果有，则得到该worker，并添加到线程池中进行执行
			if (hasMoreAcquire()) {
				Worker worker = (Worker) workerQueue.poll();
				FutureTask<String> futureTask = new FutureTask<String>(worker);
                threadPool.execute(futureTask);
			}
		}
	};
	/**
	 * @explain 该调度线程池用来通过ValidateWorkerRunOfQueue 检测queue里是否还有等待的worker,每（0 + 2
	 *          * 500）毫秒进行检查一次 如果有放入threadPool池中进行执行
	 */
	final Future taskHandler = scheduler.scheduleAtFixedRate(
			ValidateWorkerRunOfQueue, 0, 500, TimeUnit.MILLISECONDS);

	/**
	 * 关闭调度线程
	 * @Title: shutdownTimerThread
	 * @Description: TODO
	 * @param
	 * @return void 返回类型
	 * @throws
	 */
	public void shutdownTimerThread() {
		taskHandler.cancel(false);

	}

	/**
	 * @explain 有序的关闭线程池
	 */
	public void shutdownThreadPool() {
		threadPool.shutdown();

	}

	/**
	 * @functionName hasMoreAcquire
	 * @explain 检查队列里是否有任务
	 * @return true 有任务 false 没有任务
	 */
	private boolean hasMoreAcquire() {
		return !workerQueue.isEmpty();
	}

	/**
	 * @explain 向任务池中添加一个工作任务
	 * @param worker
	 */
	public void executeWorker(Worker worker) {
		FutureTask<String> futureTask = new FutureTask<String>(worker);
		threadPool.execute(futureTask);
	}

	/**
	 * 测试main
	 * @param args
	 */
	public static void main(String[] args) {
		WorkerThreadPoolManager wtpm = WorkerThreadPoolManager.getInstance();
		for (int i = 0; i < 100; i++) {
			wtpm.executeWorker(new Worker() {
				@Override
				public String doWorker() {
					logger.info(Thread.currentThread().getName());
					return "";
				}
			});

		}
		wtpm.shutdownTimerThread();
		wtpm.shutdownThreadPool();

	}
}
