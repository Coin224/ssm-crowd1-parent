import com.lch.ssm.entity.Admin;
import com.lch.ssm.mapper.AdminMapper;
import com.lch.ssm.service.api.AdminService;
import com.lch.ssm.util.CrowdUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author lch
 * @create 2020-07-16 16:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:spring-lch-mybatis.xml","classpath:spring-lch-tx.xml"})
public class TestMain {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @Test
    public void t1() {
        Admin admin = new Admin(0,"bill","123","比尔","bill@qq.com",null);
        int count = adminMapper.insert(admin);
        System.out.println("改变的行数为："+count);
    }

    @Test
    public void t2() {
        String s = CrowdUtil.MD5("123");
        System.out.println(s);
        //Admin admin = new Admin(1,"lch",s,"李春宏","coinvv@qq.com",null);
        //int count = adminService.saveAdmin(admin);
        //System.out.println("改变的行数为："+count);
    }

    @Test
    public void t3() {
        List<Admin> adminList = adminService.getAll();
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }

    @Test
    public void t4() {
        String a = "lch";
        System.out.println(CrowdUtil.MD5(a));
    }

    @Test
    public void t5() {
        String s = CrowdUtil.MD5("123");
        for (int i = 2; i < 500 ; i++) {
            Admin admin = new Admin(i, "tom"+i, s, "李春宏"+i, "jfklajfla@qq.com"+i, null);
            adminService.saveAdmin(admin);
        }
    }

    @Test
    public void t6() {
        RuntimeException runtimeException = new RuntimeException("不准删自己！！！");
        System.out.println(runtimeException.getMessage());
    }
}
