package common;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;

/**
 * This class contains plugin constants
 */
public interface ICommonConstants {

	public static String applicationTitle = "label.applicationtitle";

	public final static Font font = new Font(null, "ARIAL", 16, SWT.NONE);
	public final int WIDTH_BUTTON = 45;
	public final int HEIGTH_BUTTON = 45;

	public static final String MESSAGE_ILLEGAL_COPY = "message.illegal.copy";

	public final static Color whiteColor = Utils.getColor(255, 255, 255);
	public final static Color blackColor = Utils.getColor(0, 0, 0);

	public static String mainPerspectiveID = "MainPerspective";
	public static String applicationPerspectiveID = "StudioDietetico.perspective";
	public static final String voidPerspectiveID = "VoidPerspective";
	// VIEW ID
	public static final String medicoViewID = "MedicoViewID";
	public static final String medicoDetailViewID = "MedicoDetailViewID";
	public static final String statisticheViewID = "StatisticheViewID";
	public static final String graficiViewID = "GraficiViewID";
	public static final String loginViewID = "LoginViewID";
	public static final String registrazioneViewID = "registrazioneViewID";
	
	public static final String EsameClinicoView = "StudioDietetico.EsameClinicoView";
	public static final String ParametroAntropometricoView = "StudioDietetico.ParametroAntropometricoView";
	public static final String PrenotaVisitaView = "StudioDietetico.PrenotaVisitaView";
	public static final String RegistraMedicoView = "StudioDietetico.RegistraMedicoView";
	public static final String RegistraPazienteView = "StudioDietetico.RegistraPazienteView";
	public static final String RegistraVisitaView = "StudioDietetico.RegistraVisitaView";
	public static final String RilevazioneParametroAntroView = "StudioDietetico.RilevazioneParametroAntroView";
	public static final String RisultatoAnalisiView = "StudioDietetico.RisultatoAnalisiView";
	public static final String HomePazienteView = "HomePazienteView";
	public static final String mainViewID = "MainViewID";
	public static final String queryView = "StudioDietetico.DynamicQueryView";

	public static String labelOkMessageDialog = "label.okmessagedialog";
	public static String labelCancelMessageDialog = "label.cancelmessagedialog";
	public static String labelYesMessageDialog = "label.yesmessagedialog";
	public static String labelNoMessageDialog = "label.nomessagedialog";
	public static String labelConfirmMessageDialog = "label.confirmmessagedialog";
	public static String labelErrorMessageDialog = "label.errormessagedialog";
	public static String labelInformationMessageDialog = "label.informationmessagedialog";
	public static String labelQuestionMessageDialog = "label.questionmessagedialog";
	public static String labelWarningMessageDialog = "label.warningmessagedialog";
	public static final String label_progress = "label.progress";
	public static String label_operazione_in_corso = "label.operazione.corso";

	public static String messageBarId = "MessageBar";
	public static String messageErrorLoadProperties = "message.error.loadproperties";
	public static String messageConfirmLoadProperties = "message.confirmloadproperties";

	public static String patternDateHour = "dd/MM/yyyy HH:mm";
	public static String patternDate = "dd/MM/yyyy";
	public static String patternHour = "HH:mm";


	// security - Ruoli
	public static String RUOLO_AMM_ID = "0";
	public static String RUOLO_SEGRETARIO_ID = "1";
	public static String RUOLO_MEDICO_ID = "2";
	public static String RUOLO_TECNICO_ID = "3";
	

	public static String RUOLO_AMM = "Amministratore";
	public static String RUOLO_SEGRETARIO = "Segretario";
	public static String RUOLO_MEDICO = "Medico";
	public static String RUOLO_TECNICO = "Tecnico";
	
}
