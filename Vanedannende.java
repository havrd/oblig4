class Vanedannende extends Legemiddel{
  protected int styrke;

  //Konstruktor
  public Vanedannende(String nvn, Double virkestoff, Double prs, int styrk){
    super(nvn, virkestoff, prs);
    styrke = styrk;
  }

  //Henter styrken til legemiddelet
  public int hentStyrke(){
    return styrke;
  }

  //Definerer toString
  public String toString(){
    return ("Navn: "+navn +"\nVirkestoff i mg: "+mgvirkestoff+"\nPris: "+pris+"\nStyrke: "+styrke);
  }
}
