import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;


public class AgenteNefrologista extends Agent {
	
	private static final long serialVersionUID = -2167781497926095049L;

	protected void setup() {
		
		ServiceDescription servico = new ServiceDescription();
		servico.setType("Trata de problemas do sistema unirário");
		servico.setName(this.getLocalName());
		resgistrarServico(servico);
		recebeMensagem("Problema Urinário", "Vou diagnosticar e tratar os problemas urinários do paciente.");
		
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

			private static final long serialVersionUID = 5685788877024464710L;

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
