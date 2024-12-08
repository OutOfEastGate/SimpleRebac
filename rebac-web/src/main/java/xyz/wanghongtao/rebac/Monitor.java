package xyz.wanghongtao.rebac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * @author wanghongtao <wanghongtao05@kuaishou.com>
 * Created on 2024-12-07
 */
@EnableScheduling
@Slf4j
@Configuration
public class Monitor {
  // 设置阈值：CPU时间 (单位：纳秒) 和 堆内存分配量 (单位：字节)
  long cpuTimeLimit = 1000000000; // 1秒
  long memoryLimit = 78040;


  @Scheduled(fixedRate = 2000)
  public void monitor() {
    log.info("start monitor");
    ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
    com.sun.management.ThreadMXBean memoryCounter = (com.sun.management.ThreadMXBean) threadMXBean;

    // 获取所有线程的ID
    long[] threadIds = threadMXBean.getAllThreadIds();

    for (long threadId : threadIds) {
      // 获取线程的堆栈信息
      ThreadInfo threadInfo = threadMXBean.getThreadInfo(threadId);
      if(!threadInfo.getThreadName().startsWith("http-nio-8080-exec") && !threadInfo.getThreadName().contains("exec")) {
        continue;
      }
      log.info("Thread ID: {}, Name: {}", threadId, threadInfo.getThreadName());

      // 获取每个线程的 CPU 时间和用户时间
      long cpuTime = threadMXBean.getThreadCpuTime(threadId);

      // 获取每个线程的堆内存使用情况
      memoryCounter.setThreadAllocatedMemoryEnabled(true);
      long threadAllocatedBytes = memoryCounter.getThreadAllocatedBytes(threadId);
      if (threadAllocatedBytes > 32L) {
        log.info("Thread Allocated bytes: {}", threadAllocatedBytes);
      }

      // 如果 CPU 时间超过限制，或者堆内存分配超过限制，打断线程执行
      if (threadAllocatedBytes > memoryLimit) {
        log.warn("Thread {} exceeds limits. Interrupting...", threadInfo.getThreadName());
        Thread thread = getThreadById(threadId);
        if (thread != null) {
          thread.interrupt();  // 打断线程
        }
      }
    }
  }

  // 根据线程 ID 获取线程对象
  private Thread getThreadById(long threadId) {
    ThreadGroup group = Thread.currentThread().getThreadGroup();
    Thread[] threads = new Thread[group.activeCount()];
    group.enumerate(threads);
    for (Thread t : threads) {
      if (t != null && t.getId() == threadId) {
        return t;
      }
    }
    return null;
  }
}
