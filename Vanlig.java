class Vanlig extends Legemiddel{

  //Konstruktor som sender alle argumentene videre
  public Vanlig(String nvn, Double prs, Double virkestoff){
    super(nvn, prs, virkestoff);
  }

  //Definerer toString
  public String toString(){
    return ("Navn: "+navn +"\nVirkestoff i mg: "+mgvirkestoff+"\nPris: "+pris);
  }

  public String hentType(){
    return "vanlig";
  }

  public int hentStyrke(){
    return 0;
  }
}
