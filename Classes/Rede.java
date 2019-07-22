package completo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Rede {
	
	public Processo[] p;
	private List<Integer> coordenadores;
	Random r;

	public Rede(int t) {		
		p = new Processo[t];		
		coordenadores = new ArrayList<Integer>();
		for (int i = 0; i < t; i++) {
			p[i] = new Processo(i, this);
			adicionarCoordenador(i);
		}
		r = new Random();
	}
	public void imprimirCoordenadores(){
		for(int i = 0; i < coordenadores.size(); i++){
			System.out.print(coordenadores.get(i) + " ");
		}
		System.out.println();
	}
	
	public void imprimir() {
		
		System.out.print("Ind: [");
		for (int i = 0; i < p.length; i++) {
			System.out.print(" " + i + " ");
		}
		System.out.print(" ]\n");

		System.out.print("Cor: [");
		for (int i = 0; i < p.length; i++) {
			if(p[i].getCoordenador() != -1)
				System.out.print(" " + p[i].getCoordenador() + " ");
			else
				System.out.print(" - ");
		}
		System.out.print(" ]\n");

		System.out.print("Ran: [");
		for (int i = 0; i < p.length; i++) {
			if(p[i].getRank() != -1)
				System.out.print(" " + p[i].getRank() + " ");
			else
				System.out.print(" - ");
		}
		System.out.print(" ]\n");
		System.out.println();
	}

	int encontrar(int x) {
		if(x >= 0){
			if (p[x].getCoordenador() == x)
				return x;
			p[x].setCoordenador(encontrar(p[x].getCoordenador()));
			return p[x].getCoordenador();
			}
		return -1;
	}
	
	void isolar(int x) {		
		System.out.println("isolando elemento " + x);
		p[x].setRank(-1);
		p[x].setCoordenador(-1);
		removerCoordenador(x);//gossip
	}

	private void removerCoordenador(int x) {
//		System.out.println("Recebi " + x );
		Integer valor = x;
		if(coordenadores.contains(valor))
		coordenadores.remove(valor);
	}

	void reativar(int x){
		System.out.println("reativando elemento " + x);
		p[x].setRank(0);
		p[x].setCoordenador(x);
		adicionarCoordenador(x);//gossip
	}
	
	void unir(int x, int y) {
		int rx = encontrar(x);
		int ry = encontrar(y);

		if(rx < 0 || ry < 0){
			
			//Maquina x foi desativada 
			if(rx < 0 && p[x].getRank() < 0){
				p[y].setCoordenador(y);
				adicionarCoordenador(y);//gossip
			}
			//Maquina y foi desativada 
			if(ry < 0 && p[y].getRank() < 0){
				p[x].setCoordenador(x);
				adicionarCoordenador(x);//gossip
			}
			//pai foi desativado
			if(p[x].getCoordenador() == -1 && p[x].getRank() >= 0 ){
				p[x].setCoordenador(y);
				p[ry].incRank();
				adicionarCoordenador(y);//gossip
			}
			if(p[y].getCoordenador()  == -1 && p[y].getRank() >= 0 ){
				adicionarCoordenador(x);//gossip
				p[y].setCoordenador(x);
				p[rx].incRank();
			}
		}
		else{
			System.out.println("Rx: " + rx + " Ry: " + ry);			
			if (p[rx].getRank() > p[ry].getRank()){
				p[ry].setCoordenador(rx);
				adicionarCoordenador(rx);//gossip
				removerCoordenador(ry);
			}
			else if (rx != ry) {
				p[rx].setCoordenador(ry);
				adicionarCoordenador(ry);//gossip
				removerCoordenador(rx);
				if (p[rx].getRank()== p[ry].getRank())
					p[ry].incRank();
			}
		}
	}
	
	void adicionarCoordenador(int x){
		if(!coordenadores.contains(x))
			coordenadores.add(x);
	}
	void clearList(){
		coordenadores.clear();
	}
	
	void consenso(int requisitor){
		
		System.out.println("Estou requisitando um consenso e sou " + coordenadores.get(requisitor));
		
		int somatorioP = 0;
		int somatorioN = 0;
		int ponderacao[] = new int[coordenadores.size()];
		int mediana[] =   new int[coordenadores.size()];
		boolean votos[] = new boolean[coordenadores.size()];
		
		
		for(int i = 0; i < coordenadores.size(); i++){
			ponderacao[i] = r.nextInt(10);
			votos[i] = r.nextBoolean();
			mediana[i] = ponderacao[i];
			if(votos[i]){
				somatorioP += ponderacao[i];
			}else{
				somatorioN += ponderacao[i];
			}	
		}
		if(somatorioP > somatorioN){
			System.out.println("Permissao concedida");
		}
		else if(somatorioP == somatorioN){
			for(int i = 0; i < coordenadores.size(); i++)
				for(int j = 0; j < coordenadores.size(); j++){
					if(mediana[i] < mediana[j]){
						int aux = mediana[i];
						mediana[i] = mediana[j];
						mediana[j] = aux;
					}
				}
			
			
			int medianaValor = mediana[coordenadores.size()/2];
			medianaValor /= 2;
			if(ponderacao[requisitor] > medianaValor)
				System.out.println("Permissao concedida");
			else
				System.out.println("Permissao negada");
			
		}		
		else{
			System.out.println("Permissao negada");
		}
		
	}
	
	public int getQtdCoordenadores(){
		return coordenadores.size();
	}
}
	