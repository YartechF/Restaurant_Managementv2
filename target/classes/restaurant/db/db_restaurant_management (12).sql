-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jun 23, 2024 at 02:57 AM
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
-- Table structure for table `store_product`
--

CREATE TABLE `store_product` (
  `productID` int(11) NOT NULL,
  `storeID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `store_product`
--

INSERT INTO `store_product` (`productID`, `storeID`) VALUES
(35, 1),
(36, 1),
(35, 11),
(36, 11),
(43, 11);

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
(14, 'snack'),
(15, 'food'),
(17, 'sea foods');

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
  `ingredient_cost_typeID` int(11) NOT NULL,
  `IsProductIngredient` tinyint(4) NOT NULL,
  `Is_per_pcs` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ingredient`
--

INSERT INTO `tbl_ingredient` (`ID`, `name`, `description`, `ingredient_cost_typeID`, `IsProductIngredient`, `Is_per_pcs`) VALUES
(19, 'patty', 'for burger', 2, 1, 1),
(20, 'bun', 'burger', 2, 1, 1),
(21, 'toppings', '', 1, 1, 0);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ingredient_cost`
--

CREATE TABLE `tbl_ingredient_cost` (
  `ID` int(11) NOT NULL,
  `ingredientID` int(11) NOT NULL,
  `productID` int(11) NOT NULL,
  `quantity` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ingredient_cost`
--

INSERT INTO `tbl_ingredient_cost` (`ID`, `ingredientID`, `productID`, `quantity`) VALUES
(30, 19, 35, 3),
(31, 20, 35, 1),
(32, 21, 35, 25),
(33, 21, 36, 25),
(34, 19, 36, 1);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_ingredient_cost_type`
--

CREATE TABLE `tbl_ingredient_cost_type` (
  `ID` int(11) NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_ingredient_cost_type`
--

INSERT INTO `tbl_ingredient_cost_type` (`ID`, `name`) VALUES
(1, 'grams'),
(2, 'pcs');

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
  `ispaid` tinyint(1) DEFAULT NULL,
  `storeID` int(11) NOT NULL,
  `Date` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_invoice`
--

INSERT INTO `tbl_invoice` (`ID`, `costumer_name`, `istakeout`, `tableID`, `discount`, `ispaid`, `storeID`, `Date`) VALUES
(790, '', 0, 1, 30, 1, 1, '2024-06-20 05:21:44'),
(791, '', 0, 4, 30, 1, 1, '2024-06-20 05:23:48'),
(792, '', 0, 5, 0, 1, 11, '2024-06-21 09:31:19'),
(793, '', 0, 5, 0, 1, 11, '2024-06-20 09:32:13'),
(794, '', 0, 5, 0, 1, 11, '2024-05-20 09:32:45'),
(795, '', 0, 5, 0, 1, 11, '2024-04-20 09:33:08'),
(797, '', 0, 7, 0, 1, 1, '2024-06-22 05:34:05'),
(799, '', 0, 7, 0, 1, 1, '2024-06-22 06:04:10');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_orders`
--

CREATE TABLE `tbl_orders` (
  `ID` int(11) NOT NULL,
  `invoiceID` int(11) NOT NULL,
  `productID` int(11) DEFAULT NULL,
  `quantity` int(11) DEFAULT NULL,
  `Isdone` tinyint(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_orders`
--

INSERT INTO `tbl_orders` (`ID`, `invoiceID`, `productID`, `quantity`, `Isdone`) VALUES
(799, 790, 36, 1, 0),
(801, 791, 36, 1, 1),
(802, 792, 35, 3, 0),
(803, 793, 35, 1, 0),
(804, 794, 35, 1, 0),
(805, 795, 35, 1, 0),
(806, 795, 35, 1, 0),
(807, 797, 35, 2, 0),
(808, 797, 36, 1, 0),
(809, 799, 36, 1, 0);

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
(14, 'admin', 'admin', 'admin', 'admin'),
(15, 'mars', 'none', 'none', '0956458184'),
(16, 'inventory', 'none', 'none', '09565465'),
(17, 'order1', 'none', 'none', '0955648512'),
(18, 'pans', 'none', 'none', '09564545'),
(19, 'ipan', 'none', 'none', '0964564'),
(20, 'opans', 'none', 'none', '095545445');

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
(35, 'burger', 45, NULL, 'C:\\Users\\Administrator\\Documents\\Restaurant_Managementv2\\src\\main\\java\\restaurant\\images\\burger.png', NULL),
(36, 'pizza', 56, NULL, 'C:\\Users\\Administrator\\Documents\\Restaurant_Managementv2\\src\\main\\java\\restaurant\\images\\pizza-2.png', NULL),
(43, 'Bonefish Grill', 400, NULL, 'C:\\Users\\Administrator\\Desktop\\1382539155471-removebg-preview.png', NULL);

-- --------------------------------------------------------

--
-- Table structure for table `tbl_product_categories`
--

CREATE TABLE `tbl_product_categories` (
  `productID` int(11) DEFAULT NULL,
  `categoryID` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_product_categories`
--

INSERT INTO `tbl_product_categories` (`productID`, `categoryID`) VALUES
(35, 14),
(36, 14),
(36, 15),
(43, 14),
(43, 17);

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
(1, 'store 1', 'location: quezon.'),
(11, 'Store 2', 'location: valencia');

-- --------------------------------------------------------

--
-- Table structure for table `tbl_store_ingredient`
--

CREATE TABLE `tbl_store_ingredient` (
  `ID` int(11) NOT NULL,
  `ingredientID` int(11) DEFAULT NULL,
  `storeID` int(11) DEFAULT NULL,
  `stock` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbl_store_ingredient`
--

INSERT INTO `tbl_store_ingredient` (`ID`, `ingredientID`, `storeID`, `stock`) VALUES
(114, 18, 1, 44.35),
(115, 18, 11, 0),
(116, 16, 1, 21),
(117, 16, 11, 0),
(118, 17, 1, 16),
(119, 17, 11, 0),
(121, 19, 1, 18),
(122, 20, 1, 34),
(123, 19, 11, 7),
(124, 20, 11, 19),
(128, 21, 1, 19650),
(129, 21, 11, 24850);

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
(1, 'Table 1', 1, 0, 1, 4),
(4, 'table 2', 1, 1, 1, 4),
(5, 'table 2', 1, 1, 11, 4),
(7, 'Table 4', 1, 1, 1, 2);

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
(10, '', '', 14, 1, 1),
(11, 'mars', 'mars', 15, 2, 1),
(12, 'inventory1', 'inventory1', 16, 3, 1),
(13, 'order1', 'order1', 17, 4, 1),
(14, 'pans', 'pans', 18, 2, 11),
(15, 'ipans', 'ipans', 19, 3, 11),
(16, 'opans', 'opans', 20, 4, 11);

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
(3, 'inventory_manager'),
(4, 'order_manager');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `store_product`
--
ALTER TABLE `store_product`
  ADD KEY `productID` (`productID`);

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
  ADD KEY `ingredient_type_ID` (`ingredient_cost_typeID`);

--
-- Indexes for table `tbl_ingredient_cost`
--
ALTER TABLE `tbl_ingredient_cost`
  ADD PRIMARY KEY (`ID`),
  ADD KEY `ingredientID` (`ingredientID`),
  ADD KEY `productID` (`productID`);

--
-- Indexes for table `tbl_ingredient_cost_type`
--
ALTER TABLE `tbl_ingredient_cost_type`
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
  ADD PRIMARY KEY (`ID`),
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
-- Indexes for table `tbl_product_categories`
--
ALTER TABLE `tbl_product_categories`
  ADD KEY `productID` (`productID`),
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
  ADD PRIMARY KEY (`ID`),
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
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT for table `tbl_company`
--
ALTER TABLE `tbl_company`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `tbl_ingredient`
--
ALTER TABLE `tbl_ingredient`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=23;

--
-- AUTO_INCREMENT for table `tbl_ingredient_cost`
--
ALTER TABLE `tbl_ingredient_cost`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=35;

--
-- AUTO_INCREMENT for table `tbl_ingredient_cost_type`
--
ALTER TABLE `tbl_ingredient_cost_type`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=805;

--
-- AUTO_INCREMENT for table `tbl_orders`
--
ALTER TABLE `tbl_orders`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=810;

--
-- AUTO_INCREMENT for table `tbl_person`
--
ALTER TABLE `tbl_person`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;

--
-- AUTO_INCREMENT for table `tbl_product`
--
ALTER TABLE `tbl_product`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tbl_store`
--
ALTER TABLE `tbl_store`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `tbl_store_ingredient`
--
ALTER TABLE `tbl_store_ingredient`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=130;

--
-- AUTO_INCREMENT for table `tbl_table`
--
ALTER TABLE `tbl_table`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT for table `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `tbl_usertype`
--
ALTER TABLE `tbl_usertype`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `store_product`
--
ALTER TABLE `store_product`
  ADD CONSTRAINT `productID` FOREIGN KEY (`productID`) REFERENCES `tbl_product` (`ID`);

--
-- Constraints for table `tbl_ingredient`
--
ALTER TABLE `tbl_ingredient`
  ADD CONSTRAINT `tbl_ingredient_ibfk_1` FOREIGN KEY (`ingredient_cost_typeID`) REFERENCES `tbl_ingredient_cost_type` (`ID`);

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
-- Constraints for table `tbl_product_categories`
--
ALTER TABLE `tbl_product_categories`
  ADD CONSTRAINT `tbl_product_categories_ibfk_1` FOREIGN KEY (`productID`) REFERENCES `tbl_product` (`ID`),
  ADD CONSTRAINT `tbl_product_categories_ibfk_2` FOREIGN KEY (`categoryID`) REFERENCES `tbl_category` (`ID`);

--
-- Constraints for table `tbl_sales`
--
ALTER TABLE `tbl_sales`
  ADD CONSTRAINT `tbl_sales_ibfk_1` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`);

--
-- Constraints for table `tbl_table`
--
ALTER TABLE `tbl_table`
  ADD CONSTRAINT `tbl_table_ibfk_1` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`);

--
-- Constraints for table `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD CONSTRAINT `personID` FOREIGN KEY (`personID`) REFERENCES `tbl_person` (`ID`),
  ADD CONSTRAINT `storeID` FOREIGN KEY (`storeID`) REFERENCES `tbl_store` (`ID`),
  ADD CONSTRAINT `usertypeID` FOREIGN KEY (`usertypeID`) REFERENCES `tbl_usertype` (`ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
