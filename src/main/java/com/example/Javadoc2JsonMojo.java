package com.example;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * 通过遍历指定package下代码注释, 生成json文件
 *
 * @goal generate
 * @phase process-sources
 */
public class Javadoc2JsonMojo
        extends AbstractMojo {
    /**
     * Location of the file.
     * 文件写出到class根目录
     *
     * @parameter expression="${project.build.directory}"
     * @required
     */
    private File outputDirectory;

    /**
     * Location of the file.
     * 源代码根目录
     *
     * @parameter expression="${project.build.sourceDirectory}"
     * @required
     */
    private File srcDirectory;

    /**
     * Location of the file.
     * 注释解析的根目录
     *
     * @parameter alias="<basePackage>"
     * @required
     */
    private String basePackage;

    public void execute()
            throws MojoExecutionException {
        System.out.println("doc源码扫描路径: " + srcDirectory.getPath() + basePackage);
        System.out.println("javadoc.json生成路径: " + outputDirectory.getPath() + File.separator + "javadoc.json");
        new CommentToJson().exec(srcDirectory.getPath() + File.separator + basePackage, outputDirectory.getPath());
    }
}
