class Narkotisk extends Legemiddel{
  protected int styrke;

  //Konstruktor
  public Narkotisk(String nvn, Double prs, Double virkestoff, int styrk){
    super(nvn, prs, virkestoff);
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

  public String hentType(){
    return "narkotisk";
  }
}
