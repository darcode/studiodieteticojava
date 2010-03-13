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

/*!40000 ALTER TABLE `funzione` DISABLE KEYS */;
INSERT INTO `funzione` (`idFunzione`,`Descrizione`) VALUES 
 (1,'Registra Utente'),
 (2,'Statistiche'),
 (3,'Prenota Visita'),
 (4,'Registra Visita'),
 (5,'Interrogazioni dinamiche'),
 (6,'MenuStatisticheHandler'),
 (7,'MenuAnamnesi'),
 (8,'MenuDinamiche'),
 (9,'MenuEsameClinico'),
 (10,'MenuGrafici'),
 (11,'MenuMedico'),
 (12,'MenuParametroAntropometrico'),
 (13,'MenuPaziente'),
 (14,'MenuPrenotaVisita'),
 (15,'MenuRegistraVisita'),
 (16,'MenuRegistrazione'),
 (17,'MenuRilevazioneParametroAntro'),
 (18,'MenuRisultatoAnalisi'),
 (19,'MenuMedicoRicerca'),
 (20,'MenuPazienteRicerca'),
 (21,'MenuDietaRicerca'),
 (22,'MenuTurniRicerca'),
 (23,'MenuVisitaRicerca'),
 (24,'FUNZIONE_PAZIENTE'),
 (25,'FUNZIONE_PRENOTAZIONE_VISITA'),
 (26,'FUNZIONE_DIETA'),
 (27,'FUNZIONE_ANAMNESI'),
 (28,'FUNZIONE_VISITA'),
 (29,'FUNZIONE_REGISTRAZIONE'),
 (30,'FUNZIONE_FATTURA_VISITA'),
 (31,'FUNZIONE_VISITA_CONT'),
 (32,'FUNZIONE_MEDICO_INS'),
 (33,'FUNZIONE_MEDICO_UPDATE'),
 (34,'MenuEsameClinicoRicerca'),
 (35,'MenuParametroAntropometricoRicerca');
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
 (1,12),
 (3,12),
 (1,13),
 (2,13),
 (3,13),
 (1,14),
 (2,14),
 (1,15),
 (2,15),
 (3,15),
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
 (1,29),
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
