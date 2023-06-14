# 配置项

| 配置项                                                          | 说明                                                                                                                     |    默认值    | 是否必填 |
|:-------------------------------------------------------------|:-----------------------------------------------------------------------------------------------------------------------|:---------:|:----:|
| fs.qiniu.auth.accessKey                                      | 七牛云对象存储的 access key                                                                                                    |   None    | YES  |
| fs.qiniu.auth.secretKey                                      | 七牛云对象存储的 secret key                                                                                                    |   None    | YES  |
| fs.kodo.impl                                                 | 配置Hadoop的文件系统实现类                                                                                                       |   None    | YES  |
| fs.AbstractFileSystem.kodo.impl                              | 配置Hadoop的抽象文件系统实现类                                                                                                     |   None    | YES  |
| fs.defaultFS                                                 | 配置Hadoop的默认文件系统                                                                                                        |   None    |  NO  |
| fs.qiniu.proxy.enable                                        | 是否启用代理配置                                                                                                               |   false   |  NO  |
| fs.qiniu.proxy.host                                          | 代理服务器地址                                                                                                                | 127.0.0.1 |  NO  |
| fs.qiniu.proxy.port                                          | 代理服务器端口                                                                                                                |   8080    |  NO  |
| fs.qiniu.proxy.user                                          | 代理服务器用户名                                                                                                               |   None    |  NO  |
| fs.qiniu.proxy.pass                                          | 代理服务器密码                                                                                                                |   None    |  NO  |
| fs.qiniu.proxy.type                                          | 代理服务器类型                                                                                                                |   HTTP    |  NO  |
| fs.qiniu.download.useHttps                                   | 下载文件时是否使用HTTPS                                                                                                         |   true    |  NO  |
| fs.qiniu.download.blockSize                                  | 分块下载文件时每个块的大小。单位为字节                                                                                                    |  4194304  |  NO  |
| fs.qiniu.download.domain                                     | 下载文件时使用的域名，默认自动使用源站域名，用户可配置该项使用CDN加速域名                                                                                 |   None    |  NO  |
| fs.qiniu.download.sign.enable                                | 是否启用下载签名                                                                                                               |   true    |  NO  |
| fs.qiniu.download.sign.expires                               | 下载签名有效期，单位为秒                                                                                                           |    180    |  NO  |
| fs.qiniu.download.cache.disk.enable                          | 是否启用磁盘缓存                                                                                                               |   false   |  NO  |
| fs.qiniu.download.cache.disk.expires                         | 磁盘缓存过期时间，单位为秒。默认值为一天                                                                                                   |   86400   |  NO  |
| fs.qiniu.download.cache.disk.blocks                          | 磁盘缓存的最大块数                                                                                                              |    120    |  NO  |
| fs.qiniu.download.cache.disk.dir                             | 磁盘缓存路径                                                                                                                 |   None    |  NO  |
| fs.qiniu.download.cache.memory.enable                        | 是否启用内存缓存                                                                                                               |   true    |  NO  |
| fs.qiniu.download.cache.memory.blocks                        | 内存缓存的最大块数                                                                                                              |    25     |  NO  |
| fs.qiniu.download.random.enable                              | 是否启用随机读取优化                                                                                                             |   false   |  NO  |
| fs.qiniu.download.random.blockSize                           | 随机读取优化时每个块的大小。单位为字节                                                                                                    |   65536   |  NO  |
| fs.qiniu.download.random.maxBlocks                           | 随机读取优化时最大的块数                                                                                                           |    100    |  NO  |
| fs.qiniu.useHttps                                            | 是否使用HTTPS协议进行文件上传，文件管理                                                                                                 |   true    |  NO  |
| fs.qiniu.upload.useDefaultUpHostIfNone                       | 如果从uc服务器获取的域名上传失败，则使用默认上传域名                                                                                            |   true    |  NO  |
| fs.qiniu.upload.sign.expires                                 | 上传签名有效期，单位为秒                                                                                                           |  604800   |  NO  |
| fs.qiniu.upload.accUpHostFirst                               | 是否优先使用加速域名                                                                                                             |   false   |  NO  |
| fs.qiniu.upload.maxConcurrentTasks                           | 最大并发上传任务数                                                                                                              |    10     |  NO  |
| fs.qiniu.upload.v2.enable                                    | 是否启用七牛云V2分片上传                                                                                                          |   true    |  NO  |
| fs.qiniu.upload.v2.blockSize                                 | 七牛云V2分片上传时每个分片的大小。单位为字节                                                                                                | 33554432  |  NO  |
| fs.qiniu.upload.bufferSize                                   | hadoop 与 qiniu-java-sdk 之间的上传管道缓冲区大小。单位为字节                                                                             | 16777216  |  NO  |
| fs.qiniu.client.cache.enable                                 | 是否启用七牛云客户端缓存，将针对文件系统操作做部分优化                                                                                            |   true    |  NO  |
| fs.qiniu.client.cache.maxCapacity                            | 七牛云客户端最大缓存数                                                                                                            |    100    |  NO  |
| fs.qiniu.client.list.useListV2                               | 是否使用 list v2 api 进行文件列举                                                                                                |   false   |  NO  |
| fs.qiniu.client.list.singleRequestLimit                      | 单次列举文件时最大的文件数                                                                                                          |   1000    |  NO  |
| fs.qiniu.client.list.bufferSize                              | 文件列举生产者的生产缓冲区大小                                                                                                        |    100    |  NO  |
| fs.qiniu.client.list.offerTimeout                            | 文件列举生产者的生产缓冲区满时的轮询等待时间。单位为毫秒                                                                                           |    10     |  NO  |
| fs.qiniu.client.copy.listProducer.useListV2                  | 文件拷贝时的文件列举生产者是否使用 list v2 api 进行文件列举                                                                                   |   false   |  NO  |
| fs.qiniu.client.copy.listProducer.singleRequestLimit         | 文件拷贝时的文件列举生产者单次列举文件时最大的文件数                                                                                             |   1000    |  NO  |
| fs.qiniu.client.copy.listProducer.bufferSize                 | 文件拷贝时的文件列举生产者文件列举生产者的生产缓冲区大小                                                                                           |    100    |  NO  |
| fs.qiniu.client.copy.listProducer.offerTimeout               | 文件拷贝时的文件列举生产者的生产缓冲区满时的轮询等待时间。单位为毫秒                                                                                     |    10     |  NO  |
| fs.qiniu.client.copy.batchConsumer.bufferSize                | 文件拷贝时的消费者的消费缓冲区大小                                                                                                      |   1000    |  NO  |
| fs.qiniu.client.copy.batchConsumer.count                     | 文件拷贝时的消费者的数量                                                                                                           |     4     |  NO  |
| fs.qiniu.client.copy.batchConsumer.singleBatchRequestLimit   | 文件拷贝时的消费者单次消费请求的最大文件数                                                                                                  |    200    |  NO  |
| fs.qiniu.client.copy.batchConsumer.pollTimeout               | 文件拷贝时的消费缓冲区空时的轮询等待时间。单位为毫秒                                                                                             |    10     |  NO  |
| fs.qiniu.client.delete.listProducer.useListV2                | 文件删除时的文件列举生产者是否使用 list v2 api 进行文件列举                                                                                   |   false   |  NO  |
| fs.qiniu.client.delete.listProducer.singleRequestLimit       | 文件删除时的文件列举生产者单次列举文件时最大的文件数                                                                                             |   1000    |  NO  |
| fs.qiniu.client.delete.listProducer.bufferSize               | 文件删除时的文件列举生产者文件列举生产者的生产缓冲区大小                                                                                           |    100    |  NO  |
| fs.qiniu.client.delete.listProducer.offerTimeout             | 文件删除时的文件列举生产者的生产缓冲区满时的轮询等待时间。单位为毫秒                                                                                     |    10     |  NO  |
| fs.qiniu.client.delete.batchConsumer.bufferSize              | 文件删除时的消费者的消费缓冲区大小                                                                                                      |   1000    |  NO  |
| fs.qiniu.client.delete.batchConsumer.count                   | 文件删除时的消费者的数量                                                                                                           |     4     |  NO  |
| fs.qiniu.client.delete.batchConsumer.singleBatchRequestLimit | 文件删除时的消费者单次消费请求的最大文件数                                                                                                  |    200    |  NO  |
| fs.qiniu.client.delete.batchConsumer.pollTimeout             | 文件删除时的消费缓冲区空时的轮询等待时间。单位为毫秒                                                                                             |    10     |  NO  |
| fs.qiniu.logger.level                                        | 日志级别                                                                                                                   |   INFO    |  NO  |
| fs.qiniu.test.useMock                                        | 测试环境是否使用mock进行集成测试                                                                                                     |   false   |  NO  |
| fs.qiniu.customRegion.id                                     | 自定义区域id                                                                                                                |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.ucServer                   | 注意{id}为`fs.qiniu.customRegion.id`中自定义的id。该配置项用于自定义区域ucServer地址，如果该字段被配置了，则后续字段将自动配置，无需手工配置。例如`https://uc.qiniuapi.com` |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.rsHost                     | kodo 服务的 rs 域名，例如`rs-z0.qiniuapi.com`                                                                                  |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.rsfHost                    | kodo 服务的 rsf 域名，例如`rsf-z0.qiniuapi.com`                                                                                |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.apiHost                    | kodo 服务的 api 域名，例如`api.qiniuapi.com`                                                                                   |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.iovipHost                  | kodo 服务的 iovip 域名，例如`iovip.qiniuio.com`                                                                                |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.accUpHosts                 | kodo 服务的加速上传域名，例如`upload.qiniup.com`，如果有多个，请使用逗号分割                                                                     |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.srcUpHosts                 | kodo 服务的源站上传域名，例如`up.qiniup.com`，如果有多个，请使用逗号分割                                                                         |   None    |  NO  |
| fs.qiniu.customRegion.custom.{id}.ioSrcHost                  | kodo 服务的源站下载域名，例如 `kodo-cn-east-1.qiniucs.com                                                                          |   None    |  NO  |
