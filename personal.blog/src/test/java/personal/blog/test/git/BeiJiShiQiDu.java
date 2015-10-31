package personal.blog.test.git;

import org.apache.log4j.Logger;

public class BeiJiShiQiDu {

	private static final Logger LOGGER = Logger.getLogger(BeiJiShiQiDu.class);

	public static void addAiqingmugua() {
		LOGGER.debug("删除了main方法，增加了爱情木瓜的方法...");
	}

	public static void main(String[] args) {
		LOGGER.debug("执行main方法....");

		LOGGER.debug("修改了main里面的一些实现...");
	}
}
