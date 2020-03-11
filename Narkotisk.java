class Narkotisk extends Legemiddel{
  protected int styrke;

  //Konstruktor
  public Narkotisk(String nvn, Double virkestoff, Double prs, int styrk){
    super(nvn, virkestoff, prs);
    styrke = styrk;
  }

  //Henter styrke
  public int hentStyrke(){
    return styrke;
  }

  //Definerer toString
  public String toString(){
    return ("Navn: "+navn +"\nVirkestoff i mg: "+mgvirkestoff+"\nPris: "+pris+"\nStyrke: "+styrke);
  }
}
