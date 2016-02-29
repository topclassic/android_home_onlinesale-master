-- phpMyAdmin SQL Dump
-- version 3.2.0.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Feb 15, 2015 at 05:09 AM
-- Server version: 5.1.36
-- PHP Version: 5.3.0

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `home_onlinesale`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_product`
--

CREATE TABLE IF NOT EXISTS `tbl_product` (
  `pro_id` int(11) NOT NULL AUTO_INCREMENT,
  `pro_title` varchar(100) NOT NULL,
  `pro_desc` varchar(255) NOT NULL,
  `pro_price` float NOT NULL,
  `pro_saler` varchar(50) NOT NULL,
  `pro_date` varchar(50) NOT NULL,
  PRIMARY KEY (`pro_id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=6 ;

--
-- Dumping data for table `tbl_product`
--

INSERT INTO `tbl_product` (`pro_id`, `pro_title`, `pro_desc`, `pro_price`, `pro_saler`, `pro_date`) VALUES
(3, 'Galaxy Tab 4', '100%', 280, 'K', 'Sunday 15th of February 2015 04:40:36 AM');
