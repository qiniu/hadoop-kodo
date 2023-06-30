# HADOOP-KODO

## 相关文档

* [Testing](./docs/test.md)
* [Config](./docs/config.md)
* [Private Cloud](./docs/private-cloud.md)
* [Design](./docs/design.md)

* [测试文档](./docs/test_zh.md)
* [配置文档](./docs/config_zh.md)
* [私有云用户配置文档](./docs/private-cloud_zh.md)
* [设计文档](./docs/design_zh.md)

## 概述

七牛云海量存储系统（Kodo）是自主研发的非结构化数据存储管理平台，支持中心和边缘存储。 平台经过多年大规模用户验证已跻身先进技术行列，并广泛应用于海量数据管理的各类场景。

本模块为`Hadoop`提供了对接[七牛云存储服务Kodo](https://www.qiniu.com/products/kodo)的支持。


### 特性

+ 支持 Hadoop MapReduce 和 Spark 在 Kodo 上的读写操作
+ 实现了 Hadoop 文件系统的接口，通过模拟分级目录结构提供了和HDFS相同的使用体验
+ 支持大文件分片上传，最高支持 10TB 的单个文件
+ 使用了 Kodo 的 batch api，具有高性能的文件系统操作能力
+ 支持文件的内存块级缓存和磁盘块级缓存，提高了文件的读取性能

### Warning

#### 警告 #1：对象存储不是文件系统

由于对象存储并不是文件系统，因此在使用上存在一些限制
1. 对象存储是基于 key-value 的存储，不支持分层目录结构，因此需要使用路径分隔符模拟分层目录结构
2. 不跟踪目录的修改和访问时间
3. Kodo 对象存储暂不支持文件的追加写入，因此不能在已有文件的末尾追加写入数据
4. 不跟踪文件的修改时间，因此在文件系统中，文件的修改时间为文件的创建时间
5. delete和rename的操作是非原子的，这意味着如果操作被意外中断，文件系统可能处于不一致的状态
6. 对象存储并不支持unix-like的权限管理，因此在文件系统中，因此需要提供以下规则：
   + 目录权限为715
   + 文件权限为666
   + 文件所有者为本地当前用户
   + 文件所有组为本地当前用户
7. 单次请求的最大文件数量为1000
8. 支持大文件分片上传，但分片数量限制为10000，由于单个分片的最大大小为1GB，因此最大支持10TB的单个文件


#### 警告#2：目录最后访问时间不被跟踪
依赖此特性的Hadoop特性可能会有意外的行为。例如，YARN的AggregatedLogDeletionService将无法删除相应的日志文件。

#### 警告#3：您的七牛凭证非常非常有价值

你的七牛凭证非常非常有价值。您的七牛凭证不仅可以支付，还可以读写数据。任何拥有凭证的人都可以读取您的 bucket，他们也可以删除这些 bucket。

## Quick Start

### 安装

#### 获取`hadoop-kodo`分发包及其依赖：

根据您的Hadoop版本，从以下地址获取`hadoop-kodo`分发包及其依赖：

[hadoop-kodo release](https://github.com/qiniu/hadoop-kodo/releases)

若 Release 中不存在您需要的 hadoop 版本，可自行编译打包，过程如下：

```shell
# 打包 hadoop-kodo 分发包，其中 <hadoop.version> 为您的目标 hadoop 版本
mvn package -DskipTests -Dhadoop.version=<hadoop.version>

# 提取其所有依赖包到 target/dependency 目录下
mvn dependency:copy-dependencies

# 拷贝其依赖的 qiniu-java-sdk 到 target 目录下
cp target/dependency/qiniu-* target/
```

#### 安装`hadoop-kodo`

1. 将`hadoop-kodo-<hadoop.version>-x.x.x.jar`和`qiniu-java-sdk-x.x.x.jar`拷贝到`$HADOOP_HOME/share/hadoop/tools/lib`目录下
   > 注意：请根据您的Hadoop版本选择对应的`hadoop-kodo`的jar包，若找不到对应版本的jar包，请自行编译

2. 编辑文件`$HADOOP_HOME/etc/hadoop/hadoop-env.sh`，增加如下配置：
   ```shell
   for f in $HADOOP_HOME/share/hadoop/tools/lib/*.jar; do
     if [ "$HADOOP_CLASSPATH" ]; then
       export HADOOP_CLASSPATH=$HADOOP_CLASSPATH:$f
     else
       export HADOOP_CLASSPATH=$f
     fi
   done
   ```

### 配置

#### core-site.xml

修改`$HADOOP_HOME/etc/hadoop/core-site.xml`，增加Kodo相关的用户配置与实现类相关信息。在公有云环境下通常仅需配置如下即可正常工作：

```xml

<configuration>
    <property>
        <name>fs.qiniu.auth.accessKey</name>
        <value>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</value>
        <description>Qiniu Access Key</description>
    </property>

    <property>
        <name>fs.qiniu.auth.secretKey</name>
        <value>XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX</value>
        <description>Qiniu Secret Key</description>
    </property>

    <property>
        <name>fs.kodo.impl</name>
        <value>org.apache.hadoop.fs.qiniu.kodo.QiniuKodoFileSystem</value>
    </property>
    <property>
        <name>fs.AbstractFileSystem.kodo.impl</name>
        <value>org.apache.hadoop.fs.qiniu.kodo.QiniuKodo</value>
    </property>
    <property>
        <name>fs.defaultFS</name>
        <value>kodo://example-bucket-name/</value>
        <description>hadoop default fs</description>
    </property>

</configuration>

```

更多配置请参考文档：[config.md](./docs/config.md)。
对于私有云用户可参考文档：[private-cloud.md](./docs/private-cloud.md)。

### 运行 mapreduce 示例程序 wordcount

#### put命令

```shell
mkdir testDir
touch testDir/input.txt
echo "a b c d ee a b s" > testDir/input.txt
hadoop fs -put testDir kodo:///testDir

```

#### ls命令

```shell
hadoop fs -ls -R kodo://example-bucket/
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/user
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/user/root
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/testDir
-rw-rw-rw-   0 root root         17 2023-01-18 15:54 kodo://example-bucket/testDir/input.txt
```

#### get命令

```shell
$ hadoop fs -get kodo:///testDir testDir1
$ ls -l -R testDir1
total 8
-rw-r--r--  1 root  staff  17 Jan 18 15:57 input.txt
```

#### 运行 wordcount 示例

```shell
hadoop jar $HADOOP_HOME/share/hadoop/mapreduce/hadoop-mapreduce-examples-{version}.jar wordcount kodo://example-bucket/testDir/input.txt kodo://example-bucket/testDir/output
```

执行成功后返回统计信息

```text
2023-01-18 16:00:49,228 INFO mapreduce.Job: Counters: 35
	File System Counters
		FILE: Number of bytes read=564062
		FILE: Number of bytes written=1899311
		FILE: Number of read operations=0
		FILE: Number of large read operations=0
		FILE: Number of write operations=0
		KODO: Number of bytes read=34
		KODO: Number of bytes written=25
		KODO: Number of read operations=3
		KODO: Number of large read operations=0
		KODO: Number of write operations=0
	Map-Reduce Framework
		Map input records=1
		Map output records=8
		Map output bytes=49
		Map output materialized bytes=55
		Input split bytes=102
		Combine input records=8
		Combine output records=6
		Reduce input groups=6
		Reduce shuffle bytes=55
		Reduce input records=6
		Reduce output records=6
		Spilled Records=12
		Shuffled Maps =1
		Failed Shuffles=0
		Merged Map outputs=1
		GC time elapsed (ms)=31
		Total committed heap usage (bytes)=538968064
	Shuffle Errors
		BAD_ID=0
		CONNECTION=0
		IO_ERROR=0
		WRONG_LENGTH=0
		WRONG_MAP=0
		WRONG_REDUCE=0
	File Input Format Counters 
		Bytes Read=17
	File Output Format Counters 
		Bytes Written=25
```

```text
$ hadoop fs -ls -R kodo://example-bucket/
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/user
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/user/root
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/testDir
-rw-rw-rw-   0 root root         17 2023-01-18 15:54 kodo://example-bucket/testDir/input.txt
drwx--xr-x   - root root          0 1970-01-01 08:00 kodo://example-bucket/testDir/output
-rw-rw-rw-   0 root root          0 2023-01-18 16:00 kodo://example-bucket/testDir/output/_SUCCESS
-rw-rw-rw-   0 root root         25 2023-01-18 16:00 kodo://example-bucket/testDir/output/part-r-00000
```

#### cat命令

```text
$ hadoop fs -cat kodo://example-bucket/testDir/output/part-r-00000
a	2
b	2
c	1
d	1
ee	1
s	1
```

## 测试

请参考文档：[test.md](./docs/test.md)。
