-- phpMyAdmin SQL Dump
-- version 4.7.4
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 18. Apr 2018 um 15:32
-- Server-Version: 10.1.28-MariaDB
-- PHP-Version: 7.1.11

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
  `cardNum` int(11) NOT NULL,
  `expDate` date NOT NULL,
  `ccv` smallint(6) NOT NULL,
  `bank` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `cards`
--

INSERT INTO `cards` (`id`, `cardNum`, `expDate`, `ccv`, `bank`) VALUES
(0, 12345, '2018-03-05', 112, 'Santander');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `interests`
--

CREATE TABLE `interests` (
  `username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `offers`
--

CREATE TABLE `offers` (
  `name` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `description` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `price` float NOT NULL,
  `publishDate` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `sold` tinyint(1) NOT NULL,
  `seller_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `offers`
--

INSERT INTO `offers` (`name`, `description`, `price`, `publishDate`, `sold`, `seller_username`, `id`) VALUES
('Iphone', 'iPhone 4 usado', 120, '2018-03-25 17:51:28', 0, 'majitoven', 24);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `offer_tags`
--

CREATE TABLE `offer_tags` (
  `offer_id` int(11) NOT NULL,
  `tag` varchar(50) CHARACTER SET utf8 COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `persons`
--

CREATE TABLE `persons` (
  `username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `password` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `full_name` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `email` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `birthDate` date NOT NULL,
  `nationality` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `card_id` int(11) DEFAULT NULL,
  `reputation` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `persons`
--

INSERT INTO `persons` (`username`, `password`, `full_name`, `email`, `birthDate`, `nationality`, `card_id`, `reputation`) VALUES
('majitoven', 'miley1823', 'maria ventura', 'mariajose_v13@hotmail.com', '2018-03-05', 'uruguaya', 12345, 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `purchases`
--

CREATE TABLE `purchases` (
  `buy_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `buyer_username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

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
-- Indizes für die Tabelle `offer_tags`
--
ALTER TABLE `offer_tags`
  ADD KEY `offer_id` (`offer_id`);

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
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `offers`
--
ALTER TABLE `offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

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
-- Constraints der Tabelle `offer_tags`
--
ALTER TABLE `offer_tags`
  ADD CONSTRAINT `offer_tags_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
