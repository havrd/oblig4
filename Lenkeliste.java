import java.util.Iterator;

class Lenkeliste<T> implements Liste<T>{
  //Klassen Node tar vare på data for hvert element i listen, og er lenket til det neste elementet i listen.
  class Node{
    protected Node neste = null;
    protected T data;

    public Node(T x){
      data = x;
    }
  }

  class LenkelisteIterator implements Iterator<T>{
    protected int iteratorPosisjon = 0;
    private Node naaverendeNode = start;

    public boolean hasNext(){
      if(iteratorPosisjon == 0 && naaverendeNode != null) return true;
      else if(naaverendeNode==null || naaverendeNode.neste == null) return false;
      else return true;
    }

    public T next(){
      if(iteratorPosisjon==0){
        iteratorPosisjon++;
        return naaverendeNode.data;
      }
      else{
        naaverendeNode = naaverendeNode.neste;
        iteratorPosisjon++;
        return naaverendeNode.data;
      }
    }

    public void remove(){
      if(iteratorPosisjon==0){
        throw new UgyldigListeIndeks(-1);
      }
      else{
        fjern(iteratorPosisjon-1);
        iteratorPosisjon--;
      }
    }
  }

  //Teller er antall elementer i listen, og start er første element.
  protected int teller;
  protected Node start = null;

  //Konstruktøren setter start til null, og teller til 0
  public Lenkeliste(){
    start = null;
    teller=0;
  }

  public Iterator iterator(){
    return new LenkelisteIterator();
  }

  //Returnerer størrelsen til listen, altså antall elementer
  public int stoerrelse(){
    return teller;
  }

  //Metoden legger inn et element i en ønsket plass i listen.
  public void leggTil(int pos, T x){
    if(pos>teller || pos<0) throw new UgyldigListeIndeks(pos);
    Node ny = new Node(x);
    //Hvis den skal legges til først i listen.
    if(pos==0){
      ny.neste = start;
      start = ny;
      teller++;
    }
    //Ellers vil programmet iterere gjennom elementene frem til riktig posisjon og sette elementet inn der.
    else{
      Node forrige = null;
      Node denne = start;
      for(int i=0; i<pos;i++){
        forrige = denne;
        denne = denne.neste;
      }
      forrige.neste = ny;
      ny.neste = denne;
      teller++;
    }
  }

  //Metoden legger til et element bakerst i listen.
  public void leggTil(T x){
    Node ny = new Node(x);
    if(teller==0){
      start = ny;
    }
    else{
      Node denne = start;
      while(denne.neste!=null){
        denne=denne.neste;
      }
      denne.neste=ny;
    }
    teller++;
  }

  //Metoden erstatter et element i en gitt posisjon.
  public void sett(int pos, T x){
    if(pos>=teller || teller==0 || pos<0) throw new UgyldigListeIndeks(pos);
    Node ny = new Node(x);
    if(pos==0){
      ny.neste=start.neste;
      start=ny;
    }
    else{
      Node denne = start;
      Node forrige = start;
      for(int i=0; i<pos;i++){
        forrige = denne;
        denne = denne.neste;
      }
      ny.neste = denne.neste;
      forrige.neste = ny;
    }
  }

  //Metoden henter ut et element i en gitt posisjon.
  public T hent(int pos){
    if(pos>=teller || pos<0) throw new UgyldigListeIndeks(pos);
    Node index = start;
    for(int i=0; i<pos;i++){
      index = index.neste;
    }
    return index.data;
  }

  //Metoden fjerner og returnerer et element i en gitt posisjon.
  public T fjern(int pos){
    if(pos>=teller || pos<0) throw new UgyldigListeIndeks(pos);
    if(pos==0){
      return fjern();
    }
    Node index = start;
    Node neste = start.neste;
    for(int i=0; i<pos-1;i++){
      index = index.neste;
      neste = index.neste;
    }
    index.neste = neste.neste;
    teller--;

    return neste.data;
  }

  //Metoden fjerner og returnerer det første elementet i listen.
  public T fjern(){
    if(teller<=0) throw new UgyldigListeIndeks(-1);
    Node gammelstart = start;
    start = start.neste;
    teller--;
    return gammelstart.data;
  }

}
