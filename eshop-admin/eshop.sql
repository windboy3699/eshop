-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- 主机： localhost
-- 生成日期： 2020-05-23 09:22:31
-- 服务器版本： 8.0.17
-- PHP 版本： 7.3.7

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- 数据库： `eshop`
--

-- --------------------------------------------------------

--
-- 表的结构 `system_group`
--

CREATE TABLE `system_group` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `menus` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `system_menu`
--

CREATE TABLE `system_menu` (
  `id` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `level` tinyint(1) NOT NULL,
  `topid` int(11) NOT NULL,
  `path` varchar(100) NOT NULL DEFAULT '',
  `link` varchar(255) NOT NULL DEFAULT '',
  `visible` tinyint(1) NOT NULL DEFAULT '1',
  `sort` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- 表的结构 `system_user`
--

CREATE TABLE `system_user` (
  `id` int(11) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) CHARACTER SET utf8 COLLATE utf8_bin NOT NULL DEFAULT '',
  `group_id` smallint(6) NOT NULL,
  `realname` varchar(45) NOT NULL DEFAULT '',
  `add_user` varchar(45) NOT NULL DEFAULT '',
  `last_login` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01',
  `update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `create_time` timestamp NOT NULL DEFAULT '1970-01-01 00:00:01'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- 转储表的索引
--

--
-- 表的索引 `system_group`
--
ALTER TABLE `system_group`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `system_menu`
--
ALTER TABLE `system_menu`
  ADD PRIMARY KEY (`id`);

--
-- 表的索引 `system_user`
--
ALTER TABLE `system_user`
  ADD PRIMARY KEY (`id`);

--
-- 在导出的表使用AUTO_INCREMENT
--

--
-- 使用表AUTO_INCREMENT `system_group`
--
ALTER TABLE `system_group`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `system_menu`
--
ALTER TABLE `system_menu`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- 使用表AUTO_INCREMENT `system_user`
--
ALTER TABLE `system_user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
