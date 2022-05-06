import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * mybatis-plus代码生成器
 *
 * @author BLCheung
 * @date 2021/11/27 10:57 下午
 */
public class MyBatisCodeGenerator {
    private static final String dbUrl
                                           = "jdbc:mysql://localhost:3306/zbl_missyou_v2?allowPublicKeyRetrieval=true&useUnicode=true&useSSL=false&characterEncoding=utf8&serverTimezone=Asia/Hong_Kong";
    private static final String dbUser     = "root";
    private static final String dbPassword = "123456";

    private static final String projectPath = System.getProperty("user.dir");
    private static final String packageName = "com.blcheung.cappuccino";
    private static final String xmlMapper   = "/src/main/resources/mapper";

    public static void main(String[] args) {
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(dbUrl, dbUser, dbPassword);

        FastAutoGenerator.create(dataSourceConfig)
                         // 全局配置
                         .globalConfig(builder -> builder.author("BLCheung")
                                                         .disableOpenDir()
                                                         .outputDir(projectPath + "/src/main/java"))
                         // 包配置
                         .packageConfig(builder -> builder.parent(packageName)
                                                          .controller("controller.v1")
                                                          .entity("model")
                                                          .pathInfo(initPatInfo()))
                         // 策略配置
                         .strategyConfig(builder -> builder.addInclude(scanner("表名，多个英文逗号分割").split(","))
                                                           .controllerBuilder()
                                                           .enableRestStyle()
                                                           .enableHyphenStyle()
                                                           .formatFileName("%sController")
                                                           .serviceBuilder()
                                                           .formatServiceFileName("%sService")
                                                           .formatServiceImplFileName("%sServiceImpl")
                                                           .entityBuilder()
                                                           .naming(NamingStrategy.underline_to_camel)
                                                           .superClass(packageName + ".model.BaseDO")
                                                           .addSuperEntityColumns("id", "create_time", "update_time",
                                                                                  "delete_time")
                                                           .enableLombok()
                                                           .idType(IdType.AUTO)
                                                           .formatFileName("%sDO")
                                                           .mapperBuilder()
                                                           .enableBaseResultMap()
                                                           .formatMapperFileName("%sMapper")
                                                           .formatXmlFileName("%sMapper")
                                                           .build())
                         // 生成代码模板引擎（需要添加依赖）
                         .templateEngine(new FreemarkerTemplateEngine())
                         .execute();
    }

    /**
     * 配置相应生成文件的输出路径
     *
     * @return java.util.Map<com.baomidou.mybatisplus.generator.config.OutputFile, java.lang.String>
     * @author BLCheung
     * @date 2021/11/28 4:16 上午
     */
    private static Map<OutputFile, String> initPatInfo() {
        Map<OutputFile, String> pathInfo = new HashMap<>();
        // 指定mapper xml文件的输出路径
        pathInfo.put(OutputFile.mapperXml, projectPath + xmlMapper);
        // 可以配置多个文件类型输出路径...
        return pathInfo;
    }

    /**
     * 读取控制台内容
     */
    private static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("请输入" + tip + "：");
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("请输入正确的" + tip + "！");
    }
}
