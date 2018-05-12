-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 13. Mai 2018 um 01:29
-- Server-Version: 10.1.31-MariaDB
-- PHP-Version: 7.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `garagesale`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `cards`
--

CREATE TABLE `cards` (
  `id` int(11) NOT NULL,
  `cardNum` varchar(19) COLLATE utf8_spanish_ci NOT NULL,
  `expDate` date NOT NULL,
  `ccv` varchar(4) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `cards`
--

INSERT INTO `cards` (`id`, `cardNum`, `expDate`, `ccv`) VALUES
(1, '12345', '2018-03-05', '112'),
(5, '1234123412341234', '2021-08-01', '6789'),
(6, '123456789123456789', '2034-12-01', '789');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `interests`
--

CREATE TABLE `interests` (
  `username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL,
  `price` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `interests`
--

INSERT INTO `interests` (`username`, `offer_id`, `price`) VALUES
('majitoven', 24, 1000);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `offers`
--

CREATE TABLE `offers` (
  `name` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `description` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `price` float NOT NULL,
  `startTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `sold` tinyint(1) NOT NULL DEFAULT '0',
  `seller_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `id` int(11) NOT NULL,
  `state` tinyint(4) NOT NULL,
  `activePeriod` int(11) NOT NULL,
  `latitude` double DEFAULT NULL,
  `longitude` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `offers`
--

INSERT INTO `offers` (`name`, `description`, `price`, `startTime`, `sold`, `seller_username`, `id`, `state`, `activePeriod`, `latitude`, `longitude`) VALUES
('MacbookPro1', 'es una macbook que. balboa', 1200, '2018-05-02 17:33:28', 0, 'majitoven', 1, 0, 3, NULL, NULL),
('Iphone', 'iPhone 4 usado', 120, '2018-04-23 18:47:56', 0, 'majitoven', 24, 0, 3, NULL, NULL),
('prueba prueba 123', 'Esta es una descripción.', 1.23, '2018-05-12 16:03:21', 0, 'testuser', 52, 2, 3, 0, 0),
('Prueba con tags, fotos y ubicación!', 'Se vende una prueba. Amo esta app! :)', 7.89, '2018-05-12 16:05:37', 0, 'testuser', 53, 0, 7, 0, 0),
('Compraos esta app!', 'Esperamos que finalmente funciona todo con la crea', 32.17, '2018-05-12 23:19:51', 0, 'majitoven', 54, 2, 14, 37.3376829, -5.561727899999999),
('TV con cuadrado verde', 'Se vende una televisión de un ingeniero de Google.', 599.99, '2018-05-12 23:26:07', 0, 'majitoven', 55, 0, 7, 37.4418834, -122.14301950000001);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `persons`
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
-- Daten für Tabelle `persons`
--

INSERT INTO `persons` (`username`, `password`, `name`, `email`, `birthDate`, `nationality`, `card_id`, `reputation`) VALUES
('majitoven', 'miley1823', 'maria ventura', 'mariajose_v13@hotmail.com', '2018-03-05', 'uruguaya', 12345, 0),
('testuser', 'test6789', 'Testiño Test Testosteron', 'test@test.tr', '1995-10-12', 'Turquía', 5, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `purchases`
--

CREATE TABLE `purchases` (
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `buyer_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tags`
--

CREATE TABLE `tags` (
  `offer_id` int(11) NOT NULL,
  `tag` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `tags`
--

INSERT INTO `tags` (`offer_id`, `tag`) VALUES
(52, 'tag1'),
(52, 'tag2'),
(52, 'tag3'),
(53, 'yolo'),
(53, 'sw4g'),
(53, '¿qué?'),
(53, 'cuatro'),
(54, 'sevilla'),
(54, 'oferta'),
(54, 'estupendo'),
(54, 'todoFunciona!!!'),
(55, 'android'),
(55, 'cam'),
(55, 'sample');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `cards`
--
ALTER TABLE `cards`
  ADD PRIMARY KEY (`id`);

--
-- Indizes für die Tabelle `interests`
--
ALTER TABLE `interests`
  ADD KEY `username` (`username`),
  ADD KEY `offer_id` (`offer_id`);

--
-- Indizes für die Tabelle `offers`
--
ALTER TABLE `offers`
  ADD PRIMARY KEY (`id`),
  ADD KEY `seller_username` (`seller_username`);

--
-- Indizes für die Tabelle `persons`
--
ALTER TABLE `persons`
  ADD PRIMARY KEY (`username`),
  ADD KEY `card_id` (`card_id`);

--
-- Indizes für die Tabelle `purchases`
--
ALTER TABLE `purchases`
  ADD KEY `offer_id` (`offer_id`),
  ADD KEY `buyer_username` (`buyer_username`);

--
-- Indizes für die Tabelle `tags`
--
ALTER TABLE `tags`
  ADD KEY `offer_id` (`offer_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `cards`
--
ALTER TABLE `cards`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT für Tabelle `offers`
--
ALTER TABLE `offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=56;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `interests`
--
ALTER TABLE `interests`
  ADD CONSTRAINT `interests_ibfk_1` FOREIGN KEY (`username`) REFERENCES `persons` (`username`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `interests_ibfk_2` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `offers`
--
ALTER TABLE `offers`
  ADD CONSTRAINT `offers_ibfk_1` FOREIGN KEY (`seller_username`) REFERENCES `persons` (`username`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints der Tabelle `tags`
--
ALTER TABLE `tags`
  ADD CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
