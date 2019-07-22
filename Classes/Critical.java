package completo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Critical extends Thread{

	//Estrutura para organizar requisi��es(heap)
	List<Integer> heap;
	Random r;
	boolean Requisicao;
	int processoCritico;
	
        //Critical vai receber a instancia de Rede
        Rede rede;
        
	public Critical(Rede rd){
		 r = new Random();
		 heap = new ArrayList<Integer>();
		 Requisicao = false;
                 
                 //Instancia da classe Rede
                 this.rede = rd;
	}

	void InserirRequisicao(int x){
		heap.add(x);
		Requisicao = false;
	}
	
	
	synchronized void enter() throws InterruptedException{
		processoCritico = heap.get(0);		
		System.out.println("Entrando em sessao critica " + processoCritico);
		resourceAccesses();		
		exit();
	}
	
	void resourceAccesses() throws InterruptedException{
		Thread.sleep((r.nextInt(8) + 5)*1000);
	}
	
	void exit(){		
		System.out.println("Saindo da sessao critica para o processo " + processoCritico);
		
                rede.p[heap.remove(0)].leaveCritical();
                //heap.remove(0);
		//Requisicao = false;
	}

	@Override
	public void run() {

		while(true){
			if(heap.size() > 0){
				try {
					enter();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
