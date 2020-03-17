abstract class Resept{
  protected static int id;
  protected Legemiddel legemiddel;
  protected Lege utskrivendeLege;
  protected Pasient pasient;
  protected int reit;
  protected int denneId;

  //Konstruktor tar imot argumentene og lagrer dem i variabler. oker ogsaa den statiske variablen med 1, og setter ID paa
  //instansene som blir opprettet
  public Resept(Legemiddel lm, Lege l, Pasient pid, int r){
    if(lm==null || l==null || pid==null){
      throw new NullPointerException();
    }
    else{
      legemiddel = lm;
      utskrivendeLege = l;
      pasient = pid;
      reit = r;
      denneId = id;
      id++;
    }
  }

  //Henter ID
  public int hentId(){
    return denneId;
  }

  //Returnerer en referanse til et legemiddel-objekt
  public Legemiddel hentLegemiddel(){
    return legemiddel;
  }

  //Returnerer en referanse til et lege-objekt
  public Lege hentLege(){
    return utskrivendeLege;
  }

  //Returnerer pasient
  public Pasient hentPasient(){
    return pasient;
  }

  public int hentReit(){
    return reit;
  }

  //Sjekker om antall reiterasjoner er mer enn 0, og returnerer true eller false
  public Boolean bruk(){
    if(reit>0){
      reit--;
      return true;
    }
    return false;
  }

  //Tre abstrakte metoder som defineres i subklassene
  abstract public String farge();

  abstract public Double prisAaBetale();

  abstract public String toString();


}
