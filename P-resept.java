class Presept extends HvitResept{

  //Konstruktor
  public Presept(Legemiddel lm, Lege l, Pasient pasient){
    super(lm,l,pasient,3);
  }

  //Returnerer pris-108 hvis legemiddelet koster mer enn 108kr, ellers returnerer 0
  public Double prisAaBetale(){
    if(legemiddel.hentPris()>= 108){
      return legemiddel.hentPris()-108;
    }
    return 0.0;
  }

  //Definerer toString
  public String toString(){
    return ("Legemiddel: "+legemiddel.hentNavn() +"\nUtskrivende lege: "+utskrivendeLege.hentNavn()+"\nPasient: "+pasient+"\nAntall reiterasjoner: "+reit+"\nPris: "+prisAaBetale());
  }
}
