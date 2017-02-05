-- phpMyAdmin SQL Dump
-- version 3.5.2.2
-- http://www.phpmyadmin.net
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 31-01-2016 a las 13:06:24
-- Versión del servidor: 5.5.27
-- Versión de PHP: 5.4.7

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `agenda`
--

CREATE SCHEMA `agenda`;

USE `agenda`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `contactos`
--

CREATE TABLE IF NOT EXISTS `contactos` (
  `id_contacto` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11) NOT NULL,
  `nombre_contacto` varchar(100) NOT NULL,
  `direccion_contacto` varchar(100) NOT NULL,
  `telefono_contacto` varchar(45) NOT NULL,
  PRIMARY KEY (`id_contacto`),
  KEY `id_usuario_idx` (`id_usuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=19 ;

--
-- Volcado de datos para la tabla `contactos`
--

INSERT INTO `contactos` (`id_contacto`, `id_usuario`, `nombre_contacto`, `direccion_contacto`, `telefono_contacto`) VALUES
(1, 1, 'José Manuel Condes', 'Leganés, CP: 28917', '+34-657622322'),
(2, 1, 'Miguel Mamolar', 'San Lorenzo de El Escorial', '+34-654543355'),
(3, 1, 'David Navarro de la Morena', 'Villalba', '+34-632234554'),
(4, 1, 'Lidia Martín Martín', 'Torrelodones', '+34-674433221'),
(5, 1, 'Jie Zhou', 'Madrid CP: 28000', '+34-673145776'),
(6, 1, 'Andrey Cattalin', 'Alcalá de Henares', '+34-677522111'),
(7, 1, 'Blake Lively', 'Beverly-Hills', '+334-677345112'),
(8, 1, 'Bill Gates', 'Sillicon Valley', '+552-553333222'),
(9, 1, 'Barack Obama', 'White House', 'Clasified'),
(10, 1, 'Ridley Scott', 'California, USA', '+334-125533222'),
(11, 1, 'François Hollande', 'Palacio del Eliseo', 'Clasified'),
(12, 1, 'Amancio Ortega', 'New York', '+334-444221555'),
(13, 1, 'Elsa Pataky', 'Beverly-Hills', '+334-443221267'),
(14, 1, 'Jessica Biel', 'Beverly-Hills', '+334-412121447'),
(15, 1, 'Robert de Niro', 'Washington DC', '+326-434325356'),
(16, 1, 'Milla Jovovich', 'París, Francia', '+221-555292929'),
(17, 1, 'Lindsay Lohan', 'California, USA', '+221-442666777'),
(18, 1, 'Al Pacino', 'New York, USA', '+226-455435343');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE IF NOT EXISTS `usuarios` (
  `id_usuario` int(11) NOT NULL AUTO_INCREMENT,
  `nombre_usuario` varchar(100) NOT NULL,
  `password_usuario` varchar(45) NOT NULL,
  `telefono_usuario` varchar(45) NOT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `nombre_usuario_UNIQUE` (`nombre_usuario`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=2 ;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id_usuario`, `nombre_usuario`, `password_usuario`, `telefono_usuario`) VALUES
(1, 'jose', 'jose', '655555555');

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `contactos`
--
ALTER TABLE `contactos`
  ADD CONSTRAINT `id_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id_usuario`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
