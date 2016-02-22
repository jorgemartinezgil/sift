import com.hp.hpl.jena.ontology.*;
import com.hp.hpl.jena.rdf.model.*;
import com.hp.hpl.jena.shared.PrefixMapping;
import com.hp.hpl.jena.util.iterator.Filter;
import java.util.*;


public class ClassHierarchy {

    protected OntModel m_model;
    private Map m_anonIDs = new HashMap();
    private int m_anonCount = 0;
    
    private List<Cell> lista = new ArrayList<Cell>();

    public void loadCellList(OntModel m ) {
        // create an iterator over the root classes that are not anonymous class expressions
        Iterator i = m.listHierarchyRootClasses()
                      .filterDrop( new Filter() {
                                    public boolean accept( Object o ) {
                                        return ((Resource) o).isAnon();
                                    }} );

        while (i.hasNext()) {
            showClass((OntClass) i.next(), new ArrayList(), 0 );
        }

        reordenar();
    }

    public void mostrar ()
    {
    
	for (int i = 0; i < lista.size(); i++) {
      	System.out.println ("Depth: " + lista.get(i).getLevel());
            System.out.println ("Children: " + lista.get(i).getNumChildren());
	 	System.out.println ("Brothers: " + lista.get(i).getNumBrothers());
	  	System.out.println ("Brothers on the left: " + lista.get(i).getNumLeftBrothers());
            System.out.println ("Same level: " + lista.get(i).getNumSameLevel());
	 	System.out.println (lista.get(i).getName());
	 	System.out.println ("------------------------------");
        }
    }

    public void mostrar2 ()
    {
      System.out.println ("Concept   Depth   Children   Brothers   Left Brothers   Same Level");
      System.out.println ("-------------------------------------------------------------------");
	for (int i = 0; i < lista.size(); i++) {
      	System.out.println (lista.get(i).getName()+ " | " + lista.get(i).getLevel() + " | " + lista.get(i).getNumChildren() + " | " + lista.get(i).getNumBrothers()  + " | " +  lista.get(i).getNumLeftBrothers() + " | " + lista.get(i).getNumSameLevel());
	 	
	 	//System.out.println ("------------------------------");
        }
    }


    protected void showClass(OntClass cls, List occurs, int depth ) {
        renderClassDescription(cls, depth );
        //out.println();

        // recurse to the next level down
        if (cls.canAs( OntClass.class )  &&  !occurs.contains( cls )) {
            for (Iterator i = cls.listSubClasses( true );  i.hasNext(); ) {
                OntClass sub = (OntClass) i.next();

                // we push this expression on the occurs list before we recurse
                occurs.add( cls );
                showClass(sub, occurs, depth + 1 );
                occurs.remove( cls );
            }
        }
    }



    public void renderClassDescription(OntClass c, int depth ) {
        
        //System.out.println ("Profundidad: " + depth + ":");
        //indent( out, depth );

        if (c.isRestriction()) {
            renderRestriction((Restriction) c.as( Restriction.class ) );
        }
        else {
            if (!c.isAnon()) {
                //out.print( "Class " );
                renderURI(c.getModel(), c.getURI() );
                //out.print( ' ' );
            }
            else {
                renderAnonymous(c, "class" );
            }
        }


        Cell celda = new Cell (depth, 0, 0, 0, 0, c.getModel().shortForm(c.getURI()));
        lista.add ((Cell)celda); 

    }
    
    
   private void reordenar () 
   {
      
      int hermanos_izq;
      int hermanos;
      int hijos;
      int mismo_nivel;
      boolean enc = false;  //variable auxiliar     

      for (int i = 0; i < lista.size (); i++) {
         hermanos_izq = 0;
         hermanos = 0;
         hijos = 0;
         mismo_nivel = 0; 
         enc = false;
         
         for (int j = 0; j < lista.size (); j++) {
            if (j<i) {
               if(lista.get (i).getLevel () == lista.get(j).getLevel ()){
                  hermanos++;
                  hermanos_izq++;
               } 
               if(lista.get (j).getLevel () < lista.get(i).getLevel ()){
                  hermanos = 0;
                  hermanos_izq = 0;
               } 
            }

            if (j>i) {
               if(lista.get (i).getLevel () == lista.get(j).getLevel ()){
                  hermanos++;
               } 
               if(lista.get (j).getLevel () < lista.get(i).getLevel ()){
                  j = lista.size () + 99;
               } 
            }
                            
             
            if ((j == i +1) && (lista.get (i).getLevel () + 1 == lista.get(j).getLevel ()) && (!enc)) {
               for (int k = j; lista.get(k).getLevel () >= lista.get(j).getLevel() && k < lista.size()-1; k++) {
                  if (lista.get(k).getLevel() == lista.get(j).getLevel())
                     hijos++;
                  if (lista.get(k).getLevel() == lista.get(j).getLevel())
                     enc = true;
               }
            }
      
         }

         for (int t = 0; t < lista.size(); t++)
            if ((t != i) && (lista.get (i).getLevel () == lista.get(t).getLevel ()))
              mismo_nivel++;
    
         lista.get(i).setNumChildren (hijos);
         lista.get(i).setNumBrothers (hermanos);
         lista.get(i).setNumLeftBrothers (hermanos_izq);
         lista.get(i).setNumSameLevel (mismo_nivel);
     }  
      
   }
  
    public String toString()
    {
    	String res ="";
      for (int i = 0; i < lista.size(); i++) {
        lista.get(i).getName();
      }
	for (int i = 0; i < lista.size(); i++) {
      	String str = ("Nivel: " + lista.get(i).getLevel())
      		+ ("\nHijos: " + lista.get(i).getNumChildren()) 
      		+ ("Hermanos: " + lista.get(i).getNumBrothers()) 
	  	    + ("Hermanos izquierda: " + lista.get(i).getNumLeftBrothers())
	  	    + (lista.get(i).getName())
      		+ ("------------------------------");
      		res = res + str+"\n\n";
        }
	return res;
    }


    protected void renderRestriction(Restriction r ) {
        if (!r.isAnon()) {
            //out.print( "Restriction " );
            renderURI(r.getModel(), r.getURI() );
        }
        else {
            renderAnonymous(r, "restriction" );
        }

        //out.print( " on property " );
        renderURI(r.getModel(), r.getOnProperty().getURI() );
    }


    protected void renderURI(PrefixMapping prefixes, String uri ) {
        //out.print( prefixes.shortForm( uri ) );
    }


    protected void renderAnonymous(Resource anon, String name ) {
        String anonID = (String) m_anonIDs.get( anon.getId() );
        if (anonID == null) {
            anonID = "a-" + m_anonCount++;
            m_anonIDs.put( anon.getId(), anonID );
        }

    }


    protected void indent(int depth ) {

    }


	public List<Cell> getList() {
		
		return lista;
	}



}
