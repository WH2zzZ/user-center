CREATE TABLE `iot_user`
(
    `id`          bigint(32)    NOT NULL COMMENT '主键id',
    `mobile`      varchar(15)   NOT NULL COMMENT '手机号',
    `username`    varchar(32)   NOT NULL DEFAULT '' COMMENT '用户名',
    `password`    varchar(32)   NOT NULL DEFAULT '' COMMENT '密码',
    `wechat_id`   varchar(32)   NOT NULL DEFAULT '' COMMENT '微信号',
    `nick_name`   varchar(50)   NOT NULL DEFAULT '' COMMENT '昵称',
    `image_url`   varchar(256)  NOT NULL DEFAULT '' COMMENT '头像',
    `address`     varchar(1024) NOT NULL DEFAULT '' COMMENT '地址',
    `email`       varchar(30)   NOT NULL DEFAULT '' COMMENT '邮箱',
    `gender`      tinyint(4)    NOT NULL DEFAULT '0' COMMENT '性别 1：男 2：女 0：保密',
    `birthday`    int(13)       NOT NULL DEFAULT '0' COMMENT '生日 13位时间戳',
    `status`      tinyint(4)    NOT NULL DEFAULT '1' COMMENT '状态',

    `creator`     varchar(50)   NOT NULL,
    `create_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    `updator`     varchar(50)   NOT NULL,
    `update_time` timestamp     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

CREATE INDEX mobile_idx ON iot_user (`mobile`) USING BTREE;
CREATE INDEX username_idx ON iot_user (`username`) USING BTREE;