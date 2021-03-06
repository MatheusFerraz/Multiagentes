import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPANames;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.proto.SubscriptionInitiator;

public class AgentePaciente extends Agent {

	private static final long serialVersionUID = 56354548957835340L;

	protected void setup() {

		Object[] args = getArguments();

		if (args != null && args.length > 0) {
			String argumento = (String) args[0];
			if (argumento.equalsIgnoreCase("Problema Neurol�gico")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata problemas neurol�gicos");
				solicitaNotificacao(servico, "Problema Neurol�gico");
			}
			if (argumento.equalsIgnoreCase("Problema Muscular")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata problemas musculares e �sseos");
				solicitaNotificacao(servico, "Problema Muscular");
			}
			if (argumento.equalsIgnoreCase("Problema Card�aco")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas card�acos");
				solicitaNotificacao(servico, "Problema Card�aco");
			}
			if (argumento.equalsIgnoreCase("Problema Ocular")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas dos olhos");
				solicitaNotificacao(servico, "Problema Ocular");
			}
			if (argumento.equalsIgnoreCase("Problema Renal")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas renais");
				solicitaNotificacao(servico, "Problema Renal");
			}
			if (argumento.equalsIgnoreCase("Problema da Pele")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas dermatol�gicos");
				solicitaNotificacao(servico, "Problema da Pele");
			}
			if (argumento.equalsIgnoreCase("Problema de gl�ndula")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas end�crinos");
				solicitaNotificacao(servico, "Problema de gl�ndula");
			}
			if (argumento.equalsIgnoreCase("Problema psiqui�trico")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas psiqui�tricos");
				solicitaNotificacao(servico, "Problema psiqui�trico");
			}
			if (argumento.equalsIgnoreCase("Problema de Pele")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas de pele");
				solicitaNotificacao(servico, "Problema de Pele");
			}
			if (argumento.equalsIgnoreCase("Problema Urin�rio")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas do sistema unir�rio");
				solicitaNotificacao(servico, "Problema Urin�rio");
			}
			if (argumento.equalsIgnoreCase("Problema de Vis�o")) {
				ServiceDescription servico = new ServiceDescription();
				servico.setType("Trata de problemas de vis�o");
				solicitaNotificacao(servico, "Problema de Vis�o");
			}
		}

		addBehaviour(new CyclicBehaviour(this) {

			private static final long serialVersionUID = 4869080887493521263L;

			MessageTemplate filtro1 = MessageTemplate
					.MatchProtocol(FIPANames.InteractionProtocol.FIPA_SUBSCRIBE);
			MessageTemplate filtro2 = MessageTemplate.not(filtro1);

			public void action() {
				ACLMessage mensagem = receive(filtro2);
				if (mensagem != null) {
					System.out.println(mensagem.getSender() + " : "
							+ mensagem.getContent());
				} else
					block();
			}
		});

	}

	protected void solicitaNotificacao(final ServiceDescription sd,
			final String Solicitacao) {

		DFAgentDescription dfd = new DFAgentDescription();
		dfd.addServices(sd);

		ACLMessage mensagem = DFService.createSubscriptionMessage(this,
				getDefaultDF(), dfd, null);

		addBehaviour(new SubscriptionInitiator(this, mensagem) {

			private static final long serialVersionUID = -1785057802872640854L;

			protected void handleInform(ACLMessage inform) {
				try {

					DFAgentDescription[] dfds = DFService
							.decodeNotification(inform.getContent());

					ACLMessage mensagemCriada = new ACLMessage(
							ACLMessage.INFORM);
					mensagemCriada.addReceiver(dfds[0].getName());
					mensagemCriada.setContent(Solicitacao);
					myAgent.send(mensagemCriada);

				} catch (FIPAException fpe) {
					fpe.printStackTrace();
				}
			}
		});
	}

}
