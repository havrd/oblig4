class HvitResept extends Resept{

  //Konstruktor
  public HvitResept(Legemiddel lm, Lege l, Pasient pasient, int r){
    super(lm,l,pasient,r);
  }

  //Returnerer fargen til resepten
  public String farge(){
    return "hvit";
  }

  //Hvis det ikke er militaer- eller P-resept returnerer metoden bare prisen.
  public Double prisAaBetale(){
    return legemiddel.hentPris();
  }

  //Definerer toString
  public String toString(){
    return ("Legemiddel: "+legemiddel +"\nUtskrivende lege: "+utskrivendeLege.hentNavn()+"\nPasient: "+pasient+"\nAntall reiterasjoner: "+reit);
  }
}
