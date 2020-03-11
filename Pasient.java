class Pasient{
  private String navn;
  private int fnr;
  private static int ID;
  private int denneID;
  private Stabel<Resept> resepter;

  public Pasient(String nvn, int fodselsnummer){
    navn = nvn;
    fnr = fodselsnummer;
    denneID = ID;
    ID++;
  }

  public Stabel hentResepter(){
    return resepter;
  }

  public void leggTilResept(Resept res){
    resepter.leggPaa(res);
  }
}
