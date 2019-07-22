package completo;

public class Processo {

	private int coordenador;
	private int rank;
	private Critical sessoesCritica;
	private int id;
	
        
	public Processo(int id, Rede rd){
		//Processo vai receber a instancia da classe rede para passar para critical
        sessoesCritica = new Critical(rd);
		setId(id);
		setCoordenador(id);
		setRank(0);
		sessoesCritica.start();
	}
	
	public int getCoordenador() {
		return coordenador;
	}
	public void setCoordenador(int coordenador) {
		this.coordenador = coordenador;
	}
	public int getRank() {
		return rank;
	}
	public void setRank(int rank) {
		this.rank = rank;
	}
	public void incRank(){
		this.rank++;
	}

	//Sess�o Critica
	public void enterCritical(){
		sessoesCritica.Requisicao = true;
	}
	public boolean checkCritical(){
		return sessoesCritica.Requisicao;
	}
	public void leaveCritical(){
               sessoesCritica.Requisicao = false;
        }
	
	public void addRequest(int x){
		sessoesCritica.InserirRequisicao(x);
	}
	
	public void imprimirRequisicoes() throws InterruptedException{	
		System.out.println("Lista de requisi��es criticas do PC " +  id + " ");
		for(int j = 0; j < sessoesCritica.heap.size(); j++)
			System.out.print(sessoesCritica.heap.get(j)+ " ");
		System.out.println();
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
}
