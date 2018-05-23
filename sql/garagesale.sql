-- phpMyAdmin SQL Dump
-- version 4.8.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 23. Mai 2018 um 23:32
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
(2, '1234123412341234', '2021-11-01', '123'),
(4, '1234567812345678', '2022-07-01', '789');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `interests`
--

CREATE TABLE `interests` (
  `username` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offer_id` int(11) NOT NULL,
  `price` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `interests`
--

INSERT INTO `interests` (`username`, `offer_id`, `price`) VALUES
('compradora', 1335, 1),
('compradora', 1335, 2),
('compradora', 1336, 1),
('compradora', 1335, 3.51),
('compradora', 1335, 4.2),
('compradora', 1335, 5.72),
('compradora', 1335, 7.0102),
('juanfe', 1338, 260),
('juanfe', 1338, 265.5);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `offers`
--

CREATE TABLE `offers` (
  `name` varchar(100) COLLATE utf8_spanish_ci NOT NULL,
  `description` varchar(400) COLLATE utf8_spanish_ci NOT NULL,
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
-- Daten für Tabelle `offers`
--

INSERT INTO `offers` (`name`, `description`, `price`, `startTime`, `isSold`, `seller_username`, `id`, `state`, `durationDays`, `latitude`, `longitude`) VALUES
('NUEVO APPLE WATCH SERIES 3 MQL22 42MM GOLD ALUMINIUM CASE + PINK SAND SPORT BAND', 'Manténgase conectado con estilo con la 42mm GPS-only Apple Watch Serie 3, que viene con un chasis de aluminio anodizado y una banda deportiva.', 279.99, '2018-05-23 15:22:44', 0, 'juanfe', 1334, 0, 3, 40.4167754, -3.7037902),
('PLANCHA DE VAPOR 2600W', '- Suela de cerámica, para un fácil deslizamiento\n- Regulador de termostato\n- Vapor vertical\n- Funciones: seco/spray/vapor/vapor vertical/autolimpieza\n- fuerza en la función spray\n- antical', 19.95, '2018-05-23 19:14:32', 1, 'juanfe', 1335, 1, 7, 37.3890924, -5.9844589),
('Sillas de Ratán', 'Se venden 4 Sillas de ratán. Bien cuidadas.', 80, '2018-05-23 15:31:54', 0, 'juanfe', 1336, 2, 7, 37.8881751, -4.7793835),
('Volkswagen Escarabajo', 'Poquito oxidado pero no es para tanto. Con depósito lleno.', 1000, '2018-05-23 15:33:39', 0, 'juanfe', 1337, 3, 14, 37.1773363, -3.5985571),
('Android TV 32\"', 'Se vende una tele con cuadrados en verde y rojo.', 280, '2018-05-23 20:27:02', 1, 'compradora', 1338, 0, 7, 37.261421, -6.9447224);

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
  `reputation` int(11) NOT NULL,
  `phone` varchar(50) COLLATE utf8_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `persons`
--

INSERT INTO `persons` (`username`, `password`, `name`, `email`, `birthDate`, `nationality`, `card_id`, `reputation`, `phone`) VALUES
('compradora', 'comprador4', 'Carmen Clementina Compradora', 'carmen@compradora.co', '1993-03-07', 'Colombia', 4, 0, '+576456789123'),
('juanfe', 'efnauj', 'Juan Fernando Quintero', 'juanfquintero@correos.es', '1985-05-21', 'España', 2, 0, '+341862793241');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `purchases`
--

CREATE TABLE `purchases` (
  `buyTime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `buyerUsername` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `offerId` int(11) NOT NULL,
  `price` float NOT NULL,
  `paymentMethod` varchar(50) COLLATE utf8_spanish_ci NOT NULL,
  `hasContactedSeller` tinyint(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_spanish_ci;

--
-- Daten für Tabelle `purchases`
--

INSERT INTO `purchases` (`buyTime`, `buyerUsername`, `offerId`, `price`, `paymentMethod`, `hasContactedSeller`) VALUES
('2018-05-23 20:24:15', 'compradora', 1335, 7.0102, 'card', 0),
('2018-05-23 20:24:15', 'compradora', 1335, 7.0102, 'card', 0),
('2018-05-23 20:27:02', 'juanfe', 1338, 265.5, '', 0);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `ranking`
--

CREATE TABLE `ranking` (
  `seller_username` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `buyer_username` varchar(50) COLLATE utf16_spanish_ci NOT NULL,
  `value` float NOT NULL,
  `offer_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `tags`
--

CREATE TABLE `tags` (
  `offer_id` int(11) NOT NULL,
  `tag` varchar(50) COLLATE utf16_spanish_ci NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf16 COLLATE=utf16_spanish_ci;

--
-- Daten für Tabelle `tags`
--

INSERT INTO `tags` (`offer_id`, `tag`) VALUES
(1334, 'apple'),
(1334, 'watch'),
(1334, 'trendy'),
(1334, 'tech'),
(1335, 'plancha'),
(1335, 'ropa'),
(1335, 'cuidado'),
(1335, 'camisas'),
(1335, 'vapor'),
(1336, 'silla'),
(1336, 'verano'),
(1336, 'afuera'),
(1336, 'amigos'),
(1336, 'relajar'),
(1337, 'escarabajo'),
(1337, 'culto'),
(1337, 'coche'),
(1337, 'vrooomvrooom'),
(1337, '4ruedas'),
(1338, 'televisión'),
(1338, 'electrónica'),
(1338, 'distracción'),
(1338, 'casa');

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
  ADD KEY `offer_id` (`offerId`),
  ADD KEY `buyer_username` (`buyerUsername`);

--
-- Indizes für die Tabelle `ranking`
--
ALTER TABLE `ranking`
  ADD PRIMARY KEY (`seller_username`,`buyer_username`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT für Tabelle `offers`
--
ALTER TABLE `offers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1339;

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
  ADD CONSTRAINT `tags_ibfk_1` FOREIGN KEY (`offer_id`) REFERENCES `offers` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
