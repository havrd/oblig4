class Vanedannende extends Legemiddel{
  protected int styrke;

  //Konstruktor
  public Vanedannende(String nvn, Double prs, Double virkestoff, int styrk){
    super(nvn, prs, virkestoff);
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

  public String hentType(){
    return "vanedannende";
  }
}
