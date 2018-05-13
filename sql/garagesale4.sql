-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 13-05-2018 a las 18:03:56
-- Versión del servidor: 10.1.31-MariaDB
-- Versión de PHP: 5.6.34

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `garagesale`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `cards`
--

CREATE TABLE `cards` (
  `id` int(11) NOT NULL,
  `cardNum` varchar(19) COLLATE utf8_spanish_ci NOT NULL,
  `expDate` date NOT NULL,
  `ccv` varchar(4) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `cards`
--

INSERT INTO `cards` (`id`, `cardNum`, `expDate`, `ccv`) VALUES
(1, '12345', '2018-03-05', '112');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `interests`
--

CREATE TABLE `interests` (
  `username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `interests`
--

INSERT INTO `interests` (`username`, `offer_id`, `price`) VALUES
('mery1313', 24, 10909090),
('majitoven', 24, 888888888);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `offers`
--

CREATE TABLE `offers` (
  `name` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `description` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `price` float NOT NULL,
  `startTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sold` tinyint(1) NOT NULL,
  `seller_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `id` int(11) NOT NULL,
  `state` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `activePeriod` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `offers`
--

INSERT INTO `offers` (`name`, `description`, `price`, `startTime`, `sold`, `seller_username`, `id`, `state`, `activePeriod`) VALUES
('Iphone', 'iPhone 4 usado', 1209, '2018-05-12 18:03:47', 1, 'majitoven', 24, 'usado', 3),
('prueba', 'asjhkfaksj', 121212, '2018-05-07 22:00:00', 0, 'majitoven', 1313, 's', 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `offer_tags`
--

CREATE TABLE `offer_tags` (
  `offer_id` int(11) NOT NULL,
  `tag` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persons`
--

CREATE TABLE `persons` (
  `username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `name` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `birthDate` date NOT NULL,
  `nationality` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `card_id` int(11) DEFAULT NULL,
  `reputation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `persons`
--

INSERT INTO `persons` (`username`, `password`, `name`, `email`, `birthDate`, `nationality`, `card_id`, `reputation`) VALUES
('majitoven', 'miley1823', 'maria ventura', 'mariajose_v13@hotmail.com', '2018-03-05', 'uruguaya', 12345, 0),
('mery1313', 'hola', 'maria', 'marianos', '2018-05-01', 'españa', 1244111, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `purchases`
--

CREATE TABLE `purchases` (
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `buyer_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `purchases`
--

INSERT INTO `purchases` (`buy_time`, `buyer_username`, `offer_id`, `price`) VALUES
('2018-05-12 18:03:47', 'majitoven', 24, 888888888);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ranking`
--

CREATE TABLE `ranking` (
  `seller_username` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `buyer_username` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `value` float NOT NULL,
  `offer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;

--
-- Volcado de datos para la tabla `ranking`
--

INSERT INTO `ranking` (`seller_username`, `buyer_username`, `value`, `offer_id`) VALUES
('majitoven', 'majitoven', 3, 24);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `cards`
--
ALTER TABLE `cards`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `interests`
--
ALTER TABLE `interests`
  ADD KEY `username` (`username`),
  ADD KEY `offer_id` (`offer_id`);

--
-- Indices de la tabla `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `seller_username` (`seller_username`);

--
-- Indices de la tabla `offer_tags`
--
ALTER TABLE `offer_tags`
  ADD KEY `offer_id` (`offer_id`);

--
-- Indices de la tabla `persons`
--
ALTER TABLE `persons`
  ADD PRIMARY KEY (`username`),
  ADD KEY `card_id` (`card_id`);

--
-- Indices de la tabla `purchases`
--
ALTER TABLE `purchases`
  ADD KEY `offer_id` (`offer_id`),
  ADD KEY `buyer_username` (`buyer_username`);

--
-- Indices de la tabla `ranking`
--
ALTER TABLE `ranking`
  ADD PRIMARY KEY (`seller_username`,`buyer_username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `cards`
--
ALTER TABLE `cards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `offers`
--
ALTER TABLE `offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1314;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `interests`
--
ALTER TABLE `interests`
  ADD CONSTRAINT `interests_ibfk_1` FOREIGN KEY (`username`) REFERENCES `persons` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `interests_ibfk_2` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `offers`
--
ALTER TABLE `offers`
  ADD CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`seller_username`) REFERENCES `persons` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `offer_tags`
--
ALTER TABLE `offer_tags`
  ADD CONSTRAINT `offer_tags_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
