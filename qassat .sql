-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Sep 11, 2019 at 11:55 AM
-- Server version: 10.1.30-MariaDB
-- PHP Version: 5.6.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `qassat`
--

-- --------------------------------------------------------

--
-- Table structure for table `aqsat`
--

CREATE TABLE `aqsat` (
  `id` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL DEFAULT '-1',
  `client_id` int(20) NOT NULL,
  `month_payment` varchar(20) COLLATE utf8_bin NOT NULL,
  `value` float NOT NULL,
  `tahsel` tinyint(4) NOT NULL DEFAULT '0',
  `received_date` date NOT NULL,
  `order` int(20) NOT NULL,
  `done` tinyint(1) NOT NULL DEFAULT '0',
  `CRI` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `area`
--

CREATE TABLE `area` (
  `id` int(20) NOT NULL,
  `area_name` varchar(50) COLLATE utf8_bin NOT NULL,
  `branchName` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `area`
--

INSERT INTO `area` (`id`, `area_name`, `branchName`) VALUES
(22, 'حي السلام', 'الاسماعيلية');

-- --------------------------------------------------------

--
-- Table structure for table `blocked_client`
--

CREATE TABLE `blocked_client` (
  `id` int(20) NOT NULL,
  `client_id` int(20) NOT NULL,
  `branchName` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `id` int(20) NOT NULL,
  `name` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `branch`
--

INSERT INTO `branch` (`id`, `name`) VALUES
(15, 'الاسماعيلية');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `id` int(20) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `card_num` text COLLATE utf8_bin NOT NULL,
  `address1` text COLLATE utf8_bin NOT NULL,
  `address2` text COLLATE utf8_bin NOT NULL,
  `mob1` varchar(20) COLLATE utf8_bin NOT NULL,
  `mob2` varchar(20) COLLATE utf8_bin NOT NULL,
  `job` text COLLATE utf8_bin NOT NULL,
  `job_place` text COLLATE utf8_bin NOT NULL,
  `blocked` tinyint(1) NOT NULL,
  `areaName` varchar(50) COLLATE utf8_bin NOT NULL,
  `branchName` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`id`, `name`, `card_num`, `address1`, `address2`, `mob1`, `mob2`, `job`, `job_place`, `blocked`, `areaName`, `branchName`) VALUES
(0, '--', '--', '--', '--', '--', '--', '--', '--', 0, 'حي السلام', 'الاسماعيلية');

-- --------------------------------------------------------

--
-- Table structure for table `client_recieved_items`
--

CREATE TABLE `client_recieved_items` (
  `id` int(20) NOT NULL,
  `clientId` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `handOver` float NOT NULL,
  `quantity` int(20) NOT NULL,
  `received_date` date NOT NULL,
  `month_count` int(20) NOT NULL,
  `total_payment` float NOT NULL,
  `payment_type` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `history`
--

CREATE TABLE `history` (
  `id` int(20) NOT NULL,
  `person_id` int(20) NOT NULL,
  `person_type` text COLLATE utf8_bin NOT NULL,
  `date` date NOT NULL,
  `operation` text COLLATE utf8_bin NOT NULL,
  `type` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` bigint(20) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `quantity` int(20) NOT NULL,
  `cons_rate` int(20) NOT NULL,
  `buy_price` float NOT NULL,
  `cash_price` float NOT NULL,
  `Taqset_price` float NOT NULL,
  `selling_percentage` float NOT NULL,
  `cons` int(11) NOT NULL,
  `branchName` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `mandob`
--

CREATE TABLE `mandob` (
  `id` int(20) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `card_num` varchar(20) COLLATE utf8_bin NOT NULL,
  `address` text COLLATE utf8_bin NOT NULL,
  `mob` varchar(20) COLLATE utf8_bin NOT NULL,
  `total_recieved_money` float NOT NULL,
  `total_required_money` float NOT NULL,
  `Tahsel_percentage` int(20) NOT NULL,
  `stopped` tinyint(1) NOT NULL DEFAULT '0',
  `areaName` varchar(50) COLLATE utf8_bin NOT NULL,
  `branchName` varchar(50) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `mandob`
--

INSERT INTO `mandob` (`id`, `name`, `card_num`, `address`, `mob`, `total_recieved_money`, `total_required_money`, `Tahsel_percentage`, `stopped`, `areaName`, `branchName`) VALUES
(0, 'متعدد', '0', '0', '0', 0, 0, 0, 0, 'حي السلام', 'الاسماعيلية');

-- --------------------------------------------------------

--
-- Table structure for table `mandobrecivables`
--

CREATE TABLE `mandobrecivables` (
  `id` int(20) NOT NULL,
  `mandobId` int(20) NOT NULL,
  `value` float NOT NULL,
  `type` varchar(20) NOT NULL,
  `clientId` int(20) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mandob_received_items`
--

CREATE TABLE `mandob_received_items` (
  `id` int(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `quantity` int(20) NOT NULL,
  `date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `multiman`
--

CREATE TABLE `multiman` (
  `id` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `CRI` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `notes`
--

CREATE TABLE `notes` (
  `id` int(20) NOT NULL,
  `title` varchar(20) COLLATE utf8_bin NOT NULL,
  `note` text COLLATE utf8_bin NOT NULL,
  `date` date NOT NULL,
  `branchName` varchar(50) COLLATE utf8_bin NOT NULL,
  `type` varchar(20) COLLATE utf8_bin NOT NULL,
  `person_id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `partners`
--

CREATE TABLE `partners` (
  `id` int(20) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `mobile` varchar(20) COLLATE utf8_bin NOT NULL,
  `payments` float NOT NULL,
  `received_money` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `payment`
--

CREATE TABLE `payment` (
  `id` int(20) NOT NULL,
  `title` varchar(20) COLLATE utf8_bin NOT NULL,
  `date` date NOT NULL,
  `quantity` int(20) NOT NULL,
  `type` varchar(20) COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `received_money`
--

CREATE TABLE `received_money` (
  `id` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `title` varchar(20) COLLATE utf8_bin NOT NULL,
  `date` date NOT NULL,
  `value` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `reciepts`
--

CREATE TABLE `reciepts` (
  `id` int(20) NOT NULL,
  `client_id` int(20) NOT NULL,
  `area_id` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `month` int(20) NOT NULL,
  `printed` tinyint(1) NOT NULL,
  `count` int(20) NOT NULL,
  `price` float NOT NULL,
  `last` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `return_items`
--

CREATE TABLE `return_items` (
  `id` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `item_id` bigint(20) NOT NULL,
  `client_id` int(20) NOT NULL,
  `branchName` text COLLATE utf8_bin NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `tahsel`
--

CREATE TABLE `tahsel` (
  `id` int(20) NOT NULL,
  `mandob_id` int(20) NOT NULL,
  `paid` float NOT NULL,
  `done` tinyint(1) NOT NULL,
  `Tahsel_percentage` int(20) NOT NULL,
  `qest_id` int(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(20) NOT NULL,
  `name` text COLLATE utf8_bin NOT NULL,
  `password` varchar(20) COLLATE utf8_bin NOT NULL,
  `logged` tinyint(1) NOT NULL,
  `branchName` varchar(50) COLLATE utf8_bin NOT NULL,
  `p1` tinyint(1) NOT NULL,
  `p2` tinyint(1) NOT NULL,
  `p3` tinyint(1) NOT NULL,
  `p4` tinyint(1) NOT NULL,
  `p5` tinyint(1) NOT NULL,
  `p6` tinyint(1) NOT NULL,
  `p7` tinyint(1) NOT NULL,
  `p8` tinyint(1) NOT NULL,
  `p9` tinyint(1) NOT NULL,
  `p10` tinyint(1) NOT NULL,
  `p11` tinyint(1) NOT NULL,
  `p12` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_bin;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `logged`, `branchName`, `p1`, `p2`, `p3`, `p4`, `p5`, `p6`, `p7`, `p8`, `p9`, `p10`, `p11`, `p12`) VALUES
(100008, 'admin', '123456789', 1, 'الاسماعيلية', 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `aqsat`
--
ALTER TABLE `aqsat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CRI` (`CRI`),
  ADD KEY `mandob_id` (`mandob_id`),
  ADD KEY `client_id` (`client_id`);

--
-- Indexes for table `area`
--
ALTER TABLE `area`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `area_name` (`area_name`),
  ADD KEY `branchName` (`branchName`);

--
-- Indexes for table `blocked_client`
--
ALTER TABLE `blocked_client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `branchName` (`branchName`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name` (`name`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `areaName` (`areaName`),
  ADD KEY `branchName` (`branchName`);

--
-- Indexes for table `client_recieved_items`
--
ALTER TABLE `client_recieved_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `clientId` (`clientId`),
  ADD KEY `mandob_id` (`mandob_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `history`
--
ALTER TABLE `history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `mandob`
--
ALTER TABLE `mandob`
  ADD PRIMARY KEY (`id`),
  ADD KEY `areaName` (`areaName`),
  ADD KEY `branchName` (`branchName`);

--
-- Indexes for table `mandobrecivables`
--
ALTER TABLE `mandobrecivables`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mandobId` (`mandobId`),
  ADD KEY `clientId` (`clientId`);

--
-- Indexes for table `mandob_received_items`
--
ALTER TABLE `mandob_received_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mandob_received_items_ibfk_1` (`mandob_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `multiman`
--
ALTER TABLE `multiman`
  ADD PRIMARY KEY (`id`),
  ADD KEY `CRI` (`CRI`),
  ADD KEY `firstMan` (`mandob_id`);

--
-- Indexes for table `notes`
--
ALTER TABLE `notes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `branchName` (`branchName`);

--
-- Indexes for table `partners`
--
ALTER TABLE `partners`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `payment`
--
ALTER TABLE `payment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `received_money`
--
ALTER TABLE `received_money`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mandob_id` (`mandob_id`);

--
-- Indexes for table `reciepts`
--
ALTER TABLE `reciepts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `return_items`
--
ALTER TABLE `return_items`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_id` (`client_id`),
  ADD KEY `mandob_id` (`mandob_id`),
  ADD KEY `item_id` (`item_id`);

--
-- Indexes for table `tahsel`
--
ALTER TABLE `tahsel`
  ADD PRIMARY KEY (`id`),
  ADD KEY `mandob_id` (`mandob_id`),
  ADD KEY `qest_id` (`qest_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`),
  ADD KEY `branchName` (`branchName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `aqsat`
--
ALTER TABLE `aqsat`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `area`
--
ALTER TABLE `area`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `blocked_client`
--
ALTER TABLE `blocked_client`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `history`
--
ALTER TABLE `history`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT for table `mandob`
--
ALTER TABLE `mandob`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `mandobrecivables`
--
ALTER TABLE `mandobrecivables`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `mandob_received_items`
--
ALTER TABLE `mandob_received_items`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `multiman`
--
ALTER TABLE `multiman`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `notes`
--
ALTER TABLE `notes`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `partners`
--
ALTER TABLE `partners`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment`
--
ALTER TABLE `payment`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `received_money`
--
ALTER TABLE `received_money`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `reciepts`
--
ALTER TABLE `reciepts`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `return_items`
--
ALTER TABLE `return_items`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tahsel`
--
ALTER TABLE `tahsel`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100009;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `aqsat`
--
ALTER TABLE `aqsat`
  ADD CONSTRAINT `aqsat_ibfk_1` FOREIGN KEY (`CRI`) REFERENCES `client_recieved_items` (`id`),
  ADD CONSTRAINT `aqsat_ibfk_2` FOREIGN KEY (`mandob_id`) REFERENCES `mandob` (`id`),
  ADD CONSTRAINT `aqsat_ibfk_3` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`);

--
-- Constraints for table `area`
--
ALTER TABLE `area`
  ADD CONSTRAINT `area_ibfk_1` FOREIGN KEY (`branchName`) REFERENCES `branch` (`name`);

--
-- Constraints for table `blocked_client`
--
ALTER TABLE `blocked_client`
  ADD CONSTRAINT `blocked_client_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `blocked_client_ibfk_2` FOREIGN KEY (`branchName`) REFERENCES `branch` (`name`);

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `client_ibfk_1` FOREIGN KEY (`areaName`) REFERENCES `area` (`area_name`),
  ADD CONSTRAINT `client_ibfk_2` FOREIGN KEY (`branchName`) REFERENCES `branch` (`name`);

--
-- Constraints for table `client_recieved_items`
--
ALTER TABLE `client_recieved_items`
  ADD CONSTRAINT `client_recieved_items_ibfk_1` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `client_recieved_items_ibfk_3` FOREIGN KEY (`mandob_id`) REFERENCES `mandob` (`id`),
  ADD CONSTRAINT `client_recieved_items_ibfk_4` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

--
-- Constraints for table `mandob`
--
ALTER TABLE `mandob`
  ADD CONSTRAINT `mandob_ibfk_1` FOREIGN KEY (`areaName`) REFERENCES `area` (`area_name`),
  ADD CONSTRAINT `mandob_ibfk_2` FOREIGN KEY (`branchName`) REFERENCES `branch` (`name`);

--
-- Constraints for table `mandobrecivables`
--
ALTER TABLE `mandobrecivables`
  ADD CONSTRAINT `mandobrecivables_ibfk_1` FOREIGN KEY (`mandobId`) REFERENCES `mandob` (`id`),
  ADD CONSTRAINT `mandobrecivables_ibfk_2` FOREIGN KEY (`clientId`) REFERENCES `client` (`id`);

--
-- Constraints for table `mandob_received_items`
--
ALTER TABLE `mandob_received_items`
  ADD CONSTRAINT `mandob_received_items_ibfk_1` FOREIGN KEY (`mandob_id`) REFERENCES `mandob` (`id`),
  ADD CONSTRAINT `mandob_received_items_ibfk_2` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

--
-- Constraints for table `multiman`
--
ALTER TABLE `multiman`
  ADD CONSTRAINT `multiman_ibfk_1` FOREIGN KEY (`CRI`) REFERENCES `client_recieved_items` (`id`),
  ADD CONSTRAINT `multiman_ibfk_2` FOREIGN KEY (`mandob_id`) REFERENCES `mandob` (`id`);

--
-- Constraints for table `notes`
--
ALTER TABLE `notes`
  ADD CONSTRAINT `notes_ibfk_1` FOREIGN KEY (`branchName`) REFERENCES `branch` (`name`);

--
-- Constraints for table `received_money`
--
ALTER TABLE `received_money`
  ADD CONSTRAINT `received_money_ibfk_1` FOREIGN KEY (`mandob_id`) REFERENCES `mandob` (`id`);

--
-- Constraints for table `return_items`
--
ALTER TABLE `return_items`
  ADD CONSTRAINT `return_items_ibfk_1` FOREIGN KEY (`client_id`) REFERENCES `client` (`id`),
  ADD CONSTRAINT `return_items_ibfk_2` FOREIGN KEY (`mandob_id`) REFERENCES `mandob` (`id`),
  ADD CONSTRAINT `return_items_ibfk_3` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);

--
-- Constraints for table `tahsel`
--
ALTER TABLE `tahsel`
  ADD CONSTRAINT `tahsel_ibfk_2` FOREIGN KEY (`qest_id`) REFERENCES `aqsat` (`id`);

--
-- Constraints for table `users`
--
ALTER TABLE `users`
  ADD CONSTRAINT `users_ibfk_1` FOREIGN KEY (`branchName`) REFERENCES `branch` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
