package cn.edu.nwsuaf.cie.ssms.util;

/**
 * Created by zhangrenjie on 2018-03-10
 * 分页工具
 */
public class PageUtil {

    private static final int DEFAULT_PAGE = 1;
    private static final int DEFAULT_NUMS = 10;

    /**
     * 根据页码和每页数量计算出从数据库取数据时候 limit 的起始值
     * @param page 当前的页数
     * @param nums 每页的数量
     * @return
     */
    public static int[] getPage(int page, int nums) {
        if (page <= 0) {
            page = DEFAULT_PAGE;
        }
        if (nums <= 0) {
            nums = DEFAULT_NUMS;
        }
        return new int[]{nums * (page - 1), nums};
    }

}
