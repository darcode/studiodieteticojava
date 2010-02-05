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


--
-- Create schema studiodietetico
--

CREATE DATABASE IF NOT EXISTS studiodietetico;
USE studiodietetico;

--
-- Definition of table `abitudinialimentari`
--

DROP TABLE IF EXISTS `abitudinialimentari`;
CREATE TABLE `abitudinialimentari` (
  `idAbitudiniAlimentari` int(11) NOT NULL auto_increment,
  `NumPastiGiorno` int(11) NOT NULL,
  `PrimaColazione` tinyint(1) NOT NULL,
  `Integratori` text,
  `CibiNonGraditi` text,
  `PreferenzaSchemaDietetico` text,
  `Note` text,
  `FK5_Paziente` int(11) NOT NULL,
  PRIMARY KEY  (`idAbitudiniAlimentari`),
  KEY `FK5_Paziente` (`FK5_Paziente`),
  CONSTRAINT `FK5_Paziente` FOREIGN KEY (`FK5_Paziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `abitudinialimentari`
--

/*!40000 ALTER TABLE `abitudinialimentari` DISABLE KEYS */;
/*!40000 ALTER TABLE `abitudinialimentari` ENABLE KEYS */;


--
-- Definition of table `alimento`
--

DROP TABLE IF EXISTS `alimento`;
CREATE TABLE `alimento` (
  `idAlimento` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Tipologia` varchar(45) default NULL,
  `Calorie` int(11) default NULL,
  PRIMARY KEY  (`idAlimento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `alimento`
--

/*!40000 ALTER TABLE `alimento` DISABLE KEYS */;
/*!40000 ALTER TABLE `alimento` ENABLE KEYS */;


--
-- Definition of table `assunzionebevande`
--

DROP TABLE IF EXISTS `assunzionebevande`;
CREATE TABLE `assunzionebevande` (
  `idAbitudineAlimentare` int(11) NOT NULL,
  `idTipologiaBevanda` int(11) NOT NULL,
  `Quantita` varchar(20) default NULL,
  PRIMARY KEY  (`idAbitudineAlimentare`,`idTipologiaBevanda`),
  KEY `FK_AbitudiniAlimentari` (`idAbitudineAlimentare`),
  KEY `FK_TipologiaBevanda` (`idTipologiaBevanda`),
  CONSTRAINT `FK_AbitudiniAlimentari` FOREIGN KEY (`idAbitudineAlimentare`) REFERENCES `abitudinialimentari` (`idAbitudiniAlimentari`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipologiaBevanda` FOREIGN KEY (`idTipologiaBevanda`) REFERENCES `tipologiabevanda` (`idTipologiaBevanda`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assunzionebevande`
--

/*!40000 ALTER TABLE `assunzionebevande` DISABLE KEYS */;
/*!40000 ALTER TABLE `assunzionebevande` ENABLE KEYS */;


--
-- Definition of table `attivitafisica`
--

DROP TABLE IF EXISTS `attivitafisica`;
CREATE TABLE `attivitafisica` (
  `idAttivitaFisica` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  `Durata` varchar(45) default NULL,
  `FrequenzaSettimanale` int(11) NOT NULL,
  `FK2_Paziente` int(11) NOT NULL,
  PRIMARY KEY  (`idAttivitaFisica`),
  KEY `FK2_Paziente` (`FK2_Paziente`),
  CONSTRAINT `FK2_Paziente` FOREIGN KEY (`FK2_Paziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `attivitafisica`
--

/*!40000 ALTER TABLE `attivitafisica` DISABLE KEYS */;
/*!40000 ALTER TABLE `attivitafisica` ENABLE KEYS */;


--
-- Definition of table `composizione`
--

DROP TABLE IF EXISTS `composizione`;
CREATE TABLE `composizione` (
  `idRicetta` int(11) NOT NULL,
  `idIngrediente` int(11) NOT NULL,
  `Quantita` varchar(50) NOT NULL,
  PRIMARY KEY  (`idRicetta`,`idIngrediente`),
  KEY `FK_Ricetta` (`idRicetta`),
  KEY `FK_Ingrediente` (`idIngrediente`),
  CONSTRAINT `FK_Ingrediente` FOREIGN KEY (`idIngrediente`) REFERENCES `ingrediente` (`idIngrediente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Ricetta` FOREIGN KEY (`idRicetta`) REFERENCES `ricetta` (`idRicetta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `composizione`
--

/*!40000 ALTER TABLE `composizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `composizione` ENABLE KEYS */;


--
-- Definition of table `condizione`
--

DROP TABLE IF EXISTS `condizione`;
CREATE TABLE `condizione` (
  `idPaziente` int(11) NOT NULL,
  `idMalattia` int(11) NOT NULL,
  PRIMARY KEY  (`idPaziente`,`idMalattia`),
  KEY `FK4_Paziente` (`idPaziente`),
  KEY `FK_Malattia` (`idMalattia`),
  CONSTRAINT `FK4_Paziente` FOREIGN KEY (`idPaziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Malattia` FOREIGN KEY (`idMalattia`) REFERENCES `malattia` (`idMalattia`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `condizione`
--

/*!40000 ALTER TABLE `condizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `condizione` ENABLE KEYS */;


--
-- Definition of table `costituzione`
--

DROP TABLE IF EXISTS `costituzione`;
CREATE TABLE `costituzione` (
  `idSchemaDietetico` int(11) NOT NULL,
  `idPasto` int(11) NOT NULL,
  `idAlimento` int(11) NOT NULL,
  `Quantita` varchar(50) NOT NULL,
  PRIMARY KEY  (`idSchemaDietetico`,`idAlimento`,`idPasto`),
  KEY `FK2_SchemaDietetico` (`idSchemaDietetico`),
  KEY `FK_Pasto` (`idPasto`),
  KEY `FK1_Alimento` (`idAlimento`),
  CONSTRAINT `FK1_Alimento` FOREIGN KEY (`idAlimento`) REFERENCES `alimento` (`idAlimento`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK2_SchemaDietetico` FOREIGN KEY (`idSchemaDietetico`) REFERENCES `schemadietetico` (`idSchemaDietetico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Pasto` FOREIGN KEY (`idPasto`) REFERENCES `pasto` (`idPasto`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `costituzione`
--

/*!40000 ALTER TABLE `costituzione` DISABLE KEYS */;
/*!40000 ALTER TABLE `costituzione` ENABLE KEYS */;


--
-- Definition of table `dieta`
--

DROP TABLE IF EXISTS `dieta`;
CREATE TABLE `dieta` (
  `idDieta` int(11) NOT NULL auto_increment,
  `Nome` varchar(100) NOT NULL,
  `DurataCiclo` int(11) NOT NULL,
  `Note` text,
  `dietaStandard` tinyint(1) default NULL,
  `FK_SpecificheDieta` int(11) NOT NULL,
  PRIMARY KEY  (`idDieta`),
  KEY `FK_SpecificheDieta` (`FK_SpecificheDieta`),
  CONSTRAINT `FK_SpecificheDieta` FOREIGN KEY (`FK_SpecificheDieta`) REFERENCES `specifichedieta` (`idSpecificheDieta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `dieta`
--

/*!40000 ALTER TABLE `dieta` DISABLE KEYS */;
/*!40000 ALTER TABLE `dieta` ENABLE KEYS */;


--
-- Definition of table `esameclinico`
--

DROP TABLE IF EXISTS `esameclinico`;
CREATE TABLE `esameclinico` (
  `idEsameClinico` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  PRIMARY KEY  (`idEsameClinico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `esameclinico`
--

/*!40000 ALTER TABLE `esameclinico` DISABLE KEYS */;
/*!40000 ALTER TABLE `esameclinico` ENABLE KEYS */;


--
-- Definition of table `farmacoassunto`
--

DROP TABLE IF EXISTS `farmacoassunto`;
CREATE TABLE `farmacoassunto` (
  `idFarmacoAssunto` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  `Dose` varchar(45) NOT NULL,
  `Frequenza` varchar(45) NOT NULL,
  `PrincipiAttivi` varchar(45) default NULL,
  `FK3_Paziente` int(11) NOT NULL,
  PRIMARY KEY  (`idFarmacoAssunto`),
  KEY `FK3_Paziente` (`FK3_Paziente`),
  CONSTRAINT `FK3_Paziente` FOREIGN KEY (`FK3_Paziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `farmacoassunto`
--

/*!40000 ALTER TABLE `farmacoassunto` DISABLE KEYS */;
/*!40000 ALTER TABLE `farmacoassunto` ENABLE KEYS */;


--
-- Definition of table `fattura`
--

DROP TABLE IF EXISTS `fattura`;
CREATE TABLE `fattura` (
  `idFattura` int(11) NOT NULL auto_increment,
  `Importo` double NOT NULL,
  `Acconto` double default NULL,
  `ImportoSconto` double default NULL,
  `Note` text,
  `Descrizione` varchar(300) NOT NULL,
  `Data` datetime NOT NULL,
  PRIMARY KEY  (`idFattura`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `fattura`
--

/*!40000 ALTER TABLE `fattura` DISABLE KEYS */;
INSERT INTO `fattura` (`idFattura`,`Importo`,`Acconto`,`ImportoSconto`,`Note`,`Descrizione`,`Data`) VALUES 
 (5,122,0,0,'ok','fatturadata','2010-01-26 15:29:15'),
 (6,145,0,0,'26gennaio','fattura2','2010-01-26 15:34:25'),
 (7,155,0,0,'fattura3mod','fattura3','2010-01-19 11:50:40'),
 (8,111,0,0,'aa','fattura4','2010-01-19 12:58:28'),
 (9,11,0,0,'fattura5 modificata e aggiornata 26 gennaio','fattura5','2010-01-26 15:31:32'),
 (10,100,0,0,'ok','fattura6','2010-01-27 11:31:37'),
 (11,200,0,0,'das','fattura7','2010-01-18 16:05:45'),
 (12,111,0,0,'modificata4','fattura4','2010-01-19 11:51:31'),
 (13,113,11,0,'fattura8','fattura8	','2010-01-26 17:04:44'),
 (14,133,0,3,'sconto 3 euro','fattura9','2010-01-18 19:14:25'),
 (15,33,0,0,'19genn1','fatt','2010-01-19 11:28:24'),
 (16,100,40,15,'notefattura19 acconto aumentato, sconto maggiore','fattura19genn','2010-01-19 11:54:25'),
 (17,111,0,1,'ok19','19gennaio bis','2010-01-19 11:16:34'),
 (18,22,0,0,'note','fa','2010-01-19 11:46:29');
/*!40000 ALTER TABLE `fattura` ENABLE KEYS */;


--
-- Definition of table `funzione`
--

DROP TABLE IF EXISTS `funzione`;
CREATE TABLE `funzione` (
  `idFunzione` int(10) unsigned NOT NULL auto_increment,
  `Descrizione` varchar(45) NOT NULL,
  PRIMARY KEY  (`idFunzione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `funzione`
--

/*!40000 ALTER TABLE `funzione` DISABLE KEYS */;
/*!40000 ALTER TABLE `funzione` ENABLE KEYS */;


--
-- Definition of table `ingrediente`
--

DROP TABLE IF EXISTS `ingrediente`;
CREATE TABLE `ingrediente` (
  `idIngrediente` int(11) NOT NULL auto_increment,
  `Nome` varchar(60) NOT NULL,
  PRIMARY KEY  (`idIngrediente`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ingrediente`
--

/*!40000 ALTER TABLE `ingrediente` DISABLE KEYS */;
INSERT INTO `ingrediente` (`idIngrediente`,`Nome`) VALUES 
 (1,'cipolla');
/*!40000 ALTER TABLE `ingrediente` ENABLE KEYS */;


--
-- Definition of table `intervento`
--

DROP TABLE IF EXISTS `intervento`;
CREATE TABLE `intervento` (
  `idPaziente` int(11) NOT NULL,
  `idTipologiaIntervento` int(11) NOT NULL,
  `Data` date default NULL,
  `Numero` int(11) NOT NULL,
  PRIMARY KEY  (`idPaziente`,`idTipologiaIntervento`),
  KEY `idPaziente` (`idPaziente`),
  KEY `idTiplogiaIntervento` (`idTipologiaIntervento`),
  CONSTRAINT `idPaziente` FOREIGN KEY (`idPaziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idTiplogiaIntervento` FOREIGN KEY (`idTipologiaIntervento`) REFERENCES `tipologiaintervento` (`idTipologiaIntervento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `intervento`
--

/*!40000 ALTER TABLE `intervento` DISABLE KEYS */;
/*!40000 ALTER TABLE `intervento` ENABLE KEYS */;


--
-- Definition of table `intolleranzaallergia`
--

DROP TABLE IF EXISTS `intolleranzaallergia`;
CREATE TABLE `intolleranzaallergia` (
  `idIntolleranzaAllergia` int(11) NOT NULL auto_increment,
  `FlagIntAll` varchar(3) NOT NULL,
  `Sostanza` varchar(45) NOT NULL,
  `AlimentoPrincipale` varchar(45) NOT NULL,
  `Derivati` text,
  `Grado` varchar(20) default NULL,
  `EffettiCollaterali` text,
  `FK1_Paziente` int(11) NOT NULL,
  PRIMARY KEY  (`idIntolleranzaAllergia`),
  KEY `FK1_Paziente` (`FK1_Paziente`),
  CONSTRAINT `FK1_Paziente` FOREIGN KEY (`FK1_Paziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `intolleranzaallergia`
--

/*!40000 ALTER TABLE `intolleranzaallergia` DISABLE KEYS */;
/*!40000 ALTER TABLE `intolleranzaallergia` ENABLE KEYS */;


--
-- Definition of table `malattia`
--

DROP TABLE IF EXISTS `malattia`;
CREATE TABLE `malattia` (
  `idMalattia` int(11) NOT NULL auto_increment,
  `Patologia` text,
  `MalattiaEreditaria` tinyint(1) default NULL,
  `FK_TipologiaDietaSpeciale` int(11) default NULL,
  PRIMARY KEY  (`idMalattia`),
  KEY `FK_TipologiaDietaSpeciale` (`FK_TipologiaDietaSpeciale`),
  CONSTRAINT `FK_TipologiaDietaSpeciale` FOREIGN KEY (`FK_TipologiaDietaSpeciale`) REFERENCES `tipologiadietaspeciale` (`idTipologiaDietaSpeciale`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `malattia`
--

/*!40000 ALTER TABLE `malattia` DISABLE KEYS */;
/*!40000 ALTER TABLE `malattia` ENABLE KEYS */;


--
-- Definition of table `medico`
--

DROP TABLE IF EXISTS `medico`;
CREATE TABLE `medico` (
  `idMedico` int(11) NOT NULL auto_increment,
  `CodiceFiscale` char(16) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `DataNascita` date NOT NULL,
  `Sesso` char(1) NOT NULL,
  `Indirizzo` varchar(45) default NULL,
  `Citta` varchar(45) default NULL,
  `CAP` char(5) default NULL,
  `Provincia` char(2) default NULL,
  `Specializzazione` varchar(45) NOT NULL,
  `Telefono1` varchar(20) default NULL,
  `Telefono2` varchar(20) default NULL,
  `Email` varchar(45) default NULL,
  PRIMARY KEY  (`idMedico`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `medico`
--

/*!40000 ALTER TABLE `medico` DISABLE KEYS */;
INSERT INTO `medico` (`idMedico`,`CodiceFiscale`,`Cognome`,`Nome`,`DataNascita`,`Sesso`,`Indirizzo`,`Citta`,`CAP`,`Provincia`,`Specializzazione`,`Telefono1`,`Telefono2`,`Email`) VALUES 
 (1,'1234567890123456','m','e','1980-02-02','M','asdaddaddas','dasddddddd','5675','ba','dqwwqddqdw','56757567','32424','dk@.it'),
 (3,'adsdsasdadssadsa','med2','med','1910-01-02','M','dasadsdsasd','adadsadsads','21332','aa','aaaaa','31232132','32332','sas'),
 (4,'adsdsasdadssadsa','med3','med3','1910-01-02','M','dasadsdsasd','adadsadsads','21332','aa','aaaaa','31232132','32332','sas'),
 (5,'adsdasasdsadsads','med4','nome4','1910-01-01','F','aa','aaa','32434','aa','aaa','aa','aa','aaa'),
 (6,'adsdasadddffffff','med5','nome5','1910-01-01','F','aa','aaa','32434','aa','aaa','aa','aa','aaa'),
 (7,'cvvdasadddffffff','med6','nome6','1910-01-01','F','aa','aaa','32434','aa','aaa','aa','aa','aaa');
/*!40000 ALTER TABLE `medico` ENABLE KEYS */;


--
-- Definition of table `misurazione`
--

DROP TABLE IF EXISTS `misurazione`;
CREATE TABLE `misurazione` (
  `idPaziente` int(11) NOT NULL,
  `idParametroAntropometrico` int(11) NOT NULL,
  `Osservazioni` text,
  PRIMARY KEY  (`idPaziente`,`idParametroAntropometrico`),
  KEY `FK7_Paziente` (`idPaziente`),
  KEY `FK_ParametroAntropometrico` (`idParametroAntropometrico`),
  CONSTRAINT `FK7_Paziente` FOREIGN KEY (`idPaziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ParametroAntropometrico` FOREIGN KEY (`idParametroAntropometrico`) REFERENCES `parametroantropometrico` (`idParametroAntropometrico`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `misurazione`
--

/*!40000 ALTER TABLE `misurazione` DISABLE KEYS */;
/*!40000 ALTER TABLE `misurazione` ENABLE KEYS */;


--
-- Definition of table `modalitapastofuori`
--

DROP TABLE IF EXISTS `modalitapastofuori`;
CREATE TABLE `modalitapastofuori` (
  `idModalitaPastoFuori` int(11) NOT NULL auto_increment,
  `Luogo` varchar(45) default NULL,
  `SpecificaGiorno` varchar(45) NOT NULL,
  `FrequenzaGIornaliera` int(11) default NULL,
  `FK_Abitudini` int(11) NOT NULL,
  PRIMARY KEY  (`idModalitaPastoFuori`),
  KEY `FK_Abitudini` (`FK_Abitudini`),
  CONSTRAINT `FK_Abitudini` FOREIGN KEY (`FK_Abitudini`) REFERENCES `abitudinialimentari` (`idAbitudiniAlimentari`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `modalitapastofuori`
--

/*!40000 ALTER TABLE `modalitapastofuori` DISABLE KEYS */;
/*!40000 ALTER TABLE `modalitapastofuori` ENABLE KEYS */;


--
-- Definition of table `parametroantropometrico`
--

DROP TABLE IF EXISTS `parametroantropometrico`;
CREATE TABLE `parametroantropometrico` (
  `idParametroAntropometrico` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  PRIMARY KEY  (`idParametroAntropometrico`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `parametroantropometrico`
--

/*!40000 ALTER TABLE `parametroantropometrico` DISABLE KEYS */;
/*!40000 ALTER TABLE `parametroantropometrico` ENABLE KEYS */;


--
-- Definition of table `parametroesame`
--

DROP TABLE IF EXISTS `parametroesame`;
CREATE TABLE `parametroesame` (
  `idParametroEsame` int(11) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  `MinUomo` varchar(45) default NULL,
  `MaxUomo` varchar(45) default NULL,
  `MinDonna` varchar(45) default NULL,
  `MaxDonna` varchar(45) default NULL,
  `MinBambino` varchar(45) default NULL,
  `MaxBambino` varchar(45) default NULL,
  `FK_EsameClinico` int(11) NOT NULL,
  PRIMARY KEY  (`idParametroEsame`),
  KEY `FK_EsameClinico` (`FK_EsameClinico`),
  CONSTRAINT `FK_EsameClinico` FOREIGN KEY (`FK_EsameClinico`) REFERENCES `esameclinico` (`idEsameClinico`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `parametroesame`
--

/*!40000 ALTER TABLE `parametroesame` DISABLE KEYS */;
/*!40000 ALTER TABLE `parametroesame` ENABLE KEYS */;


--
-- Definition of table `pasto`
--

DROP TABLE IF EXISTS `pasto`;
CREATE TABLE `pasto` (
  `idPasto` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  PRIMARY KEY  (`idPasto`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `pasto`
--

/*!40000 ALTER TABLE `pasto` DISABLE KEYS */;
INSERT INTO `pasto` (`idPasto`,`Nome`) VALUES 
 (1,'Colazione'),
 (2,'Spuntino mattutino'),
 (3,'Pranzo'),
 (4,'Spuntino pomeridiano'),
 (5,'Cena');
/*!40000 ALTER TABLE `pasto` ENABLE KEYS */;


--
-- Definition of table `paziente`
--

DROP TABLE IF EXISTS `paziente`;
CREATE TABLE `paziente` (
  `idPaziente` int(11) NOT NULL auto_increment,
  `Codice Fiscale` char(16) NOT NULL,
  `Cognome` varchar(45) NOT NULL,
  `Nome` varchar(45) NOT NULL,
  `DataNascita` date NOT NULL,
  `Sesso` char(1) NOT NULL,
  `Indirizzo` varchar(45) default NULL,
  `Citta` varchar(45) default NULL,
  `CAP` char(5) default NULL,
  `Provincia` char(2) default NULL,
  `Professione` varchar(45) default NULL,
  `Telefono1` varchar(20) default NULL,
  `Telefono2` varchar(20) default NULL,
  `Email` varchar(45) default NULL,
  `NumTesseraSanitaria` varchar(20) default NULL,
  `Note` text,
  PRIMARY KEY  (`idPaziente`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `paziente`
--

/*!40000 ALTER TABLE `paziente` DISABLE KEYS */;
INSERT INTO `paziente` (`idPaziente`,`Codice Fiscale`,`Cognome`,`Nome`,`DataNascita`,`Sesso`,`Indirizzo`,`Citta`,`CAP`,`Provincia`,`Professione`,`Telefono1`,`Telefono2`,`Email`,`NumTesseraSanitaria`,`Note`) VALUES 
 (2,'1234567890123456','paz1','iente1','1912-01-03','M','via','bari','70023','ba','asdfio','2113123','12132','ggi@g.it','21313221321323','ok'),
 (3,'1234567890123456','paz2','iente2','1912-01-03','M','via','bari','70023','ba','asdfio','2113123','12132','ggi@g.it','21313221321323','ok'),
 (4,'ssadadsasdadsasd','dsdsa','adsadsasd','1913-01-02','F','sadsad','sadsds','sad','ba','sadsad','sadds','sadds','dsasad@.it','sad','ad'),
 (5,'2321312321321321','pp1','pp1','1912-04-02','M','dasads','dsads','213','qa','sadasd','dsadas','dassad','dsa@das.i','dasdassadasd','dsads'),
 (6,'fdsafdsfdsfadsfa','a','aaaa','1983-12-04','M','fdasdfsf','fasddff','32432','ba','fsadfdafsdf','6577','567457','ffff@.it','fadsdfdfdf3484',''),
 (7,'qerqeweqrrwqrrqr','saddassadads','dsaadsdasads','1910-05-02','M','sasaddas','sdassad','34211','ba','dasads','32434232','34223432','we','2342134234234234','dasdadda');
/*!40000 ALTER TABLE `paziente` ENABLE KEYS */;


--
-- Definition of table `personalizzazionegiornata`
--

DROP TABLE IF EXISTS `personalizzazionegiornata`;
CREATE TABLE `personalizzazionegiornata` (
  `idSchemaDietetico` int(11) NOT NULL,
  `idDieta` int(11) NOT NULL,
  `GiornoCiclo` int(11) NOT NULL,
  PRIMARY KEY  (`idSchemaDietetico`,`idDieta`,`GiornoCiclo`),
  KEY `FK1_SchemaDietetico` (`idSchemaDietetico`),
  KEY `FK_Dieta` (`idDieta`),
  CONSTRAINT `FK1_SchemaDietetico` FOREIGN KEY (`idSchemaDietetico`) REFERENCES `schemadietetico` (`idSchemaDietetico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Dieta` FOREIGN KEY (`idDieta`) REFERENCES `dieta` (`idDieta`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `personalizzazionegiornata`
--

/*!40000 ALTER TABLE `personalizzazionegiornata` DISABLE KEYS */;
/*!40000 ALTER TABLE `personalizzazionegiornata` ENABLE KEYS */;


--
-- Definition of table `prenotazione`
--

DROP TABLE IF EXISTS `prenotazione`;
CREATE TABLE `prenotazione` (
  `idPaziente` int(11) NOT NULL,
  `idTipologiaVisita` int(11) NOT NULL,
  `DataOra` datetime NOT NULL,
  `Note` text,
  `idPrenotazione` int(10) unsigned NOT NULL auto_increment,
  PRIMARY KEY  (`idPrenotazione`),
  KEY `FK8_Paziente` (`idPaziente`),
  KEY `FK_TipologiaVisita1` USING BTREE (`idTipologiaVisita`),
  CONSTRAINT `FK8_Paziente` FOREIGN KEY (`idPaziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_TipologiaVisita` FOREIGN KEY (`idTipologiaVIsita`) REFERENCES `tipologiavisita` (`idTipologiaVisita`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prenotazione`
--

/*!40000 ALTER TABLE `prenotazione` DISABLE KEYS */;
INSERT INTO `prenotazione` (`idPaziente`,`idTipologiaVisita`,`DataOra`,`Note`,`idPrenotazione`) VALUES 
 (3,2,'2010-02-01 04:02:00','prova',1),
 (3,2,'2010-01-04 05:00:00','1',2),
 (3,2,'2010-01-05 05:00:00','2',3),
 (4,2,'2010-04-09 13:00:00','2',4),
 (4,3,'2010-01-15 01:00:00','15',5),
 (4,3,'2011-01-15 01:00:00','15',6),
 (4,3,'2011-01-16 01:00:00','',7),
 (4,3,'2011-01-15 10:30:00','15',8),
 (3,1,'2010-01-15 10:30:00','15',9),
 (7,1,'2010-01-19 07:00:00','19gen',10),
 (4,3,'2010-01-19 07:00:00','19gen',11),
 (2,3,'2010-02-05 03:02:00','3.02',12),
 (7,2,'2010-03-11 10:26:00','10:26 orario visita',13),
 (3,1,'2010-01-29 15:34:00','15:34 ora visita',14),
 (3,2,'2010-02-27 19:00:00','27 febbraio ore 19 ',15),
 (5,4,'2010-01-31 17:22:00','22',16),
 (2,1,'2010-01-26 15:00:00','26 gennaio alle 15',17),
 (2,2,'2010-01-27 11:30:00','',18),
 (7,2,'2010-01-27 11:30:00','',19),
 (5,2,'2010-01-28 12:49:00','aa',20),
 (6,2,'2010-02-02 17:18:00','2febb',21);
/*!40000 ALTER TABLE `prenotazione` ENABLE KEYS */;


--
-- Definition of table `prescrizione`
--

DROP TABLE IF EXISTS `prescrizione`;
CREATE TABLE `prescrizione` (
  `idPrescrizione` int(10) unsigned NOT NULL auto_increment,
  `dataInizio` date NOT NULL,
  `NumRipetizCiclo` int(10) unsigned NOT NULL,
  `Note` text,
  `FK_Paziente` int(11) NOT NULL,
  `FK_Dieta` int(11) NOT NULL,
  PRIMARY KEY  (`idPrescrizione`),
  KEY `FK_prescrizione_dieta` (`FK_Dieta`),
  KEY `FK_prescrizione_paziente` (`FK_Paziente`),
  CONSTRAINT `FK_prescrizione_dieta` FOREIGN KEY (`FK_Dieta`) REFERENCES `dieta` (`idDieta`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_prescrizione_paziente` FOREIGN KEY (`FK_Paziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prescrizione`
--

/*!40000 ALTER TABLE `prescrizione` DISABLE KEYS */;
/*!40000 ALTER TABLE `prescrizione` ENABLE KEYS */;


--
-- Definition of table `prestazione`
--

DROP TABLE IF EXISTS `prestazione`;
CREATE TABLE `prestazione` (
  `idMedico` int(11) NOT NULL,
  `idTurno` int(11) NOT NULL,
  `DataTurno` date NOT NULL,
  PRIMARY KEY  (`idMedico`,`DataTurno`,`idTurno`),
  KEY `FK1_Medico` (`idMedico`),
  KEY `FK_Turno` (`idTurno`),
  CONSTRAINT `FK1_Medico` FOREIGN KEY (`idMedico`) REFERENCES `medico` (`idMedico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Turno` FOREIGN KEY (`idTurno`) REFERENCES `turno` (`idTurno`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `prestazione`
--

/*!40000 ALTER TABLE `prestazione` DISABLE KEYS */;
INSERT INTO `prestazione` (`idMedico`,`idTurno`,`DataTurno`) VALUES 
 (1,11,'2010-01-01'),
 (1,12,'2010-01-01'),
 (1,1,'2010-01-03'),
 (1,4,'2010-03-06'),
 (1,12,'2010-03-06'),
 (3,1,'2010-01-01'),
 (3,10,'2010-01-01'),
 (3,15,'2010-01-02'),
 (3,4,'2010-01-08'),
 (3,16,'2010-04-06'),
 (3,2,'2010-05-05'),
 (4,10,'2010-01-01'),
 (4,15,'2010-01-02'),
 (4,4,'2010-12-14'),
 (5,3,'2010-01-01'),
 (5,11,'2010-01-01'),
 (5,5,'2010-11-09'),
 (6,11,'2010-01-01'),
 (6,2,'2010-02-04'),
 (6,1,'2010-10-06'),
 (6,19,'2010-12-18'),
 (7,3,'2010-03-10');
/*!40000 ALTER TABLE `prestazione` ENABLE KEYS */;


--
-- Definition of table `referto`
--

DROP TABLE IF EXISTS `referto`;
CREATE TABLE `referto` (
  `idPaziente` int(11) NOT NULL,
  `idParametroEsame` int(11) NOT NULL,
  `Osservazioni` text,
  PRIMARY KEY  (`idPaziente`,`idParametroEsame`),
  KEY `FK6_Paziente` (`idPaziente`),
  KEY `FK_ParametroEsame` (`idParametroEsame`),
  CONSTRAINT `FK6_Paziente` FOREIGN KEY (`idPaziente`) REFERENCES `paziente` (`idPaziente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_ParametroEsame` FOREIGN KEY (`idParametroEsame`) REFERENCES `parametroesame` (`idParametroEsame`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `referto`
--

/*!40000 ALTER TABLE `referto` DISABLE KEYS */;
/*!40000 ALTER TABLE `referto` ENABLE KEYS */;


--
-- Definition of table `ricetta`
--

DROP TABLE IF EXISTS `ricetta`;
CREATE TABLE `ricetta` (
  `idRicetta` int(11) NOT NULL auto_increment,
  `Nome` varchar(80) NOT NULL,
  `Procedimento` text NOT NULL,
  `FK2_Alimento` int(11) default NULL,
  PRIMARY KEY  (`idRicetta`),
  KEY `FK2_Alimento` (`FK2_Alimento`),
  CONSTRAINT `FK2_Alimento` FOREIGN KEY (`FK2_Alimento`) REFERENCES `alimento` (`idAlimento`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ricetta`
--

/*!40000 ALTER TABLE `ricetta` DISABLE KEYS */;
/*!40000 ALTER TABLE `ricetta` ENABLE KEYS */;


--
-- Definition of table `rilevamento`
--

DROP TABLE IF EXISTS `rilevamento`;
CREATE TABLE `rilevamento` (
  `idRilevamento` int(11) NOT NULL auto_increment,
  `Valore` varchar(45) NOT NULL,
  `Data` date NOT NULL,
  `FK_Misurazione_Paz` int(11) NOT NULL,
  `FK_Misurazione_Par` int(11) NOT NULL,
  PRIMARY KEY  (`idRilevamento`),
  KEY `FK_Misurazione_Paz_Par` (`FK_Misurazione_Paz`,`FK_Misurazione_Par`),
  CONSTRAINT `FK_Misurazione_Paz_Par` FOREIGN KEY (`FK_Misurazione_Paz`, `FK_Misurazione_Par`) REFERENCES `misurazione` (`idPaziente`, `idParametroAntropometrico`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `rilevamento`
--

/*!40000 ALTER TABLE `rilevamento` DISABLE KEYS */;
/*!40000 ALTER TABLE `rilevamento` ENABLE KEYS */;


--
-- Definition of table `risultatoanalisi`
--

DROP TABLE IF EXISTS `risultatoanalisi`;
CREATE TABLE `risultatoanalisi` (
  `idRisultatoAnalisi` int(11) NOT NULL auto_increment,
  `Valore` varchar(45) NOT NULL,
  `Data` date NOT NULL,
  `Note` text,
  `FK_Referto_Paz` int(11) NOT NULL,
  `FK_Referto_Par` int(11) NOT NULL,
  PRIMARY KEY  (`idRisultatoAnalisi`),
  KEY `FK_Referto_Paz_Par` (`FK_Referto_Paz`,`FK_Referto_Par`),
  CONSTRAINT `FK_Referto_Paz_Par` FOREIGN KEY (`FK_Referto_Paz`, `FK_Referto_Par`) REFERENCES `referto` (`idPaziente`, `idParametroEsame`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `risultatoanalisi`
--

/*!40000 ALTER TABLE `risultatoanalisi` DISABLE KEYS */;
/*!40000 ALTER TABLE `risultatoanalisi` ENABLE KEYS */;


--
-- Definition of table `ruolo`
--

DROP TABLE IF EXISTS `ruolo`;
CREATE TABLE `ruolo` (
  `idRuolo` int(10) unsigned NOT NULL auto_increment,
  `descrizione` varchar(45) NOT NULL,
  PRIMARY KEY  (`idRuolo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ruolo`
--

/*!40000 ALTER TABLE `ruolo` DISABLE KEYS */;
/*!40000 ALTER TABLE `ruolo` ENABLE KEYS */;


--
-- Definition of table `ruolo_funzione`
--

DROP TABLE IF EXISTS `ruolo_funzione`;
CREATE TABLE `ruolo_funzione` (
  `idRuolo` int(10) unsigned NOT NULL,
  `idFunzione` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`idRuolo`,`idFunzione`),
  KEY `FK_ruolo_funzione_2` (`idFunzione`),
  CONSTRAINT `FK_ruolo_funzione_1` FOREIGN KEY (`idRuolo`) REFERENCES `ruolo` (`idRuolo`),
  CONSTRAINT `FK_ruolo_funzione_2` FOREIGN KEY (`idFunzione`) REFERENCES `funzione` (`idFunzione`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `ruolo_funzione`
--

/*!40000 ALTER TABLE `ruolo_funzione` DISABLE KEYS */;
/*!40000 ALTER TABLE `ruolo_funzione` ENABLE KEYS */;


--
-- Definition of table `schemadietetico`
--

DROP TABLE IF EXISTS `schemadietetico`;
CREATE TABLE `schemadietetico` (
  `idSchemaDietetico` int(11) NOT NULL auto_increment,
  `Descrizione` text NOT NULL,
  `Note` text,
  PRIMARY KEY  (`idSchemaDietetico`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `schemadietetico`
--

/*!40000 ALTER TABLE `schemadietetico` DISABLE KEYS */;
INSERT INTO `schemadietetico` (`idSchemaDietetico`,`Descrizione`,`Note`) VALUES 
 (1,'',''),
 (2,'',''),
 (3,'',''),
 (4,'',''),
 (5,'',''),
 (6,'',''),
 (7,'','');
/*!40000 ALTER TABLE `schemadietetico` ENABLE KEYS */;


--
-- Definition of table `specifichedieta`
--

DROP TABLE IF EXISTS `specifichedieta`;
CREATE TABLE `specifichedieta` (
  `idSpecificheDieta` int(11) NOT NULL auto_increment,
  `Kilocalorie` int(11) NOT NULL,
  `Indicata` text NOT NULL,
  `ContenutoPresente` text,
  `ContenutoAssente` text,
  PRIMARY KEY  (`idSpecificheDieta`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `specifichedieta`
--

/*!40000 ALTER TABLE `specifichedieta` DISABLE KEYS */;
INSERT INTO `specifichedieta` (`idSpecificheDieta`,`Kilocalorie`,`Indicata`,`ContenutoPresente`,`ContenutoAssente`) VALUES 
 (1,10,'llkok','dsfds','adszd<');
/*!40000 ALTER TABLE `specifichedieta` ENABLE KEYS */;


--
-- Definition of table `tipologiabevanda`
--

DROP TABLE IF EXISTS `tipologiabevanda`;
CREATE TABLE `tipologiabevanda` (
  `idTipologiaBevanda` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  PRIMARY KEY  (`idTipologiaBevanda`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tipologiabevanda`
--

/*!40000 ALTER TABLE `tipologiabevanda` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipologiabevanda` ENABLE KEYS */;


--
-- Definition of table `tipologiadietaspeciale`
--

DROP TABLE IF EXISTS `tipologiadietaspeciale`;
CREATE TABLE `tipologiadietaspeciale` (
  `idTipologiaDietaSpeciale` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  PRIMARY KEY  (`idTipologiaDietaSpeciale`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tipologiadietaspeciale`
--

/*!40000 ALTER TABLE `tipologiadietaspeciale` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipologiadietaspeciale` ENABLE KEYS */;


--
-- Definition of table `tipologiaintervento`
--

DROP TABLE IF EXISTS `tipologiaintervento`;
CREATE TABLE `tipologiaintervento` (
  `idTipologiaIntervento` int(11) NOT NULL auto_increment,
  `Nome` varchar(45) NOT NULL,
  `Descrizione` varchar(45) default NULL,
  `Localizzazione` varchar(45) NOT NULL,
  PRIMARY KEY  (`idTipologiaIntervento`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tipologiaintervento`
--

/*!40000 ALTER TABLE `tipologiaintervento` DISABLE KEYS */;
/*!40000 ALTER TABLE `tipologiaintervento` ENABLE KEYS */;


--
-- Definition of table `tipologiavisita`
--

DROP TABLE IF EXISTS `tipologiavisita`;
CREATE TABLE `tipologiavisita` (
  `idTipologiaVisita` int(11) NOT NULL auto_increment,
  `Tipologia` varchar(45) NOT NULL,
  `CostoVisita` double NOT NULL,
  PRIMARY KEY  (`idTipologiaVisita`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `tipologiavisita`
--

/*!40000 ALTER TABLE `tipologiavisita` DISABLE KEYS */;
INSERT INTO `tipologiavisita` (`idTipologiaVisita`,`Tipologia`,`CostoVisita`) VALUES 
 (1,'tipo1',10),
 (2,'tipo2',20),
 (3,'tipo3',30),
 (4,'visita tipo4',10);
/*!40000 ALTER TABLE `tipologiavisita` ENABLE KEYS */;


--
-- Definition of table `turno`
--

DROP TABLE IF EXISTS `turno`;
CREATE TABLE `turno` (
  `idTurno` int(11) NOT NULL auto_increment,
  `OraInizio` time NOT NULL,
  `OraFine` time NOT NULL,
  `Nome` varchar(45) NOT NULL,
  PRIMARY KEY  (`idTurno`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `turno`
--

/*!40000 ALTER TABLE `turno` DISABLE KEYS */;
INSERT INTO `turno` (`idTurno`,`OraInizio`,`OraFine`,`Nome`) VALUES 
 (1,'08:00:00','09:00:00','turno01'),
 (2,'09:00:00','10:00:00','turno02'),
 (3,'10:00:00','11:00:00','turno03'),
 (4,'11:00:00','12:00:00','turno04'),
 (5,'12:00:00','13:00:00','turno05'),
 (6,'13:00:00','14:00:00','turno06'),
 (7,'14:00:00','15:00:00','turno07'),
 (8,'15:00:00','16:00:00','turno08'),
 (9,'16:00:00','17:00:00','turno09'),
 (10,'17:00:00','18:00:00','turno10'),
 (11,'18:00:00','19:00:00','turno11'),
 (12,'19:00:00','20:00:00','turno12'),
 (13,'20:00:00','21:00:00','turno13'),
 (14,'21:00:00','22:00:00','turno14'),
 (15,'08:00:00','11:00:00','turno15'),
 (16,'11:00:00','14:00:00','turno16'),
 (17,'14:00:00','17:00:00','turno17'),
 (18,'17:00:00','20:00:00','turno18'),
 (19,'00:00:00','23:53:00','turnosilletti'),
 (20,'14:53:00','15:53:00','turno20');
/*!40000 ALTER TABLE `turno` ENABLE KEYS */;


--
-- Definition of table `utente`
--

DROP TABLE IF EXISTS `utente`;
CREATE TABLE `utente` (
  `idUtente` int(10) unsigned NOT NULL auto_increment,
  `nomeUtente` varchar(50) NOT NULL,
  `password` varchar(45) NOT NULL,
  `idRuolo` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`idUtente`),
  KEY `FK_utente_ruolo` (`idRuolo`),
  CONSTRAINT `FK_utente_ruolo` FOREIGN KEY (`idRuolo`) REFERENCES `ruolo` (`idRuolo`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `utente`
--

/*!40000 ALTER TABLE `utente` DISABLE KEYS */;
/*!40000 ALTER TABLE `utente` ENABLE KEYS */;


--
-- Definition of table `visita`
--

DROP TABLE IF EXISTS `visita`;
CREATE TABLE `visita` (
  `idVisita` int(11) NOT NULL auto_increment,
  `DataOraInizio` datetime NOT NULL,
  `DataOraFine` datetime NOT NULL,
  `Motivazioni` text,
  `StatoPagamento` varchar(45) default NULL,
  `Note` text,
  `FK_Fattura` int(11) default NULL,
  `FK2_Medico` int(11) NOT NULL,
  `FK_Prenotazione` int(10) unsigned NOT NULL,
  PRIMARY KEY  (`idVisita`),
  KEY `FK_Fattura` (`FK_Fattura`),
  KEY `FK2_Medico` (`FK2_Medico`),
  KEY `FK_Prenotazione` (`FK_Prenotazione`),
  CONSTRAINT `FK2_Medico` FOREIGN KEY (`FK2_Medico`) REFERENCES `medico` (`idMedico`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Fattura` FOREIGN KEY (`FK_Fattura`) REFERENCES `fattura` (`idFattura`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_Prenotazione` FOREIGN KEY (`FK_Prenotazione`) REFERENCES `prenotazione` (`idPrenotazione`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8;

--
-- Dumping data for table `visita`
--

/*!40000 ALTER TABLE `visita` DISABLE KEYS */;
INSERT INTO `visita` (`idVisita`,`DataOraInizio`,`DataOraFine`,`Motivazioni`,`StatoPagamento`,`Note`,`FK_Fattura`,`FK2_Medico`,`FK_Prenotazione`) VALUES 
 (1,'2009-01-19 17:00:00','2009-01-19 21:00:00','motivazion	','pagamento','note',16,1,4),
 (2,'2009-01-19 20:00:00','2009-01-19 22:00:00','asdaadsdasds','ddd','ddddddd',17,1,5),
 (3,'2009-01-19 01:00:00','2009-01-19 02:00:00','aa','aa','',15,1,5),
 (4,'2009-01-19 03:00:00','2009-01-19 04:00:00','a','d','da',6,1,4),
 (5,'2009-01-19 03:00:00','2009-01-19 04:00:00','m','m','m',17,1,11),
 (6,'2009-01-19 08:00:00','2009-01-19 11:00:00','a','aaaa','aaaa',17,1,10),
 (7,'2009-01-19 02:00:00','2009-01-19 03:00:00','a','d','d',18,1,10),
 (8,'2009-01-19 01:00:00','2009-01-19 01:02:00','d','ss','nottr',12,1,10),
 (9,'2009-01-19 04:00:00','2009-01-19 09:00:00','motiv	','pagam','',16,1,10),
 (10,'2009-01-04 01:00:00','2009-01-04 01:00:00','aa','a','aaaaa',8,1,11),
 (11,'2010-01-26 15:40:00','2010-01-26 17:48:00','sadsadassad','asadsadsadsd','adasaadwqdwqdqwq',9,1,17),
 (12,'2010-01-26 07:05:00','2010-01-26 08:00:00','mot	','sadas','dalle 7 alle 8',6,1,17),
 (13,'2010-01-26 14:03:00','2010-01-26 17:03:00','mah	','pagato','dasdasdas',13,1,17),
 (14,'2010-01-27 11:40:00','2010-01-27 12:31:00','controllo	','completato','',10,1,18);
/*!40000 ALTER TABLE `visita` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
