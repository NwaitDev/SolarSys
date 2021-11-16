package jrubytrying ;

public abstract class AbstractPerson {
    //
    public abstract void hello() ;
    //
    public void repeat(int nbtimes) {
	if (nbtimes <= 0) System.out.println("I shut up;") ;
	else for (int i = 0 ; i < nbtimes ; i++) hello() ;
    }
    //
}
