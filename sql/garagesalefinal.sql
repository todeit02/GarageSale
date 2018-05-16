-- phpMyAdmin SQL Dump
-- version 4.7.9
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost
-- Tiempo de generación: 16-05-2018 a las 15:49:31
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
  `isSold` int(1) NOT NULL,
  `seller_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `id` int(11) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `durationDays` int(11) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `offers`
--

INSERT INTO `offers` (`name`, `description`, `price`, `startTime`, `isSold`, `seller_username`, `id`, `state`, `durationDays`, `latitude`, `longitude`) VALUES
('Iphone', 'iPhone 4 usado', 1209, '2018-05-16 12:51:55', 1, 'majitoven', 24, 0, 3, 37.3376829, -5.561727899999999),
('prueba', 'asjhkfaksj', 121212, '2018-05-07 22:00:00', 0, 'majitoven', 1313, 0, 3, 0, 0),
('holjjjijiji', 'bibi', 34, '2018-05-14 12:38:46', 0, 'majitoven', 1314, 1, 3, 37.42199750000001, -122.08399609374999),
('Iphone jhashfas', 'aaaa', 12, '2018-05-14 14:40:59', 0, 'majitoven', 1315, 1, 3, 0, 0),
('nuevommmaielProd', 'nono', 1, '2018-05-15 15:12:43', 0, 'majitoven', 1316, 0, 3, 0, 0),
('Nuevalanfsibsfa', '123', 321, '2018-05-14 17:49:26', 0, 'majitoven', 1318, 0, 3, 0, 0),
('Pruebafinal', 'noese', 122, '2018-05-14 17:52:31', 0, 'majitoven', 1319, 0, 3, 0, 0);

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
  `reputation` int(11) NOT NULL,
  `phone` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `persons`
--

INSERT INTO `persons` (`username`, `password`, `name`, `email`, `birthDate`, `nationality`, `card_id`, `reputation`, `phone`) VALUES
('majitoven', 'miley1823', 'maria ventura', 'mariajose_v13@hotmail.com', '2018-03-05', 'uruguaya', 12345, 0, '+34645668935'),
('mery1313', 'hola', 'maria', 'marianos', '2018-05-01', 'españa', 1244111, 1, '+87885');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `purchases`
--

CREATE TABLE `purchases` (
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `buyer_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL,
  `price` int(11) NOT NULL,
  `paymentMethod` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `hasContactedSeller` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Volcado de datos para la tabla `purchases`
--

INSERT INTO `purchases` (`buy_time`, `buyer_username`, `offer_id`, `price`, `paymentMethod`, `hasContactedSeller`) VALUES
('2018-05-16 12:52:14', 'majitoven', 24, 888888888, 'cash', 0);

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

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `tags`
--

CREATE TABLE `tags` (
  `offer_id` int(11) NOT NULL,
  `tag` varchar(50) COLLATE utf16_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;

--
-- Volcado de datos para la tabla `tags`
--

INSERT INTO `tags` (`offer_id`, `tag`) VALUES
(1318, 'maiel'),
(1319, 'dontmind');

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
-- Indices de la tabla `tags`
--
ALTER TABLE `tags`
  ADD KEY `offer_id` (`offer_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1320;

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

--
-- Filtros para la tabla `tags`
--
ALTER TABLE `tags`
  ADD CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
