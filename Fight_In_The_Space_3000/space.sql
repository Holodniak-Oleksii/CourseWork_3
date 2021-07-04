-- phpMyAdmin SQL Dump
-- version 5.0.2
-- https://www.phpmyadmin.net/
--
-- Хост: 127.0.0.1:3306
-- Час створення: Чрв 08 2021 р., 01:07
-- Версія сервера: 5.7.31
-- Версія PHP: 7.3.21

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- База даних: `space`
--

-- --------------------------------------------------------

--
-- Структура таблиці `admin_of_space`
--

DROP TABLE IF EXISTS `admin_of_space`;
CREATE TABLE IF NOT EXISTS `admin_of_space` (
  `id_admin` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `password` varchar(20000) NOT NULL,
  `experience` int(11) NOT NULL,
  `rang` varchar(100) NOT NULL,
  PRIMARY KEY (`id_admin`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Дамп даних таблиці `admin_of_space`
--

INSERT INTO `admin_of_space` (`id_admin`, `name`, `password`, `experience`, `rang`) VALUES
(1, 'Arsak', 'e2512172abf8cc9f67fdd49eb6cacf2df71bbad3', 14238, 'res\\rang\\Тенно.png'),
(2, 'q', '22ea1c649c82946aa6e479e1ffd321e4a318b1b0', 29, 'res\\rang\\Самурай.png'),
(3, 'w', 'aff024fe4ab0fece4091de044c58c9ae4233383a', 0, 'res\\rang\\Самурай.png'),
(4, 'd', '3c363836cf4e16666669a25da280a1865c2d2874', 295, 'res\\rang\\Даймьо.png');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
