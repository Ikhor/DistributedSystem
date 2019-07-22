package completo;

import java.util.Random;

public class Driver {

	private int numeroPc;
	private Rede network;
	private Random r; 
	
	
	public Driver(int numeroPc) throws InterruptedException{
		this.numeroPc = numeroPc;
		network = new Rede(numeroPc);
		r = new Random();
	}
	
	public void simuladorAdHoc(int timeOut) throws InterruptedException{

		while(timeOut > 0){
			
			for(int i = 0 ; i < numeroPc; i++)
				if(r.nextInt(100) < 20)
					network.isolar(i);
			
			for(int i = 0 ; i < numeroPc; i++){
				if(r.nextInt(100) < 25){
					if(network.p[i].getRank() != -1 ){
						int connect;
						do { 
							connect = r.nextInt(numeroPc);
						}while(connect == i);						
						System.out.println("PC " + i + " requisitando " + connect);
						network.unir(i, connect);
						}else{
							network.reativar(i);
						}
					}
				}	
				network.imprimir();
				Thread.sleep(5000);
				timeOut--;
			}
		}
	
	public void simuladorSessaoCritical(int timeOut) throws InterruptedException{
		
		
		while(timeOut > 0){			
			for(int i = 0 ; i < numeroPc; i++){
				if(r.nextInt(100) < 25){					
						int connect = r.nextInt(numeroPc);						
						System.out.println("PC " + i + " requisitando sessao critica " + connect);
						network.unir(i, connect);						
						if(! network.p[i].checkCritical() ){
							network.p[i].enterCritical();
							network.p[connect].addRequest(i);
						}
						else{
							System.out.println(i + " ja esta em sessao critica");						
						}
				}
			}
			network.imprimir();
			//conj.imprimirRequisicoes();
			Thread.sleep(3000);
			timeOut--;
		}

	}
	
	public void simuladorGossip(int timeOut) throws InterruptedException{
		
		boolean falha = false;
		while(timeOut > 0){
			
			if(falha)
				for(int i = 0 ; i < numeroPc; i++)
					if(r.nextInt(100) < 20)
						network.isolar(i);
			
			for(int i = 0 ; i < numeroPc; i++){
				if(r.nextInt(100) < 25){
					if(network.p[i].getRank() != -1 ){
						int connect;
						do { 
							connect = r.nextInt(numeroPc);
						}while(connect == i);						
						System.out.println("PC " + i + " requisitando " + connect);
						network.unir(i, connect);
						}else{
							network.reativar(i);
						}
					}
				}	
				network.imprimir();
				network.imprimirCoordenadores();
				Thread.sleep(5000);
				timeOut--;
			}
		}

	public void simuladorConsenso(int timeOut) throws InterruptedException{
			
			boolean falha = true;
			while(timeOut > 0){
				
				if(falha)
					for(int i = 0 ; i < numeroPc; i++)
						if(r.nextInt(100) < 20)
							network.isolar(i);
				
				for(int i = 0 ; i < numeroPc; i++){
					if(r.nextInt(100) < 25){
						if(network.p[i].getRank() != -1 ){
							int connect;
							do { 
								connect = r.nextInt(numeroPc);
							}while(connect == i);						
							System.out.println("PC " + i + " requisitando " + connect);
							network.unir(i, connect);
						}else{
							network.reativar(i);
						}
					}
				}
				network.imprimir();
				network.imprimirCoordenadores();
				Thread.sleep(5000);
				timeOut--;
				int RequisicaoRede = r.nextInt(network.getQtdCoordenadores());
				network.consenso(RequisicaoRede);
			}
		}

	
	public static void main(String[] args) throws InterruptedException{
		
		Driver d = new Driver(4);
		d.simuladorConsenso(6);
	}			

}
