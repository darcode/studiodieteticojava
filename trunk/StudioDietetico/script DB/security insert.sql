-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version	5.0.45-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


USE studiodietetico;

--
-- Dumping data for table `funzione`
--
delete from utente;
delete from ruolo_funzione;
delete from funzione;
delete from ruolo;

/*!40000 ALTER TABLE `funzione` DISABLE KEYS */;
INSERT INTO `funzione` (`idFunzione`,`Descrizione`) VALUES
 (1,'Registra Utente'),
 (2,'Statistiche'),
 (3,'Prenota Visita'),
 (4,'Registra Visita'),
 (5,'Interrogazioni dinamiche'),
 (6,'Menu Statistiche'),
 (7,'Menu Anamnesi'),
 (8,'Menu Query Dinamiche'),
 (9,'Menu Esame Clinico'),
 (10,'Menu Statistiche Automatiche'),
 (11,'Menu Gestione Medici'),
 (13,'Gestione Pazienti'),
 (16,'Gestione Utenti'),
 (17,'Rilevazione Parametro Antropometrico'),
 (18,'Risultato Analisi'),
 (19,'Ricerca Medico'),
 (20,'Ricerca Paziente'),
 (21,'Ricerca Dieta'),
 (22,'Gestione Turni'),
 (23,'Gestione Visita'),
 (24,'Dettagli Paziente'),
 (25,'Prenotazione Visita'),
 (26,'Gestione Dieta'),
 (27,'Gestione Anamnesi'),
 (28,'Dettaglio Visita'),
 (30,'Fattura Visita'),
 (31,'Conto Visita'),
 (32,'Inserimento Medico'),
 (33,'Modifica Medico'),
 (34,'Gestione Esame Clinico'),
 (35,'Gestione Parametri Antropometrici'),
 (36,'Menu Fatture'),
 (37,'Menu Prenotazioni');
/*!40000 ALTER TABLE `funzione` ENABLE KEYS */;


--
-- Dumping data for table `ruolo`
--

/*!40000 ALTER TABLE `ruolo` DISABLE KEYS */;
INSERT INTO `ruolo` (`idRuolo`,`descrizione`) VALUES 
 (1,'Amministratore'),
 (2,'Segreteria'),
 (3,'Medico');
/*!40000 ALTER TABLE `ruolo` ENABLE KEYS */;


--
-- Dumping data for table `ruolo_funzione`
--

/*!40000 ALTER TABLE `ruolo_funzione` DISABLE KEYS */;
INSERT INTO `ruolo_funzione` (`idRuolo`,`idFunzione`) VALUES 
 (1,1),
 (2,1),
 (1,2),
 (3,2),
 (1,3),
 (2,3),
 (1,4),
 (2,4),
 (3,4),
 (1,5),
 (3,5),
 (1,6),
 (3,6),
 (1,7),
 (3,7),
 (1,8),
 (3,8),
 (1,9),
 (3,9),
 (1,10),
 (3,10),
 (1,11),
 (2,11),
 (3,11),
 (1,13),
 (2,13),
 (3,13),
 (1,16),
 (1,17),
 (3,17),
 (1,18),
 (3,18),
 (1,19),
 (2,19),
 (3,19),
 (1,20),
 (2,20),
 (3,20),
 (1,21),
 (3,21),
 (1,22),
 (2,22),
 (3,22),
 (1,23),
 (2,23),
 (3,23),
 (1,24),
 (2,24),
 (3,24),
 (1,25),
 (2,25),
 (1,26),
 (3,26),
 (1,27),
 (3,27),
 (1,28),
 (3,28),
 (1,30),
 (2,30),
 (1,31),
 (2,31),
 (1,32),
 (2,32),
 (1,33),
 (2,33),
 (3,33),
 (1,34),
 (3,34),
 (1,35),
 (3,35);
/*!40000 ALTER TABLE `ruolo_funzione` ENABLE KEYS */;


--
-- Dumping data for table `utente`
--

/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
INSERT INTO `utente` (`idUtente`,`nomeUtente`,`password`,`idRuolo`) VALUES 
 (1,'admin','admin',1),
 (2,'segretaria','segretaria',2),
 (3,'medico','medico',3);
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
