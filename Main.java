import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import java.util.*;

public class Main {
  

    public static void main( String[] args ) {
        OntModel m = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );
        OntModel m2 = ModelFactory.createOntologyModel( OntModelSpec.OWL_MEM, null );

	  //m.getDocumentManager().addAltEntry( "http://www.w3.org/1999/02/22-rdf-syntax-ns#", "file:" + args[0] ); 

        m.read( "file:" + args[0] );

        ClassHierarchy CH = new ClassHierarchy();
        //new ClassHierarchy().showHierarchy( System.out, m ); 
        java.util.Date inicio=new java.util.Date();
        CH.loadCellList(m);
        java.util.Date fin=new java.util.Date();
        CH.mostrar();
        long ttotal=fin.getTime()-inicio.getTime();
        System.out.println("Total time "+ttotal/1000f+" seconds");


        //Uncomment this if you want to align two ontologies in a structural way       

        List<Cell> lista = new ArrayList<Cell>();
        lista = CH.getList ();

        m2.read( "file:" + args[1] );

        ClassHierarchy CH2 = new ClassHierarchy();
        //new ClassHierarchy().showHierarchy( System.out, m2 ); 
        //java.util.Date inicio=new java.util.Date();
        CH2.loadCellList(m2);
        //java.util.Date fin=new java.util.Date();
        CH2.mostrar();
        //long ttotal=fin.getTime()-inicio.getTime();
        //System.out.println("Total time "+ttotal/1000f+" second");

        
        List<Cell> lista2 = new ArrayList<Cell>();
        lista2 = CH2.getList ();  
      
        double result = 0.00;
        for (int i = 0; i < lista.size(); i++) {
          for (int j = 0; j < lista2.size(); j++) {
           result = 0.00;
           if (lista.get(i).getLevel() == lista2.get(j).getLevel())
             result = result + 0.20;
           if (lista.get(i).getNumChildren() == lista2.get(j).getNumChildren())
             result = result + 0.20;
           if (lista.get(i).getNumBrothers() == lista2.get(j).getNumBrothers())
             result = result + 0.20;         
           if (lista.get(i).getNumLeftBrothers() == lista2.get(j).getNumLeftBrothers())
             result = result + 0.20;         
           if (lista.get(i).getNumSameLevel() == lista2.get(j).getNumSameLevel())
             result = result + 0.20;
         
           if (result > 0.1) 
            System.out.println (lista.get(i).getName() + " vs " + lista2.get(j).getName() + " -> " + result);
        }
       } 


        int resulta = 0;
        for (int i = 0; i < lista.size(); i++) {
             resulta = resulta + lista.get(i).getLevel() ;
             resulta = resulta + lista.get(i).getNumChildren() ;
             resulta = resulta + lista.get(i).getNumBrothers();         
             resulta = resulta + lista.get(i).getNumLeftBrothers();         
             resulta = resulta + lista.get(i).getNumSameLevel();
             
        }
        System.out.println ("Structural index: " + resulta);

        int resulta2 = 0;
        for (int i = 0; i < lista2.size(); i++) {
             resulta2 = resulta2 + lista2.get(i).getLevel() ;
             resulta2 = resulta2 + lista2.get(i).getNumChildren() ;
             resulta2 = resulta2 + lista2.get(i).getNumBrothers();         
             resulta2 = resulta2 + lista2.get(i).getNumLeftBrothers();         
             resulta2 = resulta2 + lista2.get(i).getNumSameLevel();
             
        }
       System.out.println ("Structural index: " + resulta2); 

    }


    // it computes the max between two numbers
    private static int max (int a, int b)
    { 
       if (a == 0 || b == 0) return 1;        

       if (a >= b)
         return a;
       else
         return b; 
    }

    // it computes the min between two numbers
    private static int min (int a, int b)
    { 
       if (a <= b)
         return a;
       else
         return b; 
    }
   
}