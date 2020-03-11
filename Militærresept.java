class Militaerresept extends HvitResept{

  //Konstruktor
  public Militaerresept(Legemiddel lm, Lege l, Pasient pasient, int r){
    super(lm,l,pasient,r);
  }

  //Alle militaerresepter er gratis, saa returnerer 0
  public Double prisAaBetale(){
    return legemiddel.hentPris()*0;
  }

  //Definerer toString
  public String toString(){
    return ("Legemiddel: "+legemiddel.hentNavn() +"\nUtskrivende lege: "+utskrivendeLege.hentNavn()+"\nPasient: "+pasient+"\nAntall reiterasjoner: "+reit+"\nPris: "+this.prisAaBetale());
  }
}
