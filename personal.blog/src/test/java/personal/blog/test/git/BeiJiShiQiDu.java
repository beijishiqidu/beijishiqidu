package personal.blog.test.git;

import org.apache.log4j.Logger;

public class BeiJiShiQiDu {

    private static final Logger LOGGER = Logger.getLogger(BeiJiShiQiDu.class);

	public static void addAiqingmugua() {
		LOGGER.debug("删除了main方法，增加了爱情木瓜的方法...");//我就是测试立即提交合并的改动的
	}

    public static void main(String[] args) {
        LOGGER.debug("执行main方法....");

        LOGGER.debug("修改了main里面的一些实现...");// TODO删除此方法....
    }
    
    
    //测试push的功能的。
    
    //还是测试push多个的功能。
}
