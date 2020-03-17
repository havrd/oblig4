class BlaaResept extends Resept{

  //Konstruktor
  public BlaaResept(Legemiddel lm, Lege l, Pasient pasient, int r){
    super(lm,l,pasient,r);
  }

  //Returnerer fargen til resepten
  public String farge(){
    return "blaa";
  }

  //Blaa resepter faar 75% avslag
  public Double prisAaBetale(){
    return legemiddel.hentPris()*0.25;
  }

  //Definerer toString
  public String toString(){
    return ("Legemiddel: "+legemiddel.hentNavn() +"\nUtskrivende lege: "+utskrivendeLege.hentNavn()+"\nPasient: "+pasient.hentNavn()+"\nAntall reiterasjoner: "+reit+"\nPris: "+prisAaBetale());
  }
}
