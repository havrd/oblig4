class SortertLenkeliste<T extends Comparable<T>> extends Lenkeliste<T>{

  //Metoden legger til et element på riktig plass i listen ved å sammenligne størrelse.
  @Override
  public void leggTil(T x){
    Node ny = new Node(x);

    //Hvis listen er tom, eller elementet som skal legges til er mindre eller lik det første elementet.
    if(teller == 0 || x.compareTo(start.data)<=0){
      ny.neste = start;
      start = ny;
    }
    //Itererer gjennom listen og sammenligner størrelse for å legge til elementet i riktig posisjon.
    else{
      Node denne = start;
      while(denne.neste!=null && x.compareTo(denne.neste.data)>0){
        denne = denne.neste;
      }
      ny.neste = denne.neste;
      denne.neste = ny;
    }
    teller++;
  }

  //Fjerner og returnerer det største elementet, altså det siste i listen.
  @Override
  public T fjern(){
    if(teller>1){
      return fjern(teller-1);
    }
    else{
      return super.fjern();
    }
  }

  //Kaster et unntak
  @Override
  public void leggTil(int pos, T x){
    throw new UnsupportedOperationException();
  }

  //Kaster et unntak
  @Override
  public void sett(int pos, T x){
    throw new UnsupportedOperationException();
  }
}
