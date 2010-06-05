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
-- Dumping data for table `abitudinialimentari`
--

/*!40000 ALTER TABLE `abitudinialimentari` DISABLE KEYS */;
/*!40000 ALTER TABLE `abitudinialimentari` ENABLE KEYS */;


--
-- Dumping data for table `alimento`
--

--
-- Dumping data for table `assunzionebevande`
--

/*!40000 ALTER TABLE `assunzionebevande` DISABLE KEYS */;
/*!40000 ALTER TABLE `assunzionebevande` ENABLE KEYS */;


--
-- Dumping data for table `attivitafisica`
--

/*!40000 ALTER TABLE `attivitafisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `attivitafisica` ENABLE KEYS */;


--
-- Dumping data for table `composizione`
--


--
-- Dumping data for table `condizione`
--

/*!40000 ALTER TABLE `condizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `condizione` ENABLE KEYS */;


--
-- Dumping data for table `costituzione`
--

/*!40000 ALTER TABLE `costituzione` DISABLE KEYS */;
/*!40000 ALTER TABLE `costituzione` ENABLE KEYS */;


--
-- Dumping data for table `dieta`
--

--
-- Dumping data for table `esameclinico`
--
--
-- Dumping data for table `farmacoassunto`
--

/*!40000 ALTER TABLE `farmacoassunto` DISABLE KEYS */;
/*!40000 ALTER TABLE `farmacoassunto` ENABLE KEYS */;


--
-- Dumping data for table `fattura`
--

/*!40000 ALTER TABLE `fattura` DISABLE KEYS */;
INSERT INTO `fattura` (`idFattura`,`Importo`,`Acconto`,`ImportoSconto`,`Note`,`Descrizione`,`Data`) VALUES 
 (29,50,0,0,'','Fattura 05-06-2010 ore 18 dott.Posa','2010-06-05 16:14:50'),
 (30,50,0,10,'','fattura 06-05-2010 ore 13	','2010-06-05 16:17:47'),
 (31,50,0,0,'','fattura 05-06-2010 ore 10','2010-06-05 16:18:42');
/*!40000 ALTER TABLE `fattura` ENABLE KEYS */;


--
-- Dumping data for table `funzione`
--

--
-- Dumping data for table `ingrediente`
--

--
-- Dumping data for table `intervento`
--
--
-- Dumping data for table `intolleranzaallergia`
--
--
-- Dumping data for table `malattia`
--

/*!40000 ALTER TABLE `malattia` DISABLE KEYS */;
/*!40000 ALTER TABLE `malattia` ENABLE KEYS */;


--
-- Dumping data for table `medico`
--

/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` (`idMedico`,`CodiceFiscale`,`Cognome`,`Nome`,`DataNascita`,`Sesso`,`Indirizzo`,`Citta`,`CAP`,`Provincia`,`Specializzazione`,`Telefono1`,`Telefono2`,`Email`) VALUES 
 (10,'DGVGPP85A23A048E','Di Giovanni','Giuseppe','1985-01-23','M','via sammichele 12','Acquaviva delle fonti','70021','BA','dietologo','080751332','','digiovanni@alice.it'),
 (11,'PSAPSQ85A12A048E','Posa','Pasquale','1985-01-12','M','via laera 12','Acquaviva delle fonti','70021','BA','nutrizionista','080754432','','posapas@alice.it'),
 (12,'LCNANT60B14A048E','Loiacono','Antonio','1960-02-14','M','via bari 33','Acquaviva delle fonti','70021','BA','nutrizionista','080768881','3298834221','dottloiacono@alice.it');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;


--
-- Dumping data for table `misurazione`
--

/*!40000 ALTER TABLE `misurazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `misurazione` ENABLE KEYS */;


--
-- Dumping data for table `modalitapastofuori`
--

/*!40000 ALTER TABLE `modalitapastofuori` DISABLE KEYS */;
/*!40000 ALTER TABLE `modalitapastofuori` ENABLE KEYS */;


--
-- Dumping data for table `parametroantropometrico`
--

--
-- Dumping data for table `parametroesame`
--

--
-- Dumping data for table `pasto`
--

--
-- Dumping data for table `paziente`
--

/*!40000 ALTER TABLE `paziente` DISABLE KEYS */;
INSERT INTO `paziente` (`idPaziente`,`Codice Fiscale`,`Cognome`,`Nome`,`DataNascita`,`Sesso`,`Indirizzo`,`Citta`,`CAP`,`Provincia`,`Professione`,`Telefono1`,`Telefono2`,`Email`,`NumTesseraSanitaria`,`Note`) VALUES 
 (13,'NTTGPP86A22A048E','Nettis','Giuseppe','1986-01-22','M','via curzio 55','Acquaviva delle fonti','70021','BA','studente','3207717111','3207171223','giunet@gmail.com','334dhg358837nc',''),
 (14,'NTTFNC67B01A048E','Nettis','Francesco','1967-02-01','M','via curzio 55','Acquaviva delle fonti','70021','BA','imprenditore','0807773421','0805433654','nettis@libero.it','4445babd132das',''),
 (15,'NTTANT84H05A048E','Nettis','Antonio','1984-08-05','M','via curzio 55','Acquaviva delle fonti','70021','BA','studente','3204453221','080765541','ant.nettis@alice.it','412ABBTUI3HZN3','');
/*!40000 ALTER TABLE `paziente` ENABLE KEYS */;


--
-- Dumping data for table `personalizzazionegiornata`
--

/*!40000 ALTER TABLE `personalizzazionegiornata` DISABLE KEYS */;
/*!40000 ALTER TABLE `personalizzazionegiornata` ENABLE KEYS */;


--
-- Dumping data for table `prenotazione`
--

/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` (`idPaziente`,`idTipologiaVisita`,`DataOra`,`Note`,`idPrenotazione`) VALUES 
 (13,10,'2010-06-08 11:50:00','',32),
 (13,9,'2010-06-15 14:50:00','',33),
 (13,12,'2010-06-23 15:00:00','',34),
 (13,11,'2010-06-18 17:30:00','',35),
 (14,10,'2010-06-05 13:00:00','prima visita per il paziente',36),
 (14,9,'2010-06-07 11:00:00','',37),
 (14,11,'2010-06-17 10:00:00','',38),
 (14,12,'2010-06-25 16:00:00','',39),
 (15,10,'2010-06-05 18:00:00','',40),
 (15,9,'2010-06-11 12:02:00','',41),
 (15,9,'2010-06-15 08:00:00','',42),
 (15,9,'2010-06-22 15:00:00','',43),
 (15,11,'2010-07-02 12:00:00','',44),
 (15,12,'2010-06-30 11:00:00','',45),
 (13,10,'2010-06-05 10:00:00','',46);
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;


--
-- Dumping data for table `prescrizione`
--

--
-- Dumping data for table `prestazione`
--

/*!40000 ALTER TABLE `prestazione` DISABLE KEYS */;
INSERT INTO `prestazione` (`idMedico`,`idTurno`,`DataTurno`) VALUES 
 (10,21,'2010-06-01'),
 (10,24,'2010-06-03'),
 (10,23,'2010-06-04'),
 (10,24,'2010-06-08'),
 (10,24,'2010-06-10'),
 (10,26,'2010-06-11'),
 (10,34,'2010-06-15'),
 (10,22,'2010-06-16'),
 (10,25,'2010-06-18'),
 (10,26,'2010-06-21'),
 (10,23,'2010-06-22'),
 (10,24,'2010-06-24'),
 (10,22,'2010-06-26'),
 (10,32,'2010-06-28'),
 (10,32,'2010-06-29'),
 (11,27,'2010-06-02'),
 (11,32,'2010-06-03'),
 (11,31,'2010-06-04'),
 (11,31,'2010-06-05'),
 (11,22,'2010-06-07'),
 (11,28,'2010-06-09'),
 (11,22,'2010-06-11'),
 (11,32,'2010-06-12'),
 (11,32,'2010-06-14'),
 (11,24,'2010-06-16'),
 (11,25,'2010-06-17'),
 (11,25,'2010-06-19'),
 (11,22,'2010-06-21'),
 (11,34,'2010-06-22'),
 (11,26,'2010-06-24'),
 (11,22,'2010-06-25'),
 (11,31,'2010-06-28'),
 (11,26,'2010-06-29'),
 (11,28,'2010-06-30'),
 (12,26,'2010-06-01'),
 (12,24,'2010-06-02'),
 (12,25,'2010-06-04'),
 (12,25,'2010-06-05'),
 (12,24,'2010-06-07'),
 (12,32,'2010-06-08'),
 (12,26,'2010-06-09'),
 (12,27,'2010-06-10'),
 (12,22,'2010-06-12'),
 (12,31,'2010-06-14'),
 (12,22,'2010-06-15'),
 (12,22,'2010-06-17'),
 (12,23,'2010-06-18'),
 (12,23,'2010-06-19'),
 (12,25,'2010-06-22'),
 (12,24,'2010-06-23'),
 (12,32,'2010-06-25'),
 (12,24,'2010-06-26'),
 (12,31,'2010-06-29'),
 (12,24,'2010-06-30');
/*!40000 ALTER TABLE `prestazione` ENABLE KEYS */;


--
-- Dumping data for table `referto`
--

/*!40000 ALTER TABLE `referto` DISABLE KEYS */;
/*!40000 ALTER TABLE `referto` ENABLE KEYS */;


--
-- Dumping data for table `ricetta`
--

--
-- Dumping data for table `rilevamento`
--

/*!40000 ALTER TABLE `rilevamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `rilevamento` ENABLE KEYS */;


--
-- Dumping data for table `risultatoanalisi`
--

/*!40000 ALTER TABLE `risultatoanalisi` DISABLE KEYS */;
/*!40000 ALTER TABLE `risultatoanalisi` ENABLE KEYS */;


--
-- Dumping data for table `ruolo`
--

--
-- Dumping data for table `ruolo_funzione`

--
-- Dumping data for table `schemadietetico`
--

--
-- Dumping data for table `specifichedieta`
--

--
-- Dumping data for table `tipologiabevanda`
--

/*!40000 ALTER TABLE `tipologiabevanda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipologiabevanda` ENABLE KEYS */;


--
-- Dumping data for table `tipologiadietaspeciale`
--

/*!40000 ALTER TABLE `tipologiadietaspeciale` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipologiadietaspeciale` ENABLE KEYS */;


--
-- Dumping data for table `tipologiaintervento`
--

--
-- Dumping data for table `tipologiavisita`
--

/*!40000 ALTER TABLE `tipologiavisita` DISABLE KEYS */;
INSERT INTO `tipologiavisita` (`idTipologiaVisita`,`Tipologia`,`CostoVisita`) VALUES 
 (9,'visita di controllo',50),
 (10,'prima visita',80),
 (11,'visita nutrizionale',100),
 (12,'visita psicologica',50);
/*!40000 ALTER TABLE `tipologiavisita` ENABLE KEYS */;


--
-- Dumping data for table `turno`
--

/*!40000 ALTER TABLE `turno` DISABLE KEYS */;
INSERT INTO `turno` (`idTurno`,`OraInizio`,`OraFine`,`Nome`) VALUES 
 (21,'08:00:00','12:00:00','mattina 8-12'),
 (22,'09:00:00','13:00:00','mattina 9-13'),
 (23,'08:00:00','13:00:00','mattina 8-13'),
 (24,'09:00:00','14:00:00','mattina 9-14'),
 (25,'14:00:00','18:00:00','pomeriggio 14-18'),
 (26,'14:00:00','19:00:00','pomeriggio 14-19'),
 (27,'14:00:00','20:00:00','pomeriggio 14-20'),
 (28,'15:00:00','20:00:00','pomeriggio 15-20'),
 (29,'16:00:00','19:00:00','pomeriggio 16-19'),
 (30,'16:00:00','20:00:00','pomeriggio 16-20'),
 (31,'09:00:00','12:00:00','mattina 9-12'),
 (32,'15:00:00','18:00:00','pomeriggio 15-18'),
 (33,'16:00:00','18:00:00','pomeriggio 16-18'),
 (34,'15:00:00','19:00:00','pomeriggio 15-19');
/*!40000 ALTER TABLE `turno` ENABLE KEYS */;


--
-- Dumping data for table `utente`
--
--
-- Dumping data for table `visita`
--

/*!40000 ALTER TABLE `visita` DISABLE KEYS */;
INSERT INTO `visita` (`idVisita`,`DataOraInizio`,`DataOraFine`,`Motivazioni`,`StatoPagamento`,`Note`,`FK_Fattura`,`FK2_Medico`,`FK_Prenotazione`) VALUES 
 (20,'2010-06-05 13:00:00','2010-06-05 13:50:00','prima visita del paziente','completato','',30,10,36),
 (21,'2010-06-05 10:00:00','2010-06-05 11:20:00','prima visita del paziente','completato','',31,12,46),
 (22,'2010-06-05 18:00:00','2010-06-05 19:30:00','prima visita del paziente','completato','',29,11,40);
/*!40000 ALTER TABLE `visita` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
