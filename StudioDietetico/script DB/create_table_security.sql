DROP TABLE IF EXISTS `studiodietetico`.`utente`;
CREATE TABLE `studiodietetico`.`utente` (
  `ID_UTENTE` INTEGER UNSIGNED NOT NULL AUTO_INCREMENT,
  `NOME_UTENTE` VARCHAR(45) NOT NULL,
  `PASSWORD` VARCHAR(45) NOT NULL,
  `ID_RUOLO` INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_UTENTE`)
)
DROP TABLE IF EXISTS `studiodietetico`.`ruolo`;
CREATE TABLE  `studiodietetico`.`ruolo` (
  `ID_RUOLO` int(10) unsigned NOT NULL auto_increment,
  `DESCR` varchar(45) NOT NULL,
  PRIMARY KEY  (`ID_RUOLO`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
DROP TABLE IF EXISTS `studiodietetico`.`funzione`;
CREATE TABLE  `studiodietetico`.`funzione` (
  `ID_FUNZIONE` int(10) unsigned NOT NULL auto_increment,
  `DESCRIZIONE` varchar(45) NOT NULL,
  PRIMARY KEY  (`ID_FUNZIONE`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
CREATE TABLE `studiodietetico`.`ruolo_funzione` (
  `ID_RUOLO` INTEGER UNSIGNED NOT NULL,
  `ID_FUNZIONE` INTEGER UNSIGNED NOT NULL,
  PRIMARY KEY (`ID_RUOLO`, `ID_FUNZIONE`)
)
ENGINE = InnoDB;
ALTER TABLE `studiodietetico`.`ruolo_funzione` ADD CONSTRAINT `FK_ruolo` FOREIGN KEY `FK_ruolo` (`ID_RUOLO`)
    REFERENCES `ruolo` (`ID_RUOLO`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT,
 ADD CONSTRAINT `FK_funzione` FOREIGN KEY `FK_funzione` (`ID_FUNZIONE`)
    REFERENCES `funzione` (`ID_FUNZIONE`)
    ON DELETE RESTRICT
    ON UPDATE RESTRICT;

