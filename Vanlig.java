class Vanlig extends Legemiddel{

  //Konstruktor som sender alle argumentene videre
  public Vanlig(String nvn, Double virkestoff, Double prs){
    super(nvn, virkestoff, prs);
  }

  //Definerer toString
  public String toString(){
    return ("Navn: "+navn +"\nVirkestoff i mg: "+mgvirkestoff+"\nPris: "+pris);
  }
}
