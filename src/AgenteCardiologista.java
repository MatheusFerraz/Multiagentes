import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class AgenteCardiologista extends Agent {
	
	private static final long serialVersionUID = 3464874314236807824L;

	protected void setup() {
		
		ServiceDescription servico = new ServiceDescription();
		servico.setType("Trata de problemas card�acos");
		servico.setName(this.getLocalName());
		resgistrarServico(servico);
		recebeMensagem("Problema Card�aco", "Vou resolver os problemas card�acos do paciente.");
		
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

			private static final long serialVersionUID = -6793138883957438644L;

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
