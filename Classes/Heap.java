package completo;

import java.util.Random;


public class Heap {
	
    Elemento dados[];
    int tamanhoAtual;
    int tamanhoMaximo;
    Random r;
    
    public Heap (int t){
	    this.dados = new Elemento[t];
	    this.tamanhoAtual = 0;
	    this.tamanhoMaximo = t;
	    r  = new Random();
    }
    
    public int sucEsq (int i) {
    	return 2*i+1; 
    }

    public int sucDir (int i) {
    	return 2*i+2;
    }
    
    public int pai (int i) {
    	if ( i > 0 ) {
    		return (i-1)/2; 
    	}else{
    		return -1; 
    	}
    }

    void moverparacima (int i) {
    	int p = pai(i);
    	if ( p>=0 ) {
    		if ( this.dados[i].prioridade < this.dados[p].prioridade ) {
    			Elemento aux = this.dados[i];
    			this.dados[i] = this.dados[p];
    			this.dados[p] = aux;
    			moverparacima(p);
    		}
    	}
     }
    
    void inserir ( Elemento d ) {
    	if ( this.tamanhoAtual < this.tamanhoMaximo ) {
    		this.dados[this.tamanhoAtual] = d ;
    		this.tamanhoAtual++;
    		this.moverparacima(tamanhoAtual-1);
    	}else { 
    		System.out.println("Heap ja esta cheia");
    	}
   	}
               

    Elemento retMin ( ) {
    	return this.dados[0];
    }
    
    void moverparabaixo( int i ) {
    	int se = sucEsq(i);
    	int sd = sucDir(i);
     
	if ( sd >= this.tamanhoAtual ) {
		if ( se >= this.tamanhoAtual ) { return ; }
		if ( this.dados[se].prioridade < this.dados[i].prioridade ) {
			Elemento aux = this.dados[i];
			this.dados[i] = this.dados[se];
			this.dados[se] = aux;
			}
		}else{
	        int ms = se ; 
	        if ( this.dados[sd].prioridade < this.dados[ms].prioridade ) {
	        	ms = sd;
	        }
	        if ( this.dados[ms].prioridade < this.dados[i].prioridade ) {
	        	Elemento aux = this.dados[i];
	        	this.dados[i] = this.dados[ms];
	        	this.dados[ms] = aux;
	        	moverparabaixo(ms);
	        }
	   }
	}
    
    Elemento remover () {
    	Elemento r = new Elemento(-1);
    	//r.prioridade = -1;
    	if ( this.tamanhoAtual>= 0 ) {
    		r = this.dados[0];
    		this.dados[0] = this.dados[this.tamanhoAtual-1];
    		this.tamanhoAtual--;
    		moverparabaixo(0);
    	}
    	return r ;
    }     

    public void imprimir( ) {
    	System.out.print("\n [ ");
    	for ( int i = 0 ; i < this.tamanhoAtual; i++) {
    		System.out.print(this.dados[i].prioridade+" ");
    	}
    	System.out.print("] \n");
     }
    
    public void reorder(){
    	for(int i =0; i < tamanhoAtual; i++){
    		if( dados[i].alterado ){
    			dados[i].alterado = false;
    			dados[i].prioridade +=  r.nextInt(10);
    			moverparabaixo(i);
    			i = 0;
    		}
    	}
    	for(int i =0; i < tamanhoAtual; i++){
    		dados[i].alterado = true;
    	}
    }
}
