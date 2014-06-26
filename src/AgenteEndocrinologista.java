import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

public class AgenteEndocrinologista extends Agent {
	
	private static final long serialVersionUID = 4259278883710078167L;

	protected void setup() {
		
		ServiceDescription servico = new ServiceDescription();
		servico.setType("Trata de problemas endócrinos");
		servico.setName(this.getLocalName());
		resgistrarServico(servico);
		recebeMensagem("Problema de glândula", "Vou resolver os problemas endócrinos do paciente.");
		
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

			private static final long serialVersionUID = 3655543590919128887L;

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

