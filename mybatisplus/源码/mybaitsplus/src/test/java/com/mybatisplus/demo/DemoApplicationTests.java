package com.mybatisplus.demo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mybatisplus.demo.entity.Liquor;
import com.mybatisplus.demo.mapper.LiquorMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Map;

@SpringBootTest
class DemoApplicationTests {

    @Autowired
    private LiquorMapper liquorMapper;

    @Test
    void contextLoads() {
        List<Liquor> liquors = liquorMapper.selectList(null);

        System.out.println(liquors);
    }

    @Test
    void testo1(){
        Liquor liquor = new Liquor();
        QueryWrapper<Liquor> liquorQueryWrapper = new QueryWrapper<>();
//                liquorQueryWrapper.apply("name ilike %{0}%","j");
    liquorQueryWrapper.like("name","j");
        liquor.setName("j");
        QueryWrapper<Liquor> wrapper = new QueryWrapper<>();
        Page<Liquor> page = new Page<>(1,2);
//        IPage<Liquor> liquorIPage = liquorMapper.selectUserPage(page,liquor);
//        Page<Map<String, Object>> mapPage = liquorMapper.selectMapsPage(new Page<>(1, 2), liquorQueryWrapper);
//        List<Liquor> records = liquorIPage.getRecords();
//        wrapper
//                .like("name","老");
//        liquorQueryWrapper.like()
        String customSqlSegment = liquorQueryWrapper.getCustomSqlSegment();
        List<Liquor> liquors = liquorMapper.selectList(liquorQueryWrapper);
//        String sqlSelect = wrapper.getCustomSqlSegment();
        System.out.println();
        liquors.forEach(System.out::println);
    }

}
