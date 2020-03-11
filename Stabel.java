class Stabel<T> extends Lenkeliste<T>{
  //Metoden legger på et element bakerst i listen ved å kalle på leggTil() fra superklassen.
  public void leggPaa(T x){
    leggTil(x);
  }

  //Metoden fjerner og returnerer det siste elementet i listen ved å kalle på metoden fjern(int pos) fra superklassen.
  public T taAv(){
    if(teller<=0) throw new UgyldigListeIndeks(-1);
    return fjern(teller-1);
  }
}
