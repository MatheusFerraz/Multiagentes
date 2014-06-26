import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class AgenteDermatologista extends Agent {
	
	private static final long serialVersionUID = -2505160406773526508L;

	protected void setup() {
		
		ServiceDescription servico = new ServiceDescription();
		servico.setType("Trata de problemas de pele");
		servico.setName(this.getLocalName());
		resgistrarServico(servico);
		recebeMensagem("Problema de Pele", "Vou diagnosticar e tratar os problemas de pele do paciente.");
		
	}

	protected void resgistrarServico(ServiceDescription sd) {
		DFAgentDescription dfd = new DFAgentDescription();
		dfd.addServices(sd);
		try{
			DFService.register(this, dfd);
		} catch (FIPAException e) {
			e.printStackTrace();
		}
	}
	
	protected void recebeMensagem (final String mensagem, final String resposta) {
		
		addBehaviour (new CyclicBehaviour(this) {

			private static final long serialVersionUID = 8919255256841054238L;

			public void action() {
				ACLMessage msg = receive();
				if (msg != null) {
					if (msg.getContent().equalsIgnoreCase(mensagem)) {
						ACLMessage reply = msg.createReply();
						reply.setContent(resposta);
						myAgent.send(reply);
					}
				} else 
					block();
			}
		});
	}
}
