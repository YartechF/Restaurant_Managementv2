-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 17, 2024 at 05:04 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_restaurant_management`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbl_category`
--

CREATE TABLE `tbl_category` (
  `ID` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_category`
--

INSERT INTO `tbl_category` (`ID`, `name`) VALUES
(3, 'food'),
(7, 'drinks'),
(9, 'sea foods');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_company`
--

CREATE TABLE `tbl_company` (
  `ID` int(11) NOT NULL,
  `name` varchar(35) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ingredient`
--

CREATE TABLE `tbl_ingredient` (
  `ID` int(11) NOT NULL,
  `name` varchar(75) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `ingredient_type_ID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ingredient`
--

INSERT INTO `tbl_ingredient` (`ID`, `name`, `description`, `ingredient_type_ID`) VALUES
(3, 'patty', 'for burger', 2),
(4, 'bun', 'for burger', 2),
(5, 'susaige', NULL, 2),
(6, 'topping', NULL, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ingredient_cost`
--

CREATE TABLE `tbl_ingredient_cost` (
  `ingredientID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `quantity` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ingredient_cost`
--

INSERT INTO `tbl_ingredient_cost` (`ingredientID`, `productID`, `quantity`) VALUES
(4, 23, 1),
(3, 23, 2),
(5, 24, 5),
(6, 24, 0.25);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ingredient_type`
--

CREATE TABLE `tbl_ingredient_type` (
  `ID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ingredient_type`
--

INSERT INTO `tbl_ingredient_type` (`ID`, `name`) VALUES
(1, 'Per kg'),
(2, 'Per pcs');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_invoice`
--

CREATE TABLE `tbl_invoice` (
  `ID` int(11) NOT NULL,
  `costumer_name` varchar(50) DEFAULT NULL,
  `istakeout` tinyint(1) DEFAULT NULL,
  `tableID` int(11) DEFAULT NULL,
  `discount` double DEFAULT NULL,
  `ispaid` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_invoice`
--

INSERT INTO `tbl_invoice` (`ID`, `costumer_name`, `istakeout`, `tableID`, `discount`, `ispaid`) VALUES
(1, 'blah', 0, 1, 0, 0),
(2, '', 0, 1, 0, 0),
(3, '', 0, 1, 0, 0),
(4, '', 0, 1, 0, 0),
(5, '', 0, 1, 0, 0),
(6, '', 0, 1, 0, 0),
(7, '', 0, 1, 0, 0),
(8, '', 0, 1, 0, 0),
(9, '', 0, 1, 0, 0),
(10, '', 0, 1, 0, 0),
(11, '', 0, 1, 0, 0),
(12, '', 0, 1, 0, 0),
(13, '', 0, 1, 0, 0),
(14, '', 0, 1, 0, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_orders`
--

CREATE TABLE `tbl_orders` (
  `invoiceID` int(11) NOT NULL,
  `productID` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_orders`
--

INSERT INTO `tbl_orders` (`invoiceID`, `productID`, `quantity`) VALUES
(1, 23, 3),
(1, 24, 3),
(3, 23, 3),
(12, 23, 1),
(13, 24, 1),
(13, 23, 1),
(14, 24, 1),
(14, 23, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_person`
--

CREATE TABLE `tbl_person` (
  `ID` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `address` varchar(50) NOT NULL,
  `Email` varchar(50) NOT NULL,
  `Contact` varchar(13) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_person`
--

INSERT INTO `tbl_person` (`ID`, `name`, `address`, `Email`, `Contact`) VALUES
(1, 'felbert', 'lumintao', 'felbertyarte6@gmail.com', '09556646149'),
(2, 'felix', 'lumintao', 'lixte@gmail.com', '095656648'),
(4, '', '', '', ''),
(5, 'gsdfg', 'gsd', 'gdfsg', 'gds'),
(6, 'gsdfg', 'gsd', 'gdfsg', 'gds'),
(7, 'gs  dfg', 'gsd', 'gdfsg', 'gds'),
(8, 'gs  dfg', 'gsd', 'gdfsg', 'gds'),
(9, 'gs  dfg', 'gsd', 'gdfsg', 'gds'),
(10, '', '', '', ''),
(11, 'ben', 'lumintao', 'gmagfg', '094245;1245'),
(12, 'ben', 'lumintao', 'gmagfg', '094245;1245');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_product`
--

CREATE TABLE `tbl_product` (
  `ID` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `price` double NOT NULL,
  `categoryID` int(11) DEFAULT NULL,
  `picture` varchar(200) NOT NULL,
  `type` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_product`
--

INSERT INTO `tbl_product` (`ID`, `name`, `price`, `categoryID`, `picture`, `type`) VALUES
(23, 'Burger', 99, 3, 'C:\\Users\\Administrator\\Desktop\\burger3.png', NULL),
(24, 'pizza', 100, 3, 'C:\\Users\\Administrator\\Downloads\\pizza-2.png', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_sales`
--

CREATE TABLE `tbl_sales` (
  `ID` int(11) NOT NULL,
  `product_name` varchar(50) NOT NULL,
  `price` double DEFAULT NULL,
  `transanction_date` date DEFAULT NULL,
  `storeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_sales`
--

INSERT INTO `tbl_sales` (`ID`, `product_name`, `price`, `transanction_date`, `storeID`) VALUES
(2, 'burger', 100, '2024-05-06', 1),
(3, 'pizaa', 500, '2024-05-06', 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_store`
--

CREATE TABLE `tbl_store` (
  `ID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `decription` varchar(150) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_store`
--

INSERT INTO `tbl_store` (`ID`, `name`, `decription`) VALUES
(1, 'store 1', 'location: valencia buk'),
(2, 'store 2', 'location : lum,intao');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_store_ingredient`
--

CREATE TABLE `tbl_store_ingredient` (
  `ingredientID` int(11) DEFAULT NULL,
  `storeID` int(11) DEFAULT NULL,
  `stock` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_store_ingredient`
--

INSERT INTO `tbl_store_ingredient` (`ingredientID`, `storeID`, `stock`) VALUES
(3, 1, 300),
(4, 1, 300),
(3, 2, 50),
(4, 2, 50),
(5, 1, 100),
(6, 1, 100);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_table`
--

CREATE TABLE `tbl_table` (
  `ID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL,
  `isActive` tinyint(1) DEFAULT NULL,
  `isAvailable` tinyint(1) DEFAULT NULL,
  `storeID` int(11) DEFAULT NULL,
  `capacity` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_table`
--

INSERT INTO `tbl_table` (`ID`, `name`, `isActive`, `isAvailable`, `storeID`, `capacity`) VALUES
(1, 'Table 1', 1, 1, 1, 4),
(2, 'table 2', 1, 1, 1, 2);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_user`
--

CREATE TABLE `tbl_user` (
  `ID` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `personID` int(11) NOT NULL,
  `usertypeID` int(11) NOT NULL,
  `storeID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_user`
--

INSERT INTO `tbl_user` (`ID`, `username`, `password`, `personID`, `usertypeID`, `storeID`) VALUES
(1, '', '', 1, 2, 1),
(2, 'felix', 'felix421', 1, 2, 1),
(3, '  ', '   ', 1, 2, 1),
(4, 'mary-cris', '123456', 9, 2, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_usertype`
--

CREATE TABLE `tbl_usertype` (
  `ID` int(11) NOT NULL,
  `usertype` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_usertype`
--

INSERT INTO `tbl_usertype` (`ID`, `usertype`) VALUES
(1, 'admin'),
(2, 'stuff'),
(3, 'inventory_manager');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_company`
--
ALTER TABLE `tbl_company`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_ingredient`
--
ALTER TABLE `tbl_ingredient`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ingredient_type_ID` (`ingredient_type_ID`);

--
-- Indexes for table `tbl_ingredient_cost`
--
ALTER TABLE `tbl_ingredient_cost`
  ADD KEY `ingredientID` (`ingredientID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `tbl_ingredient_type`
--
ALTER TABLE `tbl_ingredient_type`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `tableID` (`tableID`);

--
-- Indexes for table `tbl_orders`
--
ALTER TABLE `tbl_orders`
  ADD KEY `invoiceID` (`invoiceID`),
  ADD KEY `fk_orders_products` (`productID`);

--
-- Indexes for table `tbl_person`
--
ALTER TABLE `tbl_person`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_product`
--
ALTER TABLE `tbl_product`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `categoryID` (`categoryID`);

--
-- Indexes for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `storeID` (`storeID`);

--
-- Indexes for table `tbl_store`
--
ALTER TABLE `tbl_store`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tbl_store_ingredient`
--
ALTER TABLE `tbl_store_ingredient`
  ADD KEY `ingredientID` (`ingredientID`),
  ADD KEY `storeID` (`storeID`);

--
-- Indexes for table `tbl_table`
--
ALTER TABLE `tbl_table`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `storeID` (`storeID`);

--
-- Indexes for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `personID` (`personID`),
  ADD KEY `usertypeID` (`usertypeID`),
  ADD KEY `storeID` (`storeID`);

--
-- Indexes for table `tbl_usertype`
--
ALTER TABLE `tbl_usertype`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `tbl_category`
--
ALTER TABLE `tbl_category`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `tbl_company`
--
ALTER TABLE `tbl_company`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_ingredient`
--
ALTER TABLE `tbl_ingredient`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `tbl_ingredient_type`
--
ALTER TABLE `tbl_ingredient_type`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT for table `tbl_person`
--
ALTER TABLE `tbl_person`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT for table `tbl_product`
--
ALTER TABLE `tbl_product`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_store`
--
ALTER TABLE `tbl_store`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_table`
--
ALTER TABLE `tbl_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT for table `tbl_usertype`
--
ALTER TABLE `tbl_usertype`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `tbl_ingredient`
--
ALTER TABLE `tbl_ingredient`
  ADD CONSTRAINT `tbl_ingredient_ibfk_1` FOREIGN KEY (`ingredient_type_ID`) REFERENCES `tbl_ingredient_type` (`ID`);

--
-- Constraints for table `tbl_ingredient_cost`
--
ALTER TABLE `tbl_ingredient_cost`
  ADD CONSTRAINT `tbl_ingredient_cost_ibfk_1` FOREIGN KEY (`ingredientID`) REFERENCES `tbl_ingredient` (`ID`),
  ADD CONSTRAINT `tbl_ingredient_cost_ibfk_2` FOREIGN KEY (`productID`) REFERENCES `tbl_product` (`ID`);

--
-- Constraints for table `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  ADD CONSTRAINT `tbl_invoice_ibfk_1` FOREIGN KEY (`tableID`) REFERENCES `tbl_table` (`ID`);

--
-- Constraints for table `tbl_orders`
--
ALTER TABLE `tbl_orders`
  ADD CONSTRAINT `fk_orders_products` FOREIGN KEY (`productID`) REFERENCES `tbl_product` (`ID`),
  ADD CONSTRAINT `tbl_orders_ibfk_1` FOREIGN KEY (`invoiceID`) REFERENCES `tbl_invoice` (`ID`);

--
-- Constraints for table `tbl_product`
--
ALTER TABLE `tbl_product`
  ADD CONSTRAINT `tbl_product_ibfk_1` FOREIGN KEY (`categoryID`) REFERENCES `tbl_category` (`ID`);

--
-- Constraints for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  ADD CONSTRAINT `tbl_sales_ibfk_1` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`);

--
-- Constraints for table `tbl_store_ingredient`
--
ALTER TABLE `tbl_store_ingredient`
  ADD CONSTRAINT `tbl_store_ingredient_ibfk_1` FOREIGN KEY (`ingredientID`) REFERENCES `tbl_ingredient` (`ID`),
  ADD CONSTRAINT `tbl_store_ingredient_ibfk_2` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`);

--
-- Constraints for table `tbl_table`
--
ALTER TABLE `tbl_table`
  ADD CONSTRAINT `tbl_table_ibfk_1` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`);

--
-- Constraints for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD CONSTRAINT `tbl_user_ibfk_1` FOREIGN KEY (`personID`) REFERENCES `tbl_person` (`ID`),
  ADD CONSTRAINT `tbl_user_ibfk_2` FOREIGN KEY (`usertypeID`) REFERENCES `tbl_usertype` (`ID`),
  ADD CONSTRAINT `tbl_user_ibfk_3` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
