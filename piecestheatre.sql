-- phpMyAdmin SQL Dump
-- version 3.3.9.2
-- http://www.phpmyadmin.net
--
-- Serveur: localhost
-- Généré le : Mer 02 Avril 2014 à 14:51
-- Version du serveur: 5.5.9
-- Version de PHP: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Base de données: `piecestheatre`
--

-- --------------------------------------------------------

--
-- Structure de la table `chevillotte`
--

CREATE TABLE `chevillotte` (
  `nom_pieces` text NOT NULL,
  `places` int(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `chevillotte`
--

INSERT INTO `chevillotte` VALUES('Scapin', 2);
INSERT INTO `chevillotte` VALUES('Le renard', 3);
INSERT INTO `chevillotte` VALUES('Pinoccio', 1);
INSERT INTO `chevillotte` VALUES('Skyfall', 4);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `nom_user` text NOT NULL,
  `nom_pieces` text NOT NULL,
  `nb_places` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Contenu de la table `reservation`
--

INSERT INTO `reservation` VALUES('chevi', 'Le Philanthrope', 2);
INSERT INTO `reservation` VALUES('', 'Titus', 0);
INSERT INTO `reservation` VALUES('Chevi Florent', 'skyfall', 3);
INSERT INTO `reservation` VALUES('', 'Le renard', 0);
INSERT INTO `reservation` VALUES('', 'Le renard', 2);
INSERT INTO `reservation` VALUES('', 'Le renard', 0);
INSERT INTO `reservation` VALUES('', 'Skyfall', 0);
INSERT INTO `reservation` VALUES('Chevillotte Florent', 'Skyfall', 3);
