class Pasient{
  private String navn;
  private String fnr;
  private static int ID;
  private int denneID;
  private Stabel<Resept> resepter;

  public Pasient(String nvn, String fodselsnummer){
    if(nvn==null || fodselsnummer==null){
      throw new NullPointerException();
    }
    resepter = new Stabel<Resept>();
    navn = nvn;
    fnr = fodselsnummer;
    denneID = ID;
    ID++;
  }

  public Stabel hentResepter(){
    return resepter;
  }

  public String hentNavn(){
    return navn;
  }

  public String hentFodselsnummer(){
    return fnr;
  }

  public int hentId(){
    return denneID;
  }

  public void leggTilResept(Resept res){
    resepter.leggPaa(res);
  }

  public String toString(){
    return (navn+", "+fnr);
  }
}
