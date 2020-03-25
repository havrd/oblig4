abstract class Legemiddel{
  protected String navn;
  protected Double mgvirkestoff;
  protected Double pris;
  protected static int id;
  protected int denneId;

  //Konstruktor tar imot argumentene og lagrer dem i variabler. oker ogsaa den statiske variablen med 1, og setter ID paa
  //instansene som blir opprettet
  public Legemiddel(String nvn, Double prs, Double virkestoff){
    if(nvn==null || prs==null || virkestoff==null){
      throw new NullPointerException();
    }
    navn = nvn;
    mgvirkestoff = virkestoff;
    pris = prs;
    denneId=id;
    id++;
  }

  //Henter navn
  public String hentNavn(){
    return navn;
  }

  //Henter mg virkestoff
  public Double hentVirkestoff(){
    return mgvirkestoff;
  }

  //Henter pris
  public Double hentPris(){
    return pris;
  }

  //Henter ID
  public int hentId(){
    return denneId;
  }

  //Setter ny pris paa legemiddelet
  public void settNyPris(Double nypris){
    pris = nypris;
  }

  abstract public String toString();

  abstract public String hentType();

  abstract public int hentStyrke();

}
