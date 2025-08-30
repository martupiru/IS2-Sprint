CREATE DATABASE  IF NOT EXISTS `videojuegos` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `videojuegos`;
-- MySQL dump 10.13  Distrib 8.0.43, for Win64 (x86_64)
--
-- Host: localhost    Database: videojuegos
-- ------------------------------------------------------
-- Server version	8.0.43

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categorias`
--

DROP TABLE IF EXISTS `categorias`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categorias` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categorias`
--

LOCK TABLES `categorias` WRITE;
/*!40000 ALTER TABLE `categorias` DISABLE KEYS */;
INSERT INTO `categorias` VALUES (1,_binary '','Aventura'),(2,_binary '','RPG'),(3,_binary '','Sandbox'),(4,_binary '','Plataforma');
/*!40000 ALTER TABLE `categorias` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `estudios`
--

DROP TABLE IF EXISTS `estudios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `estudios` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `estudios`
--

LOCK TABLES `estudios` WRITE;
/*!40000 ALTER TABLE `estudios` DISABLE KEYS */;
INSERT INTO `estudios` VALUES (1,_binary '','Square Enix'),(2,_binary '','Toby Fox'),(3,_binary '','Mojang'),(4,_binary '','Shrek Studios');
/*!40000 ALTER TABLE `estudios` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `videojuegos`
--

DROP TABLE IF EXISTS `videojuegos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `videojuegos` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activo` bit(1) NOT NULL,
  `descripcion` varchar(100) DEFAULT NULL,
  `fecha_lanzamiento` datetime(6) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `precio` float NOT NULL,
  `stock` smallint NOT NULL,
  `titulo` varchar(255) NOT NULL,
  `fk_categoria` bigint NOT NULL,
  `fk_estudio` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK54ev8cdsfhbdfxufd9wc8g23x` (`fk_categoria`),
  KEY `FKev9v8m1hnvqlpne73pgko027c` (`fk_estudio`),
  CONSTRAINT `FK54ev8cdsfhbdfxufd9wc8g23x` FOREIGN KEY (`fk_categoria`) REFERENCES `categorias` (`id`),
  CONSTRAINT `FKev9v8m1hnvqlpne73pgko027c` FOREIGN KEY (`fk_estudio`) REFERENCES `estudios` (`id`),
  CONSTRAINT `videojuegos_chk_1` CHECK (((`precio` <= 10000) and (`precio` >= 5))),
  CONSTRAINT `videojuegos_chk_2` CHECK (((`stock` >= 0) and (`stock` <= 10000)))
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `videojuegos`
--

LOCK TABLES `videojuegos` WRITE;
/*!40000 ALTER TABLE `videojuegos` DISABLE KEYS */;
INSERT INTO `videojuegos` VALUES (1,_binary '','Aventura y exploración con Lara Croft','2013-03-05 00:00:00.000000','sprintgames.png',1500,30,'Tomb Raider',1,1),(2,_binary '','RPG independiente con elecciones morales','2015-09-14 03:00:00.000000','sprintgames.png',500,25,'Undertale',2,2),(3,_binary '','Construcción y aventuras en mundo abierto','2011-11-17 03:00:00.000000','sprintgames.png',1200,0,'Minecraft',3,3),(4,_binary '','Aventura basada en la película Shrek 2 locuraaa','2004-05-18 03:00:00.000000','sprintgames.png',800,15,'Shrek2',4,4),(5,_binary '','Juego que le gusta al Emi :)','2024-03-09 03:00:00.000000','1756330338284.jpg',950,9,'Skyrim',1,3),(6,_binary '','Juego para reirte de la marti jajajaj','2025-07-10 03:00:00.000000','peak.jpeg',159,7,'Peak',1,4),(7,_binary '','Sale COC?','2015-11-13 03:00:00.000000','1756421094654.png',6,45,'Clash of Clans',1,1);
/*!40000 ALTER TABLE `videojuegos` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-08-30 17:59:13
