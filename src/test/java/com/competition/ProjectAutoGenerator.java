package com.competition;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
    代码生成器
 */
public class ProjectAutoGenerator {

    // 自定义静态输入提示方法
    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("请输入" + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }


    public static void main(String[] args) {
        // 构建一个  代码生成器 对象
        com.baomidou.mybatisplus.generator.AutoGenerator mpg = new com.baomidou.mybatisplus.generator.AutoGenerator();

        // 配置策略
        // 1.全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + "/src/main/java");
        gc.setAuthor("conchino");
        gc.setOpen(false);           // .setOpen() 是否打开资源管理器
        gc.setFileOverride(false);   // 是否覆盖原生文件夹

        gc.setServiceName("%sService");  // 去service的I前缀

        gc.setIdType(IdType.ID_WORKER);     // 设置ID类型
//        gc.setIdType(IdType.AUTO);     // 设置ID类型: 自增
        gc.setDateType(DateType.ONLY_DATE);
        gc.setSwagger2(true);        // 开启Swagger2

        mpg.setGlobalConfig(gc);        // 将全局配置放入代码生成器对象中


        // 2.数据源
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/assign-program?useSSL=true&useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123456");
        dsc.setDbType(DbType.MYSQL);

        mpg.setDataSource(dsc);     // 将数据源配置放入代码生成器中


        // 3.包的配置
        PackageConfig pc = new PackageConfig();
//        pc.setModuleName(inputModuleName("模块名   (自定义模块名)"));
        pc.setModuleName(scanner("要生成的模块名 (com.competition.[模块名]])"));       // 生成的模块名
        pc.setParent("com.competition");
        // 设置 实体类、Mapper、Service、Controller 模块名称
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");

        // 将包配置加载到代码生成器对象
        mpg.setPackageInfo(pc);

        // 4.策略配置
        StrategyConfig strategyConfig = new StrategyConfig();
        strategyConfig.setInclude(scanner("映射的表名 (数据库中的表)").split(","));      // 设置要映射的表名
        // 可写入多个数据库表
//        strategyConfig.setInclude("blog_tags","course","links","sys_settings","user_record"," user_say");

        strategyConfig.setNaming(NamingStrategy.underline_to_camel);        // 命名规则转换：下划线转驼峰命名
        strategyConfig.setColumnNaming(NamingStrategy.underline_to_camel);  // 数据库列名转化：下划线转驼峰
        strategyConfig.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀
        strategyConfig.setEntityLombokModel(true);              // 实体类自动Lombok
        strategyConfig.setLogicDeleteFieldName("deleted");       // 逻辑删除，设置逻辑删除属性名
        // 自动填充配置
        // 自动填充配置创建时间和修改时间
        TableFill create_time = new TableFill("create_time", FieldFill.INSERT);
        TableFill update_time = new TableFill("update_time", FieldFill.INSERT_UPDATE);
        ArrayList<TableFill> tableFills  = new ArrayList<>();
        tableFills.add(create_time); tableFills.add(update_time);
        strategyConfig.setTableFillList(tableFills);

        // 乐观锁配置
        strategyConfig.setVersionFieldName("version");
        strategyConfig.setRestControllerStyle(true);
        strategyConfig.setControllerMappingHyphenStyle(true);

        mpg.setStrategy(strategyConfig);


        // 自定义配置
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        // 如果模板引擎是 velocity
        String templatePath = "/templates/mapper.xml.vm";

        // 自定义输出配置
        List<FileOutConfig> focList = new ArrayList<>();
        // 自定义配置会被优先输出
        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                // 自定义输出文件名 ， 如果你 Entity 设置了前后缀、此处注意 xml 的名称会跟着发生变化！！
                return projectPath + "/src/main/resources/mapper/" + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 配置模板
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);
        mpg.execute();     // 执行
    }
}
