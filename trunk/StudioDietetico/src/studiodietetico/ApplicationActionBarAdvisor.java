package studiodietetico;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.ICoolBarManager;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;

import common.ICommonConstants;
import common.ui.OpenViewAction;

/**
 * An action bar advisor is responsible for creating, adding, and disposing of
 * the actions added to a workbench window. Each window will be populated with
 * new actions.
 */
public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	// Actions - important to allocate these only in makeActions, and then use
	// them
	// in the fill methods. This ensures that the actions aren't recreated
	// when fillActionBars is called with FILL_PROXY.


	/*private Action medicoViewAction;
	private Action statisticheViewAction;
	private Action esameClinicoViewAction;
	private Action parametroAntropometricoViewAction;
	private Action prenotaVisitaViewAction;
	private Action registraMedicoViewAction;
	private Action registraPazienteViewAction;
	private Action registraVisitaViewAction;
	private Action rilevazioneParametroAntroViewAction;
	private Action risultatoAnalisiViewAction;

	private IWorkbenchAction exitAction;*/

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
		super(configurer);
	}

	protected void makeActions(IWorkbenchWindow window) {

		/*esameClinicoViewAction = new OpenViewAction(window, "Esame Clinico",
				ICommonConstants.EsameClinicoView);
		register(esameClinicoViewAction);
		parametroAntropometricoViewAction = new OpenViewAction(window,
				"Parametro Antropometrico",
				ICommonConstants.ParametroAntropometricoView);
		register(parametroAntropometricoViewAction);
		prenotaVisitaViewAction = new OpenViewAction(window, "Prenota Visita",
				ICommonConstants.PrenotaVisitaView);
		register(prenotaVisitaViewAction);
		registraMedicoViewAction = new OpenViewAction(window,
				"Registra Medico", ICommonConstants.RegistraMedicoView);
		register(registraMedicoViewAction);
		registraPazienteViewAction = new OpenViewAction(window,
				"Registra Paziente", ICommonConstants.RegistraPazienteView);
		register(registraPazienteViewAction);
		registraVisitaViewAction = new OpenViewAction(window,
				"Registra Visita", ICommonConstants.RegistraVisitaView);
		register(registraVisitaViewAction);
		rilevazioneParametroAntroViewAction = new OpenViewAction(window,
				"Rilevazione Parametro",
				ICommonConstants.RilevazioneParametroAntroView);
		register(rilevazioneParametroAntroViewAction);
		risultatoAnalisiViewAction = new OpenViewAction(window,
				"Risultato Analisi", ICommonConstants.RisultatoAnalisiView);
		register(risultatoAnalisiViewAction);
		medicoViewAction = new OpenViewAction(window, "Medico",
				ICommonConstants.medicoViewID);
		register(medicoViewAction);
		statisticheViewAction = new OpenViewAction(window, "Statistiche",
				ICommonConstants.statisticheViewID);
		register(statisticheViewAction);*/
	}

	protected void fillMenuBar(IMenuManager menuBar) {

		/*MenuManager applicationMenu = new MenuManager("&Anagrafica");
		applicationMenu.removeAll();
		applicationMenu.update(true);
		applicationMenu.add(prenotaVisitaViewAction);
		applicationMenu.add(registraPazienteViewAction);
		applicationMenu.add(registraVisitaViewAction);
		applicationMenu.add(rilevazioneParametroAntroViewAction);
		applicationMenu.add(risultatoAnalisiViewAction);
		applicationMenu.add(esameClinicoViewAction);
		applicationMenu.add(new Separator());
		applicationMenu.add(registraMedicoViewAction);
		applicationMenu.add(parametroAntropometricoViewAction);
		menuBar.add(applicationMenu);
		MenuManager utilitaMenu = new MenuManager("&Utilità");
		utilitaMenu.add(statisticheViewAction);
		utilitaMenu.removeAll();
		utilitaMenu.update(true);
		utilitaMenu.add(statisticheViewAction);
		menuBar.add(utilitaMenu);
		MenuManager sistemaMenu = new MenuManager("&Sistema");
		sistemaMenu.removeAll();
		sistemaMenu.update(true);
		sistemaMenu.add(exitAction);
		menuBar.add(sistemaMenu);*/
	}

	@Override
	protected void fillCoolBar(ICoolBarManager coolBar) {
	}

}
