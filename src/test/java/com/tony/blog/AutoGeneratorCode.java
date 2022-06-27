/*
package com.tony.blog;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.po.TableFill;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

public class AutoGeneratorCode {
    public static void main(String[] args) {
        // 需要构建一个 代码自动生成器 对象
        AutoGenerator mpg = new AutoGenerator();
        // 配置策略
        // 1、全局配置
        GlobalConfig gc = new GlobalConfig();
        // 获取当前项目目录
        String projectPath = System.getProperty("user.dir");
        // 设置生成代码的位置
        gc.setOutputDir(projectPath+"/src/main/java");
        // 作者
        gc.setAuthor("tony");
        gc.setOpen(false); // 是否打开文件管理器
        gc.setFileOverride(false); // 是否覆盖
        gc.setServiceName("%sService"); // 去 service的 I 前缀
        gc.setIdType(IdType.ID_WORKER); // id 默认是初始算法
        gc.setDateType(DateType.ONLY_DATE); // 日期类型
        gc.setSwagger2(true); // 是否生成swagger
        mpg.setGlobalConfig(gc);

        // 2、设置数据源
        DataSourceConfig dsc= new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/tonyblog?useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Shanghai");
        dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("123");
        dsc.setDbType(DbType.MYSQL); // 数据库类型 mysql
        mpg.setDataSource(dsc);

        // 3、包的配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("autoblog"); // 生成到指定模块中
        pc.setParent("com.tony");
        pc.setEntity("entity");
        pc.setMapper("mapper");
        pc.setService("service");
        pc.setController("controller");
        mpg.setPackageInfo(pc);

        // 4、策略配置
        StrategyConfig strategy = new StrategyConfig();
        // 设置要映射的表名（重要，需要修改的地方）
        strategy.setInclude("pictures", "t_blog", "t_comment", "t_message", "t_type", "t_user");
        strategy.setNaming(NamingStrategy.underline_to_camel); // 自动转换表名的驼峰命名法
        strategy.setColumnNaming(NamingStrategy.underline_to_camel); // 自动转换列名的驼峰命名法
        strategy.setEntityLombokModel(true); // 是否使用lombox
//        strategy.setLogicDeleteFieldName("deleted"); // 配置逻辑删除字段
        // 自动填充策略
//        TableFill gmtCreate =  new TableFill("create", FieldFill.INSERT);
//        TableFill gmtModified =  new TableFill("updated", FieldFill.INSERT);
//        ArrayList<TableFill> tableFills = new ArrayList<>();
//        tableFills.add(gmtCreate);
//        tableFills.add(gmtModified);
//        strategy.setTableFillList(tableFills);
        // 乐观锁
        strategy.setVersionFieldName("version");

        mpg.setStrategy(strategy);
        mpg.execute(); // 执行
    }
}
*/
