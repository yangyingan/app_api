package com.dm.core.annotation.log;//package com.leigq.www.shiro.config.annotation.log;
//
//import com.td.core.sys.entity.Log;
//import com.td.core.sys.service.LogService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.util.concurrent.BlockingQueue;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.LinkedBlockingQueue;
//
///**
// * 系统日志队列入库
// */
//@Component
//public class LogQueue {
//	/** 存储日志队列 */
//	private static volatile BlockingQueue<Log> loggerQueue = new LinkedBlockingQueue<Log>(5000);
//
//    @Autowired
//    private LogService logService;
//
//	/**
//	 * 启动线程队列
//	 */
//    @PostConstruct
//	public void startThread() {
//		ExecutorService service = Executors.newCachedThreadPool();
//		service.execute(new LoggerRunnable());
//		service.execute(new LoggerRunnable());
//		service.execute(new LoggerRunnable());
//	}
//
//	/**
//	 * 数据入队列
//	 */
//	public static void addLog(Log log) {
//		loggerQueue.offer(log);
//	}
//
//	/**
//	 * 数据出队列，入库
//	 */
//	private class LoggerRunnable implements Runnable {
//		@Override
//		public void run() {
//			while (true) {
//				try {
//						Log log = loggerQueue.poll();
//						if (log != null) {
//							Thread.sleep(5000);
//							System.out.println(log);
//							System.out.println("11111");
//							//((LogService) WEBApp.SPRING_CONTEXT.getBean("logService")).save(log);
//							logService.save(log);
//						}
//				}
//				catch (Exception e) {
//					e.printStackTrace();
//				} finally {
//					try {
//						Thread.sleep(5);
//					}
//					catch (InterruptedException e) {
//						e.printStackTrace();
//					}
//				}
//			}
//		}
//	}
//}
