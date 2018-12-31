-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: mysql.somenerds.xyz
-- Generation Time: Dec 31, 2018 at 02:42 PM
-- Server version: 5.6.34-log
-- PHP Version: 7.1.22

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `mics`
--

-- --------------------------------------------------------

--
-- Table structure for table `canceled_users`
--

CREATE TABLE `canceled_users` (
  `id` int(20) NOT NULL,
  `username` varchar(255) NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `token` varchar(1000) NOT NULL,
  `tokenSecret` text NOT NULL,
  `created` date NOT NULL,
  `lastLogin` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `canceled_words`
--

CREATE TABLE `canceled_words` (
  `id` int(10) NOT NULL,
  `text` varchar(300) NOT NULL,
  `severity` int(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `canceled_words`
--

INSERT INTO `canceled_words` (`id`, `text`, `severity`) VALUES
(1000, 'nigger', 1),
(1001, 'niggers', 1),
(1002, 'nigga', 2),
(1003, 'niggas', 2),
(1004, 'kike', 1),
(1005, 'faggots', 1),
(1006, 'faggot', 1),
(1007, 'fag', 1),
(1008, 'fags', 1),
(1009, 'bitch', 3),
(1010, 'cunt', 3),
(1011, 'cunts', 3),
(1012, 'bitches', 3),
(1013, 'gay', 2),
(1014, 'gays', 2),
(1015, 'beaner', 1),
(1016, 'beaners', 1),
(1017, 'gook', 1),
(1018, 'gooks', 1),
(1019, 'coon', 1),
(1020, 'coons', 1),
(1021, 'nig', 1),
(1022, 'nigs', 1),
(1023, 'nignog', 1),
(1024, 'zipperhead', 1),
(1025, 'retard', 3),
(1026, 'retards', 3),
(1027, 'tard', 3),
(1028, 'tards', 3),
(1029, 'dyke', 2),
(1030, 'dykes', 2),
(1031, 'queer', 2),
(1032, 'queers', 2);

-- --------------------------------------------------------

--
-- Table structure for table `hosts`
--

CREATE TABLE `hosts` (
  `host_id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mics`
--

CREATE TABLE `mics` (
  `mic_id` int(11) NOT NULL,
  `mic_type` int(11) NOT NULL,
  `start_date` datetime NOT NULL,
  `end_date` varchar(255) NOT NULL,
  `duration` int(11) NOT NULL,
  `IsRecurring` tinyint(1) NOT NULL,
  `RecurrencePattern` varchar(255) NOT NULL,
  `notes` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mic_host`
--

CREATE TABLE `mic_host` (
  `mic_host_id` int(11) NOT NULL,
  `mic_id` int(11) NOT NULL,
  `host_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Table structure for table `mic_type`
--

CREATE TABLE `mic_type` (
  `type_id` int(11) NOT NULL,
  `type_of_mic` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `mic_type`
--

INSERT INTO `mic_type` (`type_id`, `type_of_mic`) VALUES
(1, 'Comedy'),
(2, 'Music'),
(3, 'Anything'),
(4, 'Poetry');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `hosts`
--
ALTER TABLE `hosts`
  ADD PRIMARY KEY (`host_id`);

--
-- Indexes for table `mics`
--
ALTER TABLE `mics`
  ADD PRIMARY KEY (`mic_id`),
  ADD KEY `mics_fk0` (`mic_type`);

--
-- Indexes for table `mic_host`
--
ALTER TABLE `mic_host`
  ADD PRIMARY KEY (`mic_host_id`),
  ADD KEY `mic_host_fk0` (`mic_id`),
  ADD KEY `mic_host_fk1` (`host_id`);

--
-- Indexes for table `mic_type`
--
ALTER TABLE `mic_type`
  ADD PRIMARY KEY (`type_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `hosts`
--
ALTER TABLE `hosts`
  MODIFY `host_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mics`
--
ALTER TABLE `mics`
  MODIFY `mic_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mic_host`
--
ALTER TABLE `mic_host`
  MODIFY `mic_host_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `mic_type`
--
ALTER TABLE `mic_type`
  MODIFY `type_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `mics`
--
ALTER TABLE `mics`
  ADD CONSTRAINT `mics_fk0` FOREIGN KEY (`mic_type`) REFERENCES `mic_type` (`type_id`);

--
-- Constraints for table `mic_host`
--
ALTER TABLE `mic_host`
  ADD CONSTRAINT `mic_host_fk0` FOREIGN KEY (`mic_id`) REFERENCES `mics` (`mic_id`),
  ADD CONSTRAINT `mic_host_fk1` FOREIGN KEY (`host_id`) REFERENCES `hosts` (`host_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
