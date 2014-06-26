import jade.core.Agent;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AgentePacienteIntenso extends Agent {

	private static final long serialVersionUID = 683546094743097757L;

	protected void setup() {

		Object[] args = getArguments();

		if (args != null && args.length > 0) {
			String argumento = (String) args[0];
			if (argumento.equalsIgnoreCase("Problema Neurol�gico")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata problemas neurol�gicos");
				buscaServico(servico, "Problema Neurol�gico");
			}
			if (argumento.equalsIgnoreCase("Problema Muscular")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata problemas musculares e �sseos");
				buscaServico(servico, "Problema Muscular");
			}
			if (argumento.equalsIgnoreCase("Problema Card�aco")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas card�acos");
				buscaServico(servico, "Problema Card�aco");
			}
			if (argumento.equalsIgnoreCase("Problema Ocular")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas dos olhos");
				buscaServico(servico, "Problema Ocular");
			}
			if (argumento.equalsIgnoreCase("Problema Renal")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas renais");
				buscaServico(servico, "Problema Renal");
			}
			if (argumento.equalsIgnoreCase("Problema da Pele")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas dermatol�gicos");
				buscaServico(servico, "Problema da Pele");
			}
			if (argumento.equalsIgnoreCase("Problema de gl�ndula")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas end�crinos");
				buscaServico(servico, "Problema de gl�ndula");
			}
			if (argumento.equalsIgnoreCase("Problema psiqui�trico")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas psiqui�tricos");
				buscaServico(servico, "Problema psiqui�trico");
			}
		}

	}

	protected void buscaServico(final ServiceDescription sd, final String Pedido) {

		addBehaviour(new TickerBehaviour(this, 5000) {

			private static final long serialVersionUID = 515505071990058508L;

			@Override
			protected void onTick() {
				DFAgentDescription dfd = new DFAgentDescription();
				dfd.addServices(sd);

				try {
					DFAgentDescription[] resultado = DFService.search(myAgent,
							dfd);
					if (resultado.length != 0) {
						ACLMessage msg = new ACLMessage(ACLMessage.INFORM);
						msg.addReceiver(resultado[0].getName());
						msg.setContent(Pedido);
						myAgent.send(msg);
						stop();
					}
				} catch (FIPAException e) {
					e.printStackTrace();
				}
			}
		});
	}

}
