import com.lch.ssm.entity.Admin;
import com.lch.ssm.mapper.AdminMapper;
import com.lch.ssm.service.api.AdminService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * @author lch
 * @create 2020-07-16 16:24
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations = {"classpath:spring-lch-mybatis.xml","classpath:spring-lch-tx.xml"})
public class Test {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private AdminService adminService;

    @org.junit.Test
    public void t1() {
        Admin admin = new Admin(null,"bill","123","比尔","bill@qq.com",null);
        int count = adminMapper.insert(admin);
        System.out.println("改变的行数为："+count);
    }

    @org.junit.Test
    public void t2() {
        Admin admin = new Admin(null,"bill","123","比尔","bill@qq.com",null);
        int count = adminService.saveAdmin(admin);
        System.out.println("改变的行数为："+count);
    }

    @org.junit.Test
    public void t3() {
        List<Admin> adminList = adminService.getAll();
        for (Admin admin : adminList) {
            System.out.println(admin);
        }
    }
}
