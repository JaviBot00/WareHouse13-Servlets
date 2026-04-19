-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: warehouse13
-- ------------------------------------------------------
-- Server version	8.0.45

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Current Database: `warehouse13`
--

/*!40000 DROP DATABASE IF EXISTS `warehouse13`*/;

CREATE DATABASE /*!32312 IF NOT EXISTS*/ `warehouse13` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;

USE `warehouse13`;

--
-- Table structure for table `perishable_products`
--

DROP TABLE IF EXISTS `perishable_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `perishable_products` (
  `product_code` varchar(16) NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  `expiration_date` char(8) NOT NULL,
  PRIMARY KEY (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `perishable_products`
--

LOCK TABLES `perishable_products` WRITE;
/*!40000 ALTER TABLE `perishable_products` DISABLE KEYS */;
INSERT INTO `perishable_products` VALUES ('INK55665F','Toner b/w genérico HP 8750',79.99,18,0,'20260713'),('PRNT7890A','Impresora multifunción con Wi-Fi',89.99,0,1,'20240215'),('SCNR2345A','Escáner de documentos portátil A4',49.95,0,1,'20240330'),('USB34560A','Memoria USB 16GB resistente al agua',19.99,0,1,'20231231'),('WEBC1234A','Webcam HD 720p con micrófono integrado',29.99,0,1,'20240101');
/*!40000 ALTER TABLE `perishable_products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `product_code` varchar(16) NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `stock` int NOT NULL DEFAULT '0',
  `retired` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`product_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('AURC3456L','Auriculares inalámbricos con cancelación de ruido',129.99,23,0),('CARG5678O','Cargador rápido USB-C 65W con 2 puertos',45.80,34,0),('COOL6789I','Base refrigeradora para portátil con 3 ventiladores',29.95,41,0),('DISK1234R','Disco duro externo 1TB USB-C resistente al agua',79.99,18,0),('HDMI9012A','Cable HDMI de alta velocidad 1.5 metros',12.50,0,1),('HUBB2345M','Hub USB 3.0 de 4 puertos con alimentación',24.75,56,0),('KEYB5678A','Teclado de membrana con cable USB',14.99,0,1),('LAPD5678A','Base para portátil con ventilador y soporte ajustable',24.99,0,1),('LAPD9012P','Soporte ajustable para portátil de aluminio',39.99,50,0),('MICR2345U','Micrófono USB de condensador para streaming',65.30,27,0),('MONS4567T','Monitor portátil 15.6 pulgadas Full HD',189.50,12,0),('MONS9012A','Monitor de ordenador de 24 pulgadas Full HD',149.99,0,1),('MOUSE1234','Ratón óptico básico con cable USB',9.99,0,1),('PADT8901Y','Alfombrilla de ratón XXL con base de goma',19.99,89,0),('RATN9012K','Ratón inalámbrico ergonómico con 5 botones',34.50,67,0),('SPEK6789A','Altavoces estéreo para ordenador con subwoofer',39.99,0,1),('TECL5678X','Teclado mecánico RGB con switches rojos',89.99,45,0),('WEBC7890P','Webcam Full HD 1080p con micrófono integrado',59.90,32,0);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-04-19 12:00:44
