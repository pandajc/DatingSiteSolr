package com.atguigu.datingsite.test;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 逆向工程
 * @ClassName	MBGUtil	
 * @author		Juncao Liu
 * @date		2017年8月28日
 */
public class MBGUtil {
	
	public static void main(String[] args) throws Exception {
		List<String> warnings = new ArrayList<String>();
		boolean overwrite = true;

		File configFile = new File("mbg.xml");

		ConfigurationParser cp = new ConfigurationParser(warnings);
		Configuration config = cp.parseConfiguration(configFile);
		DefaultShellCallback callback = new DefaultShellCallback(overwrite);
		MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
				callback, warnings);
		//生成
		myBatisGenerator.generate(null);
		System.out.println("成功完成...");
	}
}
