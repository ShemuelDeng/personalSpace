package com.shemuel.site;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.shemuel.site.entity.Article;

/**
 * @Author: 公众号: 加瓦点灯
 * @Date: 2025-03-28-17:10
 * @Description:
 */
public class SimpleTest {
    public static void main(String[] args) {
        String a= "# 深入解析Java Fork/Join框架：并行任务处理的利器\n" +
                "\n" +
                "## 1. 引言\n" +
                "\n" +
                "在Java并发编程中，Fork/Join框架是一个强大的工具，它允许开发者轻松地利用多核处理器的优势来执行并行任务。作为一名资深Java开发工程师，我将在本文中深入探讨Fork/Join框架的概念、原理、使用场景以及具体的使用方法，并通过实例帮助你更好地理解和掌握这一技术。\n" +
                "\n" +
                "## 2. Fork/Join框架概述\n" +
                "\n" +
                "### 2.1 核心思想\n" +
                "采用递归分解策略：将大任务拆分为子任务（Fork），并行执行后合并结果（Join）。\n" +
                "\n" +
                "### 2.2 工作窃取算法（Work-Stealing）\n" +
                "- 每个线程维护双端队列，优先处理自己的任务。\n" +
                "- 空闲线程从其他队列**尾部**窃取任务，减少竞争，提升资源利用率。\n" +
                "\n" +
                "## 3. 核心组件\n" +
                "\n" +
                "### 3.1 ForkJoinPool\n" +
                "- 线程池实现，默认线程数等于处理器核心数。\n" +
                "- 可通过`ForkJoinPool.commonPool()`获取通用池。\n" +
                "\n" +
                "### 3.2 ForkJoinTask\n" +
                "- 抽象任务类，子类包括：\n" +
                "  - **RecursiveAction**：无返回值任务（如数据排序）\n" +
                "  - **RecursiveTask**：带返回值任务（如数值计算）\n" +
                "\n" +
                "## 4. 使用步骤详解\n" +
                "\n" +
                "### 4.1 任务拆分逻辑\n" +
                "- 继承`RecursiveTask`/`RecursiveAction`\n" +
                "- 在`compute()`中实现任务拆分与结果合并\n" +
                "\n" +
                "### 4.2 代码示例：数组求和\n" +
                "\n" +
                "```java\n" +
                "public class SumTask extends RecursiveTask<Long> {\n" +
                "    private static final int THRESHOLD = 10_000;\n" +
                "    private final int[] array;\n" +
                "    private final int start;\n" +
                "    private final int end;\n" +
                "\n" +
                "    public SumTask(int[] array, int start, int end) {\n" +
                "        this.array = array;\n" +
                "        this.start = start;\n" +
                "        this.end = end;\n" +
                "    }\n" +
                "\n" +
                "    @Override\n" +
                "    protected Long compute() {\n" +
                "        if (end - start <= THRESHOLD) {\n" +
                "            long sum = 0;\n" +
                "            for (int i = start; i < end; i++) {\n" +
                "                sum += array[i];\n" +
                "            }\n" +
                "            return sum;\n" +
                "        } else {\n" +
                "            int mid = (start + end) >>> 1;\n" +
                "            SumTask left = new SumTask(array, start, mid);\n" +
                "            SumTask right = new SumTask(array, mid, end);\n" +
                "            left.fork();\n" +
                "            Long rightResult = right.compute();\n" +
                "            Long leftResult = left.join();\n" +
                "            return leftResult + rightResult;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    public static void main(String[] args) {\n" +
                "        int[] array = new int[100_000];\n" +
                "        Arrays.fill(array, 1);\n" +
                "        \n" +
                "        ForkJoinPool pool = new ForkJoinPool();\n" +
                "        Long result = pool.invoke(new SumTask(array, 0, array.length));\n" +
                "        System.out.println(\"Sum: \" + result); // 输出100000\n" +
                "    }\n" +
                "}\n" +
                "```\n" +
                "\n" +
                "### 4.3 关键方法解析\n" +
                "- `fork()`: 提交子任务到队列\n" +
                "- `join()`: 阻塞等待结果\n" +
                "- 注意：优先调用当前任务的子任务`compute()`以减少线程开销\n" +
                "\n" +
                "## 5. 性能优化策略\n" +
                "\n" +
                "### 5.1 任务拆分最佳实践\n" +
                "- **黄金分割点**：设置合理的阈值（如10,000元素）\n" +
                "- **平衡拆分**：避免生成过多小任务（建议二叉树结构）\n" +
                "\n" +
                "### 5.2 避免常见陷阱\n" +
                "- **阻塞操作**：可能引起线程饥饿\n" +
                "- **同步机制**：慎用`synchronized`，改用并发集合\n" +
                "- **异常处理**：重写`getException()`捕获异常\n" +
                "\n" +
                "## 6. 适用场景分析\n" +
                "\n" +
                "| 场景类型          | 示例                  | 优势体现               |\n" +
                "|-------------------|-----------------------|------------------------|\n" +
                "| CPU密集型计算     | 大规模数值计算        | 充分利用多核           |\n" +
                "| 递归结构问题      | 归并排序/快速排序     | 自然的任务分解         |\n" +
                "| 大数据处理        | 日志分析/数据统计     | 并行加速处理           |\n" +
                "\n" +
                "## 7. 与传统线程池对比\n" +
                "\n" +
                "| 特性               | ForkJoinPool          | ThreadPoolExecutor     |\n" +
                "|--------------------|-----------------------|------------------------|\n" +
                "| 任务队列           | 双端队列（工作窃取）  | 阻塞队列（FIFO）       |\n" +
                "| 适用场景           | 短任务/递归分解       | 异构任务/IO密集型      |\n" +
                "| 资源利用           | 自动负载均衡          | 需手动控制任务分配     |\n" +
                "\n" +
                "## 8. 高级技巧\n" +
                "\n" +
                "### 8.1 结果合并优化\n" +
                "- 使用`invokeAll()`提交多个任务：\n" +
                "```java\n" +
                "invokeAll(task1, task2);\n" +
                "Long result1 = task1.join();\n" +
                "Long result2 = task2.join();\n" +
                "```\n" +
                "\n" +
                "### 8.2 并行流整合\n" +
                "Java8+的`parallelStream()`底层采用Fork/Join：\n" +
                "```java\n" +
                "Arrays.stream(array).parallel().sum();\n" +
                "```\n" +
                "\n" +
                "## 9. 总结\n" +
                "\n" +
                "Fork/Join框架通过智能的任务分发和高效的工作窃取机制，成为处理可分解并行任务的利器。合理设置阈值、避免阻塞调用，能够显著提升CPU密集型任务的执行效率。当面对大规模数据计算或递归型问题时，Fork/Join应是您的首选方案。\n" +
                "\n" +
                "> **思考题**：如何处理需要分解为三个以上子任务的场景？欢迎在评论区分享你的解决方案！";

        Article article = new Article();
        article.setId(1);
        article.setContent(a);

        System.out.println(JSON.toJSONString(article, SerializerFeature.IgnoreNonFieldGetter));
    }
}
